package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class UserDaoJDBCImpl implements UserDao{

    public UserDaoJDBCImpl() {
    }


    public void createUsersTable() {
        Util util = new Util();
        String CREATE_TABLE = "create table if not exists users" +
                "(id int AUTO_INCREMENT PRIMARY KEY NOT NULL ," +
                "name varchar(15) null," +
                "lastName varchar(20) null," +
                "age int null)";
        try {
            Statement statement = util.getConnection().createStatement();

            statement.executeUpdate(CREATE_TABLE);

            statement.close();
        } catch (SQLException throwables) {
            System.err.println("Ошибка в создании таблицы.");
            throwables.printStackTrace();
        }
        util.close();
    };

    public void dropUsersTable() {
        Util util = new Util();
        String DROP_TABLE = "drop table if exists users";
        try {
            Statement statement = util.getConnection().createStatement();

            statement.executeUpdate(DROP_TABLE);

            statement.close();
        } catch (SQLException throwables) {
            System.err.println("Ошибка в удалении таблицы");
            throwables.printStackTrace();
        }
        util.close();
    };

    public void saveUser(String name, String lastName, byte age) {
        Util util = new Util();
        String SAVE_USER = "insert into users(name, lastName, age) values (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = util.getConnection().prepareStatement(SAVE_USER);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();

            System.out.println("User с именем " + name + " добавлен в базу данных.");
            preparedStatement.close();
        } catch (SQLException throwables) {
            System.err.println("Ошибка в добавлении пользователя.");
            throwables.printStackTrace();
        }
        util.close();
    };

    public void removeUserById(long id) {
        Util util = new Util();
        String REMOVE_USER = "delete from users where id=(?)";

        try {
            PreparedStatement preparedStatement = util.getConnection().prepareStatement(REMOVE_USER);

            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException throwables) {
            System.err.println("Проблема с удалением пользователя.");
            throwables.printStackTrace();
        }
        util.close();
    };

    public List<User> getAllUsers() {
        Util util = new Util();
        String GET_ALL_USER = "select * from users";
        List<User> result = null;
        try {
            Statement statement = util.getConnection().createStatement();

            result = new ArrayList<>();
            ResultSet set = statement.executeQuery(GET_ALL_USER);

            while (set.next()) {
                User user = new User(set.getString("name"), set.getString("lastName"), set.getByte("age"));
                user.setId(set.getLong("id"));
                result.add(user);
            }

            statement.close();
        } catch (SQLException throwables) {
            System.err.println("Проблема с выдачей списка.");
            throwables.printStackTrace();
            result = null;
        }
        util.close();
        return result;
    };

    public void cleanUsersTable() {
        Util util = new Util();
        String CLEAN_USERS = "delete from users";

        try {
            Statement statement = util.getConnection().createStatement();

            statement.executeUpdate(CLEAN_USERS);

            statement.close();
        } catch (SQLException throwables) {
            System.err.println("Проблема с очисткой таблицы.");
            throwables.printStackTrace();
        }
        util.close();
    };
}

