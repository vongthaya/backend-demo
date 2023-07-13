package com.vongthaya.backenddemo.repository;

import com.vongthaya.backenddemo.entity.Address;
import com.vongthaya.backenddemo.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {

    List<Address> findByUser(User user);

}
