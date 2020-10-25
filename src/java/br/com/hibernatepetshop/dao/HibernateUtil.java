/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Categoria;
import br.com.hibernatepetshop.entidade.Fornecedor;
import br.com.hibernatepetshop.entidade.Produto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get S ession Factory
 * object.
 *
 * @author gusta
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(Fornecedor.class);
            cfg.addAnnotatedClass(Produto.class);
            cfg.addAnnotatedClass(Categoria.class);

            
            cfg.configure("/br/com/hibernatepetshop/dao/hibernate.cfg.xml");
            ServiceRegistry servico = new StandardServiceRegistryBuilder().
                    applySettings(cfg.getProperties()).build();
            
            sessionFactory = cfg.buildSessionFactory(servico);
            
            
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static Session abrirSessao() {
        return sessionFactory.openSession();
    }
}
