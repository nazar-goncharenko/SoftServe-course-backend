package com.softserve.app.repository;

import com.softserve.app.models.CheckBox;
import com.softserve.app.models.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckBoxRepository extends JpaRepository<CheckBox, Long> {
    List<CheckBox> findAllBySurvey(Survey survey);
    Optional<CheckBox> findById(Long id);
}
