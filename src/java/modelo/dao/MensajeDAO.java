/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.entidades.Cliente;
import modelo.entidades.Mensaje;
import modelo.entidades.Proveedor;

/**
 *
 * @author Aida
 */
public class MensajeDAO implements Serializable {

    public MensajeDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mensaje mensaje) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = mensaje.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getUsuario());
                mensaje.setCliente(cliente);
            }
            Proveedor proveedor = mensaje.getProveedor();
            if (proveedor != null) {
                proveedor = em.getReference(proveedor.getClass(), proveedor.getUsuario());
                mensaje.setProveedor(proveedor);
            }
            em.persist(mensaje);
            if (cliente != null) {
                cliente.getMensajeCollection().add(mensaje);
                cliente = em.merge(cliente);
            }
            if (proveedor != null) {
                proveedor.getMensajeCollection().add(mensaje);
                proveedor = em.merge(proveedor);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mensaje mensaje) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mensaje persistentMensaje = em.find(Mensaje.class, mensaje.getId());
            Cliente clienteOld = persistentMensaje.getCliente();
            Cliente clienteNew = mensaje.getCliente();
            Proveedor proveedorOld = persistentMensaje.getProveedor();
            Proveedor proveedorNew = mensaje.getProveedor();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getUsuario());
                mensaje.setCliente(clienteNew);
            }
            if (proveedorNew != null) {
                proveedorNew = em.getReference(proveedorNew.getClass(), proveedorNew.getUsuario());
                mensaje.setProveedor(proveedorNew);
            }
            mensaje = em.merge(mensaje);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getMensajeCollection().remove(mensaje);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getMensajeCollection().add(mensaje);
                clienteNew = em.merge(clienteNew);
            }
            if (proveedorOld != null && !proveedorOld.equals(proveedorNew)) {
                proveedorOld.getMensajeCollection().remove(mensaje);
                proveedorOld = em.merge(proveedorOld);
            }
            if (proveedorNew != null && !proveedorNew.equals(proveedorOld)) {
                proveedorNew.getMensajeCollection().add(mensaje);
                proveedorNew = em.merge(proveedorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mensaje.getId();
                if (findMensaje(id) == null) {
                    throw new NonexistentEntityException("The mensaje with id " + id + " no longer exists.");
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
            Mensaje mensaje;
            try {
                mensaje = em.getReference(Mensaje.class, id);
                mensaje.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mensaje with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = mensaje.getCliente();
            if (cliente != null) {
                cliente.getMensajeCollection().remove(mensaje);
                cliente = em.merge(cliente);
            }
            Proveedor proveedor = mensaje.getProveedor();
            if (proveedor != null) {
                proveedor.getMensajeCollection().remove(mensaje);
                proveedor = em.merge(proveedor);
            }
            em.remove(mensaje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mensaje> findMensajeEntities() {
        return findMensajeEntities(true, -1, -1);
    }

    public List<Mensaje> findMensajeEntities(int maxResults, int firstResult) {
        return findMensajeEntities(false, maxResults, firstResult);
    }

    private List<Mensaje> findMensajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mensaje.class));
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

    public Mensaje findMensaje(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mensaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getMensajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mensaje> rt = cq.from(Mensaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
