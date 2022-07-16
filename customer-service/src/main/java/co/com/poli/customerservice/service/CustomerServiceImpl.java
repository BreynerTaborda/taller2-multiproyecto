package co.com.poli.customerservice.service;

import co.com.poli.customerservice.persistence.entity.Customer;
import co.com.poli.customerservice.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findByNumberID(String numberID) {
        return customerRepository.findByNumberID(numberID);
    }
}
