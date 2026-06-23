/*
 * PagamentoDinheiro representa o caso em que o paciente paga à vista.
 * A regra de 5% de desconto é específica desta subclasse, mostrando que
 * o mesmo método calcularValorFinal() pode ter uma lógica diferente para cada tipo.
 */
public class PagamentoDinheiro extends Pagamento {

    public PagamentoDinheiro(int indiceConsulta, double valorFinal, String tipoPagamento) {
        super(indiceConsulta, valorFinal, tipoPagamento);
    }

    @Override
    public double calcularValorFinal(double valorBase) {
        // Desconto fixo de 5% para pagamento em dinheiro.
        double desconto = valorBase * 0.05;
        return Math.max(0, valorBase - desconto);
    }
}
