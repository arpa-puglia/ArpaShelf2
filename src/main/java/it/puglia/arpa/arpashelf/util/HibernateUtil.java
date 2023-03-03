/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.puglia.arpa.arpashelf.util.HibernateUtil
 *  org.hibernate.SessionFactory
 *  org.hibernate.cfg.Configuration
 */
package it.puglia.arpa.arpashelf.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory((String)"hibernate.cfg.xml");

    private static SessionFactory buildSessionFactory(String resource) {
        try {
            return new Configuration().configure(resource).buildSessionFactory();
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed from " + resource + ". " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

