package com.example.demo.service;

import com.example.demo.bean.request.AdminRequest;
import com.example.demo.bean.request.CustomerRequest;
import com.example.demo.exceptions.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    public void validateCustomerUpdateRequest(String password, String address){
        if((password==null || password.isBlank()) && (address==null || address.isBlank())){
            throw new ValidationException("Invalid Request! One of newPassword and newAddress must be there for update");
        }
    }

    public void validateAdminPasswordUpdateRequest(String password){
        if(password==null || password.isBlank()){
            throw new ValidationException("Invalid Request! Password must be there to update");
        }
    }

    public void validateAdminCreateRequest(AdminRequest adminRequest){
        StringBuffer sb = new StringBuffer();
        if(adminRequest.getName()==null || adminRequest.getName().isBlank()){
            sb.append("name cannot be empty! ");
        }
        if(adminRequest.getUserName()==null || adminRequest.getUserName().isBlank()){
            sb.append("userName cannot be empty! ");
        }
        if(adminRequest.getPassword()==null || adminRequest.getPassword().isBlank()){
            sb.append("password cannot be empty! ");
        }
        String error = sb.toString();
        if(error!=null && !error.isEmpty()){
            throw new ValidationException("Invalid Request! " + error);
        }
    }

    public void validateCustomerCreateRequest(CustomerRequest customerRequest){
        StringBuffer sb = new StringBuffer();
        if(customerRequest.getName()==null || customerRequest.getName().isBlank()){
            sb.append("name cannot be empty! ");
        }
        if(customerRequest.getUserName()==null || customerRequest.getUserName().isBlank()){
            sb.append("userName cannot be empty! ");
        }
        if(customerRequest.getPassword()==null || customerRequest.getPassword().isBlank()){
            sb.append("password cannot be empty! ");
        }
        if(customerRequest.getAddress()==null || customerRequest.getAddress().isBlank()){
            sb.append("address cannot be empty! ");
        }
        String error = sb.toString();
        if(error!=null && !error.isEmpty()){
            throw new ValidationException("Invalid Request! " + error);
        }
    }
}
