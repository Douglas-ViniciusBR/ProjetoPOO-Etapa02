/*
 * Classe abstrata porque um pagamento genérico não possui uma regra única de cálculo.
 * Não faz sentido criar um objeto "Pagamento" sem definir como ele deve tratar o valor.
 *
 * O polimorfismo aparece porque o método calcularValorFinal() pode ter 3 comportamentos
 * diferentes dependendo da subclasse escolhida: dinheiro, cartão ou convênio.
 */
public abstract class Pagamento {
    public int indiceConsulta;
    public double valorFinal;
    public String tipoPagamento;
    public int parcelas;

    public Pagamento(int indiceConsulta, double valorFinal, String tipoPagamento) {
        this.indiceConsulta = indiceConsulta;
        this.valorFinal = valorFinal;
        this.tipoPagamento = tipoPagamento;
        this.parcelas = 1;
    }

    // com parcelas (usado principalmente para cartão)
    public Pagamento(int indiceConsulta, double valorFinal, String tipoPagamento, int parcelas) {
        this.indiceConsulta = indiceConsulta;
        this.valorFinal = valorFinal;
        this.tipoPagamento = tipoPagamento;
        this.parcelas = parcelas;
    }

    /**
     * Método abstrato: cada subclasse implementa sua própria lógica.
     * Isso é o coração do polimorfismo, pois o mesmo método pode produzir
     * resultados diferentes conforme o tipo do objeto.
     */
    public abstract double calcularValorFinal(double valorBase);

    public String exibirResumo() {
        // arredonda pra 2 casas
        double valorArredondado = Math.round(valorFinal * 100.0) / 100.0;
        String resumo = "Consulta #" + indiceConsulta + " | Valor: R$" + valorArredondado
                + " | Tipo: " + tipoPagamento + " | Parcelas: " + parcelas;
        if (parcelas > 1) {
            double valorParcela = Math.round((valorFinal / parcelas) * 100.0) / 100.0;
            resumo = resumo + " (R$" + valorParcela + " cada)";
        }
        return resumo;
    }
}
