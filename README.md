# NotifyMe Web App
## About
This project is about building a java application for small businesses that require both booking and reminder functionality for their business to run efficiently and satisfy the customer needs.

This is a Java Spring application that is connected to a PostgreSQL database and has a frontend client created in React.js

## Problem Statement
In the fast-paced world of small businesses, effective management of appointments and timely communication with customers is crucial for ensuring operational efficiency and customer satisfaction. However, many small businesses struggle with the absence of a comprehensive solution that seamlessly integrates both booking and reminder functionalities.

The lack of a dedicated system often leads to scheduling conflicts, missed appointments, and suboptimal customer experiences. Small businesses are in need of an easy-to-use, feature-rich application that combines booking capabilities with automated reminders to streamline their operations and enhance customer engagement.

To address this challenge, the NotifyMe app aims to provide a tailored solution specifically designed for small businesses. Leveraging Java Spring for backend functionality, connected to a PostgreSQL database for robust data management, and with a React.js frontend for an intuitive user interface, this application seeks to bridge the gap between efficient business management and customer satisfaction.

**Primary Objectives :**
- **Booking Management:** Simplify the process of appointment scheduling, allowing businesses to efficiently manage their calendars and avoid conflicting appointments.

- **Automated Reminders:** Implement a notification system that automatically reminds customers of their upcoming appointments, reducing no-shows and enhancing overall customer satisfaction.

- **User-Friendly Interface:** Develop an intuitive frontend interface using React.js to ensure that businesses can easily navigate and utilize the application without extensive training.

- **Data Security and Integrity:** Ensure the confidentiality and integrity of customer and business data by employing secure practices and leveraging the reliability of PostgreSQL for database management.

By addressing these key challenges, the NotifyMe app aims to empower small businesses with a comprehensive tool that not only simplifies their daily operations but also enhances the overall customer experience, ultimately contributing to the success and growth of the business.

## UML Diagram
Can be adapted to your business needs.
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

## App Functionality

The NotifyMe app is designed to streamline appointment management for small businesses by providing a comprehensive solution with the following key functionalities:

### 1. Booking Management

Simplify the appointment scheduling process, enabling businesses to efficiently manage their calendars and avoid conflicting appointments. The application offers a user-friendly interface for easy navigation and intuitive scheduling.

### 2. Automated Reminders

Implement a robust notification system that automatically reminds customers of their upcoming appointments. This feature aims to reduce no-shows, enhance overall customer satisfaction, and improve the overall reliability of the business.

### 3. User-Friendly Interface

Developed using React.js, the frontend interface ensures a seamless and intuitive experience for businesses. The user-friendly design allows for easy navigation and utilization of the application, eliminating the need for extensive training.

### 4. Data Security and Integrity

Ensuring the confidentiality and integrity of customer and business data is a top priority. The NotifyMe app employs secure practices and leverages the reliability of PostgreSQL for database management, providing a secure environment for sensitive information.

### 5. Java Spring Backend

The application is powered by a Java Spring backend, offering a robust and scalable foundation for the seamless integration of various functionalities. This ensures a stable and efficient performance for businesses of all sizes.

### 6. Growth and Success

By addressing the challenges of appointment management, the NotifyMe app aims to empower small businesses with a comprehensive tool. This not only simplifies daily operations but also enhances the overall customer experience, contributing to the success and growth of the business.

Explore the NotifyMe app today and revolutionize the way your business manages appointments and engages with customers.
