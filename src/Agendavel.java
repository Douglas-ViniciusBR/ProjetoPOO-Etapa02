/**
 * Interface para objetos que podem ser agendados.
 * Define o contrato necessário para controlar data, horário e status da consulta.
 */
public interface Agendavel {

    /**
     * Agenda a consulta em uma data e horário informados.
     *
     * @param data data da consulta
     * @param horario horário da consulta
     */
    void agendar(String data, String horario);

    /**
     * Cancela a consulta.
     */
    void cancelar();

    /**
     * Retorna a data da consulta.
     *
     * @return data da consulta
     */
    String getData();

    /**
     * Retorna o horário da consulta.
     *
     * @return horário da consulta
     */
    String getHorario();

    /**
     * Verifica se a consulta está agendada.
     *
     * @return true se estiver agendada, false caso contrário
     */
    boolean isAgendada();
}