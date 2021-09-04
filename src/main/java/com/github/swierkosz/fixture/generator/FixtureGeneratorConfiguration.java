package com.github.swierkosz.fixture.generator;
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

import com.github.swierkosz.fixture.generator.values.ArrayValueGenerator;
import com.github.swierkosz.fixture.generator.values.BooleanValueGenerator;
import com.github.swierkosz.fixture.generator.values.CharacterValueGenerator;
import com.github.swierkosz.fixture.generator.values.CollectionValueGenerator;
import com.github.swierkosz.fixture.generator.values.ConstructingValueGenerator;
import com.github.swierkosz.fixture.generator.values.EnumValueGenerator;
import com.github.swierkosz.fixture.generator.values.JavaTimeValueGenerator;
import com.github.swierkosz.fixture.generator.values.MapValueGenerator;
import com.github.swierkosz.fixture.generator.values.NumberValueGenerator;
import com.github.swierkosz.fixture.generator.values.OptionalValueGenerator;
import com.github.swierkosz.fixture.generator.values.StringValueGenerator;
import com.github.swierkosz.fixture.generator.values.UUIDValueGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class FixtureGeneratorConfiguration {

    private final Collection<ValueGenerator> generators = new ArrayList<>();
    private final Collection<Function<Object, Object>> transformers = new ArrayList<>();
    private boolean ignoreCyclicReferences;
    private boolean ignoreNoValue;

    protected FixtureGeneratorConfiguration() {
        generators.add(new ArrayValueGenerator());
        generators.add(new BooleanValueGenerator());
        generators.add(new CharacterValueGenerator());
        generators.add(new CollectionValueGenerator());
        generators.add(new EnumValueGenerator());
        generators.add(new JavaTimeValueGenerator());
        generators.add(new MapValueGenerator());
        generators.add(new NumberValueGenerator());
        generators.add(new OptionalValueGenerator());
        generators.add(new StringValueGenerator());
        generators.add(new UUIDValueGenerator());

        generators.add(new ConstructingValueGenerator());
    }

    public Collection<ValueGenerator> getGenerators() {
        return generators;
    }

    public boolean isIgnoreCyclicReferences() {
        return ignoreCyclicReferences;
    }

    public void setIgnoreCyclicReferences(boolean ignoreCyclicReferences) {
        this.ignoreCyclicReferences = ignoreCyclicReferences;
    }

    public boolean isIgnoreNoValue() {
        return ignoreNoValue;
    }

    public void setIgnoreNoValue(boolean ignoreNoValue) {
        this.ignoreNoValue = ignoreNoValue;
    }

    public Collection<Function<Object, Object>> getTransformers() {
        return transformers;
    }

    public void addTransformer(Function<Object, Object> transformer) {
        transformers.add(transformer);
    }
}
