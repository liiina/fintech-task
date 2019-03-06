package ru.spirina.t_automation.util;

public class UserUtils {

    public static final String DATE_PATTERN = "dd-MM-yyyy";
    public static final String REMOTE_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /* Генератор инн от http://mellarius.ru/random-inn */
    public static String generateInn() {
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
}
