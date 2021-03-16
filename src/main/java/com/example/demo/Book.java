package com.example.demo;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Book")
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id",updatable = false)
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    private Long id;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id",referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_BOOK_ID_TO_STUDENT_ID"))
    private Student student;

    public Book() {
    }

    public Book(Long id, Integer studentId, String bookName, Date createdAt, Student student) {
        this.id = id;
        this.bookName = bookName;
        this.createdAt = createdAt;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", createdAt=" + createdAt +
                ", student=" + student +
                '}';
    }
}
