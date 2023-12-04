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

import java.util.HashMap;
import java.util.Map;

public class DelegatingValueGenerator implements ValueGenerator {

    private final Map<Class<?>, ValueGenerator> generators = new HashMap<>();

    public void assignGenerator(Class<?> type, ValueGenerator generator) {
        generators.put(type, generator);
    }

    @Override
    public Object generateValue(ValueContext valueContext) {
        ValueGenerator generator = generators.get(valueContext.getType().getRawType());

        if (generator != null) {
            return generator.generateValue(valueContext);
        } else {
            return NO_VALUE;
        }
    }

}
