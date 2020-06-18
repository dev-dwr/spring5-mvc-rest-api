package com.dwr.api.v1.mapper;

import com.dwr.api.v1.model.VendorDTO;
import com.dwr.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO vendorToVendorDTO(Vendor vendor);

    Vendor vendorDTOToVendor(VendorDTO vendorDTO);
}
