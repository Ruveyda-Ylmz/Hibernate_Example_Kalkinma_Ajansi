package com.ruveyda.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {
    private static final SessionFactory SESSION_FACTORY = sessionFactoryHibernate();

    private static SessionFactory sessionFactoryHibernate(){
        try {
            Configuration configuration = new Configuration();
            SessionFactory factory = configuration.configure().buildSessionFactory(); //configure.
            return factory;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static SessionFactory getSessionFactory(){
        return SESSION_FACTORY;
    }

//    private static final SessionFactory SESSION_FUNCTORY =sessionFuctoryHibernate();
//
//    private static SessionFactory sessionFuctoryHibernate(){
//        try {
//            Configuration configuration = new Configuration();
//            SessionFactory factory = configuration.configure().buildSessionFactory();
//            return factory;
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
//
//    public static SessionFactory getSessionFunctory(){
//        return SESSION_FUNCTORY;
//    }

}
