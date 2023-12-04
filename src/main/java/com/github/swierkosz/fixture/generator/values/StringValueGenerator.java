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
import com.github.swierkosz.fixture.generator.reflection.TypeInformationImpl;

import java.util.UUID;

public class StringValueGenerator implements ValueGenerator {

    private final TypeInformation UUID_TYPE = new TypeInformationImpl(UUID.class);

    @Override
    public Object generateValue(ValueContext valueContext) {
        if (valueContext.getType().is(String.class)) {
            StringBuilder sb = new StringBuilder();

            if (valueContext.getFieldName() != null) {
                sb.append(valueContext.getFieldName());
            } else {
                sb.append("string");
            }
            sb.append("-");
            sb.append(valueContext.create(null, UUID_TYPE).toString());

            return sb.toString();
        } else {
            return NO_VALUE;
        }
    }
}