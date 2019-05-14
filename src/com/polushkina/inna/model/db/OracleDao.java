package com.polushkina.inna.model.db;

import com.polushkina.inna.model.Product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OracleDao implements DAO {
    private static final OracleDao instance = new OracleDao();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public static OracleDao getInstance () {
        return instance;
    }


    @Override
    public void connect() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","INNA","12345678");
            if ( !connection.isClosed()) {
                System.out.println("Connection successfully  . . .");
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void disconnect() {
        try {
            if(connection != null) {
                connection.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(resultSet != null) {
                resultSet.close();
            }
        }
        catch (Exception ex) {
            System.out.println("disconnect " + ex);
        }
    }

    @Override
    public void addProduct(Product product) {
        connect();
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO PRODUCTS(ID, NAME, PRODUCER, PRICE, LIFE, COUNT_PRODUCT) VALUES (PRODUCT_SEQ.nextval,?,?,?,?,?)");
            setProductData(product);
            preparedStatement.execute();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        disconnect();
    }

    @Override
    public void removeProduct(Product product) {
        connect();
        try {
            preparedStatement = connection.prepareStatement("DELETE from PRODUCTS where id = ?");
            preparedStatement.setInt(1,product.getId());
            preparedStatement.execute();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        disconnect();
    }

    @Override
    public void updateProduct(int id, Product product) {
        connect();
        try {
            preparedStatement = connection.prepareStatement("UPDATE PRODUCTS SET NAME = ?, PRODUCER = ? , PRICE = ?, LIFE = ?, COUNT_PRODUCT = ? WHERE ID = ?");
            setProductData(product);
            preparedStatement.setInt(6,id);
            setProductData(product);
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }


        disconnect();
    }

    private void setProductData(Product product) throws SQLException {
        preparedStatement.setString(1,product.getName());
        preparedStatement.setString(2,product.getProducer());
        preparedStatement.setInt(3,product.getPrice());
        preparedStatement.setDate(4,product.getLife());
        preparedStatement.setInt(5,product.getCount());
    }

    @Override
    public List<Product> getProducts() {
        connect();
        List<Product> productList = null;
        try {
            productList = new LinkedList<>();
            preparedStatement = connection.prepareStatement("SELECT *from PRODUCTS");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(parseProduct(resultSet));
            }

        }
        catch (SQLException ex) {
            System.out.println(ex);
        }


        disconnect();
        return productList;
    }

    private Product parseProduct(ResultSet resultSet) {
        Product product = null;
        try {
            int id  = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");
            String producer = resultSet.getString("PRODUCER");
            int price = resultSet.getInt("PRICE");
            Date life = resultSet.getDate("LIFE");
            int count = resultSet.getInt("count_product");

            product = new Product(id,name,producer,price,life,count);


        }
        catch (Exception ex) {
            System.out.println(ex);
        }

        return product;
    }

    @Override
    public List<Product> searchByName(String name) {
        connect();
        List<Product> productList = null;
        try {
            productList = new LinkedList<>();
            preparedStatement = connection.prepareStatement("SELECT *from PRODUCTS WHERE NAME = ?");
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(parseProduct(resultSet));
            }

        }
        catch (SQLException ex) {
            System.out.println(ex);
        }


        disconnect();
        return productList;
    }

    @Override
    public List<Product> searchByNameAndPrice(String name, int price) {
        connect();
        List<Product> productList = null;
        try {
            productList = new LinkedList<>();
            preparedStatement = connection.prepareStatement("SELECT *from PRODUCTS WHERE NAME = ? and PRICE<?");
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,price);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(parseProduct(resultSet));
            }

        }
        catch (SQLException ex) {
            System.out.println(ex);
        }


        disconnect();
        return productList;
    }

    @Override
    public List<Product> searchByLife(Date endOfLife) {
        connect();
        List<Product> productList = null;
        try {
            productList = new LinkedList<>();
            preparedStatement = connection.prepareStatement("SELECT *from PRODUCTS WHERE LIFE > ?");
            preparedStatement.setDate(1,endOfLife);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(parseProduct(resultSet));
            }

        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        disconnect();
        return productList;
    }
}
