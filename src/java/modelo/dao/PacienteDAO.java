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
import modelo.entidades.Cliente;
import modelo.entidades.Solicitud;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.dao.exceptions.IllegalOrphanException;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.entidades.Paciente;

/**
 *
 * @author Aida
 */
public class PacienteDAO implements Serializable {

    public PacienteDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Paciente paciente) {
        if (paciente.getSolicitudCollection() == null) {
            paciente.setSolicitudCollection(new ArrayList<Solicitud>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = paciente.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getUsuario());
                paciente.setCliente(cliente);
            }
            Collection<Solicitud> attachedSolicitudCollection = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionSolicitudToAttach : paciente.getSolicitudCollection()) {
                solicitudCollectionSolicitudToAttach = em.getReference(solicitudCollectionSolicitudToAttach.getClass(), solicitudCollectionSolicitudToAttach.getId());
                attachedSolicitudCollection.add(solicitudCollectionSolicitudToAttach);
            }
            paciente.setSolicitudCollection(attachedSolicitudCollection);
            em.persist(paciente);
            if (cliente != null) {
                cliente.getPacienteCollection().add(paciente);
                cliente = em.merge(cliente);
            }
            for (Solicitud solicitudCollectionSolicitud : paciente.getSolicitudCollection()) {
                Paciente oldPacienteOfSolicitudCollectionSolicitud = solicitudCollectionSolicitud.getPaciente();
                solicitudCollectionSolicitud.setPaciente(paciente);
                solicitudCollectionSolicitud = em.merge(solicitudCollectionSolicitud);
                if (oldPacienteOfSolicitudCollectionSolicitud != null) {
                    oldPacienteOfSolicitudCollectionSolicitud.getSolicitudCollection().remove(solicitudCollectionSolicitud);
                    oldPacienteOfSolicitudCollectionSolicitud = em.merge(oldPacienteOfSolicitudCollectionSolicitud);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paciente paciente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paciente persistentPaciente = em.find(Paciente.class, paciente.getId());
            Cliente clienteOld = persistentPaciente.getCliente();
            Cliente clienteNew = paciente.getCliente();
            Collection<Solicitud> solicitudCollectionOld = persistentPaciente.getSolicitudCollection();
            Collection<Solicitud> solicitudCollectionNew = paciente.getSolicitudCollection();
            List<String> illegalOrphanMessages = null;
            for (Solicitud solicitudCollectionOldSolicitud : solicitudCollectionOld) {
                if (!solicitudCollectionNew.contains(solicitudCollectionOldSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitud " + solicitudCollectionOldSolicitud + " since its paciente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getUsuario());
                paciente.setCliente(clienteNew);
            }
            Collection<Solicitud> attachedSolicitudCollectionNew = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionNewSolicitudToAttach : solicitudCollectionNew) {
                solicitudCollectionNewSolicitudToAttach = em.getReference(solicitudCollectionNewSolicitudToAttach.getClass(), solicitudCollectionNewSolicitudToAttach.getId());
                attachedSolicitudCollectionNew.add(solicitudCollectionNewSolicitudToAttach);
            }
            solicitudCollectionNew = attachedSolicitudCollectionNew;
            paciente.setSolicitudCollection(solicitudCollectionNew);
            paciente = em.merge(paciente);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getPacienteCollection().remove(paciente);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getPacienteCollection().add(paciente);
                clienteNew = em.merge(clienteNew);
            }
            for (Solicitud solicitudCollectionNewSolicitud : solicitudCollectionNew) {
                if (!solicitudCollectionOld.contains(solicitudCollectionNewSolicitud)) {
                    Paciente oldPacienteOfSolicitudCollectionNewSolicitud = solicitudCollectionNewSolicitud.getPaciente();
                    solicitudCollectionNewSolicitud.setPaciente(paciente);
                    solicitudCollectionNewSolicitud = em.merge(solicitudCollectionNewSolicitud);
                    if (oldPacienteOfSolicitudCollectionNewSolicitud != null && !oldPacienteOfSolicitudCollectionNewSolicitud.equals(paciente)) {
                        oldPacienteOfSolicitudCollectionNewSolicitud.getSolicitudCollection().remove(solicitudCollectionNewSolicitud);
                        oldPacienteOfSolicitudCollectionNewSolicitud = em.merge(oldPacienteOfSolicitudCollectionNewSolicitud);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = paciente.getId();
                if (findPaciente(id) == null) {
                    throw new NonexistentEntityException("The paciente with id " + id + " no longer exists.");
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
            Paciente paciente;
            try {
                paciente = em.getReference(Paciente.class, id);
                paciente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paciente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Solicitud> solicitudCollectionOrphanCheck = paciente.getSolicitudCollection();
            for (Solicitud solicitudCollectionOrphanCheckSolicitud : solicitudCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paciente (" + paciente + ") cannot be destroyed since the Solicitud " + solicitudCollectionOrphanCheckSolicitud + " in its solicitudCollection field has a non-nullable paciente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente cliente = paciente.getCliente();
            if (cliente != null) {
                cliente.getPacienteCollection().remove(paciente);
                cliente = em.merge(cliente);
            }
            em.remove(paciente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paciente> findPacienteEntities() {
        return findPacienteEntities(true, -1, -1);
    }

    public List<Paciente> findPacienteEntities(int maxResults, int firstResult) {
        return findPacienteEntities(false, maxResults, firstResult);
    }

    private List<Paciente> findPacienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paciente.class));
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

    public Paciente findPaciente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paciente.class, id);
        } finally {
            em.close();
        }
    }

    public int getPacienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paciente> rt = cq.from(Paciente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
