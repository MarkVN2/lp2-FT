package git.markvn2.controllers;

import git.markvn2.models.Aluno;
import git.markvn2.models.HistoricoPeso;
import git.markvn2.utils.DBUtils;
import git.markvn2.utils.GUIUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    private HBox selected;
    @FXML
    private VBox vb_students;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_delete;
    @FXML
    private Button btn_edit;
    @FXML
    private Button btn_return;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Aluno local = DBUtils.getStudent(StudentsController.selectedCPF);


        Label lb_title_cpf = new Label("CPF");
        lb_title_cpf.setPrefWidth(80);

        Label lb_student = new Label("Student");

        Label lb_title_weight = new Label("Weight");
        Label lb_title_height = new Label("Height");
        Label lb_title_imc = new Label("IMC");
        HBox toprow = new HBox();
        toprow.getChildren().addAll(lb_title_cpf, lb_student,lb_title_weight,lb_title_height,lb_title_imc);
        toprow.setSpacing(20);

        vb_students.getChildren().add(toprow);



        for (HistoricoPeso hsc : DBUtils.getAllEntries()){

            if(Objects.equals(hsc.getStudent().getCPF(), StudentsController.selectedCPF)){
            String cpf = hsc.getStudent().getCPF();
            String name = hsc.getStudent().getName();

            Label lb_cpf = new Label(cpf);
            lb_cpf.setPrefWidth(80);

            Label lb_name = new Label(name);

            Label lb_weight = new Label(String.valueOf(hsc.getStudent().getWeight()));
            Label lb_height = new Label(String.valueOf(hsc.getStudent().getHeight()));
            Label lb_imc = new Label(String.valueOf(hsc.getIMC()));

            HBox hBox = new HBox();
            hBox.getChildren().addAll(lb_cpf, lb_name,lb_weight,lb_height,lb_imc);
            hBox.setSpacing(20);
            vb_students.getChildren().add(hBox);

            hBox.onMouseClickedProperty().set((MouseEvent t)->{


                if (selected != null){
                    selected.getStyleClass().clear();
                }

                this.selected = hBox;

            });
        }
        }
        btn_add.setOnAction(event -> {
            Aluno student = DBUtils.getStudent(StudentsController.selectedCPF);
            DBUtils.addEntry(new HistoricoPeso(student, LocalDate.now(),0));
            GUIUtils.changeScene(event, "/fxml/history.fxml", "Student History");
        });
        btn_delete.setOnAction(event -> {
            Aluno student = DBUtils.getStudent(StudentsController.selectedCPF);
            DBUtils.deleteEntry(new HistoricoPeso(student, LocalDate.now(),0));
            vb_students.getChildren().remove(selected);
        });
        btn_return.setOnAction(event -> {
            GUIUtils.changeScene(event, "/fxml/students.fxml","Students");
        });
    }
}
