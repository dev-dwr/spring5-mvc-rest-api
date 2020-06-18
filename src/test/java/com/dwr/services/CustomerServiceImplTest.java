package com.dwr.services;

import com.dwr.api.v1.mapper.CustomerMapper;
import com.dwr.api.v1.model.CustomerDTO;
import com.dwr.domain.Customer;
import com.dwr.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;


    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(customerRepository, customerMapper);
    }

    @Test
    public void getAllCustomers() {
        //given
        Customer customer1 = new Customer();
        customer1.setFirstName("Joe");
        customer1.setId(1L);
        customer1.setLastName("Mama");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Ann");
        customer2.setLastName("Ham");

        List<Customer> customers = Arrays.asList(customer1,customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals(2, customerDTOS.size());



    }

    @Test
    public void getCustomerById() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLastName("Mama");
        customer.setFirstName("Joe");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        //then
        assertEquals("Joe", customerDTO.getFirstName());
    }

    @Test
    public void createdNewCustomerTest(){
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("David");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
        assertEquals("/api/v1/customers/1", savedDto.getCustomerURL());
    }

    @Test
    public void saveCustomerByDTO(){
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("David");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.saveCustomerByDTO(1L,customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
        assertEquals("/api/v1/customers/1", savedDto.getCustomerURL());
    }

    @Test
    public void deleteCustomerById(){
        Long id = 1L;
        customerRepository.deleteById(id);
        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}










