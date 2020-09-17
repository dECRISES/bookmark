package com.decrises.bms.bookmark.domain;

import java.util.Optional;
import com.decrises.bms.bookmark.domain.exception.BookmarkNotFoundException;
import com.decrises.bms.bookmark.domain.model.Bookmark;
import com.decrises.bms.bookmark.domain.model.BookmarkInfo;
import com.decrises.bms.bookmark.domain.port.ObtainBookmark;
import com.decrises.bms.bookmark.domain.port.RequestBookmark;

public class BookmarkDomain implements RequestBookmark {

  private ObtainBookmark obtainBookmark;

  public BookmarkDomain() {
    this(new ObtainBookmark() {
    });
  }

  public BookmarkDomain(ObtainBookmark obtainBookmark) {
    this.obtainBookmark = obtainBookmark;
  }

  @Override
  public BookmarkInfo getBookmarks() {
    return BookmarkInfo.builder().bookmarks(obtainBookmark.getAllBookmarks()).build();
  }

  @Override
  public Bookmark getBookmarkByCode(Long code) {
    Optional<Bookmark> bookmark = obtainBookmark.getBookmarkByCode(code);
    return bookmark.orElseThrow(() -> new BookmarkNotFoundException(code));
  }
}
