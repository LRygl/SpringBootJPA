package com.example.demo;

import javax.persistence.*;

@Entity(name = "Student")
@Table(name = "student",uniqueConstraints = {
        @UniqueConstraint(name = "student_email_unique", columnNames = "email")
    }
)
public class Student {
    @Column(name = "id", updatable = false)
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Id
    private Long id;

    @Column(name = "first_name", updatable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name", updatable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "email", updatable = false, columnDefinition = "TEXT")
    private String email;

    @Column(name = "age")
    private Integer age;

    /*
    * Jako první byla definována vazba ve StudentIdCard.java - OneToOne ze StudentIdCard entity na Student
    * Nyní přidáváme novou vazbu i obráceně. Jelikož při použití StudentIdCardRepository při použítí FETCH=EAGER
    * vrací tato metoda i navazující hodnoty, bude možná potřeba realizovat i obráceně. Tedy pokud zavolám StudentRepository
    * tak chci aby mi bylo vráceno i cardID pro daného studenta na kterého se dotazuji
    *
    * Mapování je provedeno jako bi-directional a mapuje se na student, který je vytvořený ve StudentIdCard jako
    * private Student student; - Stejně tak zde byl vytvořen private StudentIdCard studentIdCard;
    *
    * StudentIdCard is the OWNER ENTITY - IT OWNS THE RELATION
    * ORPHAN REMOVA = DEFAULT / FALSE - Nelze smazat záznam studenta jelikož má existující vazbu na parenta
    * ORPHAN REMOVAL = TRUE - Lze smasat, smaže se jak student tak card
    * */


    @OneToOne(mappedBy = "student", orphanRemoval = true)
    private StudentIdCard studentIdCard;

    @OneToMany(mappedBy = "student", orphanRemoval = false)
    private Book book;

    public Student(String firstName,
                   String lastName,
                   String email,
                   Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    //Empty constructor
    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
