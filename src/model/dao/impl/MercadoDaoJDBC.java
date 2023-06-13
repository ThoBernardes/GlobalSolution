package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.MercadoDao;
import model.entities.Mercado;
import model.entities.Regiao;

public class MercadoDaoJDBC implements MercadoDao {
	
	private Connection conn;
	
	public MercadoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Mercado mercado) {
		PreparedStatement st = null;
		try {
			Connection conn = DB.getConnection();
			st = conn.prepareStatement("INSERT INTO Mercado (id_Mercado, nome, endereco, id_Regiao) VALUES (?, ?, ?, ?)");
			
			st.setInt(1, mercado.getIdMercado());
			st.setString(2, mercado.getNome());
			st.setString(3, mercado.getEndereco());
			st.setInt(4, mercado.getRegiao().getIdRregiao());
			
			int rows = st.executeUpdate();
			if(rows == 0){
				 throw new DbException("Erro inesperado !!");
			 }else{
				 System.out.println("Mercado adicionado !!");
			 }
		}catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	    	DB.closeStatement(st);
	    	
	    }
		
	}

	@Override
	public void update(Mercado mercado) {
		PreparedStatement st = null;
		try {
			Connection conn = DB.getConnection();
			st = conn.prepareStatement("UPDATE Mercado SET nome = ?, endereco = ? WHERE id_Mercado = ?");
			
			st.setString(1, mercado.getNome());
			st.setString(2, mercado.getEndereco());
			st.setInt(3 , mercado.getIdMercado());
			
			int rows = st.executeUpdate();
			if(rows == 0){
				throw new DbException("ID não encontrado");
			}else{
				System.out.println("Mercado Atualizado !!");
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
			st = conn.prepareStatement("DELETE FROM Mercado WHERE id_Mercado = ?");
			
			st.setInt(1 , id);
			int rows = st.executeUpdate();
			if(rows == 0){
				throw new DbException("ID não encontrado");
			}else{
				System.out.println("Mercado Deletado !!");
			}
		}catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	    	DB.closeStatement(st);    	
	    }
	}

	@Override
	public Mercado findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			Connection conn = DB.getConnection();
			st = conn.prepareStatement("SELECT Mercado.*, Regiao.id_Regiao AS id_Regiao"
					+" FROM Mercado"
					+" INNER JOIN Regiao ON Mercado.id_Regiao = Regiao.id_Regiao"
					+" WHERE Mercado.id_Mercado = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()){
				Regiao rg = instantiateRegiao(rs);				
				Mercado mercado = instantiateMercado(rs, rg);
				return mercado;
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
	public List<Mercado> mostarMercados() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT Mercado.*, Regiao.id_Regiao AS id_Regiao"
					+" FROM Mercado"
					+" INNER JOIN Regiao ON Mercado.id_Regiao = Regiao.id_Regiao");
			
			rs = st.executeQuery();
			
			List<Mercado> list = new ArrayList<Mercado>();
			while(rs.next()) {
				Regiao regiao = instantiateRegiao(rs);
				Mercado mercado = instantiateMercado(rs, regiao);
				list.add(mercado);
			}
			for (Mercado obj : list) {
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
	
	private Mercado instantiateMercado(ResultSet rs, Regiao rg) throws SQLException{
		Mercado mercado = new Mercado();
		mercado.setNome(rs.getString("Nome"));
		mercado.setEndereco(rs.getString("Endereco"));
		mercado.setRegiao(rg);
		mercado.setIdMercado(rs.getInt("id_Mercado"));
		
		return mercado;		
	}
	
	private Regiao instantiateRegiao(ResultSet rs) throws SQLException{
		Regiao regiao = new Regiao();
		regiao.setIdRregiao(rs.getInt("id_Regiao"));
		regiao.setNome(rs.getString("Nome"));
		return regiao;
	}
	
	public List<Mercado> mostarMercadosFiltrado(Regiao regiao) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT Mercado.*, Regiao.id_Regiao AS id_Regiao"
					+" FROM Mercado"
					+" INNER JOIN Regiao ON Mercado.id_Regiao = Regiao.id_Regiao"
					+" WHERE Mercado.id_Regiao = ?");
			
			st.setInt(1, regiao.getIdRregiao());
			rs = st.executeQuery();
			
			List<Mercado> list = new ArrayList<Mercado>();
			while(rs.next()) {
				Mercado mercado = instantiateMercado(rs, regiao);
				list.add(mercado);
			}
			for (Mercado obj : list) {
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
