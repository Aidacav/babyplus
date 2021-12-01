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
import com.aida.babyplus.modelo.ActualizacionProveedores;
import com.aida.babyplus.modelo.BusquedaProveedores;
import com.aida.babyplus.modelo.entidades.Proveedor;

/**
 *
 * @author Aida
 */
public class ProveedorDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public ProveedorDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    
    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }
    
    public List<Proveedor> buscarPorId(Integer id) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Proveedor> cq = cb.createQuery(Proveedor.class);
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.where(cb.equal(rt.get("usuario"), id));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Proveedor> buscarPorCriterios(BusquedaProveedores criterios) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Proveedor> cq = cb.createQuery(Proveedor.class);
            Root<Proveedor> rt = cq.from(Proveedor.class);
            List<Predicate> predicados = new ArrayList<>();
            
            predicados.add(cb.like(rt.get("usuario1").get("usuario").as(String.class), criterios.getNombreUsuario()));
            predicados.add(cb.like(rt.get("razonSocial").as(String.class), criterios.getRazonSocial()));
            predicados.add(cb.like(rt.get("cif").as(String.class), criterios.getCif()));
            
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

    public Proveedor actualizarValoresComoAdmin(ActualizacionProveedores nuevosValores) {
        EntityManager em = getEntityManager();
        try {
            Proveedor proveedorGuardado = em.find(Proveedor.class, nuevosValores.getId());
            if (proveedorGuardado != null) {
                em.getTransaction().begin();
                proveedorGuardado.setRazonSocial(nuevosValores.getRazonSocial());
                proveedorGuardado.setCif(nuevosValores.getCif());
                proveedorGuardado.setDireccion(nuevosValores.getDireccion());
                proveedorGuardado.setLocalidad(nuevosValores.getLocalidad());
                proveedorGuardado.setCp(nuevosValores.getCp());
                proveedorGuardado.setResponsable(nuevosValores.getResponsable());
                proveedorGuardado.getUsuario1().setPassword(nuevosValores.getPassword());
                em.getTransaction().commit();
                return proveedorGuardado;
            }
        } finally {
            em.close();
        }

        return null;
    }

    public Proveedor guardar(Proveedor proveedor) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(proveedor);
            em.getTransaction().commit();
            return proveedor;
        }finally {
            em.close();
        }
    }
}
