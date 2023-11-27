package git.markvn2.models;

import java.time.LocalDate;

public class Aluno {

    private String CPF;
    private String name;
    private LocalDate birthdate;
    private Float weight;
    private Float height;

    public Aluno(String CPF, String name, LocalDate birthdate, float weight, float height) {
        this.CPF = CPF;
        this.name = name;
        this.birthdate = birthdate;
        this.weight = weight;
        this.height = height;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
