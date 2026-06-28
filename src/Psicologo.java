public class Psicologo extends Profissional {
    private String abordagemTerapeutica;

    public Psicologo(String nome, String cpf) {
        super(nome, cpf, "psicologia");
        this.abordagemTerapeutica = "Nao informada";
    }

    public Psicologo(String nome, String cpf, String registroProfissional, double valorConsulta) {
        super(nome, cpf, "psicologia", registroProfissional, valorConsulta);
        this.abordagemTerapeutica = "Nao informada";
    }

    public Psicologo(String nome, String cpf, String registroProfissional, double valorConsulta,
                     String[] dias, int totalDias) {
        super(nome, cpf, "psicologia", registroProfissional, valorConsulta, dias, totalDias);
        this.abordagemTerapeutica = "Nao informada";
    }

    public String getAbordagemTerapeutica() {
        return abordagemTerapeutica;
    }

    public void setAbordagemTerapeutica(String abordagemTerapeutica) {
        if (abordagemTerapeutica == null || abordagemTerapeutica.trim().isEmpty()) {
            throw new DadosInvalidosException("Abordagem terapêutica inválida");
        }
        this.abordagemTerapeutica = abordagemTerapeutica.trim();
    }

    @Override
    public void exibirResumo() {
        System.out.println("Nome: " + getNome()
                + " | Conselho: " + getRegistroProfissional()
                + " | Especialidade: " + getEspecialidade()
                + " | Abordagem: " + abordagemTerapeutica);
    }

    @Override
    public String toString() {
        return "Psicologo{" + super.toString() + ", abordagemTerapeutica='" + abordagemTerapeutica + "'}";
    }
}