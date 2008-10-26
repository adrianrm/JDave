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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

/**
 * @author Marko Sibakov
 */
public class WebDriverTestPage extends WebPage {
    public WebDriverTestPage() {
        final Label label = new Label("testLabel", new Model("test label"));
        label.setOutputMarkupId(true);
        add(label);
        add(new AjaxFallbackLink("testLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                label.setModelObject("link clicked");
                target.addComponent(label);
            }
        });
        TextField textField = new TextField("testTextField", new Model());
        add(textField);
        textField.add(new OnChangeAjaxBehavior() {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                label.setModelObject(getModelObject());
                target.addComponent(label);
            }
        });
    }
}
