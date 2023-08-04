package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private PaginationRespository pagirepo;
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	//dung csdl ao thay vi thuc te
	//quan ly cac doi tuong
	//entity la dai dien cho mot doi tuong co the dc luu tru va quan ly trong csdl
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userNameHM = new User("name@codejava.net","name2020","Name","Ha Minh");
		userNameHM.addRole(roleAdmin);
	
		User saveUser = repo.save(userNameHM);
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
	
		User userRavi = new User("ravi@gmail.com","ravi2020","Ravi","Kumar");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		
		User savedUser = repo.save(userRavi);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		
		listUsers.forEach(user -> System.out.println(user));
		
	}
	
	@Test
	public void testGetUserById() {
	User userNam = repo.findById(1).get();
	System.out.println(userNam);
	assertThat(userNam).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userNam = repo.findById(1).get();
		userNam.setEnabled(true);
		userNam.setEmail("namjavaprogrammer@gmail.com");
		repo.save(userNam);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userRavi = repo.findById(7).get();
		Role roleEditor = new Role(3);
		userRavi.getRoles().remove(roleEditor);
		Role roleSalesperson = new Role(2);
		userRavi.addRole(roleSalesperson);
		repo.save(userRavi);
	}
	
	@Test 
	public void testDeleteUser() {
		Integer userId = 5;
		repo.deleteById(userId);
		
		
	}
	
	@Test 
	public void testGetUserByEmail() {
		String email = "nghiangua111@gmail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 6;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 17;
		repo.updateEnabledStatus(id, true);
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 1;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = pagirepo.findAll(pageable);
		
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
	
	@Test
	public void testSearchUsers() {
		String keyword = "bruce";
		
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<User> page = repo.findAll(keyword, pageable);
		
List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));	
		
		assertThat(listUsers.size()).isGreaterThan(0);
	}
}
