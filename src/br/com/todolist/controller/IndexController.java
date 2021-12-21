package br.com.todolist.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.todolist.io.TarefaIO;
import br.com.todolist.model.StatusTarefa;
import br.com.todolist.model.Tarefa;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

	@FXML
	private Label lbConcluida;

	@FXML
	private TextField inpCodigo;

	private List<Tarefa> tarefas;

	private Tarefa tarefa;

	@FXML
	void clickCalendar(ActionEvent event) {
		if (tarefa != null) {
			int dias = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantos dias você deseja adiar?",
					"Informe quantos dias", JOptionPane.QUESTION_MESSAGE));

			DateTimeFormatter padraoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			LocalDate novaData = tarefa.getDataLimite().plusDays(dias);
			tarefa.setDataLimite(novaData);
			tarefa.setStatus(StatusTarefa.ADIADA);

			try {
				TarefaIO.saveTarefas(tarefas);
				JOptionPane.showMessageDialog(null, "A Nova data é " + novaData.format(padraoData), "Adiado",
						JOptionPane.INFORMATION_MESSAGE);

				carregarTarefas();
				limpar();
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(null, "Erro ao Salvar Tarefa", "ERROR", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	@FXML
	void clickConc(ActionEvent event) {
		if (tarefa != null) {
			tarefa.setStatus(StatusTarefa.CONCLUIDA);
			tarefa.setDataConcluida(LocalDate.now());
			try {
				JOptionPane.showMessageDialog(null, "Parabéns, Tarefa Concluida!!!", "Joia",
						JOptionPane.INFORMATION_MESSAGE);

				TarefaIO.saveTarefas(tarefas);
				carregarTarefas();
				limpar();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao Concluir Tarefa", "ERROR", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	@FXML
	void clickDelete(ActionEvent event) {
		if (tarefa != null) {
			int opt = JOptionPane.showConfirmDialog(null, "Você deseja excluir a Tarefa " + tarefa.getId() + " ?",
					"Excluir", JOptionPane.YES_NO_OPTION);
			if (opt == 0) {
				tarefas.remove(tarefa);

				try {
					TarefaIO.saveTarefas(tarefas);
					carregarTarefas();
					limpar();
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Ocorreu um erro ao Excluir", "Excluir",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				return;
			}
		}
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
			// verifica se a tarefa e nula
			if (tarefa == null) {
				// instanciando tarefa
				tarefa = new Tarefa();
				tarefa.setDataCriacao(LocalDate.now());
				tarefa.setStatus(StatusTarefa.ABERTA);
			}

			tarefa.setDataLimite(inpData.getValue());
			tarefa.setDescricao(inpComent.getText());
			tarefa.setComentarios(inpDescricao.getText());

			try {
				if (tarefa.getId() == 0) {
					TarefaIO.insert(tarefa);
				} else {
					TarefaIO.saveTarefas(tarefas);
				}

				limpar();
				carregarTarefas();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao gravar: " + e.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// Limpar os campos
	private void limpar() {
		tarefa = null;
		inpData.setValue(null);
		inpComent.setText(null);
		inpDescricao.setText(null);
		inptStatus.setText(null);
		inpData.requestFocus();

		btnCalendar.setDisable(true);
		btnConc.setDisable(true);
		btnDelete.setDisable(true);
		inpData.setDisable(false);
		inpCodigo.setText(null);
		btnSave.setDisable(false);
		tvTarefa.getSelectionModel().clearSelection();

		inpData.setEditable(true);
		inpDescricao.setEditable(true);
		inpComent.setEditable(true);

		leitorID();
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

		/*
		 * if(tarefa.getDataLimite().isBefore(tarefa.getDataLimite())) {
		 * tarefa.setStatus(StatusTarefa.ATRASADA); }
		 */

		tvTarefa.setRowFactory(call -> new TableRow<Tarefa>() {
			protected void updateItem(Tarefa item, boolean empty) {
				super.updateItem(item, empty);

				if (item == null) {
					setStyle("");
				} else if (item.getStatus() == StatusTarefa.CONCLUIDA) {
					setStyle("-fx-background-color: MediumSeaGreen");
				} else if (item.getDataLimite().isBefore(LocalDate.now())) {
					setStyle("-fx-background-color: Maroon");
				} else if (item.getStatus() == StatusTarefa.ADIADA) {
					setStyle("-fx-background-color: Gold");
				} else {
					setStyle("-fx-background-color: CornflowerBlue");
				}
			};
		});

		tvTarefa.getSelectionModel().selectedItemProperty().addListener(this);

		carregarTarefas();

		leitorID();

		veAtraso(tarefas);
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
			inpCodigo.setText(tarefa.getId() + "");
			inpData.setValue(tarefa.getDataLimite());
			inpDescricao.setText(tarefa.getDescricao());
			inpComent.setText(tarefa.getComentarios());
			inptStatus.setText(tarefa.getStatus() + "");

			btnDelete.setDisable(false);
			btnConc.setDisable(false);

			switch (tarefa.getStatus()) {
			case ADIADA:
				lbConcluida.setText("Data para realização:");
				btnCalendar.setDisable(true);
				btnSave.setDisable(false);
				break;
			case CONCLUIDA:
				lbConcluida.setText("Data de Conclusão:");
				inpData.setValue(tarefa.getDataConcluida());

				btnCalendar.setDisable(true);
				btnConc.setDisable(true);
				btnSave.setDisable(true);

				inpData.setEditable(false);
				inpDescricao.setEditable(false);
				inpComent.setEditable(false);
				break;
			default:
				lbConcluida.setText("Data para realização:");
				btnSave.setDisable(false);
				btnCalendar.setDisable(false);
				inpData.setDisable(true);
				break;
			}

		}
	}

	public void leitorID() {
		try {
			inpCodigo.setText(TarefaIO.leId() + "");
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Erro não foi possivel ler o ID", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void veAtraso() {
		if (tarefa.getDataLimite().isBefore(tarefa.getDataLimite())) {
			tarefa.setStatus(StatusTarefa.ATRASADA);

			JOptionPane.showMessageDialog(null, "Você tem tarefas ATRASADAS", "AVISO", JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	public void acExport() {
		javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter("Arquivos HTML", "html", "htm");
		JFileChooser chooser = new JFileChooser();

		chooser.setFileFilter(filter);
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File arqSelecionado = chooser.getSelectedFile();

			if (!arqSelecionado.getAbsolutePath().endsWith(".html")) {
				arqSelecionado = new File(arqSelecionado + ".html");
			}

			try {
				TarefaIO.exportHtml(tarefas, arqSelecionado);

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao exportar tarefas:" + e.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void acSobre() {
		Stage primaryStage = new Stage();

		try {

			TarefaIO.createFiles();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/br/com/todolist/view/sobre.fxml"));
			Scene scene = new Scene(root, 250, 275);
			scene.getStylesheets()
					.add(getClass().getResource("/br/com/todolist/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sobre");
			primaryStage.getIcons()
					.add(new Image(getClass().getResourceAsStream("/br/com/todolist/images/image_disquete.png")));
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void acSair() {
		int opt = JOptionPane.showConfirmDialog(null, "Não se vá, Deseja Sair ?", "Sair", JOptionPane.YES_NO_OPTION);
		if (opt == 0) {
			System.exit(0);
		} else {
			JOptionPane.showMessageDialog(null, "Obrigado, <3", "<3", JOptionPane.INFORMATION_MESSAGE);

		}

	}

	public void veAtraso(List<Tarefa> tarefas) {
		for (Tarefa t : tarefas) {
			if (t.getDataCriacao().isAfter(t.getDataLimite())) {
				JOptionPane.showMessageDialog(null, "Você possuí tarefas atrasadas", "Alerta",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}