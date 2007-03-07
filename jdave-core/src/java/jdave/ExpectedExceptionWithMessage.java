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
package jdave;

/**
 * @author Lasse Koskela
 * @author Pekka Enberg
 */
public class ExpectedExceptionWithMessage<T> extends ExpectedException<T> {
    private final String message;

    public ExpectedExceptionWithMessage(Class<? extends T> type, String message) {
        super(type);
        this.message = message;
    }
    
    @Override
    public boolean matchesMessage(String message) {
        if (message == null && this.message == null) {
            return true;
        }
        if (message == null) {
            return false;
        }
        if (this.message == null) {
            return false;
        }
        return message.equals(this.message);
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
