package com.vongthaya.backenddemo.service;

import com.vongthaya.backenddemo.dto.CreateAddressDTO;
import com.vongthaya.backenddemo.entity.Address;
import com.vongthaya.backenddemo.entity.User;
import com.vongthaya.backenddemo.exception.BaseException;
import com.vongthaya.backenddemo.exception.UserException;
import com.vongthaya.backenddemo.repository.AddressRepository;
import com.vongthaya.backenddemo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public List<Address> findByUser(User user) {
        return addressRepository.findByUser(user);
    }

    public Address create(CreateAddressDTO createAddressDTO) throws BaseException {
        // TODO: validate data
        Optional<User> userOp = userRepository.findById(createAddressDTO.getUserId());

        if (userOp.isEmpty()) {
            throw UserException.notFound();
        }

        User user = userOp.get();

        Address address = new Address();
        address.setLine1(createAddressDTO.getLine1());
        address.setLine2(createAddressDTO.getLine2());
        address.setZipcode(createAddressDTO.getZipcode());
        address.setUser(user);

        return addressRepository.save(address);
    }

}
