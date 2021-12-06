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
import com.aida.babyplus.modelo.entidades.Cliente;
import com.aida.babyplus.modelo.entidades.ClienteSubscripcion;
import com.aida.babyplus.modelo.entidades.Paciente;
import com.aida.babyplus.util.Parseador;
import java.util.Collection;
import java.util.LinkedList;

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
    
    public Cliente buscarPorId(Integer id) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.where(cb.equal(rt.get("usuario"), id));
            Query q = em.createQuery(cq);
            return ((Cliente) q.getSingleResult());
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Cliente> buscarPorCriteriosAdmin(Cliente clienteABuscar) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
            Root<Cliente> rt = cq.from(Cliente.class);
            List<Predicate> predicados = new ArrayList<>();
            
            predicados.add(cb.like(rt.get("usuario1").get("usuario").as(String.class), Parseador.aLike(clienteABuscar.getUsuario1().getUsuario())));
            predicados.add(cb.like(rt.get("nombre").as(String.class), Parseador.aLike(clienteABuscar.getNombre())));
            predicados.add(cb.like(rt.get("apellidos").as(String.class), Parseador.aLike(clienteABuscar.getApellidos())));
            
            if(clienteABuscar.getUsuario1().getActivo() != null) {
                predicados.add(cb.equal(rt.get("usuario1").get("activo"), clienteABuscar.getUsuario1().getActivo()));
            }
            
            if(clienteABuscar.getUsuario1().getFechaAlta() != null) {
                predicados.add(cb.greaterThanOrEqualTo(rt.get("usuario1").<java.util.Date>get("fechaAlta"), clienteABuscar.getUsuario1().getFechaAlta()));
            }
            
            cq.where(predicados.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(rt.get("usuario")));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente actualizarCliente(Cliente datosCliente, String nuevoPassword) {
        EntityManager em = getEntityManager();
        try {
            Cliente clienteGuardado = em.find(Cliente.class, datosCliente.getUsuario());
            if (clienteGuardado != null) {
                em.getTransaction().begin();
                clienteGuardado.setNombre(datosCliente.getNombre());
                clienteGuardado.setApellidos(datosCliente.getApellidos());
                clienteGuardado.setFechaNacimiento(datosCliente.getFechaNacimiento());
                clienteGuardado.setDomicilio(datosCliente.getDomicilio());
                clienteGuardado.setLocalidad(datosCliente.getLocalidad());
                clienteGuardado.setCp(datosCliente.getCp());
                clienteGuardado.getUsuario1().setPassword(nuevoPassword);
                em.getTransaction().commit();
                return clienteGuardado;
            }
        } finally {
            em.close();
        }

        return null;
    }

    public Cliente guardar(Cliente cliente) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return cliente;
        } finally {
            em.close();
        }
    }
    
    public Cliente agregarSubscripcion(ClienteSubscripcion subscripcion) {
        EntityManager em = getEntityManager();
        try {
            Cliente clienteGuardado = em.find(Cliente.class, subscripcion.getCliente().getUsuario());
            if (clienteGuardado != null) {
                em.getTransaction().begin();
                clienteGuardado.getSubscripciones().add(subscripcion);
                em.getTransaction().commit();
                return clienteGuardado;
            }
        } finally {
            em.close();
        }

        return null;
    }

    public Cliente agregarPaciente(Paciente nuevoHijo) {
        EntityManager em = getEntityManager();
        try {
            Cliente clienteGuardado = em.find(Cliente.class, nuevoHijo.getCliente().getUsuario());
            if (clienteGuardado != null) {
                em.getTransaction().begin();
                clienteGuardado.getHijos().add(nuevoHijo);
                em.getTransaction().commit();
                return clienteGuardado;
            }
        } finally {
            em.close();
        }

        return null;
    }

    public Cliente actualizarPaciente(Paciente datosHijo) {
        EntityManager em = getEntityManager();
        try {
            Cliente clienteGuardado = em.find(Cliente.class, datosHijo.getCliente().getUsuario());
            if (clienteGuardado != null) {
                em.getTransaction().begin();
                for(Paciente hijo : clienteGuardado.getHijos()) {
                    if(hijo.getId().equals(datosHijo.getId())) {
                        hijo.setNombre(datosHijo.getNombre());
                        hijo.setFechaNacimiento(datosHijo.getFechaNacimiento());
                        hijo.setObservaciones(datosHijo.getObservaciones());
                        break;
                    }
                }
                em.getTransaction().commit();
                return clienteGuardado;
            }
        } finally {
            em.close();
        }

        return null;
    }

    public Cliente eliminarPaciente(Paciente datosHijo) {
        EntityManager em = getEntityManager();
        try {
            Cliente clienteGuardado = em.find(Cliente.class, datosHijo.getCliente().getUsuario());
            if (clienteGuardado != null) {
                em.getTransaction().begin();
                Collection<Paciente> restantes = new LinkedList<>();
                for(Paciente hijo : clienteGuardado.getHijos()) {
                    if(!hijo.getId().equals(datosHijo.getId())) {
                        restantes.add(hijo);
                    }
                }
                clienteGuardado.getHijos().clear();
                clienteGuardado.getHijos().addAll(restantes);
                em.getTransaction().commit();
                return clienteGuardado;
            }
        } finally {
            em.close();
        }

        return null;
    }
}
