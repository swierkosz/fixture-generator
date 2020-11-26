package com.github.swierkosz.fixture.generator.util;
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

import java.util.Collection;
import java.util.Random;

public class ExtendedRandom extends Random {

    public ExtendedRandom() {
    }

    public ExtendedRandom(long seed) {
        super(seed);
    }

    public <T> T randomOneOf(T[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Empty array");
        }
        return array[Math.abs(nextInt()) % array.length];
    }

    public <T> T randomOneOf(Collection<T> collection) {
        return collection.stream()
                .skip(Math.abs(nextInt()) % collection.size())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Empty collection"));
    }

    public long randomLong(long fromInclusive, long toInclusive) {
        if (fromInclusive == toInclusive) {
            return fromInclusive;
        } else {
            return fromInclusive + Math.abs(nextLong()) % (toInclusive - fromInclusive + 1);
        }
    }

    public int randomInt(int fromInclusive, int toInclusive) {
        if (fromInclusive == toInclusive) {
            return fromInclusive;
        } else {
            return fromInclusive + Math.abs(nextInt()) % (toInclusive - fromInclusive + 1);
        }
    }
}
