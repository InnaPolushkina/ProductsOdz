package com.polushkina.inna.controller;


import com.polushkina.inna.model.db.OracleDao;
import com.polushkina.inna.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Date;
import java.util.List;

/**
 * Class Controller have methods for handling users actions
 */

public class Controller {

    @FXML
    private TableView tableAllProductsData;

    @FXML
    private TableColumn<Product, Integer> idProduct;

    @FXML
    private TableColumn<Product, String> nameProduct;

    @FXML
    private TableColumn<Product, String> produceProduct;

    @FXML
    private TableColumn<Product, Integer> priceProduct;

    @FXML
    private TableColumn<Product, Date> lifeProduct;

    @FXML
    private TableColumn<Product, Integer> countProduct;

    private ObservableList<Product> observableList = FXCollections.observableArrayList();

    private ObservableList<Product> searchObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView tableSearchProductsData;

    @FXML
    private TableColumn<Product, Integer> idProductSearch;

    @FXML
    private TableColumn<Product, String> nameProductSerach;

    @FXML
    private TableColumn<Product, String> producerProductSerach;

    @FXML
    private TableColumn<Product, Integer> priceProductSearch;

    @FXML
    private TableColumn<Product, Date> lifeProductSerach;

    @FXML
    private TableColumn<Product, Integer> countProductSearch;


    @FXML
    private Button addButton;

    @FXML
    private TextField nameAddTextField;

    @FXML
    private TextField producerAddTextField;

    @FXML
    private TextField priceAddTextField;

    @FXML
    private TextField lifeAddTextField;

    @FXML
    private TextField countAddTextField;

    @FXML
    private Label errorAddMessageLabel;

    @FXML
    private Button updateButton;

    @FXML
    private TextField nameUpdateTextField;

    @FXML
    private TextField producerUpdateTextField;

    @FXML
    private TextField priceUpdateTextField;

    @FXML
    private TextField lifeUpdateTextField;

    @FXML
    private TextField countUpdateTextField;

    @FXML
    private Label erroUpdatemessageLabel;

    @FXML
    private Button removeButton;

    @FXML
    private TextField nameSearchTextField;

    @FXML
    private TextField priceSearchByPriceTextField;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private Button searchByNameButton;

    @FXML
    private Button searchByPriceButton;

    @FXML
    private Button searchByLifeButton;

    @FXML
    private TextField nameSearchByPriceTextField;

    @FXML
    private Label userMessageLabel;

    @FXML
    private TextField searchByLifeTextField;

    private OracleDao oracleDao = OracleDao.getInstance();

    /**
     * initialize main view
     */
    public void initialize () {
        setEmptyLineToAllUserMes();
        observableList.setAll(oracleDao.getProducts());
        setValuesIntoColumns(idProduct, nameProduct, produceProduct, priceProduct, lifeProduct, countProduct);
        tableAllProductsData.setItems(observableList);
    }

    private void setValuesIntoColumns(TableColumn<Product, Integer> idProduct, TableColumn<Product, String> nameProduct, TableColumn<Product, String> produceProduct, TableColumn<Product, Integer> priceProduct, TableColumn<Product, Date> lifeProduct, TableColumn<Product, Integer> countProduct) {
        idProduct.setCellValueFactory(new PropertyValueFactory<Product,Integer>("id"));
        nameProduct.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        produceProduct.setCellValueFactory(new PropertyValueFactory<Product,String>("producer"));
        priceProduct.setCellValueFactory(new PropertyValueFactory<Product,Integer>("price"));
        lifeProduct.setCellValueFactory(new PropertyValueFactory<Product,Date>("life"));
        countProduct.setCellValueFactory(new PropertyValueFactory<Product,Integer>("count"));
    }

    /**
     * the method for handling adding new product
     * @param event
     */
    @FXML
    void addProduct(ActionEvent event) {
        setEmptyLineToAllUserMes();
        Product product = new Product();
        try {
            setValueIntoProductFromTextField(product, nameAddTextField, producerAddTextField, priceAddTextField, lifeAddTextField, countAddTextField);
        } catch (Exception e) {
            setErrorAddMessage("Please enter correct data");
        }
        //observableList.add(product);
        oracleDao.addProduct(product);
        observableList.setAll(oracleDao.getProducts());
    }

    /**
     * the method for handling removing selected product
     * @param event
     */
    @FXML
    void removeProduct(ActionEvent event) {
        Product removingProduct = observableList.get(selectedRow());
        observableList.remove(removingProduct);
        oracleDao.removeProduct(removingProduct);
    }

    /**
     * the method for getting selected row from table
     * @return number of selected row
     */
    public int selectedRow() {
        TableView.TableViewSelectionModel seltionModel = tableAllProductsData.getSelectionModel();
        ObservableList selectedCells = seltionModel.getSelectedCells();
        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
        int row = tablePosition.getRow();
        return row;
    }

    /**
     * the method for handling editing data selected product
     * @param event
     */
    @FXML
    void updateProduct(ActionEvent event) {
        setEmptyLineToAllUserMes();
        Product product = observableList.get(selectedRow());

        try {
            setValueIntoProductFromTextField(product, nameUpdateTextField, producerUpdateTextField, priceUpdateTextField, lifeUpdateTextField, countUpdateTextField);
        } catch (Exception e) {
            setErrorUpdateMessage("Please enter correct data");
        }
        observableList.set(selectedRow(),product);
        oracleDao.updateProduct(product.getId(),product);
    }

    private void setValueIntoProductFromTextField(Product product, TextField nameUpdateTextField, TextField producerUpdateTextField, TextField priceUpdateTextField, TextField lifeUpdateTextField, TextField countUpdateTextField) throws Exception{

           product.setName(nameUpdateTextField.getText().trim());
           product.setProducer(producerUpdateTextField.getText().trim());
           product.setPrice(Integer.parseInt(priceUpdateTextField.getText()));
           product.setLife(Date.valueOf(lifeUpdateTextField.getText()));
           product.setCount(Integer.parseInt(countUpdateTextField.getText()));

    }

    /**
     * the the method for handling search product by life
     * @param event
     */
    @FXML
    void searchByLife(ActionEvent event) {
        setEmptyLineToAllUserMes();
        List<Product> result = oracleDao.searchByLife(Date.valueOf(searchByLifeTextField.getText()));
        if (result.size() == 0)
            setUserSearchMessage("Did not find any result for this date");
        searchObservableList.setAll(result);
        setValuesIntoColumns(idProductSearch, nameProductSerach, producerProductSerach, priceProductSearch, lifeProductSerach, countProductSearch);
        tableSearchProductsData.setItems(searchObservableList);

    }

    /**
     * the the method for handling search product by name
     * @param event
     */
    @FXML
    void searchByName(ActionEvent event) {
        setEmptyLineToAllUserMes();
        List<Product> result = oracleDao.searchByName(nameSearchTextField.getText().trim());
        if (result.size() == 0)
            setUserSearchMessage("Did not find any result for this name");
        searchObservableList.setAll(result);
        setValuesIntoColumns(idProductSearch, nameProductSerach, producerProductSerach, priceProductSearch, lifeProductSerach, countProductSearch);
        tableSearchProductsData.setItems(searchObservableList);
    }

    /**
     * the the method for handling search product by name and price
     * @param event
     */
    @FXML
    void searchByNameAndPrice(ActionEvent event) {
        setEmptyLineToAllUserMes();
        try {
            List<Product> res = oracleDao.searchByNameAndPrice(nameSearchByPriceTextField.getText().trim(), Integer.parseInt(priceSearchByPriceTextField.getText()));
            searchObservableList.setAll(res);
            if (res.size() == 0)
                setUserSearchMessage("Did not find any result for this name and price");
            setValuesIntoColumns(idProductSearch, nameProductSerach, producerProductSerach, priceProductSearch, lifeProductSerach, countProductSearch);
            tableSearchProductsData.setItems(searchObservableList);
        }
        catch (Exception ex) {
            setErrorSearchMessage("Please enter correct data");
        }
    }



    private void setErrorAddMessage (String errorAddMessage) {
        errorAddMessageLabel.setText(errorAddMessage);
    }

    private void setErrorUpdateMessage (String errorUpdateMessage) {
        erroUpdatemessageLabel.setText(errorUpdateMessage);
    }

    private void setErrorSearchMessage (String errorSearchMessage) {
        errorMessageLabel.setText(errorSearchMessage);
    }
    private void setUserSearchMessage (String userSearchMessage) {
        userMessageLabel.setText(userSearchMessage);
    }


    public void setDataIntoUpdateField(MouseEvent mouseEvent) {
       Product pr = observableList.get(selectedRow());
       nameUpdateTextField.setText(pr.getName());
       producerUpdateTextField.setText(pr.getProducer());
       priceUpdateTextField.setText(String.valueOf(pr.getPrice()));
       lifeUpdateTextField.setText(String.valueOf(pr.getLife()));
       countUpdateTextField.setText(String.valueOf(pr.getCount()));
    }

    private void setEmptyLineToAllUserMes() {
        setUserSearchMessage("");
        setErrorSearchMessage("");
        setErrorAddMessage("");
        setErrorUpdateMessage(" ");
    }
}
