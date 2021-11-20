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
import modelo.entidades.Solicitud;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.dao.exceptions.IllegalOrphanException;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.dao.exceptions.PreexistingEntityException;
import modelo.entidades.EstadoSolicitud;

/**
 *
 * @author Aida
 */
public class EstadoSolicitudDAO implements Serializable {

    public EstadoSolicitudDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoSolicitud estadoSolicitud) throws PreexistingEntityException, Exception {
        if (estadoSolicitud.getSolicitudCollection() == null) {
            estadoSolicitud.setSolicitudCollection(new ArrayList<Solicitud>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Solicitud> attachedSolicitudCollection = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionSolicitudToAttach : estadoSolicitud.getSolicitudCollection()) {
                solicitudCollectionSolicitudToAttach = em.getReference(solicitudCollectionSolicitudToAttach.getClass(), solicitudCollectionSolicitudToAttach.getId());
                attachedSolicitudCollection.add(solicitudCollectionSolicitudToAttach);
            }
            estadoSolicitud.setSolicitudCollection(attachedSolicitudCollection);
            em.persist(estadoSolicitud);
            for (Solicitud solicitudCollectionSolicitud : estadoSolicitud.getSolicitudCollection()) {
                EstadoSolicitud oldEstadoOfSolicitudCollectionSolicitud = solicitudCollectionSolicitud.getEstado();
                solicitudCollectionSolicitud.setEstado(estadoSolicitud);
                solicitudCollectionSolicitud = em.merge(solicitudCollectionSolicitud);
                if (oldEstadoOfSolicitudCollectionSolicitud != null) {
                    oldEstadoOfSolicitudCollectionSolicitud.getSolicitudCollection().remove(solicitudCollectionSolicitud);
                    oldEstadoOfSolicitudCollectionSolicitud = em.merge(oldEstadoOfSolicitudCollectionSolicitud);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoSolicitud(estadoSolicitud.getNombre()) != null) {
                throw new PreexistingEntityException("EstadoSolicitud " + estadoSolicitud + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoSolicitud estadoSolicitud) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoSolicitud persistentEstadoSolicitud = em.find(EstadoSolicitud.class, estadoSolicitud.getNombre());
            Collection<Solicitud> solicitudCollectionOld = persistentEstadoSolicitud.getSolicitudCollection();
            Collection<Solicitud> solicitudCollectionNew = estadoSolicitud.getSolicitudCollection();
            List<String> illegalOrphanMessages = null;
            for (Solicitud solicitudCollectionOldSolicitud : solicitudCollectionOld) {
                if (!solicitudCollectionNew.contains(solicitudCollectionOldSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitud " + solicitudCollectionOldSolicitud + " since its estado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Solicitud> attachedSolicitudCollectionNew = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionNewSolicitudToAttach : solicitudCollectionNew) {
                solicitudCollectionNewSolicitudToAttach = em.getReference(solicitudCollectionNewSolicitudToAttach.getClass(), solicitudCollectionNewSolicitudToAttach.getId());
                attachedSolicitudCollectionNew.add(solicitudCollectionNewSolicitudToAttach);
            }
            solicitudCollectionNew = attachedSolicitudCollectionNew;
            estadoSolicitud.setSolicitudCollection(solicitudCollectionNew);
            estadoSolicitud = em.merge(estadoSolicitud);
            for (Solicitud solicitudCollectionNewSolicitud : solicitudCollectionNew) {
                if (!solicitudCollectionOld.contains(solicitudCollectionNewSolicitud)) {
                    EstadoSolicitud oldEstadoOfSolicitudCollectionNewSolicitud = solicitudCollectionNewSolicitud.getEstado();
                    solicitudCollectionNewSolicitud.setEstado(estadoSolicitud);
                    solicitudCollectionNewSolicitud = em.merge(solicitudCollectionNewSolicitud);
                    if (oldEstadoOfSolicitudCollectionNewSolicitud != null && !oldEstadoOfSolicitudCollectionNewSolicitud.equals(estadoSolicitud)) {
                        oldEstadoOfSolicitudCollectionNewSolicitud.getSolicitudCollection().remove(solicitudCollectionNewSolicitud);
                        oldEstadoOfSolicitudCollectionNewSolicitud = em.merge(oldEstadoOfSolicitudCollectionNewSolicitud);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = estadoSolicitud.getNombre();
                if (findEstadoSolicitud(id) == null) {
                    throw new NonexistentEntityException("The estadoSolicitud with id " + id + " no longer exists.");
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
            EstadoSolicitud estadoSolicitud;
            try {
                estadoSolicitud = em.getReference(EstadoSolicitud.class, id);
                estadoSolicitud.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoSolicitud with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Solicitud> solicitudCollectionOrphanCheck = estadoSolicitud.getSolicitudCollection();
            for (Solicitud solicitudCollectionOrphanCheckSolicitud : solicitudCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EstadoSolicitud (" + estadoSolicitud + ") cannot be destroyed since the Solicitud " + solicitudCollectionOrphanCheckSolicitud + " in its solicitudCollection field has a non-nullable estado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estadoSolicitud);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoSolicitud> findEstadoSolicitudEntities() {
        return findEstadoSolicitudEntities(true, -1, -1);
    }

    public List<EstadoSolicitud> findEstadoSolicitudEntities(int maxResults, int firstResult) {
        return findEstadoSolicitudEntities(false, maxResults, firstResult);
    }

    private List<EstadoSolicitud> findEstadoSolicitudEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoSolicitud.class));
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

    public EstadoSolicitud findEstadoSolicitud(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoSolicitud.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoSolicitudCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoSolicitud> rt = cq.from(EstadoSolicitud.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
