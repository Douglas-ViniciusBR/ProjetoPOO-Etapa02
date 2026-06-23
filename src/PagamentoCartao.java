/*
 * PagamentoCartao aplica juros conforme a quantidade de parcelas.
 * Aqui o polimorfismo fica evidente: a mesma assinatura do método calcula
 * um valor diferente porque o cartão precisa considerar o custo do parcelamento.
 */
public class PagamentoCartao extends Pagamento {
    private static final double TAXA_JUROS_POR_PARCELA = 0.03;

    public PagamentoCartao(int indiceConsulta, double valorFinal, String tipoPagamento, int parcelas) {
        super(indiceConsulta, valorFinal, tipoPagamento, parcelas);
    }

    @Override
    public double calcularValorFinal(double valorBase) {
        // Juros do cartão aumentam conforme o número de parcelas.
        double juros = valorBase * TAXA_JUROS_POR_PARCELA * (this.parcelas - 1);
        return valorBase + juros;
    }
}
