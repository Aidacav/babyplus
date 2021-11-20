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
import modelo.entidades.ProveedorServicio;
import modelo.entidades.Servicio;

/**
 *
 * @author Aida
 */
public class ServicioDAO implements Serializable {

    public ServicioDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servicio servicio) {
        if (servicio.getSolicitudCollection() == null) {
            servicio.setSolicitudCollection(new ArrayList<Solicitud>());
        }
        if (servicio.getProveedorServicioCollection() == null) {
            servicio.setProveedorServicioCollection(new ArrayList<ProveedorServicio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Solicitud> attachedSolicitudCollection = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionSolicitudToAttach : servicio.getSolicitudCollection()) {
                solicitudCollectionSolicitudToAttach = em.getReference(solicitudCollectionSolicitudToAttach.getClass(), solicitudCollectionSolicitudToAttach.getId());
                attachedSolicitudCollection.add(solicitudCollectionSolicitudToAttach);
            }
            servicio.setSolicitudCollection(attachedSolicitudCollection);
            Collection<ProveedorServicio> attachedProveedorServicioCollection = new ArrayList<ProveedorServicio>();
            for (ProveedorServicio proveedorServicioCollectionProveedorServicioToAttach : servicio.getProveedorServicioCollection()) {
                proveedorServicioCollectionProveedorServicioToAttach = em.getReference(proveedorServicioCollectionProveedorServicioToAttach.getClass(), proveedorServicioCollectionProveedorServicioToAttach.getProveedorServicioPK());
                attachedProveedorServicioCollection.add(proveedorServicioCollectionProveedorServicioToAttach);
            }
            servicio.setProveedorServicioCollection(attachedProveedorServicioCollection);
            em.persist(servicio);
            for (Solicitud solicitudCollectionSolicitud : servicio.getSolicitudCollection()) {
                Servicio oldServicioOfSolicitudCollectionSolicitud = solicitudCollectionSolicitud.getServicio();
                solicitudCollectionSolicitud.setServicio(servicio);
                solicitudCollectionSolicitud = em.merge(solicitudCollectionSolicitud);
                if (oldServicioOfSolicitudCollectionSolicitud != null) {
                    oldServicioOfSolicitudCollectionSolicitud.getSolicitudCollection().remove(solicitudCollectionSolicitud);
                    oldServicioOfSolicitudCollectionSolicitud = em.merge(oldServicioOfSolicitudCollectionSolicitud);
                }
            }
            for (ProveedorServicio proveedorServicioCollectionProveedorServicio : servicio.getProveedorServicioCollection()) {
                Servicio oldServicio1OfProveedorServicioCollectionProveedorServicio = proveedorServicioCollectionProveedorServicio.getServicio1();
                proveedorServicioCollectionProveedorServicio.setServicio1(servicio);
                proveedorServicioCollectionProveedorServicio = em.merge(proveedorServicioCollectionProveedorServicio);
                if (oldServicio1OfProveedorServicioCollectionProveedorServicio != null) {
                    oldServicio1OfProveedorServicioCollectionProveedorServicio.getProveedorServicioCollection().remove(proveedorServicioCollectionProveedorServicio);
                    oldServicio1OfProveedorServicioCollectionProveedorServicio = em.merge(oldServicio1OfProveedorServicioCollectionProveedorServicio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servicio servicio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicio persistentServicio = em.find(Servicio.class, servicio.getId());
            Collection<Solicitud> solicitudCollectionOld = persistentServicio.getSolicitudCollection();
            Collection<Solicitud> solicitudCollectionNew = servicio.getSolicitudCollection();
            Collection<ProveedorServicio> proveedorServicioCollectionOld = persistentServicio.getProveedorServicioCollection();
            Collection<ProveedorServicio> proveedorServicioCollectionNew = servicio.getProveedorServicioCollection();
            List<String> illegalOrphanMessages = null;
            for (Solicitud solicitudCollectionOldSolicitud : solicitudCollectionOld) {
                if (!solicitudCollectionNew.contains(solicitudCollectionOldSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitud " + solicitudCollectionOldSolicitud + " since its servicio field is not nullable.");
                }
            }
            for (ProveedorServicio proveedorServicioCollectionOldProveedorServicio : proveedorServicioCollectionOld) {
                if (!proveedorServicioCollectionNew.contains(proveedorServicioCollectionOldProveedorServicio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProveedorServicio " + proveedorServicioCollectionOldProveedorServicio + " since its servicio1 field is not nullable.");
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
            servicio.setSolicitudCollection(solicitudCollectionNew);
            Collection<ProveedorServicio> attachedProveedorServicioCollectionNew = new ArrayList<ProveedorServicio>();
            for (ProveedorServicio proveedorServicioCollectionNewProveedorServicioToAttach : proveedorServicioCollectionNew) {
                proveedorServicioCollectionNewProveedorServicioToAttach = em.getReference(proveedorServicioCollectionNewProveedorServicioToAttach.getClass(), proveedorServicioCollectionNewProveedorServicioToAttach.getProveedorServicioPK());
                attachedProveedorServicioCollectionNew.add(proveedorServicioCollectionNewProveedorServicioToAttach);
            }
            proveedorServicioCollectionNew = attachedProveedorServicioCollectionNew;
            servicio.setProveedorServicioCollection(proveedorServicioCollectionNew);
            servicio = em.merge(servicio);
            for (Solicitud solicitudCollectionNewSolicitud : solicitudCollectionNew) {
                if (!solicitudCollectionOld.contains(solicitudCollectionNewSolicitud)) {
                    Servicio oldServicioOfSolicitudCollectionNewSolicitud = solicitudCollectionNewSolicitud.getServicio();
                    solicitudCollectionNewSolicitud.setServicio(servicio);
                    solicitudCollectionNewSolicitud = em.merge(solicitudCollectionNewSolicitud);
                    if (oldServicioOfSolicitudCollectionNewSolicitud != null && !oldServicioOfSolicitudCollectionNewSolicitud.equals(servicio)) {
                        oldServicioOfSolicitudCollectionNewSolicitud.getSolicitudCollection().remove(solicitudCollectionNewSolicitud);
                        oldServicioOfSolicitudCollectionNewSolicitud = em.merge(oldServicioOfSolicitudCollectionNewSolicitud);
                    }
                }
            }
            for (ProveedorServicio proveedorServicioCollectionNewProveedorServicio : proveedorServicioCollectionNew) {
                if (!proveedorServicioCollectionOld.contains(proveedorServicioCollectionNewProveedorServicio)) {
                    Servicio oldServicio1OfProveedorServicioCollectionNewProveedorServicio = proveedorServicioCollectionNewProveedorServicio.getServicio1();
                    proveedorServicioCollectionNewProveedorServicio.setServicio1(servicio);
                    proveedorServicioCollectionNewProveedorServicio = em.merge(proveedorServicioCollectionNewProveedorServicio);
                    if (oldServicio1OfProveedorServicioCollectionNewProveedorServicio != null && !oldServicio1OfProveedorServicioCollectionNewProveedorServicio.equals(servicio)) {
                        oldServicio1OfProveedorServicioCollectionNewProveedorServicio.getProveedorServicioCollection().remove(proveedorServicioCollectionNewProveedorServicio);
                        oldServicio1OfProveedorServicioCollectionNewProveedorServicio = em.merge(oldServicio1OfProveedorServicioCollectionNewProveedorServicio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = servicio.getId();
                if (findServicio(id) == null) {
                    throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicio servicio;
            try {
                servicio = em.getReference(Servicio.class, id);
                servicio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Solicitud> solicitudCollectionOrphanCheck = servicio.getSolicitudCollection();
            for (Solicitud solicitudCollectionOrphanCheckSolicitud : solicitudCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servicio (" + servicio + ") cannot be destroyed since the Solicitud " + solicitudCollectionOrphanCheckSolicitud + " in its solicitudCollection field has a non-nullable servicio field.");
            }
            Collection<ProveedorServicio> proveedorServicioCollectionOrphanCheck = servicio.getProveedorServicioCollection();
            for (ProveedorServicio proveedorServicioCollectionOrphanCheckProveedorServicio : proveedorServicioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servicio (" + servicio + ") cannot be destroyed since the ProveedorServicio " + proveedorServicioCollectionOrphanCheckProveedorServicio + " in its proveedorServicioCollection field has a non-nullable servicio1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(servicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Servicio> findServicioEntities() {
        return findServicioEntities(true, -1, -1);
    }

    public List<Servicio> findServicioEntities(int maxResults, int firstResult) {
        return findServicioEntities(false, maxResults, firstResult);
    }

    private List<Servicio> findServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servicio.class));
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

    public Servicio findServicio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servicio> rt = cq.from(Servicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
