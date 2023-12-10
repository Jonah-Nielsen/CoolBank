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

public class AdminAccountPageController {

    public TextField AccountField;
    public TextField MoneyTransferField;
    public TextField TransferToAccountField;
    public TextField TransferFromAccountField;
    private Stage primaryStage;

    private Scene scene;
    private Parent root;

    String accnt = DataHolder.getData();

    // Set the primaryStage
    public void setPrimaryStage(Stage newPrimaryStage) {
        this.primaryStage = newPrimaryStage;
    }
    @FXML
    protected void onLogOutClick(ActionEvent event) {
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

    public void onTransferClick(ActionEvent event) {
        Connection con = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "idjN42069");
        try {

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coolbank", connectionProps);
            Statement stmt = con.createStatement();
            CallableStatement statement = con.prepareCall("{CALL makePayment(?, ?, ?)}");
            String query = "SELECT u.balance from user u where ssn = '"+ String.valueOf(TransferFromAccountField.getText()) +"';";
            String txtField = String.valueOf(MoneyTransferField.getText());
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            if (txtField.contains(".")){
                String[] arr = txtField.split("\\.");
                txtField = arr[0] + arr[1];
            }
            else{
                txtField = txtField +"00";
            }
            if(rs.getInt(1) >= Integer.valueOf(txtField)){
                statement.setString(1, txtField);
                statement.setString(2, String.valueOf(TransferFromAccountField.getText()));
                statement.setString(3, String.valueOf(TransferToAccountField.getText()));
                ResultSet resultSet = statement.executeQuery();
                MoneyTransferField.setText("");
                TransferFromAccountField.setText("");
                TransferToAccountField.setText("");
            }





        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDeleteAccount(ActionEvent event) {
        Connection con = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "idjN42069");
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coolbank", connectionProps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Statement stmt = con.createStatement()) {

            String query = "DELETE FROM user WHERE ssn = '" + String.valueOf(AccountField.getText()) +"';";
            System.out.println(query);
            stmt.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println("Imma Dunce" );
            e.printStackTrace(System.out);
        }
    }

    public void onMakeAdmin(ActionEvent event) {
        Connection con = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "idjN42069");
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coolbank", connectionProps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Statement stmt = con.createStatement()) {

            String query = "Update user SET is_admin = 1 WHERE ssn = '" + String.valueOf(AccountField.getText()) +"';";
            System.out.println(query);
            stmt.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println("Imma Dunce" );
            e.printStackTrace(System.out);
        }
    }

    public void onChangePasswordClick(ActionEvent event) {
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
}
