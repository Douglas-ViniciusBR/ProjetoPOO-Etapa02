public class Paciente extends Pessoa {
    private int idade;
    private String telefone;
    private String convenioNome;
    private boolean ativo;

    public Paciente(String nome, String cpf) {
        super(nome, cpf);
        this.idade = 0;
        this.telefone = "";
        this.convenioNome = "";
        this.ativo = true;
    }

    public Paciente(String nome, String cpf, int idade, String telefone) {
        super(nome, cpf);
        this.idade = idade;
        this.telefone = telefone;
        this.convenioNome = "";
        this.ativo = true;
    }

    // construtor com todos os dados
    public Paciente(String nome, String cpf, int idade, String telefone, String convenioNome) {
        super(nome, cpf);
        this.idade = idade;
        this.telefone = telefone;
        this.convenioNome = convenioNome;
        this.ativo = true;
    }

    // atualiza so idade e telefone
    public void complementar(int idade, String telefone) {
        this.idade = idade;
        this.telefone = telefone;
    }

    // atualiza tudo incluindo convenio
    public void complementar(int idade, String telefone, String convenioNome) {
        this.idade = idade;
        this.telefone = telefone;
        this.convenioNome = convenioNome;
    }

    public void desativar() {
        this.ativo = false;
    }

    public int getIdade() {
        return idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getConvenioNome() {
        return convenioNome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    @Override
    public void exibirResumo() {
        String status = ativo ? "Sim" : "Nao";
        System.out.println("Nome: " + getNome() + " | CPF: " + getCpf() + " | Idade: " + idade
                + " | Tel: " + telefone + " | Convenio: " + convenioNome
                + " | Ativo: " + status);
    }

    /**
     * Retorna uma string com os principais dados do paciente.
     * Facilita logs, depuração e exibição rápida do estado do objeto.
     */
    @Override
    public String toString() {
        return "Paciente{nome='" + getNome() + "', cpf='" + getCpf() + "', idade=" + idade
                + ", telefone='" + telefone + "', convenio='" + convenioNome + "', ativo=" + ativo + "}";
    }
}
