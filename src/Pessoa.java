public abstract class Pessoa {
    // Classe abstrata representa uma pessoa generica na clinica.
    // Usamos uma classe abstrata para compartilhar atributos comuns
    // e para definir um contrato que obrigue as subclasses a implementarem
    // comportamentos especificos.
    private String nome;
    private String cpf;

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // Metodo abstrato define um comportamento que deve ser implementado
    // por todas as subclasses, sem fornecer a implementacao na classe pai.
    public abstract void exibirResumo();
}
