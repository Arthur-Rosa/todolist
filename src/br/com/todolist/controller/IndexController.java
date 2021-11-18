package br.com.todolist.controller;

import javax.swing.JOptionPane;

import br.com.todolist.model.Tarefa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

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
		JOptionPane.showMessageDialog(null, "Ainda não funciona :(", "Alerta", JOptionPane.ERROR_MESSAGE);
	}

	@FXML
	void clickSave(ActionEvent event) throws IOException {
		// Verificando se Valores estão vazios
		if (inpData.getValue() == null) {
			JOptionPane.showMessageDialog(null, "Informe a data de realização", "Alerta", JOptionPane.ERROR_MESSAGE);
			inpData.requestFocus();

		} else if (inpDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o Titulo", "Alerta", JOptionPane.ERROR_MESSAGE);
			inpDescricao.requestFocus();

		} else if (inpComent.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe a Descrição", "Alerta", JOptionPane.ERROR_MESSAGE);
			inpComent.requestFocus();
		} else {
			Tarefa tarefa = new Tarefa();
			
			
			
			/* FileWriter arquive = new FileWriter("C:\\Users\\TecDevNoite\\AppData\\Roaming\\armazenador.txt");

			try {
				PrintWriter escreveArq = new PrintWriter(arquive);
				escreveArq.printf("so um teste boy");

			} catch (Exception e) {
				e.printStackTrace();
			} */
		}
	}
}
