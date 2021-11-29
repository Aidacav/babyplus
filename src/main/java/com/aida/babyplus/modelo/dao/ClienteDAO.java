/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.modelo.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.aida.babyplus.modelo.ActualizacionClientes;
import com.aida.babyplus.modelo.BusquedaClientes;
import com.aida.babyplus.modelo.entidades.Cliente;

/**
 *
 * @author Aida
 */
public class ClienteDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public ClienteDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    
    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }
    
    public List<Cliente> buscarPorId(Integer id) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.where(cb.equal(rt.get("usuario"), id));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Cliente> buscarPorCriterios(BusquedaClientes criterios) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
            Root<Cliente> rt = cq.from(Cliente.class);
            List<Predicate> predicados = new ArrayList<>();
            
            predicados.add(cb.like(rt.get("usuario1").get("usuario").as(String.class), criterios.getNombreUsuario()));
            predicados.add(cb.like(rt.get("nombre").as(String.class), criterios.getNombre()));
            predicados.add(cb.like(rt.get("apellidos").as(String.class), criterios.getApellidos()));
            
            if(criterios.getActivo() != null) {
                predicados.add(cb.equal(rt.get("usuario1").get("activo"), criterios.getActivo()));
            }
            
            if(criterios.getFechaAlta() != null) {
                predicados.add(cb.greaterThanOrEqualTo(rt.get("usuario1").<java.util.Date>get("fechaAlta"), criterios.getFechaAlta()));
            }
            
            cq.where(predicados.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(rt.get("usuario")));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente actualizarValoresComoAdmin(ActualizacionClientes nuevosValores) {
        EntityManager em = getEntityManager();
        try {
            Cliente clienteGuardado = em.find(Cliente.class, nuevosValores.getId());
            if (clienteGuardado != null) {
                em.getTransaction().begin();
                clienteGuardado.setNombre(nuevosValores.getNombre());
                clienteGuardado.setApellidos(nuevosValores.getApellidos());
                clienteGuardado.setFechaNacimiento(nuevosValores.getFechaNacimiento());
                clienteGuardado.setDomicilio(nuevosValores.getDomicilio());
                clienteGuardado.setLocalidad(nuevosValores.getLocalidad());
                clienteGuardado.setCp(nuevosValores.getCp());
                clienteGuardado.getUsuario1().setPassword(nuevosValores.getPassword());
                em.getTransaction().commit();
                return clienteGuardado;
            }
        } finally {
            em.close();
        }

        return null;
    }
}
