package com.softserve.app.repository;

import com.softserve.app.models.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findAllByCategoryNameIgnoreCase (String category);

    List<Banner> findByTitleIgnoreCaseContaining(String keyWord);

    @Query(
            value = "SELECT * FROM banner b WHERE b.status = 'PUBLISHED' or b.status = 'NOT_PUBLISHED'",
            nativeQuery = true
    )
    List<Banner> findAllOpen();

    @Query(
            value = "SELECT * FROM banner b WHERE b.status = 'CLOSED'",
            nativeQuery = true
    )
    List<Banner> findAllClosed();
}
