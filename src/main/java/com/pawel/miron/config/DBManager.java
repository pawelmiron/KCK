/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pawel.miron.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Pawel
 */
public class DBManager {

    private static DBManager instance;
    private EntityManagerFactory emf;

    private DBManager() {
    }

    public synchronized static DBManager getManager() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public EntityManagerFactory createEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("PersistanceUnit");
        }
        return emf;
    }

    public EntityManager createEntityManager() {
        return this.createEntityManagerFactory().createEntityManager();
    }

    public void closeEntityManagerFactory() {
        if (emf != null) {
            emf.close();
        }

    }
}
