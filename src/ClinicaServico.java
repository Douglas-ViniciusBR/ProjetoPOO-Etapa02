import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClinicaServico {
    //========================
    // ---- ARMAZENAMENTO ----
    //========================
    private List<Paciente> pacientes;
    private Map<String, Paciente> pacientesPorCpf;
    private List<Profissional> profissionais;
    private List<Consulta> consultas;
    private List<Atendimento> atendimentos;
    private List<Pagamento> pagamentos;
    private List<Double> multas;
    
    // Coleção unificada para armazenar todas as pessoas (Pacientes e Profissionais)
    private List<Pessoa> todasAsPessoas;

    public ClinicaServico() {
        this.pacientes = new ArrayList<>();
        this.pacientesPorCpf = new HashMap<>();
        this.profissionais = new ArrayList<>();
        this.consultas = new ArrayList<>();
        this.atendimentos = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
        this.multas = new ArrayList<>();
        this.todasAsPessoas = new ArrayList<>(); //[cite: 31]
    }

    // ========================================
    //               PACIENTES
    // ========================================

    public boolean cadastrarPaciente(Paciente paciente) {
        if (paciente == null) {
            System.out.println("Erro: paciente inválido.");
            return false;
        }
        String cpf = paciente.getCpf();
        if (cpf == null || cpf.isEmpty()) {
            System.out.println("Erro: CPF inválido.");
            return false;
        }
        if (pacientesPorCpf.containsKey(cpf)) {
            System.out.println("CPF ja cadastrado!");
            return false;
        }
        pacientes.add(paciente);
        pacientesPorCpf.put(cpf, paciente);
        todasAsPessoas.add(paciente); // Mantém a lista unificada sincronizada
        System.out.println("Paciente cadastrado com sucesso!");
        return true;
    }

    public Paciente buscarPacientePorCpf(String cpf) {
        cpf = normalizarCpf(cpf);
        if (cpf == null || cpf.isEmpty()) {
            return null;
        }
        return pacientesPorCpf.get(cpf);
    }

    public void listarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }
        for (Paciente p : pacientes) {
            p.exibirResumo();
        }
    }

    public boolean desativarPaciente(String cpf) {
        Paciente p = buscarPacientePorCpf(cpf);
        if (p == null) {
            System.out.println("Paciente nao encontrado.");
            return false;
        }
        p.desativar();
        System.out.println("Paciente desativado.");
        return true;
    }

    private String normalizarCpf(String cpf) {
        if (cpf == null) {
            return null;
        }
        return cpf.replaceAll("\\D", "");
    }

    // ========================================
    //              PROFISSIONAIS
    // ========================================

    public boolean cadastrarProfissional(Profissional prof) {
        if (prof == null) {
            System.out.println("Erro: profissional inválido.");
            return false;
        }
        profissionais.add(prof);
        todasAsPessoas.add(prof); // Mantém a lista unificada sincronizada[cite: 31]
        System.out.println("Profissional cadastrado!");
        return true;
    }

    public Profissional buscarProfissionalPorNome(String nome) {
        if (nome == null) return null;
        for (Profissional p : profissionais) {
            if (p.getNome().equalsIgnoreCase(nome.trim())) {
                return p;
            }
        }
        return null;
    }

    public Profissional buscarProfissionalPorCpf(String cpf) {
        if (cpf == null) return null;
        for (Profissional p : profissionais) {
            if (p.getCpf().equals(cpf.trim())) {
                return p;
            }
        }
        return null;
    }

    public boolean atualizarProfissional(String cpf, String registro, double valor, String[] dias, int totalDias) {
        Profissional prof = buscarProfissionalPorCpf(cpf);
        if (prof == null) {
            System.out.println("Profissional nao encontrado.");
            return false;
        }
        if (registro != null && !registro.trim().isEmpty()) {
            prof.setRegistroProfissional(registro);
        }
        if (valor >= 0) {
            prof.setValorConsulta(valor);
        }
        if (dias != null) {
            prof.setDias(dias, totalDias);
        }
        System.out.println("Profissional atualizado com sucesso.");
        return true;
    }

    public boolean existeProfissionalComEspecialidade(String especialidade) {
        if (especialidade == null) return false;
        for (Profissional p : profissionais) {
            if (p.getEspecialidade().equalsIgnoreCase(especialidade.trim())) {
                return true;
            }
        }
        return false;
    }

    public void listarProfissionais() {
        if (profissionais.isEmpty()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }
        for (Profissional p : profissionais) {
            p.exibirResumo();
        }
    }

    public void filtrarProfissionaisPorEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty()) {
            System.out.println("Especialidade invalida.");
            return;
        }
        boolean achou = false;
        for (Profissional p : profissionais) {
            if (p.getEspecialidade().equalsIgnoreCase(especialidade.trim())) {
                p.exibirResumo();
                achou = true;
            }
        }
        if (!achou) {
            System.out.println("Nenhum profissional com essa especialidade.");
        }
    }

    // ========================================
    //                CONSULTAS
    // ========================================

    public boolean agendarConsulta(Consulta consulta) {
        if (consulta == null) {
            System.out.println("Erro: consulta inválida.");
            return false;
        }
        consultas.add(consulta);
        System.out.println("Consulta agendada com sucesso!");
        return true;
    }

    public boolean cancelarConsulta(int indice, String motivo) {
        Consulta consulta = buscarConsultaPorIndice(indice);
        if (consulta == null) {
            System.out.println("Indice invalido.");
            return false;
        }
        if ("realizada".equals(consulta.status)) {
            System.out.println("Nao e possivel cancelar uma consulta ja realizada.");
            return false;
        }

        double valorConsulta = obterValorConsultaPorProfissional(consulta.nomeProfissional);
        double multa = calcularMultaCancelamento(consulta.data, valorConsulta);
        if (multa > 0) {
            registrarMulta(multa);
            System.out.println("Multa aplicada: R$" + Math.round(multa * 100.0) / 100.0);
        }

        consulta.cancelar();
        if (motivo != null && !motivo.trim().isEmpty()) {
            System.out.println("Consulta cancelada. Motivo: " + motivo);
        }
        return true;
    }

    public boolean remarcarConsulta(int indice, String novaData, String novoHorario) {
        Consulta consulta = buscarConsultaPorIndice(indice);
        if (consulta == null) {
            System.out.println("Indice invalido.");
            return false;
        }
        if ("realizada".equals(consulta.status)) {
            System.out.println("Nao e possivel remarcar uma consulta ja realizada.");
            return false;
        }

        Profissional prof = buscarProfissionalPorNome(consulta.nomeProfissional);
        if (prof == null) {
            System.out.println("Profissional associado a consulta nao encontrado.");
            return false;
        }

        String diaSemana = descobrirDiaSemana(novaData);
        if (!prof.atendeNoDia(diaSemana)) {
            System.out.println("Profissional nao atende nesse dia. Dias: " + prof.getDiasDisponiveisFormatados());
            return false;
        }

        if (temConflito(consulta.nomeProfissional, novaData, novoHorario, indice)) {
            System.out.println("Horario ocupado para esse profissional nessa data.");
            return false;
        }

        double multa = calcularMultaRemarcacao(consulta.data, prof.getValorConsulta());
        if (multa > 0) {
            registrarMulta(multa);
            System.out.println("Multa de remarcacao aplicada: R$" + Math.round(multa * 100.0) / 100.0);
        }

        consulta.remarcar(novaData, novoHorario);
        return true;
    }

    public Consulta buscarConsultaPorIndice(int indice) {
        if (indice < 0 || indice >= consultas.size()) {
            return null;
        }
        return consultas.get(indice);
    }

    public void listarConsultas() {
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta.");
            return;
        }
        for (int i = 0; i < consultas.size(); i++) {
            System.out.println("[" + i + "] " + consultas.get(i).exibirResumo());
        }
    }

    public void listarConsultasPorCpf(String cpf) {
        boolean achou = false;
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).cpfPaciente.equals(cpf)) {
                System.out.println("[" + i + "] " + consultas.get(i).exibirResumo());
                achou = true;
            }
        }
        if (!achou) {
            System.out.println("Nenhuma consulta encontrada.");
        }
    }

    // Verifica se existe conflito de horário para um profissional em uma data/horário.
    public boolean temConflito(String nomeProfissional, String data, String horario) {
        return temConflito(nomeProfissional, data, horario, -1);
    }

    public boolean temConflito(String nomeProfissional, String data, String horario, int ignorarIndice) {
        for (int i = 0; i < consultas.size(); i++) {
            if (i == ignorarIndice) {
                continue;
            }
            Consulta c = consultas.get(i);
            if (c.nomeProfissional.equals(nomeProfissional)
                    && c.data.equals(data)
                    && c.horario.equals(horario)
                    && c.status.equals("agendada")) {
                return true;
            }
        }
        return false;
    }

    // Sugere o próximo horário disponível (de hora em hora, 08h até 18h)
    public String sugerirHorario(String nomeProfissional, String data) {
        for (int h = 8; h <= 18; h++) {
            String teste = (h < 10 ? "0" + h : "" + h) + ":00";
            if (!temConflito(nomeProfissional, data, teste)) {
                return teste;
            }
        }
        return "";
    }

    // Descobre o dia da semana usando a fórmula de Zeller.
    public String descobrirDiaSemana(String data) {
        int dia = Integer.parseInt(data.substring(0, 2));
        int mes = Integer.parseInt(data.substring(3, 5));
        int ano = Integer.parseInt(data.substring(6, 10));

        if (mes < 3) {
            mes = mes + 12;
            ano = ano - 1;
        }

        int k = ano % 100;
        int j = ano / 100;
        int resultado = (dia + (13 * (mes + 1)) / 5 + k + k/4 + j/4 - 2*j) % 7;
        if (resultado < 0) resultado = resultado + 7;

        String[] nomes = {"sabado", "domingo", "segunda", "terca", "quarta", "quinta", "sexta"};
        return nomes[resultado];
    }

    private double obterValorConsultaPorProfissional(String nomeProfissional) {
        Profissional prof = buscarProfissionalPorNome(nomeProfissional);
        if (prof == null) {
            return 0;
        }
        return prof.getValorConsulta();
    }

    private long diasAteConsulta(String dataConsulta) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate hoje = LocalDate.now();
        LocalDate data = LocalDate.parse(dataConsulta, formatter);
        return ChronoUnit.DAYS.between(hoje, data);
    }

    private double calcularMultaCancelamento(String dataConsulta, double valorConsulta) {
        long dias = diasAteConsulta(dataConsulta);
        if (dias < 0) {
            return 0;
        }
        if (dias <= 1) {
            return valorConsulta * 1.0; // multa total para cancelamento em tempo muito curto
        }
        if (dias <= 3) {
            return valorConsulta * 0.5; // multa parcial para cancelamento de curto prazo
        }
        return 0;
    }

    private double calcularMultaRemarcacao(String dataConsulta, double valorConsulta) {
        long dias = diasAteConsulta(dataConsulta);
        if (dias < 0) {
            return 0;
        }
        if (dias <= 3) {
            return valorConsulta * 0.3; // remarcação de curto prazo gera penalidade
        }
        return 0;
    }

    // ========================================
    //              ATENDIMENTOS
    // ========================================

    public boolean registrarAtendimento(Atendimento atendimento) {
        if (atendimento == null) {
            System.out.println("Erro: atendimento inválido.");
            return false;
        }
        atendimentos.add(atendimento);
        System.out.println("Atendimento registrado com sucesso!");
        return true;
    }

    public Atendimento buscarAtendimentoPorIndice(int indice) {
        if (indice < 0 || indice >= atendimentos.size()) {
            return null;
        }
        return atendimentos.get(indice);
    }

    public void listarAtendimentos() {
        if (atendimentos.isEmpty()) {
            System.out.println("Nenhum atendimento registrado.");
            return;
        }
        for (Atendimento a : atendimentos) {
            System.out.println(a.toString());
        }
    }

    // ========================================
    //               PAGAMENTOS
    // ========================================

    public boolean registrarPagamento(Pagamento pagamento) {
        if (pagamento == null) {
            System.out.println("Erro: pagamento inválido.");
            return false;
        }
        pagamentos.add(pagamento);
        System.out.println("Pagamento registrado!");
        return true;
    }

    public void listarPagamentos() {
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return;
        }
        for (Pagamento p : pagamentos) {
            System.out.println(p.exibirResumo());
        }
    }

    public boolean registrarMulta(double valor) {
        if (valor <= 0) {
            return false;
        }
        multas.add(valor);
        return true;
    }

    // ========================================
    //         RELATÓRIOS ANALÍTICOS
    // ========================================

    // Exibe um relatório contando quantos profissionais existem no sistema usando o operador instanceof.
    public void exibirContagemPorEspecialidade() {
        int qtdFisioterapeutas = 0;
        int qtdPsicologos = 0;
        int qtdNutricionistas = 0;
        int qtdClinicosGerais = 0;

        for (Pessoa p : todasAsPessoas) {
            if (p instanceof Profissional) {
                if (p instanceof Fisioterapeuta) {
                    qtdFisioterapeutas++;
                } else if (p instanceof Psicologo) {
                    qtdPsicologos++;
                } else if (p instanceof Nutricionista) {
                    qtdNutricionistas++;
                } else if (p instanceof ClinicoGeral) {
                    qtdClinicosGerais++;
                }
            }
        }

        System.out.println("\n=== QUANTIDADE DE PROFISSIONAIS POR ESPECIALIDADE (ANÁLISE INSTANCEOF) ===");
        System.out.println("Fisioterapia:   " + qtdFisioterapeutas);
        System.out.println("Psicologia:     " + qtdPsicologos);
        System.out.println("Nutrição:       " + qtdNutricionistas);
        System.out.println("Clínica Geral:  " + qtdClinicosGerais);
    }

    // Lista detalhadamente os profissionais de uma subclasse específica (via Reflection).
    public void listarProfissionaisPorSubclasse(Class<? extends Profissional> tipoAlvo) {
        System.out.println("\n=== LISTAGEM FILTRADA POR CLASSE REAL: " + tipoAlvo.getSimpleName() + " ===");
        boolean encontrou = false;
        
        for (Pessoa p : todasAsPessoas) {
            if (tipoAlvo.isInstance(p)) {
                p.exibirResumo(); 
                encontrou = true;
            }
        }
        
        if (!encontrou) {
            System.out.println("Nenhum profissional do tipo " + tipoAlvo.getSimpleName() + " registrado no sistema.");
        }
    }

    // ========================================
    //            RELATÓRIOS GERAIS
    // ========================================

    public void gerarRelatorioGeral() {
        System.out.println("\n=== RELATORIO GERAL ===");
        for (int i = 0; i < consultas.size(); i++) {
            System.out.println(consultas.get(i).exibirResumo());
            String diag = buscarDiagnosticoPorIndiceConsulta(i);
            if (!diag.isEmpty()) {
                System.out.println("  Diagnostico: " + diag);
            }
            System.out.println("---");
        }
    }

    public void gerarRelatorioPorProfissional(String nomeProfissional) {
        System.out.println("\n=== RELATORIO - " + nomeProfissional + " ===");
        boolean achou = false;
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).nomeProfissional.equals(nomeProfissional)) {
                System.out.println(consultas.get(i).exibirResumo());
                String diag = buscarDiagnosticoPorIndiceConsulta(i);
                if (!diag.isEmpty()) {
                    System.out.println("  Diagnostico: " + diag);
                }
                System.out.println("---");
                achou = true;
            }
        }
        if (!achou) {
            System.out.println("Nenhuma consulta encontrada para esse profissional.");
        }
    }

    public void gerarRelatorioPorPeriodo(String dataInicio, String dataFim) {
        System.out.println("\n=== RELATORIO - " + dataInicio + " a " + dataFim + " ===");
        for (int i = 0; i < consultas.size(); i++) {
            if (estaNoIntervalo(consultas.get(i).data, dataInicio, dataFim)) {
                System.out.println(consultas.get(i).exibirResumo());
                String diag = buscarDiagnosticoPorIndiceConsulta(i);
                if (!diag.isEmpty()) {
                    System.out.println("  Diagnostico: " + diag);
                }
                System.out.println("---");
            }
        }
    }

    public void gerarResumoFinanceiro() {
        int realizadas = 0;
        int canceladas = 0;
        double totalFaturado = 0;
        double totalEmMultas = 0;

        for (Consulta c : consultas) {
            if (c.status.equals("realizada")) realizadas++;
            if (c.status.equals("cancelada")) canceladas++;
        }

        for (Pagamento p : pagamentos) {
            totalFaturado += p.valorFinal;
        }

        for (Double m : multas) {
            totalEmMultas += m;
        }

        System.out.println("\n=== RESUMO FINANCEIRO ===");
        System.out.println("Atendimentos realizados: " + realizadas);
        System.out.println("Total faturado: R$" + Math.round(totalFaturado * 100.0) / 100.0);
        System.out.println("Cancelamentos: " + canceladas);
        System.out.println("Total em multas: R$" + Math.round(totalEmMultas * 100.0) / 100.0);
    }

    // ========================================
    //      MÉTODOS AUXILIARES PRIVADOS
    // ========================================

    private String buscarDiagnosticoPorIndiceConsulta(int indiceConsulta) {
        for (Atendimento a : atendimentos) {
            if (a.indiceConsulta == indiceConsulta) {
                return a.getDiagnostico();
            }
        }
        return "";
    }

    private boolean estaNoIntervalo(String data, String inicio, String fim) {
        int valorData = converterDataParaNumero(data);
        int valorInicio = converterDataParaNumero(inicio);
        int valorFim = converterDataParaNumero(fim);
        return valorData >= valorInicio && valorData <= valorFim;
    }

    private int converterDataParaNumero(String data) {
        int dia = Integer.parseInt(data.substring(0, 2));
        int mes = Integer.parseInt(data.substring(3, 5));
        int ano = Integer.parseInt(data.substring(6, 10));
        return ano * 10000 + mes * 100 + dia;
    }

    // =============================================
    // GETTERS (para acesso aos dados se necessário)
    // =============================================

    public int getQuantidadePacientes() { return pacientes.size(); }
    public int getQuantidadeProfissionais() { return profissionais.size(); }
    public int getQuantidadeConsultas() { return consultas.size(); }
    public int getQuantidadeAtendimentos() { return atendimentos.size(); }
    public List<Consulta> getConsultas() { return consultas; }
    public List<Paciente> getPacientes() { return pacientes; }
    public List<Profissional> getProfissionais() { return profissionais; }
    public List<Pessoa> getTodasAsPessoas() { return todasAsPessoas; }
}