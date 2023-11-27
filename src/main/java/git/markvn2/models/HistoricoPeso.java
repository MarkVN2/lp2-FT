package git.markvn2.models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HistoricoPeso {
    private int entryId;
    private Aluno student;
    private LocalDate entryDate;


    public HistoricoPeso(Aluno student, LocalDate entryDate, int entryid) {
        this.student = student;
        this.entryDate = entryDate;
        this.entryId = entryid;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int id) {
        this.entryId = id;
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
    public String processIMC(double imc){
        if (imc < 18.5) return "Underweight";
        if ( 18.5 < imc && imc < 24.9) return "Normal weight";
        if (25 < imc && imc < 29.9) return "Overweight";
        return "Obesity";
    }

    private File createArchive() throws IOException {
        File archive = new File(System.getProperty("user.home")+ "/Desktop/icm_archive.txt");
        if (!archive.exists()) {
            archive.createNewFile();
        }
        FileWriter wrt = new FileWriter(archive, true);
        BufferedWriter bw = new BufferedWriter(wrt);
        bw.write("| CPF | IMC | NAME | DATE | INTERPRETATION |");
        bw.newLine();
        bw.close();
        return archive;
    }
    public void saveArchive() throws IOException {

        String cpf = this.getStudent().getCPF();
        double imc = this.getIMC();
        String name = this.getStudent().getName();
        String date = this.entryDate.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
        String interpretation = processIMC(imc);



        File archive = createArchive();


        FileWriter wrt = new FileWriter(archive, true);
        BufferedWriter bw = new BufferedWriter(wrt);

        bw.write(String.format("/ %s / %s / %s / %s / %s ", cpf, imc , name , date , interpretation ));
        bw.newLine();

        bw.close();

    }
}
