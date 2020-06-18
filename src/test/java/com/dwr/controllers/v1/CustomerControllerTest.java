package com.dwr.controllers.v1;

import com.dwr.api.v1.model.CustomerDTO;
import com.dwr.services.CustomerService;

import static com.dwr.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.dwr.services.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestResponseEntityException())
                .build();
    }

    @Test
    public void getListOfCustomers() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Michale");
        customer1.setLastName("Weston");
        customer1.setCustomerURL("/api/v1/customer/1");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstName("Sam");
        customer2.setLastName("Axe");
        customer2.setCustomerURL("/api/v1/customer/2");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getCustomerById() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Michale");
        customer1.setLastName("Weston");
        customer1.setCustomerURL("/api/v1/customer/1");

        //when
        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        //then
        mockMvc.perform(get(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Michale")));
    }

    @Test
    public void createNewCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("David");
        customerDTO.setLastName("Rym");

        CustomerDTO returnedDTO = new CustomerDTO();
        returnedDTO.setFirstName(customerDTO.getFirstName());
        returnedDTO.setLastName(customerDTO.getLastName());
        returnedDTO.setCustomerURL("/api/v1/customers/1");

        when(customerService.createNewCustomer(customerDTO)).thenReturn(returnedDTO);

        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("David")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void updateCustomerTest() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Dave");
        customerDTO.setLastName("Rym");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setLastName(customerDTO.getLastName());
        returnDTO.setFirstName(customerDTO.getFirstName());
        returnDTO.setCustomerURL("/api/v1/customer/1");

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Dave")))
                .andExpect(jsonPath("$.lastName", equalTo("Rym")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customer/1")));
    }
    @Test
    public void patchCustomerTest() throws Exception{
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Dave");

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstName(customerDTO.getFirstName());
        returnDto.setLastName("Rym");
        returnDto.setCustomerURL("/api/v1/customers/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDto);

        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(returnDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Dave")))
                .andExpect(jsonPath("$.lastName", equalTo("Rym")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }
    @Test
    public void deleteCustomerById() throws Exception{
        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService).deleteCustomerById(anyLong());
    }
    @Test
    public void testNotFoundException() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}





















