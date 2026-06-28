// Classe abstrata intermediária representa o conceito geral de um profissional na clínica.
// A herança de Pessoa para Profissional permite compartilhar os atributos comuns de pessoa,
// enquanto as subclasses concretas fornecem o comportamento específico de cada especialidade.
public abstract class Profissional extends Pessoa implements Exportavel {
    private String especialidade;
    private String registroProfissional;
    private double valorConsulta;
    private String[] diasDisponiveis;
    private int totalDias;

    // Construtor mínimo com os dados compartilhados de Pessoa e Profissional
    public Profissional(String nome, String cpf, String especialidade) {
        super(nome, cpf);
        setEspecialidade(especialidade);
        this.registroProfissional = "";
        this.valorConsulta = 0;
        this.diasDisponiveis = new String[7];
        this.totalDias = 0;
    }

    public Profissional(String nome, String cpf, String especialidade, String registroProfissional, double valorConsulta) {
        super(nome, cpf);
        setEspecialidade(especialidade);
        setRegistroProfissional(registroProfissional);
        setValorConsulta(valorConsulta);
        this.diasDisponiveis = new String[7];
        this.totalDias = 0;
    }

    // Construtor completo com dias de atendimento
    public Profissional(String nome, String cpf, String especialidade, String registroProfissional,
                        double valorConsulta, String[] dias, int totalDias) {
        super(nome, cpf);
        setEspecialidade(especialidade);
        setRegistroProfissional(registroProfissional);
        setValorConsulta(valorConsulta);
        this.diasDisponiveis = new String[7];
        this.totalDias = 0;
        setDias(dias, totalDias);
    }

    public void atualizar(String registro, double valor) {
        setRegistroProfissional(registro);
        setValorConsulta(valor);
    }

    public void atualizar(String registro, double valor, String[] dias, int totalDias) {
        setRegistroProfissional(registro);
        setValorConsulta(valor);
        setDias(dias, totalDias);
    }

    // verifica se o profissional atende naquele dia
    public boolean atendeNoDia(String dia) {
        if (dia == null || dia.trim().isEmpty()) return false;
        String diaNormalizado = dia.trim().toLowerCase();
        for (int i = 0; i < totalDias; i++) {
            if (diasDisponiveis[i] != null && diasDisponiveis[i].toLowerCase().equals(diaNormalizado)) {
                return true;
            }
        }
        return false;
    }

    public String[] getDiasDisponiveis() {
        return diasDisponiveis.clone();
    }

    public String getDiasDisponiveisFormatados() {
        if (totalDias == 0) {
            return "nenhum dia definido";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < totalDias; i++) {
            if (diasDisponiveis[i] != null) {
                if (builder.length() > 0) {
                    builder.append(", ");
                }
                builder.append(diasDisponiveis[i]);
            }
        }
        return builder.toString();
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public double getValorConsulta() {
        return valorConsulta;
    }

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    // valida as especialidades aceitas pela clinica
    public static boolean especialidadeValida(String esp) {
        if (esp == null) return false;
        String s = esp.toLowerCase().trim();
        if (s.equals("clinica geral")) return true;
        if (s.equals("fisioterapia")) return true;
        if (s.equals("psicologia")) return true;
        if (s.equals("nutricao")) return true;
        return false;
    }

    // A classe Profissional continua abstrata e força as subclasses a
    // implementarem o comportamento específico de exibição do resumo.
    public String exportarParaTexto() {
        return toString();
    }

    public abstract void exibirResumo();

    public void setEspecialidade(String esp) {
        if (!especialidadeValida(esp)) throw new DadosInvalidosException("Especialidade inválida: " + esp);
        this.especialidade = esp.toLowerCase().trim();
    }

    public void setValorConsulta(double valor) {
        if (valor < 0) throw new DadosInvalidosException("Valor de consulta negativo: " + valor);
        this.valorConsulta = valor;
    }

    public void setRegistroProfissional(String reg) {
        if (reg == null) reg = "";
        this.registroProfissional = reg.trim();
    }

    public void setDias(String[] dias, int totalDias) {
        if (dias == null) {
            this.totalDias = 0;
            this.diasDisponiveis = new String[7];
            return;
        }
        if (totalDias < 0 || totalDias > dias.length || totalDias > 7) {
            throw new DadosInvalidosException("Total de dias inválido: " + totalDias);
        }
        this.diasDisponiveis = new String[7];
        this.totalDias = totalDias;
        for (int i = 0; i < totalDias; i++) {
            if (dias[i] == null || dias[i].trim().isEmpty()) {
                throw new DadosInvalidosException("Dia inválido em posição: " + i);
            }
            this.diasDisponiveis[i] = dias[i].trim().toLowerCase();
        }
    }

    /**
     * Retorna os dados principais do profissional (nome, cpf,
     * especialidade, registro e valor da consulta).
     * Bom para inspeção rápida e geração de relatórios simples.
     */
    @Override
    public String toString() {
        return "Profissional{nome='" + getNome() + "', cpf='" + getCpf() + "', especialidade='" + especialidade
                + "', registro='" + registroProfissional + "', valorConsulta=" + valorConsulta + "}";
    }
}
