# NotifyMe Web App
## About
This project is about building a java application for small businesses that require both booking and reminder functionality for their business to run efficiently and satisfy the customer needs.

This is a Java Spring application that is connected to a PostgreSQL database and has a frontend client created in React.js

## Problem Statement


## UML Diagram
```mermaid
erDiagram
USER |o--|| CUSTOMER : is
USER ||--|| ADMIN : is
USER ||--|| EMPLOYEE : is
SERVICE }o--|| BOOKING : has
EMPLOYEE }o--|| BOOKING : has
CUSTOMER }o--|| BOOKING : has
SERVICE ||--|{ EMPLOYEE_TO_SERVICE : "is provided"
EMPLOYEE ||--|{ EMPLOYEE_TO_SERVICE : provides

USER {
    IID int8 PK
    FIRSTNAME VARCHAR 
	  LASTNAME VARCHAR
    EMAILADDRESS VARCHAR
	  PHONENUMBER VARCHAR
	  STATUS VARCHAR
    USERTYPE VARCHAR
	  GEOGRAPHICADDRESS VARCHAR
	  CREATIONDATETIME VARCHAR
	  LASTLOGINDATETIME VARCHAR
	  PASSWORD VARCHAR
  }
  CUSTOMER {
    USER_ID int8 PK,FK
    PAYMENTID VARCHAR
    INVOICE VARCHAR 
  }
  EMPLOYEE {
    USER_ID int8 PK,FK
    DEPARTMENT VARCHAR
    SALARY VARCHAR
    JOBTITLE VARCHAR
  }
  ADMIN {
    USER_ID int8 PK,FK
    ROLE VARCHAR
    PERMISSIONS VARCHAR
  }
  BOOKING {
    IID int8 PK
	  CUSTOMER_ID int8 FK
	  EMPLOYEE_ID int8 FK
	  SERVICE_ID int8 FK
	  STARTDATETIME int8 
	  ENDDATETIME int8
    NOTIFIED BOOLEAN
	  STATUS VARCHAR
	  PAYMENTSTATUS VARCHAR 
	  NOTES VARCHAR
  }
  SERVICE {
    IID int8 PK
	  SERVICENAME VARCHAR
	  DESCRIPTION VARCHAR
	  PRICE VARCHAR 
	  DURATION VARCHAR 
	  AVAILABILITY BOOLEAN
	  CATEGORY VARCHAR 
	  IMAGEURL VARCHAR 
	 }
  EMPLOYEE_TO_SERVICE {
    EMPLOYEE_ID int8 PK,FK
	  SERVICE_ID int8 PK,FK
  }

 ```

## Functionality




