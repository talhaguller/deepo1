package deepo.com.deepoECommerce.business.abstracts;

import deepo.com.deepoECommerce.entities.concretes.Customer;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;


public interface AuthDataService {

    Customer findByUsername(String username);

    Customer findByEmail(String email);

    // NoSuchAlgorithmException -> Bu istisna, belirli bir şifreleme algoritması istendiğinde ancak ortamda mevcut olmadığında atılır.
    void deleteByUsernamePassword(String username, String password) throws NoSuchAlgorithmException;

    void createUserProfile(Customer customer) throws NoSuchAlgorithmException;

}
