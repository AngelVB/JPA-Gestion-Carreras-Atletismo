/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.NonexistentEntityException;
import Modelo.Corredor;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Marca;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Angel
 */
public class CorredorJpaController implements Serializable {

    public CorredorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Corredor corredor) {
        if (corredor.getMarcaList() == null) {
            corredor.setMarcaList(new ArrayList<Marca>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Marca> attachedMarcaList = new ArrayList<Marca>();
            for (Marca marcaListMarcaToAttach : corredor.getMarcaList()) {
                marcaListMarcaToAttach = em.getReference(marcaListMarcaToAttach.getClass(), marcaListMarcaToAttach.getIdMarca());
                attachedMarcaList.add(marcaListMarcaToAttach);
            }
            corredor.setMarcaList(attachedMarcaList);
            em.persist(corredor);
            for (Marca marcaListMarca : corredor.getMarcaList()) {
                Corredor oldIdCorredorOfMarcaListMarca = marcaListMarca.getIdCorredor();
                marcaListMarca.setIdCorredor(corredor);
                marcaListMarca = em.merge(marcaListMarca);
                if (oldIdCorredorOfMarcaListMarca != null) {
                    oldIdCorredorOfMarcaListMarca.getMarcaList().remove(marcaListMarca);
                    oldIdCorredorOfMarcaListMarca = em.merge(oldIdCorredorOfMarcaListMarca);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Corredor corredor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Corredor persistentCorredor = em.find(Corredor.class, corredor.getIdCorredor());
            List<Marca> marcaListOld = persistentCorredor.getMarcaList();
            List<Marca> marcaListNew = corredor.getMarcaList();
            List<Marca> attachedMarcaListNew = new ArrayList<Marca>();
            for (Marca marcaListNewMarcaToAttach : marcaListNew) {
                marcaListNewMarcaToAttach = em.getReference(marcaListNewMarcaToAttach.getClass(), marcaListNewMarcaToAttach.getIdMarca());
                attachedMarcaListNew.add(marcaListNewMarcaToAttach);
            }
            marcaListNew = attachedMarcaListNew;
            corredor.setMarcaList(marcaListNew);
            corredor = em.merge(corredor);
            for (Marca marcaListOldMarca : marcaListOld) {
                if (!marcaListNew.contains(marcaListOldMarca)) {
                    marcaListOldMarca.setIdCorredor(null);
                    marcaListOldMarca = em.merge(marcaListOldMarca);
                }
            }
            for (Marca marcaListNewMarca : marcaListNew) {
                if (!marcaListOld.contains(marcaListNewMarca)) {
                    Corredor oldIdCorredorOfMarcaListNewMarca = marcaListNewMarca.getIdCorredor();
                    marcaListNewMarca.setIdCorredor(corredor);
                    marcaListNewMarca = em.merge(marcaListNewMarca);
                    if (oldIdCorredorOfMarcaListNewMarca != null && !oldIdCorredorOfMarcaListNewMarca.equals(corredor)) {
                        oldIdCorredorOfMarcaListNewMarca.getMarcaList().remove(marcaListNewMarca);
                        oldIdCorredorOfMarcaListNewMarca = em.merge(oldIdCorredorOfMarcaListNewMarca);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = corredor.getIdCorredor();
                if (findCorredor(id) == null) {
                    throw new NonexistentEntityException("The corredor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Corredor corredor;
            try {
                corredor = em.getReference(Corredor.class, id);
                corredor.getIdCorredor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The corredor with id " + id + " no longer exists.", enfe);
            }
            List<Marca> marcaList = corredor.getMarcaList();
            for (Marca marcaListMarca : marcaList) {
                marcaListMarca.setIdCorredor(null);
                marcaListMarca = em.merge(marcaListMarca);
            }
            em.remove(corredor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Corredor> findCorredorEntities() {
        return findCorredorEntities(true, -1, -1);
    }

    public List<Corredor> findCorredorEntities(int maxResults, int firstResult) {
        return findCorredorEntities(false, maxResults, firstResult);
    }

    private List<Corredor> findCorredorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Corredor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Corredor findCorredor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Corredor.class, id);
        } finally {
            em.close();
        }
    }

    public int getCorredorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Corredor> rt = cq.from(Corredor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
