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

import com.github.swierkosz.fixture.generator.reflection.TypeInformationImpl;
import com.github.swierkosz.fixture.generator.values.ValueContextImpl;

import java.util.Random;

public class FixtureGenerator {

    private final FixtureGeneratorConfiguration configuration = new FixtureGeneratorConfiguration();
    private final Random random = new Random();

    /**
     * Method for configuring fluently generation of fixtures.
     *
     * @return A configurator class.
     */
    public FixtureGeneratorConfigurator configure() {
        return new FixtureGeneratorConfigurator(configuration);
    }

    /**
     * Creates an instance of a class specified by type parameter.
     * All values are deterministic - calling the method with same arguments will produce equal results.
     *
     * @param type Specifies type of the object to be constructed.
     * @return An instance of the requested type.
     */
    public <T> T createDeterministic(Class<T> type) {
        return create(type, 0);
    }

    /**
     * Creates an instance of a class specified by type parameter.
     * All values are randomized - calling the method with same arguments will produce different results.
     *
     * @param type Specifies type of the object to be constructed.
     * @return An instance of the requested type.
     */
    public <T> T createRandomized(Class<T> type) {
        return create(type, random.nextInt());
    }

    @SuppressWarnings("unchecked")
    private <T> T create(Class<T> type, int seed) {
        ValueContextImpl valueContext = new ValueContextImpl(configuration, null, new TypeInformationImpl(type), seed);
        return (T) valueContext.create();
    }

}
