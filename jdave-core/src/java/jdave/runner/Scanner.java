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
package jdave.runner;

import java.io.File;
import java.util.Stack;

/**
 * @author Joni Freeman
 */
public class Scanner {
    private final String path;

    public Scanner(String path) {
        this.path = path;
    }

    public void forEach(String extension, IFileHandler fileHandler) {
        Stack<File> dirs = new Stack<File>();
        dirs.push(new File(path));
        forEach(extension, fileHandler, dirs);
    }
    
    private void forEach(final String extension, IFileHandler fileHandler, Stack<File> dirs) {
        do {
            File dir = dirs.pop();
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    dirs.push(file);
                } else {
                    if (file.getName().endsWith("." + extension)) {
                        fileHandler.handle(file);                        
                    }
                }
            }
        } while (!dirs.isEmpty());
    }
}
