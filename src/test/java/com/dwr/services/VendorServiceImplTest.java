package com.dwr.services;

import com.dwr.api.v1.mapper.VendorMapper;
import com.dwr.api.v1.model.VendorDTO;
import com.dwr.api.v1.model.VendorListDTO;
import com.dwr.domain.Vendor;
import com.dwr.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class VendorServiceImplTest {
    public static final String NAME_1 = "My Vendor";
    public static final Long ID_1 = 1L;
    public static final String NAME_2 = "My Vendor";
    public static final Long ID_2 = 1L;

    @Mock
    VendorRepository repository;


    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, repository);
    }

    @Test
    public void getVendorById() {
        //given
        Vendor vendor1 = getVendor1();
        //mockito BDD syntax
        given(repository.findById(anyLong())).willReturn(Optional.of(vendor1));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        //then
        then(repository).should(times(1)).findById(anyLong());
        assertThat(vendorDTO.getName(), is(equalTo(NAME_1)));
    }
    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound() {
        //given
        //mockito BDD syntax
        given(repository.findById(anyLong())).willReturn(Optional.empty());

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        //then
        then(repository).should(times(1)).findById(anyLong());
    }

    @Test
    public void getAllVendors() {
        //given
        List<Vendor> vendors = Arrays.asList(getVendor1(), getVendor2());

        given(repository.findAll()).willReturn(vendors);

        //when
        VendorListDTO listDTO = vendorService.getAllVendors();

        //then
        then(repository).should(times(1)).findAll();
        assertThat(listDTO.getVendors().size(), is(equalTo(2)));
    }

    @Test
    public void createNewVendor() {
        //given
        VendorDTO vendorDTO = getVendorDTO();

        Vendor vendor = getVendor1();

        given(repository.save(any(Vendor.class))).willReturn(vendor);

        //when
        VendorDTO vendorDtoToSave = vendorService.createNewVendor(vendorDTO);

        //then
        then(repository).should(times(1)).save(any(Vendor.class));
        assertThat(vendorDtoToSave.getVendorURL(), containsString("1"));
    }

    @Test
    public void saveVendorByDTO() {
        //given
        VendorDTO vendorDTO = getVendorDTO();

        Vendor vendor = getVendor1();

        given(repository.save(any(Vendor.class))).willReturn(vendor);

        //when
        VendorDTO savedVendorDTO = vendorService.saveVendorByDTO(ID_1, vendorDTO);
        //then
        then(repository).should().save(any(Vendor.class));
        assertThat(savedVendorDTO.getVendorURL(), containsString("1"));
    }

    @Test
    public void patchVendor() {
        //given
        VendorDTO vendorDTO = getVendorDTO();

        Vendor vendor = getVendor1();

        given(repository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(repository.save(any(Vendor.class))).willReturn(vendor);

        //when
        VendorDTO savedVendorDTO = vendorService.patchVendor(ID_1, vendorDTO);

        //then
        then(repository).should().save(any(Vendor.class));
        then(repository).should(times(1)).findById(anyLong());
        assertThat(savedVendorDTO.getVendorURL(), containsString("1"));
    }

    @Test
    public void deleteVendorById() {
        //when
        vendorService.deleteVendorById(1L);

        //then
        then(repository).should().deleteById(anyLong());
    }

    private VendorDTO getVendorDTO(){
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);
        return vendorDTO;
    }

    private Vendor getVendor1() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_1);
        vendor.setId(ID_1);
        return vendor;
    }

    private Vendor getVendor2() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_2);
        vendor.setId(ID_2);
        return vendor;
    }
}