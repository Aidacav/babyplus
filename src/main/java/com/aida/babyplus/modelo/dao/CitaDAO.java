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

/**
 *
 * @author Aida
 */
public class CitaDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public CitaDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    
    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }
}
