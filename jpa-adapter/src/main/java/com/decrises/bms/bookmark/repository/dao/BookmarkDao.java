package com.decrises.bms.bookmark.repository.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.decrises.bms.bookmark.repository.entity.BookmarkEntity;

@Repository
public interface BookmarkDao extends JpaRepository<BookmarkEntity, Long> {

  Optional<BookmarkEntity> findByCode(Long code);
}
