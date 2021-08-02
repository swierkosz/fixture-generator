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
        return intercept(new TypedInterceptor<>(type, interceptor));
    }

    /**
     * Registers an untyped interceptor that will be called for every generated object.
     * Allows performing additional post-processing after object is generated.
     *
     * @param interceptor The interceptor to be called.
     * @return configurator
     */
    public FixtureGeneratorConfigurator intercept(Consumer<Object> interceptor) {
        configuration.addInterceptor(interceptor);
        return this;
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
     * Method for fluently finishing configuring of fixture generator.
     *
     * @return fixtureGenerator
     */
    public FixtureGenerator done() {
        return fixtureGenerator;
    }

    private static class TypedTransformer<T, U extends T> implements Function<Object, Object> {

        private final Class<T> type;
        private final Function<T, U> transformer;

        public TypedTransformer(Class<T> type, Function<T, U> transformer) {
            this.type = type;
            this.transformer = transformer;
        }

        @Override
        public Object apply(Object value) {
            if (value != null && value.getClass().equals(type)) {
                return transformer.apply(type.cast(value));
            } else {
                return value;
            }
        }
    }

    private static class TypedInterceptor<T> implements Consumer<Object> {

        private final Class<T> type;
        private final Consumer<T> interceptor;

        public TypedInterceptor(Class<T> type, Consumer<T> interceptor) {
            this.type = type;
            this.interceptor = interceptor;
        }

        @Override
        public void accept(Object value) {
            if (value != null && value.getClass().equals(type)) {
                interceptor.accept(type.cast(value));
            }
        }
    }
}
