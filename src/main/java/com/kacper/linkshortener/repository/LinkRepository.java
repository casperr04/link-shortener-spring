package com.kacper.linkshortener.repository;

import com.kacper.linkshortener.model.entity.LinkEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository <LinkEntity, Long> {
    LinkEntity findByRedirectLink(String redirectLink);
    @Transactional
    void removeAllByExpirationDateIsLessThan(long unix);
    int countAllByExpirationDateIsLessThan(long unix);

}
