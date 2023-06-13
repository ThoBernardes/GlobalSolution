package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ProdutoDao;
import model.entities.Mercado;
import model.entities.Produto;
import model.entities.Regiao;
import model.entities.Unidade;

public class ProdutoDaoJDBC implements ProdutoDao{
	
	Connection conn;
	
	public ProdutoDaoJDBC() {
		
	}

	public ProdutoDaoJDBC(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void insert(Produto produto) {
		PreparedStatement st = null;
		try {
			Connection conn = DB.getConnection();
			st = conn.prepareStatement("INSERT INTO Produto (id_Produto, nome, id_Mercado, lote, quantidade, validade) VALUES (?, ?, ?, ?, ?, ?)");
			
			
			st.setInt(1, produto.getIdProduto());
			st.setString(2, produto.getNome());
			st.setInt(3, produto.getMercado().getIdMercado());
			st.setInt(4, produto.getLote());
			st.setInt(5, produto.getQuantdade());
			st.setDate(6, produto.getValidade());
			int rows = st.executeUpdate();
			if(rows == 0){
				throw new DbException("Erro inesperado !!");
			}else{
				System.out.println("Produto adicionado !!");
			}
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
    	}	
	}

	@Override
	public void update(Produto produto) {
		PreparedStatement st = null;
		try {
			Connection conn = DB.getConnection();
			st = conn.prepareStatement("UPDATE Produto SET nome = ?, id_Mercado = ?, lote = ?, quantidade = ?, validade = ? WHERE id_Produto = ?");
			
			
			st.setString(1, produto.getNome());
			st.setInt(2, produto.getMercado().getIdMercado());
			st.setInt(3, produto.getLote());
			st.setInt(4, produto.getQuantdade());
			st.setDate(5, produto.getValidade());
			st.setInt(6, produto.getIdProduto());
			
			int rows = st.executeUpdate();
			if(rows == 0){
				throw new DbException("ID não encontrado");
			}else{
				System.out.println("Produto Atualizado !!");
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
			st = conn.prepareStatement("DELETE FROM Produto WHERE id_Produto = ?");
			
			st.setInt(1 , id);
			int rows = st.executeUpdate();
			if(rows == 0){
				throw new DbException("ID não encontrado");
			}else{
				System.out.println("Produto Deletado !!");
			}
		}catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	    	DB.closeStatement(st);    	
	    }
		
	}

	@Override
	public Produto findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			Connection conn = DB.getConnection();
			st = conn.prepareStatement("SELECT Produto.*, Mercado.id_Mercado AS id_Mercado"
					+" FROM Produto"
					+" INNER JOIN Mercado ON Produto.id_Mercado = Mercado.id_Mercado"
					+" WHERE Produto.id_Produto = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()){
				Mercado mec = instantiateMercado(rs, null);
				Produto produto = instantiateProduto(rs, mec);
				return produto;
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
	public List<Produto> mostarProdutos() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT Produto.*, Mercado.id_Regiao AS id_Mercado"
					+" FROM Produto"
					+" INNER JOIN Mercado ON Produto.id_Mercado = Mercado.id_Regiao");
			
			rs = st.executeQuery();
			
			List<Produto> list = new ArrayList<Produto>();
			while(rs.next()) {
				Regiao regiao = new Regiao();
				Mercado mercado = instantiateMercado(rs, regiao);
				Produto produto = instantiateProduto(rs, mercado);
				list.add(produto);
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
		mercado.setIdMercado(rs.getInt("id_Mercado"));
		
		return mercado;		
	}
	
	public List<Produto> mostarProdutoFiltrado(Mercado mercado) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT Produto.*, Mercado.id_Regiao AS id_Mercado"
					+" FROM Produto"
					+" INNER JOIN Mercado ON Produto.id_Mercado = Mercado.id_Regiao"
					+" WHERE Produto.id_Mercado = ?");
			
			st.setInt(1, mercado.getIdMercado());
			rs = st.executeQuery();
			
			List<Produto> list = new ArrayList<Produto>();
			while(rs.next()) {
				Produto produto = instantiateProduto(rs, mercado);
				list.add(produto);
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
	
	private Produto instantiateProduto(ResultSet rs, Mercado mec) throws SQLException{
		Produto produto = new Produto();
		produto.setNome(rs.getString("Nome"));
		produto.setLote(rs.getInt("Lote"));
		produto.setQuantdade(rs.getInt("Quantidade"));
		produto.setMercado(mec);
		produto.setIdProduto(rs.getInt("id_Produto"));
		produto.setValidade(rs.getDate("Validade"));
		return produto;
	}


}
