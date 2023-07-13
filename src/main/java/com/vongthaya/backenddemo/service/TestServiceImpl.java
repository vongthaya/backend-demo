//package com.vongthaya.backenddemo.service;
//
//import com.vongthaya.backenddemo.exception.BaseException;
//import com.vongthaya.backenddemo.exception.UserException;
//import com.vongthaya.backenddemo.model.RegisterRequest;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class TestServiceImpl implements TestService {
//
//    @Override
//    public String register(RegisterRequest registerRequest) throws BaseException {
//        if (registerRequest == null) {
//            throw UserException.requestNull();
//        }
//
//        if (registerRequest.getEmail() == null || registerRequest.getEmail().isBlank()) {
//            throw UserException.emailNullOrBlank();
//        }
//
//        return "Received: " + registerRequest;
//    }
//
//    @Override
//    public String uploadProfilePicture(MultipartFile file) {
//        if (file == null) {
//            // throw exception
//        }
//
//        // file.getSize() return file size in bytes
//        if (file.getSize() > 1048576 * 2) {
//            // throw exception
//        }
//
//        String contentType = file.getContentType();
//        if (contentType == null) {
//            // throw
//        }
//
//        List<String> supportTypes = new ArrayList<>();
//        supportTypes.add("image/png");
//        supportTypes.add("image/jpg");
//
//        if (!supportTypes.contains(contentType)) {
//            // throw
//        }
//
//        // use byte array upload file
////        byte[] bytes = file.getBytes();
//
//        return "uploaded file.";
//    }
//
//}
