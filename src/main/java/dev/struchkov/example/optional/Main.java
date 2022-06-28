package dev.struchkov.example.optional;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private final PersonService personService;

    public Main(PersonService personService) {
        this.personService = personService;
    }

    public static void main(String[] args) {
        final Map<Long, Person> personMap = new HashMap<>();

        personMap.put(1L, new Person(1L, "komar", "Dominique", "Hancock"));

        final PersonRepository personRepository = new PersonRepository(personMap);
        final PersonService personService = new PersonService(personRepository);
        final Main main = new Main(personService);
        main.run();
    }

    private void run() {
        System.out.println();
        personService.showFirstAndLastNamesOrDefaultName(100L);
    }


}
