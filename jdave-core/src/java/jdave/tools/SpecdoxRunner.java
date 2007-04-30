/*
 * Copyright 2007 the original author or authors.
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
package jdave.tools;

import jdave.Specification;

/**
 * @author Joni Freeman
 */
public class SpecdoxRunner {
    public static final String FORMAT = "jdave.tools.specdox.format";
    public static final String DIRNAME = "jdave.tools.specdox.dir";

    public void generate(Class<? extends Specification<?>> specType) {
        if (System.getProperty(FORMAT) != null) {
            IDoxFormat format = formatFor(System.getProperty(FORMAT));
            Specdox specdox = new Specdox(new FileStore(dirname()));
            specdox.generate(specType, format);
        }
    }

    private String dirname() {
        return System.getProperty(DIRNAME, "target");
    }

    protected IDoxFormat formatFor(String formatName) {
        if (formatName.equals("txt")) {
            return new PlainTextFormat();
        }
        throw new RuntimeException("unknown format '" + formatName + "'");
    }
}
