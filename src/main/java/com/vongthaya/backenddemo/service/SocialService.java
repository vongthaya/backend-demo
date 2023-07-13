package com.vongthaya.backenddemo.service;

import com.vongthaya.backenddemo.dto.SocialCreateDTO;
import com.vongthaya.backenddemo.entity.Social;
import com.vongthaya.backenddemo.entity.User;
import com.vongthaya.backenddemo.exception.BaseException;
import com.vongthaya.backenddemo.exception.UserException;
import com.vongthaya.backenddemo.repository.SocialRepository;
import com.vongthaya.backenddemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SocialService {

    private final SocialRepository socialRepository;

    private final UserRepository userRepository;

    @Autowired
    public SocialService(SocialRepository socialRepository, UserRepository userRepository) {
        this.socialRepository = socialRepository;
        this.userRepository = userRepository;
    }

    public Optional<User> findByUser(User user) {
        return socialRepository.findByUser(user);
    }

    public Social create(SocialCreateDTO socialCreateDTO) throws BaseException {
        // TODO: validate data
        Optional<User> userOp = userRepository.findById(socialCreateDTO.getUserId());

        if (userOp.isEmpty()) {
            throw UserException.notFound();
        }

        User user = userOp.get();

        // create
        Social social = new Social();
        social.setFacebook(socialCreateDTO.getFacebook());
        social.setLine(socialCreateDTO.getLine());
        social.setInstagram(socialCreateDTO.getInstagram());
        social.setTiktok(socialCreateDTO.getTiktok());
        social.setUser(user);

        return socialRepository.save(social);
    }

}
