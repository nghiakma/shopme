package com.shopme.admin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.admin.user.UserService;

@RestController
public class UserRestController {
   
	@Autowired
	private UserService service;
	
	//anh xa mot yeu cau http get den url này chỉ định rằng phương thức này sẽ được gọi khi nhan dc yeu cau Get toi url đã chỉ định
	@PostMapping("/users/check_email")
	public String checkDuplicateEmail(@Param("id") Integer id,@Param("email") String email) {
		return service.isEmailUnique(id,email) ? "OK" : "Duplicated";
	}
	
	
}
