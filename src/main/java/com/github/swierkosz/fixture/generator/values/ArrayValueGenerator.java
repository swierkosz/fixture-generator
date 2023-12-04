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

import com.github.swierkosz.fixture.generator.TypeInformation;
import com.github.swierkosz.fixture.generator.ValueContext;
import com.github.swierkosz.fixture.generator.ValueGenerator;

import java.lang.reflect.Array;
import java.util.Collection;

public class ArrayValueGenerator implements ValueGenerator {

    @Override
    public Object generateValue(ValueContext valueContext) {
        TypeInformation type = valueContext.getType();

        if (type.getRawType().isArray()) {
            TypeInformation componentType = type.getTypeParameters().get(0);
            Collection<Object> components = valueContext.createCollection(valueContext.getFieldName(), componentType);
            Object array = Array.newInstance(componentType.getRawType(), components.size());
            int index = 0;
            for (Object component : components) {
                Array.set(array, index, component);
                index++;
            }
            return array;
        } else {
            return NO_VALUE;
        }
    }
}
