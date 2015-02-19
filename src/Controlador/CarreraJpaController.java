/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.NonexistentEntityException;
import Modelo.Carrera;
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
public class CarreraJpaController implements Serializable {

    public CarreraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carrera carrera) {
        if (carrera.getMarcaList() == null) {
            carrera.setMarcaList(new ArrayList<Marca>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Marca> attachedMarcaList = new ArrayList<Marca>();
            for (Marca marcaListMarcaToAttach : carrera.getMarcaList()) {
                marcaListMarcaToAttach = em.getReference(marcaListMarcaToAttach.getClass(), marcaListMarcaToAttach.getIdMarca());
                attachedMarcaList.add(marcaListMarcaToAttach);
            }
            carrera.setMarcaList(attachedMarcaList);
            em.persist(carrera);
            for (Marca marcaListMarca : carrera.getMarcaList()) {
                Carrera oldIdCarreraOfMarcaListMarca = marcaListMarca.getIdCarrera();
                marcaListMarca.setIdCarrera(carrera);
                marcaListMarca = em.merge(marcaListMarca);
                if (oldIdCarreraOfMarcaListMarca != null) {
                    oldIdCarreraOfMarcaListMarca.getMarcaList().remove(marcaListMarca);
                    oldIdCarreraOfMarcaListMarca = em.merge(oldIdCarreraOfMarcaListMarca);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carrera carrera) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera persistentCarrera = em.find(Carrera.class, carrera.getIdCarrera());
            List<Marca> marcaListOld = persistentCarrera.getMarcaList();
            List<Marca> marcaListNew = carrera.getMarcaList();
            List<Marca> attachedMarcaListNew = new ArrayList<Marca>();
            for (Marca marcaListNewMarcaToAttach : marcaListNew) {
                marcaListNewMarcaToAttach = em.getReference(marcaListNewMarcaToAttach.getClass(), marcaListNewMarcaToAttach.getIdMarca());
                attachedMarcaListNew.add(marcaListNewMarcaToAttach);
            }
            marcaListNew = attachedMarcaListNew;
            carrera.setMarcaList(marcaListNew);
            carrera = em.merge(carrera);
            for (Marca marcaListOldMarca : marcaListOld) {
                if (!marcaListNew.contains(marcaListOldMarca)) {
                    marcaListOldMarca.setIdCarrera(null);
                    marcaListOldMarca = em.merge(marcaListOldMarca);
                }
            }
            for (Marca marcaListNewMarca : marcaListNew) {
                if (!marcaListOld.contains(marcaListNewMarca)) {
                    Carrera oldIdCarreraOfMarcaListNewMarca = marcaListNewMarca.getIdCarrera();
                    marcaListNewMarca.setIdCarrera(carrera);
                    marcaListNewMarca = em.merge(marcaListNewMarca);
                    if (oldIdCarreraOfMarcaListNewMarca != null && !oldIdCarreraOfMarcaListNewMarca.equals(carrera)) {
                        oldIdCarreraOfMarcaListNewMarca.getMarcaList().remove(marcaListNewMarca);
                        oldIdCarreraOfMarcaListNewMarca = em.merge(oldIdCarreraOfMarcaListNewMarca);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = carrera.getIdCarrera();
                if (findCarrera(id) == null) {
                    throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.");
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
            Carrera carrera;
            try {
                carrera = em.getReference(Carrera.class, id);
                carrera.getIdCarrera();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.", enfe);
            }
            List<Marca> marcaList = carrera.getMarcaList();
            for (Marca marcaListMarca : marcaList) {
                marcaListMarca.setIdCarrera(null);
                marcaListMarca = em.merge(marcaListMarca);
            }
            em.remove(carrera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carrera> findCarreraEntities() {
        return findCarreraEntities(true, -1, -1);
    }

    public List<Carrera> findCarreraEntities(int maxResults, int firstResult) {
        return findCarreraEntities(false, maxResults, firstResult);
    }

    private List<Carrera> findCarreraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carrera.class));
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

    public Carrera findCarrera(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carrera.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarreraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carrera> rt = cq.from(Carrera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
