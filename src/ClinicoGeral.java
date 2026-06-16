public class ClinicoGeral extends Profissional {
    // Subclasse concreta representa o clínico geral.
    // A implementação de exibirResumo() reforça que cada especialidade tem sua própria
    // forma de apresentar o resumo, mesmo usando o mesmo método definido em Pessoa.

    public ClinicoGeral(String nome, String cpf) {
        super(nome, cpf, "clinica geral");
    }

    public ClinicoGeral(String nome, String cpf, String registroProfissional, double valorConsulta) {
        super(nome, cpf, "clinica geral", registroProfissional, valorConsulta);
    }

    public ClinicoGeral(String nome, String cpf, String registroProfissional, double valorConsulta,
                        String[] dias, int totalDias) {
        super(nome, cpf, "clinica geral", registroProfissional, valorConsulta, dias, totalDias);
    }

    @Override
    public void exibirResumo() {
        System.out.println("Nome: " + getNome()
                + " | Conselho: " + getRegistroProfissional()
                + " | Especialidade: " + getEspecialidade());
    }

    /**
     * Mantém o padrão de apresentação entre profissionais e facilita leitura.
     * Útil ao imprimir ou registrar instâncias.
     */
    @Override
    public String toString() {
        return "ClinicoGeral{" + super.toString() + "}";
    }
}
