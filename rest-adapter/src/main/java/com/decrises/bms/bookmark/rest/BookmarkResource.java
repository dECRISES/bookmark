package com.decrises.bms.bookmark.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.decrises.bms.bookmark.domain.model.Bookmark;
import com.decrises.bms.bookmark.domain.model.BookmarkInfo;
import com.decrises.bms.bookmark.domain.port.RequestBookmark;

@RestController
@RequestMapping("/api/v1/bookmarks")
public class BookmarkResource {

  private RequestBookmark requestBookmark;

  public BookmarkResource(RequestBookmark requestBookmark) {
    this.requestBookmark = requestBookmark;
  }

  @GetMapping
  public ResponseEntity<BookmarkInfo> getBookmarks() {
    return ResponseEntity.ok(requestBookmark.getBookmarks());
  }

  @GetMapping("/{code}")
  public ResponseEntity<Bookmark> getBookmarkByCode(@PathVariable Long code) {
    return ResponseEntity.ok(requestBookmark.getBookmarkByCode(code));
  }
}
