package com.decrises.bms.bookmark.domain.port;

import com.decrises.bms.bookmark.domain.model.Bookmark;
import com.decrises.bms.bookmark.domain.model.BookmarkInfo;

public interface RequestBookmark {

  BookmarkInfo getBookmarks();
  Bookmark getBookmarkByCode(Long code);
}
