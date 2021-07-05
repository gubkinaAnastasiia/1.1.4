package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Anastasiiya", "Gubkina", (byte) 18);
        userService.saveUser("Anastasiiya", "Mirror", (byte) 26);
        userService.saveUser("Georgiy", "Mirror", (byte) 32);
        userService.saveUser("Pirat", "Mirror", (byte) 2);

        List<User> result = userService.getAllUsers();
        Iterator<User> it = result.iterator();
        while (it.hasNext()) {
            User user = it.next();
            System.out.println(user.toString());
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
