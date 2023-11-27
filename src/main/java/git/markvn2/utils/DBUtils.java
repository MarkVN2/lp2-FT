package git.markvn2.utils;

import git.markvn2.models.Aluno;
import git.markvn2.keys.DBKeys;
import git.markvn2.models.HistoricoPeso;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DBKeys.getSQLDatabase(), DBKeys.getSQLUser(), DBKeys.getSQLPassword());
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void addStudent(Aluno aluno) {
        String sql = "INSERT INTO students(cpf,student_name,birth_date,weight,height) VALUES(?,?,?,?,?)";
        try {

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, aluno.getCPF());
            stmt.setString(2, aluno.getName());
            stmt.setDate(3, Date.valueOf(String.valueOf(aluno.getBirthdate())));
            stmt.setFloat(4, aluno.getWeight());
            stmt.setFloat(5, aluno.getHeight());

            stmt.execute();
            stmt.close();
            connection.close();

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void deleteStudent(String cpf) {

        String registrysql = "delete from registry where cpf=?";
        String sql = "delete from students where cpf=?";
        try {

            Connection connection = getConnection();

            PreparedStatement registry_stmt = connection.prepareStatement(registrysql);
            registry_stmt.setString(1, cpf);

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);

            registry_stmt.execute();
            registry_stmt.close();

            stmt.execute();
            stmt.close();

            connection.close();

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
    public static Aluno getStudent(String cpf){
        String sql = "select * from students where cpf = ?";
        Aluno student;
        try{
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();

            if(!rs.isBeforeFirst()){
                System.out.println("Student not found");

            }else {
                rs.next();
                student = new Aluno(
                        rs.getString("cpf"),
                        rs.getString("student_name"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getFloat("weight"),
                        rs.getFloat("height")
                        );

                stmt.close();
                connection.close();
                return student;
            }

            stmt.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }
    public static void updateStudent(Aluno aluno) {
        String sql = "update students set student_name=?, birth_date=?, weight=?, height=? where cpf=?";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, aluno.getName());
            stmt.setDate(2, Date.valueOf(String.valueOf(aluno.getBirthdate())));
            stmt.setFloat(3, aluno.getWeight());
            stmt.setFloat(4, aluno.getHeight());
            stmt.setString(5, aluno.getCPF());

            stmt.execute();
            stmt.close();
            connection.close();

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
    public static List<Aluno> getAllStudents(){
        String sql = "select student_name, cpf from students";

        List<Aluno> students = new ArrayList<>();

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.isBeforeFirst()){
                System.out.println("Students not found");
            }else{
                while(rs.next()){
                    String name = rs.getString("student_name");
                    String cpf = rs.getString("cpf");
                    students.add(new Aluno(cpf,name,null,0,0));
                }
            }

            stmt.close();
            connection.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return students;
    }

    public static void addEntry(HistoricoPeso hscpeso) {
        String sql = "insert into registry(cpf,entry_date,weight,height,imc) VALUES(?,?,?,?,?)";
        try {

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, hscpeso.getStudent().getCPF());
            stmt.setDate(2, Date.valueOf(hscpeso.getEntryDate()));
            stmt.setFloat(3, hscpeso.getStudent().getWeight());
            stmt.setFloat(4, hscpeso.getStudent().getHeight());
            stmt.setDouble(5, hscpeso.getIMC());

            stmt.execute();
            stmt.close();
            connection.close();

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
    public static void updateEntry(HistoricoPeso hscpeso) {
        String sql = "update registry set entry_date=?, weight=?, height=?, imc=? where cpf=?";
        try {

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setDate(1, Date.valueOf(hscpeso.getEntryDate()));
            stmt.setFloat(2, hscpeso.getStudent().getWeight());
            stmt.setFloat(3, hscpeso.getStudent().getHeight());
            stmt.setDouble(4, hscpeso.getIMC());
            stmt.setString(5, hscpeso.getStudent().getCPF());

            stmt.execute();
            stmt.close();
            connection.close();

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
    public static void deleteEntry(HistoricoPeso hscpeso) {
        String sql = "delete from registry where cpf=?";
        try {

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, hscpeso.getStudent().getCPF());

            stmt.execute();
            stmt.close();
            connection.close();

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
    public static List<HistoricoPeso> getAllEntries(){
        String sql = "select entry_date,cpf,weight,height from registry";
        List<HistoricoPeso> entries = new ArrayList<>();
        try{
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.isBeforeFirst()){
                System.out.println("Entries not found");
            }else{
                while(rs.next()){
                    String cpf = rs.getString("cpf");
                    Float weight = Float.valueOf(rs.getString("weight"));
                    Float height = Float.valueOf(rs.getString("height"));
                    Aluno student = getStudent(cpf);
                    Aluno entry_student = new Aluno(student.getCPF(), student.getName(), student.getBirthdate(),weight,height);
                    LocalDate entrydate = rs.getDate("entry_date").toLocalDate();

                    entries.add(new HistoricoPeso(entry_student,entrydate,0));
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return entries;
    }

}