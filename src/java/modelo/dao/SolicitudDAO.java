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
import modelo.entidades.Paciente;
import modelo.entidades.Proveedor;
import modelo.entidades.Servicio;
import modelo.entidades.EstadoSolicitud;
import modelo.entidades.Cita;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.dao.exceptions.IllegalOrphanException;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.entidades.Solicitud;

/**
 *
 * @author Aida
 */
public class SolicitudDAO implements Serializable {

    public SolicitudDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Solicitud solicitud) {
        if (solicitud.getCitaCollection() == null) {
            solicitud.setCitaCollection(new ArrayList<Cita>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = solicitud.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getUsuario());
                solicitud.setCliente(cliente);
            }
            Paciente paciente = solicitud.getPaciente();
            if (paciente != null) {
                paciente = em.getReference(paciente.getClass(), paciente.getId());
                solicitud.setPaciente(paciente);
            }
            Proveedor proveedor = solicitud.getProveedor();
            if (proveedor != null) {
                proveedor = em.getReference(proveedor.getClass(), proveedor.getUsuario());
                solicitud.setProveedor(proveedor);
            }
            Servicio servicio = solicitud.getServicio();
            if (servicio != null) {
                servicio = em.getReference(servicio.getClass(), servicio.getId());
                solicitud.setServicio(servicio);
            }
            EstadoSolicitud estado = solicitud.getEstado();
            if (estado != null) {
                estado = em.getReference(estado.getClass(), estado.getNombre());
                solicitud.setEstado(estado);
            }
            Collection<Cita> attachedCitaCollection = new ArrayList<Cita>();
            for (Cita citaCollectionCitaToAttach : solicitud.getCitaCollection()) {
                citaCollectionCitaToAttach = em.getReference(citaCollectionCitaToAttach.getClass(), citaCollectionCitaToAttach.getId());
                attachedCitaCollection.add(citaCollectionCitaToAttach);
            }
            solicitud.setCitaCollection(attachedCitaCollection);
            em.persist(solicitud);
            if (cliente != null) {
                cliente.getSolicitudCollection().add(solicitud);
                cliente = em.merge(cliente);
            }
            if (paciente != null) {
                paciente.getSolicitudCollection().add(solicitud);
                paciente = em.merge(paciente);
            }
            if (proveedor != null) {
                proveedor.getSolicitudCollection().add(solicitud);
                proveedor = em.merge(proveedor);
            }
            if (servicio != null) {
                servicio.getSolicitudCollection().add(solicitud);
                servicio = em.merge(servicio);
            }
            if (estado != null) {
                estado.getSolicitudCollection().add(solicitud);
                estado = em.merge(estado);
            }
            for (Cita citaCollectionCita : solicitud.getCitaCollection()) {
                Solicitud oldSolicitudOfCitaCollectionCita = citaCollectionCita.getSolicitud();
                citaCollectionCita.setSolicitud(solicitud);
                citaCollectionCita = em.merge(citaCollectionCita);
                if (oldSolicitudOfCitaCollectionCita != null) {
                    oldSolicitudOfCitaCollectionCita.getCitaCollection().remove(citaCollectionCita);
                    oldSolicitudOfCitaCollectionCita = em.merge(oldSolicitudOfCitaCollectionCita);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Solicitud solicitud) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitud persistentSolicitud = em.find(Solicitud.class, solicitud.getId());
            Cliente clienteOld = persistentSolicitud.getCliente();
            Cliente clienteNew = solicitud.getCliente();
            Paciente pacienteOld = persistentSolicitud.getPaciente();
            Paciente pacienteNew = solicitud.getPaciente();
            Proveedor proveedorOld = persistentSolicitud.getProveedor();
            Proveedor proveedorNew = solicitud.getProveedor();
            Servicio servicioOld = persistentSolicitud.getServicio();
            Servicio servicioNew = solicitud.getServicio();
            EstadoSolicitud estadoOld = persistentSolicitud.getEstado();
            EstadoSolicitud estadoNew = solicitud.getEstado();
            Collection<Cita> citaCollectionOld = persistentSolicitud.getCitaCollection();
            Collection<Cita> citaCollectionNew = solicitud.getCitaCollection();
            List<String> illegalOrphanMessages = null;
            for (Cita citaCollectionOldCita : citaCollectionOld) {
                if (!citaCollectionNew.contains(citaCollectionOldCita)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cita " + citaCollectionOldCita + " since its solicitud field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getUsuario());
                solicitud.setCliente(clienteNew);
            }
            if (pacienteNew != null) {
                pacienteNew = em.getReference(pacienteNew.getClass(), pacienteNew.getId());
                solicitud.setPaciente(pacienteNew);
            }
            if (proveedorNew != null) {
                proveedorNew = em.getReference(proveedorNew.getClass(), proveedorNew.getUsuario());
                solicitud.setProveedor(proveedorNew);
            }
            if (servicioNew != null) {
                servicioNew = em.getReference(servicioNew.getClass(), servicioNew.getId());
                solicitud.setServicio(servicioNew);
            }
            if (estadoNew != null) {
                estadoNew = em.getReference(estadoNew.getClass(), estadoNew.getNombre());
                solicitud.setEstado(estadoNew);
            }
            Collection<Cita> attachedCitaCollectionNew = new ArrayList<Cita>();
            for (Cita citaCollectionNewCitaToAttach : citaCollectionNew) {
                citaCollectionNewCitaToAttach = em.getReference(citaCollectionNewCitaToAttach.getClass(), citaCollectionNewCitaToAttach.getId());
                attachedCitaCollectionNew.add(citaCollectionNewCitaToAttach);
            }
            citaCollectionNew = attachedCitaCollectionNew;
            solicitud.setCitaCollection(citaCollectionNew);
            solicitud = em.merge(solicitud);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getSolicitudCollection().remove(solicitud);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getSolicitudCollection().add(solicitud);
                clienteNew = em.merge(clienteNew);
            }
            if (pacienteOld != null && !pacienteOld.equals(pacienteNew)) {
                pacienteOld.getSolicitudCollection().remove(solicitud);
                pacienteOld = em.merge(pacienteOld);
            }
            if (pacienteNew != null && !pacienteNew.equals(pacienteOld)) {
                pacienteNew.getSolicitudCollection().add(solicitud);
                pacienteNew = em.merge(pacienteNew);
            }
            if (proveedorOld != null && !proveedorOld.equals(proveedorNew)) {
                proveedorOld.getSolicitudCollection().remove(solicitud);
                proveedorOld = em.merge(proveedorOld);
            }
            if (proveedorNew != null && !proveedorNew.equals(proveedorOld)) {
                proveedorNew.getSolicitudCollection().add(solicitud);
                proveedorNew = em.merge(proveedorNew);
            }
            if (servicioOld != null && !servicioOld.equals(servicioNew)) {
                servicioOld.getSolicitudCollection().remove(solicitud);
                servicioOld = em.merge(servicioOld);
            }
            if (servicioNew != null && !servicioNew.equals(servicioOld)) {
                servicioNew.getSolicitudCollection().add(solicitud);
                servicioNew = em.merge(servicioNew);
            }
            if (estadoOld != null && !estadoOld.equals(estadoNew)) {
                estadoOld.getSolicitudCollection().remove(solicitud);
                estadoOld = em.merge(estadoOld);
            }
            if (estadoNew != null && !estadoNew.equals(estadoOld)) {
                estadoNew.getSolicitudCollection().add(solicitud);
                estadoNew = em.merge(estadoNew);
            }
            for (Cita citaCollectionNewCita : citaCollectionNew) {
                if (!citaCollectionOld.contains(citaCollectionNewCita)) {
                    Solicitud oldSolicitudOfCitaCollectionNewCita = citaCollectionNewCita.getSolicitud();
                    citaCollectionNewCita.setSolicitud(solicitud);
                    citaCollectionNewCita = em.merge(citaCollectionNewCita);
                    if (oldSolicitudOfCitaCollectionNewCita != null && !oldSolicitudOfCitaCollectionNewCita.equals(solicitud)) {
                        oldSolicitudOfCitaCollectionNewCita.getCitaCollection().remove(citaCollectionNewCita);
                        oldSolicitudOfCitaCollectionNewCita = em.merge(oldSolicitudOfCitaCollectionNewCita);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = solicitud.getId();
                if (findSolicitud(id) == null) {
                    throw new NonexistentEntityException("The solicitud with id " + id + " no longer exists.");
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
            Solicitud solicitud;
            try {
                solicitud = em.getReference(Solicitud.class, id);
                solicitud.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitud with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cita> citaCollectionOrphanCheck = solicitud.getCitaCollection();
            for (Cita citaCollectionOrphanCheckCita : citaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Solicitud (" + solicitud + ") cannot be destroyed since the Cita " + citaCollectionOrphanCheckCita + " in its citaCollection field has a non-nullable solicitud field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente cliente = solicitud.getCliente();
            if (cliente != null) {
                cliente.getSolicitudCollection().remove(solicitud);
                cliente = em.merge(cliente);
            }
            Paciente paciente = solicitud.getPaciente();
            if (paciente != null) {
                paciente.getSolicitudCollection().remove(solicitud);
                paciente = em.merge(paciente);
            }
            Proveedor proveedor = solicitud.getProveedor();
            if (proveedor != null) {
                proveedor.getSolicitudCollection().remove(solicitud);
                proveedor = em.merge(proveedor);
            }
            Servicio servicio = solicitud.getServicio();
            if (servicio != null) {
                servicio.getSolicitudCollection().remove(solicitud);
                servicio = em.merge(servicio);
            }
            EstadoSolicitud estado = solicitud.getEstado();
            if (estado != null) {
                estado.getSolicitudCollection().remove(solicitud);
                estado = em.merge(estado);
            }
            em.remove(solicitud);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Solicitud> findSolicitudEntities() {
        return findSolicitudEntities(true, -1, -1);
    }

    public List<Solicitud> findSolicitudEntities(int maxResults, int firstResult) {
        return findSolicitudEntities(false, maxResults, firstResult);
    }

    private List<Solicitud> findSolicitudEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Solicitud.class));
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

    public Solicitud findSolicitud(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Solicitud.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitudCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solicitud> rt = cq.from(Solicitud.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
