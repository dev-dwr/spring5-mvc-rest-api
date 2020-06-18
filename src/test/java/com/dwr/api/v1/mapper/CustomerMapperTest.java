package com.dwr.api.v1.mapper;

import com.dwr.api.v1.model.CustomerDTO;
import com.dwr.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
        //given
        Customer customer = new Customer();
        customer.setFirstName("Joe");
        customer.setLastName("Mama");

        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        //then
        assertEquals("Joe", customerDTO.getFirstName());
        assertEquals("Mama", customerDTO.getLastName());
    }
}