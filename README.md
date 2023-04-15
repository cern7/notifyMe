# NotifyMe 
## Database UML Diagram
---
```mermaid
erDiagram
USER |o--|| CUSTOMER : is
USER ||--|| ADMIN : is
USER ||--|| EMPLOYEE : is
SERVICE }o--|| BOOKING : has
EMPLOYEE }o--|| BOOKING : has
CUSTOMER }o--|| BOOKING : has
REMINDER ||--|| BOOKING : "is for / has"
SERVICE ||--|{ EMPLOYEE_SERVICE : "is provided"
EMPLOYEE ||--|{ EMPLOYEE_SERVICE : provides

USER {
    IID VARCHAR PK
    FIRSTNAME VARCHAR 
	  LASTNAME VARCHAR
    EMAILADDRESS VARCHAR
	  PHONENUMBER VARCHAR
	  STATUS VARCHAR
	  GEOGRAPHICADDRESS VARCHAR
	  CREATIONDATETIME VARCHAR
	  LASTLOGINDATETIME VARCHAR
	  PASSWORD VARCHAR
  }
  CUSTOMER {
    USER_ID VARCHAR PK,FK
    PAYMENTID VARCHAR
    INVOICE VARCHAR 
  }
  EMPLOYEE {
    USER_ID VARCHAR PK,FK
    DEPARTMENT VARCHAR
    SALARY VARCHAR
    JOBTITLE VARCHAR
  }
  ADMIN {
    USER_ID VARCHAR PK,FK
    ROLE VARCHAR
    PERMISSIONS VARCHAR
  }
  BOOKING {
    IID VARCHAR PK
	  CUSTOMER_ID VARCHAR FK
	  EMPLOYEE_ID VARCHAR FK
	  SERVICE_ID VARCHAR FK
	  STARTDATETIME VARCHAR 
	  ENDDATETIME VARCHAR 
	  STATUS VARCHAR
	  PAYMENTSTATUS VARCHAR 
	  NOTES teVARCHARxt
  }
  SERVICE {
    IID VARCHAR PK
	  NAME VARCHAR
	  DESCRIPTION VARCHAR
	  PRICE VARCHAR 
	  DURATION VARCHAR 
	  AVAILABILITY VARCHAR 
	  CATEGORY VARCHAR 
	  IMAGEURL VARCHAR 
	  STATUS VARCHAR 
  }
  REMINDER {
  IID VARCHAR PK
	USER_ID VARCHAR FK
	BOOKING_ID VARCHAR FK
	CONTENT VARCHAR 
	STATUS VARCHAR 
	FIRSTSENTDATE VARCHAR 
	LASTSENTDATE VARCHAR 
	TYPE VARCHAR 
  }
  EMPLOYEE_SERVICE {
    EMPLOYEE_ID VARCHAR PK,FK
	  SERVICE_ID VARCHAR PK,FK
	  HOURLYRATE VARCHAR 
	  STARTDATETIME VARCHAR 
  }

 ```


 Relation                                     | Description                                                                                  
:--------------------------------------------:|:--------------------------------------------------------------------------------------------:
 'USER \- CUSTOMER : is'                      | \(zero/one user can be customer\) \(one and only one customer can be a user\) \+ solid line  
 'USER \- ADMIN : is'                         | \(exactly one user is admin and viceversa\)                                                  
 'USER \- EMPLOYEE : is'                      | \(exactly one user is employee and viceversa\)                                               
 'CUSTOMER \- BOOKING : has '                 | \(Customer has zero / more bookings\) \(Booking has exactly one customer\)                   
 'EMPLOYEE \- BOOKING : has '                 | \(EMPLOYEE has zero / more bookings\) \(Booking has exactly one employee\)                   
 'SERVICE \- BOOKING : has'                   | \(SERVICE has zero / more bookings\) \(Booking has exactly one service\)                     
 'REMINDER \- BOOKING : is for / has'         | REMINDER is exactly for one BOOKING\) \(BOOKING has exactly one REMINDER\)                   
 'SERVICE \- EMPLOYEE\_SERVICE : is provided' | \(Exactly one service is provided by one ore more Employees\) \(Employee \)                  
 'EMPLOYEE \- EMPLOYEE\_SERVICE : provides'   | \(Exactly one employee provides one or more Service\)                                        


