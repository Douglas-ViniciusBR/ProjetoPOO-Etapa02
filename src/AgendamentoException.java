/**
 * Exceção usada quando ocorre um problema ao agendar,
 * remarcar ou cancelar uma consulta.
 */
public class AgendamentoException extends ClinicaException {
    public AgendamentoException() {
        super();
    }

    public AgendamentoException(String message) {
        super(message);
    }

    public AgendamentoException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgendamentoException(Throwable cause) {
        super(cause);
    }
}
