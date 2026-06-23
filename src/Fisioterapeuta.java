public class Fisioterapeuta extends Profissional {
    // Subclasse concreta representa o profissional de fisioterapia.
    // Cada especialidade implementa exibirResumo() de forma específica,
    // demonstrando polimorfismo na hierarquia de Profissional.

    public Fisioterapeuta(String nome, String cpf) {
        super(nome, cpf, "fisioterapia");
    }

    public Fisioterapeuta(String nome, String cpf, String registroProfissional, double valorConsulta) {
        super(nome, cpf, "fisioterapia", registroProfissional, valorConsulta);
    }

    public Fisioterapeuta(String nome, String cpf, String registroProfissional, double valorConsulta,
                          String[] dias, int totalDias) {
        super(nome, cpf, "fisioterapia", registroProfissional, valorConsulta, dias, totalDias);
    }

    @Override
    public void exibirResumo() {
        // Sobrescrita em nível de especialidade: embora todas as subclasses tenham resumo,
        // cada uma pode apresentar sua própria mensagem e focar nos dados relevantes.
        System.out.println("Nome: " + getNome()
                + " | Conselho: " + getRegistroProfissional()
                + " | Especialidade: " + getEspecialidade());
    }

    /**
     * Reutiliza a representação de `Profissional` e identifica a especialidade.
     * Útil ao imprimir ou registrar objetos desta classe.
     */
    @Override
    public String toString() {
        return "Fisioterapeuta{" + super.toString() + "}";
    }
}
