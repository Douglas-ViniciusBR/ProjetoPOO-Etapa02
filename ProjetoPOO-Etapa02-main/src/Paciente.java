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
        setIdade(idade);
        setTelefone(telefone);
        this.convenioNome = "";
        this.ativo = true;
    }

    // construtor com todos os dados
    public Paciente(String nome, String cpf, int idade, String telefone, String convenioNome) {
        super(nome, cpf);
        setIdade(idade);
        setTelefone(telefone);
        setConvenioNome(convenioNome);
        this.ativo = true;
    }

    // atualiza so idade e telefone
    public void complementar(int idade, String telefone) {
        setIdade(idade);
        setTelefone(telefone);
    }

    // atualiza tudo incluindo convenio
    public void complementar(int idade, String telefone, String convenioNome) {
        setIdade(idade);
        setTelefone(telefone);
        setConvenioNome(convenioNome);
    }

    public void desativar() {
        this.ativo = false;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade < 0 || idade > 130) {
            throw new IllegalArgumentException("Idade inválida: " + idade);
        }
        this.idade = idade;
    }

    public void setTelefone(String telefone) {
        if (telefone == null) telefone = "";
        String t = telefone.trim();
        if (!t.isEmpty() && !t.matches("[0-9()+\\- ]+")) {
            throw new IllegalArgumentException("Telefone inválido: " + telefone);
        }
        this.telefone = t;
    }

    public void setConvenioNome(String convenioNome) {
        if (convenioNome == null) convenioNome = "";
        this.convenioNome = convenioNome.trim();
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
