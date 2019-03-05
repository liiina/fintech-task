package ru.spirina.t_automation.presentation;

import java.util.Arrays;
import java.util.List;

import ru.spirina.t_automation.model.DataFile;

public class UserDataContainer {

    public static final String SEX_MALE = "М";
    public static final String SEX_FEMALE = "Ж";

    private List<String> manNames;
    private List<String> womanNames;
    private List<String> womanSecondNames;
    private List<String> manSecondNames;
    private List<String> womanSurnames;
    private List<String> manSurnames;
    private List<String> cities;
    private List<String> countries;
    private List<String> regions;
    private List<String> sex;
    private List<String> streets;

    public List<String> getManNames() {
        return manNames;
    }

    public List<String> getWomanNames() {
        return womanNames;
    }

    public List<String> getWomanSecondNames() {
        return womanSecondNames;
    }

    public List<String> getManSecondNames() {
        return manSecondNames;
    }

    public List<String> getWomanSurnames() {
        return womanSurnames;
    }

    public List<String> getManSurnames() {
        return manSurnames;
    }

    public List<String> getCities() {
        return cities;
    }

    public List<String> getCountries() {
        return countries;
    }

    public List<String> getRegions() {
        return regions;
    }

    public List<String> getSex() {
        return sex;
    }

    public List<String> getStreets() {
        return streets;
    }

    public void init() {
        UserDataReader reader = new UserDataReader();
        manNames = reader.readStringParameters(DataFile.FILE_MAN_NAMES);
        womanNames = reader.readStringParameters(DataFile.FILE_WOMAN_NAMES);
        womanSecondNames = reader.readStringParameters(DataFile.FILE_WOMAN_SECONDNAMES);
        manSecondNames = reader.readStringParameters(DataFile.FILE_MAN_SECONDNAMES);
        womanSurnames = reader.readStringParameters(DataFile.FILE_WOMAN_SURNAMES);
        manSurnames = reader.readStringParameters(DataFile.FILE_MAN_SURNAMES);
        cities = reader.readStringParameters(DataFile.FILE_CITIES);
        countries = reader.readStringParameters(DataFile.FILE_COUNTRIES);
        regions = reader.readStringParameters(DataFile.FILE_REGIONS);
        streets = reader.readStringParameters(DataFile.FILE_STREETS);
        sex = Arrays.asList(SEX_MALE, SEX_FEMALE);
    }
}
