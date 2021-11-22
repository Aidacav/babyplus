/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.entidades.Usuario;

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
    
}
