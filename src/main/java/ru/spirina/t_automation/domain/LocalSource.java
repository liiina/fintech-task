package ru.spirina.t_automation.domain;

import ru.spirina.t_automation.model.User;
import ru.spirina.t_automation.presentation.UserDataContainer;
import ru.spirina.t_automation.presentation.UserDataMapper;

import java.util.List;

public class LocalSource {

    public List<User> getUsers(int count) {
        UserDataContainer container = new UserDataContainer();
        container.readFileParameters();
        return UserDataMapper.mapFromRawData(container, count);
    }
}
