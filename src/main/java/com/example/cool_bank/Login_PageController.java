package com.example.cool_bank;

import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.sql.*;

import java.io.IOException;
import java.util.Properties;

public class Login_PageController {

    public Label Error_Field;
    public ImageView imageView;
    // Reference to the primary stage
    private Stage primaryStage;

    private Scene scene;
    private Parent root;
    public TextField UsernameField;
    public PasswordField Password_Field;

    // Set the primaryStage
    public void setPrimaryStage(Stage newPrimaryStage) {

        imageView.setVisible(true);
        this.primaryStage = newPrimaryStage;

    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void onCreateAccountButtonClick(ActionEvent event) {


        try {
            root = FXMLLoader.load(getClass().getResource("CreateAccountPage.fxml"));
            primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1080, 824);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    @FXML
    protected void onLoginClick(ActionEvent event) {
        if (UsernameField.getText() == null) {
            System.out.println("No username");
        } else {
            Connection con = null;
            Properties connectionProps = new Properties();
            connectionProps.put("user", "root");
            connectionProps.put("password", "idjN42069");
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coolbank", connectionProps);

                try (Statement stmt = con.createStatement()) {


                    String query = "SELECT u.password, u.ssn FROM user u WHERE u.email = '".concat( String.valueOf(UsernameField.getText())) + "';";
                    System.out.println(query);
                    System.out.println(String.valueOf(UsernameField.getText()));
                    ResultSet rs = stmt.executeQuery(query);
                    rs.next();
                    DataHolder accnt = new DataHolder();
                    accnt.setData(String.valueOf(rs.getString("ssn")));
                    System.out.println(rs.getString(1));
                    if (Objects.equals(String.valueOf(Password_Field.getText()), rs.getString(1))) {

                        String query2 = "SELECT u.is_admin FROM user u WHERE u.email = '".concat(String.valueOf(UsernameField.getText())) + "';";
                        ResultSet res = stmt.executeQuery(query2);
                        res.next();
                        System.out.println(res.getInt(1));

                        // If admin
                        if (res.getInt(1) == 1) {
                            try {

                                root = FXMLLoader.load(getClass().getResource("AdminAccountPage.fxml"));
                                primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                scene = new Scene(root, 1080, 824);
                                primaryStage.setScene(scene);
                                primaryStage.show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                        // If regular user
                        else {
                            try {
                                root = FXMLLoader.load(getClass().getResource("UserAccountPage.fxml"));
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
                        Error_Field.setText("Incorrect Username or Password");
                    }
                } catch (SQLException e) {
                    Error_Field.setText("Incorrect Username or Password");
                    System.out.println("Imma Dunce");
                    System.out.println(String.valueOf(Password_Field.getText()));
                    e.printStackTrace(System.out);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
    }
    @FXML
    protected void onPasswordChangeButtonClick(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
            primaryStage =(Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1080, 824);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void testSQL() throws SQLException {
        Connection con = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "idjN42069");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coolbank", connectionProps);
        System.out.println("Email: "  + ", balance: " );
        try (Statement stmt = con.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM User");
            while (rs.next()) {
                String email = rs.getString("email");
                int balance = rs.getInt("balance");
                System.out.println("Email: " + email + ", balance: " + String.valueOf(balance));
            }
        } catch (SQLException e) {
            System.out.println("Imma Dunce" );
            e.printStackTrace(System.out);
        }
    }


}