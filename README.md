# demo
A demo app using Spring boot, Spring security and JWT.

**APIs**
- Login APIs
  - demo-app/auth/admin [POST]
  - demo-app/auth/customer [POST]
 
    
- API used by customers to see/update/delete their account.
  - demo-app/customer [GET, PUT, DELETE]

- APIs used by admin to view his details
  - demo-app/admin [GET, PUT, DELETE]
 
- API used by admin to see/update/create customers
  - demo-app/manage/customer [GET, POST]
  - demo-app/manage/customer/{customerId} [GET, PUT, DELETE]

- API used by an admin to create another admin
  - demo-app/manage/admin [POST] : One admin can create another admin using this API
