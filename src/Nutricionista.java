public class Nutricionista extends Profissional {
    // Subclasse concreta representa o profissional de nutrição.
    // Polimorfismo: o mesmo método exibirResumo() é usado na chamada,
    // mas cada especialidade define seu próprio comportamento.

    public Nutricionista(String nome, String cpf) {
        super(nome, cpf, "nutricao");
    }

    public Nutricionista(String nome, String cpf, String registroProfissional, double valorConsulta) {
        super(nome, cpf, "nutricao", registroProfissional, valorConsulta);
    }

    public Nutricionista(String nome, String cpf, String registroProfissional, double valorConsulta,
                         String[] dias, int totalDias) {
        super(nome, cpf, "nutricao", registroProfissional, valorConsulta, dias, totalDias);
    }

    @Override
    public void exibirResumo() {
        System.out.println("Nome: " + getNome()
                + " | Conselho: " + getRegistroProfissional()
                + " | Especialidade: " + getEspecialidade());
    }
}
