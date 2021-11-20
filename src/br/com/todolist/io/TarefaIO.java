package br.com.todolist.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
}