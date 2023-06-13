package model.dao;

import java.sql.SQLException;

import db.DB;
import model.dao.impl.MercadoDaoJDBC;
import model.dao.impl.ProdutoDaoJDBC;
import model.dao.impl.RegiaoDaoJDBC;
import model.dao.impl.SistemaDaoJDBC;
import model.dao.impl.UnidadeDaoJDBC;

public interface DaoFactory {
	
	public static RegiaoDao createRegiaoDao() {
		try {
			return new  RegiaoDaoJDBC(DB.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ProdutoDao createProdutoDao() {
		try {
			
			return new ProdutoDaoJDBC(DB.getConnection());
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public static MercadoDao createMercadoDao() {
		try {
			
			return new MercadoDaoJDBC(DB.getConnection());
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public static UnidadeDao createUnidadeDao() {
		try {
			
			return new UnidadeDaoJDBC(DB.getConnection());
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public static SistemaDao createSistemaDao() {
		try {
			return new  SistemaDaoJDBC(DB.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
