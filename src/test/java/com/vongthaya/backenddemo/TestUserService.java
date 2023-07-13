package com.vongthaya.backenddemo;

import com.vongthaya.backenddemo.dto.*;
import com.vongthaya.backenddemo.entity.Address;
import com.vongthaya.backenddemo.entity.Social;
import com.vongthaya.backenddemo.entity.User;
import com.vongthaya.backenddemo.exception.BaseException;
import com.vongthaya.backenddemo.service.AddressService;
import com.vongthaya.backenddemo.service.SocialService;
import com.vongthaya.backenddemo.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestUserService {

	@Autowired
	private UserService userService;

	@Autowired
	private SocialService socialService;

	@Autowired
	private AddressService addressService;

	@Order(1)
	@Test
	void testCreate() throws BaseException {
		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setEmail(TestCreateData.email);
		registerRequest.setPassword(TestCreateData.password);
		registerRequest.setFullname(TestCreateData.name);

		UserResponse userResponse = userService.create(registerRequest);

		// check not null
		Assertions.assertNotNull(userResponse);

		// check not equals
		Assertions.assertNotEquals(0, userResponse.getId());

		// check equals
		Assertions.assertEquals(registerRequest.getFullname(), userResponse.getName());
		Assertions.assertEquals(registerRequest.getEmail(), userResponse.getEmail());
	}

	@Order(2)
	@Test
	void testUpdate() throws BaseException {
		Optional<User> userOp = userService.findByEmail(TestCreateData.email);

		Assertions.assertTrue(userOp.isPresent());

		User user = userOp.get();

		UpdateUserDTO updateUserDTO = new UpdateUserDTO();
		updateUserDTO.setId(user.getId());
		updateUserDTO.setName(TestUpdateData.name);

		UserResponse userResponse = userService.update(updateUserDTO);

		Assertions.assertNotNull(userResponse);

		Assertions.assertEquals(TestUpdateData.name, userResponse.getName());
	}

	@Order(3)
	@Test
	void testSocialCreate() throws BaseException {
		Optional<User> userOp = userService.findByEmail(TestCreateData.email);

		Assertions.assertTrue(userOp.isPresent());

		User user = userOp.get();

		Assertions.assertNull(user.getSocial());

		// prepare data to save.
		SocialCreateDTO socialCreateDTO = new SocialCreateDTO();
		socialCreateDTO.setFacebook(TestSocialCreateData.facebook);
		socialCreateDTO.setLine(TestSocialCreateData.line);
		socialCreateDTO.setInstagram(TestSocialCreateData.instagram);
		socialCreateDTO.setTiktok(TestSocialCreateData.tiktok);
		socialCreateDTO.setUserId(user.getId());

		Social social = socialService.create(socialCreateDTO);

		Assertions.assertNotNull(social);
		Assertions.assertNotEquals(0, social.getId());

		Assertions.assertEquals(TestSocialCreateData.facebook, social.getFacebook());
		Assertions.assertEquals(TestSocialCreateData.line, social.getLine());
		Assertions.assertEquals(TestSocialCreateData.instagram, social.getInstagram());
		Assertions.assertEquals(TestSocialCreateData.tiktok, social.getTiktok());

		Assertions.assertNotNull(social.getUser());
		Assertions.assertEquals(user.getId(), social.getUser().getId());
	}

	@Order(4)
	@Test
	void testCreateAddress() throws BaseException {
		Optional<User> userOp = userService.findByEmail(TestCreateData.email);

		Assertions.assertTrue(userOp.isPresent());

		User user = userOp.get();

		List<Address> addresses = user.getAddresses();
		Assertions.assertTrue(addresses.isEmpty());

		CreateAddressDTO createAddressDTO = new CreateAddressDTO();
		createAddressDTO.setLine1(AddressTestCreateData.line1);
		createAddressDTO.setLine2(AddressTestCreateData.line2);
		createAddressDTO.setZipcode(AddressTestCreateData.zipcode);
		createAddressDTO.setUserId(user.getId());

		Address address = addressService.create(createAddressDTO);

		Assertions.assertNotNull(address);

		Assertions.assertEquals(AddressTestCreateData.line1, address.getLine1());
		Assertions.assertEquals(AddressTestCreateData.line2, address.getLine2());
		Assertions.assertEquals(AddressTestCreateData.zipcode, address.getZipcode());
		Assertions.assertEquals(user.getId(), address.getUser().getId());
	}

	@Order(9)
	@Test
	void testDelete() {
		Optional<User> userOp = userService.findByEmail(TestCreateData.email);

		Assertions.assertTrue(userOp.isPresent());

		User user = userOp.get();

		userService.deleteById(user.getId());

		Optional<User> userOpDelete = userService.findByEmail(TestCreateData.email);

		Assertions.assertTrue(userOpDelete.isEmpty());
	}

	private interface TestCreateData {

		String email = "vongthaya@gmail.com";

		String password = "123";

		String name = "vongthaya";

	}

	private interface TestUpdateData {

		String name = "kou";

	}

	private interface AddressTestCreateData {

		String line1 = "1234";

		String line2 = "5678";

		String zipcode = "0000";

	}

	private interface TestSocialCreateData {

		String facebook = "vongthaya@facebook";

		String line = "";

		String instagram = "";

		String tiktok = "";

	}

}
