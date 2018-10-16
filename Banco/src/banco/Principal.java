package banco;
import java.util.ArrayList;
import java.util.Scanner;

import banco.dao.DbConnection;
import banco.modelo.Cliente;
import banco.modelo.Conta;
import banco.modelo.Pessoa;


public class Principal {

	public static void main(String[] args) {

		DbConnection pdb = new DbConnection();
		
		Cliente c1 = new Cliente(0, "Osvaldo", "Rua Paraná", 4544, 456456, 46564, 4656456);
		Cliente c2 = new Cliente(0, "Sabrina", "Rua Schroeder", 7877, 23123213, 4546456, 456456);
		
		pdb.addCliente(c1);
		pdb.addCliente(c2);
		
		System.out.println("Base Inici al: ");
		for (Cliente cliente : pdb.getClientes()) {
			System.out.println(cliente);
			System.out.println("----------------------------------");
		}
		
		c1.setEndereco("Ruá Azliravolna");
		c2.setNome("Rafael");
		
		pdb.updateCliente(c1);
		pdb.updateCliente(c2);
		
		System.out.println("Pós Update: ");
		for (Cliente cliente : pdb.getClientes()) {
			System.out.println(cliente);
			System.out.println("----------------------------------");
		}
		
		pdb.deleteCliente(1);
		
		System.out.println("Pós delete: ");
		for (Cliente cliente : pdb.getClientes()) {
			System.out.println(cliente);
			System.out.println("----------------------------------");
		}
	}
}