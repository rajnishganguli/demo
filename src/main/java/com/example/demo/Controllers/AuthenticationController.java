package com.example.demo.Controllers;

import com.example.demo.bean.request.AuthRequest;
import com.example.demo.bean.response.AuthenticationResponse;
import com.example.demo.bean.response.Response;
import com.example.demo.constants.CommonConstants;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.service.AdminDetailsService;
import com.example.demo.service.CustomerDetailsService;
import com.example.demo.service.util.JwtUtil;
import com.example.demo.service.MyUserDetailsService;
import com.example.demo.service.util.ThreadLocalContextHolder;
import com.example.demo.util.TransformerMethods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Autowired
    private AdminDetailsService adminDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;


    @PostMapping(path = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> authenticateAdminAndGetToken(@RequestBody AuthRequest authRequest) throws JsonProcessingException {
        try {
            ThreadLocalContextHolder.set(CommonConstants.USER_TYPE_ADMIN);
            logger.warn("Credentials for admin: " + authRequest);
            Authentication authentication;
            try {
                authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            } catch (BadCredentialsException e) {
                logger.info("Bad admin credentials: {}", e.getMessage());
                throw new UserNotFoundException(e.getMessage());
            }
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            logger.info("Admin Authentication principal: {}", authentication.getPrincipal());
            boolean authenticated = authentication.isAuthenticated();
            logger.info("Admin user authenticated? :{}", authenticated);
            if (authenticated) {
                AuthenticationResponse token = new AuthenticationResponse(jwtUtil.generateToken(userDetails));
                Object data = TransformerMethods.prepareAdminResponse(adminDetailsService.retrieveAdminByUserName(authRequest.getUserName()));
                Response response = new Response(CommonConstants.STATUS_SUCCESS, CommonConstants.MESSAGE_LOGIN_SUCCESSFUL, token.getToken(), data);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new UserNotFoundException("invalid Admin request !");
            }
        } finally {
            ThreadLocalContextHolder.remove();
        }
    }


    @PostMapping(path = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> authenticateCustomerAndGetToken(@RequestBody AuthRequest authRequest) throws JsonProcessingException {
        try {
            ThreadLocalContextHolder.set(CommonConstants.USER_TYPE_CUSTOMER);
            logger.warn("Credentials for customer: " + authRequest);
            Authentication authentication;
            try {
                authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            } catch (BadCredentialsException e) {
                logger.info("Bad customer credentials: {}", e.getMessage());
                throw new UserNotFoundException(e.getMessage());
            }
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            logger.info("Customer Authentication principal: {}", authentication.getPrincipal());
            boolean authenticated = authentication.isAuthenticated();
            logger.info("Customer user authenticated? :{}", authenticated);
            if (authenticated) {
                AuthenticationResponse token = new AuthenticationResponse(jwtUtil.generateToken(userDetails));
                Object data = TransformerMethods.prepareCustomerResponse(customerDetailsService.retriveCustomerByUserName(authRequest.getUserName()));
                Response response = new Response(CommonConstants.STATUS_SUCCESS, CommonConstants.MESSAGE_LOGIN_SUCCESSFUL, token.getToken(), data);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new UserNotFoundException("invalid Customer request !");
            }
        } finally {
            ThreadLocalContextHolder.remove();
        }
    }

}
