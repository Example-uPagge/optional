package dev.struchkov.example.optional;

public class Person {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private Long age;

    public Person(Long id, String login, String firstName, String lastName, Long age) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getAge() {
        return age;
    }

}
