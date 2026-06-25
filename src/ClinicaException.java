/**
 * Exceção base usada pelo sistema da clínica.
 * Todas as exceções customizadas podem herdar desta classe,
 * mantendo o tratamento de erros do sistema mais consistente.
 */
public class ClinicaException extends RuntimeException {
    public ClinicaException() {
        super();
    }

    public ClinicaException(String message) {
        super(message);
    }

    public ClinicaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClinicaException(Throwable cause) {
        super(cause);
    }
}
