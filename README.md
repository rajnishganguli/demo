# demo
A demo app using Spring boot, Spring security and JWT.

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

Where {{baseURL}} = http://13.201.79.200:8081/demo-app
This IP may change keep visiting for fresh IP.
