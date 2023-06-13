package model.entities;

public class Mercado {
	
	private Integer idMercado;
	private String nome;
	private String endereco;
	private Regiao regiao;
	public Integer getIdMercado() {
		return idMercado;
	}
	public void setIdMercado(Integer idMercado) {
		this.idMercado = idMercado;
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
	
	public Regiao getRegiao() {
		return regiao;
	}
	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}
	@Override
	public String toString() {
		return "Mercado [idMercado=" + idMercado + ", nome=" + nome + ", endereco=" + endereco + ", idRegiao="
				+ regiao.getIdRregiao() + "]";
	}
	public Mercado(Integer idMercado, String nome, String endereco, Regiao regiao) {
		
		this.idMercado = idMercado;
		this.nome = nome;
		this.endereco = endereco;
		this.regiao = regiao;
	}
	
	public Mercado(){
		
	}
	

}
