package com.kacper.linkshortener.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Data
public class LinkEntity {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String originalLink;

    @Column(nullable = false)
    private String redirectLink;

    @Column(nullable = false)
    private Date date;

}
