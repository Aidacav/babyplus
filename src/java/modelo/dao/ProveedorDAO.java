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
import modelo.entidades.Solicitud;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.dao.exceptions.IllegalOrphanException;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.dao.exceptions.PreexistingEntityException;
import modelo.entidades.Mensaje;
import modelo.entidades.Proveedor;
import modelo.entidades.ProveedorServicio;

/**
 *
 * @author Aida
 */
public class ProveedorDAO implements Serializable {

    public ProveedorDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (proveedor.getSolicitudCollection() == null) {
            proveedor.setSolicitudCollection(new ArrayList<Solicitud>());
        }
        if (proveedor.getMensajeCollection() == null) {
            proveedor.setMensajeCollection(new ArrayList<Mensaje>());
        }
        if (proveedor.getProveedorServicioCollection() == null) {
            proveedor.setProveedorServicioCollection(new ArrayList<ProveedorServicio>());
        }
        List<String> illegalOrphanMessages = null;
        Usuario usuario1OrphanCheck = proveedor.getUsuario1();
        if (usuario1OrphanCheck != null) {
            Proveedor oldProveedorOfUsuario1 = usuario1OrphanCheck.getProveedor();
            if (oldProveedorOfUsuario1 != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + usuario1OrphanCheck + " already has an item of type Proveedor whose usuario1 column cannot be null. Please make another selection for the usuario1 field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario1 = proveedor.getUsuario1();
            if (usuario1 != null) {
                usuario1 = em.getReference(usuario1.getClass(), usuario1.getId());
                proveedor.setUsuario1(usuario1);
            }
            Collection<Solicitud> attachedSolicitudCollection = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionSolicitudToAttach : proveedor.getSolicitudCollection()) {
                solicitudCollectionSolicitudToAttach = em.getReference(solicitudCollectionSolicitudToAttach.getClass(), solicitudCollectionSolicitudToAttach.getId());
                attachedSolicitudCollection.add(solicitudCollectionSolicitudToAttach);
            }
            proveedor.setSolicitudCollection(attachedSolicitudCollection);
            Collection<Mensaje> attachedMensajeCollection = new ArrayList<Mensaje>();
            for (Mensaje mensajeCollectionMensajeToAttach : proveedor.getMensajeCollection()) {
                mensajeCollectionMensajeToAttach = em.getReference(mensajeCollectionMensajeToAttach.getClass(), mensajeCollectionMensajeToAttach.getId());
                attachedMensajeCollection.add(mensajeCollectionMensajeToAttach);
            }
            proveedor.setMensajeCollection(attachedMensajeCollection);
            Collection<ProveedorServicio> attachedProveedorServicioCollection = new ArrayList<ProveedorServicio>();
            for (ProveedorServicio proveedorServicioCollectionProveedorServicioToAttach : proveedor.getProveedorServicioCollection()) {
                proveedorServicioCollectionProveedorServicioToAttach = em.getReference(proveedorServicioCollectionProveedorServicioToAttach.getClass(), proveedorServicioCollectionProveedorServicioToAttach.getProveedorServicioPK());
                attachedProveedorServicioCollection.add(proveedorServicioCollectionProveedorServicioToAttach);
            }
            proveedor.setProveedorServicioCollection(attachedProveedorServicioCollection);
            em.persist(proveedor);
            if (usuario1 != null) {
                usuario1.setProveedor(proveedor);
                usuario1 = em.merge(usuario1);
            }
            for (Solicitud solicitudCollectionSolicitud : proveedor.getSolicitudCollection()) {
                Proveedor oldProveedorOfSolicitudCollectionSolicitud = solicitudCollectionSolicitud.getProveedor();
                solicitudCollectionSolicitud.setProveedor(proveedor);
                solicitudCollectionSolicitud = em.merge(solicitudCollectionSolicitud);
                if (oldProveedorOfSolicitudCollectionSolicitud != null) {
                    oldProveedorOfSolicitudCollectionSolicitud.getSolicitudCollection().remove(solicitudCollectionSolicitud);
                    oldProveedorOfSolicitudCollectionSolicitud = em.merge(oldProveedorOfSolicitudCollectionSolicitud);
                }
            }
            for (Mensaje mensajeCollectionMensaje : proveedor.getMensajeCollection()) {
                Proveedor oldProveedorOfMensajeCollectionMensaje = mensajeCollectionMensaje.getProveedor();
                mensajeCollectionMensaje.setProveedor(proveedor);
                mensajeCollectionMensaje = em.merge(mensajeCollectionMensaje);
                if (oldProveedorOfMensajeCollectionMensaje != null) {
                    oldProveedorOfMensajeCollectionMensaje.getMensajeCollection().remove(mensajeCollectionMensaje);
                    oldProveedorOfMensajeCollectionMensaje = em.merge(oldProveedorOfMensajeCollectionMensaje);
                }
            }
            for (ProveedorServicio proveedorServicioCollectionProveedorServicio : proveedor.getProveedorServicioCollection()) {
                Proveedor oldProveedor1OfProveedorServicioCollectionProveedorServicio = proveedorServicioCollectionProveedorServicio.getProveedor1();
                proveedorServicioCollectionProveedorServicio.setProveedor1(proveedor);
                proveedorServicioCollectionProveedorServicio = em.merge(proveedorServicioCollectionProveedorServicio);
                if (oldProveedor1OfProveedorServicioCollectionProveedorServicio != null) {
                    oldProveedor1OfProveedorServicioCollectionProveedorServicio.getProveedorServicioCollection().remove(proveedorServicioCollectionProveedorServicio);
                    oldProveedor1OfProveedorServicioCollectionProveedorServicio = em.merge(oldProveedor1OfProveedorServicioCollectionProveedorServicio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProveedor(proveedor.getUsuario()) != null) {
                throw new PreexistingEntityException("Proveedor " + proveedor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getUsuario());
            Usuario usuario1Old = persistentProveedor.getUsuario1();
            Usuario usuario1New = proveedor.getUsuario1();
            Collection<Solicitud> solicitudCollectionOld = persistentProveedor.getSolicitudCollection();
            Collection<Solicitud> solicitudCollectionNew = proveedor.getSolicitudCollection();
            Collection<Mensaje> mensajeCollectionOld = persistentProveedor.getMensajeCollection();
            Collection<Mensaje> mensajeCollectionNew = proveedor.getMensajeCollection();
            Collection<ProveedorServicio> proveedorServicioCollectionOld = persistentProveedor.getProveedorServicioCollection();
            Collection<ProveedorServicio> proveedorServicioCollectionNew = proveedor.getProveedorServicioCollection();
            List<String> illegalOrphanMessages = null;
            if (usuario1New != null && !usuario1New.equals(usuario1Old)) {
                Proveedor oldProveedorOfUsuario1 = usuario1New.getProveedor();
                if (oldProveedorOfUsuario1 != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + usuario1New + " already has an item of type Proveedor whose usuario1 column cannot be null. Please make another selection for the usuario1 field.");
                }
            }
            for (Solicitud solicitudCollectionOldSolicitud : solicitudCollectionOld) {
                if (!solicitudCollectionNew.contains(solicitudCollectionOldSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitud " + solicitudCollectionOldSolicitud + " since its proveedor field is not nullable.");
                }
            }
            for (Mensaje mensajeCollectionOldMensaje : mensajeCollectionOld) {
                if (!mensajeCollectionNew.contains(mensajeCollectionOldMensaje)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mensaje " + mensajeCollectionOldMensaje + " since its proveedor field is not nullable.");
                }
            }
            for (ProveedorServicio proveedorServicioCollectionOldProveedorServicio : proveedorServicioCollectionOld) {
                if (!proveedorServicioCollectionNew.contains(proveedorServicioCollectionOldProveedorServicio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProveedorServicio " + proveedorServicioCollectionOldProveedorServicio + " since its proveedor1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuario1New != null) {
                usuario1New = em.getReference(usuario1New.getClass(), usuario1New.getId());
                proveedor.setUsuario1(usuario1New);
            }
            Collection<Solicitud> attachedSolicitudCollectionNew = new ArrayList<Solicitud>();
            for (Solicitud solicitudCollectionNewSolicitudToAttach : solicitudCollectionNew) {
                solicitudCollectionNewSolicitudToAttach = em.getReference(solicitudCollectionNewSolicitudToAttach.getClass(), solicitudCollectionNewSolicitudToAttach.getId());
                attachedSolicitudCollectionNew.add(solicitudCollectionNewSolicitudToAttach);
            }
            solicitudCollectionNew = attachedSolicitudCollectionNew;
            proveedor.setSolicitudCollection(solicitudCollectionNew);
            Collection<Mensaje> attachedMensajeCollectionNew = new ArrayList<Mensaje>();
            for (Mensaje mensajeCollectionNewMensajeToAttach : mensajeCollectionNew) {
                mensajeCollectionNewMensajeToAttach = em.getReference(mensajeCollectionNewMensajeToAttach.getClass(), mensajeCollectionNewMensajeToAttach.getId());
                attachedMensajeCollectionNew.add(mensajeCollectionNewMensajeToAttach);
            }
            mensajeCollectionNew = attachedMensajeCollectionNew;
            proveedor.setMensajeCollection(mensajeCollectionNew);
            Collection<ProveedorServicio> attachedProveedorServicioCollectionNew = new ArrayList<ProveedorServicio>();
            for (ProveedorServicio proveedorServicioCollectionNewProveedorServicioToAttach : proveedorServicioCollectionNew) {
                proveedorServicioCollectionNewProveedorServicioToAttach = em.getReference(proveedorServicioCollectionNewProveedorServicioToAttach.getClass(), proveedorServicioCollectionNewProveedorServicioToAttach.getProveedorServicioPK());
                attachedProveedorServicioCollectionNew.add(proveedorServicioCollectionNewProveedorServicioToAttach);
            }
            proveedorServicioCollectionNew = attachedProveedorServicioCollectionNew;
            proveedor.setProveedorServicioCollection(proveedorServicioCollectionNew);
            proveedor = em.merge(proveedor);
            if (usuario1Old != null && !usuario1Old.equals(usuario1New)) {
                usuario1Old.setProveedor(null);
                usuario1Old = em.merge(usuario1Old);
            }
            if (usuario1New != null && !usuario1New.equals(usuario1Old)) {
                usuario1New.setProveedor(proveedor);
                usuario1New = em.merge(usuario1New);
            }
            for (Solicitud solicitudCollectionNewSolicitud : solicitudCollectionNew) {
                if (!solicitudCollectionOld.contains(solicitudCollectionNewSolicitud)) {
                    Proveedor oldProveedorOfSolicitudCollectionNewSolicitud = solicitudCollectionNewSolicitud.getProveedor();
                    solicitudCollectionNewSolicitud.setProveedor(proveedor);
                    solicitudCollectionNewSolicitud = em.merge(solicitudCollectionNewSolicitud);
                    if (oldProveedorOfSolicitudCollectionNewSolicitud != null && !oldProveedorOfSolicitudCollectionNewSolicitud.equals(proveedor)) {
                        oldProveedorOfSolicitudCollectionNewSolicitud.getSolicitudCollection().remove(solicitudCollectionNewSolicitud);
                        oldProveedorOfSolicitudCollectionNewSolicitud = em.merge(oldProveedorOfSolicitudCollectionNewSolicitud);
                    }
                }
            }
            for (Mensaje mensajeCollectionNewMensaje : mensajeCollectionNew) {
                if (!mensajeCollectionOld.contains(mensajeCollectionNewMensaje)) {
                    Proveedor oldProveedorOfMensajeCollectionNewMensaje = mensajeCollectionNewMensaje.getProveedor();
                    mensajeCollectionNewMensaje.setProveedor(proveedor);
                    mensajeCollectionNewMensaje = em.merge(mensajeCollectionNewMensaje);
                    if (oldProveedorOfMensajeCollectionNewMensaje != null && !oldProveedorOfMensajeCollectionNewMensaje.equals(proveedor)) {
                        oldProveedorOfMensajeCollectionNewMensaje.getMensajeCollection().remove(mensajeCollectionNewMensaje);
                        oldProveedorOfMensajeCollectionNewMensaje = em.merge(oldProveedorOfMensajeCollectionNewMensaje);
                    }
                }
            }
            for (ProveedorServicio proveedorServicioCollectionNewProveedorServicio : proveedorServicioCollectionNew) {
                if (!proveedorServicioCollectionOld.contains(proveedorServicioCollectionNewProveedorServicio)) {
                    Proveedor oldProveedor1OfProveedorServicioCollectionNewProveedorServicio = proveedorServicioCollectionNewProveedorServicio.getProveedor1();
                    proveedorServicioCollectionNewProveedorServicio.setProveedor1(proveedor);
                    proveedorServicioCollectionNewProveedorServicio = em.merge(proveedorServicioCollectionNewProveedorServicio);
                    if (oldProveedor1OfProveedorServicioCollectionNewProveedorServicio != null && !oldProveedor1OfProveedorServicioCollectionNewProveedorServicio.equals(proveedor)) {
                        oldProveedor1OfProveedorServicioCollectionNewProveedorServicio.getProveedorServicioCollection().remove(proveedorServicioCollectionNewProveedorServicio);
                        oldProveedor1OfProveedorServicioCollectionNewProveedorServicio = em.merge(oldProveedor1OfProveedorServicioCollectionNewProveedorServicio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedor.getUsuario();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
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
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Solicitud> solicitudCollectionOrphanCheck = proveedor.getSolicitudCollection();
            for (Solicitud solicitudCollectionOrphanCheckSolicitud : solicitudCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the Solicitud " + solicitudCollectionOrphanCheckSolicitud + " in its solicitudCollection field has a non-nullable proveedor field.");
            }
            Collection<Mensaje> mensajeCollectionOrphanCheck = proveedor.getMensajeCollection();
            for (Mensaje mensajeCollectionOrphanCheckMensaje : mensajeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the Mensaje " + mensajeCollectionOrphanCheckMensaje + " in its mensajeCollection field has a non-nullable proveedor field.");
            }
            Collection<ProveedorServicio> proveedorServicioCollectionOrphanCheck = proveedor.getProveedorServicioCollection();
            for (ProveedorServicio proveedorServicioCollectionOrphanCheckProveedorServicio : proveedorServicioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the ProveedorServicio " + proveedorServicioCollectionOrphanCheckProveedorServicio + " in its proveedorServicioCollection field has a non-nullable proveedor1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario1 = proveedor.getUsuario1();
            if (usuario1 != null) {
                usuario1.setProveedor(null);
                usuario1 = em.merge(usuario1);
            }
            em.remove(proveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
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

    public Proveedor findProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
