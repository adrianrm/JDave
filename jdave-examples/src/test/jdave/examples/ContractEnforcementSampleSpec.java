/*
 * Copyright 2006 the original author or authors.
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
package jdave.examples;

import jdave.CloneableContract;
import jdave.EqualsComparableContract;
import jdave.EqualsHashCodeContract;
import jdave.SerializableContract;
import jdave.Specification;
import jdave.examples.contract.SampleDomainObject;

/**
 * @author Joni Freeman
 */
public class ContractEnforcementSampleSpec extends Specification<SampleDomainObject> {
    public class NormalDomainObject {
        private SampleDomainObject object;

        public SampleDomainObject create() {
            object = new SampleDomainObject(1).setName("John");
            return object;
        }
        
        public void isSerializable() {
            specify(object, satisfies(new SerializableContract()));
        }
        
        public void isCloneable() {
            specify(object, satisfies(new CloneableContract()));
        }
        
        public void isConsistentWithEqualsAndHashCode() {
            specify(object, satisfies(new EqualsHashCodeContract<SampleDomainObject>() {
                @Override
                protected SampleDomainObject equal() {
                    return new SampleDomainObject(1);
                }
                @Override
                protected SampleDomainObject nonEqual() {
                    return new SampleDomainObject(2);
                }
                @Override
                protected SampleDomainObject subType() {
                    return new SampleDomainObject(1) {};
                }
            }));
        }
        
        public void isConsistentWithEqualsAndCompareTo() {
            specify(object, satisfies(new EqualsComparableContract<SampleDomainObject>() {
                @Override
                protected SampleDomainObject preceding() {
                    return new SampleDomainObject(2).setName("Andy");
                }                
                @Override
                protected SampleDomainObject subsequent() {
                    return new SampleDomainObject(3).setName("Zoe");
                }
                @Override
                protected SampleDomainObject equivalentByComparisonButNotByEqual() {
                    return new SampleDomainObject(4).setName("John");
                }
            }));
        }
    }
}
