package br.com.todolist.controller;

import javax.swing.JOptionPane;

import br.com.todolist.model.StatusTarefa;
import br.com.todolist.model.Tarefa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;

public class IndexController {

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
	void clickSave(ActionEvent event) throws IOException {
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
			
			// Salva
			System.out.println(tarefa.formatToSave());

			// Clear
			limpar();
		}
	}

	// Limpar os campos
	private void limpar() {
		tarefa = null;
		inpData.setValue(null);
		inpComent.setText(null);
		inpDescricao.setText(null);
		inpData.requestFocus();
	}
}