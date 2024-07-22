package com.github.swierkosz.fixture.generator;
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

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Deque;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

class FixtureGeneratorTest {

    private final FixtureGenerator fixtureGenerator = new FixtureGenerator();

    @Test
    void shouldCreateDeterministicTestClassWithPrimitiveFields() {
        // When
        TestClassWithPrimitiveFields result = fixtureGenerator.createDeterministic(TestClassWithPrimitiveFields.class);

        // Then
        assertThat(result.booleanField).isEqualTo(false);
        assertThat(result.byteField).isEqualTo((byte) 16);
        assertThat(result.characterField).isEqualTo('T');
        assertThat(result.shortField).isEqualTo((short) 26264);
        assertThat(result.integerField).isEqualTo(254947610);
        assertThat(result.longField).isEqualTo(9150660109912313462L);
        assertThat(result.floatField).isEqualTo(864993.8f);
        assertThat(result.doubleField).isEqualTo(1678954.9);
    }

    private static class TestClassWithPrimitiveFields {

        boolean booleanField;
        byte byteField;
        char characterField;
        short shortField;
        int integerField;
        long longField;
        float floatField;
        double doubleField;

    }

    @Test
    void shouldCreateDeterministicTestClassWithBoxedFields() {
        // When
        TestClassWithBoxedFields result = fixtureGenerator.createDeterministic(TestClassWithBoxedFields.class);

        // Then
        assertThat(result.booleanField).isEqualTo(false);
        assertThat(result.byteField).isEqualTo((byte) 16);
        assertThat(result.characterField).isEqualTo('T');
        assertThat(result.shortField).isEqualTo((short) 26264);
        assertThat(result.integerField).isEqualTo(254947610);
        assertThat(result.longField).isEqualTo(9150660109912313462L);
        assertThat(result.floatField).isEqualTo(864993.8f);
        assertThat(result.doubleField).isEqualTo(1678954.9);
    }

    private static class TestClassWithBoxedFields {

        Boolean booleanField;
        Byte byteField;
        Character characterField;
        Short shortField;
        Integer integerField;
        Long longField;
        Float floatField;
        Double doubleField;

    }

    @Test
    void shouldCreateDeterministicTestClassWithArrays() {
        // When
        TestClassWithArrays result = fixtureGenerator.createDeterministic(TestClassWithArrays.class);

        // Then
        assertThat(result.booleanArray).containsExactly(false, true, false);
        assertThat(result.byteArray).containsExactly(30, 43, -57);
        assertThat(result.characterArray).containsExactly('G', 'X', 'A');
        assertThat(result.shortArray).containsExactly(3136, 2350, -18218);
        assertThat(result.integerArray).containsExactly(-2114316906, 1351460435, -1623121394);
        assertThat(result.longArray).containsExactly(6352455206930730101L, 696449787518465034L, 2956100878239368784L);
        assertThat(result.floatArray).containsExactly(-1871629.6f, 1771556.8f, -637592.44f);
        assertThat(result.doubleArray).containsExactly(-1955625.589, 677163.213, -796981.021);
        assertThat(result.uuidArray).containsExactly(
                UUID.fromString("33e6ca28-8d97-363a-8d4e-11b46096743e"),
                UUID.fromString("ab3e1734-7f8f-3120-bbb4-f7a21bfb40fa"),
                UUID.fromString("00f176d0-6ccb-3bae-8c76-d92f1c2028a3"));
        assertThat(result.byte2dArray).isEqualTo(new byte[][]{{-126, 94, -114}, {-33, 74, 65}, {-21, 127, 116}});
    }

    private static class TestClassWithArrays {

        boolean[] booleanArray;
        byte[] byteArray;
        char[] characterArray;
        short[] shortArray;
        int[] integerArray;
        long[] longArray;
        float[] floatArray;
        double[] doubleArray;
        UUID[] uuidArray;
        byte[][] byte2dArray;

    }

    @Test
    void shouldCreateDeterministicTestClassWithMiscTypes() {
        // When
        TestClassWithMiscTypes result = fixtureGenerator.createDeterministic(TestClassWithMiscTypes.class);

        // Then
        assertThat(result.objectField).isExactlyInstanceOf(Object.class);
        assertThat(result.stringField).isEqualTo("stringField-09568fd4-7072-3c1d-81dd-f383836cc584");
        assertThat(result.bigDecimalField).isEqualTo(new BigDecimal("-3688168203866324.5"));
        assertThat(result.bigIntegerField).isEqualTo(new BigInteger("-2942810233360991754"));
        assertThat(result.uuidField).isEqualTo(UUID.fromString("4569f91d-2dd5-35cd-8603-3f62db2d373b"));
        assertThat(result.enumField).isEqualTo(TestEnum.VALUE1);
        assertThat(result.dateField).isEqualTo(new Date(1455984452620L));
        GregorianCalendar expectedCalendar = new GregorianCalendar();
        expectedCalendar.setTimeInMillis(949578827448L);
        assertThat(result.calendar).isEqualTo(expectedCalendar);
        assertThat(result.timeZone).isEqualTo(TimeZone.getTimeZone("Australia/Tasmania"));
        assertThat(result.optional).hasValue("optional-c5ef4b95-5ca5-3e5e-9a8e-91dd8b3c3996");
        assertThat(result.optionalInt).hasValue(485775616);
        assertThat(result.optionalLong).hasValue(-7776313108192547524L);
        assertThat(result.optionalDouble).hasValue(-571863.09);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static class TestClassWithMiscTypes {

        Object objectField;
        String stringField;
        BigDecimal bigDecimalField;
        BigInteger bigIntegerField;
        UUID uuidField;
        TestEnum enumField;
        Date dateField;
        Calendar calendar;
        TimeZone timeZone;
        Optional<String> optional;
        OptionalInt optionalInt;
        OptionalLong optionalLong;
        OptionalDouble optionalDouble;

    }

    private enum TestEnum {
        VALUE1, VALUE2, VALUE3
    }

    @Test
    void shouldIgnoreStaticFields() {
        // When
        fixtureGenerator.createDeterministic(TestClassWithStaticFields.class);

        // Then
        assertThat(TestClassWithStaticFields.stringField).isNull();
    }

    private static class TestClassWithStaticFields {

        static String stringField;

    }

    @Test
    void shouldCreateDeterministicTestClassWithGenericFields() {
        // When
        TestClassWithGenericFields result = fixtureGenerator.createDeterministic(TestClassWithGenericFields.class);

        // Then
        assertThat(result.nonGenericField).isEqualTo(4498685320103737957L);
        assertThat(result.genericField).isEqualTo(-998140668);
        assertThat(result.genericArrayField)
                .isInstanceOf(Integer[].class)
                .containsExactly(-317659813, 1066978622, 334328014);
        assertThat(result.generic2dArrayField)
                .isInstanceOf(Integer[][].class)
                .isEqualTo(new Integer[][]{{485706256, 1360074792, -212260867}, {-1898279121, 104990488, 786089418}, {73499670, -1255213771, -1658036559}});
    }

    private static class TestClassWithGenericFields extends TestClassBaseWithGenericFields<Integer> {

    }

    private static class TestClassBaseWithGenericFields<T> {

        long nonGenericField;
        T genericField;
        T[] genericArrayField;
        T[][] generic2dArrayField;

    }

    @Test
    void shouldCreateDeterministicTestClassWithCollections() {
        // When
        TestClassWithCollections result = fixtureGenerator.createDeterministic(TestClassWithCollections.class);

        // Then
        assertThat(result.collection).containsExactly(-1843759460, 1404550549, -2026141693);
        assertThat(result.list).containsExactly(364081409, -1803931803, 1436889296);
        assertThat(result.arrayList).containsExactly(-1562832347, 1784123885, -1399152520);
        assertThat(result.linkedList).containsExactly(752929627, -1864776586, 1487173431);
        assertThat(result.set).containsExactly(1470689271, -1027664039, -730147211);
        assertThat(result.hashSet).containsExactlyInAnyOrder(1981718553, 1207569541, 83464358);
        assertThat(result.linkedHashSet).containsExactly(1433787392, 2005269781, 2102228394);
        assertThat(result.sortedSet).containsExactly(-1188273037, 1866132387, 2037921577);
        assertThat(result.navigableSet).containsExactly(-2002290545, -56300848, 77203634);
        assertThat(result.treeSet).containsExactly(884447480, 1726024139, 2046492061);
        assertThat(result.queue).containsExactly(1762985327, -1538953926, -1117422962);
        assertThat(result.deque).containsExactly(-634401951, 801159369, -1618375301);
        assertThat(result.vector).containsExactly(-1969052177, -1822093618, 1285349790);
        assertThat(result.stack).containsExactly(-1811459804, -515105706, -1536456603);
    }

    private static class TestClassWithCollections {

        Collection<Integer> collection;
        List<Integer> list;
        ArrayList<Integer> arrayList;
        LinkedList<Integer> linkedList;
        Set<Integer> set;
        HashSet<Integer> hashSet;
        LinkedHashSet<Integer> linkedHashSet;
        SortedSet<Integer> sortedSet;
        NavigableSet<Integer> navigableSet;
        TreeSet<Integer> treeSet;
        Queue<Integer> queue;
        Deque<Integer> deque;
        Vector<Integer> vector;
        Stack<Integer> stack;

    }

    @Test
    void shouldCreateDeterministicTestClassWithGenericCollections() {
        // When
        TestClassWithGenericCollections result = fixtureGenerator.createDeterministic(TestClassWithGenericCollections.class);

        // Then
        assertThat(result.collection).containsExactly(-1843759460, 1404550549, -2026141693);
        assertThat(result.list).containsExactly(364081409, -1803931803, 1436889296);
        assertThat(result.arrayList).containsExactly(-1562832347, 1784123885, -1399152520);
        assertThat(result.linkedList).containsExactly(752929627, -1864776586, 1487173431);
        assertThat(result.set).containsExactly(1470689271, -1027664039, -730147211);
        assertThat(result.hashSet).containsExactlyInAnyOrder(1981718553, 1207569541, 83464358);
        assertThat(result.linkedHashSet).containsExactly(1433787392, 2005269781, 2102228394);
        assertThat(result.sortedSet).containsExactly(-1188273037, 1866132387, 2037921577);
        assertThat(result.navigableSet).containsExactly(-2002290545, -56300848, 77203634);
        assertThat(result.treeSet).containsExactly(884447480, 1726024139, 2046492061);
        assertThat(result.queue).containsExactly(1762985327, -1538953926, -1117422962);
        assertThat(result.deque).containsExactly(-634401951, 801159369, -1618375301);
        assertThat(result.vector).containsExactly(-1969052177, -1822093618, 1285349790);
        assertThat(result.stack).containsExactly(-1811459804, -515105706, -1536456603);
    }

    private static class TestClassWithGenericCollections extends TestClassBaseWithGenericCollections<Integer> {

    }

    private static class TestClassBaseWithGenericCollections<T> {

        Collection<T> collection;
        List<T> list;
        ArrayList<T> arrayList;
        LinkedList<T> linkedList;
        Set<T> set;
        HashSet<T> hashSet;
        LinkedHashSet<T> linkedHashSet;
        SortedSet<T> sortedSet;
        NavigableSet<T> navigableSet;
        TreeSet<T> treeSet;
        Queue<T> queue;
        Deque<T> deque;
        Vector<T> vector;
        Stack<T> stack;

    }

    @Test
    void shouldCreateDeterministicTestClassWithGenericMaps() {
        // When
        TestClassWithGenericMaps result = fixtureGenerator.createDeterministic(TestClassWithGenericMaps.class);

        // Then
        assertThat(result.map)
                .isInstanceOf(LinkedHashMap.class)
                .hasSize(3)
                .containsEntry(1308101242, "map-value-7fd2c5ed-024f-3fd0-9ddc-b2b40bd8e0c1")
                .containsEntry(1835752923, "map-value-9e9668cf-1658-35cb-9ca7-64631b15de27")
                .containsEntry(2007266947, "map-value-af236fe3-0f83-3a1b-8e69-1639dd64d64e");
        assertThat(result.sortedMap)
                .isInstanceOf(TreeMap.class)
                .hasSize(3)
                .containsEntry(3844843, "sortedMap-value-f798544b-9ced-30a3-bdd7-1c7ac779d85c")
                .containsEntry(1008667453, "sortedMap-value-fcd917fb-6e36-3811-918f-0a3db654e63c")
                .containsEntry(1745290344, "sortedMap-value-a62f76be-8bcd-3581-9886-0f959e70c099");
        assertThat(result.navigableMap)
                .isInstanceOf(TreeMap.class)
                .hasSize(3)
                .containsEntry(-1446167973, "navigableMap-value-4ed4c841-2ab9-3bde-bb0d-04ca911bf8b8")
                .containsEntry(48323405, "navigableMap-value-f42a35f5-dc12-3610-97ce-c69e85d350cf")
                .containsEntry(2109647268, "navigableMap-value-6d82be93-322d-3fad-8cc8-1c4bdd529af9");
        assertThat(result.hashMap)
                .hasSize(3)
                .containsEntry(-1294647894, "hashMap-value-3c1556ab-4de2-30d8-8b6a-c3363f2b073b")
                .containsEntry(-1182873173, "hashMap-value-6078383e-506d-330c-8895-a0f0bada9cbc")
                .containsEntry(1684838814, "hashMap-value-a362612d-bd95-3b86-97e3-5aaafa981e78");
        assertThat(result.linkedHashMap)
                .hasSize(3)
                .containsEntry(-2134294008, "linkedHashMap-value-0f8a6d3f-7907-33b9-8eca-a2c69f18370c")
                .containsEntry(-2015486492, "linkedHashMap-value-6e3c6397-4fc1-31e3-880d-25b9644731a1")
                .containsEntry(1623334746, "linkedHashMap-value-3d454cdb-2639-30f2-8f7c-581312438323");
        assertThat(result.hashtable)
                .hasSize(3)
                .containsEntry(-1254374329, "hashtable-value-09d3a56c-8ce9-3190-8faa-450f7f02e6d9")
                .containsEntry(-1045433417, "hashtable-value-2e21addf-ee5f-3e53-af93-8af70303b17a")
                .containsEntry(-944308288, "hashtable-value-bc569f4f-e189-3fc1-bf7e-26e5fe423ee4");
        assertThat(result.treeMap)
                .hasSize(3)
                .containsEntry(-21942582, "treeMap-value-a4f2d042-c694-32b0-b310-93011bfc4aa3")
                .containsEntry(51145327, "treeMap-value-08a5429d-7336-3a8d-9bf0-ec97e202260e")
                .containsEntry(1036328176, "treeMap-value-80d2f2bc-92f9-3f18-9f51-d3e2a345d3d3");
    }

    private static class TestClassWithGenericMaps extends TestClassBaseWithGenericMaps<Integer, String> {

    }

    private static class TestClassBaseWithGenericMaps<K, V> {

        Map<K, V> map;
        SortedMap<K, V> sortedMap;
        NavigableMap<K, V> navigableMap;
        HashMap<K, V> hashMap;
        LinkedHashMap<K, V> linkedHashMap;
        Hashtable<K, V> hashtable;
        TreeMap<K, V> treeMap;

    }

    @Test
    void shouldCreateDeterministicTestClassWithDeepGenerics() {
        // When
        TestClassWithDeepGenerics result = fixtureGenerator.createDeterministic(TestClassWithDeepGenerics.class);

        // Then
        assertThat(result.inner.field1).isEqualTo(-1455021897);
        assertThat(result.inner.field2).containsExactly(1199802702, 1234106591, -550836707);
        assertThat(result.inner.field3).containsExactly(-939938044, -456008426, 1365986789);
        assertThat(result.inner.field4).isEqualTo(-1390768830);
    }

    private static class TestClassWithDeepGenerics {

        TestClassWithDeepGenericsInner2<Set<Integer>, Integer> inner;

    }

    private static class TestClassWithDeepGenericsInner2<A, B> extends TestClassWithDeepGenericsInner1<B, A> {

        A field3;
        B field4;

    }

    private static class TestClassWithDeepGenericsInner1<A, B> {

        A field1;
        B field2;

    }

    @Test
    void shouldCreateDeterministicTestClassWithJavaTimeClasses() {
        // When
        TestClassWithJavaTimeClasses result = fixtureGenerator.createDeterministic(TestClassWithJavaTimeClasses.class);

        // Then
        assertThat(result.duration).isEqualTo(Duration.parse("PT3863H4M57.868141824S"));
        assertThat(result.instant).isEqualTo(Instant.parse("1976-03-31T08:48:06.511266481Z"));
        assertThat(result.localDate).isEqualTo(LocalDate.parse("1999-07-08"));
        assertThat(result.localTime).isEqualTo(LocalTime.parse("11:36:37.608003390"));
        assertThat(result.localDateTime).isEqualTo(LocalDateTime.parse("1987-02-08T00:07:41.410647409"));
        assertThat(result.monthDay).isEqualTo(MonthDay.of(5, 7));
        assertThat(result.offsetDateTime).isEqualTo(OffsetDateTime.parse("1978-11-15T16:32:13.907817200-11:00"));
        assertThat(result.offsetTime).isEqualTo(OffsetTime.parse("19:10:28.857831130+03:00"));
        assertThat(result.period).isEqualTo(Period.parse("P9Y5M8D"));
        assertThat(result.year).isEqualTo(Year.of(1997));
        assertThat(result.yearMonth).isEqualTo(YearMonth.of(1988, 12));
        assertThat(result.zonedDateTime).isEqualTo(ZonedDateTime.parse("2002-05-10T19:51:52.551515351-05:00[Canada/Central]"));
        assertThat(result.zoneId).isEqualTo(ZoneId.of("America/Nuuk"));
        assertThat(result.zoneOffset).isEqualTo(ZoneOffset.ofHours(-11));
    }

    private static class TestClassWithJavaTimeClasses {

        Duration duration;
        Instant instant;
        LocalDate localDate;
        LocalTime localTime;
        LocalDateTime localDateTime;
        MonthDay monthDay;
        OffsetDateTime offsetDateTime;
        OffsetTime offsetTime;
        Period period;
        Year year;
        YearMonth yearMonth;
        ZonedDateTime zonedDateTime;
        ZoneId zoneId;
        ZoneOffset zoneOffset;

    }

    @Test
    void shouldCreateDeterministicTestClassWithFinalFieldsAndWithoutDefaultConstructor() {
        // When
        TestClassWithFinalFieldsAndWithoutDefaultConstructor result = fixtureGenerator.createDeterministic(TestClassWithFinalFieldsAndWithoutDefaultConstructor.class);

        // Then
        assertThat(result.intField).isEqualTo(303335902);
        assertThat(result.stringField).isEqualTo("stringField-09568fd4-7072-3c1d-81dd-f383836cc584");
        assertThat(result.setField).containsExactly(652111919, -2012151326, 237287371);
    }

    private static class TestClassWithFinalFieldsAndWithoutDefaultConstructor {

        final int intField;
        final String stringField;
        final Set<Integer> setField;

        private TestClassWithFinalFieldsAndWithoutDefaultConstructor(int intField, String stringField, Set<Integer> setField) {
            this.intField = intField;
            this.stringField = stringField;
            this.setField = setField;
        }

    }

    @Test
    void shouldCreateDeterministicTestClassWithoutDefaultConstructor() {
        // When
        TestClassWithoutDefaultConstructor result = fixtureGenerator.createDeterministic(TestClassWithoutDefaultConstructor.class);

        // Then
        assertThat(result.testField.list).containsExactly(858912947, -709195832, -1953750246);
    }

/* FIXME: Test is commented out, because I did not set up maven build to run tests under java 17

    private static record TestRecord(String name, String address) {
    }

    @Test
    void shouldCreateRecord() {
        // When
        TestRecord result = fixtureGenerator.createDeterministic(TestRecord.class);

        // Then
        assertThat(result.name()).isEqualTo("name-4db11361-7395-3a23-ab85-f89277e25541");
        assertThat(result.address()).isEqualTo("address-3e556675-aaa3-33f3-a317-9370a46f2592");
    }
*/

    private static class TestClassWithoutDefaultConstructor {

        final TestClassWithGenericsAndWithoutDefaultConstructor<Integer> testField;

        private TestClassWithoutDefaultConstructor(TestClassWithGenericsAndWithoutDefaultConstructor<Integer> testField) {
            this.testField = testField;
        }

    }

    private static class TestClassWithGenericsAndWithoutDefaultConstructor<T> {

        final List<T> list;

        private TestClassWithGenericsAndWithoutDefaultConstructor(List<T> list) {
            this.list = list;
        }

    }

    @Test
    void shouldThrowExceptionForTestClassWithCyclicReferenceA() {
        // When
        Exception exception = catchException(() -> fixtureGenerator.createDeterministic(TestClassWithCyclicReferenceA.class));

        // Then
        assertThat(exception)
                .isInstanceOf(FixtureGenerationException.class)
                .hasMessage("Cyclic reference starting with: class com.github.swierkosz.fixture.generator.FixtureGeneratorTest$TestClassWithCyclicReferenceB <- TestClassWithCyclicReferenceC <- List<TestClassWithCyclicReferenceC> <- TestClassWithCyclicReferenceB <- TestClassWithCyclicReferenceB[] <- TestClassWithCyclicReferenceA");
    }

    @Test
    void shouldIgnoreForTestClassWithCyclicReferenceA() {
        // Given
        fixtureGenerator.configure()
                .ignoreCyclicReferences();

        // When
        TestClassWithCyclicReferenceA result = fixtureGenerator.createDeterministic(TestClassWithCyclicReferenceA.class);

        // Then
        assertThat(result.referencesToB).isNotNull();
        assertThat(result.referencesToB[0].referencesToC).isNotNull();
        assertThat(result.referencesToB[0].referencesToC.get(0).referenceToB).isNull();
    }

    private static class TestClassWithCyclicReferenceA {

        TestClassWithCyclicReferenceB[] referencesToB;

    }

    private static class TestClassWithCyclicReferenceB {

        List<TestClassWithCyclicReferenceC> referencesToC;

    }

    private static class TestClassWithCyclicReferenceC {

        TestClassWithCyclicReferenceB referenceToB;

    }

    @Test
    void shouldThrowExceptionForTestClassWithNoValueWithCallTrace() {
        // When
        Exception exception = catchException(() -> fixtureGenerator.createDeterministic(TestClassWithNoValue.class));

        // Then
        assertThat(exception)
                .isInstanceOf(FixtureGenerationException.class)
                .hasMessage("Failed to construct: interface com.github.swierkosz.fixture.generator.FixtureGeneratorTest$InterfaceWithNoImplementation <- TestClassWithNoValue");
    }

    @Test
    void shouldThrowExceptionForTestClassWithNoValueWithoutCallTrace() {
        // When
        Exception exception = catchException(() -> fixtureGenerator.createDeterministic(InterfaceWithNoImplementation.class));

        // Then
        assertThat(exception)
                .isInstanceOf(FixtureGenerationException.class)
                .hasMessage("Failed to construct: interface com.github.swierkosz.fixture.generator.FixtureGeneratorTest$InterfaceWithNoImplementation");
    }

    @Test
    void shouldIgnoreForTestClassWithNoValue() {
        // Given
        fixtureGenerator.configure()
                .ignoreNoValue();

        // When
        TestClassWithNoValue result = fixtureGenerator.createDeterministic(TestClassWithNoValue.class);

        // Then
        assertThat(result.fieldWithNoValue).isNull();
    }

    private static class TestClassWithNoValue {

        InterfaceWithNoImplementation fieldWithNoValue;

    }

    interface InterfaceWithNoImplementation {

    }

    @Test
    void shouldInterceptWithType() {
        // Given
        fixtureGenerator.configure()
                .intercept(TestClassWithMiscTypes.class, value -> value.stringField = "my custom string");

        // When
        TestClassWithMiscTypes result = fixtureGenerator.createDeterministic(TestClassWithMiscTypes.class);

        // Then
        assertThat(result.stringField).isEqualTo("my custom string");
    }

    @Test
    void shouldInterceptWithoutType() {
        // Given
        fixtureGenerator.configure()
                .intercept(value -> {
                    if (value instanceof TestClassWithMiscTypes) {
                        ((TestClassWithMiscTypes) value).stringField = "my custom string";
                    }
                });

        // When
        TestClassWithMiscTypes result = fixtureGenerator.createDeterministic(TestClassWithMiscTypes.class);

        // Then
        assertThat(result.stringField).isEqualTo("my custom string");
    }

    @Test
    void shouldTransform() {
        // Given
        fixtureGenerator.configure()
                .transform(Instant.class, value -> value.truncatedTo(ChronoUnit.SECONDS));

        // When
        TestClassWithJavaTimeClasses result = fixtureGenerator.createDeterministic(TestClassWithJavaTimeClasses.class);

        // Then
        assertThat(result.instant).isEqualTo(Instant.parse("1976-03-31T08:48:06Z"));
    }

    interface TestInterface {

    }

    private static class TestClassImplementingInterface1 implements TestInterface {

        String stringField;

    }

    private static class TestClassImplementingInterface2 implements TestInterface {

        int intField;

    }

    @Test
    void shouldSubclass() {
        // Given
        fixtureGenerator.configure()
                .subclass(TestInterface.class, TestClassImplementingInterface1.class, TestClassImplementingInterface2.class);

        // When
        TestInterface result = fixtureGenerator.createDeterministic(TestInterface.class);

        // Then
        assertThat(result).isInstanceOf(TestClassImplementingInterface1.class);
        TestClassImplementingInterface1 testClass = (TestClassImplementingInterface1) result;
        assertThat(testClass.stringField).isNotNull();
    }

    @Test
    void shouldDefineGeneratorWithType() {
        // Given
        fixtureGenerator.configure()
                .defineGenerator(long.class, valueContext -> 42L);

        // When
        Long result = fixtureGenerator.createRandomized(long.class);

        // Then
        assertThat(result).isEqualTo(42);
    }

    @Test
    void shouldDefineGeneratorWithoutType() {
        // Given
        fixtureGenerator.configure()
                .defineGenerator(valueContext -> "Strings everywhere");

        // When
        Object result = fixtureGenerator.createRandomized(Object.class);

        // Then
        assertThat(result).isEqualTo("Strings everywhere");
    }

    @Test
    void shouldCreateRandomized() {
        // Given
        Set<Integer> values = new HashSet<>();

        // When
        for (int i = 0; i < 10; i++) {
            values.add(fixtureGenerator.createRandomized(int.class));
        }

        // Then
        assertThat(values).hasSizeGreaterThan(1);
    }

    @Test
    void shouldConfigureFluently() {
        // When
        FixtureGenerator result = fixtureGenerator.configure()
                .done();

        // Then
        assertThat(result).isSameAs(fixtureGenerator);
    }

    @Test
    void shouldHandleExceptions() {
        // Given
        fixtureGenerator.configure()
                .defineGenerator(valueContext -> {
                    throw new RuntimeException("Something went wrong");
                });

        // When
        Exception exception = catchException(() -> fixtureGenerator.createRandomized(Object.class));

        // Then
        assertThat(exception)
                .isInstanceOf(FixtureGenerationException.class)
                .hasMessage("Exception was thrown during construction of: class java.lang.Object")
                .hasRootCauseInstanceOf(RuntimeException.class)
                .hasRootCauseMessage("Something went wrong");
    }

}