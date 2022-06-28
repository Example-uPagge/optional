package dev.struchkov.example.optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OptionalTest {

    static final Long KOMAR_ID = 1L;
    static final Person komar = new Person(KOMAR_ID, "komar", "Алексей", "ertyuiop");

    PersonRepository personService;

    @BeforeEach
    public void beforeEach() {
        final Map<Long, Person> persons = new HashMap<>();
        persons.put(KOMAR_ID, komar);

        personService = new PersonRepository(persons);
    }

    @Test
    @DisplayName("Создание Optional через ofNullable()")
    void createOptionalByOfNullable() {
        final Optional<Person> optionalPerson = personService.findById(KOMAR_ID);

        assertTrue(optionalPerson.isPresent());
        assertFalse(optionalPerson.isEmpty());
        assertEquals(komar, optionalPerson.get());
    }

    @Test
    @DisplayName("Создание пустого Optional через ofNullable()")
    void createNullOptionalByOfNullable() {
        final String testString = null;
        final Optional<String> testOptional = Optional.ofNullable(testString);

        assertEquals(testOptional, Optional.empty());
        assertFalse(testOptional.isPresent());
        assertTrue(testOptional.isEmpty());
        assertNotEquals(testOptional, null);
    }

    @Test
    @DisplayName("Создание Optional через of()")
    void createByOf() {
        final String testString = "Test String";
        final Optional<String> testOptional = Optional.of(testString);

        assertEquals(testString, testOptional.get());
    }

    @Test
    @DisplayName("Создание пустого Optional через of")
    void createNullOptionalByOf() {
        final String testString = null;

        assertThrows(NullPointerException.class, () -> {
            final Optional<String> testOptional = Optional.of(testString);
        });
    }

    @Test
    @DisplayName("Вызываем get у пустого Optional")
    void getForNullOptional() {
        final String testString = null;
        final Optional<String> testOptional = Optional.ofNullable(testString);

        assertFalse(testOptional.isPresent());
        assertThrows(NoSuchElementException.class, testOptional::get);
    }

    @Test
    @DisplayName("Проверка метода ifPresent()")
    void useIfPresentMethod() {
        final String testString = "test string";
        final Optional<String> testOptional = Optional.of(testString);

        final Consumer<String> ourConsumer = ourTestSpring -> {
            System.out.println(ourTestSpring);
            assertEquals(ourTestSpring, testString);
        };

        testOptional.ifPresent(ourConsumer);
    }

    @Test
    @DisplayName("Проверка метода ifPresentOrElse()")
    void useIfPresentOrElse() {
        final String testString = null;
        final Optional<String> testOptional = Optional.ofNullable(testString);

        testOptional.ifPresentOrElse(
                ourTestSpring -> System.out.println(ourTestSpring),
                () -> System.out.println("Null String in optional")
        );
    }

    @Test
    @DisplayName("Используем метод orElse")
    void useGetOrElse() {
        final String testString = null;
        final Optional<String> testOptional = Optional.ofNullable(testString);

        final String valueOfOptional = testOptional.orElse("Default String");

        assertEquals("Default String", valueOfOptional);
    }

    @Test
    @DisplayName("Используем метод orElseGet")
    void useGetOrElseGet() {
        final String testString = null;
        final Optional<String> testOptional = Optional.ofNullable(testString);

        final String valueOfOptional = testOptional.orElseGet(() -> {
            System.out.println("String is null");
            return "Default String";
        });

        assertEquals("Default String", valueOfOptional);
    }

    @Test
    @DisplayName("Проверяем метод orElseThrow")
    void useOrElseThrow() {
        final String testString = null;
        final Optional<String> testOptional = Optional.ofNullable(testString);

        assertThrows(
                NoSuchElementException.class,
                () -> testOptional.orElseThrow()
        );
    }


    @Test
    @DisplayName("Проверяем метод orElseThrow со своим исключением")
    void useOrElseThrowRuntimeException() {
        final String testString = null;
        final Optional<String> testOptional = Optional.ofNullable(testString);

        assertThrows(
                RuntimeException.class,
                () -> testOptional.orElseThrow(() -> new RuntimeException("Null String in optional"))
        );
    }

    @Test
    @DisplayName("Проверка метода filter")
    void useFilter() {
        final String testString = "test string";
        final Optional<String> testOptional = Optional.of(testString);

        final Optional<String> filteredOptional = testOptional
                .filter(ourTestSpring -> ourTestSpring.equals("test string"));
        final String valueOfOptional = filteredOptional.get();

        assertTrue(filteredOptional.isPresent());
        assertEquals("test string", valueOfOptional);
    }

    @Test
    @DisplayName("Проверка метода map")
    void useMap() {
        final String testString = "test string";
        final Optional<String> testOptional = Optional.of(testString);

        final Optional<Integer> mappedOptional = testOptional
                .map(ourTestSpring -> ourTestSpring.length());
        final Integer lengthString = mappedOptional.get();

        assertTrue(mappedOptional.isPresent());
        assertEquals(testString.length(), lengthString);
    }

    @Test
    @DisplayName("Проверка метода flatMap")
    void useFlatMap() {
        final String testString = "test string";
        final Optional<String> testOptional = Optional.of(testString);

        final Optional<String> mappedOptional = testOptional
                .flatMap(ourTestSpring -> Optional.of(ourTestSpring.toUpperCase()));
        final String valueOfOptional = mappedOptional.get();

        assertTrue(mappedOptional.isPresent());
        assertEquals("TEST STRING", valueOfOptional);
    }

    @Test
    @DisplayName("Проверка метода or")
    void useOr() {
        final String testString = null;
        final Optional<String> testOptional = Optional.ofNullable(testString);

        final Optional<String> optDefaultString = testOptional.or(() -> Optional.of("Default String"));
        final String valueOfOptional = optDefaultString.get();

        assertEquals("Default String", valueOfOptional);
    }

}
