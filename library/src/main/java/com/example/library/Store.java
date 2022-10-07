package com.example.library;


import jakarta.persistence.*;

@Entity
@Table(name = "store")
public class Store {
/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45, nullable = false, unique = true)
    private String name;

 */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, table = "store")
    private String name;

    @Column(length = 50, table = "store")
    private String count;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Store() {
    }

    public Store(String name) {
        this.name = name;
    }

    public Store(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Store(Integer id, String name,  String count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

}
