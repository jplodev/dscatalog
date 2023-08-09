package com.jpdev.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpdev.dscatalog.entities.Category;


public interface CategoryRepository extends JpaRepository<Category, Long>{

}
