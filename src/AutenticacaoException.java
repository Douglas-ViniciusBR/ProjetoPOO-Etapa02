/**
 * Exceção para falhas em login, permissões ou validação de acesso.
 *
 * Mesmo que o projeto atual não tenha módulo de login completo,
 * este tipo de erro fica separado aqui para crescimento futuro.
 */
public class AutenticacaoException extends ClinicaException {
    public AutenticacaoException() {
        super();
    }

    public AutenticacaoException(String message) {
        super(message);
    }

    public AutenticacaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutenticacaoException(Throwable cause) {
        super(cause);
    }
}
