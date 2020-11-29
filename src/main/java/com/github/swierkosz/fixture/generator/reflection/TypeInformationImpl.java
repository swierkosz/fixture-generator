package com.github.swierkosz.fixture.generator.reflection;
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

import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class TypeInformationImpl implements TypeInformation {

    private final Class<?> rawType;
    private final List<TypeInformation> typeParameters;

    public TypeInformationImpl(Class<?> rawType, List<TypeInformation> typeParameters) {
        this.rawType = rawType;
        this.typeParameters = unmodifiableList(typeParameters);
    }

    public TypeInformationImpl(Class<?> rawType) {
        this(rawType, emptyList());
    }

    @Override
    public Class<?> getRawType() {
        return rawType;
    }

    @Override
    public List<TypeInformation> getTypeParameters() {
        return typeParameters;
    }

    @Override
    public boolean is(Class<?> other) {
        return rawType.equals(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TypeInformationImpl that = (TypeInformationImpl) o;
        return Objects.equals(rawType, that.rawType) &&
                Objects.equals(typeParameters, that.typeParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawType, typeParameters);
    }

    @Override
    public String toString() {
        return "TypeInformationImpl{" +
                "rawType=" + rawType +
                ", typeParameters=" + typeParameters +
                '}';
    }
}
