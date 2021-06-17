package com.sbnz.adsys.repository;

import com.sbnz.adsys.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
