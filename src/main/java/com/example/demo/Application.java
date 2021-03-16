package com.example.demo;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository studentRepository,
            StudentIdCardRepository studentIdCardRepository
    ){

        //Loggerfactory Implementation used for logging and debugging
        Logger logger = LoggerFactory.getLogger(Application.class);
        logger.info("Logger initialized");
        logger.info("Logging starter");

        return args -> {

            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu",firstName,lastName);
            Integer age = faker.number().numberBetween(18,99);

            //Create a new student
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    age
            );


            StudentIdCard studentIdCard = new StudentIdCard("123456789", student);
            studentIdCardRepository.save(studentIdCard);

            studentRepository.findById(1L).ifPresent(System.out::println);

            studentIdCardRepository.findById(1L).ifPresent(System.out::println);

            studentRepository.deleteById(1L);


          /*  //Generate random students with Faker
            generateRandomStudents(studentRepository);

            //Method returns students sorted
            sortingStudents(studentRepository);

            //Method returns pages of size 5 sorted
            paginateStudents(studentRepository);
*/




           /* Student maria1 = new Student(
                    "Maria",
                    "Jhones",
                    "maria.j@gmail.com",
                    21
            );

            Student maria2 = new Student(
                    "Maria",
                    "Smith",
                    "maria.s@gmail.com",
                    21
            );

            Student paul = new Student(
                    "Paul",
                    "Jhones",
                    "paul.j@gmail.com",
                    68
            );


            studentRepository.saveAll(List.of(maria1,paul,maria2));

            // Find by email definované ve StudentRepository pro vyhledání studenta, který jako param obsahuje email
            studentRepository.
                    findStudentByEmail("paul.j@gmail.com").
                    ifPresentOrElse(
                            System.out::println,
                            ()-> System.out.println("Student not found"));


            studentRepository.findStudentsByFirstNameEqualsAndAgeEquals("Maria",21).forEach(System.out::println);

            //Should return student with ID2
            studentRepository
                    .findById(2L)
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("SSS"));

            //Should return error student with ID 3 does not exist
            studentRepository
                    .findById(3L)
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("SSS"));

            List<Student> students = studentRepository.findAll();
            students.forEach(System.out::println);

            //Delete
            studentRepository.deleteById(1L);
            System.out.println("LOG: Student count = " + studentRepository.count());

            studentRepository.selectStudentWhereFirstNameAndAgeGreaterOrEqualNative("Maria", 21).forEach(System.out::println);



            //DELETE STUDENT ID = 3
            System.out.println("Deleting student with ID 3");
            System.out.println(studentRepository.deleteStudentById(3L));*/

        };
    }

    private void paginateStudents(StudentRepository studentRepository) {
        PageRequest pageRequest = PageRequest.of(0,5, Sort.by("firstName").ascending());
        Page<Student> page = studentRepository.findAll(pageRequest);
        //page.hasNext() ; page.nextPageable() ; page.getTotalElements() ;
        System.out.println(page.hasNext());
    }

    private void sortingStudents(StudentRepository studentRepository) {
        //Sort records
        Sort sort = Sort.by( "firstName").ascending().and(Sort.by("age").descending());
        studentRepository.findAll(sort).forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()));
    }

    private void generateRandomStudents(StudentRepository studentRepository) {
        // Initialize Faker
        Faker faker = new Faker();

        //Loop generate new user
        for (int i = 0; i < 20; i++) {
            // Prepare data for student
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu",firstName,lastName);
            Integer age = faker.number().numberBetween(18,99);

            //Create a new student
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    age
            );

            //Save student
            studentRepository.save(student);
        }
    }
}