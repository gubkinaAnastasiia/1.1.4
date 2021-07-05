package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery("create table if not exists users" +
                    "(id int AUTO_INCREMENT PRIMARY KEY NOT NULL ," +
                    "name varchar(15) null," +
                    "lastName varchar(20) null," +
                    "age int null)").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Произошла ошибка создания таблицы.");
            if (transaction!=null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery("drop table if exists users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Произошла ошибка создания таблицы.");
            if (transaction!=null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.println("User с именем " + name + " добавлен в базу данных.");
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Произошла ошибка добавления пользователя.");
            if (transaction!=null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession();){
            transaction = session.beginTransaction();
            User userToRemove = session.get(User.class, id);
            session.delete(userToRemove);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Произошла ошибка удаления пользователя");
            if (transaction!=null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User").list();
            transaction.commit();
            return users;
        } catch (Exception e) {
            System.out.println("Произошла ошибка получения списка пользователей.");
            if (transaction!=null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            List<User> users = getAllUsers();
            Iterator<User> iterator = users.iterator();
            while (iterator.hasNext()) {
                User user = iterator.next();
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Произошла ошибка очищения таблицы.");
            if (transaction!=null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
