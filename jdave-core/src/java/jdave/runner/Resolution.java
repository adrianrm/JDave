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

import jdave.support.Assert;

/**
 * @author Joni Freeman
 */
public class Resolution {
    private final Groups groups;

    public Resolution(Groups groups) {
        Assert.notNull(groups, "must include @Groups annotation");
        this.groups = groups;
    }

    public boolean includes(String group) {
        for (String groupToInclude : groups.include()) {            
            if (includes(group, groupToInclude)) {
                return !excludes(group);
            }
        }
        return false;
    }

    private boolean excludes(String group) {
        for (String groupToExclude : groups.exclude()) {
            if (groupToExclude.equals(Groups.ALL)) {
                return true;
            }
            if (groupToExclude.equals(group)) {
                return true;
            }
        }
        return false;
    }

    private boolean includes(String group, String groupToInclude) {
        if (groupToInclude.equals(Groups.ALL)) {
            return true;
        }
        return group.equals(groupToInclude);
    }
}
