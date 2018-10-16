package banco.modelo;


public class Cliente extends Pessoa {
	private int clienteID;
	private double rendaMensal;

	public Cliente() { super(); }
	
	public Cliente(int clienteID, String nome, String endereco, long cpf, long rg,
			long telefone, double rendaMensal) {
		super(nome, endereco, cpf, rg, telefone);

		this.rendaMensal = rendaMensal;
	}

	public int getClienteID() {
		return clienteID;
	}

	public void setClienteID(int clienteID) {
		this.clienteID = clienteID;
	}

	public double getRendaMensal() {
		return rendaMensal;
	}

	public void setRendaMensal(double rendaMensal) {
		this.rendaMensal = rendaMensal;
	}

	@Override
	public String toString() {
		return super.toString()
				+ String.format("\nRenda mensal: R$ %.2f", 
						getRendaMensal());
	}
	
	
	
}