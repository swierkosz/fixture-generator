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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.github.swierkosz.fixture.generator.reflection.ReflectionUtils.createUsingDefaultConstructor;
import static com.github.swierkosz.fixture.generator.values.NoValue.NO_VALUE;

public class CollectionValueGenerator implements ValueGenerator {

    @Override
    public Object generateValue(ValueContext valueContext) {
        Collection<Object> collection;
        TypeInformation type = valueContext.getType();

        if (type.getTypeParameters().size() != 1) {
            return NO_VALUE;
        }

        if (type.is(Collection.class)) {
            collection = new ArrayList<>();

        } else if (type.is(List.class)) {
            collection = new ArrayList<>();

        } else if (type.is(Set.class)) {
            collection = new LinkedHashSet<>();

        } else if (type.is(SortedSet.class)) {
            collection = new TreeSet<>();

        } else if (type.is(NavigableSet.class)) {
            collection = new TreeSet<>();

        } else if (type.is(Queue.class)) {
            collection = new LinkedList<>();

        } else if (type.is(Deque.class)) {
            collection = new LinkedList<>();

        } else if (Collection.class.isAssignableFrom(type.getRawType())) {
            collection = tryToCreate(type.getRawType());

        } else {
            collection = null;
        }

        if (collection == null) {
            return NO_VALUE;
        }

        collection.addAll(valueContext.createCollection(valueContext.getFieldName(), type.getTypeParameters().get(0)));
        return collection;
    }

    private Collection<Object> tryToCreate(Class<?> type) {
        try {
            return (Collection) createUsingDefaultConstructor(type);
        } catch (Exception e) {
            return null;
        }
    }
}
