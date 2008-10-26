/*
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jdave.webdriver.testapplication;

import jdave.junit4.JDaveRunner;
import jdave.webdriver.WebDriverHolder;
import jdave.webdriver.elements.Find;
import jdave.webdriver.specification.WebDriverSpecification;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author Marko Sibakov
 */
@RunWith(JDaveRunner.class)
public class WebDrivertTestApplicationSpec extends WebDriverSpecification<Void> {
    private TestAppStarter testAppStarter;

    @Override
    public void onCreate() {
        testAppStarter = new TestAppStarter();
        testAppStarter.start();
    }

    public class TestPage {
        private WebDriver webDriver = WebDriverHolder.get();

        public void create() {
            webDriver.navigate().to("http://localhost:8080");
        }

        public void containsBodyText() {
            specify(webDriver.getPageSource().contains("foo"));
        }

        public void containsLinkThatCanBeClicked() {
            Find.link(By.linkText("testLink")).click();
            specify(webDriver.getPageSource().contains("link clicked"));
        }
    }

    @Override
    public void onDestroy() {
        testAppStarter.stop();
    }
}
