public class Prontuario {
    private String observacoes;
    private String diagnostico;
    private String[] procedimentos;
    private int totalProcedimentos;

    public Prontuario(String observacoes) {
        this(observacoes, "");
    }

    public Prontuario(String observacoes, String diagnostico) {
        this.observacoes = observacoes != null ? observacoes : "";
        this.diagnostico = diagnostico != null ? diagnostico : "";
        this.procedimentos = new String[10];
        this.totalProcedimentos = 0;
    }

    public Prontuario(String observacoes, String diagnostico, String[] procedimentos, int totalProcedimentos) {
        this.observacoes = observacoes != null ? observacoes : "";
        this.diagnostico = diagnostico != null ? diagnostico : "";
        this.procedimentos = new String[10];
        this.totalProcedimentos = 0;
        if (procedimentos != null) {
            for (int i = 0; i < totalProcedimentos && i < procedimentos.length && i < 10; i++) {
                this.procedimentos[i] = procedimentos[i];
                this.totalProcedimentos++;
            }
        }
    }

    public void adicionarProcedimento(String procedimento) {
        if (totalProcedimentos < 10) {
            procedimentos[totalProcedimentos] = procedimento;
            totalProcedimentos++;
        }
    }

    public void adicionarProcedimento(String[] procs, int quantidade) {
        for (int i = 0; i < quantidade && i < procs.length && totalProcedimentos < 10; i++) {
            procedimentos[totalProcedimentos] = procs[i];
            totalProcedimentos++;
        }
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String exibirResumo() {
        String resumo = "Observacoes: " + observacoes;

        if (!diagnostico.equals("")) {
            resumo = resumo + "\nDiagnostico: " + diagnostico;
        }

        if (totalProcedimentos > 0) {
            resumo = resumo + "\nProcedimentos: ";
            for (int i = 0; i < totalProcedimentos; i++) {
                resumo = resumo + procedimentos[i];
                if (i < totalProcedimentos - 1) {
                    resumo = resumo + ", ";
                }
            }
        }
        return resumo;
    }

    @Override
    public String toString() {
        return exibirResumo();
    }
}
