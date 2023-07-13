package com.vongthaya.backenddemo.repository;

import com.vongthaya.backenddemo.entity.Social;
import com.vongthaya.backenddemo.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SocialRepository extends CrudRepository<Social, Integer> {

    Optional<User> findByUser(User user);

}
