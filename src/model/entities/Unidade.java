package model.entities;

public class Unidade {
	
	private Integer idUnidade;
	private String nome;
	private String endereco;
	private String publico;
	private Regiao regiao;
	public Integer getIdUnidade() {
		return idUnidade;
	}
	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getPublico() {
		return publico;
	}
	public void setPublico(String publico) {
		this.publico = publico;
	}
	public Regiao getRegiao() {
		return regiao;
	}
	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}
	@Override
	public String toString() {
		return "Unidades [idUnidade=" + idUnidade + ", nome=" + nome + ", endereco=" + endereco + ", publico=" + publico
				+ ", regiao=" + regiao.getIdRregiao() + "]";
	}
	public Unidade(Integer idUnidade, String nome, String endereco, String publico, Regiao regiao) {
		this.idUnidade = idUnidade;
		this.nome = nome;
		this.endereco = endereco;
		this.publico = publico;
		this.regiao = regiao;
	}
	
	public Unidade() {
		
	}
	
	
	
	
	
	
	

}
