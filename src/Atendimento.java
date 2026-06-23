public class Atendimento {
    public int indiceConsulta;
    private final Prontuario prontuario;

    // registro basico - so observacoes
    public Atendimento(int indiceConsulta, String observacoes) {
        this.indiceConsulta = indiceConsulta;
        this.prontuario = new Prontuario(observacoes);
    }

    public Atendimento(int indiceConsulta, String observacoes, String diagnostico) {
        this.indiceConsulta = indiceConsulta;
        this.prontuario = new Prontuario(observacoes, diagnostico);
    }

    // registro completo com procedimentos ja definidos
    public Atendimento(int indiceConsulta, String observacoes, String diagnostico,
                       String[] procedimentos, int totalProcedimentos) {
        this.indiceConsulta = indiceConsulta;
        this.prontuario = new Prontuario(observacoes, diagnostico, procedimentos, totalProcedimentos);
    }

    // adiciona um procedimento ao prontuario
    public void adicionarProcedimento(String procedimento) {
        prontuario.adicionarProcedimento(procedimento);
    }

    // adiciona varios procedimentos ao prontuario
    public void adicionarProcedimento(String[] procs, int quantidade) {
        prontuario.adicionarProcedimento(procs, quantidade);
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public String getDiagnostico() {
        return prontuario.getDiagnostico();
    }

    public String exibirResumo() {
        return prontuario.exibirResumo();
    }

    @Override
    public String toString() {
        return exibirResumo();
    }
}
