import java.util.ArrayList;
import java.util.List;

public class GerenciadorConsultasAtendimentos {

    // aqui ficam todas as consultas agendadas
    // a ordem e conseguir acessar por índice quando precisar
    private List<Consulta> listaConsultas;

    // mesma ideia pros atendimentos
    private List<Atendimento> listaAtendimentos;

    public GerenciadorConsultasAtendimentos() {
        this.listaConsultas = new ArrayList<>();
        this.listaAtendimentos = new ArrayList<>();
    }

    public boolean agendarConsulta(Consulta consulta) {
        if (consulta == null) {
            System.out.println("Erro: consulta inválida.");
            return false;
        }
        listaConsultas.add(consulta);
        System.out.println("Consulta agendada com sucesso!");
        return true;
    }

    public boolean registrarAtendimento(Atendimento atendimento) {
        if (atendimento == null) {
            System.out.println("Erro: atendimento inválido.");
            return false;
        }
        listaAtendimentos.add(atendimento);
        System.out.println("Atendimento registrado com sucesso!");
        return true;
    }

    public void listarTodasConsultas() {
        if (listaConsultas.isEmpty()) {
            System.out.println("Nenhuma consulta agendada ainda.");
            return;
        }

        System.out.println("--- Consultas ---");
        for (Consulta c : listaConsultas) {
            System.out.println(c.toString());
        }
    }

    public void listarTodosAtendimentos() {
        if (listaAtendimentos.isEmpty()) {
            System.out.println("Nenhum atendimento registrado ainda.");
            return;
        }

        System.out.println("--- Atendimentos ---");
        for (Atendimento a : listaAtendimentos) {
            System.out.println(a.toString());
        }
    }

    public boolean removerConsulta(int indice) {
        if (indice < 0 || indice >= listaConsultas.size()) {
            System.out.println("Indice invalido, nenhuma consulta removida.");
            return false;
        }
        listaConsultas.remove(indice);
        System.out.println("Consulta removida.");
        return true;
    }

    public boolean removerAtendimento(int indice) {
        if (indice < 0 || indice >= listaAtendimentos.size()) {
            System.out.println("Indice invalido, nenhum atendimento removido.");
            return false;
        }
        listaAtendimentos.remove(indice);
        System.out.println("Atendimento removido.");
        return true;
    }

    // busca consulta por índice, útil pra operações de cancelamento e remarcação
    public Consulta buscarConsultaPorIndice(int indice) {
        if (indice < 0 || indice >= listaConsultas.size()) {
            System.out.println("Índice inválido.");
            return null;
        }
        return listaConsultas.get(indice);
    }

    public Atendimento buscarAtendimentoPorIndice(int indice) {
        if (indice < 0 || indice >= listaAtendimentos.size()) {
            System.out.println("Índice inválido.");
            return null;
        }
        return listaAtendimentos.get(indice);
    }

    public int obterQuantidadeConsultas() {
        return listaConsultas.size();
    }

    public int obterQuantidadeAtendimentos() {
        return listaAtendimentos.size();
    }

    public List<Consulta> getListaConsultas() {
        return listaConsultas;
    }

    public List<Atendimento> getListaAtendimentos() {
        return listaAtendimentos;
    }
}