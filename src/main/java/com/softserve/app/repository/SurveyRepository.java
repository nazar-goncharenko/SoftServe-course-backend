package com.softserve.app.repository;

import com.softserve.app.models.Survey;
import com.softserve.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findAllByUser(User user);
    List<Survey> findAllByUserAndIsOpen(User user, Boolean isOpen);
    Optional<Survey> findById(Long id);
    List<Survey> findAllByIsOpen(Boolean isOpen);
}
