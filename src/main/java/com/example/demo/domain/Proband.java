package com.example.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class Proband {
        @Id
        @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Long id;

        @Column(nullable = false)
        @NotBlank(message = "Jméno je povinné")
        private String firstName;

        @Column(nullable = false)
        @NotBlank(message = "Příjmení je povinné")
        private String lastName;

        @Column(nullable = false)
        @NotNull(message = "Datum narození je povinné")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate dateOfBirth;

        @Column(nullable = true)
        private Integer ringSize;

        @Column(nullable = false)
        @NotBlank(message = "Telefonní číslo je povinné")
        private String phoneNumber;

        @Column(nullable = false, unique = true)
        @NotBlank(message = "E-mail je povinný")
        @Email(message = "Zadejte prosím platnou e-mailovou adresu")
        private String email;

        public Proband() {
        }

        // Getters
        public Long getId() {
                return id;
        }

        public String getFirstName() {
                return firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public LocalDate getDateOfBirth() {
                return dateOfBirth;
        }

        public Integer getRingSize() {
                return ringSize;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public String getEmail() {
                return email;
        }

        // Setters
        public void setId(Long id) {
                this.id = id;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public void setDateOfBirth(LocalDate dateOfBirth) {
                this.dateOfBirth = dateOfBirth;
        }

        public void setRingSize(Integer ringSize) {
                this.ringSize = ringSize;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        public void setEmail(String email) {
                this.email = email;
        }
}
