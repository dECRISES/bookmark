package com.decrises.bms.bookmark.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.decrises.bms.bookmark.domain.model.Bookmark;

@Table(name = "T_BOOKMARK")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_BOOKMARK")
  @Column(name = "TECH_ID")
  private Long techId;

  @Column(name = "CODE")
  private Long code;

  @Column(name = "DESCRIPTION")
  private String description;

  public Bookmark toModel() {
    return Bookmark.builder().code(code).description(description).build();
  }
}
