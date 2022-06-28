package dev.struchkov.example.optional;

import java.util.Map;
import java.util.Optional;

public class PersonRepository {

    private final Map<Long, Person> persons;

    public PersonRepository(Map<Long, Person> persons) {
        this.persons = persons;
    }

    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(persons.get(id));
    }

    public Optional<Person> findByLogin(String login) {
        for (Person person : persons.values()) {
            if (person.getLogin().equals(login)) {
                return Optional.of(person);
            }
        }
        return Optional.empty();
    }

}
