package git.markvn2.controllers;

import git.markvn2.models.Aluno;
import git.markvn2.utils.DBUtils;
import git.markvn2.utils.GUIUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {

    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_cpf;
    @FXML
    private DatePicker dp_birth;
    @FXML
    private TextField tf_height;
    @FXML
    private TextField tf_weight;
    @FXML
    private Button btn_return;
    @FXML
    private Button btn_add;
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        btn_return.setOnAction(event -> {
            GUIUtils.changeScene(event, "/fxml/students.fxml", "students");
        });
        btn_add.setOnAction(event -> {
            if(tf_cpf.getText().matches("([0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}[-]?[0-9]{2})")){
            String name = tf_name.getText();
            String cpf = tf_cpf.getText();
            LocalDate birthday = dp_birth.getValue();
            float weight = Float.parseFloat(tf_weight.getText());
            float height = Float.parseFloat(tf_height.getText());

            Aluno new_student = new Aluno(cpf,name,birthday,weight,height);

            DBUtils.addStudent(new_student);
            GUIUtils.changeScene(event, "/fxml/students.fxml", "students");
            }
            else {
                System.out.println("INVALID CPF");
            }
        });

    }
}
