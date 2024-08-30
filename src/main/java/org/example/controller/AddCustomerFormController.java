package org.example.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import org.example.db.DBConnection;
import org.example.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {
    @FXML
    public JFXTextField textID;
    @FXML
    public JFXTextField txtName;
    @FXML
    public JFXTextField txtAge;
    @FXML
    public JFXTextField txtContact;
    @FXML
    public JFXTextField txtAddress;
    private int id = 1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Customer> customerList = DBConnection.getInstance().getConnection();
        if ( !customerList.isEmpty() ) {
            String lastId = customerList.getLast().getId();
            id = Integer.parseInt(lastId.substring(1)) + 1;
            textID.setText(generateId());
        } else {
            textID.setText(generateId());
        }

        // Setting tooltips for each text field
        txtName.setTooltip(new Tooltip("Name"));
        txtAge.setTooltip(new Tooltip("Age"));
        txtContact.setTooltip(new Tooltip("Contact"));
        txtAddress.setTooltip(new Tooltip("Address"));
        textID.setTooltip(new Tooltip("ID"));
    }

    public String generateId() {
        String baseId = "C";
        // Format the ID as a string with leading zeros
        String formattedId = String.format("%s%03d", baseId, id);
        // Increment the ID for the next use
        id++;
        return formattedId;
    }

    @FXML
    public void OnClickBtnSave(ActionEvent actionEvent) {
        try {
            // Convert the string to an integer
            int age = Integer.parseInt(txtAge.getText());

            // Add the new customer to the list with the current ID
            Customer customer = new Customer(
                    textID.getText(),
                    txtName.getText(),
                    age,
                    txtContact.getText(),
                    txtAddress.getText()
            );

            List<Customer> customerList = DBConnection.getInstance().getConnection();
            customerList.add(customer);

            clearTxt();

            // Generate a new ID and set it to the textID field
            String newId = generateId();
            textID.setText(newId);

        } catch (NumberFormatException e) {
            // Handle the exception if age is not a valid integer
            System.err.println("Invalid age input. Please enter a valid number.");
        }
    }

    private void clearTxt() {
        // Clear the text fields
        txtName.clear();
        txtAge.clear();
        txtContact.clear();
        txtAddress.clear();
    }

    @FXML
    public void OnClickBtnClear(ActionEvent actionEvent) {
        // Clear the text fields
        txtName.clear();
        txtAge.clear();
        txtContact.clear();
        txtAddress.clear();
    }

    @FXML
    public void OnClickBtnCancel(ActionEvent actionEvent) {
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
