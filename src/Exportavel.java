/**
 * Interface para objetos que podem ser exportados em formato de texto.
 * Serve para padronizar a conversão dos dados para apresentação ou salvamento.
 */
public interface Exportavel {

    /**
     * Converte os dados do objeto em texto.
     *
     * @return representação textual do objeto
     */
    String exportarParaTexto();
}
