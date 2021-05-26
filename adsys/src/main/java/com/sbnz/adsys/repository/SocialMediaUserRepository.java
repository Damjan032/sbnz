package com.sbnz.adsys.repository;

import com.sbnz.adsys.model.SocialMediaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialMediaUserRepository extends JpaRepository<SocialMediaUser, Long> {}
