/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.entidades.Cita;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.dao.exceptions.IllegalOrphanException;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.dao.exceptions.PreexistingEntityException;
import modelo.entidades.Valoracion;

/**
 *
 * @author Aida
 */
public class ValoracionDAO implements Serializable {

    public ValoracionDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Valoracion valoracion) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Cita cita1OrphanCheck = valoracion.getCita1();
        if (cita1OrphanCheck != null) {
            Valoracion oldValoracionOfCita1 = cita1OrphanCheck.getValoracion();
            if (oldValoracionOfCita1 != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Cita " + cita1OrphanCheck + " already has an item of type Valoracion whose cita1 column cannot be null. Please make another selection for the cita1 field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cita cita1 = valoracion.getCita1();
            if (cita1 != null) {
                cita1 = em.getReference(cita1.getClass(), cita1.getId());
                valoracion.setCita1(cita1);
            }
            em.persist(valoracion);
            if (cita1 != null) {
                cita1.setValoracion(valoracion);
                cita1 = em.merge(cita1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findValoracion(valoracion.getCita()) != null) {
                throw new PreexistingEntityException("Valoracion " + valoracion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Valoracion valoracion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Valoracion persistentValoracion = em.find(Valoracion.class, valoracion.getCita());
            Cita cita1Old = persistentValoracion.getCita1();
            Cita cita1New = valoracion.getCita1();
            List<String> illegalOrphanMessages = null;
            if (cita1New != null && !cita1New.equals(cita1Old)) {
                Valoracion oldValoracionOfCita1 = cita1New.getValoracion();
                if (oldValoracionOfCita1 != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Cita " + cita1New + " already has an item of type Valoracion whose cita1 column cannot be null. Please make another selection for the cita1 field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cita1New != null) {
                cita1New = em.getReference(cita1New.getClass(), cita1New.getId());
                valoracion.setCita1(cita1New);
            }
            valoracion = em.merge(valoracion);
            if (cita1Old != null && !cita1Old.equals(cita1New)) {
                cita1Old.setValoracion(null);
                cita1Old = em.merge(cita1Old);
            }
            if (cita1New != null && !cita1New.equals(cita1Old)) {
                cita1New.setValoracion(valoracion);
                cita1New = em.merge(cita1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = valoracion.getCita();
                if (findValoracion(id) == null) {
                    throw new NonexistentEntityException("The valoracion with id " + id + " no longer exists.");
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
            Valoracion valoracion;
            try {
                valoracion = em.getReference(Valoracion.class, id);
                valoracion.getCita();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The valoracion with id " + id + " no longer exists.", enfe);
            }
            Cita cita1 = valoracion.getCita1();
            if (cita1 != null) {
                cita1.setValoracion(null);
                cita1 = em.merge(cita1);
            }
            em.remove(valoracion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Valoracion> findValoracionEntities() {
        return findValoracionEntities(true, -1, -1);
    }

    public List<Valoracion> findValoracionEntities(int maxResults, int firstResult) {
        return findValoracionEntities(false, maxResults, firstResult);
    }

    private List<Valoracion> findValoracionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Valoracion.class));
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

    public Valoracion findValoracion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Valoracion.class, id);
        } finally {
            em.close();
        }
    }

    public int getValoracionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Valoracion> rt = cq.from(Valoracion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
