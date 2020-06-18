package com.dwr.services;

import com.dwr.api.v1.mapper.VendorMapper;
import com.dwr.api.v1.model.VendorDTO;
import com.dwr.api.v1.model.VendorListDTO;
import com.dwr.controllers.v1.VendorController;
import com.dwr.domain.Vendor;
import com.dwr.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .map(vendorDTO -> {
                    vendorDTO.setVendorURL(getVendorURL(id));
                    return vendorDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorListDTO getAllVendors() {
        List<VendorDTO> vendorDTOS = vendorRepository
                .findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorURL(getVendorURL(vendor.getId()));
                    return vendorDTO;
                })
                .collect(Collectors.toList());

        return new VendorListDTO(vendorDTOS);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnVendor(vendorMapper.vendorDTOToVendor(vendorDTO));
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendorToSave = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendorToSave.setId(id);
        return saveAndReturnVendor(vendorToSave);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    if (vendor.getName() != null) {
                        vendorDTO.setName(vendor.getName());
                    }
                    return saveAndReturnVendor(vendor);
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO saveAndReturnVendor(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO vendorDtoToReturn = vendorMapper.vendorToVendorDTO(savedVendor);
        vendorDtoToReturn.setVendorURL(getVendorURL(savedVendor.getId()));

        return vendorDtoToReturn;
    }

    private String getVendorURL(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }
}
