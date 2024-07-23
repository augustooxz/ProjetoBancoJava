import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		List<IConta> contas = new ArrayList<>();
		Banco banco = new Banco("Meu Banco", contas);

		boolean exit = false;

		while (!exit) {
			System.out.println("Escolha uma opção:");
			System.out.println("1. Criar Conta Corrente");
			System.out.println("2. Criar Conta Poupança");
			System.out.println("3. Depositar");
			System.out.println("4. Transferir");
			System.out.println("5. Sacar");
			System.out.println("6. Imprimir Extrato");
			System.out.println("7. Sair");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consumir a nova linha

			switch (choice) {
				case 1:
					criarConta(scanner, banco, "corrente");
					break;

				case 2:
					criarConta(scanner, banco, "poupança");
					break;

				case 3:
					IConta contaDepositar = autenticarConta(scanner, banco);
					if (contaDepositar != null) {
						System.out.print("Digite o valor para depositar: ");
						double valorDepositar = scanner.nextDouble();
						contaDepositar.depositar(valorDepositar);
						System.out.println("Depósito realizado com sucesso!");
					}
					break;

				case 4:
					IConta contaTransferirOrigem = autenticarConta(scanner, banco);
					if (contaTransferirOrigem != null) {
						System.out.println("Selecione a conta destino:");
						IConta contaTransferirDestino = selecionarConta(banco.getContas(), scanner);
						if (contaTransferirDestino != null) {
							System.out.print("Digite o valor para transferir: ");
							double valorTransferir = scanner.nextDouble();
							contaTransferirOrigem.transferir(valorTransferir, contaTransferirDestino);
							System.out.println("Transferência realizada com sucesso!");
						}
					}
					break;

				case 5:
					IConta contaSacar = autenticarConta(scanner, banco);
					if (contaSacar != null) {
						System.out.print("Digite o valor para sacar: ");
						double valorSacar = scanner.nextDouble();
						contaSacar.sacar(valorSacar);
						System.out.println("Saque realizado com sucesso!");
					}
					break;

				case 6:
					IConta contaExtrato = autenticarConta(scanner, banco);
					if (contaExtrato != null) {
						contaExtrato.imprimirExtrato();
					}
					break;

				case 7:
					exit = true;
					System.out.println("Obrigado por usar nosso banco. Até logo!");
					break;

				default:
					System.out.println("Opção inválida! Tente novamente.");
					break;
			}
		}

		scanner.close();
	}

	private static void criarConta(Scanner scanner, Banco banco, String tipo) {
		System.out.print("Digite o nome do cliente: ");
		String nome = scanner.nextLine();
		System.out.print("Digite a senha do cliente: ");
		String senha = scanner.nextLine();
		Cliente cliente = new Cliente(nome, senha);
		IConta conta;
		if (tipo.equals("corrente")) {
			conta = new ContaCorrente(cliente);
		} else {
			conta = new ContaPoupanca(cliente);
		}
		banco.getContas().add(conta);
		System.out.println("Conta " + tipo + " criada com sucesso!");
	}

	private static IConta selecionarConta(List<IConta> contas, Scanner scanner) {
		System.out.println("Selecione a conta:");
		for (int i = 0; i < contas.size(); i++) {
			IConta conta = contas.get(i);
			System.out.println((i + 1) + ". " + ((Conta) conta).getCliente().getNome() + " - " + conta.getClass().getSimpleName());
		}
		int index = scanner.nextInt() - 1;
		if (index >= 0 && index < contas.size()) {
			return contas.get(index);
		} else {
			System.out.println("Conta inválida! Tente novamente.");
			return null;
		}
	}

	private static IConta autenticarConta(Scanner scanner, Banco banco) {
		System.out.print("Digite o nome do cliente: ");
		String nome = scanner.nextLine();
		System.out.print("Digite a senha do cliente: ");
		String senha = scanner.nextLine();

		for (IConta conta : banco.getContas()) {
			if (((Conta) conta).getCliente().getNome().equals(nome) && ((Conta) conta).autenticar(senha)) {
				return conta;
			}
		}

		System.out.println("Nome ou senha incorretos! Tente novamente.");
		return null;
	}
}
