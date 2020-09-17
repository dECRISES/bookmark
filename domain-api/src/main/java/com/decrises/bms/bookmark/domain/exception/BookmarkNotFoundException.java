package com.decrises.bms.bookmark.domain.exception;

public class BookmarkNotFoundException extends RuntimeException {

  public BookmarkNotFoundException(Long id) {
    super("Bookmark with code " + id + " does not exist");
  }
}

