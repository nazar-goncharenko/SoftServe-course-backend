package com.softserve.app.repository;

import com.softserve.app.models.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findAllByCategoryNameIgnoreCase (String category);
    List<Banner> findByTitleIgnoreCaseContaining(String keyWord);
    List<Banner> findByStatusEquals(Banner.Status status);
    List<Banner> findByStatusNot(Banner.Status status);

}
