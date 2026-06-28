import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static ClinicaServico servico = new ClinicaServico();

    public static void main(String[] args) {
        int opcao = -1;
        while (opcao != 0) {
            try {
                System.out.println("\n=== CLINICA VIDAPLENA ===");
                System.out.println("1 - Pacientes");
                System.out.println("2 - Profissionais");
                System.out.println("3 - Consultas");
                System.out.println("4 - Atendimentos");
                System.out.println("5 - Pagamentos");
                System.out.println("6 - Relatorios");
                System.out.println("0 - Sair");
                opcao = lerInt("Escolha: ");

                switch (opcao) {
                    case 1: menuPacientes(); break;
                    case 2: menuProfissionais(); break;
                    case 3: menuConsultas(); break;
                    case 4: menuAtendimentos(); break;
                    case 5: menuPagamentos(); break;
                    case 6: menuRelatorios(); break;
                    case 0: break;
                    default: System.out.println("Opcao invalida!"); break;
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        System.out.println("Sistema encerrado.");
        sc.close();
    }

    // ========== PACIENTES ==========

    public static void menuPacientes() {
        int op = -1;
        while (op != 0) {
            try {
                System.out.println("\n--- PACIENTES ---");
                System.out.println("1 - Cadastrar");
                System.out.println("2 - Buscar por CPF");
                System.out.println("3 - Listar todos");
                System.out.println("4 - Desativar");
                System.out.println("5 - Complementar cadastro");
                System.out.println("0 - Voltar");
                op = lerInt("Opcao: ");

                switch (op) {
                    case 1: 
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("CPF: ");
                        String cpf = sc.nextLine();
                        System.out.print("Tipo (1-Minimo / 2-Com idade e tel / 3-Completo): ");
                        int tipo = lerInt("");
                        
                        Paciente paciente = criarPaciente(nome, cpf, tipo);
                        servico.cadastrarPaciente(paciente);
                        break;
                    case 2:
                        System.out.print("CPF: ");
                        cpf = sc.nextLine();
                        paciente = servico.buscarPacientePorCpf(cpf);
                        if (paciente != null) {
                            paciente.exibirResumo();
                        } else {
                            System.out.println("Paciente nao encontrado.");
                        }
                        break;
                    case 3:
                        servico.listarPacientes();
                        break;
                    case 4:
                        System.out.print("CPF: ");
                        cpf = sc.nextLine();
                        servico.desativarPaciente(cpf);
                        break;
                    case 5:
                        menuComplementarPaciente();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static Paciente criarPaciente(String nome, String cpf, int tipo) {
        if (tipo == 1) {
            return new Paciente(nome, cpf);
        } else if (tipo == 2) {
            int idade = lerInt("Idade: ");
            System.out.print("Telefone: ");
            String tel = sc.nextLine();
            return new Paciente(nome, cpf, idade, tel);
        } else {
            int idade = lerInt("Idade: ");
            System.out.print("Telefone: ");
            String tel = sc.nextLine();
            System.out.print("Convenio: ");
            String conv = sc.nextLine();
            return new Paciente(nome, cpf, idade, tel, conv);
        }
    }

    // ========== PROFISSIONAIS ==========

    public static void menuProfissionais() {
        int op = -1;
        while (op != 0) {
            try {
                System.out.println("\n--- PROFISSIONAIS ---");
                System.out.println("1 - Cadastrar");
                System.out.println("2 - Atualizar profissional");
                System.out.println("3 - Listar todos");
                System.out.println("4 - Filtrar por especialidade");
                System.out.println("0 - Voltar");
                op = lerInt("Opcao: ");

                switch (op) {
                    case 1:
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("CPF: ");
                        String cpf = sc.nextLine();
                        System.out.print("Especialidade (clinica geral/fisioterapia/psicologia/nutricao): ");
                        String esp = sc.nextLine();

                        if (!Profissional.especialidadeValida(esp)) {
                            System.out.println("Especialidade invalida!");
                            break;
                        }

                        System.out.print("Tipo (1-Minimo / 2-Com registro e valor / 3-Completo): ");
                        int tipo = lerInt("");
                        Profissional prof = criarProfissional(nome, cpf, esp, tipo);
                        servico.cadastrarProfissional(prof);
                        break;
                    case 2:
                        menuAtualizarProfissional();
                        break;
                    case 3:
                        servico.listarProfissionais();
                        break;
                    case 4:
                        System.out.print("Especialidade: ");
                        esp = sc.nextLine();
                        servico.filtrarProfissionaisPorEspecialidade(esp);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static Profissional criarProfissional(String nome, String cpf, String esp, int tipo) {
        String registro = "";
        double valor = 0;
        String[] dias = new String[7];
        int totalDias = 0;

        if (tipo == 1) {
            // Apenas nome e CPF
        } else if (tipo == 2) {
            System.out.print("Registro: ");
            registro = sc.nextLine();
            valor = lerDouble("Valor consulta: ");
        } else {
            System.out.print("Registro: ");
            registro = sc.nextLine();
            valor = lerDouble("Valor consulta: ");
            totalDias = lerInt("Quantos dias atende? ");
            for (int i = 0; i < totalDias && i < dias.length; i++) {
                System.out.print("Dia " + (i + 1) + ": ");
                dias[i] = sc.nextLine();
            }
        }

        if (esp.equals("clinica geral")) {
            if (tipo == 1) return new ClinicoGeral(nome, cpf);
            if (tipo == 2) return new ClinicoGeral(nome, cpf, registro, valor);
            return new ClinicoGeral(nome, cpf, registro, valor, dias, totalDias);
        }
        if (esp.equals("fisioterapia")) {
            if (tipo == 1) return new Fisioterapeuta(nome, cpf);
            if (tipo == 2) return new Fisioterapeuta(nome, cpf, registro, valor);
            return new Fisioterapeuta(nome, cpf, registro, valor, dias, totalDias);
        }
        if (esp.equals("psicologia")) {
            if (tipo == 1) return new Psicologo(nome, cpf);
            if (tipo == 2) return new Psicologo(nome, cpf, registro, valor);
            return new Psicologo(nome, cpf, registro, valor, dias, totalDias);
        }
        if (esp.equals("nutricao")) {
            if (tipo == 1) return new Nutricionista(nome, cpf);
            if (tipo == 2) return new Nutricionista(nome, cpf, registro, valor);
            return new Nutricionista(nome, cpf, registro, valor, dias, totalDias);
        }
        return new ClinicoGeral(nome, cpf);
    }

    public static void menuAtualizarProfissional() {
        System.out.print("CPF do profissional: ");
        String cpf = sc.nextLine();
        Profissional prof = servico.buscarProfissionalPorCpf(cpf);
        if (prof == null) {
            System.out.println("Profissional nao encontrado.");
            return;
        }

        System.out.println("Atualizando: " + prof.getNome() + " (" + prof.getEspecialidade() + ")");
        System.out.println("Registro atual: " + prof.getRegistroProfissional());
        System.out.println("Valor atual: " + prof.getValorConsulta());
        System.out.println("Dias atuais: " + prof.getDiasDisponiveisFormatados());

        System.out.print("Novo registro (enter para manter): ");
        String registro = sc.nextLine();
        double valor = -1;
        System.out.print("Novo valor (deixe vazio para manter): ");
        String valorTexto = sc.nextLine();
        if (!valorTexto.trim().isEmpty()) {
            try {
                valor = Double.parseDouble(valorTexto);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Mantendo valor atual.");
                valor = prof.getValorConsulta();
            }
        } else {
            valor = prof.getValorConsulta();
        }

        System.out.print("Definir dias de atendimento agora? (1-Sim / 2-Nao): ");
        int defineDias = lerInt("");
        String[] dias = null;
        int totalDias = 0;
        if (defineDias == 1) {
            totalDias = lerInt("Quantos dias atende? ");
            dias = new String[totalDias];
            for (int i = 0; i < totalDias && i < dias.length; i++) {
                System.out.print("Dia " + (i + 1) + ": ");
                dias[i] = sc.nextLine();
            }
        }

        if (servico.atualizarProfissional(cpf,
                registro.trim().isEmpty() ? null : registro,
                valor,
                dias,
                totalDias)) {
            System.out.println("Profissional atualizado com sucesso.");
        }
    }

    // ========== CONSULTAS ==========

    public static void menuConsultas() {
        int op = -1;
        while (op != 0) {
            try {
                System.out.println("\n--- CONSULTAS ---");
                System.out.println("1 - Agendar com profissional");
                System.out.println("2 - Listar todas");
                System.out.println("3 - Buscar por CPF");
                System.out.println("4 - Cancelar consulta");
                System.out.println("5 - Remarcar consulta");
                System.out.println("0 - Voltar");
                op = lerInt("Opcao: ");

                switch (op) {
                    case 1:
                        System.out.print("CPF do paciente: ");
                        String cpf = sc.nextLine();
                        Paciente paciente = servico.buscarPacientePorCpf(cpf);
                        if (paciente == null) {
                            System.out.println("Paciente nao encontrado.");
                            break;
                        }
                        if (!paciente.isAtivo()) {
                            System.out.println("Paciente inativo. Nao e possivel agendar.");
                            break;
                        }

                        System.out.println("1 - Agendar por profissional");
                        System.out.println("2 - Agendar por especialidade");
                        int modo = lerInt("Opcao: ");
                        Profissional prof = null;
                        String nomeProf = "";
                        if (modo == 2) {
                            System.out.print("Especialidade: ");
                            String esp = sc.nextLine();
                            if (!Profissional.especialidadeValida(esp)) {
                                System.out.println("Especialidade invalida.");
                                break;
                            }
                            System.out.println("Profissionais disponiveis para " + esp + ":");
                            servico.filtrarProfissionaisPorEspecialidade(esp);
                            System.out.print("Nome do profissional escolhido: ");
                            nomeProf = sc.nextLine();
                            prof = servico.buscarProfissionalPorNome(nomeProf);
                        } else {
                            System.out.print("Nome do profissional: ");
                            nomeProf = sc.nextLine();
                            prof = servico.buscarProfissionalPorNome(nomeProf);
                        }

                        if (prof == null) {
                            System.out.println("Profissional nao encontrado.");
                            break;
                        }
                        if (prof.getValorConsulta() == 0) {
                            System.out.println("Profissional sem valor definido. Nao pode agendar.");
                            break;
                        }

                        System.out.print("Data (DD/MM/AAAA): ");
                        String data = sc.nextLine();
                        System.out.print("Horario (HH:MM): ");
                        String horario = sc.nextLine();

                        String diaSemana = servico.descobrirDiaSemana(data);
                        if (!prof.atendeNoDia(diaSemana)) {
                            System.out.println("Profissional nao atende nesse dia. Dias: " + prof.getDiasDisponiveisFormatados());
                            break;
                        }

                        if (servico.temConflito(nomeProf, data, horario)) {
                            System.out.println("Horario ocupado!");
                            String sugestao = servico.sugerirHorario(nomeProf, data);
                            if (!sugestao.isEmpty()) {
                                System.out.println("Sugestao: " + sugestao);
                                int aceita = lerInt("Aceita? (1-Sim / 2-Nao): ");
                                if (aceita == 1) {
                                    horario = sugestao;
                                } else {
                                    break;
                                }
                            } else {
                                System.out.println("Nenhum horario disponivel nesse dia.");
                                break;
                            }
                        }

                        String tipoConsulta = prof.getEspecialidade();
                        Consulta consulta = new Consulta(cpf, nomeProf, data, horario, tipoConsulta);
                        servico.agendarConsulta(consulta);
                        break;
                    case 2:
                        servico.listarConsultas();
                        break;
                    case 3:
                        System.out.print("CPF: ");
                        cpf = sc.nextLine();
                        servico.listarConsultasPorCpf(cpf);
                        break;
                    case 4:
                        servico.listarConsultas();
                        int idxCancelar = lerInt("Indice da consulta para cancelar: ");
                        Consulta consultaCancelar = servico.buscarConsultaPorIndice(idxCancelar);
                        if (consultaCancelar == null) {
                            System.out.println("Indice invalido.");
                            break;
                        }
                        System.out.print("Motivo do cancelamento: ");
                        String motivo = sc.nextLine();
                        if (servico.cancelarConsulta(idxCancelar, motivo)) {
                            System.out.println("Consulta cancelada com sucesso.");
                        }
                        break;
                    case 5:
                        servico.listarConsultas();
                        int idxRemarcar = lerInt("Indice da consulta para remarcar: ");
                        Consulta consultaRemarcar = servico.buscarConsultaPorIndice(idxRemarcar);
                        if (consultaRemarcar == null) {
                            System.out.println("Indice invalido.");
                            break;
                        }
                        System.out.print("Nova data (DD/MM/AAAA): ");
                        String novaData = sc.nextLine();
                        System.out.print("Novo horario (HH:MM): ");
                        String novoHorario = sc.nextLine();
                        if (servico.remarcarConsulta(idxRemarcar, novaData, novoHorario)) {
                            System.out.println("Consulta remarcada com sucesso.");
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    // ========== ATENDIMENTOS ==========

    public static void menuAtendimentos() {
        int op = -1;
        while (op != 0) {
            try {
                System.out.println("\n--- ATENDIMENTOS ---");
                System.out.println("1 - Registrar atendimento");
                System.out.println("2 - Listar atendimentos");
                System.out.println("0 - Voltar");
                op = lerInt("Opcao: ");

                switch (op) {
                    case 1:
                        int idxConsulta = lerInt("Indice da consulta: ");
                        Consulta consulta = servico.buscarConsultaPorIndice(idxConsulta);
                        if (consulta == null) {
                            System.out.println("Indice invalido.");
                            break;
                        }
                        if (!consulta.status.equals("agendada")) {
                            System.out.println("So pode registrar atendimento em consulta agendada.");
                            break;
                        }

                        System.out.print("Observacoes: ");
                        String obs = sc.nextLine();
                        int tipo = lerInt("Tipo (1-So obs / 2-Com diagnostico / 3-Completo): ");

                        Atendimento atendimento;
                        if (tipo == 1) {
                            atendimento = new Atendimento(idxConsulta, obs);
                        } else if (tipo == 2) {
                            System.out.print("Diagnostico: ");
                            String diag = sc.nextLine();
                            atendimento = new Atendimento(idxConsulta, obs, diag);
                        } else {
                            System.out.print("Diagnostico: ");
                            String diag = sc.nextLine();
                            System.out.print("Quantos procedimentos? ");
                            int qtd = lerInt("");
                            String[] procs = new String[qtd];
                            for (int i = 0; i < qtd; i++) {
                                System.out.print("Proc " + (i + 1) + ": ");
                                procs[i] = sc.nextLine();
                            }
                            atendimento = new Atendimento(idxConsulta, obs, diag, procs, qtd);
                        }

                        if (servico.registrarAtendimento(atendimento)) {
                            consulta.realizar();
                            System.out.println("Atendimento registrado.");
                            System.out.println(atendimento.exibirResumo());
                        }
                        break;
                    case 2:
                        servico.listarAtendimentos();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    // ========== PAGAMENTOS ==========

    public static void menuPagamentos() {
        int op = -1;
        while (op != 0) {
            try {
                System.out.println("\n--- PAGAMENTOS ---");
                System.out.println("1 - Registrar pagamento");
                System.out.println("2 - Listar pagamentos");
                System.out.println("0 - Voltar");
                op = lerInt("Opcao: ");

                switch (op) {
                    case 1:
                        int idxConsulta = lerInt("Indice da consulta: ");
                        Consulta consulta = servico.buscarConsultaPorIndice(idxConsulta);
                        if (consulta == null) {
                            System.out.println("Indice invalido.");
                            break;
                        }

                        double valor = lerDouble("Valor: ");
                        System.out.print("Tipo (dinheiro/cartao/convenio): ");
                        String tipoPag = sc.nextLine().trim().toLowerCase();

                        Pagamento pagamento;
                        if ("cartao".equals(tipoPag)) {
                            int parc = lerInt("Parcelas (1 a 3): ");
                            if (parc < 1 || parc > 3) {
                                System.out.println("Parcelas inválidas. Informe entre 1 e 3.");
                                break;
                            }
                            pagamento = new PagamentoCartao(idxConsulta, valor, tipoPag, parc);
                        } else if ("convenio".equals(tipoPag)) {
                            double cobertura = lerDouble("Percentual de cobertura do convenio (%): ");
                            pagamento = new PagamentoConvenio(idxConsulta, valor, tipoPag, cobertura);
                        } else if ("dinheiro".equals(tipoPag)) {
                            pagamento = new PagamentoDinheiro(idxConsulta, valor, tipoPag);
                        } else {
                            System.out.println("Tipo de pagamento inválido. Use dinheiro, cartao ou convenio.");
                            break;
                        }

                        servico.registrarPagamento(pagamento);
                        break;
                    case 2:
                        servico.listarPagamentos();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    // ========== RELATORIOS ==========

    public static void menuRelatorios() {
        int op = -1;
        while (op != 0) {
            try {
                System.out.println("\n--- RELATORIOS ---");
                System.out.println("1 - Relatorio geral");
                System.out.println("2 - Relatorio por profissional");
                System.out.println("3 - Relatorio por periodo");
                System.out.println("4 - Resumo financeiro");
                System.out.println("5 - Relatorio de cancelamentos");
                System.out.println("6 - Relatorio de multas");
                System.out.println("7 - Exportar dados");
                System.out.println("0 - Voltar");
                op = lerInt("Opcao: ");

                switch (op) {
                    case 1:
                        servico.gerarRelatorioGeral();
                        break;
                    case 2:
                        System.out.print("Nome do profissional: ");
                        String nome = sc.nextLine();
                        servico.gerarRelatorioPorProfissional(nome);
                        break;
                    case 3:
                        System.out.print("Data inicio (DD/MM/AAAA): ");
                        String ini = sc.nextLine();
                        System.out.print("Data fim (DD/MM/AAAA): ");
                        String fim = sc.nextLine();
                        servico.gerarRelatorioPorPeriodo(ini, fim);
                        break;
                    case 4:
                        servico.gerarResumoFinanceiro();
                        break;
                    case 5:
                        servico.gerarRelatorioCancelamentos();
                        break;
                    case 6:
                        servico.gerarRelatorioMultas();
                        break;
                    case 7:
                        menuExportacao();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    public static void menuExportacao() {
        int op = -1;
        while (op != 0) {
            try {
                System.out.println("\n--- EXPORTAR DADOS ---");
                System.out.println("1 - Consultas");
                System.out.println("2 - Atendimentos");
                System.out.println("3 - Pagamentos");
                System.out.println("4 - Pacientes");
                System.out.println("5 - Profissionais");
                System.out.println("6 - Todas as pessoas");
                System.out.println("0 - Voltar");
                op = lerInt("Opcao: ");

                switch (op) {
                    case 1:
                        servico.exportarConsultas();
                        break;
                    case 2:
                        servico.exportarAtendimentos();
                        break;
                    case 3:
                        servico.exportarPagamentos();
                        break;
                    case 4:
                        servico.exportarPacientes();
                        break;
                    case 5:
                        servico.exportarProfissionais();
                        break;
                    case 6:
                        servico.exportarTodasAsPessoas();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    // ========== METODOS AUXILIARES ==========

    public static int lerInt(String mensagem) {
        while (true) {
            if (!mensagem.isEmpty()) {
                System.out.print(mensagem);
            }
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um numero inteiro valido.");
            }
        }
    }

    public static double lerDouble(String mensagem) {
        while (true) {
            if (!mensagem.isEmpty()) {
                System.out.print(mensagem);
            }
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um numero valido.");
            }
        }
    }

    public static void menuComplementarPaciente() {
        System.out.println("\n--- COMPLEMENTAR PACIENTE ---");
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        Paciente paciente = servico.buscarPacientePorCpf(cpf);
        if (paciente == null) {
            System.out.println("Paciente nao encontrado.");
            return;
        }
        System.out.println("1 - Complementar apenas idade e telefone");
        System.out.println("2 - Complementar idade, telefone e convenio");
        int opcao = lerInt("Opcao: ");
        if (opcao == 1) {
            int idade = lerInt("Idade: ");
            System.out.print("Telefone: ");
            String tel = sc.nextLine();
            try {
                paciente.complementar(idade, tel);
                System.out.println("Cadastro complementado com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else if (opcao == 2) {
            int idade = lerInt("Idade: ");
            System.out.print("Telefone: ");
            String tel = sc.nextLine();
            System.out.print("Convenio: ");
            String conv = sc.nextLine();
            try {
                paciente.complementar(idade, tel, conv);
                System.out.println("Cadastro complementado com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Opcao invalida.");
        }
    }
}