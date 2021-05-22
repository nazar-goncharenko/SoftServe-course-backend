package com.softserve.app.repository;

import com.softserve.app.models.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {

    Config getById(Long id);

    Config getTopByOrderByIdDesc();
}
