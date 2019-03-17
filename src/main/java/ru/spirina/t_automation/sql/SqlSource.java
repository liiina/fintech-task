package ru.spirina.t_automation.sql;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import ru.spirina.t_automation.model.User;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static ru.spirina.t_automation.sql.SqlConfiguration.DB_PASSWORD;
import static ru.spirina.t_automation.sql.SqlConfiguration.DB_USERNAME;
import static ru.spirina.t_automation.sql.SqlOperation.CREATE_ADDRESS_TABLE;
import static ru.spirina.t_automation.sql.SqlOperation.CREATE_PERSONS_TABLE;
import static ru.spirina.t_automation.sql.SqlOperation.DB_CONNECTION_URL;
import static ru.spirina.t_automation.sql.SqlOperation.INSERT_ADDRESS;
import static ru.spirina.t_automation.sql.SqlOperation.INSERT_PERSON;
import static ru.spirina.t_automation.sql.SqlOperation.SELECT_ADDRESS;
import static ru.spirina.t_automation.sql.SqlOperation.SELECT_PERSON_LIMIT;
import static ru.spirina.t_automation.util.UserUtils.DATE_PATTERN;

public class SqlSource {

    private MysqlDataSource dataSource;
    private QueryRunner runner;

    public SqlSource() {
        dataSource = new MysqlDataSource();
        runner = new QueryRunner();
        dataSource.setURL(DB_CONNECTION_URL);
        dataSource.setUser(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        createTables();
    }

    public void createTables() {
        try {
            Statement statement = dataSource.getConnection().createStatement();
            statement.executeUpdate(CREATE_ADDRESS_TABLE);
            statement.executeUpdate(CREATE_PERSONS_TABLE);
            statement.close();
        } catch (SQLException error) {
            System.out.println("Error while creating database tables: " + error);
        }
    }

    public void insertOrUpdate(List<User> users) {
        try {
            for (User user : users) {
                ScalarHandler<BigInteger> scalarHandler = new ScalarHandler<>();
                BigInteger addressId = runner.insert(
                        dataSource.getConnection(),
                        INSERT_ADDRESS,
                        scalarHandler,
                        user.getPostalcode(),
                        user.getCountry(),
                        user.getRegion(),
                        user.getCity(),
                        user.getStreet(),
                        user.getHouse(),
                        user.getFlat());
                DateFormat format = new SimpleDateFormat(DATE_PATTERN);
                runner.insert(
                        dataSource.getConnection(),
                        INSERT_PERSON,
                        scalarHandler,
                        user.getSurname(),
                        user.getName(),
                        user.getSecondname(),
                        format.parse(user.getDateofbirth()),
                        user.getSex(),
                        user.getInn(),
                        addressId);
            }
        } catch (SQLException | ParseException error) {
            System.out.println("Error while inserting to database: " + error);
        }
    }

    public List<User> getUsers(int count) {
        try {
            BeanListHandler<AddressDO> addressBeanListHandler = new BeanListHandler<>(AddressDO.class);
            BeanListHandler<PersonDO> personBeanListHandler = new BeanListHandler<>(PersonDO.class);
            List<AddressDO> addresses = runner.query(dataSource.getConnection(), SELECT_ADDRESS, addressBeanListHandler);
            List<PersonDO> persons = runner.query(dataSource.getConnection(), String.format(SELECT_PERSON_LIMIT, count), personBeanListHandler);
            return DataObjectToUserMapper.map(addresses, persons);
        } catch (SQLException error) {
            System.out.println("Error while fetching data from database: " + error);
            return new ArrayList<>();
        }
    }
}
