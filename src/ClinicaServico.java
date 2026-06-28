import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClinicaServico {
    // Armazenamento
    private List<Paciente> pacientes;
    private Map<String, Paciente> pacientesPorCpf;
    private List<Profissional> profissionais;
    private List<Consulta> consultas;
    private List<Atendimento> atendimentos;
    private List<Pagamento> pagamentos;
    private List<Double> multas;
    
    // Lista de todas as pessoas (pacientes e profissionais)
    private List<Pessoa> todasAsPessoas;

    public ClinicaServico() {
        this.pacientes = new ArrayList<>();
        this.pacientesPorCpf = new HashMap<>();
        this.profissionais = new ArrayList<>();
        this.consultas = new ArrayList<>();
        this.atendimentos = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
        this.multas = new ArrayList<>();
        this.todasAsPessoas = new ArrayList<>();
    }

    // Pacientes

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

    // Profissionais

    public boolean cadastrarProfissional(Profissional prof) {
        if (prof == null) {
            System.out.println("Erro: profissional inválido.");
            return false;
        }
        profissionais.add(prof);
        todasAsPessoas.add(prof); // mantém lista unificada
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

    // Consultas

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

    // Atendimentos

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

    // Pagamentos

    public boolean registrarPagamento(Pagamento pagamento) {
        if (pagamento == null) {
            System.out.println("Erro: pagamento inválido.");
            return false;
        }
        if (pagamento.tipoPagamento == null || pagamento.tipoPagamento.trim().isEmpty()) {
            System.out.println("Erro: tipo de pagamento inválido.");
            return false;
        }

        Consulta consulta = buscarConsultaPorIndice(pagamento.indiceConsulta);
        if (consulta == null) {
            System.out.println("Indice de consulta inválido.");
            return false;
        }
        if (!"realizada".equals(consulta.status)) {
            System.out.println("Pagamento permitido apenas para consultas realizadas.");
            return false;
        }
        if (!consultaTemAtendimento(pagamento.indiceConsulta)) {
            System.out.println("Nenhum atendimento registrado para esta consulta.");
            return false;
        }

        Paciente paciente = buscarPacientePorCpf(consulta.cpfPaciente);
        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return false;
        }
        if (!paciente.isAtivo()) {
            System.out.println("Paciente inativo. Pagamento não permitido.");
            return false;
        }
        if (pagamentoJaRegistradoParaConsulta(pagamento.indiceConsulta)) {
            System.out.println("Já existe um pagamento registrado para esta consulta.");
            return false;
        }

        String tipo = pagamento.tipoPagamento.trim().toLowerCase();
        if ("cartao".equals(tipo)) {
            if (pagamento.parcelas < 1 || pagamento.parcelas > 3) {
                System.out.println("Parcelas inválidas. Informe entre 1 e 3.");
                return false;
            }
        } else if ("convenio".equals(tipo)) {
            String convenioNome = paciente.getConvenioNome();
            if (convenioNome == null || convenioNome.trim().isEmpty()) {
                System.out.println("Paciente não possui convênio cadastrado.");
                return false;
            }
            if (!convenioCobreEspecialidade(convenioNome, consulta.tipo)) {
                System.out.println("Convênio não cobre a especialidade da consulta.");
                return false;
            }
            if (!(pagamento instanceof PagamentoConvenio)) {
                System.out.println("Erro interno: tipo de pagamento convênio inválido.");
                return false;
            }
            PagamentoConvenio convenio = (PagamentoConvenio) pagamento;
            if (convenio.getPercentualCobertura() < 0 || convenio.getPercentualCobertura() > 100) {
                System.out.println("Percentual de cobertura inválido.");
                return false;
            }
        } else if (!"dinheiro".equals(tipo)) {
            System.out.println("Tipo de pagamento inválido.");
            return false;
        }

        double baseValor = pagamento.valorFinal;
        pagamento.valorFinal = pagamento.calcularValorFinal(baseValor);
        pagamentos.add(pagamento);
        System.out.println("Pagamento registrado!");
        return true;
    }

    private boolean pagamentoJaRegistradoParaConsulta(int indiceConsulta) {
        for (Pagamento p : pagamentos) {
            if (p.indiceConsulta == indiceConsulta) {
                return true;
            }
        }
        return false;
    }

    private boolean consultaTemAtendimento(int indiceConsulta) {
        for (Atendimento a : atendimentos) {
            if (a.indiceConsulta == indiceConsulta) {
                return true;
            }
        }
        return false;
    }

    public boolean convenioCobreEspecialidade(String convenioNome, String especialidade) {
        if (convenioNome == null || convenioNome.trim().isEmpty() || especialidade == null || especialidade.trim().isEmpty()) {
            return false;
        }
        String convenio = convenioNome.trim().toLowerCase();
        String esp = especialidade.trim().toLowerCase();

        if (convenio.contains("unimed") || convenio.contains("saude") || convenio.contains("universal") || convenio.contains("bradesco")) {
            return true;
        }
        if (convenio.contains("amil")) {
            return esp.equals("clinica geral") || esp.equals("fisioterapia") || esp.equals("psicologia");
        }
        if (convenio.contains("psico")) {
            return esp.equals("psicologia");
        }
        if (convenio.contains("fisio")) {
            return esp.equals("fisioterapia");
        }
        if (convenio.contains("nutri")) {
            return esp.equals("nutricao") || esp.equals("nutricionista");
        }
        if (convenio.contains("clinica")) {
            return esp.equals("clinica geral");
        }
        return false;
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

    // Relátorios e análises

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

        System.out.println("\n=== QUANTIDADE DE PROFISSIONAIS POR ESPECIALIDADE (ANALISE INSTANCEOF) ===");
        System.out.println("Fisioterapia:   " + qtdFisioterapeutas);
        System.out.println("Psicologia:     " + qtdPsicologos);
        System.out.println("Nutrição:       " + qtdNutricionistas);
        System.out.println("Clínica Geral:  " + qtdClinicosGerais);
    }

    // Lista profissionais por classe
    public void listarProfissionaisPorSubclasse(Class<? extends Profissional> tipoAlvo) {
        System.out.println("\n=== LISTAGEM POR CLASSE: " + tipoAlvo.getSimpleName() + " ===");
        boolean encontrou = false;
        for (Pessoa p : todasAsPessoas) {
            if (tipoAlvo.isInstance(p)) {
                p.exibirResumo();
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum profissional desse tipo registrado.");
        }
    }

    // RELATORIOS GERAIS

    public void gerarRelatorioUnificadoCadastros() {
        System.out.println("\n=== RELATORIO UNIFICADO DE CADASTROS ===");
        int qtdPacientes = 0;
        int qtdProfissionais = 0;

        for (Pessoa pessoa : todasAsPessoas) {
            if (pessoa instanceof Paciente) {
                qtdPacientes++;
                System.out.println("[Paciente]");
            } else if (pessoa instanceof Profissional) {
                qtdProfissionais++;
                System.out.println("[Profissional]");
            } else {
                System.out.println("[Pessoa]");
            }

            pessoa.exibirResumo();

            if (pessoa instanceof Paciente) {
                Paciente paciente = (Paciente) pessoa;
                System.out.println("  Status: " + (paciente.isAtivo() ? "ativo" : "inativo"));
            } else if (pessoa instanceof Profissional) {
                Profissional profissional = (Profissional) pessoa;
                System.out.println("  Especialidade: " + profissional.getEspecialidade());
                System.out.println("  Valor da consulta: " + profissional.getValorConsulta());
            }

            System.out.println("---");
        }

        System.out.println("Totais: Pacientes = " + qtdPacientes + " | Profissionais = " + qtdProfissionais);
    }

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

    // MÉTODOS AUXILIARES PRIVADOS

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

    // GETTERS (para acesso aos dados se necessário)

    public int getQuantidadePacientes() { return pacientes.size(); }
    public int getQuantidadeProfissionais() { return profissionais.size(); }
    public int getQuantidadeConsultas() { return consultas.size(); }
    public int getQuantidadeAtendimentos() { return atendimentos.size(); }
    public List<Consulta> getConsultas() { return consultas; }
    public List<Paciente> getPacientes() { return pacientes; }
    public List<Profissional> getProfissionais() { return profissionais; }
    public List<Pessoa> getTodasAsPessoas() { return todasAsPessoas; }

    // Relatórios de cancelamentos e multas
    public void gerarRelatorioCancelamentos() {
        System.out.println("\n=== RELATORIO DE CANCELAMENTOS ===");
        boolean encontrou = false;
        for (int i = 0; i < consultas.size(); i++) {
            if ("cancelada".equals(consultas.get(i).status)) {
                System.out.println("[" + i + "] " + consultas.get(i).exibirResumo());
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Nenhum cancelamento registrado.");
    }

    public void gerarRelatorioMultas() {
        System.out.println("\n=== RELATORIO DE MULTAS ===");
        if (multas.isEmpty()) {
            System.out.println("Nenhuma multa registrada.");
            return;
        }
        double total = 0;
        for (Double m : multas) {
            System.out.println("Multa: R$" + Math.round(m * 100.0) / 100.0);
            total += m;
        }
        System.out.println("---\nTotal em multas: R$" + Math.round(total * 100.0) / 100.0);
    }

    // Exportação de dados (apresentação simples na saída)
    public void exportarConsultas() {
        System.out.println("\n=== EXPORTACAO: CONSULTAS ===");
        if (consultas.isEmpty()) { System.out.println("Nenhuma consulta para exportar."); return; }
        for (Consulta c : consultas) {
            if (c instanceof Exportavel) System.out.println(((Exportavel) c).exportarParaTexto());
            else System.out.println(c.exibirResumo());
            System.out.println("----");
        }
    }

    public void exportarAtendimentos() {
        System.out.println("\n=== EXPORTACAO: ATENDIMENTOS ===");
        if (atendimentos.isEmpty()) { System.out.println("Nenhum atendimento para exportar."); return; }
        for (Atendimento a : atendimentos) {
            System.out.println(a.exibirResumo());
            System.out.println("----");
        }
    }

    public void exportarPagamentos() {
        System.out.println("\n=== EXPORTACAO: PAGAMENTOS ===");
        if (pagamentos.isEmpty()) { System.out.println("Nenhum pagamento para exportar."); return; }
        for (Pagamento p : pagamentos) {
            System.out.println(p.exibirResumo());
            System.out.println("----");
        }
    }

    public void exportarPacientes() {
        System.out.println("\n=== EXPORTACAO: PACIENTES ===");
        if (pacientes.isEmpty()) { System.out.println("Nenhum paciente para exportar."); return; }
        for (Paciente p : pacientes) {
            System.out.println(p.toString());
            System.out.println("----");
        }
    }

    public void exportarProfissionais() {
        System.out.println("\n=== EXPORTACAO: PROFISSIONAIS ===");
        if (profissionais.isEmpty()) { System.out.println("Nenhum profissional para exportar."); return; }
        for (Profissional p : profissionais) {
            System.out.println(p.toString());
            System.out.println("----");
        }
    }

    public void exportarTodasAsPessoas() {
        System.out.println("\n=== EXPORTACAO: TODAS AS PESSOAS ===");
        if (todasAsPessoas.isEmpty()) { System.out.println("Nenhuma pessoa registrada."); return; }
        for (Pessoa p : todasAsPessoas) {
            System.out.println(p.toString());
            System.out.println("----");
        }
    }
}