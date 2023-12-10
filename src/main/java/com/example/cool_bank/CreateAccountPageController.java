package com.example.cool_bank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;
import java.io.IOException;
import java.util.Properties;

public class CreateAccountPageController {



    public TextField email;
    public TextField password;
    public TextField ssn;
    public TextField dep;
    private Scene scene;
    private Parent root;

    // Reference to the primary stage (optional)
    private Stage primaryStage;

    // Set the primaryStage (optional)
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    protected void onCreateAccountButtonClick(ActionEvent event) {
        Connection con = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "idjN42069");
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coolbank", connectionProps);

            try (Statement stmt = con.createStatement()) {

                String moneyField = String.valueOf(dep.getText());
                if (moneyField.length() == 2){

                    moneyField = "0." + moneyField;
                }
                else if (moneyField.length() == 1){

                    moneyField = "0.0" + moneyField;
                }
                else{
                    String t1 = moneyField.substring(0, moneyField.length()-2);
                    String t2 = moneyField.substring(moneyField.length()-2);
                    moneyField = t1 +"." + t2;

                }


                String query = "insert into user Values( '" +String.valueOf(email.getText())+ "', '"+String.valueOf(password.getText())+ "', '"+String.valueOf(ssn.getText())+ "', "+ moneyField +", 0, 0);";
                System.out.println(query);

                stmt.executeUpdate(query);
                try {
                    root = FXMLLoader.load(getClass().getResource("Login_Page.fxml"));
                    primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root, 1080, 824);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("Login_Page.fxml"));
            primaryStage =(Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1080, 824);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
