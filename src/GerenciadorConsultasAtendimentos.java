import java.util.ArrayList;
import java.util.List;

public class GerenciadorConsultasAtendimentos {

    // ArrayList para armazenar Consultas
    private List<Consulta> listaConsultas;

    // ArrayList para armazenar Atendimentos
    private List<Atendimento> listaAtendimentos;

    // Construtor inicializando as estruturas de dados
    public GerenciadorConsultasAtendimentos() {
        this.listaConsultas = new ArrayList<>();
        this.listaAtendimentos = new ArrayList<>();
    }

    /**
     * Requisito Commit 2: Gerenciar Consultas e Atendimentos usando ArrayList
     */
    public boolean agendarConsulta(Consulta consulta) {
        if (consulta == null) {
            System.out.println("Erro: Consulta inválida.");
            return false;
        }
        listaConsultas.add(consulta);
        System.out.println("Consulta agendada com sucesso!");
        return true;
    }

    // Registrar Atendimento no sistema
    public boolean registrarAtendimento(Atendimento atendimento) {
        if (atendimento == null) {
            System.out.println("Erro: Atendimento inválido.");
            return false;
        }
        listaAtendimentos.add(atendimento);
        System.out.println("Atendimento registrado com sucesso!");
        return true;
    }

    // Listar todas as Consultas armazenadas (ArrayList)
    public void listarTodasConsultas() {
        if (listaConsultas.isEmpty()) {
            System.out.println("Nenhuma consulta agendada.");
            return;
        }
        System.out.println("--- Lista de Consultas (ArrayList) ---");
        for (Consulta c : listaConsultas) {
            System.out.println("Consulta: " + c.toString());
        }
    }

    // Listar todos os Atendimentos armazenados (ArrayList)
    public void listarTodosAtendimentos() {
        if (listaAtendimentos.isEmpty()) {
            System.out.println("Nenhum atendimento registrado.");
            return;
        }
        System.out.println("--- Lista de Atendimentos (ArrayList) ---");
        for (Atendimento a : listaAtendimentos) {
            System.out.println("Atendimento: " + a.toString());
        }
    }

    // Remover Consulta do sistema
    public boolean removerConsulta(int indice) {
        if (indice >= 0 && indice < listaConsultas.size()) {
            listaConsultas.remove(indice);
            System.out.println("Consulta removida com sucesso!");
            return true;
        }
        System.out.println("Consulta não encontrada para remoção.");
        return false;
    }

    // Remover Atendimento do sistema
    public boolean removerAtendimento(int indice) {
        if (indice >= 0 && indice < listaAtendimentos.size()) {
            listaAtendimentos.remove(indice);
            System.out.println("Atendimento removido com sucesso!");
            return true;
        }
        System.out.println("Atendimento não encontrado para remoção.");
        return false;
    }

    // Obter quantidade de Consultas
    public int obterQuantidadeConsultas() {
        return listaConsultas.size();
    }

    // Obter quantidade de Atendimentos
    public int obterQuantidadeAtendimentos() {
        return listaAtendimentos.size();
    }
}
