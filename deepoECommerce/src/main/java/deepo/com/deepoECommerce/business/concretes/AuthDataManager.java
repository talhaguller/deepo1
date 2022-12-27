package deepo.com.deepoECommerce.business.concretes;

import deepo.com.deepoECommerce.business.abstracts.AuthDataService;
import deepo.com.deepoECommerce.dataAccess.abstracts.CustomerRepository;
import deepo.com.deepoECommerce.entities.concretes.Customer;
import deepo.com.deepoECommerce.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class AuthDataManager implements AuthDataService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer findByUsername(String username) {
        Optional<Customer> result = customerRepository.findByUsername(username);

        Customer customer=null;

        if(result.isPresent()){
            customer=result.get();
        }


        return customer;
    }

    @Override
    public Customer findByEmail(String email) {
        Optional<Customer> result = customerRepository.findByEmail(email);

        Customer customer =null;

        if(result.isPresent()){
            customer=result.get();
        }
        return customer;
    }

    @Override
    public void deleteByUsernamePassword(String username, String password) throws NoSuchAlgorithmException {
        customerRepository.deleteByUsernamePassword(username , Md5Util.getInstance().getMd5Hash(password));

    }

    @Override
    public void createUserProfile(Customer customer) throws NoSuchAlgorithmException {
        customerRepository.createUserProfile(customer.getUsername(),
                customer.getFirstName(), customer.getLastName(),
                customer.getEmail(), Md5Util.getInstance().getMd5Hash(customer.getPassword()));
    }
}
