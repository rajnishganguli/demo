package com.example.demo.Controllers;

import com.example.demo.bean.model.AdminDetail;
import com.example.demo.bean.model.CustomerDetail;
import com.example.demo.bean.request.AdminRequest;
import com.example.demo.bean.request.CustomerRequest;
import com.example.demo.bean.response.AdminResponse;
import com.example.demo.bean.response.CustomerResponse;
import com.example.demo.bean.response.Response;
import com.example.demo.constants.CommonConstants;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.service.AdminDetailsService;
import com.example.demo.service.CustomerDetailsService;
import com.example.demo.service.ValidationService;
import com.example.demo.util.TransformerMethods;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CustomerDetailsService customerService;

    @Autowired
    AdminDetailsService adminService;

    @Autowired
    ValidationService validationService;

    Logger logger = LoggerFactory.getLogger(AdminController.class);


    /**
     * An admin can get only his own details.
     * @return
     */
    @GetMapping(path = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> fetchDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        logger.debug("Get admin :: username:{}, password:{}, authorities:{}", userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        AdminDetail adminDetail = adminService.retrieveAdminByUserName(userDetails.getUsername());
        if(adminDetail==null){
            throw new UserNotFoundException(CommonConstants.EXCEPTION_MESSAGE_ADMIN_NOT_FOUND + " with userName " + userDetails.getUsername());
        } else{
            AdminResponse adminResponse = TransformerMethods.prepareAdminResponse(adminDetail);
            Response response = new Response(CommonConstants.STATUS_SUCCESS, CommonConstants.MESSAGE_OK, adminResponse);
            return new ResponseEntity(response, HttpStatus.OK);
        }
    }


    /**
     * An admin can update only his own password.
     * @return
     */
    @PutMapping(path = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updatepassword(@RequestParam(name = "newPassword", required = true) String newPassword){
        validationService.validateAdminPasswordUpdateRequest(newPassword);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        logger.info("Update admin :: username:{}, password:{}, authorities:{}", userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        AdminDetail adminDetail = adminService.retrieveAdminByUserName(userDetails.getUsername());
        if(adminDetail==null){
            throw new UserNotFoundException(CommonConstants.EXCEPTION_MESSAGE_ADMIN_NOT_FOUND + " with userName " + userDetails.getUsername());
        } else{
            adminDetail.setPassword(newPassword);
            adminService.UpdateAdmin(adminDetail);
            logger.info("Admin successfully updated");
            AdminResponse adminResponse = TransformerMethods.prepareAdminResponse(adminDetail);
            Response response = new Response(CommonConstants.STATUS_SUCCESS, CommonConstants.MESSAGE_UPDATED_SUCCESSFULLY, adminResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * An admin can delete his account
     * @return
     */
    @DeleteMapping(path = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> deleteAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        logger.info("Delete admin :: username:{}, password:{}, authorities:{}", userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        AdminDetail adminDetail = adminService.retrieveAdminByUserName(userDetails.getUsername());
        if(adminDetail==null){
            throw new UserNotFoundException(CommonConstants.EXCEPTION_MESSAGE_ADMIN_NOT_FOUND + " with userName " + userDetails.getUsername());
        }
        adminService.deleteAdmin(adminDetail);
        logger.info("Admin successfully deleted!");
        Response response = new Response(CommonConstants.STATUS_SUCCESS, CommonConstants.MESSAGE_DELETED_SUCCESSFULLY);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * An admin can see all the customers
     * @return
     */
    @GetMapping(path="/manage/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getCustomers(){
        List<CustomerDetail> customers = customerService.findAll();
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        for(CustomerDetail customerDetail : customers){
            logger.info("Customers: {}", customers);
            customerResponseList.add(TransformerMethods.prepareCustomerResponse(customerDetail));
        }
        Response response = new Response(CommonConstants.STATUS_SUCCESS, CommonConstants.MESSAGE_OK, customerResponseList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * An admin can create one customer at a time.
     * @return
     */
    @PostMapping(path="/manage/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createCustomer(@RequestBody CustomerRequest customerRequest){
        validationService.validateCustomerCreateRequest(customerRequest);
        CustomerDetail customer = customerService.createCustomer(customerRequest);
        logger.info("Customer created successfully");
        CustomerResponse customerResponse = new CustomerResponse(customer);
        Response response = new Response(CommonConstants.STATUS_SUCCESS, CommonConstants.MESSAGE_OK, customer);
        URI productURI = URI.create(CommonConstants.APPLICATION_CONTEXT_PATH + "/manage/customer/" + customer.getId());
        return ResponseEntity.created(productURI).body(response);
    }


    /**
     * An admin can see a customer detail.
     * @param customerId
     * @return
     */
    @GetMapping(path="/manage/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getCustomer(@PathVariable("customerId") Long customerId){
        CustomerDetail customer = customerService.retrieveCustomer(customerId);
        if(customer==null){
            throw new ValidationException(CommonConstants.EXCEPTION_MESSAGE_CUSTOMER_NOT_FOUND + " with id " + customerId);
        }
        CustomerResponse customerResponse = TransformerMethods.prepareCustomerResponse(customer);
        Response response = new Response(CommonConstants.STATUS_SUCCESS, CommonConstants.MESSAGE_OK, customerResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * An admin can update one customer at a time.
     * @param customerId
     * @param newPassword
     * @param newAddress
     * @return
     */
    @PutMapping(path="/manage/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updateCustomer(@PathVariable("customerId") Long customerId,
                                   @RequestParam(name = "newPassword", required = false) String newPassword,
                                   @RequestParam(name="newAddress", required = false) String newAddress){
        validationService.validateCustomerUpdateRequest(newPassword, newAddress);
        CustomerDetail customer = customerService.retrieveCustomer(customerId);
        if(customer==null){
            throw new ValidationException(CommonConstants.EXCEPTION_MESSAGE_CUSTOMER_NOT_FOUND + " with id " + customerId);
        } else{
            if(newPassword!=null && !newPassword.isBlank()){
                customer.setPassword(newPassword);
            }
            if(newAddress!=null && !newAddress.isBlank()){
                customer.setAddress(newAddress);
            }
            customerService.updateCustomer(customer);
            logger.info("Customer updated successfully: {}", customer);
        }
        CustomerResponse customerResponse = TransformerMethods.prepareCustomerResponse(customer);
        Response response = new Response(CommonConstants.STATUS_SUCCESS, CommonConstants.MESSAGE_UPDATED_SUCCESSFULLY, customerResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * An admin can delete one customer at a time
     * @return
     */
    @DeleteMapping(path = "/manage/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> deleteCustomer(@PathVariable("customerId") Long customerId){
        CustomerDetail customer = customerService.retrieveCustomer(customerId);
        if(customer==null){
            throw new ValidationException(CommonConstants.EXCEPTION_MESSAGE_CUSTOMER_NOT_FOUND + " with id " + customerId);
        }
        customerService.deleteCustomer(customer);
        logger.info("Customer deleted successfully!");
        CustomerResponse customerResponse = TransformerMethods.prepareCustomerResponse(customer);
        Response response = new Response(CommonConstants.STATUS_SUCCESS, CommonConstants.MESSAGE_DELETED_SUCCESSFULLY);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * An admin can create a new admin
     * @return
     */
    @PostMapping(path = "/manage/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createAdmin(@RequestBody AdminRequest adminRequest) throws URISyntaxException {
        validationService.validateAdminCreateRequest(adminRequest);
        AdminDetail admin = adminService.createAdmin(adminRequest);
        logger.info("Admin created successfully!");
        AdminResponse adminResponse = TransformerMethods.prepareAdminResponse(admin);
        Response response = new Response(CommonConstants.STATUS_SUCCESS, CommonConstants.MESSAGE_OK, adminResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
