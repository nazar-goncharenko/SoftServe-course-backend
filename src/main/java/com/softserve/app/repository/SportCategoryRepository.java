package com.softserve.app.repository;

import com.softserve.app.models.SportCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportCategoryRepository extends JpaRepository<SportCategory, Long> {
    SportCategory findByIdEquals (Long id);
    SportCategory findByName (String name);
    List<SportCategory> getAllByParentEquals(Long id);
    List<SportCategory> findByIsPredefinedTrue();

    SportCategory getById(Long id);
    @Query(value = "select * from sport_category", nativeQuery = true)
    List<SportCategory> getAll();
}
