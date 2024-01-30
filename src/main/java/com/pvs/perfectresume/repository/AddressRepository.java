package com.pvs.perfectresume.repository;

import com.pvs.perfectresume.model.Address;
import com.pvs.perfectresume.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findByUser(User presentUser);

    Address findByUserAndPinAndTownAndCity(User presentUser, String pin, String town, String city);

    Address findByAddressIdAndUser(Integer addressId, User user);
}
