package ru.spirina.t_automation.sql;

import ru.spirina.t_automation.model.User;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static ru.spirina.t_automation.util.UserUtils.DATE_PATTERN;

public class DataObjectToUserMapper {

    public static List<User> map(List<AddressDO> addresses, List<PersonDO> persons) {
        List<User> users = new ArrayList<>();
        for (PersonDO person : persons) {
            User user = new User();
            user.setName(person.getName());
            user.setSecondname(person.getMiddlename());
            user.setSurname(person.getSurname());
            user.setSex(person.getGender());
            user.setInn(person.getInn());
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
            user.setDateofbirth(formatter.format(person.getBirthday()));
            user.setAge(Period.between(person.getBirthday().toLocalDate(), LocalDate.now()).getYears());
            AddressDO address = getAddress(person.getAddress_id(), addresses);
            if (address != null) {
                user.setPostalcode(Integer.valueOf(address.getPostcode()));
                user.setCountry(address.getCountry());
                user.setRegion(address.getRegion());
                user.setCity(address.getCity());
                user.setStreet(address.getStreet());
                user.setHouse(address.getHouse());
                user.setFlat(address.getFlat());
            }
            users.add(user);
        }
        return users;
    }

    private static AddressDO getAddress(int id, List<AddressDO> addresses) {
        for (AddressDO address : addresses) {
            if (address.getId() == id) {
                return address;
            }
        }
        return null;
    }
}
