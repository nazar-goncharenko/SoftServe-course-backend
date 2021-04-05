package com.softserve.app.repository;

import com.softserve.app.models.SportCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportCategoryRepository extends JpaRepository<SportCategory, Long> {


    List<SportCategory> getAllByParentEquals(Long id);
}
