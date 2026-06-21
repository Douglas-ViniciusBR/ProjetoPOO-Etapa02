/*
 * PagamentoConvenio usa o percentual de cobertura do convênio para reduzir o valor.
 * Essa regra é diferente das outras duas, reforçando a ideia de polimorfismo:
 * o método calcularValorFinal() muda conforme a subclasse usada.
 */
public class PagamentoConvenio extends Pagamento {
    private final double percentualCobertura;

    public PagamentoConvenio(int indiceConsulta, double valorFinal, String tipoPagamento,
                             double percentualCobertura) {
        super(indiceConsulta, valorFinal, tipoPagamento);
        this.percentualCobertura = percentualCobertura;
    }

    @Override
    public double calcularValorFinal(double valorBase) {
        // Garante que o percentual não saia do intervalo válido.
        double coberturaLimpa = Math.max(0, Math.min(100, percentualCobertura));
        double desconto = valorBase * (coberturaLimpa / 100);
        return Math.max(0, valorBase - desconto);
    }
}
