package model.dao;

import java.util.List;

import model.entities.Mercado;
import model.entities.Produto;
import model.entities.Unidade;

public interface ProdutoDao {
	
	void insert(Produto produto);
	void update(Produto produto);
	void deleteById(Integer id);
	Produto findById(Integer id);
	List<Produto> mostarProdutos();
	public List<Produto> mostarProdutoFiltrado(Mercado mercado);

}
