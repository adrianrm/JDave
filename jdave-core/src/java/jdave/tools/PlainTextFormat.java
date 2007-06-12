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

/**
 * @author Joni Freeman
 */
public class PlainTextFormat implements IDoxFormat {
    private StringBuilder dox = new StringBuilder();
    private String specName;
    
    public void newSpec(String specName, String fqn) {
        this.specName = specName;
    }
    
    public void endSpec(String specName) {
    }
    
    public void newContext(String contextName) {
        if (dox.length() > 0) {
            dox.append("\n");
        }
        dox.append(Sentence.fromCamelCase(contextName));
        dox.append("\n");
    }
    
    public void endContext(String name) {
    }
    
    public void newBehavior(String behaviorName) {
        dox.append("  - ");
        dox.append(Sentence.fromCamelCase(behaviorName));
        dox.append("\n");
    }
    
    public String suffix() {
        return "txt";
    }
    
    @Override
    public String toString() {
        return specName + ":\n\n" + dox.toString();
    }
}
