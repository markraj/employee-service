package com.paypal.bfs.test.employeeserv.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class EmployeeObject {

    /**
     * employee id
     *
     */
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * first name
     * (Required)
     *
     */

    private String firstName;
    /**
     * last name
     * (Required)
     *
     */
    private String lastName;
    /**
     * date of birth
     *
     */
    private String dateOfBirth;
    /**
     * address
     *
     */
    @OneToOne(cascade= CascadeType.ALL)
    private AddressObject address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public AddressObject getAddress() {
        return address;
    }

    public void setAddress(AddressObject address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "EmployeeObject{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address=" + address +
                '}';
    }
}

