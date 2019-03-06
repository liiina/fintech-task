package ru.spirina.t_automation.domain;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.spirina.t_automation.model.User;
import ru.spirina.t_automation.presentation.UserDataMapper;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.List;

public class NetworkSource {

    private static final String URL = "https://randomuser.me/api";

    public List<User> getUsers(int count) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL + "?results=" + count)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.body() != null) {
            String jsonData = response.body().string();
            return UserDataMapper.mapFromJsonString(jsonData);
        } else {
            throw new InvalidObjectException("Response has empty body");
        }
    }
}
