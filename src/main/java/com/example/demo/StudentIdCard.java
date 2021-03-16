package com.example.demo;

import javax.persistence.*;

@Entity(name="StudentIdCard")
@Table(
        name="student_id_card",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_id_card_unique",
                        columnNames = "card_number"
                )
        }
)

public class StudentIdCard {
    @Column(name = "id", updatable = false)
    @SequenceGenerator(
            name = "student_card_id_sequence",
            sequenceName = "student_card_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_card_id_sequence"
    )
    @Id
    private Long id;

    @Column(name="card_number",nullable = false,length = 15)
    private String cardNumber;

    //student_id in student_id_card Model, Id in Student table
    //Cascade all dovoluje propagaci všech operací CRUD
    //Fettch EAGER pro 1:1 je OK - loaduje data i o studentovi kdy se ptám pouze na card
    //Je bad pro Manytomany a onetomeny
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_STUDENT_ID_CARD_STUDENT_ID")
    )
    private Student student;

    public StudentIdCard() {
    }

    //Constructor 1 - Card Number
    public StudentIdCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    // Constructor 2 - Card number and Student - this way i can save both in repository
    public StudentIdCard(String cardNumber, Student student) {
        this.cardNumber = cardNumber;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String toString() {
        return "StudentIdCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", student=" + student +
                '}';
    }
}

