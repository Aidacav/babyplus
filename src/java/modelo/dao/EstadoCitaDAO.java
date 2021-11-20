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
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.dao.exceptions.IllegalOrphanException;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.dao.exceptions.PreexistingEntityException;
import modelo.entidades.EstadoCita;

/**
 *
 * @author Aida
 */
public class EstadoCitaDAO implements Serializable {

    public EstadoCitaDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoCita estadoCita) throws PreexistingEntityException, Exception {
        if (estadoCita.getCitaCollection() == null) {
            estadoCita.setCitaCollection(new ArrayList<Cita>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cita> attachedCitaCollection = new ArrayList<Cita>();
            for (Cita citaCollectionCitaToAttach : estadoCita.getCitaCollection()) {
                citaCollectionCitaToAttach = em.getReference(citaCollectionCitaToAttach.getClass(), citaCollectionCitaToAttach.getId());
                attachedCitaCollection.add(citaCollectionCitaToAttach);
            }
            estadoCita.setCitaCollection(attachedCitaCollection);
            em.persist(estadoCita);
            for (Cita citaCollectionCita : estadoCita.getCitaCollection()) {
                EstadoCita oldEstadoOfCitaCollectionCita = citaCollectionCita.getEstado();
                citaCollectionCita.setEstado(estadoCita);
                citaCollectionCita = em.merge(citaCollectionCita);
                if (oldEstadoOfCitaCollectionCita != null) {
                    oldEstadoOfCitaCollectionCita.getCitaCollection().remove(citaCollectionCita);
                    oldEstadoOfCitaCollectionCita = em.merge(oldEstadoOfCitaCollectionCita);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoCita(estadoCita.getNombre()) != null) {
                throw new PreexistingEntityException("EstadoCita " + estadoCita + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoCita estadoCita) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoCita persistentEstadoCita = em.find(EstadoCita.class, estadoCita.getNombre());
            Collection<Cita> citaCollectionOld = persistentEstadoCita.getCitaCollection();
            Collection<Cita> citaCollectionNew = estadoCita.getCitaCollection();
            List<String> illegalOrphanMessages = null;
            for (Cita citaCollectionOldCita : citaCollectionOld) {
                if (!citaCollectionNew.contains(citaCollectionOldCita)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cita " + citaCollectionOldCita + " since its estado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Cita> attachedCitaCollectionNew = new ArrayList<Cita>();
            for (Cita citaCollectionNewCitaToAttach : citaCollectionNew) {
                citaCollectionNewCitaToAttach = em.getReference(citaCollectionNewCitaToAttach.getClass(), citaCollectionNewCitaToAttach.getId());
                attachedCitaCollectionNew.add(citaCollectionNewCitaToAttach);
            }
            citaCollectionNew = attachedCitaCollectionNew;
            estadoCita.setCitaCollection(citaCollectionNew);
            estadoCita = em.merge(estadoCita);
            for (Cita citaCollectionNewCita : citaCollectionNew) {
                if (!citaCollectionOld.contains(citaCollectionNewCita)) {
                    EstadoCita oldEstadoOfCitaCollectionNewCita = citaCollectionNewCita.getEstado();
                    citaCollectionNewCita.setEstado(estadoCita);
                    citaCollectionNewCita = em.merge(citaCollectionNewCita);
                    if (oldEstadoOfCitaCollectionNewCita != null && !oldEstadoOfCitaCollectionNewCita.equals(estadoCita)) {
                        oldEstadoOfCitaCollectionNewCita.getCitaCollection().remove(citaCollectionNewCita);
                        oldEstadoOfCitaCollectionNewCita = em.merge(oldEstadoOfCitaCollectionNewCita);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = estadoCita.getNombre();
                if (findEstadoCita(id) == null) {
                    throw new NonexistentEntityException("The estadoCita with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoCita estadoCita;
            try {
                estadoCita = em.getReference(EstadoCita.class, id);
                estadoCita.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoCita with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cita> citaCollectionOrphanCheck = estadoCita.getCitaCollection();
            for (Cita citaCollectionOrphanCheckCita : citaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EstadoCita (" + estadoCita + ") cannot be destroyed since the Cita " + citaCollectionOrphanCheckCita + " in its citaCollection field has a non-nullable estado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estadoCita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoCita> findEstadoCitaEntities() {
        return findEstadoCitaEntities(true, -1, -1);
    }

    public List<EstadoCita> findEstadoCitaEntities(int maxResults, int firstResult) {
        return findEstadoCitaEntities(false, maxResults, firstResult);
    }

    private List<EstadoCita> findEstadoCitaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoCita.class));
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

    public EstadoCita findEstadoCita(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoCita.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCitaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoCita> rt = cq.from(EstadoCita.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
