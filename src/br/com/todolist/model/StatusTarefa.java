package br.com.todolist.model;

public enum StatusTarefa {
	ABERTA("Aberta"), ADIADA("Adiada"), CONCLUIDA("Conclu�da"), ATRASADA("Atrasada");

	private StatusTarefa(String descricao) {
		this.descricao = descricao;
	}

	String descricao;

	@Override
	public String toString() {

		return descricao;
	}
}
