package com.example.demo.util;

import com.example.demo.bean.model.AdminDetail;
import com.example.demo.bean.model.CustomerDetail;
import com.example.demo.bean.response.AdminResponse;
import com.example.demo.bean.response.CustomerResponse;

public class TransformerMethods {

    public static CustomerResponse prepareCustomerResponse(CustomerDetail customerDetail){
        return new CustomerResponse(customerDetail);
    }

    public static AdminResponse prepareAdminResponse(AdminDetail adminDetail) {
        return new AdminResponse(adminDetail);
    }

}
