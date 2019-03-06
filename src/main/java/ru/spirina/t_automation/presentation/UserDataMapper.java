package ru.spirina.t_automation.presentation;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.spirina.t_automation.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import static ru.spirina.t_automation.model.RemoteField.*;
import static ru.spirina.t_automation.presentation.UserDataContainer.SEX_FEMALE;
import static ru.spirina.t_automation.presentation.UserDataContainer.SEX_MALE;
import static ru.spirina.t_automation.util.UserUtils.*;

public class UserDataMapper {

    public static List<User> mapFromRawData(UserDataContainer container, int limit) {
        List<User> users = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        for (int i = 0; i < limit; i++) {
            User user = new User();
            String sex = container.getSex().get(ThreadLocalRandom.current().nextInt(container.getSex().size()));
            user.setSex(sex);
            user.setName(getRandomStringFromCollection(SEX_MALE.equals(sex)
                    ? container.getManNames()
                    : container.getWomanNames()));
            user.setSurname(getRandomStringFromCollection(SEX_MALE.equals(sex)
                    ? container.getManSurnames()
                    : container.getWomanSurnames()));
            user.setSecondname(getRandomStringFromCollection(SEX_MALE.equals(sex)
                    ? container.getManSecondNames()
                    : container.getWomanSecondNames()));
            user.setPostalcode(ThreadLocalRandom.current().nextInt(100000, 200001));
            user.setCountry(getRandomStringFromCollection(container.getCountries()));
            user.setRegion(getRandomStringFromCollection(container.getRegions()));
            user.setCity(getRandomStringFromCollection(container.getCities()));
            user.setStreet(getRandomStringFromCollection(container.getStreets()));
            user.setHouse(ThreadLocalRandom.current().nextInt(1, 101));
            user.setFlat(ThreadLocalRandom.current().nextInt(1, 201));
            user.setInn(generateInn());
            LocalDate birthDate = generateDateOfBirth();
            user.setDateofbirth(birthDate.format(formatter));
            user.setAge(Period.between(birthDate, LocalDate.now()).getYears());
            users.add(user);
        }
        return users;
    }

    public static List<User> mapFromJsonString(String jsonData) {
        JSONObject rootJson = new JSONObject(jsonData);
        JSONArray jsonArray = rootJson.getJSONArray(FIELD_RESULTS);
        List<User> users = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject userObj = jsonArray.getJSONObject(i);
            User user = new User();

            String gender = userObj.getString(FIELD_GENDER);
            if (GENDER_MALE.equals(gender)) {
                user.setSex(SEX_MALE);
            } else {
                user.setSex(SEX_FEMALE);
            }

            JSONObject nameObj = userObj.getJSONObject(FIELD_NAME);
            user.setName(nameObj.getString(FIELD_FIRST));
            user.setSurname(nameObj.getString(FIELD_LAST));

            JSONObject locObj = userObj.getJSONObject(FIELD_LOCATION);
            user.setStreet(locObj.getString(FIELD_STREET));
            user.setCity(locObj.getString(FIELD_CITY));
            user.setRegion(locObj.getString(FIELD_STATE));

            String nat = userObj.getString(FIELD_NATIONALITY);
            Locale locale = new Locale("", nat);
            user.setCountry(locale.getDisplayCountry());

            DateFormat inputFormat = new SimpleDateFormat(REMOTE_DATE_PATTERN);
            DateFormat outputFormat = new SimpleDateFormat(DATE_PATTERN);
            JSONObject dobObj = userObj.getJSONObject(FIELD_DATE_OF_BIRTH);
            String dateOfBirth = dobObj.getString(FIELD_DATE);
            try {
                Date date = inputFormat.parse(dateOfBirth);
                user.setDateofbirth(outputFormat.format(date));
            } catch (ParseException exception) {
                System.out.println("Warning: parse exception: " + exception);
                user.setDateofbirth("");
            }
            user.setAge(dobObj.getInt(FIELD_AGE));

            user.setHouse(ThreadLocalRandom.current().nextInt(1, 101));
            user.setFlat(ThreadLocalRandom.current().nextInt(1, 201));
            user.setInn(generateInn());
            user.setPostalcode(ThreadLocalRandom.current().nextInt(100000, 200001));

            users.add(user);
        }
        return users;
    }

    private static String getRandomStringFromCollection(List<String> list) {
        if (list != null && !list.isEmpty()) {
            return list.get(ThreadLocalRandom.current().nextInt(list.size()));
        } else {
            return "";
        }
    }

    private static LocalDate generateDateOfBirth() {
        long startDateMillis = LocalDate.of(1970, 1, 1).toEpochDay();
        long endDateMillis = LocalDate.of(2001, 1, 1).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startDateMillis, endDateMillis);
        return LocalDate.ofEpochDay(randomDay);
    }
}
