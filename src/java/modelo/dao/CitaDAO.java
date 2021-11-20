/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.dao.exceptions.IllegalOrphanException;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.entidades.Cita;
import modelo.entidades.EstadoCita;
import modelo.entidades.Solicitud;
import modelo.entidades.Valoracion;

/**
 *
 * @author Aida
 */
public class CitaDAO implements Serializable {

    public CitaDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cita cita) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Valoracion valoracion = cita.getValoracion();
            if (valoracion != null) {
                valoracion = em.getReference(valoracion.getClass(), valoracion.getCita());
                cita.setValoracion(valoracion);
            }
            Solicitud solicitud = cita.getSolicitud();
            if (solicitud != null) {
                solicitud = em.getReference(solicitud.getClass(), solicitud.getId());
                cita.setSolicitud(solicitud);
            }
            EstadoCita estado = cita.getEstado();
            if (estado != null) {
                estado = em.getReference(estado.getClass(), estado.getNombre());
                cita.setEstado(estado);
            }
            em.persist(cita);
            if (valoracion != null) {
                Cita oldCita1OfValoracion = valoracion.getCita1();
                if (oldCita1OfValoracion != null) {
                    oldCita1OfValoracion.setValoracion(null);
                    oldCita1OfValoracion = em.merge(oldCita1OfValoracion);
                }
                valoracion.setCita1(cita);
                valoracion = em.merge(valoracion);
            }
            if (solicitud != null) {
                solicitud.getCitaCollection().add(cita);
                solicitud = em.merge(solicitud);
            }
            if (estado != null) {
                estado.getCitaCollection().add(cita);
                estado = em.merge(estado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cita cita) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cita persistentCita = em.find(Cita.class, cita.getId());
            Valoracion valoracionOld = persistentCita.getValoracion();
            Valoracion valoracionNew = cita.getValoracion();
            Solicitud solicitudOld = persistentCita.getSolicitud();
            Solicitud solicitudNew = cita.getSolicitud();
            EstadoCita estadoOld = persistentCita.getEstado();
            EstadoCita estadoNew = cita.getEstado();
            List<String> illegalOrphanMessages = null;
            if (valoracionOld != null && !valoracionOld.equals(valoracionNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Valoracion " + valoracionOld + " since its cita1 field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (valoracionNew != null) {
                valoracionNew = em.getReference(valoracionNew.getClass(), valoracionNew.getCita());
                cita.setValoracion(valoracionNew);
            }
            if (solicitudNew != null) {
                solicitudNew = em.getReference(solicitudNew.getClass(), solicitudNew.getId());
                cita.setSolicitud(solicitudNew);
            }
            if (estadoNew != null) {
                estadoNew = em.getReference(estadoNew.getClass(), estadoNew.getNombre());
                cita.setEstado(estadoNew);
            }
            cita = em.merge(cita);
            if (valoracionNew != null && !valoracionNew.equals(valoracionOld)) {
                Cita oldCita1OfValoracion = valoracionNew.getCita1();
                if (oldCita1OfValoracion != null) {
                    oldCita1OfValoracion.setValoracion(null);
                    oldCita1OfValoracion = em.merge(oldCita1OfValoracion);
                }
                valoracionNew.setCita1(cita);
                valoracionNew = em.merge(valoracionNew);
            }
            if (solicitudOld != null && !solicitudOld.equals(solicitudNew)) {
                solicitudOld.getCitaCollection().remove(cita);
                solicitudOld = em.merge(solicitudOld);
            }
            if (solicitudNew != null && !solicitudNew.equals(solicitudOld)) {
                solicitudNew.getCitaCollection().add(cita);
                solicitudNew = em.merge(solicitudNew);
            }
            if (estadoOld != null && !estadoOld.equals(estadoNew)) {
                estadoOld.getCitaCollection().remove(cita);
                estadoOld = em.merge(estadoOld);
            }
            if (estadoNew != null && !estadoNew.equals(estadoOld)) {
                estadoNew.getCitaCollection().add(cita);
                estadoNew = em.merge(estadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cita.getId();
                if (findCita(id) == null) {
                    throw new NonexistentEntityException("The cita with id " + id + " no longer exists.");
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
            Cita cita;
            try {
                cita = em.getReference(Cita.class, id);
                cita.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cita with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Valoracion valoracionOrphanCheck = cita.getValoracion();
            if (valoracionOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cita (" + cita + ") cannot be destroyed since the Valoracion " + valoracionOrphanCheck + " in its valoracion field has a non-nullable cita1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Solicitud solicitud = cita.getSolicitud();
            if (solicitud != null) {
                solicitud.getCitaCollection().remove(cita);
                solicitud = em.merge(solicitud);
            }
            EstadoCita estado = cita.getEstado();
            if (estado != null) {
                estado.getCitaCollection().remove(cita);
                estado = em.merge(estado);
            }
            em.remove(cita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cita> findCitaEntities() {
        return findCitaEntities(true, -1, -1);
    }

    public List<Cita> findCitaEntities(int maxResults, int firstResult) {
        return findCitaEntities(false, maxResults, firstResult);
    }

    private List<Cita> findCitaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cita.class));
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

    public Cita findCita(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cita.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cita> rt = cq.from(Cita.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
