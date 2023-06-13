package model.entities;

public class Regiao {
	
	private Integer idRregiao;
	private String nome;
	
	public Regiao() {
		
	}
	public Regiao(Integer idRregiao, String nome) {
		this.idRregiao = idRregiao;
		this.nome = nome;
	}
	public Integer getIdRregiao() {
		return idRregiao;
	}
	public void setIdRregiao(Integer idRregiao) {
		this.idRregiao = idRregiao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@Override
	public String toString() {
		return "Regiao [idRregiao=" + idRregiao + ", nome=" + nome + "]";
	}
	
	
	

}
