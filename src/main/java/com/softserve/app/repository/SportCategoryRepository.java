package com.softserve.app.repository;

import com.softserve.app.models.SportCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportCategoryRepository extends JpaRepository<SportCategory, Long> {
    SportCategory findByIdEquals (Long id);
    SportCategory findByName (String name);
}