package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
/*     umožnujě nám psát vlastní JPQL - Override - tohle je example custom query
     @Query("SELECT s FROM Student s WHERE s.email = ?1")
     Optional<Student> getStudentByEmailAddress(String email);
     Stačí tohle pro JPA abych mohl hledat studenta podle emailu - Je OPtional protože
     máme na email unique constraint, kdyby tam nebyl tak to bude LIST protože může vrátit list studentů*/

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);


/*    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age >=22")
    Optional<Student> selectStudentWhereFirstNameAndAgeGreaterOrEqual(String firstName, Integer age);*/


/*    Naative Metoda na konci názvu určuje že se jedná o SQL query a ne JPQL - jednotlivé parametry ?1 a ?2 jsou order based
    @Query(value = "SELECT * FROM student WHERE first_name = ?1 AND age >= ?2", nativeQuery = true)
    Optional<Student> selectStudentWhereFirstNameAndAgeGreaterOrEqualNative(String firstName, Integer age);*/

    // Naative Metoda na konci názvu určuje že se jedná o SQL query a ne JPQL - jednotlivé parametry ?1 a ?2 jsou param based
    @Query(value = "SELECT * FROM Student WHERE first_name = :firstName AND age >= :age", nativeQuery = true)
    List<Student> selectStudentWhereFirstNameAndAgeGreaterOrEqualNative(
            @Param("firstName") String firstName,
            @Param("age") Integer age);


    //Struktura názvu musí odpovídat JPQL
    //List<Student> findStudentsByFirstNameEqualsAndAgeEquals(String firstName, Integer age);


    //DELETE user by ID
    @Transactional
    @Modifying
    @Query("DELETE FROM Student u WHERE u.id = :studentId")
    int deleteStudentById(@Param("studentId") Long id);

}
