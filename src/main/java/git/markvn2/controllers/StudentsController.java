package git.markvn2.controllers;

import git.markvn2.models.Aluno;
import git.markvn2.utils.DBUtils;
import git.markvn2.utils.GUIUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {

    public static String selectedCPF;

    private HBox selected;

    @FXML
    private VBox vb_students;
    @FXML
    private Button btn_new;
    @FXML
    private Button btn_delete;
    @FXML
    private Button btn_edit;
    @FXML
    private Button btn_history;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Alert CONFIRMATION = new Alert(Alert.AlertType.CONFIRMATION);

        Label lb_cpf = new Label("CPF");
        lb_cpf.setPrefWidth(80);

        Label lb_student = new Label("Student");
        HBox toprow = new HBox();

        toprow.getChildren().addAll(lb_cpf, lb_student);

        toprow.setSpacing(20);

        vb_students.getChildren().add( toprow);

        for (Aluno student : DBUtils.getAllStudents()){

            String cpf = student.getCPF();
            String name = student.getName();

            Label lb_student_cpf = new Label(cpf);
            lb_student_cpf.setPrefWidth(80);

            Label lb_name = new Label(name);

            HBox hb_entry = new HBox();
            hb_entry.getChildren().addAll(lb_student_cpf, lb_name);
            hb_entry.setSpacing(20);
            vb_students.getChildren().add(hb_entry);

            hb_entry.onMouseClickedProperty().set((MouseEvent t)->{

                if (selected != null){
                    selected.setStyle("-fx-background-color:WHITE;");
                }
                hb_entry.setStyle("-fx-background-color: GRAY;");
                this.selected = hb_entry;
            });
        }

        btn_new.setOnAction(event -> {
            GUIUtils.changeScene(event,"/fxml/studentform.fxml","New Student Form");
        });
        btn_delete.setOnAction(event -> {
            CONFIRMATION.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (selected != null) {
                        selectedCPF = ((Label) selected.getChildren().get(0)).getText();
                        DBUtils.deleteStudent(selectedCPF);
                        vb_students.getChildren().remove(selected);
                    }
                }
            });
        });
        btn_edit.setOnAction(event -> {
            if (selected != null) {
                selectedCPF = ((Label) selected.getChildren().get(0)).getText();
                GUIUtils.changeScene(event,"/fxml/studentedit.fxml","Edit Student");
            }
        });
        btn_history.setOnAction(event -> {
            if (selected != null) {
                selectedCPF = ((Label) selected.getChildren().get(0)).getText();
                GUIUtils.changeScene(event,"/fxml/history.fxml","Student History");
            }
        });
    }
}
