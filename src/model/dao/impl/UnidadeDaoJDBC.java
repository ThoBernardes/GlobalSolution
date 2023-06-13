package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.UnidadeDao;
import model.entities.Mercado;
import model.entities.Regiao;
import model.entities.Unidade;

public class UnidadeDaoJDBC implements UnidadeDao {
	
	private Connection conn;
	
	public UnidadeDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Unidade unidade) {
		PreparedStatement st = null;
		try {
			Connection conn = DB.getConnection();
			st = conn.prepareStatement("INSERT INTO Unidade (id_Unidade, nome, endereco, publico, id_Regiao) VALUES (?, ?, ?, ?, ?)");
			
			st.setInt(1, unidade.getIdUnidade());
			st.setString(2, unidade.getNome());
			st.setString(3, unidade.getEndereco());
			st.setString(4, unidade.getPublico());
			st.setInt(5, unidade.getRegiao().getIdRregiao());
			
			int rows = st.executeUpdate();
			if(rows == 0){
				 throw new DbException("Erro inesperado !!");
			 }else{
				 System.out.println("Unidade adicionada !!");
			 };
		}catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	    	DB.closeStatement(st);
	    	
	    }
		
	}

	@Override
	public void update(Unidade unidade) {
		PreparedStatement st = null;
		try {
			Connection conn = DB.getConnection();
			st = conn.prepareStatement("UPDATE Unidade SET nome = ?, endereco = ?, publico = ? WHERE id_Unidade = ?");
			
			st.setString(1, unidade.getNome());
			st.setString(2, unidade.getEndereco());
			st.setString(3, unidade.getPublico());
			st.setInt(4, unidade.getIdUnidade());
			
			int rows = st.executeUpdate();
			if(rows == 0){
				throw new DbException("ID não encontrado");
			}else{
				System.out.println("Unidade Atualizada !!");
				System.out.println(unidade);
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
			Connection conn =DB.getConnection();
			st = conn.prepareStatement("DELETE FROM Unidade WHERE id_Unidade = ?");
			
			st.setInt(1, id);
			
			int rows = st.executeUpdate();
			if(rows == 0){
				throw new DbException("ID não encontrado");
			}else{
				System.out.println("Unidade Deletada !!");
			}
		}catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	    	DB.closeStatement(st);    	
	    }
		
	}

	@Override
	public Unidade findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			Connection conn = DB.getConnection();
			st = conn.prepareStatement("SELECT Unidade.*, Regiao.id_Regiao AS id_Regiao"
					+" FROM Unidade"
					+" INNER JOIN Regiao ON Unidade.id_Regiao = Regiao.id_Regiao"
					+" WHERE Unidade.id_Unidade = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()){
				Regiao rg = instantiateRegiao(rs);				
				Unidade unidade = instantiateUnidade(rs, rg);
				System.out.println(unidade);
				return unidade;
			}
			else {
				System.out.println("Id não encontrado !!");
				return null;
			}
		}catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	    	DB.closeStatement(st);    	
	    }
	}

	@Override
	public List<Unidade> mostarUnidades() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT Unidade.*, Regiao.id_Regiao AS id_Regiao"
					+" FROM Unidade"
					+" INNER JOIN Regiao ON Unidade.id_Regiao = Regiao.id_Regiao");
			
			
			rs = st.executeQuery();
			
			List<Unidade> list = new ArrayList<Unidade>();
			while(rs.next()) {
				Regiao regiao = instantiateRegiao(rs);
				Unidade unidade = instantiateUnidade(rs, regiao);
				list.add(unidade);
			}
			for (Unidade obj : list) {
				System.out.println(obj);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}
	
	public List<Unidade> mostarUnidadeFiltrado(Regiao regiao) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT Unidade.*, Regiao.id_Regiao AS id_Regiao"
					+" FROM Unidade"
					+" INNER JOIN Regiao ON Unidade.id_Regiao = Regiao.id_Regiao"
					+" WHERE Unidade.id_Regiao = ?");
			
			st.setInt(1, regiao.getIdRregiao());
			rs = st.executeQuery();
			
			List<Unidade> list = new ArrayList<Unidade>();
			while(rs.next()) {
				Unidade unidade = instantiateUnidade(rs, regiao);
				list.add(unidade);
			}
			for (Unidade obj : list) {
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
	
	private Regiao instantiateRegiao(ResultSet rs) throws SQLException{
		Regiao regiao = new Regiao();
		regiao.setIdRregiao(rs.getInt("id_Regiao"));
		regiao.setNome(rs.getString("Nome"));
		return regiao;
	}
	
	private Unidade instantiateUnidade(ResultSet rs, Regiao rg) throws SQLException {
		Unidade unidade = new Unidade();
		unidade.setNome(rs.getString("Nome"));
		unidade.setEndereco(rs.getString("Endereco"));
		unidade.setRegiao(rg);
		unidade.setIdUnidade(rs.getInt("id_Unidade"));
		unidade.setPublico(rs.getString("Publico"));
		return unidade;
	}

}
