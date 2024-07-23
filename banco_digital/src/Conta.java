import java.util.ArrayList;
import java.util.List;

public abstract class Conta implements IConta {

	private static final int AGENCIA_PADRAO = 1234;
	private static int SEQUENCIAL = 1;

	protected int agencia;
	protected int numero;
	protected double saldo;
	protected Cliente cliente;
	protected List<Transacao> transacoes;

	public Conta(Cliente cliente) {
		this.agencia = Conta.AGENCIA_PADRAO;
		this.numero = SEQUENCIAL++;
		this.cliente = cliente;
		this.transacoes = new ArrayList<>();
	}

	public void sacar(double valor) {
		saldo -= valor;
		transacoes.add(new Transacao("Saque", valor));
	}

	public void depositar(double valor) {
		saldo += valor;
		transacoes.add(new Transacao("Depósito", valor));
	}

	public void transferir(double valor, IConta contaDestino) {
		this.sacar(valor);
		contaDestino.depositar(valor);
		transacoes.add(new Transacao("Transferência para " + ((Conta) contaDestino).getCliente().getNome(), valor));
	}

	public int getAgencia() {
		return agencia;
	}

	public int getNumero() {
		return numero;
	}

	public double getSaldo() {
		return saldo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	protected void imprimirInfosComuns() {
		System.out.println(String.format("Titular: %s", this.cliente.getNome()));
		System.out.println(String.format("Agencia: %d", this.agencia));
		System.out.println(String.format("Numero: %d", this.numero));
		System.out.println(String.format("Saldo: %.2f", this.saldo));
	}

	public boolean autenticar(String senha) {
		return this.cliente.getSenha().equals(senha);
	}

	public void imprimirTransacoes() {
		for (Transacao transacao : transacoes) {
			System.out.println(transacao);
		}
	}
}
