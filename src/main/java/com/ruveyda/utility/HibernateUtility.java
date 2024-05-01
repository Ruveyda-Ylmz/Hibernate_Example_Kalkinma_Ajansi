package com.ruveyda.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {
    //session = oturum
    //sessionu üretmek için session_factory'e ihtiyacımız var.
    /*burada ki ürettiğim final değişkene(SESSION_FACTORY) doğrudan erişim doğru olmadığından
    private tanımladık.get'le getirmemiz lazım(encapsülation)  */

    private static final SessionFactory SESSION_FACTORY = sessionFactoryHibernate();

    //SESSION_FACTORY oluşturmak için method tanımlıyoruz.
    /* methodu da private yapmamızın nedeni:
      başka bir değişken adıyla başka bir SESSION_FACTORY'le üretilmesini istemiyoruz.
      -configuration ayarları hibernate.cfg.xml e gitmekte,
      private yaparak her cfg ye gittiğinde birden fazla sessionfactory üretmesini engelliyoruz.
      */
    private static SessionFactory sessionFactoryHibernate(){
        try {
            Configuration configuration = new Configuration();
            SessionFactory factory = configuration.configure().buildSessionFactory(); //configure.
            return factory;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null; //data ıntegrıty
    }

    public static SessionFactory getSessionFactory(){ //sessionFactory 'i bul ve geri getir.
        return SESSION_FACTORY;
    }

}
