package ru.spirina.t_automation.sql;

public class SqlOperation {

    public static final String DB_CONNECTION_URL = "jdbc:mysql://" + SqlConfiguration.DB_SERVER + ":" + SqlConfiguration.DB_PORT + "/" + SqlConfiguration.DB_NAME +
            "?verifyServerCertificate=false" +
            "&useSSL=false" +
            "&requireSSL=false" +
            "&useLegacyDatetimeCode=false" +
            "&serverTimezone=Europe/Moscow";

    public static final String CREATE_ADDRESS_TABLE = "create table if not exists address (" +
            "id int auto_increment not null," +
            "postcode varchar(256)," +
            "country varchar(256)," +
            "region varchar(256)," +
            "city varchar(256)," +
            "street varchar(256)," +
            "house int," +
            "flat int," +
            "primary key (id))";

    public static final String CREATE_PERSONS_TABLE = "create table if not exists persons (" +
            "id int auto_increment not null," +
            "surname varchar(256)," +
            "name varchar(256)," +
            "middlename varchar(256)," +
            "birthday date," +
            "gender varchar(1)," +
            "inn varchar(12)," +
            "address_id int not null," +
            "foreign key (address_id) references address(id)," +
            "primary key (id)," +
            "unique key full_name(surname, name, birthday))";

    public static final String INSERT_ADDRESS = "insert into " +
            "address(postcode, country, region, city, street, house, flat) " +
            "values (?, ?, ?, ?, ?, ?, ?)";

    public static final String INSERT_PERSON = "insert into " +
            "persons(surname, name, middlename, birthday, gender, inn, address_id) " +
            "values (?, ?, ?, ?, ?, ?, ?) " +
            "on duplicate key update inn=values(inn), address_id=values(address_id)";

    public static final String SELECT_PERSON_LIMIT = "select * from persons order by id desc limit %d";

    public static final String SELECT_ADDRESS = "select * from address";
}
