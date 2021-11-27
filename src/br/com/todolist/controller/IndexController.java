package br.com.todolist.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.todolist.io.TarefaIO;
import br.com.todolist.model.StatusTarefa;
import br.com.todolist.model.Tarefa;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class IndexController implements Initializable, ChangeListener<Tarefa> {

	@FXML
	private DatePicker inpData;

	@FXML
	private TextField inpDescricao;

	@FXML
	private TextField inptStatus;

	@FXML
	private TextArea inpComent;

	@FXML
	private Button btnConc;

	@FXML
	private Button btnCalendar;

	@FXML
	private Button btnDelete;

	@FXML

	private Button btnSave;

	@FXML
	private Button btnRubber;

	@FXML
	private TableColumn<Tarefa, LocalDate> tcData;

	@FXML
	private TableColumn<Tarefa, String> tcTarefa;

	@FXML
	private TableView<Tarefa> tvTarefa;

	private List<Tarefa> tarefas;

	@SuppressWarnings("unused")
	private Tarefa tarefa;

	@FXML
	void clickCalendar(ActionEvent event) {
		JOptionPane.showMessageDialog(null, "Ainda não funciona :(", "Alerta", JOptionPane.ERROR_MESSAGE);
	}

	@FXML
	void clickConc(ActionEvent event) {
		JOptionPane.showMessageDialog(null, "Ainda não funciona :(", "Alerta", JOptionPane.ERROR_MESSAGE);
	}

	@FXML
	void clickDelete(ActionEvent event) {
		JOptionPane.showMessageDialog(null, "Ainda não funciona :(", "Alerta", JOptionPane.ERROR_MESSAGE);
	}

	@FXML
	void clickRubber(ActionEvent event) {
		limpar();
	}

	@FXML
	void clickSave(ActionEvent event) {
		// Verificando se Valores estão vazios
		if (inpData.getValue().isBefore(LocalDate.now())) {

			inpData.setStyle("-fx-border-color: red;");
			inpDescricao.setStyle("-fx-border-color: transparent;");
			inpComent.setStyle("-fx-border-color: transparent;");

			JOptionPane.showMessageDialog(null, "Informe uma data Válida", "Alerta", JOptionPane.ERROR_MESSAGE);
		} else if (inpData.getValue() == null) {

			inpData.setStyle("-fx-border-color: red;");
			inpDescricao.setStyle("-fx-border-color: transparent;");
			inpComent.setStyle("-fx-border-color: transparent;");

			JOptionPane.showMessageDialog(null, "Informe a data de realização", "Alerta", JOptionPane.ERROR_MESSAGE);
			inpData.requestFocus();

		} else if (inpDescricao.getText().isEmpty()) {

			inpData.setStyle("-fx-border-color: transparent;");
			inpDescricao.setStyle("-fx-border-color: red;");
			inpComent.setStyle("-fx-border-color: transparent;");

			JOptionPane.showMessageDialog(null, "Informe o Titulo", "Alerta", JOptionPane.ERROR_MESSAGE);
			inpDescricao.requestFocus();

		} else if (inpComent.getText().isEmpty()) {

			inpData.setStyle("-fx-border-color: transparent;");
			inpDescricao.setStyle("-fx-border-color: transparent;");
			inpComent.setStyle("-fx-border-color: red;");

			JOptionPane.showMessageDialog(null, "Informe a Descrição", "Alerta", JOptionPane.ERROR_MESSAGE);
			inpComent.requestFocus();
		} else {
			Tarefa tarefa = new Tarefa();

			tarefa.setDataCriacao(LocalDate.now());
			tarefa.setStatus(StatusTarefa.ABERTA);
			tarefa.setDataLimite(inpData.getValue());
			tarefa.setDescricao(inpDescricao.getText());
			tarefa.setComentarios(inpComent.getText());

			try {
				TarefaIO.insert(tarefa);
				carregarTarefas();
				limpar();

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao gravar: " + e.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}

			// Salva
			System.out.println(tarefa.formatToSave());
		}
	}

	// Limpar os campos
	private void limpar() {
		tarefa = null;
		inpData.setValue(null);
		inpComent.setText(null);
		inpDescricao.setText(null);
		inpData.requestFocus();

		btnCalendar.setDisable(true);
		btnConc.setDisable(true);
		btnDelete.setDisable(true);
		inpData.setDisable(false);
		tvTarefa.getSelectionModel().clearSelection();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// definir os parametros para as colunas do TableView !não entendi nada
		tcData.setCellValueFactory(new PropertyValueFactory<>("dataLimite"));
		tcTarefa.setCellValueFactory(new PropertyValueFactory<>("descricao"));

		tcData.setCellFactory(call -> {
			return new TableCell<Tarefa, LocalDate>() {
				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

					if (!empty) {
						setText(item.format(fmt));
					} else {
						setText("");
					}

					super.updateItem(item, empty);
				}
			};

		});

		tvTarefa.getSelectionModel().selectedItemProperty().addListener(this);

		carregarTarefas();
	}

	public void carregarTarefas() {
		try {
			tarefas = TarefaIO.read();
			tvTarefa.setItems(FXCollections.observableArrayList(tarefas));
			tvTarefa.refresh();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar as tarefas: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	@Override
	public void changed(ObservableValue<? extends Tarefa> observable, Tarefa oldValue, Tarefa newValue) {
		// passo referencia para a variavel global
		tarefa = newValue;

		if (tarefa != null) {
			inpData.setValue(tarefa.getDataLimite());
			inpDescricao.setText(tarefa.getDescricao());
			inpComent.setText(tarefa.getComentarios());
			inptStatus.setText(tarefa.getStatus() + "");

			btnCalendar.setDisable(false);
			btnConc.setDisable(false);
			btnDelete.setDisable(false);

			inpData.setDisable(true);
		}
	}
}