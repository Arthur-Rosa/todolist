package br.com.todolist.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import br.com.todolist.model.StatusTarefa;
import br.com.todolist.model.Tarefa;

public class TarefaIO {
	private static final String FOLDER = System.getProperty("user.home") + "/todolist";

	// Arquivo csv pode ser aberto no excel
	private static final String FILE_ID = FOLDER + "/id.artu";

	private static final String FILE_TAREFA = FOLDER + "/tarefas.artu";

	// Metodo para criar arquivos
	public static void createFiles() {
		try {
			File folder = new File(FOLDER);

			if (!folder.exists()) {
				File fileId = new File(FILE_ID);
				File fileTarefa = new File(FILE_TAREFA);
				// criando pasta com mkdir
				folder.mkdir();
				// criando arquivo
				fileId.createNewFile();
				fileTarefa.createNewFile();
				FileWriter writer = new FileWriter(FILE_ID);
				writer.write("1");
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insert(Tarefa tarefa) throws FileNotFoundException, IOException {
		File arqTarefa = new File(FILE_TAREFA);
		File arqId = new File(FILE_ID);

		Scanner sc = new Scanner(arqId);
		tarefa.setId(sc.nextLong());
		sc.close();

		FileWriter writer = new FileWriter(arqTarefa, true);

		writer.append(tarefa.formatToSave());
		writer.close();

		// definindo ID

		FileWriter escritor = new FileWriter(arqId);

		escritor.write((tarefa.getId() + 1) + "");
		escritor.close();
		sc.close();

	}

	public static List<Tarefa> read() throws IOException {
		File arqTarefa = new File(FILE_TAREFA);

		List<Tarefa> tarefas = new ArrayList<>();
		FileReader leitorArq = new FileReader(arqTarefa);
		BufferedReader buff = new BufferedReader(leitorArq);
		String linha;

		while ((linha = buff.readLine()) != null) {
			String[] vetor = linha.split(";");
			Tarefa t = new Tarefa();
			t.setId(Long.parseLong(vetor[0]));

			DateTimeFormatter padraoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			t.setDataCriacao(LocalDate.parse(vetor[1], padraoData));
			t.setDataLimite(LocalDate.parse(vetor[2], padraoData));

			if (!vetor[3].isEmpty()) {
				t.setDataConcluida(LocalDate.parse(vetor[3], padraoData));
			}

			t.setComentarios(vetor[4]);
			t.setDescricao(vetor[5]);
			int indStatus = Integer.parseInt(vetor[6]);
			t.setStatus(StatusTarefa.values()[indStatus]);

			tarefas.add(t);
		}
		buff.close();
		Collections.sort(tarefas);

		return tarefas;
	}

	public static void saveTarefas(List<Tarefa> tarefas) throws IOException {
		File arqTarefas = new File(FILE_TAREFA);
		FileWriter writer = new FileWriter(arqTarefas);

		for (Tarefa t : tarefas) {
			writer.write(t.formatToSave());
		}
		writer.close();
	}
	
	public static long leId() throws FileNotFoundException  {
		File arqId = new File(FILE_ID);
		Scanner sc = new Scanner(arqId);
		
		Long contId = sc.nextLong();
		
		sc.close();
		
		return contId;
	}
}