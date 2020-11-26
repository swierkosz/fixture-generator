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

import java.util.Objects;
import java.util.function.BiConsumer;

public class FieldInformation {

    private final String name;
    private final BiConsumer<Object, Object> setter;
    private final TypeInformation type;

    public FieldInformation(String name, BiConsumer<Object, Object> setter, TypeInformation type) {
        this.name = name;
        this.setter = setter;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public BiConsumer<Object, Object> getSetter() {
        return setter;
    }

    public TypeInformation getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FieldInformation that = (FieldInformation) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(setter, that.setter) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, setter, type);
    }

    @Override
    public String toString() {
        return "FieldInformation{" +
                "name='" + name + '\'' +
                ", setter=" + setter +
                ", type=" + type +
                '}';
    }
}
