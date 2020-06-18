package com.dwr.bootstrap;

import com.dwr.domain.Category;
import com.dwr.domain.Customer;
import com.dwr.domain.Vendor;
import com.dwr.repositories.CategoryRepository;
import com.dwr.repositories.CustomerRepository;
import com.dwr.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCategories(){
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);


        System.out.println("Data Loaded = " + categoryRepository.count() );
    }

    private void loadCustomers(){
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Joe");
        customer1.setLastName("Smith");

        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setFirstName("Ann");
        customer2.setLastName("Grey");
        customer2.setId(2L);

        customerRepository.save(customer2);


        System.out.println("Customers Loaded: " + customerRepository.count());
    }
    private void loadVendors(){
        Vendor vendor1 = new Vendor();
        vendor1.setName("Vendor 1");
        vendorRepository.save(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setName("Vendor 2");
        vendorRepository.save(vendor2);

        System.out.println("Vendors Loaded: " + vendorRepository.count());
    }
}
