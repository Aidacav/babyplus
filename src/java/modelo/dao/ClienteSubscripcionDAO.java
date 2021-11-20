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
import modelo.entidades.ClienteSubscripcion;
import modelo.entidades.Subscripcion;

/**
 *
 * @author Aida
 */
public class ClienteSubscripcionDAO implements Serializable {

    public ClienteSubscripcionDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClienteSubscripcion clienteSubscripcion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = clienteSubscripcion.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getUsuario());
                clienteSubscripcion.setCliente(cliente);
            }
            Subscripcion subscripcion = clienteSubscripcion.getSubscripcion();
            if (subscripcion != null) {
                subscripcion = em.getReference(subscripcion.getClass(), subscripcion.getId());
                clienteSubscripcion.setSubscripcion(subscripcion);
            }
            em.persist(clienteSubscripcion);
            if (cliente != null) {
                cliente.getClienteSubscripcionCollection().add(clienteSubscripcion);
                cliente = em.merge(cliente);
            }
            if (subscripcion != null) {
                subscripcion.getClienteSubscripcionCollection().add(clienteSubscripcion);
                subscripcion = em.merge(subscripcion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClienteSubscripcion clienteSubscripcion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteSubscripcion persistentClienteSubscripcion = em.find(ClienteSubscripcion.class, clienteSubscripcion.getId());
            Cliente clienteOld = persistentClienteSubscripcion.getCliente();
            Cliente clienteNew = clienteSubscripcion.getCliente();
            Subscripcion subscripcionOld = persistentClienteSubscripcion.getSubscripcion();
            Subscripcion subscripcionNew = clienteSubscripcion.getSubscripcion();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getUsuario());
                clienteSubscripcion.setCliente(clienteNew);
            }
            if (subscripcionNew != null) {
                subscripcionNew = em.getReference(subscripcionNew.getClass(), subscripcionNew.getId());
                clienteSubscripcion.setSubscripcion(subscripcionNew);
            }
            clienteSubscripcion = em.merge(clienteSubscripcion);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getClienteSubscripcionCollection().remove(clienteSubscripcion);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getClienteSubscripcionCollection().add(clienteSubscripcion);
                clienteNew = em.merge(clienteNew);
            }
            if (subscripcionOld != null && !subscripcionOld.equals(subscripcionNew)) {
                subscripcionOld.getClienteSubscripcionCollection().remove(clienteSubscripcion);
                subscripcionOld = em.merge(subscripcionOld);
            }
            if (subscripcionNew != null && !subscripcionNew.equals(subscripcionOld)) {
                subscripcionNew.getClienteSubscripcionCollection().add(clienteSubscripcion);
                subscripcionNew = em.merge(subscripcionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clienteSubscripcion.getId();
                if (findClienteSubscripcion(id) == null) {
                    throw new NonexistentEntityException("The clienteSubscripcion with id " + id + " no longer exists.");
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
            ClienteSubscripcion clienteSubscripcion;
            try {
                clienteSubscripcion = em.getReference(ClienteSubscripcion.class, id);
                clienteSubscripcion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clienteSubscripcion with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = clienteSubscripcion.getCliente();
            if (cliente != null) {
                cliente.getClienteSubscripcionCollection().remove(clienteSubscripcion);
                cliente = em.merge(cliente);
            }
            Subscripcion subscripcion = clienteSubscripcion.getSubscripcion();
            if (subscripcion != null) {
                subscripcion.getClienteSubscripcionCollection().remove(clienteSubscripcion);
                subscripcion = em.merge(subscripcion);
            }
            em.remove(clienteSubscripcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClienteSubscripcion> findClienteSubscripcionEntities() {
        return findClienteSubscripcionEntities(true, -1, -1);
    }

    public List<ClienteSubscripcion> findClienteSubscripcionEntities(int maxResults, int firstResult) {
        return findClienteSubscripcionEntities(false, maxResults, firstResult);
    }

    private List<ClienteSubscripcion> findClienteSubscripcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClienteSubscripcion.class));
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

    public ClienteSubscripcion findClienteSubscripcion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClienteSubscripcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteSubscripcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClienteSubscripcion> rt = cq.from(ClienteSubscripcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
