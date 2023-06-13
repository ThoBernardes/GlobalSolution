package model.dao;

import java.util.List;

import model.entities.Mercado;
import model.entities.Regiao;

public interface MercadoDao {
	
	void insert(Mercado mercado);
	void update(Mercado mercado);
	void deleteById(Integer id);
	Mercado findById(Integer id);
	List<Mercado> mostarMercados();
	List<Mercado> mostarMercadosFiltrado(Regiao regiao);

}
