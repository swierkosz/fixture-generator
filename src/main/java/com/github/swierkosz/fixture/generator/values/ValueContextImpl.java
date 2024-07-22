package com.github.swierkosz.fixture.generator.values;
/*
 *    Copyright 2024 Szymon Świerkosz
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

import com.github.swierkosz.fixture.generator.FixtureGenerationException;
import com.github.swierkosz.fixture.generator.FixtureGeneratorConfiguration;
import com.github.swierkosz.fixture.generator.TypeInformation;
import com.github.swierkosz.fixture.generator.ValueContext;
import com.github.swierkosz.fixture.generator.ValueGenerator;
import com.github.swierkosz.fixture.generator.util.ExtendedRandom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.swierkosz.fixture.generator.values.NoValue.NO_VALUE;

public class ValueContextImpl implements ValueContext {

    private final FixtureGeneratorConfiguration configuration;
    private final String fieldName;
    private final TypeInformation type;
    private final int seed;
    private final ExtendedRandom random;
    private final Set<TypeInformation> typesUnderConstruction;

    public ValueContextImpl(FixtureGeneratorConfiguration configuration, String fieldName, TypeInformation type, int seed) {
        this(configuration, fieldName, type, seed, new LinkedHashSet<>());
    }

    private ValueContextImpl(FixtureGeneratorConfiguration configuration, String fieldName, TypeInformation type, int seed, Set<TypeInformation> typesUnderConstruction) {
        this.configuration = configuration;
        this.fieldName = fieldName;
        this.type = type;
        this.seed = seed;
        this.random = new ExtendedRandom(seed);
        this.typesUnderConstruction = typesUnderConstruction;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public TypeInformation getType() {
        return type;
    }

    @Override
    public ExtendedRandom getRandom() {
        return random;
    }

    public Object create() {
        return generateAndPostProcess(this);
    }

    @Override
    public Object create(String fieldName, TypeInformation type) {
        ValueContextImpl valueContext = new ValueContextImpl(configuration, fieldName, type, Objects.hash(fieldName, seed), typesUnderConstruction);
        return generateAndPostProcess(valueContext);
    }

    @Override
    public Collection<Object> createCollection(String fieldName, TypeInformation type) {
        List<Object> result = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            ValueContextImpl valueContext = new ValueContextImpl(configuration, fieldName, type, random.nextInt(), typesUnderConstruction);
            result.add(generateAndPostProcess(valueContext));
        }
        return result;
    }

    private Object generateAndPostProcess(ValueContext valueContext) {
        Object value = generate(valueContext);
        for (Function<Object, Object> transformer : configuration.getTransformers()) {
            value = transformer.apply(value);
        }
        return value;
    }

    private Object generate(ValueContext valueContext) {
        if (typesUnderConstruction.add(valueContext.getType())) {
            try {
                for (ValueGenerator generator : configuration.getGenerators()) {
                    Object value = generator.generateValue(valueContext);
                    if (value != NO_VALUE) {
                        return value;
                    }
                }
                return handleNoValue(valueContext);
            } catch (FixtureGenerationException e) {
                // No need to wrap it, just propagate
                throw e;
            } catch (Exception e) {
                throw new FixtureGenerationException("Exception was thrown during construction of: " + valueContext.getType().getRawType(), e);
            } finally {
                typesUnderConstruction.remove(valueContext.getType());
            }
        } else {
            return handleCyclicReference(valueContext);
        }
    }

    private Object handleNoValue(ValueContext valueContext) {
        if (configuration.isIgnoreNoValue()) {
            return null;
        } else {
            throw new FixtureGenerationException("Failed to construct: " + valueContext.getType().getRawType() + getCallTrace(true));
        }
    }

    private Object handleCyclicReference(ValueContext valueContext) {
        if (configuration.isIgnoreCyclicReferences()) {
            return null;
        } else {
            throw new FixtureGenerationException("Cyclic reference starting with: " + valueContext.getType().getRawType() + getCallTrace(false));
        }
    }

    private String getCallTrace(boolean skipLast) {
        List<TypeInformation> types = new ArrayList<>(typesUnderConstruction);
        if (skipLast) {
            types.remove(types.size() - 1);
        }
        if (types.isEmpty()) {
            return "";
        } else {
            Collections.reverse(types);
            return types.stream()
                    .map(ValueContextImpl::formatTypeIdentifier)
                    .collect(Collectors.joining(" <- ", " <- ", ""));
        }
    }

    private static String formatTypeIdentifier(TypeInformation typeInformation) {
        String simpleName = typeInformation.getRawType().getSimpleName();
        if (typeInformation.getTypeParameters().isEmpty() || typeInformation.getRawType().isArray()) {
            return simpleName;
        } else {
            return typeInformation.getTypeParameters().stream()
                    .map(ValueContextImpl::formatTypeIdentifier)
                    .collect(Collectors.joining(", ", simpleName + "<", ">"));
        }
    }

}
