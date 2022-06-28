package dev.struchkov.example.optional;

import java.util.Optional;

import static dev.struchkov.example.optional.NotFoundException.notFoundException;

public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getPersonOrThrow(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    public Person getPersonOrElegantThrow(Long id) {
        return personRepository.findById(id)
                .orElseThrow(notFoundException("Пользователь {0} не найден", id));
    }

    public Optional<String> getFirstAndLastNames(Long id) {
        return personRepository.findById(id)
                .map(person -> person.getFirstName() + " " + person.getLastName());
    }

    public void showFirstAndLastNames(Long id) {
        personRepository.findById(id).ifPresent(
                person -> System.out.println(person.getFirstName() + " " + person.getLastName())
        );
    }

    public void showFirstAndLastNamesOrDefaultName(Long id) {
        personRepository.findById(id).ifPresentOrElse(
                person -> System.out.println(person.getFirstName() + " " + person.getLastName()),
                () -> System.out.println("Иван Иванов")
        );
    }

    public Optional<Person> getAdultById(Long id) {
        return personRepository.findById(id)
                .filter(person -> person.getAge() > 18);
    }

    public Person getPersonOrAnon(Long id) {
        return personRepository.findById(id)
                .orElse(new Person(-1L, "anon", "anon", "anon", 0L));
    }

    public Person getPersonOrAnonWithLog(Long id) {
        return personRepository.findById(id)
                .orElseGet(() -> {
                    System.out.println("Пользователь не был найден, отправляем анонимного");
                    return new Person(-1L, "anon", "anon", "anon", 0L);
                });
    }

    public Optional<Person> getPersonOrAnonOptional(Long id) {
        return personRepository.findById(id)
                .or(() -> Optional.of(new Person(-1L, "anon", "anon", "anon", 0L)));
    }

}
