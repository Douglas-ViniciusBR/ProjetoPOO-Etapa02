/**
 * Classe que representa uma consulta médica.
 *
 * Conceito de contrato em POO:
 * uma interface define um contrato, ou seja, um conjunto de comportamentos
 * que a classe deve obrigatoriamente implementar. Se a classe declara
 * 'implements Agendavel, Exportavel', ela precisa sobrescrever todos os
 * métodos exigidos pelas interfaces.
 */
public class Consulta implements Agendavel, Exportavel {
    public String cpfPaciente;
    public String nomeProfissional;
    public String data;
    public String horario;
    public String tipo;
    public String status;

    // sem tipo - assume inicial
    public Consulta(String cpfPaciente, String nomeProfissional, String data, String horario) {
        this.cpfPaciente = cpfPaciente;
        this.nomeProfissional = nomeProfissional;
        this.data = data;
        this.horario = horario;
        this.tipo = "inicial";
        this.status = "agendada";
    }

    public Consulta(String cpfPaciente, String nomeProfissional, String data, String horario, String tipo) {
        this.cpfPaciente = cpfPaciente;
        this.nomeProfissional = nomeProfissional;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = "agendada";
    }

    // esse aqui a gente usa na remarcacao pra poder setar o status direto
    public Consulta(String cpfPaciente, String nomeProfissional, String data,
                    String horario, String tipo, String status) {
        this.cpfPaciente = cpfPaciente;
        this.nomeProfissional = nomeProfissional;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = status;
    }

    /**
     * Sobrescreve o método exigido pela interface Agendavel.
     * Regras de negócio:
     * - não permite reagendar uma consulta já realizada;
     * - atualiza data, horário e status para agendada.
     */
    @Override
    public void agendar(String data, String horario) {
        if (this.status != null && this.status.equals("realizada")) {
            throw new IllegalStateException("Não é possível reagendar uma consulta já realizada.");
        }

        if (data == null || data.trim().isEmpty() || horario == null || horario.trim().isEmpty()) {
            throw new IllegalArgumentException("Data e horário são obrigatórios para agendar.");
        }

        this.data = data;
        this.horario = horario;
        this.status = "agendada";
    }

    /**
     * Sobrescreve o método exigido pela interface Agendavel.
     * Regras de negócio:
     * - não permite cancelar consultas já realizadas;
     * - marca a consulta como cancelada.
     */
    @Override
    public void cancelar() {
        if ("realizada".equals(this.status)) {
            throw new IllegalStateException("Não é possível cancelar uma consulta já realizada.");
        }

        this.status = "cancelada";
    }

    /**
     * Método extra para manter compatibilidade com o menu do sistema.
     * Ele recebe o motivo do cancelamento e retorna uma mensagem formatada.
     */
    public String cancelar(String motivo) {
        cancelar();
        return "Consulta cancelada. Motivo: " + motivo;
    }

    @Override
    public String getData() {
        return this.data;
    }

    @Override
    public String getHorario() {
        return this.horario;
    }

    @Override
    public boolean isAgendada() {
        return "agendada".equals(this.status);
    }

    /**
     * Método auxiliar para compatibilidade com o restante do sistema.
     * Mantém o comportamento antigo de marcar a consulta como remarcada.
     */
    public void remarcar() {
        if ("realizada".equals(this.status)) {
            throw new IllegalStateException("Não é possível remarcar uma consulta já realizada.");
        }

        this.status = "remarcada";
    }

    /**
     * Regras de negócio para remarcação com nova data e horário.
     * - não permite remarcar uma consulta já realizada;
     * - a consulta deve voltar ao estado agendada após a nova data/horário.
     */
    public void remarcar(String novaData, String novoHorario) {
        if ("realizada".equals(this.status)) {
            throw new IllegalStateException("Não é possível remarcar uma consulta já realizada.");
        }

        agendar(novaData, novoHorario);
    }

    /**
     * Mantém o comportamento antigo do projeto, mas com validação.
     */
    public void realizar() {
        if (!isAgendada()) {
            throw new IllegalStateException("Só é possível realizar uma consulta agendada.");
        }
        this.status = "realizada";
    }

    /**
     * Sobrescreve o método exigido pela interface Exportavel.
     * Gera uma representação textual completa da consulta para exportação.
     */
    @Override
    public String exportarParaTexto() {
        return String.join(System.lineSeparator(),
                "Consulta",
                "Paciente: " + cpfPaciente,
                "Profissional: " + nomeProfissional,
                "Data: " + data,
                "Horário: " + horario,
                "Tipo: " + tipo,
                "Status: " + status);
    }

    public String exibirResumo() {
        return "Paciente(CPF): " + cpfPaciente + " | Prof: " + nomeProfissional
                + " | Data: " + data + " | Hora: " + horario
                + " | Tipo: " + tipo + " | Status: " + status;
    }
}
