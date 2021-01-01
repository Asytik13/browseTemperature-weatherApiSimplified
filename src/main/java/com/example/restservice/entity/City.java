package com.example.restservice.entity;

public class City {

    private String name;
    private Integer id;

    public City (String name, Integer id){
        this.name = name;
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}
