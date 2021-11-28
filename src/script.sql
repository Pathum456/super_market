
/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
 DROP DATABASE IF EXISTS FoodCity;
CREATE DATABASE IF NOT EXISTS FoodCity;
SHOW DATABASES;
USE FoodCity;

#======================================

DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    custID VARCHAR(6),
    custName VARCHAR(30),
    custAddress VARCHAR(30),
    nationalID VARCHAR (15),
    custTitle VARCHAR(50),
    city VARCHAR(20),
    province VARCHAR(20),
    postalCode VARCHAR(9),

    CONSTRAINT PRIMARY KEY (custID)
    );
SHOW TABLES ;
DESCRIBE Customer;

#-------------------------------------------

DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`(
    orderID VARCHAR(6),
    orderDate Date,
    orderTime VARCHAR (30),
    cID VARCHAR(6),
    totalDiscount DECIMAL (20,2),
    totalPrice DECIMAL (20,2),
    CONSTRAINT PRIMARY KEY (orderID),
    CONSTRAINT FOREIGN KEY (cID) REFERENCES Customer(custID) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `Order`;

#--------------------------------------------

DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    itemCode VARCHAR(6),
    description VARCHAR(50),
    packSize VARCHAR(20),
    unitPrice DECIMAL(6,2) DEFAULT 0.00,
    qtyOnHand int (5) DEFAULT 0,
    discount DECIMAL (6,2),
    CONSTRAINT PRIMARY KEY (itemCode)
    );
SHOW TABLES ;
DESCRIBE Item;
#------------------------

DROP TABLE IF EXISTS `Order Detail`;
CREATE TABLE IF NOT EXISTS `Order Detail`(

    oID VARCHAR(6),
    cId varchar (6),
    cName varchar (45),
    iCode VARCHAR(6),
    description varchar (150),
    orderQTY INT(11),
    discount DECIMAL(6,2),
    price DECIMAL (8,2),
    CONSTRAINT PRIMARY KEY (oID , iCode),
    CONSTRAINT FOREIGN KEY(oID) REFERENCES `Order`(orderID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY(iCode) REFERENCES Item(itemCode) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `Order Detail`;

#------------------------

DROP TABLE IF EXISTS `loginDetail`;
CREATE TABLE IF NOT EXISTS `loginDetail`(
    firstName VARCHAR(15),
    lastName VARCHAR(15),
    userType VARCHAR(15),
    email VARCHAR(30),
    userName VARCHAR(10),
    password VARCHAR(15)
    );
SHOW TABLES;
DESCRIBE `loginDetail`;

#==================================================
DROP TABLE IF EXISTS `SavedOrder`;
CREATE TABLE IF NOT EXISTS `SavedOrder`(
    oId VARCHAR (6),
    custNIC VARCHAR (15),
    itemCode VARCHAR (6),
    itemDescription VARCHAR (50),
    quantity INT (11),
    discount DECIMAL (20,2),
    total DECIMAL (20,2)
    );
SHOW TABLES;
DESCRIBE `SavedOrder`;

#==========================================================

DROP TABLE IF EXISTS `TempOrderID`;
CREATE TABLE IF NOT EXISTS `TempOrderID`(
    oId VARCHAR (6)

    );
SHOW TABLES;
DESCRIBE `TempOrderID`;
insert into item values ('I-001','Cake','5',350,8,10);











