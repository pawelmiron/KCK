/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pawel.miron.listeners;

import com.pawel.miron.enitity.Album;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Pawel
 */
public class ContextListener implements ServletContextListener {

    private Configuration configuration;
    private ServiceRegistry serviceRegistry;
    private SessionFactory factory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Album.class).buildSessionFactory(serviceRegistry);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        factory.close();
    }

}
