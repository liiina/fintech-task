package ru.spirina.t_automation.presentation;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import ru.spirina.t_automation.model.User;

public class UserDataMapper {

    private static final String DATE_PATTERN = "dd-MM-yyyy";

    public static List<User> mapFromRawData(UserDataContainer container, int limit) {
        List<User> users = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        for (int i = 0; i < limit; i++) {
            User user = new User();
            String sex = container.getSex().get(ThreadLocalRandom.current().nextInt(container.getSex().size()));
            user.setSex(sex);
            user.setName(getRandomStringFromCollection(UserDataContainer.SEX_MALE.equals(sex)
                                                       ? container.getManNames()
                                                       : container.getWomanNames()));
            user.setSurname(getRandomStringFromCollection(UserDataContainer.SEX_MALE.equals(sex)
                                                          ? container.getManSurnames()
                                                          : container.getWomanSurnames()));
            user.setSecondname(getRandomStringFromCollection(UserDataContainer.SEX_MALE.equals(sex)
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

    private static String getRandomStringFromCollection(List<String> list) {
        if (list != null && !list.isEmpty()) {
            return list.get(ThreadLocalRandom.current().nextInt(list.size()));
        } else {
            return "";
        }
    }

    /* Генератор инн от http://mellarius.ru/random-inn */
    private static String generateInn() {
        /* NNNN (4 знака): для российских организаций и физических лиц - код налогового органа, который присвоил ИНН; */
        String region = "77";
        String inspection = addZeros((int) (Math.floor((Math.random() * 99) + 1)), 2);

        /* XXXXX (XXXXXX): для российской организации (физического лица) - порядковый номер записи о лице в территориальном разделе Единого государственного реестра налогоплательщиков (далее - ЕГРН) налогового органа, который присвоил ИНН, - 5 знаков - для организации (6 знаков - для физического лица); */
        String numba = addZeros(((int) Math.floor((Math.random() * 999999) + 1)), 6);

        String full = region + inspection + numba;
        char[] fullChars = full.toCharArray();
        int[] rezult = new int[fullChars.length + 2];
        for (int i = 0; i < fullChars.length; i++) {
            rezult[i] = Character.getNumericValue(fullChars[i]);
        }

        /* C (CC) - контрольное число (1 знак - для организации, 2 знака - для физического лица), рассчитанное по специальному алгоритму, установленному Федеральной налоговой службой.*/
        int kontr = ((
                7 * rezult[0] + 2 * rezult[1] + 4 * rezult[2] +
                        10 * rezult[3] + 3 * rezult[4] + 5 * rezult[5] +
                        9 * rezult[6] + 4 * rezult[7] + 6 * rezult[8] +
                        8 * rezult[9]
        ) % 11) % 10;
        rezult[10] = kontr;
        kontr = ((
                3 * rezult[0] + 7 * rezult[1] + 2 * rezult[2] +
                        4 * rezult[3] + 10 * rezult[4] + 3 * rezult[5] +
                        5 * rezult[6] + 9 * rezult[7] + 4 * rezult[8] +
                        6 * rezult[9] + 8 * rezult[10]
        ) % 11) % 10;
        rezult[11] = kontr;
        StringBuilder innBuilder = new StringBuilder();
        for (int i : rezult) {
            innBuilder.append(i);
        }

        return innBuilder.toString();
    }

    /* Добавляет 0 к строке с начала и расширяет длину строки до length */
    private static String addZeros(int number, int length) {
        return String.format("%0" + length + "d", number);
    }

    private static LocalDate generateDateOfBirth() {
        long startDateMillis = LocalDate.of(1970, 1, 1).toEpochDay();
        long endDateMillis = LocalDate.of(2001, 1, 1).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startDateMillis, endDateMillis);
        return LocalDate.ofEpochDay(randomDay);
    }
}
