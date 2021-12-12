package com.aida.babyplus.modelo.dao;

import com.aida.babyplus.modelo.entidades.Post;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Aida
 */
public class PostDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public PostDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    
    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    public List<Post> buscarTodos() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Post> cq = cb.createQuery(Post.class);
            Root<Post> rt = cq.from(Post.class);
            cq.orderBy(cb.desc(rt.get("id")));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            return new LinkedList<>();
        } 
        finally {
            em.close();
        }
    }

    public Post guardar(Post post) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(post);
            em.getTransaction().commit();
            return post;
        } finally {
            em.close();
        }
    }

    public List<Post> buscarConRol(String rol) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Post> cq = cb.createQuery(Post.class);
            Root<Post> rt = cq.from(Post.class);
            List<Predicate> predicados = new ArrayList<>();
            
            predicados.add(cb.or(cb.equal(rt.get("ambito"), rol), cb.isNull(rt.get("ambito"))));
            predicados.add(cb.and(cb.or(cb.isNull(rt.get("fechaExpiracion")), cb.greaterThanOrEqualTo(rt.<Date>get("fechaExpiracion"), Date.from(Instant.now())))));
            
            cq.where(predicados.toArray(new Predicate[0]));
            cq.orderBy(cb.desc(rt.get("id")));
            Query q = em.createQuery(cq).setMaxResults(5);
            return q.getResultList();
        } catch (Exception e) {
            return new LinkedList<>();
        } 
        finally {
            em.close();
        }
    }
}
