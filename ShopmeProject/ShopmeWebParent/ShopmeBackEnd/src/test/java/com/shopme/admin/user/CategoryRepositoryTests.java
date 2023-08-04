package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.category.CategoryRepository;
import com.shopme.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {
     
	@Autowired
	private CategoryRepository repo;
	
	@Test
	public void testCreateRootCategory() {
		Category category = new Category("Computers");
		Category savedCategory = repo.save(category);
		
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateSubCategory() {
//		Category parent = new Category(1);
//		Category subCategory = new Category("Desktops", parent);
//		Category savedCategory = repo.save(subCategory);
//		
//		assertThat(savedCategory.getId()).isGreaterThan(0);
		
		Category parent = new Category(5);
		Category subCategory = new Category("Memory", parent);
		Category savedCategory = repo.save(subCategory);
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateManySubCategory() {
//		Category parent = new Category(2);
//		Category cameras = new Category("Cameras", parent);
//		Category smartphones = new Category("Smartphones", parent);
//		
//		repo.saveAll(List.of(cameras, smartphones));
		
		
	}
	
	@Test
	public void testGetCategory() {
		Category category = repo.findById(1).get();
		System.out.println(category.getName());
		
		Set<Category> children = category.getChildren();
		for(Category subCategory : children) {
			System.out.println(subCategory.getName());
		}
		
		assertThat(children.size()).isGreaterThan(0);
	}
	
	@Test
	public void testPrintHierarchicalCategories() {
		Iterable<Category> categories = repo.findAll();
		
		for (Category category : categories) {
			if(category.getParent() == null) {
				System.out.println(category.getName());
				
				Set<Category> children = category.getChildren();
				for (Category subcategory : children) {
					System.out.println("--" + subcategory.getName());
					printChildren(subcategory,1);
				}
			}
		}
	}
	
	private void printChildren(Category parent, int subLevel) {
		
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();
		for(Category subCategory : children) {
			for (int i = 0; i < newSubLevel; i++) {
				System.out.println("--");
				
			}
			System.out.println("--" + subCategory.getName());
			
			printChildren(subCategory, newSubLevel);
		}
	}
	
	@Test
	public void testListRootCategories() {
		List<Category> rootCategories = repo.findRootCategories(Sort.by("name").ascending());
		rootCategories.forEach(cat -> System.out.println(cat.getName()));
	}
}
