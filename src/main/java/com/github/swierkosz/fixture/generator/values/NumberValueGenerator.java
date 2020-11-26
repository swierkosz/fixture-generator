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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

import static com.github.swierkosz.fixture.generator.values.NoValue.NO_VALUE;

public class NumberValueGenerator implements ValueGenerator {

    @Override
    public Object generateValue(ValueContext valueContext) {
        TypeInformation type = valueContext.getType();
        Random random = valueContext.getRandom();

        if (type.is(byte.class) || type.is(Byte.class)) {
            return (byte) random.nextInt();

        } else if (type.is(short.class) || type.is(Short.class)) {
            return (short) random.nextInt();

        } else if (type.is(int.class) || type.is(Integer.class)) {
            return random.nextInt();

        } else if (type.is(long.class) || type.is(Long.class)) {
            return random.nextLong();

        } else if (type.is(float.class) || type.is(Float.class)) {
            return (float) random.nextInt() / 1000f;

        } else if (type.is(double.class) || type.is(Double.class)) {
            return (double) random.nextInt() / 1000d;

        } else if (type.is(BigInteger.class)) {
            return BigInteger.valueOf(random.nextLong());

        } else if (type.is(BigDecimal.class)) {
            return BigDecimal.valueOf((double) random.nextLong() / 1000d);

        } else {
            return NO_VALUE;
        }
    }
}
