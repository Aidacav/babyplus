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
import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.util.Parseador;

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
    
    public Proveedor buscarPorId(Integer id) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Proveedor> cq = cb.createQuery(Proveedor.class);
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.where(cb.equal(rt.get("usuario"), id));
            Query q = em.createQuery(cq);
            return ((Proveedor) q.getSingleResult());
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Proveedor> buscarPorCriteriosAdmin(Proveedor proveedorABuscar) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Proveedor> cq = cb.createQuery(Proveedor.class);
            Root<Proveedor> rt = cq.from(Proveedor.class);
            List<Predicate> predicados = new ArrayList<>();
            
            predicados.add(cb.like(rt.get("usuario1").get("usuario").as(String.class), Parseador.aLike(proveedorABuscar.getUsuario1().getUsuario())));
            predicados.add(cb.like(rt.get("razonSocial").as(String.class), Parseador.aLike(proveedorABuscar.getRazonSocial())));
            predicados.add(cb.like(rt.get("cif").as(String.class), Parseador.aLike(proveedorABuscar.getCif())));
            
            if(proveedorABuscar.getUsuario1().getActivo() != null) {
                predicados.add(cb.equal(rt.get("usuario1").get("activo"), proveedorABuscar.getUsuario1().getActivo()));
            }
            
            if(proveedorABuscar.getUsuario1().getFechaAlta() != null) {
                predicados.add(cb.greaterThanOrEqualTo(rt.get("usuario1").<java.util.Date>get("fechaAlta"), proveedorABuscar.getUsuario1().getFechaAlta()));
            }
            
            cq.where(predicados.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(rt.get("usuario")));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Proveedor actualizarValoresAdmin(Proveedor datosProveedor, String nuevoPassword) {
        EntityManager em = getEntityManager();
        try {
            Proveedor proveedorGuardado = em.find(Proveedor.class, datosProveedor.getUsuario());
            if (proveedorGuardado != null) {
                em.getTransaction().begin();
                proveedorGuardado.setRazonSocial(datosProveedor.getRazonSocial());
                proveedorGuardado.setCif(datosProveedor.getCif());
                proveedorGuardado.setDireccion(datosProveedor.getDireccion());
                proveedorGuardado.setLocalidad(datosProveedor.getLocalidad());
                proveedorGuardado.setCp(datosProveedor.getCp());
                proveedorGuardado.setResponsable(datosProveedor.getResponsable());
                proveedorGuardado.getUsuario1().setPassword(nuevoPassword);
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
    
    public List<Proveedor> buscarPorCriteriosCliente(Proveedor proveedor) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Proveedor> cq = cb.createQuery(Proveedor.class);
            Root<Proveedor> rt = cq.from(Proveedor.class);
            List<Predicate> predicados = new ArrayList<>();
            
            if(proveedor.getRazonSocial() != null) {
                predicados.add(cb.like(rt.get("razonSocial"), Parseador.aLike(proveedor.getRazonSocial())));
            }
            
            if(proveedor.getLocalidad()!= null) {
                predicados.add(cb.like(rt.get("localidad"), Parseador.aLike(proveedor.getLocalidad())));
            }
            
            if(proveedor.getCp()!= null) {
                predicados.add(cb.equal(rt.get("cp"), proveedor.getCp()));
            }

            cq.where(predicados.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(rt.get("usuario")));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
