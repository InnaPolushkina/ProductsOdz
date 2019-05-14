package com.polushkina.inna.model.db;

import com.polushkina.inna.model.Product;

import java.sql.Date;
import java.util.List;

public interface DAO {
    /**
     * the method for connection to Data Base
     */
    void connect();

    /**
     * the method for disconnection from Data base
     */
    void disconnect();

    /**
     * the method for inserting Product to Data Base
     * @param product
     */
    void addProduct(Product product);

    /**
     * the method for deleting Product from Data Base
     * @param product
     */
    void removeProduct(Product product);

    /**
     * the method for update Product in Data Base
     * @param id
     * @param product
     */
    void updateProduct(int id, Product product);

    /**
     * the method for getting products from Data Base
     * @return
     */
    List<Product> getProducts();

    /**
     * the method for searching product in Data Base by name
     * @param name
     * @return
     */
    List<Product> searchByName(String name);

    /**
     * the method for searching product in Data Base by name and price
     * @param name
     * @return
     */
    List<Product> searchByNameAndPrice(String name, int price);

    /**
     * the method for searching product in Data Base by end of life product
     * @param endOfLife
     * @return
     */
    List<Product> searchByLife(Date endOfLife);
}
