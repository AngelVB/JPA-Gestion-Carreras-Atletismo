/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Corredor;
import Modelo.Carrera;
import Modelo.Marca;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Angel
 */
public class MarcaJpaController implements Serializable {

    public MarcaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Marca marca) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Corredor idCorredor = marca.getIdCorredor();
            if (idCorredor != null) {
                idCorredor = em.getReference(idCorredor.getClass(), idCorredor.getIdCorredor());
                marca.setIdCorredor(idCorredor);
            }
            Carrera idCarrera = marca.getIdCarrera();
            if (idCarrera != null) {
                idCarrera = em.getReference(idCarrera.getClass(), idCarrera.getIdCarrera());
                marca.setIdCarrera(idCarrera);
            }
            em.persist(marca);
            if (idCorredor != null) {
                idCorredor.getMarcaList().add(marca);
                idCorredor = em.merge(idCorredor);
            }
            if (idCarrera != null) {
                idCarrera.getMarcaList().add(marca);
                idCarrera = em.merge(idCarrera);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Marca marca) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca persistentMarca = em.find(Marca.class, marca.getIdMarca());
            Corredor idCorredorOld = persistentMarca.getIdCorredor();
            Corredor idCorredorNew = marca.getIdCorredor();
            Carrera idCarreraOld = persistentMarca.getIdCarrera();
            Carrera idCarreraNew = marca.getIdCarrera();
            if (idCorredorNew != null) {
                idCorredorNew = em.getReference(idCorredorNew.getClass(), idCorredorNew.getIdCorredor());
                marca.setIdCorredor(idCorredorNew);
            }
            if (idCarreraNew != null) {
                idCarreraNew = em.getReference(idCarreraNew.getClass(), idCarreraNew.getIdCarrera());
                marca.setIdCarrera(idCarreraNew);
            }
            marca = em.merge(marca);
            if (idCorredorOld != null && !idCorredorOld.equals(idCorredorNew)) {
                idCorredorOld.getMarcaList().remove(marca);
                idCorredorOld = em.merge(idCorredorOld);
            }
            if (idCorredorNew != null && !idCorredorNew.equals(idCorredorOld)) {
                idCorredorNew.getMarcaList().add(marca);
                idCorredorNew = em.merge(idCorredorNew);
            }
            if (idCarreraOld != null && !idCarreraOld.equals(idCarreraNew)) {
                idCarreraOld.getMarcaList().remove(marca);
                idCarreraOld = em.merge(idCarreraOld);
            }
            if (idCarreraNew != null && !idCarreraNew.equals(idCarreraOld)) {
                idCarreraNew.getMarcaList().add(marca);
                idCarreraNew = em.merge(idCarreraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = marca.getIdMarca();
                if (findMarca(id) == null) {
                    throw new NonexistentEntityException("The marca with id " + id + " no longer exists.");
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
            Marca marca;
            try {
                marca = em.getReference(Marca.class, id);
                marca.getIdMarca();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marca with id " + id + " no longer exists.", enfe);
            }
            Corredor idCorredor = marca.getIdCorredor();
            if (idCorredor != null) {
                idCorredor.getMarcaList().remove(marca);
                idCorredor = em.merge(idCorredor);
            }
            Carrera idCarrera = marca.getIdCarrera();
            if (idCarrera != null) {
                idCarrera.getMarcaList().remove(marca);
                idCarrera = em.merge(idCarrera);
            }
            em.remove(marca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Marca> findMarcaEntities() {
        return findMarcaEntities(true, -1, -1);
    }

    public List<Marca> findMarcaEntities(int maxResults, int firstResult) {
        return findMarcaEntities(false, maxResults, firstResult);
    }

    private List<Marca> findMarcaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Marca.class));
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

    public Marca findMarca(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Marca.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarcaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Marca> rt = cq.from(Marca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
