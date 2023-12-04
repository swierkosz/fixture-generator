package com.github.swierkosz.fixture.generator;
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

import com.github.swierkosz.fixture.generator.transformers.TypedInterceptor;
import com.github.swierkosz.fixture.generator.transformers.TypedTransformer;
import com.github.swierkosz.fixture.generator.transformers.UntypedInterceptor;
import com.github.swierkosz.fixture.generator.values.SubclassValueGenerator;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A helper class for configuring behaviour of FixtureGenerator fluently.
 */
public class FixtureGeneratorConfigurator {

    private final FixtureGenerator fixtureGenerator;
    private final FixtureGeneratorConfiguration configuration;

    protected FixtureGeneratorConfigurator(FixtureGenerator fixtureGenerator, FixtureGeneratorConfiguration configuration) {
        this.fixtureGenerator = fixtureGenerator;
        this.configuration = configuration;
    }

    /**
     * Changes configuration to ignore cyclic references in an object tree,
     * null values will be inserted instead.
     *
     * @return configurator
     */
    public FixtureGeneratorConfigurator ignoreCyclicReferences() {
        return ignoreCyclicReferences(true);
    }

    /**
     * Changes configuration with regard to cyclic references in an object tree.
     *
     * @param enabled true - cyclic references will be ignored; null values will be inserted instead.
     *                false - an exception will be thrown for a class with cyclic reference (default).
     * @return configurator
     */
    public FixtureGeneratorConfigurator ignoreCyclicReferences(boolean enabled) {
        configuration.setIgnoreCyclicReferences(enabled);
        return this;
    }

    /**
     * Changes configuration to ignore classes that cannot be constructed,
     * null values will be inserted instead.
     *
     * @return configurator
     */
    public FixtureGeneratorConfigurator ignoreNoValue() {
        return ignoreNoValue(true);
    }

    /**
     * Changes configuration with regard to classes that cannot be constructed.
     *
     * @param enabled true - classes that cannot be constructed will be ignored; null values will be inserted instead.
     *                false - an exception will be thrown for classes that cannot be constructed (default).
     * @return configurator
     */
    public FixtureGeneratorConfigurator ignoreNoValue(boolean enabled) {
        configuration.setIgnoreNoValue(enabled);
        return this;
    }

    /**
     * Registers a typed interceptor that will be called for every generated object of equal type.
     * Allows performing additional post-processing after object is generated.
     *
     * @param type        Specifies type of the object to intercept.
     * @param interceptor The interceptor to be called.
     * @param <T>         Type of the object to intercept.
     * @return configurator
     */
    public <T> FixtureGeneratorConfigurator intercept(Class<T> type, Consumer<T> interceptor) {
        return transform(new TypedInterceptor<>(type, interceptor));
    }

    /**
     * Registers an untyped interceptor that will be called for every generated object.
     * Allows performing additional post-processing after object is generated.
     *
     * @param interceptor The interceptor to be called.
     * @return configurator
     */
    public FixtureGeneratorConfigurator intercept(Consumer<Object> interceptor) {
        return transform(new UntypedInterceptor(interceptor));
    }

    /**
     * Registers a typed transformer that will be called for every generated object of equal type.
     * Allows performing in-place substitution after object is generated.
     *
     * @param type        Specifies type of the object to intercept.
     * @param transformer The transformer to be called.
     * @param <T>         Type of the object to transform.
     * @return configurator
     */
    public <T, U extends T> FixtureGeneratorConfigurator transform(Class<T> type, Function<T, U> transformer) {
        return transform(new TypedTransformer<>(type, transformer));
    }

    /**
     * Registers an untyped transformer that will be called for every generated object.
     * Allows performing in-place substitution after object is generated.
     *
     * @param transformer The transformer to be called.
     * @return configurator
     */
    public FixtureGeneratorConfigurator transform(Function<Object, Object> transformer) {
        configuration.addTransformer(transformer);
        return this;
    }

    /**
     * Registers a list of subclasses available for generation for a given base type.
     *
     * @param type            Specifies base type of the object to be generated.
     * @param implementations List of subclasses to choose from when generating.
     * @return configurator
     */
    public FixtureGeneratorConfigurator subclass(Class<?> type, Class<?>... implementations) {
        return defineGenerator(type, new SubclassValueGenerator(type, implementations));
    }

    /**
     * Registers a typed value generator that will be called for every request to generate an object of equal type.
     *
     * @param type      Specifies type of the object to generate.
     * @param generator The generator to be called.
     * @return configurator
     */
    public FixtureGeneratorConfigurator defineGenerator(Class<?> type, ValueGenerator generator) {
        configuration.assignGenerator(type, generator);
        return this;
    }

    /**
     * Registers an untyped generator that will be called for every request to generate an object.
     *
     * @param generator The generator to be called.
     * @return configurator
     */
    public FixtureGeneratorConfigurator defineGenerator(ValueGenerator generator) {
        configuration.addGenerator(generator);
        return this;
    }

    /**
     * Method for fluently finishing configuring of fixture generator.
     *
     * @return fixtureGenerator
     */
    public FixtureGenerator done() {
        return fixtureGenerator;
    }

}
