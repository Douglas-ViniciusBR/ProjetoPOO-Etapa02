import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NovoGerenciadorDados {

    // HashMap para buscas rápidas por CPF (Chave: CPF, Valor: Objeto Paciente)
    private Map<String, Paciente> mapaPacientes;
    
    // HashSet para garantir que não existam pacientes duplicados no sistema
    private Set<Paciente> conjuntoPacientes;

    // Construtor inicializando as novas estruturas de dados
    public NovoGerenciadorDados() {
        this.mapaPacientes = new HashMap<>();
        this.conjuntoPacientes = new HashSet<>();
    }

    /**
     * Requisito Commit 1: Cadastrar e gerenciar Pacientes usando HashMap e HashSet
     */
    public boolean cadastrarPaciente(Paciente paciente) {
        if (paciente == null || paciente.getCpf() == null) {
            System.out.println("Erro: Paciente inválido.");
            return false;
        }

        // 1. Tenta adicionar no HashSet (ele retorna falso se o paciente já existir)
        boolean adicionadoNoSet = conjuntoPacientes.add(paciente);

        if (adicionadoNoSet) {
            // 2. Se adicionou no Set com sucesso, adiciona no HashMap para indexação por CPF
            mapaPacientes.put(paciente.getCpf(), paciente);
            System.out.println("Paciente " + paciente.getNome() + " cadastrado com sucesso usando coleções!");
            return true;
        } else {
            System.out.println("Erro: Paciente com este CPF já está cadastrado (Duplicado).");
            return false;
        }
    }

    // Buscar Paciente de forma rápida usando a chave do HashMap
    public Paciente buscarPacientePorCpf(String cpf) {
        return mapaPacientes.get(cpf);
    }

    // Remover Paciente do sistema
    public boolean removerPaciente(String cpf) {
        Paciente paciente = mapaPacientes.get(cpf);
        if (paciente != null) {
            mapaPacientes.remove(cpf);
            conjuntoPacientes.remove(paciente);
            System.out.println("Paciente removido com sucesso!");
            return true;
        }
        System.out.println("Paciente não encontrado para remoção.");
        return false;
    }

    // Listar todos os pacientes do conjunto (HashSet)
    public void listarTodosPacientes() {
        if (conjuntoPacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }
        System.out.println("--- Lista de Pacientes (HashSet) ---");
        for (Paciente p : conjuntoPacientes) {
            System.out.println("Nome: " + p.getNome() + " | CPF: " + p.getCpf());
        }
    }
}
