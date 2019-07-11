package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SalesOrderDAO
{

     Order loadDataFromDB(ResultSet resultSet) throws SQLException;

}