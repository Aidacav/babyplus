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
import modelo.entidades.Usuario;
import modelo.entidades.ClienteSubscripcion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.dao.exceptions.IllegalOrphanException;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.dao.exceptions.PreexistingEntityException;
import modelo.entidades.Cliente;
import modelo.entidades.Paciente;
import modelo.entidades.Solicitud;
import modelo.entidades.Mensaje;

/**
 *
 * @author Aida
 */
public class ClienteDAO implements Serializable {

    public ClienteDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (cliente.getClienteSubscripcionCollection() == null) {
            cliente.setClienteSubscripcionCollection(new ArrayList<ClienteSubscripcion>());
        }
        if (cliente.getPacienteCollection() == null) {
            cliente.setPacienteCollection(new ArrayList<Paciente>());
        }
        if (cliente.getSolicitudCollection() == null) {
            cliente.setSolicitudCollection(new ArrayList<Solicitud>());
        }
        if (cliente.getMensajeCollection() == null) {
            cliente.setMensajeCollection(new ArrayList<Mensaje>());
        }
        List<String> illegalOrphanMessages = null;
        Usuario usuario1OrphanCheck = cliente.getUsuario1();
        if (usuario1OrphanCheck != null) {
            Cliente oldClienteOfUsuario1 = usuario1OrphanCheck.getCliente();
            if (oldClienteOfUsuario1 != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + usuario1OrphanCheck + " already has an item of type Cliente whose usuario1 column cannot be null. Please make another selection for the usuario1 field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario1 = cliente.getUsuario1();
            if (usuario1 != null) {
                usuario1 = em.getReference(usuario1.getClass(), usuario1.getId());
                cliente.setUsuario1(usuario1);
            }
            Collection<ClienteSubscripcion> attachedClienteSubscripcionCollection = new ArrayList<ClienteSubscripcion>();
            for (ClienteSubscripcion clienteSubscripcionCollectionClienteSubscripcionToAttach : cliente.getClienteSubscripcionCollection()) {
                clienteSubscripcionCollectionClienteSubscripcionToAttach = em.getReference(clienteSubscripcionCollectionClienteSubscripcionToAttach.getClass(), clienteSubscripcionCollectionClienteSubscripcionToAttach.getId());
                attachedClienteSubscripcionCollection.add(clienteSubscripcionCollectionClienteSubscripcionToAttach);
            }
            cliente.setClienteSubscripcionCollection(attachedClienteSubscripcionCollection);
            Collection<Paciente> attachedPacienteCollection = new ArrayList<Paciente>();
            for (Paciente pacienteCollectionPacienteToAttach : cliente.getPacienteCollection()) {
                pacienteCollectionPacienteToAttach = em.getReference(pacienteCollectionPacienteToAttach.getClass(), pacienteCollectionPacienteToAttach.getId());
                attachedPacienteCollection.add(pacienteCollectionPacienteToAttach);
            }
            cliente.setPacienteCollection(attachedPacienteCollection);
            Collection<Solicitud> attachedSolicitudCollection = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionSolicitudToAttach : cliente.getSolicitudCollection()) {
                solicitudCollectionSolicitudToAttach = em.getReference(solicitudCollectionSolicitudToAttach.getClass(), solicitudCollectionSolicitudToAttach.getId());
                attachedSolicitudCollection.add(solicitudCollectionSolicitudToAttach);
            }
            cliente.setSolicitudCollection(attachedSolicitudCollection);
            Collection<Mensaje> attachedMensajeCollection = new ArrayList<Mensaje>();
            for (Mensaje mensajeCollectionMensajeToAttach : cliente.getMensajeCollection()) {
                mensajeCollectionMensajeToAttach = em.getReference(mensajeCollectionMensajeToAttach.getClass(), mensajeCollectionMensajeToAttach.getId());
                attachedMensajeCollection.add(mensajeCollectionMensajeToAttach);
            }
            cliente.setMensajeCollection(attachedMensajeCollection);
            em.persist(cliente);
            if (usuario1 != null) {
                usuario1.setCliente(cliente);
                usuario1 = em.merge(usuario1);
            }
            for (ClienteSubscripcion clienteSubscripcionCollectionClienteSubscripcion : cliente.getClienteSubscripcionCollection()) {
                Cliente oldClienteOfClienteSubscripcionCollectionClienteSubscripcion = clienteSubscripcionCollectionClienteSubscripcion.getCliente();
                clienteSubscripcionCollectionClienteSubscripcion.setCliente(cliente);
                clienteSubscripcionCollectionClienteSubscripcion = em.merge(clienteSubscripcionCollectionClienteSubscripcion);
                if (oldClienteOfClienteSubscripcionCollectionClienteSubscripcion != null) {
                    oldClienteOfClienteSubscripcionCollectionClienteSubscripcion.getClienteSubscripcionCollection().remove(clienteSubscripcionCollectionClienteSubscripcion);
                    oldClienteOfClienteSubscripcionCollectionClienteSubscripcion = em.merge(oldClienteOfClienteSubscripcionCollectionClienteSubscripcion);
                }
            }
            for (Paciente pacienteCollectionPaciente : cliente.getPacienteCollection()) {
                Cliente oldClienteOfPacienteCollectionPaciente = pacienteCollectionPaciente.getCliente();
                pacienteCollectionPaciente.setCliente(cliente);
                pacienteCollectionPaciente = em.merge(pacienteCollectionPaciente);
                if (oldClienteOfPacienteCollectionPaciente != null) {
                    oldClienteOfPacienteCollectionPaciente.getPacienteCollection().remove(pacienteCollectionPaciente);
                    oldClienteOfPacienteCollectionPaciente = em.merge(oldClienteOfPacienteCollectionPaciente);
                }
            }
            for (Solicitud solicitudCollectionSolicitud : cliente.getSolicitudCollection()) {
                Cliente oldClienteOfSolicitudCollectionSolicitud = solicitudCollectionSolicitud.getCliente();
                solicitudCollectionSolicitud.setCliente(cliente);
                solicitudCollectionSolicitud = em.merge(solicitudCollectionSolicitud);
                if (oldClienteOfSolicitudCollectionSolicitud != null) {
                    oldClienteOfSolicitudCollectionSolicitud.getSolicitudCollection().remove(solicitudCollectionSolicitud);
                    oldClienteOfSolicitudCollectionSolicitud = em.merge(oldClienteOfSolicitudCollectionSolicitud);
                }
            }
            for (Mensaje mensajeCollectionMensaje : cliente.getMensajeCollection()) {
                Cliente oldClienteOfMensajeCollectionMensaje = mensajeCollectionMensaje.getCliente();
                mensajeCollectionMensaje.setCliente(cliente);
                mensajeCollectionMensaje = em.merge(mensajeCollectionMensaje);
                if (oldClienteOfMensajeCollectionMensaje != null) {
                    oldClienteOfMensajeCollectionMensaje.getMensajeCollection().remove(mensajeCollectionMensaje);
                    oldClienteOfMensajeCollectionMensaje = em.merge(oldClienteOfMensajeCollectionMensaje);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliente(cliente.getUsuario()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getUsuario());
            Usuario usuario1Old = persistentCliente.getUsuario1();
            Usuario usuario1New = cliente.getUsuario1();
            Collection<ClienteSubscripcion> clienteSubscripcionCollectionOld = persistentCliente.getClienteSubscripcionCollection();
            Collection<ClienteSubscripcion> clienteSubscripcionCollectionNew = cliente.getClienteSubscripcionCollection();
            Collection<Paciente> pacienteCollectionOld = persistentCliente.getPacienteCollection();
            Collection<Paciente> pacienteCollectionNew = cliente.getPacienteCollection();
            Collection<Solicitud> solicitudCollectionOld = persistentCliente.getSolicitudCollection();
            Collection<Solicitud> solicitudCollectionNew = cliente.getSolicitudCollection();
            Collection<Mensaje> mensajeCollectionOld = persistentCliente.getMensajeCollection();
            Collection<Mensaje> mensajeCollectionNew = cliente.getMensajeCollection();
            List<String> illegalOrphanMessages = null;
            if (usuario1New != null && !usuario1New.equals(usuario1Old)) {
                Cliente oldClienteOfUsuario1 = usuario1New.getCliente();
                if (oldClienteOfUsuario1 != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + usuario1New + " already has an item of type Cliente whose usuario1 column cannot be null. Please make another selection for the usuario1 field.");
                }
            }
            for (ClienteSubscripcion clienteSubscripcionCollectionOldClienteSubscripcion : clienteSubscripcionCollectionOld) {
                if (!clienteSubscripcionCollectionNew.contains(clienteSubscripcionCollectionOldClienteSubscripcion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClienteSubscripcion " + clienteSubscripcionCollectionOldClienteSubscripcion + " since its cliente field is not nullable.");
                }
            }
            for (Paciente pacienteCollectionOldPaciente : pacienteCollectionOld) {
                if (!pacienteCollectionNew.contains(pacienteCollectionOldPaciente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Paciente " + pacienteCollectionOldPaciente + " since its cliente field is not nullable.");
                }
            }
            for (Solicitud solicitudCollectionOldSolicitud : solicitudCollectionOld) {
                if (!solicitudCollectionNew.contains(solicitudCollectionOldSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitud " + solicitudCollectionOldSolicitud + " since its cliente field is not nullable.");
                }
            }
            for (Mensaje mensajeCollectionOldMensaje : mensajeCollectionOld) {
                if (!mensajeCollectionNew.contains(mensajeCollectionOldMensaje)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mensaje " + mensajeCollectionOldMensaje + " since its cliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuario1New != null) {
                usuario1New = em.getReference(usuario1New.getClass(), usuario1New.getId());
                cliente.setUsuario1(usuario1New);
            }
            Collection<ClienteSubscripcion> attachedClienteSubscripcionCollectionNew = new ArrayList<ClienteSubscripcion>();
            for (ClienteSubscripcion clienteSubscripcionCollectionNewClienteSubscripcionToAttach : clienteSubscripcionCollectionNew) {
                clienteSubscripcionCollectionNewClienteSubscripcionToAttach = em.getReference(clienteSubscripcionCollectionNewClienteSubscripcionToAttach.getClass(), clienteSubscripcionCollectionNewClienteSubscripcionToAttach.getId());
                attachedClienteSubscripcionCollectionNew.add(clienteSubscripcionCollectionNewClienteSubscripcionToAttach);
            }
            clienteSubscripcionCollectionNew = attachedClienteSubscripcionCollectionNew;
            cliente.setClienteSubscripcionCollection(clienteSubscripcionCollectionNew);
            Collection<Paciente> attachedPacienteCollectionNew = new ArrayList<Paciente>();
            for (Paciente pacienteCollectionNewPacienteToAttach : pacienteCollectionNew) {
                pacienteCollectionNewPacienteToAttach = em.getReference(pacienteCollectionNewPacienteToAttach.getClass(), pacienteCollectionNewPacienteToAttach.getId());
                attachedPacienteCollectionNew.add(pacienteCollectionNewPacienteToAttach);
            }
            pacienteCollectionNew = attachedPacienteCollectionNew;
            cliente.setPacienteCollection(pacienteCollectionNew);
            Collection<Solicitud> attachedSolicitudCollectionNew = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionNewSolicitudToAttach : solicitudCollectionNew) {
                solicitudCollectionNewSolicitudToAttach = em.getReference(solicitudCollectionNewSolicitudToAttach.getClass(), solicitudCollectionNewSolicitudToAttach.getId());
                attachedSolicitudCollectionNew.add(solicitudCollectionNewSolicitudToAttach);
            }
            solicitudCollectionNew = attachedSolicitudCollectionNew;
            cliente.setSolicitudCollection(solicitudCollectionNew);
            Collection<Mensaje> attachedMensajeCollectionNew = new ArrayList<Mensaje>();
            for (Mensaje mensajeCollectionNewMensajeToAttach : mensajeCollectionNew) {
                mensajeCollectionNewMensajeToAttach = em.getReference(mensajeCollectionNewMensajeToAttach.getClass(), mensajeCollectionNewMensajeToAttach.getId());
                attachedMensajeCollectionNew.add(mensajeCollectionNewMensajeToAttach);
            }
            mensajeCollectionNew = attachedMensajeCollectionNew;
            cliente.setMensajeCollection(mensajeCollectionNew);
            cliente = em.merge(cliente);
            if (usuario1Old != null && !usuario1Old.equals(usuario1New)) {
                usuario1Old.setCliente(null);
                usuario1Old = em.merge(usuario1Old);
            }
            if (usuario1New != null && !usuario1New.equals(usuario1Old)) {
                usuario1New.setCliente(cliente);
                usuario1New = em.merge(usuario1New);
            }
            for (ClienteSubscripcion clienteSubscripcionCollectionNewClienteSubscripcion : clienteSubscripcionCollectionNew) {
                if (!clienteSubscripcionCollectionOld.contains(clienteSubscripcionCollectionNewClienteSubscripcion)) {
                    Cliente oldClienteOfClienteSubscripcionCollectionNewClienteSubscripcion = clienteSubscripcionCollectionNewClienteSubscripcion.getCliente();
                    clienteSubscripcionCollectionNewClienteSubscripcion.setCliente(cliente);
                    clienteSubscripcionCollectionNewClienteSubscripcion = em.merge(clienteSubscripcionCollectionNewClienteSubscripcion);
                    if (oldClienteOfClienteSubscripcionCollectionNewClienteSubscripcion != null && !oldClienteOfClienteSubscripcionCollectionNewClienteSubscripcion.equals(cliente)) {
                        oldClienteOfClienteSubscripcionCollectionNewClienteSubscripcion.getClienteSubscripcionCollection().remove(clienteSubscripcionCollectionNewClienteSubscripcion);
                        oldClienteOfClienteSubscripcionCollectionNewClienteSubscripcion = em.merge(oldClienteOfClienteSubscripcionCollectionNewClienteSubscripcion);
                    }
                }
            }
            for (Paciente pacienteCollectionNewPaciente : pacienteCollectionNew) {
                if (!pacienteCollectionOld.contains(pacienteCollectionNewPaciente)) {
                    Cliente oldClienteOfPacienteCollectionNewPaciente = pacienteCollectionNewPaciente.getCliente();
                    pacienteCollectionNewPaciente.setCliente(cliente);
                    pacienteCollectionNewPaciente = em.merge(pacienteCollectionNewPaciente);
                    if (oldClienteOfPacienteCollectionNewPaciente != null && !oldClienteOfPacienteCollectionNewPaciente.equals(cliente)) {
                        oldClienteOfPacienteCollectionNewPaciente.getPacienteCollection().remove(pacienteCollectionNewPaciente);
                        oldClienteOfPacienteCollectionNewPaciente = em.merge(oldClienteOfPacienteCollectionNewPaciente);
                    }
                }
            }
            for (Solicitud solicitudCollectionNewSolicitud : solicitudCollectionNew) {
                if (!solicitudCollectionOld.contains(solicitudCollectionNewSolicitud)) {
                    Cliente oldClienteOfSolicitudCollectionNewSolicitud = solicitudCollectionNewSolicitud.getCliente();
                    solicitudCollectionNewSolicitud.setCliente(cliente);
                    solicitudCollectionNewSolicitud = em.merge(solicitudCollectionNewSolicitud);
                    if (oldClienteOfSolicitudCollectionNewSolicitud != null && !oldClienteOfSolicitudCollectionNewSolicitud.equals(cliente)) {
                        oldClienteOfSolicitudCollectionNewSolicitud.getSolicitudCollection().remove(solicitudCollectionNewSolicitud);
                        oldClienteOfSolicitudCollectionNewSolicitud = em.merge(oldClienteOfSolicitudCollectionNewSolicitud);
                    }
                }
            }
            for (Mensaje mensajeCollectionNewMensaje : mensajeCollectionNew) {
                if (!mensajeCollectionOld.contains(mensajeCollectionNewMensaje)) {
                    Cliente oldClienteOfMensajeCollectionNewMensaje = mensajeCollectionNewMensaje.getCliente();
                    mensajeCollectionNewMensaje.setCliente(cliente);
                    mensajeCollectionNewMensaje = em.merge(mensajeCollectionNewMensaje);
                    if (oldClienteOfMensajeCollectionNewMensaje != null && !oldClienteOfMensajeCollectionNewMensaje.equals(cliente)) {
                        oldClienteOfMensajeCollectionNewMensaje.getMensajeCollection().remove(mensajeCollectionNewMensaje);
                        oldClienteOfMensajeCollectionNewMensaje = em.merge(oldClienteOfMensajeCollectionNewMensaje);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getUsuario();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ClienteSubscripcion> clienteSubscripcionCollectionOrphanCheck = cliente.getClienteSubscripcionCollection();
            for (ClienteSubscripcion clienteSubscripcionCollectionOrphanCheckClienteSubscripcion : clienteSubscripcionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the ClienteSubscripcion " + clienteSubscripcionCollectionOrphanCheckClienteSubscripcion + " in its clienteSubscripcionCollection field has a non-nullable cliente field.");
            }
            Collection<Paciente> pacienteCollectionOrphanCheck = cliente.getPacienteCollection();
            for (Paciente pacienteCollectionOrphanCheckPaciente : pacienteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Paciente " + pacienteCollectionOrphanCheckPaciente + " in its pacienteCollection field has a non-nullable cliente field.");
            }
            Collection<Solicitud> solicitudCollectionOrphanCheck = cliente.getSolicitudCollection();
            for (Solicitud solicitudCollectionOrphanCheckSolicitud : solicitudCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Solicitud " + solicitudCollectionOrphanCheckSolicitud + " in its solicitudCollection field has a non-nullable cliente field.");
            }
            Collection<Mensaje> mensajeCollectionOrphanCheck = cliente.getMensajeCollection();
            for (Mensaje mensajeCollectionOrphanCheckMensaje : mensajeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Mensaje " + mensajeCollectionOrphanCheckMensaje + " in its mensajeCollection field has a non-nullable cliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario1 = cliente.getUsuario1();
            if (usuario1 != null) {
                usuario1.setCliente(null);
                usuario1 = em.merge(usuario1);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
