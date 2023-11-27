package git.markvn2.controllers;

import git.markvn2.models.Aluno;
import git.markvn2.models.HistoricoPeso;
import git.markvn2.utils.DBUtils;
import git.markvn2.utils.GUIUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    private HBox selected;
    public  static int entry_id;
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
    @FXML
    private Button btn_save;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Alert CONFIRMATION = new Alert(Alert.AlertType.CONFIRMATION);

        Aluno local = DBUtils.getStudent(StudentsController.selectedCPF);


        Label lb_title_cpf = new Label("CPF");
        lb_title_cpf.setPrefWidth(80);
        Label lb_id = new Label("ID");
        Label lb_title_weight = new Label("Weight");
        Label lb_title_height = new Label("Height");
        Label lb_title_imc = new Label("IMC");
        HBox toprow = new HBox();
        toprow.getChildren().addAll(lb_id, lb_title_cpf,lb_title_weight,lb_title_height,lb_title_imc);
        toprow.setSpacing(20);

        vb_students.getChildren().add(toprow);



        for (HistoricoPeso hsc : DBUtils.getAllEntries()){

            if(Objects.equals(hsc.getStudent().getCPF(), StudentsController.selectedCPF)){
            String cpf = hsc.getStudent().getCPF();
            String id = String.valueOf(hsc.getEntryId());

            Label lb_cpf = new Label(cpf);
            lb_cpf.setPrefWidth(80);

            Label lb_entry_id = new Label(id);
            Label lb_weight = new Label(String.valueOf(hsc.getStudent().getWeight()));
            Label lb_height = new Label(String.valueOf(hsc.getStudent().getHeight()));
            Label lb_imc = new Label(String.valueOf(hsc.getIMC()));

            HBox hb_entry = new HBox();
            hb_entry.getChildren().addAll(lb_entry_id,lb_cpf,lb_weight,lb_height,lb_imc);
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
        }
        btn_add.setOnAction(event -> {
                Aluno student = DBUtils.getStudent(StudentsController.selectedCPF);
                DBUtils.addEntry(new HistoricoPeso(student, LocalDate.now(), 5));
                GUIUtils.changeScene(event, "/fxml/history.fxml", "Student History");
        });
        btn_delete.setOnAction(event -> {
            CONFIRMATION.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK){
                    if (selected != null) {
                        entry_id = Integer.parseInt(((Label) selected.getChildren().get(0)).getText());
                        DBUtils.deleteEntry(DBUtils.getEntry(entry_id));
                        vb_students.getChildren().remove(selected);
                    }
                }
            });
        });
        btn_edit.setOnAction(event -> {
            if (selected != null) {
                entry_id = Integer.parseInt(((Label) selected.getChildren().get(0)).getText());
                GUIUtils.changeScene(event, "/fxml/historyedit.fxml", "History Edit");
            }
        });
        btn_return.setOnAction(event -> {
            GUIUtils.changeScene(event, "/fxml/students.fxml","Students");
        });
        btn_save.setOnAction(event -> {
            entry_id = Integer.parseInt(((Label) selected.getChildren().get(0)).getText());
            try {
                DBUtils.getEntry(entry_id).saveArchive();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
