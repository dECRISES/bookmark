package com.decrises.bms.bookmark.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", strict = true, plugin = {
    "json:target/cucumber/bookmark.json", "json:target/cucumber/bookmark.xml"}, tags =
    "@Bookmark", glue = "classpath:com.decrises.bms.bookmark.cucumber")
public class RunCucumberBookmarkTest {

}
