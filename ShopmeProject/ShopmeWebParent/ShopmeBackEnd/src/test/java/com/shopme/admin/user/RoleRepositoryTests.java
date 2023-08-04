package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

/*
 * @DataJpaTest: Annotation này cung cấp một môi trường kiểm thử JPA và tự động cấu hình các bean liên quan đến JPA, 
 * bao gồm EntityManager, EntityManagerFactory và TransactionManager.
@AutoConfigureTestDatabase(replace = Replace.NONE): Annotation này sẽ tự động cấu hình datasource để sử dụng 
trong khi kiểm thử repository. Trong trường hợp này, nó được cấu hình để không thay thế datasource hiện có, 
do đó sử dụng datasource được cấu hình trong ứng dụng.
@Rollback(false): Annotation này chỉ định xem liệu transaction được tạo ra trong phạm vi kiểm tra 
có nên rollback hay không sau khi thực thi xong. Nếu giá trị của nó là false, transaction sẽ không bị rollback 
và dữ liệu được lưu trữ vào database.
 * */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin","manager everything");
		Role savedRole =  repo.save(roleAdmin);
		//doi tuong da thuc su luu vao database
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	@Test
	public void testCreateRestRoles() {
		Role roleSalesperson = new Role("Salesperson", "manage product price, "
				+ "customers, shipping, orders and sales report");
		
		Role roleEditor = new Role("Editor", "manage categories, brands, "
				+ "products, articles and menus");
		
		Role roleShipper = new Role("Shipper", "view products, view orders "
				+ "and update order status");
		
		Role roleAssistant = new Role("Assistant", "manage questions and reviews");
		
		repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
	}
}
