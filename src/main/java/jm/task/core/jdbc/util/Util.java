package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static String URL = "jdbc:mysql://localhost:3306/users_shem?autoReconnect=true&useSSL=false";
    private static String USER = "root";
    private static String PASSWORD = "root";
    private static Connection CONNECTION;
    private static SessionFactory SESSIONFACTORY;

    public Util() {

    }

    public Util(String url, String user, String password) {
        URL = url;
        USER = user;
        PASSWORD = password;
    }


    public Connection getConnection() {
        try {
            CONNECTION = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            System.err.println("Ошибка в подключении");
            throwables.printStackTrace();
        }
        return CONNECTION;
    }

    public static void close() {
        try {
            if (CONNECTION!=null) {
                CONNECTION.close();
            }
            if (SESSIONFACTORY!=null) {
                SESSIONFACTORY.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        if (SESSIONFACTORY == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USER);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                SESSIONFACTORY = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  SESSIONFACTORY;
    }
}

