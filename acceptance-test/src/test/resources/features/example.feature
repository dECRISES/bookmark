@Bookmark
Feature: User would like to get bookmarks
  Background:
    Given the following bookmarks exists in the library
      | code | description                 |
      | 1    | Twinkle twinkle little star |
      | 2    | Johnny Johnny Yes Papa      |

  Scenario: User should be able to get all bookmarks
    When user requests for all bookmarks
    Then the user gets the following bookmarks
      | code | description                 |
      | 1    | Twinkle twinkle little star |
      | 2    | Johnny Johnny Yes Papa      |

  Scenario: User should be able to get bookmarks by code
    When user requests for bookmarks by code "1"
    Then the user gets the following bookmarks
      | code | description                 |
      | 1    | Twinkle twinkle little star |

  Scenario: User should get an appropriate NOT FOUND message while trying get bookmarks by an invalid code
    When user requests for bookmarks by id "10000" that does not exists
    Then the user gets an exception "Bookmark with code 10000 does not exist"