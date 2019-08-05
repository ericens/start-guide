package mock.dao.impl;

import mock.dao.Order;
import mock.dao.SalesOrderDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesOrderDAOImpl implements SalesOrderDAO
{

    @Override
    public Order loadDataFromDB(ResultSet resultSet) throws SQLException
    {
        String orderNumber = resultSet.getString(1);
        String region = resultSet.getString(2);
        Double totalPrice = resultSet.getDouble(3);
        Order order=new Order(orderNumber,region,totalPrice);
        return order;

    }

}