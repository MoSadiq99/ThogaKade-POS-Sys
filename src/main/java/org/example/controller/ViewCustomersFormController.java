package org.example.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.db.DBConnection;
import org.example.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewCustomersFormController implements Initializable {

    @FXML
    private JFXButton btnReloadList;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colAge;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));

        loadTable();
    }

    @FXML
    private void loadTable(){
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        List<Customer> dbList = DBConnection.getInstance().getConnection();

        dbList.forEach(obj->{
            customerObservableList.add(obj);
        });

        tblCustomers.setItems(customerObservableList);

        
    }

    @FXML
    public void OnClickBtnGoBack(ActionEvent actionEvent) {

        try {
            // Load the FXML file for the Dashboard form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dash_form.fxml"));
            Parent dashboardForm = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(dashboardForm));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
}
