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
import java.util.Objects;
import java.util.Properties;

public class ChangePasswordController {


    public TextField SSDField;
    public PasswordField P1Field;
    public PasswordField P2Field;
    private Stage primaryStage;

    private Scene scene;
    private Parent root;

    // Set the primaryStage
    public void setPrimaryStage(Stage newPrimaryStage) {
        this.primaryStage = newPrimaryStage;
    }

    @FXML
    protected void onPasswordChangeButtonClick(ActionEvent event) throws IOException {
        Connection con = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "idjN42069");
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coolbank", connectionProps);

            try (Statement stmt = con.createStatement()) {


                String query = "SELECT u.ssn FROM user u WHERE u.ssn = '".concat(String.valueOf(SSDField.getText())) + "';";
                System.out.println(query);
                System.out.println(String.valueOf(SSDField.getText()));
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()){
                    if (Objects.equals(String.valueOf(P1Field.getText()), String.valueOf(P2Field.getText()))){
                        String query1 = "Update user SET password = '" + String.valueOf(P1Field.getText()) + "' where ssn = '" + rs.getString(1) + "';";
                        System.out.println(query1);
                        stmt.executeUpdate(query1);

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
                }
                else{
                    System.out.println("invalid account");
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
            primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1080, 824);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}


