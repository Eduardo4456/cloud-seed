package com.example.CloudSeed.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_care")
public class CarePlantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_care")
    private Long id;

    private String careType;

    public CarePlantModel() {
    }

    public CarePlantModel(Long id, String careType) {
        this.id = id;
        this.careType = careType;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCareType() {
        return careType;
    }

    public void setCareType(String careType) {
        this.careType = careType;
    }
}
