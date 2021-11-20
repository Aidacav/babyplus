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
import modelo.entidades.Proveedor;
import modelo.entidades.Rol;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.dao.exceptions.IllegalOrphanException;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.entidades.Usuario;

/**
 *
 * @author Aida
 */
public class UsuarioDAO implements Serializable {

    public UsuarioDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = usuario.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getUsuario());
                usuario.setCliente(cliente);
            }
            Proveedor proveedor = usuario.getProveedor();
            if (proveedor != null) {
                proveedor = em.getReference(proveedor.getClass(), proveedor.getUsuario());
                usuario.setProveedor(proveedor);
            }
            Rol rol = usuario.getRol();
            if (rol != null) {
                rol = em.getReference(rol.getClass(), rol.getId());
                usuario.setRol(rol);
            }
            em.persist(usuario);
            if (cliente != null) {
                Usuario oldUsuario1OfCliente = cliente.getUsuario1();
                if (oldUsuario1OfCliente != null) {
                    oldUsuario1OfCliente.setCliente(null);
                    oldUsuario1OfCliente = em.merge(oldUsuario1OfCliente);
                }
                cliente.setUsuario1(usuario);
                cliente = em.merge(cliente);
            }
            if (proveedor != null) {
                Usuario oldUsuario1OfProveedor = proveedor.getUsuario1();
                if (oldUsuario1OfProveedor != null) {
                    oldUsuario1OfProveedor.setProveedor(null);
                    oldUsuario1OfProveedor = em.merge(oldUsuario1OfProveedor);
                }
                proveedor.setUsuario1(usuario);
                proveedor = em.merge(proveedor);
            }
            if (rol != null) {
                rol.getUsuarioCollection().add(usuario);
                rol = em.merge(rol);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            Cliente clienteOld = persistentUsuario.getCliente();
            Cliente clienteNew = usuario.getCliente();
            Proveedor proveedorOld = persistentUsuario.getProveedor();
            Proveedor proveedorNew = usuario.getProveedor();
            Rol rolOld = persistentUsuario.getRol();
            Rol rolNew = usuario.getRol();
            List<String> illegalOrphanMessages = null;
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Cliente " + clienteOld + " since its usuario1 field is not nullable.");
            }
            if (proveedorOld != null && !proveedorOld.equals(proveedorNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Proveedor " + proveedorOld + " since its usuario1 field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getUsuario());
                usuario.setCliente(clienteNew);
            }
            if (proveedorNew != null) {
                proveedorNew = em.getReference(proveedorNew.getClass(), proveedorNew.getUsuario());
                usuario.setProveedor(proveedorNew);
            }
            if (rolNew != null) {
                rolNew = em.getReference(rolNew.getClass(), rolNew.getId());
                usuario.setRol(rolNew);
            }
            usuario = em.merge(usuario);
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                Usuario oldUsuario1OfCliente = clienteNew.getUsuario1();
                if (oldUsuario1OfCliente != null) {
                    oldUsuario1OfCliente.setCliente(null);
                    oldUsuario1OfCliente = em.merge(oldUsuario1OfCliente);
                }
                clienteNew.setUsuario1(usuario);
                clienteNew = em.merge(clienteNew);
            }
            if (proveedorNew != null && !proveedorNew.equals(proveedorOld)) {
                Usuario oldUsuario1OfProveedor = proveedorNew.getUsuario1();
                if (oldUsuario1OfProveedor != null) {
                    oldUsuario1OfProveedor.setProveedor(null);
                    oldUsuario1OfProveedor = em.merge(oldUsuario1OfProveedor);
                }
                proveedorNew.setUsuario1(usuario);
                proveedorNew = em.merge(proveedorNew);
            }
            if (rolOld != null && !rolOld.equals(rolNew)) {
                rolOld.getUsuarioCollection().remove(usuario);
                rolOld = em.merge(rolOld);
            }
            if (rolNew != null && !rolNew.equals(rolOld)) {
                rolNew.getUsuarioCollection().add(usuario);
                rolNew = em.merge(rolNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Cliente clienteOrphanCheck = usuario.getCliente();
            if (clienteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Cliente " + clienteOrphanCheck + " in its cliente field has a non-nullable usuario1 field.");
            }
            Proveedor proveedorOrphanCheck = usuario.getProveedor();
            if (proveedorOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Proveedor " + proveedorOrphanCheck + " in its proveedor field has a non-nullable usuario1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Rol rol = usuario.getRol();
            if (rol != null) {
                rol.getUsuarioCollection().remove(usuario);
                rol = em.merge(rol);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
