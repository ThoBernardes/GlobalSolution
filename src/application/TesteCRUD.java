package application;

import model.dao.DaoFactory;
import model.dao.SistemaDao;

public class TesteCRUD {

	public static void main(String[] args) {
		SistemaDao conversa = DaoFactory.createSistemaDao();
		conversa.crud();

	}

}
