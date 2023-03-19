package com.kacper.linkshortener.repository;

import com.kacper.linkshortener.model.entity.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository <LinkEntity, Long> {
    LinkRepository findByRedirectLink(String redirectLink);

}
