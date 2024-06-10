# demo-app
- A demo app using Spring boot, Spring security and JWT.
- I have used spring security and JWT to introduce role based access to APIs
- The JWT returned has role and user info which is used in subsequent API requests after login.

**APIs**
- Login APIs
  - {{baseURL}}/auth/admin [POST]
  - {{baseURL}}/auth/customer [POST]
 
    
- API used by customers to see/update/delete their account.
  - {{baseURL}}/customer [GET, PUT, DELETE]

- APIs used by admin to view his details
  - {{baseURL}}/admin [GET, PUT, DELETE]
 
- API used by admin to see/update/create customers
  - {{baseURL}}/manage/customer [GET, POST]
  - {{baseURL}}/manage/customer/{customerId} [GET, PUT, DELETE]

- API used by an admin to create another admin
  - {{baseURL}}/manage/admin [POST] : One admin can create another admin using this API


**Where {{baseURL}} = http://13.201.79.200:8081/demo-app**

**This IP may change keep visiting the github page (https://github.com/rajnishganguli/demo) for fresh IP.**

--------------------------------------------------------------------

Postman collection : 
```
https://www.postman.com/rajnishganguli/workspace/my-public-workspace/collection/11741982-0d7e0655-db70-4ecd-92b9-05def7c1cccf?action=share&creator=11741982
```
--------------------------------------------------------------------
# API Details can be found below
--------------------------------------------------------------------

**API :** POST http://13.201.79.200:8081/demo-app/auth/admin
```
- Header
  - Content-Type : application/json

- Request Body

{
    "userName" : "raju",
    "password" : "raju101"
}

- Response HttpStatus 200
- Response Body

{
    "status": "success",
    "message": "Login successful!",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6InJhanUiLCJpYXQiOjE3MTgwNDc5MTcsImV4cCI6MTcxODA0OTcxN30.kB5oVBNSmjvrmBBnoiswhYx2kBXiG4RbuUpB_B3NPwI",
    "data": {
        "id": 1001,
        "name": "Raju",
        "userName": "raju"
    }
}
```
- Note : In case of incorrect credentials it returns 401 HttpStatus
--------------------------------------------------------------------

**API :** POST http://13.201.79.200:8081/demo-app/auth/customer
```
- Header
  - Content-Type : application/json

- Request Body

{
    "userName" : "rajnish",
    "password" : "rajnish101"
}

- Response HttpStatus 200
- Response Body

{
    "status": "success",
    "message": "Login successful!",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0NVU1RPTUVSIl0sInN1YiI6InJham5pc2giLCJpYXQiOjE3MTgwNDgyODcsImV4cCI6MTcxODA1MDA4N30.XSp8BeV5uludLATpMl8ztB61PGhqNuwIlK4uVzIM8TQ",
    "data": {
        "id": 1005,
        "name": "Rajnish",
        "userName": "rajnish",
        "address": "address-rohan-103"
    }
}
```
- Note : In case of incorrect credentials it returns 401 HttpStatus

--------------------------------------------------------------------
**API :** GET http://13.201.79.200:8081/demo-app/customer
```
Header
- Authorization : Bearer "logged in customer's JWT token"

{
    "status": "success",
    "message": "ok",
    "data": {
        "id": 1005,
        "name": "Rajnish",
        "userName": "rajnish",
        "address": "address-rohan-103"
    }
}

--- 403 forbitten : when JWT is incorrect

```


--------------------------------------------------------------------

**API :** PUT http://13.201.79.200:8081/demo-app/customer

```

Request Header :
- Authorization : Bearer "logged in customer's JWT-token"
- Content-Type : multipart/form-data

Request Body
- newPassword : new-password
- newAddress : new-address

Response Status : 200
Response body : 
{
    "status": "success",
    "message": "Updated successfully!",
    "data": {
        "id": 1005,
        "name": "Rajnish",
        "userName": "rajnish",
        "address": "my-new-address"
    }
}


Response when required data is missing
{
    "status": "failure",
    "errorDetails": [
        {
            "errorMessage": "Invalid Request! One of newPassword and newAddress must be there for update",
            "date": "2024-06-10T19:48:10.055895103"
        }
    ]
}

```

--------------------------------------------------------------------

**API :** DELETE http://13.201.79.200:8081/demo-app/customer

```
Request Header
- Authorization : Bearer "logged in customer's JWT-token"

Response HttpStatus : 200
Response Body
{
    "status": "success",
    "message": "Deleted successfully!"
}


HttpStatus 401 : when customer has been deleted like
{
    "status": "failure",
    "errorDetails": [
        {
            "errorMessage": "Customer not found| with userName rajnish",
            "date": "2024-06-10T19:56:59.228735029"
        }
    ]
}

```

--------------------------------------------------------------------

**API :** GET http://13.201.79.200:8081/demo-app/admin

```
Request Header
- Authorization : Bearer "logged in admin's JWT-token"

Response HttpStatus : 200
Response Body

{
    "status": "success",
    "message": "ok",
    "data": {
        "id": 1001,
        "name": "Raju",
        "userName": "raju"
    }
}

```

--------------------------------------------------------------------

**API :** PUT http://13.201.79.200:8081/demo-app/admin

```
Request Header
- Authorization : Bearer "logged in admin's JWT-token"
- Content-Type : multipart/form-data

Response HttpStatus : 200
Response Body : 
{
    "status": "success",
    "message": "Updated successfully!",
    "data": {
        "id": 1001,
        "name": "Raju",
        "userName": "raju"
    }
}

- When input is incorrect.
Response HttpStatus : 400
Response Body
{
    "type": "about:blank",
    "title": "Bad Request",
    "status": 400,
    "detail": "Required parameter 'newPassword' is not present.",
    "instance": "/demo-app/admin"
}

```

--------------------------------------------------------------------

**API :** DELETE http://13.201.79.200:8081/demo-app/admin
```
Request Header
- Authorization : Bearer "logged in admin's JWT-token"


Response HttpStatus : 200
Response Body
{
    "status": "success",
    "message": "Deleted successfully!"
}

- if already deleted
Response HttpStatus : 401
Response Body
{
    "status": "failure",
    "errorDetails": [
        {
            "errorMessage": "Admin not found| with userName raju",
            "date": "2024-06-10T20:05:04.295126105"
        }
    ]
}
```

--------------------------------------------------------------------


**API :** GET http://13.201.79.200:8081/demo-app/manage/customer


```
Request Header
- Authorization : Bearer "logged in admin's JWT-token"

Response HttpStatus : 200
Response Body :

{
    "status": "success",
    "message": "ok",
    "data": [
        {
            "id": 1003,
            "name": "Raju",
            "userName": "raju",
            "address": "address-raju-101"
        },
        {
            "id": 1004,
            "name": "Ravi",
            "userName": "ravi",
            "address": "address-ravi-102"
        }
    ]
}
```

--------------------------------------------------------------------


**API :** POST http://13.201.79.200:8081/demo-app/manage/customer

```
Request Header
- Authorization : Bearer "logged in admin's JWT-token"
- Content-Type : application/json

Request Body
{
    "name" : "test-1",
    "userName" : "test-user-1",
    "password" : "test-password",
    "address" : "sample-addresss-1"
}

Response HttpStatus : 201
Response Body
{
    "status": "success",
    "message": "ok",
    "data": {
        "id": 1,
        "name": "test-1",
        "userName": "test-user-1",
        "password": "test-password",
        "address": "sample-addresss-1"
    }
}

Response Header
- Location header /demo-app/manage/customer/1


When required fileds are missing form request for customer creation.
Resuest Body
{
    "name" : "test-1",
    "password" : "test-password",
}

Response HttpStatus : 400
Response Body :
{
    "status": "failure",
    "errorDetails": [
        {
            "errorMessage": "Invalid Request! userName cannot be empty! address cannot be empty! ",
            "date": "2024-06-10T20:11:50.024885938"
        }
    ]
}


- When user with that name already exists
Response HttpStatus : 409 : conflict
Response Body
{
    "status": "failure",
    "errorDetails": [
        {
            "errorMessage": "could not execute statement [Unique index or primary key violation: \"PUBLIC.CONSTRAINT_INDEX_B ON PUBLIC.CUSTOMER_DTL(USER_NAME NULLS FIRST) VALUES ( /* 1 */ 'test-user-1' )\"; SQL statement:\ninsert into customer_dtl (address,name,password,user_name,id) values (?,?,?,?,default) [23505-224]] [insert into customer_dtl (address,name,password,user_name,id) values (?,?,?,?,default)]; SQL [insert into customer_dtl (address,name,password,user_name,id) values (?,?,?,?,default)]; constraint [PUBLIC.CONSTRAINT_INDEX_B]",
            "date": "2024-06-10T20:12:29.172238732"
        }
    ]
}

```


--------------------------------------------------------------------

**API :** GET http://13.201.79.200:8081/demo-app/manage/customer/1

```
Request Header
- Authorization : Bearer "logged in admin's JWT-token"

Response HttpStatus : 200
Response Body :
{
    "status": "success",
    "message": "ok",
    "data": {
        "id": 1,
        "name": "test-1",
        "userName": "test-user-1",
        "address": "sample-addresss-1"
    }
}

- when customer doesn't exist
Response HttpStatus : 400
Response body :
{
    "status": "failure",
    "errorDetails": [
        {
            "errorMessage": "Customer not found| with id 1001",
            "date": "2024-06-10T20:14:56.644831353"
        }
    ]
}

```

--------------------------------------------------------------------

**API :** PUT http://13.201.79.200:8081/demo-app/manage/customer/1

```

Request Header
- Authorization : Bearer "logged in admin's JWT-token"
- Content-Type : multipart/form-data

Request Data
- newPassword : new-password-1
- newAddress : new-Address-1

Response HttpStatus : 200
Response Body
{
    "status": "success",
    "message": "Updated successfully!",
    "data": {
        "id": 1,
        "name": "test-1",
        "userName": "test-user-1",
        "address": "new-Address-1"
    }
}

- when none of update data present in request 
Response HttpStatus : 400
Response Body :
{
    "status": "failure",
    "errorDetails": [
        {
            "errorMessage": "Invalid Request! One of newPassword and newAddress must be there for update",
            "date": "2024-06-10T20:17:23.084611376"
        }
    ]
}
```

--------------------------------------------------------------------

**API :** DELETE http://13.201.79.200:8081/demo-app/manage/customer/1

```
Request Header
- Authorization : Bearer "logged in admin's JWT-token"


Response HttpStatus : 200
Response Body
{
    "status": "success",
    "message": "Deleted successfully!"
}

- When customer is already deleted
Response HttpStatus : 400 :
Response Body
{
    "status": "failure",
    "errorDetails": [
        {
            "errorMessage": "Customer not found| with id 1",
            "date": "2024-06-10T20:18:47.474182087"
        }
    ]
}
```


--------------------------------------------------------------------

**API :** POST : http://13.201.79.200:8081/demo-app/manage/admin

```
Request Header
- Authorization : Bearer "logged in admin's JWT-token"
- Content-Type : application/Json

Response HttpStatus : 201
Response Body : 
{
    "status": "success",
    "message": "ok",
    "data": {
        "id": 1,
        "name": "Rancho",
        "userName": "rancho"
    }
}


- When user already exists
Response HttpStatus : 409
Response Body : 
{
    "status": "failure",
    "errorDetails": [
        {
            "errorMessage": "could not execute statement [Unique index or primary key violation: \"PUBLIC.CONSTRAINT_INDEX_A ON PUBLIC.ADMIN_DTL(USER_NAME NULLS FIRST) VALUES ( /* 1 */ 'rancho' )\"; SQL statement:\ninsert into admin_dtl (name,password,user_name,id) values (?,?,?,default) [23505-224]] [insert into admin_dtl (name,password,user_name,id) values (?,?,?,default)]; SQL [insert into admin_dtl (name,password,user_name,id) values (?,?,?,default)]; constraint [PUBLIC.CONSTRAINT_INDEX_A]",
            "date": "2024-06-10T20:21:51.80558171"
        }
    ]
}

```

