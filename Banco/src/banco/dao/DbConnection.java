package banco.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import banco.modelo.Cliente;

public class DbConnection {


		private static final String URL = "jdbc:sqlite:banco.db";
		private static final String TABLE = "clientes";
		private Connection conn; 
		
		private PreparedStatement selectTodosClientes; 
		private PreparedStatement selectClientePorNome;
		private PreparedStatement insertNovoCliente; 
		private PreparedStatement updateEnderecoClientePorId;
		private PreparedStatement updateCliente;
		private PreparedStatement deleteCliente;
		
		public DbConnection() {
			try {
				conn = DriverManager.getConnection(URL);
				
				createTable();
				
				selectTodosClientes = conn.prepareStatement(
						"SELECT * FROM clientes");
				
				selectClientePorNome = conn.prepareStatement(
						"SELECT * FROM clientes WHERE nome = ?");
				
				insertNovoCliente = conn.prepareStatement(
						"INSERT INTO clientes " +
						"(nome, endereco, cpf, rg, telefone, rendaMensal) " +
						"VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				
				updateEnderecoClientePorId = conn.prepareStatement(
						"UPDATE clientes " +
						"SET endereco = ? WHERE clienteID = ?");
				
				updateCliente = conn.prepareStatement(
						"UPDATE clientes " +
						"SET nome = ?, endereco = ?, " +
						"cpf = ?, rg = ?, telefone = ?, rendaMensal = ? WHERE clienteID = ?");
				
				deleteCliente = conn.prepareStatement(
						"DELETE FROM clientes WHERE clienteID = ?");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		private void createTable() throws SQLException {
		    String sqlCreate = "CREATE TABLE IF NOT EXISTS " + TABLE
		            + "  (clienteID           INTEGER,"
		            + "   nome            VARCHAR(50),"
		            + "   endereco          VARCHAR(255),"
		            + "   cpf           INTEGER,"
		            + "   rg           INTEGER,"
		            + "   telefone           INTEGER,"
		            + "   rendaMensal           INTEGER,"
		            + "   PRIMARY KEY (clienteID))";

		    Statement stmt = conn.createStatement();
		    stmt.execute(sqlCreate);
		}
		
		private Cliente getClienteFromRs(ResultSet rs) throws SQLException {
			return new Cliente(
					rs.getInt("clienteID"),
					rs.getString("nome"),
					rs.getString("endereco"),
					rs.getLong("cpf"),
					rs.getLong("rg"),
					rs.getLong("telefone"),
					rs.getDouble("rendaMensal")
					);
		}
		
		private List<Cliente> executaSelect(PreparedStatement stmt) {
			List<Cliente> resultado = null;
			ResultSet rs = null;
			
			try {
				rs = stmt.executeQuery();
				resultado = new ArrayList<>();
				
				while (rs.next()) {
					resultado.add(getClienteFromRs(rs));
				}
			}  catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
					close();
				}
			}
			
			return resultado;
		}
		
		public List<Cliente> getClientes() {
			return executaSelect(selectTodosClientes);		
		}
		
		public List<Cliente> getClientePorNome(String nome) {
			try {
				selectClientePorNome.setString(1, nome);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return executaSelect(selectClientePorNome);
		}
		
		public int updateCliente(Cliente p) {
			int resultado = 0;
			
			try {
				updateCliente.setString(1, p.getNome());
				updateCliente.setString(2, p.getEndereco());
				updateCliente.setLong(3, p.getCpf());
				updateCliente.setLong(4, p.getRg());
				updateCliente.setLong(5, p.getTelefone());
				updateCliente.setDouble(6, p.getRendaMensal());
				updateCliente.setInt(7, p.getClienteID());
				
				resultado = updateCliente.executeUpdate();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			return resultado;
		}
		
		public int deleteCliente(int id) {
			int resultado = 0;
			
			try {
				deleteCliente.setInt(1, id);
				
				resultado = deleteCliente.executeUpdate();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			return resultado;
		}
		
		
		public int updateEnderecoClientePorId(int id, String endereco) {
			int resultado = 0;
			
			try {
				updateEnderecoClientePorId.setString(1, endereco);
				updateEnderecoClientePorId.setInt(2, id);
				
				resultado = updateEnderecoClientePorId.executeUpdate();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			return resultado;
		}
		
		public int addCliente(Cliente c) {
			int id = addCliente(c.getNome(), c.getEndereco(), c.getCpf(), c.getRg(), c.getTelefone(), c.getRendaMensal());
			
			c.setClienteID(id);
			
			return id;
		}

		public int addCliente(String nome, String endereco, long cpf, long rg,
				long telefone, double rendaMensal) {
			int resultado = 0;
			
			try {
				insertNovoCliente.setString(1, nome);
				insertNovoCliente.setString(2, endereco);
				insertNovoCliente.setLong(3, cpf);
				insertNovoCliente.setLong(4, rg);
				insertNovoCliente.setLong(5, telefone);
				insertNovoCliente.setDouble(6, rendaMensal);
				
				resultado = insertNovoCliente.executeUpdate();
				
				ResultSet rs = insertNovoCliente.getGeneratedKeys();
				if (rs.next())
					resultado = rs.getInt(1);
			} catch (SQLException ex) {
				ex.printStackTrace();
				close();
			}
			
			return resultado;
		}
		
		public void close() {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
