package deepo.com.deepoECommerce.webApi.controllers;

import deepo.com.deepoECommerce.business.abstracts.AuthDataService;
import deepo.com.deepoECommerce.business.concretes.CustomUserDetailsManager;
import deepo.com.deepoECommerce.business.requests.AccountCreationRequest;
import deepo.com.deepoECommerce.business.requests.AuthenticationRequest;
import deepo.com.deepoECommerce.business.responses.AccountCreationResponse;
import deepo.com.deepoECommerce.business.responses.AuthenticationResponse;
import deepo.com.deepoECommerce.entities.concretes.Customer;
import deepo.com.deepoECommerce.util.JwtUtil;
import deepo.com.deepoECommerce.util.Md5Util;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {


    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    private CustomUserDetailsManager customUserDetailsManager;

    private AuthDataService authDataService;

    @PostMapping("/signup")
    public ResponseEntity<?> createAccount(
            @RequestBody AccountCreationRequest accountCreationRequest)
            throws Exception{
        if (authDataService.findByUsername(accountCreationRequest.getUsername()) != null){
            return ResponseEntity.ok(
                    new AccountCreationResponse("failure","Username already exist")
            );
        }

        if (authDataService.findByEmail(accountCreationRequest.getEmail()) != null){
            return  ResponseEntity.ok(
                    new AccountCreationResponse("failure","Email Already exist")
            );
        }

        Customer customer =new Customer();
        customer.setEmail((accountCreationRequest.getEmail()));
        customer.setFirstName(accountCreationRequest.getFirstname());
        customer.setLastName(accountCreationRequest.getLastname());
        customer.setPassword(accountCreationRequest.getPassword());
        customer.setUsername(accountCreationRequest.getUsername());

        authDataService.createUserProfile(customer);
        return ResponseEntity.ok(new AccountCreationResponse("success",null));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestHeader (value = "Authorization") String headerData){

        AuthenticationRequest authenticationRequest =new AuthenticationRequest();
        String[] data = headerData.split(" ");
        byte[] decode = Base64.getDecoder().decode(data[1]);
        String decodeStr = new String(decode, StandardCharsets.UTF_8);
        data= decodeStr.split(":");

        authenticationRequest.setUsername(data[0]);
        authenticationRequest.setPassword(data[1]);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    Md5Util.getInstance().getMd5Hash(authenticationRequest.getPassword()))
            );
        }catch (BadCredentialsException e){
            return ResponseEntity.ok(new AccountCreationResponse(null,"Incorrect username or password"));
        }catch (Exception e){
            return ResponseEntity.ok(new AccountCreationResponse(null,"Username does not exist"));
        }

        final UserDetails userDetails =customUserDetailsManager.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        Customer customer = authDataService.findByUsername(authenticationRequest.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(jwt,null, customer.getFirstName()));
    }


}
