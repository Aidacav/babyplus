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
import modelo.dao.exceptions.PreexistingEntityException;
import modelo.entidades.Proveedor;
import modelo.entidades.ProveedorServicio;
import modelo.entidades.ProveedorServicioPK;
import modelo.entidades.Servicio;

/**
 *
 * @author Aida
 */
public class ProveedorServicioDAO implements Serializable {

    public ProveedorServicioDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProveedorServicio proveedorServicio) throws PreexistingEntityException, Exception {
        if (proveedorServicio.getProveedorServicioPK() == null) {
            proveedorServicio.setProveedorServicioPK(new ProveedorServicioPK());
        }
        proveedorServicio.getProveedorServicioPK().setServicio(proveedorServicio.getServicio1().getId());
        proveedorServicio.getProveedorServicioPK().setProveedor(proveedorServicio.getProveedor1().getUsuario());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor proveedor1 = proveedorServicio.getProveedor1();
            if (proveedor1 != null) {
                proveedor1 = em.getReference(proveedor1.getClass(), proveedor1.getUsuario());
                proveedorServicio.setProveedor1(proveedor1);
            }
            Servicio servicio1 = proveedorServicio.getServicio1();
            if (servicio1 != null) {
                servicio1 = em.getReference(servicio1.getClass(), servicio1.getId());
                proveedorServicio.setServicio1(servicio1);
            }
            em.persist(proveedorServicio);
            if (proveedor1 != null) {
                proveedor1.getProveedorServicioCollection().add(proveedorServicio);
                proveedor1 = em.merge(proveedor1);
            }
            if (servicio1 != null) {
                servicio1.getProveedorServicioCollection().add(proveedorServicio);
                servicio1 = em.merge(servicio1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProveedorServicio(proveedorServicio.getProveedorServicioPK()) != null) {
                throw new PreexistingEntityException("ProveedorServicio " + proveedorServicio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProveedorServicio proveedorServicio) throws NonexistentEntityException, Exception {
        proveedorServicio.getProveedorServicioPK().setServicio(proveedorServicio.getServicio1().getId());
        proveedorServicio.getProveedorServicioPK().setProveedor(proveedorServicio.getProveedor1().getUsuario());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProveedorServicio persistentProveedorServicio = em.find(ProveedorServicio.class, proveedorServicio.getProveedorServicioPK());
            Proveedor proveedor1Old = persistentProveedorServicio.getProveedor1();
            Proveedor proveedor1New = proveedorServicio.getProveedor1();
            Servicio servicio1Old = persistentProveedorServicio.getServicio1();
            Servicio servicio1New = proveedorServicio.getServicio1();
            if (proveedor1New != null) {
                proveedor1New = em.getReference(proveedor1New.getClass(), proveedor1New.getUsuario());
                proveedorServicio.setProveedor1(proveedor1New);
            }
            if (servicio1New != null) {
                servicio1New = em.getReference(servicio1New.getClass(), servicio1New.getId());
                proveedorServicio.setServicio1(servicio1New);
            }
            proveedorServicio = em.merge(proveedorServicio);
            if (proveedor1Old != null && !proveedor1Old.equals(proveedor1New)) {
                proveedor1Old.getProveedorServicioCollection().remove(proveedorServicio);
                proveedor1Old = em.merge(proveedor1Old);
            }
            if (proveedor1New != null && !proveedor1New.equals(proveedor1Old)) {
                proveedor1New.getProveedorServicioCollection().add(proveedorServicio);
                proveedor1New = em.merge(proveedor1New);
            }
            if (servicio1Old != null && !servicio1Old.equals(servicio1New)) {
                servicio1Old.getProveedorServicioCollection().remove(proveedorServicio);
                servicio1Old = em.merge(servicio1Old);
            }
            if (servicio1New != null && !servicio1New.equals(servicio1Old)) {
                servicio1New.getProveedorServicioCollection().add(proveedorServicio);
                servicio1New = em.merge(servicio1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ProveedorServicioPK id = proveedorServicio.getProveedorServicioPK();
                if (findProveedorServicio(id) == null) {
                    throw new NonexistentEntityException("The proveedorServicio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProveedorServicioPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProveedorServicio proveedorServicio;
            try {
                proveedorServicio = em.getReference(ProveedorServicio.class, id);
                proveedorServicio.getProveedorServicioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedorServicio with id " + id + " no longer exists.", enfe);
            }
            Proveedor proveedor1 = proveedorServicio.getProveedor1();
            if (proveedor1 != null) {
                proveedor1.getProveedorServicioCollection().remove(proveedorServicio);
                proveedor1 = em.merge(proveedor1);
            }
            Servicio servicio1 = proveedorServicio.getServicio1();
            if (servicio1 != null) {
                servicio1.getProveedorServicioCollection().remove(proveedorServicio);
                servicio1 = em.merge(servicio1);
            }
            em.remove(proveedorServicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProveedorServicio> findProveedorServicioEntities() {
        return findProveedorServicioEntities(true, -1, -1);
    }

    public List<ProveedorServicio> findProveedorServicioEntities(int maxResults, int firstResult) {
        return findProveedorServicioEntities(false, maxResults, firstResult);
    }

    private List<ProveedorServicio> findProveedorServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProveedorServicio.class));
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

    public ProveedorServicio findProveedorServicio(ProveedorServicioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProveedorServicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProveedorServicio> rt = cq.from(ProveedorServicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
