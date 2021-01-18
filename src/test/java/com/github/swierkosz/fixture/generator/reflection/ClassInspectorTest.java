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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ClassInspectorTest {

    private final ClassInspector classInspector = new ClassInspector();

    @Test
    void shouldListFieldsForSimpleClass() {
        // When
        List<FieldInformation> result = classInspector.listFieldsFor(typeInformation(SimpleClass.class));

        // Then
        Map<String, FieldInformation> resultMap = toMap(result);
        assertThat(resultMap).hasSize(2);
        FieldInformation booleanFieldInformation = resultMap.get("booleanField");
        assertThat(booleanFieldInformation.getType()).isEqualTo(typeInformation(boolean.class));
        assertThat(booleanFieldInformation.getSetter()).isNotNull();

        FieldInformation stringFieldInformation = resultMap.get("stringField");
        assertThat(stringFieldInformation.getType()).isEqualTo(typeInformation(String.class));
        assertThat(stringFieldInformation.getSetter()).isNotNull();
    }

    private static class SimpleClass {
        boolean booleanField;
        String stringField;
    }

    @Test
    void shouldIgnoreStaticFields() {
        // When
        List<FieldInformation> result = classInspector.listFieldsFor(typeInformation(SimpleClassWithStaticField.class));

        // Then
        Map<String, FieldInformation> resultMap = toMap(result);
        assertThat(resultMap).hasSize(1);
        FieldInformation booleanFieldInformation = resultMap.get("booleanField");
        assertThat(booleanFieldInformation.getType()).isEqualTo(typeInformation(boolean.class));
        assertThat(booleanFieldInformation.getSetter()).isNotNull();
    }

    private static class SimpleClassWithStaticField {
        boolean booleanField;
        static String stringField;
    }

    @Test
    void shouldListFieldsForClassWithInheritance() {
        // When
        List<FieldInformation> result = classInspector.listFieldsFor(typeInformation(ClassWithInheritance.class));

        // Then
        Map<String, FieldInformation> resultMap = toMap(result);
        assertThat(resultMap).hasSize(3);
        FieldInformation booleanFieldInformation = resultMap.get("booleanField");
        assertThat(booleanFieldInformation.getType()).isEqualTo(typeInformation(boolean.class));
        assertThat(booleanFieldInformation.getSetter()).isNotNull();

        FieldInformation stringFieldInformation = resultMap.get("stringField");
        assertThat(stringFieldInformation.getType()).isEqualTo(typeInformation(String.class));
        assertThat(stringFieldInformation.getSetter()).isNotNull();

        FieldInformation intFieldInformation = resultMap.get("intField");
        assertThat(intFieldInformation.getType()).isEqualTo(typeInformation(int.class));
        assertThat(intFieldInformation.getSetter()).isNotNull();
    }

    private static class ClassWithInheritance extends SimpleClass {
        int intField;
    }

    @Test
    void shouldListFieldsForClassWithConcreteArray() {
        // When
        List<FieldInformation> result = classInspector.listFieldsFor(typeInformation(ClassWithConcreteArray.class));

        // Then
        Map<String, FieldInformation> resultMap = toMap(result);
        assertThat(resultMap).hasSize(3);
        FieldInformation intArrayFieldInformation = resultMap.get("intArrayField");
        assertThat(intArrayFieldInformation.getType()).isEqualTo(typeInformation(int[].class, typeInformation(int.class)));
        assertThat(intArrayFieldInformation.getSetter()).isNotNull();

        FieldInformation int2dArrayFieldInformation = resultMap.get("int2dArrayField");
        assertThat(int2dArrayFieldInformation.getType()).isEqualTo(typeInformation(int[][].class, typeInformation(int[].class, typeInformation(int.class))));
        assertThat(int2dArrayFieldInformation.getSetter()).isNotNull();

        FieldInformation string2dArrayField = resultMap.get("string2dArrayField");
        assertThat(string2dArrayField.getType()).isEqualTo(typeInformation(String[][].class, typeInformation(String[].class, typeInformation(String.class))));
        assertThat(string2dArrayField.getSetter()).isNotNull();
    }

    private static class ClassWithConcreteArray {
        int[] intArrayField;
        int[][] int2dArrayField;
        String[][] string2dArrayField;
    }

    @ParameterizedTest
    @MethodSource("shouldListFieldsForClassWithGenericsSource")
    void shouldListFieldsForClassWithGenerics(TypeInformation typeInformation) {
        // When
        List<FieldInformation> result = classInspector.listFieldsFor(typeInformation);

        // Then
        Map<String, FieldInformation> resultMap = toMap(result);
        assertThat(resultMap).hasSize(5);
        FieldInformation fieldInformation = resultMap.get("field");
        assertThat(fieldInformation.getType()).isEqualTo(typeInformation(Integer.class));
        assertThat(fieldInformation.getSetter()).isNotNull();

        FieldInformation arrayFieldInformation = resultMap.get("arrayField");
        assertThat(arrayFieldInformation.getType()).isEqualTo(typeInformation(Integer[].class, typeInformation(Integer.class)));
        assertThat(arrayFieldInformation.getSetter()).isNotNull();

        FieldInformation twoDimensionalArrayFieldInformation = resultMap.get("twoDimensionalArrayField");
        assertThat(twoDimensionalArrayFieldInformation.getType()).isEqualTo(typeInformation(Integer[][].class, typeInformation(Integer[].class, typeInformation(Integer.class))));
        assertThat(twoDimensionalArrayFieldInformation.getSetter()).isNotNull();

        FieldInformation listFieldInformation = resultMap.get("listField");
        assertThat(listFieldInformation.getType()).isEqualTo(typeInformation(List.class, typeInformation(Integer.class)));
        assertThat(listFieldInformation.getSetter()).isNotNull();

        FieldInformation mapFieldInformation = resultMap.get("mapField");
        assertThat(mapFieldInformation.getType()).isEqualTo(
                typeInformation(Map.class,
                        typeInformation(List.class, typeInformation(Integer[].class, typeInformation(Integer.class))),
                        typeInformation(Integer.class)
                ));
        assertThat(mapFieldInformation.getSetter()).isNotNull();
    }

    static Stream<Arguments> shouldListFieldsForClassWithGenericsSource() {
        return Stream.of(
                Arguments.of(typeInformation(SpecializedClass.class)),
                Arguments.of(typeInformation(GenericClass.class, typeInformation(Integer.class))),
                Arguments.of(typeInformation(GenericClassWithInheritance.class, typeInformation(Integer.class)))
        );
    }

    private static class SpecializedClass extends GenericClass<Integer> {
    }

    private static class GenericClass<T> {
        T field;
        T[] arrayField;
        T[][] twoDimensionalArrayField;
        List<T> listField;
        Map<List<T[]>, T> mapField;
    }

    private static class GenericClassWithInheritance<Z> extends GenericClass<Z> {
    }

    @Test
    void shouldListConstructorsForClassWithDefaultConstructor() throws Exception {
        // When
        List<ConstructorInformation> result = classInspector.listConstructorsFor(typeInformation(ClassWithDefaultConstructor.class));

        // Then
        assertThat(result).hasSize(1);
        ConstructorInformation constructorInformation = result.get(0);
        assertThat(constructorInformation.getParameterTypes()).isEmpty();

        // Given
        constructorInformation.getConstructor().setAccessible(true);

        // When
        Object instance = constructorInformation.getConstructor().newInstance();

        // Then
        assertThat(instance).isInstanceOf(ClassWithDefaultConstructor.class);
    }

    private static class ClassWithDefaultConstructor {
        int intField;
    }

    @ParameterizedTest
    @MethodSource("shouldListConstructorsForClassWithoutDefaultConstructorSource")
    void shouldListConstructorsForClassWithoutDefaultConstructor(TypeInformation typeInformation) {
        // When
        List<ConstructorInformation> result = classInspector.listConstructorsFor(typeInformation);

        // Then
        assertThat(result).hasSize(1);
        ConstructorInformation constructorInformation = result.get(0);
        List<TypeInformation> parameterTypes = constructorInformation.getParameterTypes();
        assertThat(parameterTypes).hasSize(3);
        assertThat(parameterTypes.get(0)).isEqualTo(typeInformation(int.class));
        assertThat(parameterTypes.get(1)).isEqualTo(typeInformation(String.class));
        assertThat(parameterTypes.get(2)).isEqualTo(typeInformation(Set.class, typeInformation(String.class)));
    }

    static Stream<Arguments> shouldListConstructorsForClassWithoutDefaultConstructorSource() {
        return Stream.of(
                Arguments.of(typeInformation(ClassWithoutDefaultConstructor.class)),
                Arguments.of(typeInformation(BaseClassWithoutDefaultConstructor.class, typeInformation(String.class)))
        );
    }

    private static class ClassWithoutDefaultConstructor extends BaseClassWithoutDefaultConstructor<String> {
        public ClassWithoutDefaultConstructor(int intField, String stringField, Set<String> setField) {
            super(intField, stringField, setField);
        }
    }

    private static class BaseClassWithoutDefaultConstructor<T> {
        final int intField;
        final String stringField;
        final Set<T> setField;

        public BaseClassWithoutDefaultConstructor(int intField, String stringField, Set<T> setField) {
            this.intField = intField;
            this.stringField = stringField;
            this.setField = setField;
        }
    }

    private static Map<String, FieldInformation> toMap(Collection<FieldInformation> set) {
        return Maps.uniqueIndex(set, FieldInformation::getName);
    }

    private static TypeInformation typeInformation(Class<?> type, TypeInformation... typeParameters) {
        return new TypeInformationImpl(type, ImmutableList.copyOf(typeParameters));
    }
}