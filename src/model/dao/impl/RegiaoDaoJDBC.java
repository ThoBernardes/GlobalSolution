package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.RegiaoDao;
import model.entities.Mercado;
import model.entities.Regiao;

public class RegiaoDaoJDBC implements RegiaoDao{
	
	private Connection conn;
	
	

	public RegiaoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Regiao regiao) {
		PreparedStatement st = null;
		
		try {
			Connection conn = DB.getConnection();
			st = conn.prepareStatement("INSERT INTO Regiao (id_Regiao, nome) VALUES (?, ?)");
			
			st.setInt(1, regiao.getIdRregiao());
			st.setString(2, regiao.getNome());
			int rows = st.executeUpdate();
			if(rows == 0){
				 throw new DbException("Erro inesperado !!");
			 }else{
				 System.out.println("Regiao adicionada !!");
			 }
		}catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	    	DB.closeStatement(st);
	    	
	    }	
	}

	@Override
	public void update(Regiao regiao) {
		PreparedStatement st = null;
		try {
			Connection conn = DB.getConnection();
			st = conn.prepareStatement("UPDATE Regiao SET nome = ? WHERE id_Regiao = ?");
			
			st.setString(1, regiao.getNome());
			st.setInt(2,regiao.getIdRregiao());
			int rows = st.executeUpdate();
			if(rows == 0){
				throw new DbException("ID não encontrado");
			}else{
				System.out.println("Regiao Atualizada !!");
			}
		}catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	    	DB.closeStatement(st);
	    	
	    }
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		 try {
			 Connection conn = DB.getConnection();
			 st = conn.prepareStatement("DELETE FROM Regiao WHERE id_Regiao = ?");
			 
			 st.setInt(1, id);
			 int rows = st.executeUpdate();
			 if(rows == 0) {
					throw new DbException("ID não encontrado");
			 }else {
				 System.out.println("Regiao deletada !!!");
			 }
		 }
		 catch(SQLException e) {
			 throw new DbException(e.getMessage());
		 }
		 finally {
			 DB.closeStatement(st);
		 }
	}

	@Override
	public Regiao findById(Integer id){
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			Connection conn = DB.getConnection();
			st = conn.prepareStatement("SELECT * FROM Regiao WHERE id_Regiao = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Regiao regiao = instantiateRegiao(rs);
				System.out.println(regiao);
				return regiao;
			}
			return null;
		}catch(SQLException e) {
			 throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	private Regiao instantiateRegiao(ResultSet rs) throws SQLException{
		Regiao regiao = new Regiao();
		regiao.setIdRregiao(rs.getInt("id_Regiao"));
		regiao.setNome(rs.getString("Nome"));
		return regiao;
	}

	@Override
	public List<Regiao> mostarRegioes() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM Regiao ORDER BY Name");
			
			rs = st.executeQuery();
			
			List<Regiao> list = new ArrayList<Regiao>();
			while(rs.next()) {
				Regiao regiao = instantiateRegiao(rs);
				list.add(regiao);
			}
			for (Regiao obj : list) {
				System.out.println(obj);
			}

			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
}
