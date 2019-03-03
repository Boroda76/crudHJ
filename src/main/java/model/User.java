package model;


import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(unique = true)
    private String login;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column
    private Boolean sex; //false for female/true for male
    @Column
    private Integer age;
    @Column
    private Double weight;
    @Column
    private Double height;
    @Column
    private String role;

    //For hibernate with love!
    public User() {
    }

    public User(Long id, String login, String email, String password, Boolean sex, Integer age, Double weight, Double height, String role) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.role=role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
