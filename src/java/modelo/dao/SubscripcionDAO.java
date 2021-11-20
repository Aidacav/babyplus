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
import modelo.entidades.ClienteSubscripcion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.dao.exceptions.IllegalOrphanException;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.entidades.Subscripcion;

/**
 *
 * @author Aida
 */
public class SubscripcionDAO implements Serializable {

    public SubscripcionDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subscripcion subscripcion) {
        if (subscripcion.getClienteSubscripcionCollection() == null) {
            subscripcion.setClienteSubscripcionCollection(new ArrayList<ClienteSubscripcion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ClienteSubscripcion> attachedClienteSubscripcionCollection = new ArrayList<ClienteSubscripcion>();
            for (ClienteSubscripcion clienteSubscripcionCollectionClienteSubscripcionToAttach : subscripcion.getClienteSubscripcionCollection()) {
                clienteSubscripcionCollectionClienteSubscripcionToAttach = em.getReference(clienteSubscripcionCollectionClienteSubscripcionToAttach.getClass(), clienteSubscripcionCollectionClienteSubscripcionToAttach.getId());
                attachedClienteSubscripcionCollection.add(clienteSubscripcionCollectionClienteSubscripcionToAttach);
            }
            subscripcion.setClienteSubscripcionCollection(attachedClienteSubscripcionCollection);
            em.persist(subscripcion);
            for (ClienteSubscripcion clienteSubscripcionCollectionClienteSubscripcion : subscripcion.getClienteSubscripcionCollection()) {
                Subscripcion oldSubscripcionOfClienteSubscripcionCollectionClienteSubscripcion = clienteSubscripcionCollectionClienteSubscripcion.getSubscripcion();
                clienteSubscripcionCollectionClienteSubscripcion.setSubscripcion(subscripcion);
                clienteSubscripcionCollectionClienteSubscripcion = em.merge(clienteSubscripcionCollectionClienteSubscripcion);
                if (oldSubscripcionOfClienteSubscripcionCollectionClienteSubscripcion != null) {
                    oldSubscripcionOfClienteSubscripcionCollectionClienteSubscripcion.getClienteSubscripcionCollection().remove(clienteSubscripcionCollectionClienteSubscripcion);
                    oldSubscripcionOfClienteSubscripcionCollectionClienteSubscripcion = em.merge(oldSubscripcionOfClienteSubscripcionCollectionClienteSubscripcion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Subscripcion subscripcion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Subscripcion persistentSubscripcion = em.find(Subscripcion.class, subscripcion.getId());
            Collection<ClienteSubscripcion> clienteSubscripcionCollectionOld = persistentSubscripcion.getClienteSubscripcionCollection();
            Collection<ClienteSubscripcion> clienteSubscripcionCollectionNew = subscripcion.getClienteSubscripcionCollection();
            List<String> illegalOrphanMessages = null;
            for (ClienteSubscripcion clienteSubscripcionCollectionOldClienteSubscripcion : clienteSubscripcionCollectionOld) {
                if (!clienteSubscripcionCollectionNew.contains(clienteSubscripcionCollectionOldClienteSubscripcion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClienteSubscripcion " + clienteSubscripcionCollectionOldClienteSubscripcion + " since its subscripcion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ClienteSubscripcion> attachedClienteSubscripcionCollectionNew = new ArrayList<ClienteSubscripcion>();
            for (ClienteSubscripcion clienteSubscripcionCollectionNewClienteSubscripcionToAttach : clienteSubscripcionCollectionNew) {
                clienteSubscripcionCollectionNewClienteSubscripcionToAttach = em.getReference(clienteSubscripcionCollectionNewClienteSubscripcionToAttach.getClass(), clienteSubscripcionCollectionNewClienteSubscripcionToAttach.getId());
                attachedClienteSubscripcionCollectionNew.add(clienteSubscripcionCollectionNewClienteSubscripcionToAttach);
            }
            clienteSubscripcionCollectionNew = attachedClienteSubscripcionCollectionNew;
            subscripcion.setClienteSubscripcionCollection(clienteSubscripcionCollectionNew);
            subscripcion = em.merge(subscripcion);
            for (ClienteSubscripcion clienteSubscripcionCollectionNewClienteSubscripcion : clienteSubscripcionCollectionNew) {
                if (!clienteSubscripcionCollectionOld.contains(clienteSubscripcionCollectionNewClienteSubscripcion)) {
                    Subscripcion oldSubscripcionOfClienteSubscripcionCollectionNewClienteSubscripcion = clienteSubscripcionCollectionNewClienteSubscripcion.getSubscripcion();
                    clienteSubscripcionCollectionNewClienteSubscripcion.setSubscripcion(subscripcion);
                    clienteSubscripcionCollectionNewClienteSubscripcion = em.merge(clienteSubscripcionCollectionNewClienteSubscripcion);
                    if (oldSubscripcionOfClienteSubscripcionCollectionNewClienteSubscripcion != null && !oldSubscripcionOfClienteSubscripcionCollectionNewClienteSubscripcion.equals(subscripcion)) {
                        oldSubscripcionOfClienteSubscripcionCollectionNewClienteSubscripcion.getClienteSubscripcionCollection().remove(clienteSubscripcionCollectionNewClienteSubscripcion);
                        oldSubscripcionOfClienteSubscripcionCollectionNewClienteSubscripcion = em.merge(oldSubscripcionOfClienteSubscripcionCollectionNewClienteSubscripcion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = subscripcion.getId();
                if (findSubscripcion(id) == null) {
                    throw new NonexistentEntityException("The subscripcion with id " + id + " no longer exists.");
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
            Subscripcion subscripcion;
            try {
                subscripcion = em.getReference(Subscripcion.class, id);
                subscripcion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subscripcion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ClienteSubscripcion> clienteSubscripcionCollectionOrphanCheck = subscripcion.getClienteSubscripcionCollection();
            for (ClienteSubscripcion clienteSubscripcionCollectionOrphanCheckClienteSubscripcion : clienteSubscripcionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subscripcion (" + subscripcion + ") cannot be destroyed since the ClienteSubscripcion " + clienteSubscripcionCollectionOrphanCheckClienteSubscripcion + " in its clienteSubscripcionCollection field has a non-nullable subscripcion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(subscripcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Subscripcion> findSubscripcionEntities() {
        return findSubscripcionEntities(true, -1, -1);
    }

    public List<Subscripcion> findSubscripcionEntities(int maxResults, int firstResult) {
        return findSubscripcionEntities(false, maxResults, firstResult);
    }

    private List<Subscripcion> findSubscripcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subscripcion.class));
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

    public Subscripcion findSubscripcion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subscripcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubscripcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subscripcion> rt = cq.from(Subscripcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
