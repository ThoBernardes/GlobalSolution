package model.dao;

import java.util.List;

import model.entities.Regiao;
import model.entities.Unidade;

public interface RegiaoDao {
	
	void insert(Regiao regiao);
	void update(Regiao regiao);
	void deleteById(Integer id);
	Regiao findById(Integer id);
	List<Regiao> mostarRegioes();
	

}
