package com.m2p.fileprocessor.database;

import com.m2p.fileprocessor.UserDTO.UserDTO;
import com.m2p.fileprocessor.config.ConfigProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseConnection {

  private static final String INSERT_SQL =
      "INSERT INTO `sys`.`USER` (`ID`, `Name`, `Dob`, `Age`, `MobileNumber`) VALUES (?,?,?,?,?)";
  private static Logger log = Logger.getLogger(DatabaseConnection.class.getSimpleName());
  private static DatabaseConnection instance;
  private static Connection connection;
  @Autowired ConfigProperties configProp;
  private String datasourceURL =
      "jdbc:mysql://localhost:3306/sys"; // configProp.getConfigValue("spring.datasource.url");
  private String dbUserName = "root"; // configProp.getConfigValue("spring.datasource.username");
  private String dbPassword =
      "root1234"; // configProp.getConfigValue("spring.datasource.password");
  private String jdbcDriver =
      "com.mysql.cj.jdbc.Driver"; // configProp.getConfigValue("jdbc.driver");

  private DatabaseConnection() {}

  public static synchronized DatabaseConnection getInstance() {

    if (instance == null) {
      instance = new DatabaseConnection();
    }
    return instance;
  }

  public List<Object> getUsers() {

    List<Object> userDTOs = new ArrayList<>();

    connection = DatabaseConnection.getInstance().initiateDatabaseConnection();
    String query = "SELECT * FROM `sys`.`USER`";
    try (Statement statement = connection.createStatement()) {
      ResultSet rs = statement.executeQuery(query);

      log.info(String.valueOf(rs.getFetchSize()));

      int i = 0;
      while (rs.next()) {

        UserDTO userDTO =
            UserDTO.setDTO(
                i++,
                rs.getString("Name"),
                rs.getString("Dob"),
                rs.getInt("Age"),
                rs.getLong("MobileNumber"));

        userDTOs.add(userDTO);
      }

      return userDTOs;
    } catch (SQLException e) {
      e.printStackTrace();
      log.severe(e.getMessage());
    }
    return userDTOs;
  }

  public Connection initiateDatabaseConnection() {
    try {
      Class.forName(jdbcDriver);
      connection = DriverManager.getConnection(datasourceURL, dbUserName, dbPassword);
      return connection;
    } catch (Exception e) {
      log.severe(e.getMessage());
    }
    return connection;
  }

  public void createTable() {
    try {
      connection = DatabaseConnection.getInstance().initiateDatabaseConnection();
      Statement stmt = connection.createStatement();

      String sql =
          "CREATE TABLE `sys`.`USER` (\n"
              + "  `ID` INT NOT NULL,\n"
              + "  `Name` VARCHAR(45) NULL,\n"
              + "  `Dob` VARCHAR(45) NULL,\n"
              + "  `Age` INT NULL,\n"
              + "  `MobileNumber` BIGINT NULL,\n"
              + "  PRIMARY KEY (`ID`))";

      stmt.executeUpdate(sql);

    } catch (Exception e) {
      log.severe(e.getMessage());
      e.printStackTrace();
    }
  }

  public void insertingUsersIntoDB(int id, String name, String dob, long age, long mobileNumber) {
    PreparedStatement preparedStatement = null;
    try {
      connection = DatabaseConnection.getInstance().initiateDatabaseConnection();
      preparedStatement = connection.prepareStatement(INSERT_SQL);

      preparedStatement.setInt(1, id);
      preparedStatement.setString(2, name);
      preparedStatement.setString(3, dob);
      preparedStatement.setInt(4, Integer.parseInt(String.valueOf(age)));
      preparedStatement.setLong(5, Long.parseLong(String.valueOf(mobileNumber)));
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      log.severe(e.getMessage());
      e.printStackTrace();
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (preparedStatement != null) {
          connection.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
