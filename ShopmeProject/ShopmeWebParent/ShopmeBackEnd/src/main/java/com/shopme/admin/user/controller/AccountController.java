package com.shopme.admin.user.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.user.UserService;
import com.shopme.common.entity.User;

@Controller
public class AccountController {
    
	@Autowired
	private UserService service;
	
	//lay mot doi tuong nguoi dung dai dien cho nguoi dung hien tai dang nhap
	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal ShopmeUserDetails loggedUserDetails
			, Model model) {
	String email =	loggedUserDetails.getUsername();
	User user =  service.getByEmail(email);
	model.addAttribute("user", user);
	
	return "users/account_form";
	
	}
	
	 @PostMapping("/account/update")
	   public String saveDetails(User user, RedirectAttributes redirectAttributes,
			   @AuthenticationPrincipal ShopmeUserDetails loggedUser,
			   @RequestParam("image") MultipartFile multipartFile) throws IOException {
		 
		   if(!multipartFile.isEmpty()) {
			   String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			   user.setPhotos(fileName);
			   User savedUser = service.updateAccount(user);
			   String uploadDirString = "user-photos/" + savedUser.getId();
			   
			   FileUploadUtil.cleanDir(uploadDirString);
			   FileUploadUtil.saveFile( uploadDirString, fileName, multipartFile);
		   }else {
			   if(user.getPhotos().isEmpty()) user.setPhotos(null);
			   service.updateAccount(user);
		   }
		   loggedUser.setFirstName(user.getFirstName());
		   loggedUser.setLastName(user.getLastName());
		   redirectAttributes.addFlashAttribute("message","Your account detail have been updated");
		   return "redirect:/account";
	 }
}
