package application;



import java.sql.Date;

import model.dao.DaoFactory;
import model.dao.MercadoDao;
import model.dao.ProdutoDao;
import model.dao.RegiaoDao;
import model.dao.UnidadeDao;
import model.entities.Mercado;
import model.entities.Produto;
import model.entities.Regiao;
import model.entities.Unidade;

public class Teste {

	public static void main(String[] args) {
		
		RegiaoDao regiaoDao = DaoFactory.createRegiaoDao();
		MercadoDao mercadoDao = DaoFactory.createMercadoDao();
		ProdutoDao produtoDao = DaoFactory.createProdutoDao();
		UnidadeDao unidadeDao = DaoFactory.createUnidadeDao();
		
		Regiao sul = new Regiao(1,"Sul");
		Mercado walmart =new Mercado(1,"Walmart","Av. coelho cardoso 133",sul);
		Date validade = Date.valueOf("2023-01-01");
		Produto produto = new Produto(1,"Miojo",walmart,15,35,validade);
		Unidade unidade = new Unidade(1,"Crianca Esperanca","Av Jucelino 556","Crianca",sul);
		
		/*unidadeDao.insert(unidade);
		unidadeDao.findById(1);
		unidade.setNome("Caldeirão Feliz");
		unidadeDao.update(unidade);
		unidadeDao.deleteById(1);*/
		
		unidadeDao.mostarUnidadeFiltrado(sul);
		
		
		
		
		/*regiaoDao.insert(sul);
		mercadoDao.insert(walmart);
		mercadoDao.findById(1);
		produtoDao.insert(produto);
		produtoDao.findById(1);
		produto.setNome("Cafe");
		produtoDao.update(produto);
		produtoDao.findById(1);
		produtoDao.deleteById(1);
		walmart.setEndereco("Av paulista 1334");
		mercadoDao.update(walmart);
		produtoDao.update(produto);
		mercadoDao.deleteById(1);
		regiaoDao.deleteById(1);*/
		
		
		
		

		
		

	}

}
