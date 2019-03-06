package ru.spirina.t_automation.domain;

import ru.spirina.t_automation.model.User;

import java.io.IOException;
import java.util.List;

public class UsersRepository {

    private final LocalSource localSource = new LocalSource();
    private final NetworkSource remoteSource = new NetworkSource();

    public List<User> fetchUsers(int count) {
        try {
            return remoteSource.getUsers(count);
        } catch (IOException exception) {
            System.out.println("Error while fetching config from remote: " + exception);
            return localSource.getUsers(count);
        }
    }
}
