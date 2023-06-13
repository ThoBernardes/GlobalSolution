package model.dao;

import java.util.List;

import model.entities.Regiao;
import model.entities.Unidade;

public interface UnidadeDao {
	
	void insert(Unidade unidade);
	void update(Unidade unidade);
	void deleteById(Integer id);
	Unidade findById(Integer id);
	List<Unidade> mostarUnidades();
	List<Unidade> mostarUnidadeFiltrado(Regiao regiao);

}
