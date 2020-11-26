package com.github.swierkosz.fixture.generator.values;
/*
 *    Copyright 2020 Szymon Świerkosz
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

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import static com.github.swierkosz.fixture.generator.values.NoValue.NO_VALUE;

public class OptionalValueGenerator implements ValueGenerator {

    private static final TypeInformation INT_TYPE = new TypeInformationImpl(int.class);
    private static final TypeInformation LONG_TYPE = new TypeInformationImpl(long.class);
    private static final TypeInformation DOUBLE_TYPE = new TypeInformationImpl(double.class);

    @Override
    public Object generateValue(ValueContext valueContext) {
        TypeInformation type = valueContext.getType();

        if (type.is(Optional.class) && type.getTypeParameters().size() == 1) {
            return Optional.of(valueContext.create(valueContext.getFieldName(), type.getTypeParameters().get(0)));

        } else if (type.is(OptionalInt.class)) {
            return OptionalInt.of((int) valueContext.create(valueContext.getFieldName(), INT_TYPE));

        } else if (type.is(OptionalLong.class)) {
            return OptionalLong.of((long) valueContext.create(valueContext.getFieldName(), LONG_TYPE));

        } else if (type.is(OptionalDouble.class)) {
            return OptionalDouble.of((double) valueContext.create(valueContext.getFieldName(), DOUBLE_TYPE));

        } else {
            return NO_VALUE;
        }
    }
}
