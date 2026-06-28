public class Fisioterapeuta extends Profissional {
    private int quantidadeSessoesPorTratamento;

    public Fisioterapeuta(String nome, String cpf) {
        super(nome, cpf, "fisioterapia");
        this.quantidadeSessoesPorTratamento = 10;
    }

    public Fisioterapeuta(String nome, String cpf, String registroProfissional, double valorConsulta) {
        super(nome, cpf, "fisioterapia", registroProfissional, valorConsulta);
        this.quantidadeSessoesPorTratamento = 10;
    }

    public Fisioterapeuta(String nome, String cpf, String registroProfissional, double valorConsulta,
                          String[] dias, int totalDias) {
        super(nome, cpf, "fisioterapia", registroProfissional, valorConsulta, dias, totalDias);
        this.quantidadeSessoesPorTratamento = 10;
    }

    public int getQuantidadeSessoesPorTratamento() {
        return quantidadeSessoesPorTratamento;
    }

    public void setQuantidadeSessoesPorTratamento(int quantidadeSessoesPorTratamento) {
        if (quantidadeSessoesPorTratamento <= 0) {
            throw new DadosInvalidosException("Quantidade de sessões inválida: " + quantidadeSessoesPorTratamento);
        }
        this.quantidadeSessoesPorTratamento = quantidadeSessoesPorTratamento;
    }

    @Override
    public void exibirResumo() {
        System.out.println("Nome: " + getNome()
                + " | Conselho: " + getRegistroProfissional()
                + " | Especialidade: " + getEspecialidade()
                + " | Sessões por tratamento: " + quantidadeSessoesPorTratamento);
    }

    @Override
    public String toString() {
        return "Fisioterapeuta{" + super.toString() + ", sessoesPorTratamento=" + quantidadeSessoesPorTratamento + "}";
    }
}