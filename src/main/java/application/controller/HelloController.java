package application.controller;

import application.dto.Rider;
import application.entity.Customer;
import application.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class HelloController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/findAll")
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        //save a few customers
        customerRepository.save(new Customer("Jack", "Bauer"));
        customerRepository.save(new Customer("Chloe", "O'Brian"));
        customerRepository.save(new Customer("Kim", "Bauer"));
        customerRepository.save(new Customer("David", "Palmer"));
        customerRepository.save(new Customer("Michelle", "Dessler"));
        for (Customer customer : customerRepository.findAll()) {
            customers.add(customer);
        }
        return customers;
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findByLastName/{lastname}")
    public ResponseEntity findByLastName(@PathVariable String lastname) {
        List<Customer> customers = customerRepository.findByLastName(lastname);
        return new ResponseEntity(customers, HttpStatus.OK);
    }



}
