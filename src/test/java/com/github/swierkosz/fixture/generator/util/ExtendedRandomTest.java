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

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ExtendedRandomTest {

    private final ExtendedRandom extendedRandom = new ExtendedRandom();

    @Test
    void shouldReturnRandomOneOfArray() {
        // Given
        Integer[] array = {0, 1, 2};
        Set<Integer> results = new HashSet<>();

        // When
        for (int i = 0; i < 1000; i++) {
            int result = extendedRandom.randomOneOf(array);

            assertThat(result).isIn(array);
            results.add(result);
        }

        // Then
        assertThat(results).containsOnly(array);
    }

    @Test
    void shouldThrowExceptionForRandomOneOfEmptyArray() {
        // Given
        Long[] emptyArray = new Long[0];

        // When
        Throwable throwable = catchThrowable(() -> extendedRandom.randomOneOf(emptyArray));

        // Then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldReturnRandomOneOfCollection() {
        // Given
        Collection<Integer> collection = Arrays.asList(0, 1, 2);
        Set<Integer> results = new HashSet<>();

        // When
        for (int i = 0; i < 1000; i++) {
            int result = extendedRandom.randomOneOf(collection);

            assertThat(result).isIn(collection);
            results.add(result);
        }

        // Then
        assertThat(results).containsOnlyOnceElementsOf(collection);
    }

    @Test
    void shouldThrowExceptionForRandomOneOfEmptyCollection() {
        // Given
        Collection<Long> emptyCollection = new ArrayList<>();

        // When
        Throwable throwable = catchThrowable(() -> extendedRandom.randomOneOf(emptyCollection));

        // Then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldReturnRandomLong() {
        // Given
        Set<Long> results = new HashSet<>();

        // When
        for (int i = 0; i < 1000; i++) {
            long result = extendedRandom.randomLong(-10, 10);

            assertThat(result)
                    .isLessThanOrEqualTo(10)
                    .isGreaterThanOrEqualTo(-10);

            results.add(result);
        }

        // Then
        assertThat(results).hasSize(21);
    }

    @Test
    void shouldReturnRandomInt() {
        // Given
        Set<Integer> results = new HashSet<>();

        // When
        for (int i = 0; i < 1000; i++) {
            int result = extendedRandom.randomInt(-10, 10);

            assertThat(result)
                    .isLessThanOrEqualTo(10)
                    .isGreaterThanOrEqualTo(-10);

            results.add(result);
        }

        // Then
        assertThat(results).hasSize(21);
    }

}