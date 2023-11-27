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

public class StudentEditController implements Initializable {

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
    private Button btn_edit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Aluno local = DBUtils.getStudent(StudentsController.selectedCPF);

        assert local != null;
        tf_name.setText(local.getName());
        tf_name.setEditable(false);
        tf_name.setFocusTraversable(false);

        tf_cpf.setText(local.getCPF());
        tf_cpf.setEditable(false);
        tf_cpf.setFocusTraversable(false);

        dp_birth.setValue(local.getBirthdate());

        tf_height.setText(String.valueOf(local.getHeight()));
        tf_weight.setText(String.valueOf(local.getWeight()));

        btn_return.setOnAction(event -> {
            GUIUtils.changeScene(event, "/fxml/students.fxml", "Students");
        });
        btn_edit.setOnAction(event -> {

            String name = tf_name.getText();
            String cpf = tf_cpf.getText();
            LocalDate birthday = dp_birth.getValue();
            float weight = Float.parseFloat(tf_weight.getText());
            float height = (float) (Float.parseFloat(tf_height.getText()) * Math.pow(10,-2));

            Aluno new_student = new Aluno(cpf,name,birthday,weight,height);

            DBUtils.updateStudent(new_student);
            GUIUtils.changeScene(event, "/fxml/students.fxml", "Students");
        });
    }
}
