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
