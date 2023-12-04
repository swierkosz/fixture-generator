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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class SubclassValueGeneratorTest {

    @NullAndEmptySource
    @ParameterizedTest
    void shouldThrowExceptionForNullOrEmptySubclassArray(Class<?>... implementations) {
        // When
        Throwable throwable = catchThrowable(() -> new SubclassValueGenerator(int.class, implementations));

        // Then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'implementations' must not be null or empty");
    }

    @Test
    void shouldThrowExceptionForNullSubclass() {
        // When
        Throwable throwable = catchThrowable(() -> new SubclassValueGenerator(int.class, null, null));

        // Then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("int is not supertype of null");
    }

    @Test
    void shouldThrowExceptionForUnrelatedType() {
        // When
        Throwable throwable = catchThrowable(() -> new SubclassValueGenerator(int.class, String.class));

        // Then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("int is not supertype of class java.lang.String");
    }
}