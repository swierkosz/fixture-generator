# fixture-generator

A simple Java library inspired by JFixture for generating test fixtures.

## Usage

FixtureGenerator is the entry point class for the library. It has three public methods:
* `configure` - Method for configuring fluently generation of fixtures.
* `createDeterministic` - Creates an instance of a class specified by type parameter. Calling the method with same arguments will produce equal results.
* `createRandomized` - Creates an instance of a class specified by type parameter. Calling the method with same arguments will produce different results.

See javadocs for more information.

## Example
```java
import com.github.swierkosz.fixture.generator.FixtureGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MyTest {

    @Test
    void shouldDemonstrateFixtureGenerator() {
        // Given
        FixtureGenerator fixtureGenerator = new FixtureGenerator();

        // When
        Pojo result = fixtureGenerator.createDeterministic(Pojo.class);

        // Then
        assertThat(result.stringField).isEqualTo("stringField-09568fd4-7072-3c1d-81dd-f383836cc584");
        assertThat(result.intField).isEqualTo(303335902);
    }

    @Test
    void shouldDemonstrateFixtureGeneratorWithConfiguration() {
        // Given
        FixtureGenerator fixtureGenerator = new FixtureGenerator();
        fixtureGenerator.configure()
                // Intercept and modify fields
                .intercept(Pojo.class, value -> value.intField = 1234)
                // Intercept and transform the value
                .transform(String.class, value -> value.substring(0, 10));

        // When
        Pojo result = fixtureGenerator.createDeterministic(Pojo.class);

        // Then
        assertThat(result.stringField).isEqualTo("stringFiel");
        assertThat(result.intField).isEqualTo(1234);
    }

    private static class Pojo {
        String stringField;
        int intField;
    }

}
```

## Maven
```xml
<dependency>
    <groupId>com.github.swierkosz</groupId>
    <artifactId>fixture-generator</artifactId>
    <version>1.0.0</version>
</dependency>
```