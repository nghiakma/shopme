package com.shopme.admin.user;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.User;

public interface PaginationRespository extends PagingAndSortingRepository<User, Integer>{
  
}
