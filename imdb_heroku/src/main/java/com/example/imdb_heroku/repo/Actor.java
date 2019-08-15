package com.example.imdb_heroku.repo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "namebasics")
public class Actor {
    @Id
    private String nconst;

    @Column(name = "primaryName")
    private String name;

    @Column(name = "primaryProfession")
    private String profession;

    @Column(name = "birthYear")
    private Integer birthYear;

    //@Column(name = "knownForTitles")
    //private String knownForTitles;

    public String getNconst() {
        return nconst;
    }

    public void setNconst(String nconst) {
        this.nconst = nconst;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }




}