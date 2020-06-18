package com.dwr.services;

import com.dwr.api.v1.mapper.CustomerMapper;
import com.dwr.api.v1.model.CustomerDTO;
import com.dwr.controllers.v1.CustomerController;
import com.dwr.domain.Customer;
import com.dwr.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final  CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerURL(getCustomerURL(customer.getId()));
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        return saveCustomerReturnDTO(customer);
    }

    private CustomerDTO saveCustomerReturnDTO(Customer customer){
        Customer saveCustomer = customerRepository.save(customer);
        CustomerDTO returnToDTO = customerMapper.customerToCustomerDTO(saveCustomer);
        returnToDTO.setCustomerURL(getCustomerURL(saveCustomer.getId()));

        return returnToDTO;

    }
    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);

        return saveCustomerReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
            return customerRepository.findById(id).map(customer -> {
               if(customerDTO.getFirstName() != null){
                   customer.setFirstName(customerDTO.getFirstName());
               }
               if(customerDTO.getLastName() != null){
                   customer.setLastName(customerDTO.getLastName());
               }
               CustomerDTO returnCustomer = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
               returnCustomer.setCustomerURL(getCustomerURL(id));
               return returnCustomer;
            }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    private String getCustomerURL(Long id){
        return CustomerController.BASE_URL + "/" +  id;
    }
}
