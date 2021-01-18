package com.github.swierkosz.fixture.generator.values;
/*
 *    Copyright 2021 Szymon Świerkosz
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
import com.github.swierkosz.fixture.generator.reflection.ClassInspector;
import com.github.swierkosz.fixture.generator.reflection.FieldInformation;

import static com.github.swierkosz.fixture.generator.reflection.ReflectionUtils.createUsingDefaultConstructor;
import static com.github.swierkosz.fixture.generator.values.NoValue.NO_VALUE;
import static java.lang.reflect.Modifier.isAbstract;

public class ConstructingValueGenerator implements ValueGenerator {

    private final ClassInspector classInspector = new ClassInspector();

    @Override
    public Object generateValue(ValueContext valueContext) {
        TypeInformation type = valueContext.getType();
        if (isAbstract(type.getRawType().getModifiers())) {
            return NO_VALUE;
        }

        Object result = createUsingDefaultConstructor(type.getRawType());
        if (result == null) {
            return NO_VALUE;
        }

        for (FieldInformation field : classInspector.listFieldsFor(type)) {
            field.getSetter().accept(result, valueContext.create(field.getName(), field.getType()));
        }

        return result;
    }

}
