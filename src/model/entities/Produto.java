package model.entities;

import java.sql.Date;

public class Produto {
	
	private Integer idProduto;
	private String nome;
	private Mercado mercado;
	private Integer lote;
	private Integer quantdade;
	private Date validade;
	
	public Produto() {
		
	}
	
	public Produto(Integer idProduto, String nome, Mercado mercado, Integer lote, Integer quantdade, Date validade) {		
		this.idProduto = idProduto;
		this.nome = nome;
		this.mercado = mercado;
		this.lote = lote;
		this.quantdade = quantdade;
		this.validade = validade;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getLote() {
		return lote;
	}
	public void setLote(Integer lote) {
		this.lote = lote;
	}
	public Date getValidade() {
		return validade;
	}
	public void setValidade(Date validade) {
		this.validade = validade;
	}
	public Integer getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}
	public Mercado getMercado() {
		return mercado;
	}
	public void setMercado(Mercado mercado) {
		this.mercado = mercado;
	}
	public Integer getQuantdade() {
		return quantdade;
	}
	public void setQuantdade(Integer quantdade) {
		this.quantdade = quantdade;
	}
	public Integer getIdMercado() {
		Integer idMercado =  this.mercado.getIdMercado();
		return idMercado;
	}
	@Override
	public String toString() {
		return "Produtos [idProduto=" + idProduto + ", nome=" + nome + ", mercado=" + mercado.getIdMercado() + ", lote=" + lote
				+ ", validade=" + validade + "]";
	}
	
	
	
	
	
	
	

}
