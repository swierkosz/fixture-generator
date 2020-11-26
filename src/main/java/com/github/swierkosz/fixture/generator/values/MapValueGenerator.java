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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.github.swierkosz.fixture.generator.reflection.ReflectionUtils.createUsingDefaultConstructor;
import static com.github.swierkosz.fixture.generator.values.NoValue.NO_VALUE;

public class MapValueGenerator implements ValueGenerator {

    @Override
    public Object generateValue(ValueContext valueContext) {
        Map<Object, Object> map;
        TypeInformation type = valueContext.getType();

        if (type.getTypeParameters().size() != 2) {
            return NO_VALUE;
        }

        if (type.is(Map.class)) {
            map = new LinkedHashMap<>();

        } else if (type.is(SortedMap.class)) {
            map = new TreeMap<>();

        } else if (type.is(NavigableMap.class)) {
            map = new TreeMap<>();

        } else if (Map.class.isAssignableFrom(type.getRawType())) {
            map = tryToCreate(type.getRawType());

        } else {
            map = null;
        }

        if (map == null) {
            return NO_VALUE;
        }

        Collection<Object> keys = valueContext.createCollection(valueContext.getFieldName() + "-key", type.getTypeParameters().get(0));
        Collection<Object> values = valueContext.createCollection(valueContext.getFieldName() + "-value", type.getTypeParameters().get(1));

        Iterator<Object> keyIterator = keys.iterator();
        Iterator<Object> valueIterator = values.iterator();

        while (keyIterator.hasNext() && valueIterator.hasNext()) {
            map.put(keyIterator.next(), valueIterator.next());
        }

        return map;
    }

    private Map<Object, Object> tryToCreate(Class<?> type) {
        try {
            return (Map) createUsingDefaultConstructor(type);
        } catch (Exception e) {
            return null;
        }
    }
}
