/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.modelo.dao;

import com.aida.babyplus.modelo.entidades.Mensaje;
import com.aida.babyplus.servicio.TipoUsuario;
import java.io.Serializable;
import java.util.ArrayList;
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
public class MensajeDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public MensajeDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    
    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    public Mensaje guardar(Mensaje mensaje) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(mensaje);
            em.getTransaction().commit();
            return mensaje;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<Mensaje> buscarHistorico(Integer id, TipoUsuario tipo) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Mensaje> cq = cb.createQuery(Mensaje.class);
            Root<Mensaje> rt = cq.from(Mensaje.class);
            List<Predicate> predicados = new ArrayList<>();
            
            switch(tipo) {
                case CLIENTE:
                    predicados.add(cb.equal(rt.get("cliente").get("usuario"), id));
                    break;
                case PROVEEDOR:
                    predicados.add(cb.equal(rt.get("proveedor").get("usuario"), id));
                    break;
                default:
                    throw new Exception("Forzando Salida");
            }
            
            cq.where(predicados.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(rt.get("id")));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            return new LinkedList<>();
        } finally {
            em.close();
        }
    }

    public List<Mensaje> buscarPorClienteYProveedor(Integer idCliente, Integer idProveedor) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Mensaje> cq = cb.createQuery(Mensaje.class);
            Root<Mensaje> rt = cq.from(Mensaje.class);
            List<Predicate> predicados = new ArrayList<>();
            
            predicados.add(cb.equal(rt.get("cliente").get("usuario"), idCliente));
            predicados.add(cb.equal(rt.get("proveedor").get("usuario"), idProveedor));
            
            cq.where(predicados.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(rt.get("id")));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            return new LinkedList<>();
        } finally {
            em.close();
        }
    }
}
