import java.util.List;

public class Banco {

	private String nome;
	private List<IConta> contas;

	public Banco(String nome, List<IConta> contas) {
		this.nome = nome;
		this.contas = contas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<IConta> getContas() {
		return contas;
	}

	public void setContas(List<IConta> contas) {
		this.contas = contas;
	}
}
