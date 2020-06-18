package com.dwr.controllers.v1;


import com.dwr.api.v1.model.VendorDTO;
import com.dwr.api.v1.model.VendorListDTO;
import com.dwr.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getVendorList(){
        return vendorService.getAllVendors();
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id){
        return vendorService.getVendorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO){
        return vendorService.createNewVendor(vendorDTO);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@RequestBody VendorDTO vendorDTO, @PathVariable Long id){
        return vendorService.saveVendorByDTO(id, vendorDTO);
    }
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor (@RequestBody VendorDTO vendorDTO, @PathVariable Long id){
        return vendorService.saveVendorByDTO(id, vendorDTO);
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendorById(@PathVariable Long id){
        vendorService.deleteVendorById(id);
    }
}
