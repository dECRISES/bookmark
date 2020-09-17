package com.decrises.bms.bookmark.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.decrises.bms.bookmark.domain.model.Bookmark;
import com.decrises.bms.bookmark.domain.port.ObtainBookmark;
import com.decrises.bms.bookmark.repository.dao.BookmarkDao;
import com.decrises.bms.bookmark.repository.entity.BookmarkEntity;

public class BookmarkRepository implements ObtainBookmark {

  private BookmarkDao bookmarkDao;

  public BookmarkRepository(BookmarkDao bookmarkDao) {
    this.bookmarkDao = bookmarkDao;
  }

  @Override
  public List<Bookmark> getAllBookmarks() {
    return bookmarkDao.findAll().stream().map(BookmarkEntity::toModel).collect(Collectors.toList());
  }

  @Override
  public Optional<Bookmark> getBookmarkByCode(Long code) {
    Optional<BookmarkEntity> bookmarkEntity = bookmarkDao.findByCode(code);
    return bookmarkEntity.map(BookmarkEntity::toModel);
  }
}
