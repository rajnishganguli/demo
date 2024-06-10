package com.example.demo.Controllers;

import com.example.demo.bean.model.CustomerDetail;
import com.example.demo.bean.response.CustomerResponse;
import com.example.demo.bean.response.Response;
import com.example.demo.constants.CommonConstants;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.service.CustomerDetailsService;
import com.example.demo.service.ValidationService;
import com.example.demo.util.TransformerMethods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CustomerDetailsService customerService;

    @Autowired
    ValidationService validationService;

    Logger logger = LoggerFactory.getLogger(CustomerController.class);


    /**
     * A customer can fetch his own details only.
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping(path = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response fetchCustomerDetails() throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        logger.debug("Get customer :: username:{}, password:{}, authorities:{}", userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        CustomerDetail customer = customerService.retriveCustomerByUserName(userDetails.getUsername());
        if(customer==null){
            throw new UserNotFoundException(CommonConstants.EXCEPTION_MESSAGE_CUSTOMER_NOT_FOUND + " with userName " + userDetails.getUsername());
        } else{
            CustomerResponse customerResponse = TransformerMethods.prepareCustomerResponse(customer);
            return new Response(CommonConstants.STATUS_SUCCESS, CommonConstants.MESSAGE_OK, customerResponse);
        }
    }

    /**
     * A customer can update his own password or address. But he cannot update anyone else details.
     * @param newPassword
     * @param newAddress
     * @return
     */
    @PutMapping(path = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateCustomer(@RequestParam(name = "newPassword", required = false) String newPassword,
                                   @RequestParam(name="newAddress", required = false) String newAddress){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        logger.debug("Update Customer :: username:{}, password:{}, authorities:{}", userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        logger.debug("Fetching customerDetails for customer:{}, newPassword:{}, newAddress:{}", userDetails.getUsername(), newPassword, newAddress);
        CustomerDetail customer = customerService.retriveCustomerByUserName(userDetails.getUsername());
        if(customer==null){
            throw new UserNotFoundException(CommonConstants.EXCEPTION_MESSAGE_CUSTOMER_NOT_FOUND + " with userName " + userDetails.getUsername());
        } else{
            validationService.validateCustomerUpdateRequest(newPassword, newAddress);
            if(newPassword!=null && !newPassword.isBlank()){
                customer.setPassword(newPassword);
            }
            if(newAddress!=null && !newAddress.isBlank()){
                customer.setAddress(newAddress);
            }
            customerService.updateCustomer(customer);
            logger.info("Customer updated successfully: {}", customer);
            CustomerResponse customerResponse = TransformerMethods.prepareCustomerResponse(customer);
            return new Response(CommonConstants.STATUS_SUCCESS, CommonConstants.MESSAGE_UPDATED_SUCCESSFULLY, customerResponse);
        }
    }

    /**
     * A customer can delete his own account
     * @return
     */
    @DeleteMapping(path = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteCustomer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        logger.debug("Delete Customer :: username:{}, password:{}, authorities:{}", userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        CustomerDetail customer = customerService.retriveCustomerByUserName(userDetails.getUsername());
        if(customer==null){
            throw new UserNotFoundException(CommonConstants.EXCEPTION_MESSAGE_CUSTOMER_NOT_FOUND + " with userName " + userDetails.getUsername());
        }
        customerService.deleteCustomer(customer);
        logger.info("Customer deleted successfully: {}", customer);
        return new Response(CommonConstants.STATUS_SUCCESS, CommonConstants.MESSAGE_DELETED_SUCCESSFULLY);
    }


}
