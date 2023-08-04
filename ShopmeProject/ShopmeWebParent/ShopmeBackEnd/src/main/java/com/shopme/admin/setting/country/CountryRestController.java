package com.shopme.admin.setting.country;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.common.entity.Country;

@RestController
public class CountryRestController {
      

	@Autowired private CountryRepository repo;
	
	@GetMapping("/countries/list")
	public List<Country> listAll() {
		return repo.findAllByOrderByNameAsc();
	}
	
	@PostMapping("/countries/save")
	public String save(@RequestBody Country country) {
		Country savedCountry = repo.save(country);
		return String.valueOf(savedCountry.getId());
	}
	
	@DeleteMapping("/countries/delete/{id}")
	public void delete(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
	
	//Nói đơn giản hơn là annoation ResponseBody sẽ giúp bạn convert đối tượng trả 
	//về thành chuỗi json thì vì bạn dùng 1 thư viện nào nó để convert.
	
//Tóm lại khi bạn gửi lên json data annotation RequestBody sẽ nói 
	//Spring convert json data đó thành đối tượng user và sử dụng như bình thường.

	//anh xa du lieu json trong httprequestbody sang mot java type object tuong ung
}
