CREATE DATABASE CoolBank;
USE CoolBank;

CREATE TABLE User (
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  ssn varchar(9) NOT NULL,
  balance int,
  loan_balance int,
  is_admin bool,
  PRIMARY KEY (ssn)
);

CREATE TABLE Transaction (
  id int AUTO_INCREMENT,
  sender varchar(9),
  recipient varchar(9),
  amount int,
  type ENUM('Transfer', 'Withdrawal', 'Deposit'),
  PRIMARY KEY (id),
  FOREIGN KEY (sender) REFERENCES User(ssn) ON delete SEt Null ON update cascade,
  FOREIGN KEY (recipient) REFERENCES User(ssn) ON delete SEt Null ON update cascade
);

DELIMITER //

-- Make a payment between two users
CREATE PROCEDURE makePayment(value int, p_sender varchar(9), p_recipient varchar(9))
BEGIN
  -- Calculate balances
  SET @r_old_balance = (SELECT balance FROM User WHERE ssn = p_recipient);
  SET @r_new_balance = @r_old_balance + value;
  SET @s_old_balance = (SELECT balance FROM User WHERE ssn = p_sender);
  SET @s_new_balance = @s_old_balance - value;
  -- Update user tables
  UPDATE User SET balance = @r_new_balance WHERE ssn = p_recipient;
  UPDATE User SET balance = @s_new_balance WHERE ssn = p_sender;
  -- Create a new transaction record
  INSERT INTO Transaction (sender, recipient, amount, type) VALUES (p_sender, p_recipient, value, 'Transfer');
END//

-- Withdraw from a user
CREATE PROCEDURE withdraw(value int, p_sender varchar(9))
BEGIN
  SET @s_new_balance = (SELECT balance FROM User WHERE ssn = p_sender) - value;
  UPDATE User SET balance = @s_new_balance WHERE ssn = p_sender;
  INSERT INTO Transaction (sender, recipient, amount, type) VALUES (p_sender, NULL, value, 'Withdrawal');
END//

-- Deposit to a user
CREATE PROCEDURE deposit(value int, p_recipient varchar(9))
BEGIN
  SET @r_new_balance = (SELECT balance FROM User WHERE ssn = p_recipient) + value;
  UPDATE User SET balance = @r_new_balance WHERE ssn = p_recipient;
  INSERT INTO Transaction (sender, recipient, amount, type) VALUES (NULL, p_recipient, value, 'Deposit');
END//

CREATE PROCEDURE addLoan(value int, p_recipient varchar(9))
BEGIN
  SET @r_new_balance = (SELECT loan_balance FROM User WHERE ssn = p_recipient) + value;
  UPDATE User SET loan_balance = @r_new_balance WHERE ssn = p_recipient;
END//

CREATE PROCEDURE payOffLoan(value int, p_sender varchar(9))
BEGIN
  SET @s_new_balance = (SELECT loan_balance FROM User WHERE ssn = p_sender) - value;
  UPDATE User SET loan_balance = @s_new_balance WHERE ssn = p_sender;
END//

DELIMITER ;

-- minor testing
INSERT INTO User (email, password, ssn, balance, loan_balance, interest, is_admin) VALUES
('testuser1@smsu.edu', 'Guest123', '123456789', 0, 0, 0.00, true),
('testuser2@smsu.edu', 'Guest123', '987654321', 0, 0, 0.00, true);

UPDATE User SET balance = 69000 WHERE ssn = '123456789';
UPDATE User SET balance = 420000 WHERE ssn = '987654321';