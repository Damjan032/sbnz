package com.sbnz.adsys.repository;

import com.sbnz.adsys.model.AdvertisementRequestResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdvertisementRequestResponseRepository extends JpaRepository<AdvertisementRequestResponse, Long> {
}