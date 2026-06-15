public class Psicologo extends Profissional {
    // Subclasse concreta representa o profissional de psicologia.
    // O método exibirResumo() demonstra polimorfismo: cada classe especializada
    // exibe seu próprio resumo de forma diferente.

    public Psicologo(String nome, String cpf) {
        super(nome, cpf, "psicologia");
    }

    public Psicologo(String nome, String cpf, String registroProfissional, double valorConsulta) {
        super(nome, cpf, "psicologia", registroProfissional, valorConsulta);
    }

    public Psicologo(String nome, String cpf, String registroProfissional, double valorConsulta,
                     String[] dias, int totalDias) {
        super(nome, cpf, "psicologia", registroProfissional, valorConsulta, dias, totalDias);
    }

    @Override
    public void exibirResumo() {
        System.out.println("Nome: " + getNome()
                + " | Conselho: " + getRegistroProfissional()
                + " | Especialidade: " + getEspecialidade());
    }
}
