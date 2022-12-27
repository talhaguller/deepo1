package deepo.com.deepoECommerce.business.concretes;

import deepo.com.deepoECommerce.business.abstracts.AuthDataService;
import deepo.com.deepoECommerce.entities.concretes.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomUserDetailsManager implements UserDetailsService {

    @Autowired
    private AuthDataService authDataService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = authDataService.findByUsername(username);
        System.out.println("Username = " +customer.getUsername() + ", Password = " + customer.getPassword());
        return new User(customer.getUsername(), customer.getPassword(), Arrays.asList());
    }

}
