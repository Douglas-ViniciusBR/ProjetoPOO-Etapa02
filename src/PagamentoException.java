/*Exceção direcionada a problemas ocorridos na parte de pagamento.
 * Pode ser usada tanto para valores incorretos quanto para lógica de taxas.
 */
public class PagamentoException extends ClinicaException {
    public PagamentoException() {
        super();
    }

    public PagamentoException(String message) {
        super(message);
    }

    public PagamentoException(String message, Throwable cause) {
        super(message, cause);
    }

    public PagamentoException(Throwable cause) {
        super(cause);
    }
}
