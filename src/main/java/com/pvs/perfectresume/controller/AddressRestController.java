package com.pvs.perfectresume.controller;


import com.pvs.perfectresume.model.ApiRequestBody;
import com.pvs.perfectresume.model.ApiResponseBody;
import com.pvs.perfectresume.service.AddressService;
import com.pvs.perfectresume.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user/address")
public class AddressRestController {


    @Autowired
    private AddressService addressService;
    
    @PostMapping(value = "/update1Address",consumes="application/json",produces="application/json")
    public ApiResponseBody updateSingleAddress(@RequestBody ApiRequestBody apiRequestBody){
        return addressService.updateSingleAddress(apiRequestBody.getUser(),apiRequestBody.getAddress());
    }

    @PostMapping(value = "/create",consumes="application/json",produces="application/json")
    public ApiResponseBody createAddress(@RequestBody ApiRequestBody apiRequestBody){
        return addressService.createNewAddress(apiRequestBody.getUser(),apiRequestBody.getAddress());
    }

    @PostMapping(value = "/delete",consumes="application/json",produces="application/json")
    public ApiResponseBody deleteAddress(@RequestBody ApiRequestBody apiRequestBody){
        return addressService.deleteAddress(apiRequestBody.getUser(),apiRequestBody.getAddress());
    }
}

