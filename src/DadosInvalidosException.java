/**
 * Exceção para situações em que algum valor ou campo
 * recebido pelo sistema não está dentro do que é esperado.
 */
public class DadosInvalidosException extends ClinicaException {
    public DadosInvalidosException() {
        super();
    }

    public DadosInvalidosException(String message) {
        super(message);
    }

    public DadosInvalidosException(String message, Throwable cause) {
        super(message, cause);
    }

    public DadosInvalidosException(Throwable cause) {
        super(cause);
    }
}
