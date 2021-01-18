package com.github.swierkosz.fixture.generator.reflection;
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

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Objects;

public class ConstructorInformation {

    private final Constructor<?> constructor;
    private final List<TypeInformation> parameterTypes;

    public ConstructorInformation(Constructor<?> constructor, List<TypeInformation> parameterTypes) {
        this.constructor = constructor;
        this.parameterTypes = parameterTypes;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    public List<TypeInformation> getParameterTypes() {
        return parameterTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConstructorInformation that = (ConstructorInformation) o;
        return Objects.equals(constructor, that.constructor) && Objects.equals(parameterTypes, that.parameterTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constructor, parameterTypes);
    }

    @Override
    public String toString() {
        return "ConstructorInformation{" +
                "constructor=" + constructor +
                ", parameterTypes=" + parameterTypes +
                '}';
    }
}
