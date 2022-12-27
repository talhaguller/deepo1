package deepo.com.deepoECommerce.dataAccess.abstracts;

import deepo.com.deepoECommerce.entities.concretes.Customer;
import deepo.com.deepoECommerce.entities.concretes.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {


    @Query(value = "SELECT customer_id, username, first_name, last_name, email, password " +
            "FROM customer WHERE username = :USERNAME",
            nativeQuery = true)
    Optional<Customer> findByUsername(@Param("USERNAME") String USERNAME);

    @Query(value = "SELECT customer_id, username, first_name, last_name, email, password "+
            "FROM customer WHERE email = :EMAİL",
            nativeQuery = true)
    Optional<Customer> findByEmail(@Param("EMAİL") String EMAİL);

    @Query(value = "DELETE FROM customer WHERE username= :USERNAME && password= :PASSWORD",
            nativeQuery = true)
    void deleteByUsernamePassword(@Param("USERNAME") String USERNAME, @Param("PASSWORD") String PASSWORD);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO customer (username, first_name, last_name, email, password)"+
            "VALUES (:USERNAME, :LASTNAME, :FIRSTNAME, :EMAIL, :PASSWORD)",
            nativeQuery = true)
    void createUserProfile(@Param("USERNAME") String USERNAME, @Param("FIRSTNAME") String FIRSTNAME,
                           @Param("LASTNAME") String LASTNAME, @Param("EMAIL") String EMAIL,
                           @Param("PASSWORD") String PASSWORD);
}
