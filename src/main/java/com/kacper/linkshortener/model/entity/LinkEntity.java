package com.kacper.linkshortener.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "links")
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String originalLink;

    @Column(nullable = false)
    private String redirectLink;

    @Column(nullable = false)
    private LocalDateTime date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkEntity that = (LinkEntity) o;
        return id == that.id && Objects.equals(originalLink, that.originalLink) && Objects.equals(redirectLink, that.redirectLink) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
