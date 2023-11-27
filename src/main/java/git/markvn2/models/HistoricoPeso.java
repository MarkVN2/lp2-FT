package git.markvn2.models;

import java.time.LocalDate;

public class HistoricoPeso {
    private int id;
    private Aluno student;
    private LocalDate entryDate;


    public HistoricoPeso(Aluno student, LocalDate entryDate, int id) {
        this.student = student;
        this.entryDate = entryDate;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Aluno getStudent() {
        return student;
    }

    public void setStudent(Aluno student) {
        this.student = student;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public double getIMC(){
        double imc = student.getWeight()/Math.pow(student.getHeight(),2);
        return Math.floor(imc*100)/100;
    }
}
