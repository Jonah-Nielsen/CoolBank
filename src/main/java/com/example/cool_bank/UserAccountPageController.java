package com.example.cool_bank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.sql.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.io.IOException;
import java.util.Properties;
public class UserAccountPageController {
    public Label Lable;
    public Label Balance;
    public TextField PaymentField;
    public VBox TransactionsVbox;
    // Reference to the primary stage (optional)
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

    public void onCheckBalance(ActionEvent event) {
        Connection con = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "idjN42069");
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coolbank", connectionProps);

            try (Statement stmt = con.createStatement()) {


                String query = "SELECT u.balance from user u where ssn = '"+ String.valueOf(accnt) +"';";

                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                String total = String.valueOf(rs.getInt("balance"));
                if (total.length() == 2){

                    total = "0." + total;
                }
                else if (total.length() == 1){

                    total = "0." + total;
                }
                else{
                    String t1 = total.substring(0, total.length()-2);
                    String t2 = total.substring(total.length()-2);
                    total = t1 +"." + t2;

                }
                System.out.println(total);
                Lable.setText("Balance");
                Balance.setText(total);
                PaymentField.setText("");

            }

            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void onCheckLoanBalance (ActionEvent event){
        Connection con = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "idjN42069");
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coolbank", connectionProps);

            try (Statement stmt = con.createStatement()) {


                String query = "SELECT u.loan_balance from user u where ssn = '"+ String.valueOf(accnt) +"';";

                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                String total = String.valueOf(rs.getInt("loan_balance"));
                if (total.length() == 2){

                    total = "0." + total;
                }
                else if (total.length() == 1){

                    total = "0." + total;
                }
                else{
                    String t1 = total.substring(0, total.length()-2);
                    String t2 = total.substring(total.length()-2);
                    total = t1 +"." + t2;

                }
                System.out.println(total);
                Lable.setText("Loan Balance");
                Balance.setText(total);
                PaymentField.setText("");


            }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void onTakeLoan (ActionEvent event){

        Connection con = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "idjN42069");
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coolbank", connectionProps);
            CallableStatement statement = con.prepareCall("{CALL addLoan(?, ?)}");
            String txtField = String.valueOf(PaymentField.getText());
            if (txtField.contains(".")){
                String[] arr = txtField.split("\\.");
                txtField = arr[0] + arr[1];
            }
            else{
                txtField = txtField +"00";
            }
            System.out.println(txtField);
            statement.setString(1, txtField);
            statement.setString(2, accnt);
            ResultSet resultSet = statement.executeQuery();
            String query = "SELECT u.loan_balance from user u where ssn = '"+ String.valueOf(accnt) +"';";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String total = String.valueOf(rs.getInt("loan_balance"));
            if (total.length() == 2){

                total = "0." + total;
            }
            else if (total.length() == 1){

                total = "0." + total;
            }
            else{
                String t1 = total.substring(0, total.length()-2);
                String t2 = total.substring(total.length()-2);
                total = t1 +"." + t2;

            }
            System.out.println(total);
            Lable.setText("Loan Balance");
            Balance.setText(total);
            PaymentField.setText("");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void onMakeDeposit (ActionEvent event){

        Connection con = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "idjN42069");
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coolbank", connectionProps);
            CallableStatement statement = con.prepareCall("{CALL deposit(?, ?)}");
            String txtField = String.valueOf(PaymentField.getText());
            if (txtField.contains(".")){
                String[] arr = txtField.split("\\.");
                txtField = arr[0] + arr[1];
            }
            else{
                txtField = txtField +"00";
            }
            System.out.println(txtField);
            statement.setString(1, txtField);
            statement.setString(2, accnt);
            ResultSet resultSet = statement.executeQuery();
            String query = "SELECT u.balance from user u where ssn = '"+ String.valueOf(accnt) +"';";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String total = String.valueOf(rs.getInt("balance") );
            if (total.length() == 2){

                total = "0." + total;
            }
            else if (total.length() == 1){

                total = "0." + total;
            }
            else{
                String t1 = total.substring(0, total.length()-2);
                String t2 = total.substring(total.length()-2);
                total = t1 +"." + t2;

            }
            System.out.println(total);
            Lable.setText("Balance");
            Balance.setText(total);
            PaymentField.setText("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onWithdraw(ActionEvent event) {
        Connection con = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "idjN42069");
        try {

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coolbank", connectionProps);
            Statement stmt = con.createStatement();
            String query = "SELECT u.balance from user u where ssn = '"+ String.valueOf(accnt) +"';";
            String txtField = String.valueOf(PaymentField.getText());
            if (txtField.contains(".")){
                String[] arr = txtField.split("\\.");
                txtField = arr[0] + arr[1];
            }
            else{
                txtField = txtField +"00";
            }
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            if(rs.getInt(1) >= Integer.valueOf(txtField)) {
                CallableStatement statement = con.prepareCall("{CALL withdraw(?, ?)}");

                System.out.println(txtField);
                statement.setString(1, txtField);
                statement.setString(2, accnt);
                ResultSet resultSet = statement.executeQuery();

                String total = String.valueOf(rs.getInt("balance") - Integer.valueOf(txtField));
                if (total.length() == 2){

                    total = "0." + total;
                }
                else if (total.length() == 1){

                    total = "0." + total;
                }
                else{
                    String t1 = total.substring(0, total.length()-2);
                    String t2 = total.substring(total.length()-2);
                    total = t1 +"." + t2;

                }
                System.out.println(total);
                Lable.setText("Balance");
                Balance.setText(total);
                PaymentField.setText("");
            }
            else{

                Lable.setText("Error");
                Balance.setText("Overdraft");
                PaymentField.setText("");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onMakePayment(ActionEvent event) {
        Connection con = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "idjN42069");
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coolbank", connectionProps);
            CallableStatement statement = con.prepareCall("{CALL payOffLoan(?, ?)}");
            String txtField = String.valueOf(PaymentField.getText());
            if (txtField.contains(".")){
                String[] arr = txtField.split("\\.");
                txtField = arr[0] + arr[1];
            }
            else{
                txtField = txtField +"00";
            }
            System.out.println(txtField);
            statement.setString(1, txtField);
            statement.setString(2, accnt);
            ResultSet resultSet = statement.executeQuery();
            String query = "SELECT u.loan_balance from user u where ssn = '"+ String.valueOf(accnt) +"';";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String total = String.valueOf(rs.getInt("loan_balance"));
            if (total.length() == 2){

                total = "0." + total;
            }
            else if (total.length() == 1){

                total = "0.0" + total;
            }
            else{
                String t1 = total.substring(0, total.length()-2);
                String t2 = total.substring(total.length()-2);
                total = t1 +"." + t2;

            }
            System.out.println(total);
            Lable.setText("Loan Balance");
            Balance.setText(total);
            PaymentField.setText("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onPasswordChange(ActionEvent event) {
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

    public void onCheckTransactions(ActionEvent event) {
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

            ResultSet rs = stmt.executeQuery("SELECT * FROM transaction where sender = '"+ accnt +"' OR recipient = '" + accnt + "';");
            while (rs.next()) {

                String send = rs.getString("sender");
                String recp = rs.getString("recipient");
                String amount = rs.getString("amount");
                String type = rs.getString("type");
                if (amount.length() == 2){

                    amount = "0." + amount;
                }
                else if (amount.length() == 1){

                    amount = "0.0" + amount;
                }
                else {
                    String t1 = amount.substring(0, amount.length() - 2);
                    String t2 = amount.substring(amount.length() - 2);
                    amount = t1 + "." + t2;

                }

                String text = "";
                if (Objects.equals(recp, accnt)){
                    text = type + " : Received : $" + amount;
                }
                else{
                    text = type + " : Sent : $" + amount;
                }


                Button newLabel = new Button(text);
                newLabel.setStyle("-fx-background-color: #4CAF50");
                newLabel.setPrefWidth(200.0);
                TransactionsVbox.getChildren().add(newLabel);
            }
        } catch (SQLException e) {
            System.out.println("Imma Dunce" );
            e.printStackTrace(System.out);
        }


    }
}