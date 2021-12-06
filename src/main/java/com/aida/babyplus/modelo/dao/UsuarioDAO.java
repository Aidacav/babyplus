/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.modelo.dao;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.aida.babyplus.modelo.entidades.Usuario;
import javax.persistence.NoResultException;

/**
 *
 * @author Aida
 */
public class UsuarioDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public UsuarioDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    
    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    public Usuario buscarUsuarioParaLogin(String usuario, String password) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.where(cb.equal(rt.get("usuario"), usuario), cb.equal(rt.get("password"), password), cb.equal(rt.get("activo"), true));
            Query q = em.createQuery(cq);
            return ((Usuario) q.getSingleResult());
        } finally {
            em.close();
        }
    }
    
    public Usuario buscarPorUsuario(String usuario) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.where(cb.equal(rt.get("usuario"), usuario));
            Query q = em.createQuery(cq);
            return ((Usuario) q.getSingleResult());
        } catch (NoResultException nre) {
            return null;
        } finally {
            em.close();
        }
    }

    public Usuario cambiarEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            Usuario usuarioGuardado = em.find(Usuario.class, id);
            if (usuarioGuardado != null) {
                em.getTransaction().begin();
                usuarioGuardado.setActivo(!usuarioGuardado.getActivo());
                em.getTransaction().commit();
                return usuarioGuardado;
            }
        } finally {
            em.close();
        }

        return null;
    }

    public Usuario guardar(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
            return usuario;
        }finally {
            em.close();
        }
    }

    public void borrar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            Usuario usuarioGuardado = em.find(Usuario.class, id);
            if (usuarioGuardado != null) {
                em.getTransaction().begin();
                em.remove(usuarioGuardado);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}
