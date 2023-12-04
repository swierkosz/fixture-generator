package com.github.swierkosz.fixture.generator.transformers;
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

import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class TypedInterceptor<T> implements Function<Object, Object> {

    private final Class<T> type;
    private final Consumer<T> interceptor;

    public TypedInterceptor(Class<T> type, Consumer<T> interceptor) {
        this.type = requireNonNull(type, "'type' must not be null");
        this.interceptor = requireNonNull(interceptor, "'interceptor' must not be null");
    }

    @Override
    public Object apply(Object value) {
        if (value != null && value.getClass().equals(type)) {
            interceptor.accept(type.cast(value));
        }
        return value;
    }

}
