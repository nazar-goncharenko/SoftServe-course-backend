package com.softserve.app.repositories;

import com.softserve.app.models.PhotoOfTheDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoOfTheDay, Long> {

}
