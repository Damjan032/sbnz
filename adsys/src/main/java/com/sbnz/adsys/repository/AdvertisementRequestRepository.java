package com.sbnz.adsys.repository;

import com.sbnz.adsys.model.AdvertisementRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRequestRepository extends JpaRepository<AdvertisementRequest, Long> {}
