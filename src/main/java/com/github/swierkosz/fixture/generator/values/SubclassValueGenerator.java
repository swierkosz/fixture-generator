package com.github.swierkosz.fixture.generator.values;
/*
 *    Copyright 2023 Szymon Świerkosz
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.github.swierkosz.fixture.generator.ValueContext;
import com.github.swierkosz.fixture.generator.ValueGenerator;
import com.github.swierkosz.fixture.generator.reflection.TypeInformationImpl;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class SubclassValueGenerator implements ValueGenerator {

    private final List<Class<?>> implementations = new ArrayList<>();

    public SubclassValueGenerator(Class<?> type, Class<?>... implementations) {
        requireNonNull(type, "'type' must not be null");
        if (implementations == null || implementations.length == 0) {
            throw new IllegalArgumentException("'implementations' must not be null or empty");
        }
        for (Class<?> implementation : implementations) {
            if (implementation == null || !type.isAssignableFrom(implementation)) {
                throw new IllegalArgumentException(type + " is not supertype of " + implementation);
            }
            this.implementations.add(implementation);
        }
    }

    @Override
    public Object generateValue(ValueContext valueContext) {
        Class<?> type = valueContext.getRandom().randomOneOf(implementations);
        return valueContext.create(valueContext.getFieldName(), new TypeInformationImpl(type));
    }

}
