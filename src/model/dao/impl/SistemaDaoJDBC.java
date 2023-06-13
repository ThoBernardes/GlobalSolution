package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.MercadoDao;
import model.dao.ProdutoDao;
import model.dao.RegiaoDao;
import model.dao.SistemaDao;
import model.dao.UnidadeDao;
import model.entities.Mercado;
import model.entities.Produto;
import model.entities.Regiao;
import model.entities.Unidade;

public class SistemaDaoJDBC implements SistemaDao{
	
	private Connection conn;

	public SistemaDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void crud() {
		Scanner sc = new Scanner(System.in);
		boolean saida = true;
		int menu;
		do{
			int opc;
			System.out.println("-----Menu-----");
			System.out.println("1-Banco de dados");
			System.out.println("2-Sistema de doação");
			System.out.println("3-Sair");
			System.out.print("Console:");
			menu = sc.nextInt();
			sc.nextLine();
			switch (menu) {
            case 1:
            	System.out.println("-----Banco de dados-----");
        		System.out.println("1-Mercados");
        		System.out.println("2-Região");
        		System.out.println("3-Unidades");
        		System.out.println("4-Produtos");
        		System.out.println("5-Voltar");
        		System.out.print("Console:");
        		opc = sc.nextInt();
        		switch (opc){
        		case 1:
        			mercadosBD();
        			break;
        		case 2:
        			regiaoBD();
        			break;
        		case 3:
        			unidadesBD();
        			break;
        		case 4:
        			produtosBD();
        		case 5:
        			break;
        		}
                break;
            case 2:
                doacao();
                break;
            case 3:              
                break;
		}
		}while(menu !=3);
	}

	private void doacao() {
		MercadoDao mercadoDao = DaoFactory.createMercadoDao();
		ProdutoDao produtoDao = DaoFactory.createProdutoDao();
		int opc;
		do {
			
			Scanner sc = new Scanner(System.in);
			System.out.println("-----Realizar Doação-----");
			System.out.println("-----Escolha o mercado-----");
			System.out.println("1- Mercado ID");
			System.out.println("2-Filtrar por Região");
			System.out.println("3- Mostrar todos");
			System.out.println("4- Voltar");
			opc = sc.nextInt();
			sc.nextLine();
			switch(opc){
			case 1:
				System.out.print("Insira o ID:");
				int idMercado = sc.nextInt();
				Mercado mercadoEscolhido = mercadoDao.findById(idMercado);
				Unidade UnidadeEscolhida = escolherUnidade(mercadoEscolhido.getRegiao().getIdRregiao());
				List<Produto> doacao =produtoPertoValidade(mercadoEscolhido);
				for (Produto obj : doacao) {
					System.out.println(obj);
				}
				break;
			case 2:
				filtraRegiao();
				break;
			case 3:
				mercadoDao.mostarMercados();
				break;
			case 4:
				break;
			
			}
			sc.close();
		}while(opc !=4);
		
	}

	private List<Produto> produtoPertoValidade(Mercado mercado) {
		ProdutoDao produtoDao = DaoFactory.createProdutoDao();
		List<Produto> produtos = produtoDao.mostarProdutoFiltrado(mercado);
		List<Produto> doacao = new ArrayList<Produto>();
		LocalDate hojeLd = LocalDate.of(2023, 6, 7);
		LocalDate prazoLd = hojeLd.plusDays(7);
		Date hoje = Date.valueOf(hojeLd);
		Date prazo = Date.valueOf(prazoLd);
		for (Produto obj : produtos) {
			Date validade = obj.getValidade();
			if (validade.compareTo(hoje) >= 0 && validade.compareTo(prazo) <= 0) {
				doacao.add(obj);
			}
		}
		
		return null;		
	}

	private Unidade escolherUnidade(int idRegiao) {
		Scanner sc = new Scanner(System.in);
		RegiaoDao regiaoDao = DaoFactory.createRegiaoDao();
		UnidadeDao unidadeDao = DaoFactory.createUnidadeDao();
		Regiao regiao = regiaoDao.findById(idRegiao);
		System.out.println("Selecione uma  das instituições acima");
		System.out.print("ID:");
		int id = sc.nextInt();
		sc.nextLine();
		Unidade unidade = unidadeDao.findById(id);
		sc.close();
		return unidade;
	}

	private void filtraRegiao() {
		Scanner sc = new Scanner(System.in);
		RegiaoDao regiaoDao = DaoFactory.createRegiaoDao();
		MercadoDao mercadoDao = DaoFactory.createMercadoDao();
		System.out.println("Insira o id da região:");
		int id = sc.nextInt();
		sc.nextLine();
		Regiao regiao = regiaoDao.findById(id);
		mercadoDao.mostarMercadosFiltrado(regiao);
		sc.close();
	}

	private void produtosBD() {
		Scanner sc = new Scanner(System.in);
		MercadoDao mercadoDao = DaoFactory.createMercadoDao();
		ProdutoDao produtoDao = DaoFactory.createProdutoDao();
		RegiaoDao regiaoDao = DaoFactory.createRegiaoDao();
		System.out.println("-----BD Mercados-----");
		System.out.println("1-Inserir");
		System.out.println("2-Deletar");
		System.out.println("3-Mostrar");
		System.out.println("4-Modificar");
		System.out.println("5-Econtrar");
		System.out.print("Console:");
		int opc = sc.nextInt();
		sc.nextLine();
		switch(opc) {
		
		case 1:
			System.out.print("ID Produto:");
			int idInsert = sc.nextInt();
			sc.nextLine();
			System.out.print("Nome:");
			String nome = sc.nextLine();
			System.out.print("Lote: ");
			int lote = sc.nextInt();
			sc.nextLine();
			System.out.print("Quantidade: ");
			int quantidade = sc.nextInt();
			sc.nextLine();
			System.out.print("Validade (formato dd/MM/yyyy): ");
	        String dataString = sc.nextLine();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        Date validade = null;
	        try {
	            java.util.Date dataUtil = dateFormat.parse(dataString);
	            validade = new Date(dataUtil.getTime());
	        } catch (ParseException e) {
	            System.out.println("Formato de data inválido.");
	        }
			System.out.print("Mercado: (Deseja criar uma nova Mercado?1-Sim,2-Não)");
			int opcmercado = sc.nextInt();
			sc.nextLine();
			Mercado mercado = new Mercado();
			if(1 == opcmercado) {
				System.out.print("ID mercado:");
				int idMercado = sc.nextInt();
				sc.nextLine();
				System.out.print("Nome:");
				String nomeMercado = sc.nextLine();
				System.out.print("Endereço: ");
				String enderecoMercado = sc.nextLine();
				System.out.print("Região ID:");
				int idRegiao = sc.nextInt();
				sc.nextLine();
				Regiao regiaoMercado = regiaoDao.findById(idRegiao);
				mercado = new Mercado(idMercado,nomeMercado,enderecoMercado,regiaoMercado);
				
			}else {				
				System.out.print("Digite o id do mercado:");
				int idMercado = sc.nextInt();
				sc.nextLine();
				mercado = mercadoDao.findById(idMercado);				
			}
			Produto produto = new Produto(idInsert,nome,mercado,lote,quantidade,validade);
			produtoDao.insert(produto);
			break;
		
		case 2:
			System.out.println("Insira o ID:");
			int idDelete = sc.nextInt();
			sc.nextLine();
			produtoDao.deleteById(idDelete);
			break;
		
		case 3:
			List<Produto> produtos= produtoDao.mostarProdutos();
			for (Produto obj : produtos) {
				System.out.println(obj);
			}
			break;
		
		case 4:
			System.out.println("Insira o ID: ");
			int idUpdate = sc.nextInt();
			sc.nextLine();
			Produto produtoUp = produtoDao.findById(idUpdate);
			if(produtoUp == null) {
				break;
			}
			System.out.print("Nome:");
			String nomeUp = sc.nextLine();
			System.out.print("Lote: ");
			int loteUp = sc.nextInt();
			sc.nextLine();
			System.out.print("Quantidade: ");
			int quantidadeUp = sc.nextInt();
			sc.nextLine();
			System.out.print("Validade (formato dd/MM/yyyy): ");
	        String dataString2 = sc.nextLine();
	        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
	        Date validadeUp = null;
	        try {
	            java.util.Date dataUtil = dateFormat2.parse(dataString2);
	            validade = new Date(dataUtil.getTime());
	        } catch (ParseException e) {
	            System.out.println("Formato de data inválido.");
	        }
	        produtoUp.setNome(nomeUp);
	        produtoUp.setLote(loteUp);
	        produtoUp.setQuantdade(quantidadeUp);
	        produtoUp.setValidade(validadeUp);
	        produtoDao.update(produtoUp);
			break;
		
		case 5:
			System.out.println("Insira o ID: ");
			int idEncontrar = sc.nextInt();
			sc.nextLine();
			produtoDao.findById(idEncontrar);
			break;				
		}
		sc.close();
		
	}

	private void unidadesBD() {
		Scanner sc = new Scanner(System.in);
		UnidadeDao unidadeDao = DaoFactory.createUnidadeDao();
		RegiaoDao regiaoDao = DaoFactory.createRegiaoDao();
		System.out.println("-----BD Unidades-----");
		System.out.println("1-Inserir");
		System.out.println("2-Deletar");
		System.out.println("3-Mostrar");
		System.out.println("4-Modificar");
		System.out.println("5-Econtrar");
		System.out.print("Console:");
		int opc = sc.nextInt();
		sc.nextLine();
		switch(opc) {
		
		case 1:
			System.out.print("ID unidade:");
			int idInsert = sc.nextInt();
			sc.nextLine();
			System.out.print("Nome:");
			String nome = sc.nextLine();
			System.out.print("Endereço: ");
			String endereco = sc.nextLine();
			System.out.print("Publico: ");
			String publico = sc.nextLine();
			System.out.print("Região: (Deseja criar uma nova regiao?1-Sim,2-Não)");
			int opcregiao = sc.nextInt();
			sc.nextLine();
			Regiao regiao = new Regiao();
			if(1 == opcregiao) {
				System.out.print("ID região:");
				int idReg = sc.nextInt();
				sc.nextLine();
				System.out.print("Nome:");
				String nomeReg = sc.nextLine();
				regiao = new Regiao(idReg,nomeReg);
				regiaoDao.insert(regiao);
				
			}else {				
				System.out.print("Digite o id da região:");
				int idRegiao = sc.nextInt();
				sc.nextLine();
				regiao = regiaoDao.findById(idRegiao);				
			}
			Unidade unidade = new Unidade(idInsert,nome,endereco,publico,regiao);
			unidadeDao.insert(unidade);
			break;
		
		case 2:
			System.out.println("Insira o ID:");
			int idDelete = sc.nextInt();
			sc.nextLine();
			unidadeDao.deleteById(idDelete);
			break;
		
		case 3:
			unidadeDao.mostarUnidades();
			break;
		
		case 4:
			System.out.println("Insira o ID: ");
			int idUpdate = sc.nextInt();
			sc.nextLine();
			Unidade unidadeUp = unidadeDao.findById(idUpdate);
			if(unidadeUp == null) {
				break;
			}
			System.out.print("Nome:");
			String nomeUp = sc.nextLine();
			System.out.print("Endereço: ");
			String enderecoUp = sc.nextLine();
			System.out.print("Publico: ");
			String publicoUp = sc.nextLine();
			unidadeUp.setNome(nomeUp);
			unidadeUp.setEndereco(enderecoUp);
			unidadeUp.setPublico(publicoUp);
			break;
		
		case 5:
			System.out.println("Insira o ID: ");
			int idEncontrar = sc.nextInt();
			sc.nextLine();
			unidadeDao.findById(idEncontrar);
			break;
		}
		sc.close();
		
	}

	private void regiaoBD() {
		Scanner sc = new Scanner(System.in);
		RegiaoDao regiaoDao = DaoFactory.createRegiaoDao();
		System.out.println("-----BD Região-----");
		System.out.println("1-Inserir");
		System.out.println("2-Deletar");
		System.out.println("3-Mostrar");
		System.out.println("4-Modificar");
		System.out.println("5-Econtrar");
		System.out.print("Console:");
		int opc = sc.nextInt();
		sc.nextLine();
		switch(opc) {
		
		case 1:
			System.out.print("ID região:");
			int idInsert = sc.nextInt();
			sc.nextLine();
			System.out.print("Nome:");
			String nome = sc.nextLine();
			Regiao regiao = new Regiao(idInsert,nome);
			regiaoDao.insert(regiao);
			break;
		
		case 2:
			System.out.println("Insira o ID:");
			int idDelete = sc.nextInt();
			sc.nextLine();
			regiaoDao.deleteById(idDelete);
			break;
		
		case 3:
			regiaoDao.mostarRegioes();
			break;
		
		case 4:
			System.out.println("Insira o ID: ");
			int idUpdate = sc.nextInt();
			sc.nextLine();
			Regiao regiaoUp = regiaoDao.findById(idUpdate);
			if(regiaoUp == null) {
				break;
			}
			System.out.print("Nome:");
			String nomeUp = sc.nextLine();
			regiaoUp.setNome(nomeUp);
			break;
		
		case 5:
			System.out.println("Insira o ID: ");
			int idEncontrar = sc.nextInt();
			sc.nextLine();
			regiaoDao.findById(idEncontrar);
			break;				
		}
		sc.close();
	}

	private void mercadosBD() {
		Scanner sc = new Scanner(System.in);
		MercadoDao mercadoDao = DaoFactory.createMercadoDao();
		RegiaoDao regiaoDao = DaoFactory.createRegiaoDao();
		System.out.println("-----BD Mercados-----");
		System.out.println("1-Inserir");
		System.out.println("2-Deletar");
		System.out.println("3-Mostrar");
		System.out.println("4-Modificar");
		System.out.println("5-Econtrar");
		System.out.print("Console:");
		int opc = sc.nextInt();
		sc.nextLine();
		switch(opc) {
		
		case 1:
			System.out.print("ID mercado:");
			int idInsert = sc.nextInt();
			sc.nextLine();
			System.out.print("Nome:");
			String nome = sc.nextLine();
			System.out.print("Endereço: ");
			String endereco = sc.nextLine();
			System.out.print("Região: (Deseja criar uma nova regiao?1-Sim,2-Não)");
			int opcregiao = sc.nextInt();
			sc.nextLine();
			Regiao regiao = new Regiao();
			if(1 == opcregiao) {
				System.out.print("ID região:");
				int idReg = sc.nextInt();
				sc.nextLine();
				System.out.print("Nome:");
				String nomeReg = sc.nextLine();
				regiao = new Regiao(idReg,nomeReg);
				regiaoDao.insert(regiao);
				
			}else {				
				System.out.print("Digite o id da região:");
				int idRegiao = sc.nextInt();
				sc.nextLine();
				regiao = regiaoDao.findById(idRegiao);				
			}
			Mercado mercado = new Mercado(idInsert,nome,endereco,regiao);
			mercadoDao.insert(mercado);
			break;
		
		case 2:
			System.out.println("Insira o ID:");
			int idDelete = sc.nextInt();
			sc.nextLine();
			mercadoDao.deleteById(idDelete);
			break;
		
		case 3:
			mercadoDao.mostarMercados();
			break;
		
		case 4:
			System.out.println("Insira o ID: ");
			int idUpdate = sc.nextInt();
			sc.nextLine();
			Mercado mercadoUp = mercadoDao.findById(idUpdate);
			if(mercadoUp == null) {
				break;
			}
			System.out.print("Nome:");
			String nomeUp = sc.nextLine();
			System.out.print("Endereço: ");
			String enderecoUp = sc.nextLine();
			mercadoUp.setNome(nomeUp);
			mercadoUp.setEndereco(enderecoUp);
			break;
		
		case 5:
			System.out.println("Insira o ID: ");
			int idEncontrar = sc.nextInt();
			sc.nextLine();
			mercadoDao.findById(idEncontrar);
			break;				
		}
		sc.close();
	}
	

}
