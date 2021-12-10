package br.com.todolist.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SobreController {

	@FXML
	private Button btOk;

	@FXML
	private void btOkClick() {
		Stage stage = (Stage) btOk.getScene().getWindow();
		stage.close();
	}
}