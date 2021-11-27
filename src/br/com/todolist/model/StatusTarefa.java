package br.com.todolist.model;

public enum StatusTarefa {
	ABERTA("Aberta"), ADIADA("Adiada"), CONCLUIDA("Concluída");

	private StatusTarefa(String descricao) {
		this.descricao = descricao;
	}

	String descricao;

	@Override
	public String toString() {

		return descricao;
	}
}
