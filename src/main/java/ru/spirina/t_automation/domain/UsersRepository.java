package ru.spirina.t_automation.domain;

import ru.spirina.t_automation.model.User;
import ru.spirina.t_automation.sql.SqlSource;

import java.io.IOException;
import java.util.List;

public class UsersRepository {

    private final LocalSource localSource = new LocalSource();
    private final NetworkSource remoteSource = new NetworkSource();
    private final SqlSource database = new SqlSource();

    public List<User> getUsers(int count) {
        try {
            List<User> users = remoteSource.getUsers(count);
            database.insertOrUpdate(users);
            return users;
        } catch (IOException exception) {
            System.out.println("Error while fetching config from remote: " + exception);
            System.out.println("Fetching from database...");
            List<User> users = database.getUsers(count);
            if (users.isEmpty()) {
                System.out.print("Database is empty. Fallback to local source...");
                return localSource.getUsers(count);
            }
            return users;
        }
    }
}
