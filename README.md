# Clínica VidaPlena

## 1. Sobre o sistema

Este projeto é uma aplicação em Java para gerenciar o fluxo de uma clínica médica no modo console. Ele oferece recursos básicos que ajudam a controlar:

- pacientes
- profissionais de saúde
- consultas
- atendimentos e prontuários
- pagamentos
- relatórios

A ideia foi criar um sistema que seja simples de usar e, ao mesmo tempo, que mostre os principais conceitos de Orientação a Objetos: herança, polimorfismo, interfaces, abstração e tratamento de exceções.

## 2. Diagrama de classes

```
  Agendavel       Exportavel
  +-----------+   +-----------+
  | +agendar  |   | +exportar |
  | +cancelar |   +-----------+
  +-----------+
        ^
        |
    +---------+
    | Consulta|
    +---------+
    | cpf     |
    | prof    |
    | data    |
    | hora    |
    +---------+

    +-------+
    | Pessoa|
    +-------+
    | nome  |
    | cpf   |
    +-------+
       / \
      /   \
+--------+ +-------------+
|Paciente| |Profissional |
+--------+ +-------------+
| idade  | | especialidade|
| tel    | | registro    |
| conv   | | valor       |
| ativo  | | dias        |
+--------+ +-------------+

    +------------+   +----------+   +---------+   +-------------+
    |ClinicoGeral|   |Fisioter. |   |Psicologo|   |Nutricionista|
    +------------+   +----------+   +---------+   +-------------+

  +------------+
  | Pagamento  |
  +------------+
  | indice     |
  | valor      |
  | tipo       |
  | parcelas   |
  +------------+
      / | \
     /  |  \
+-------+ +-------+ +---------+
|Dinheiro| |Cartao | |Convenio|
+-------+ +-------+ +---------+

  +-------------------+
  | ClinicaServico    |
  +-------------------+
  | pacientes         |
  | profissionais     |
  | consultas         |
  | atendimentos      |
  | pagamentos        |
  | multas            |
  +-------------------+
```

> Observação: `GerenciadorConsultasAtendimentos` serve como classe de apoio, mas o fluxo principal é tratado em `ClinicaServico`.

## 3. Funcionalidades desenvolvidas

O sistema já implementa as funcionalidades principais para uma clínica simples:

- cadastro de pacientes com validação de CPF, idade, telefone e convênio
- desativação de paciente para impedir novos agendamentos
- complementação de cadastro de paciente com mais informações
- cadastro de profissionais por especialidade
- atualização de registro, valor de consulta e dias de atendimento do profissional
- filtro de profissionais por especialidade
- agendamento de consultas com verificação de conflitos de horário
- cancelamento de consultas com aplicação de multa dependendo do prazo
- remarcação de consultas e verificação de disponibilidade
- registro de atendimentos com observações, diagnóstico e procedimentos
- pagamentos apenas para consultas realizadas
- pagamentos em dinheiro, cartão e convênio com regras específicas
- relatórios diversos: consultas, finanças, cancelamentos, multas e cadastros
- exportação de dados para visualização em texto no console

## 4. Como compilar e executar

### Requisitos

- Java instalado (JDK 11 ou superior)
- acesso à pasta do projeto

### Passo a passo

Abra o terminal e vá para a pasta do projeto:

```bash
cd "c:\Users\vdoug\OneDrive\Documentos\ProjetoPOO-Etapa02"
```

Compile os arquivos Java:

```bash
javac src\*.java
```

Se quiser separar as classes compiladas em outra pasta:

```bash
javac -d bin src\*.java
```

Execute o sistema:

```bash
java -cp src Main
```

ou, se usar o diretório `bin`:

```bash
java -cp bin Main
```

## 5. Como usar o sistema

Ao abrir o programa, o menu principal mostra as opções disponíveis:

1. Pacientes
2. Profissionais
3. Consultas
4. Atendimentos
5. Pagamentos
6. Relatórios
0. Sair

### 5.1 Pacientes

- `1 - Cadastrar`: cria um novo paciente. Há três níveis de cadastro:
  - mínimo: nome e CPF
  - com idade e telefone
  - completo: idade, telefone e convênio
- `2 - Buscar por CPF`: encontra um paciente pelo CPF
- `3 - Listar todos`: mostra todos os pacientes registrados
- `4 - Desativar`: marca um paciente como inativo
- `5 - Complementar cadastro`: adiciona idade, telefone e convênio depois do cadastro inicial

### 5.2 Profissionais

- `1 - Cadastrar`: registra o profissional e sua especialidade
- `2 - Atualizar profissional`: altera informações como registro, valor e dias de atendimento
- `3 - Listar todos`: mostra a lista completa
- `4 - Filtrar por especialidade`: exibe apenas profissionais daquela área

### 5.3 Consultas

- `1 - Agendar com profissional`: escolhe paciente e profissional e agenda data/hora
- `2 - Listar todas`: mostra todas as consultas agendadas
- `3 - Buscar por CPF`: mostra consultas de um paciente específico
- `4 - Cancelar consulta`: cancela uma consulta e aplica multa, se necessário
- `5 - Remarcar consulta`: muda a data e/ou horário de uma consulta

### 5.4 Atendimentos

- `1 - Registrar atendimento`: registra o atendimento associado a uma consulta agendada
- `2 - Listar atendimentos`: mostra os atendimentos já registrados

### 5.5 Pagamentos

- `1 - Registrar pagamento`: cadastra o pagamento de uma consulta realizada
  - `dinheiro`: desconto de 5%
  - `cartao`: juros conforme o número de parcelas
  - `convenio`: desconto de acordo com a cobertura
- `2 - Listar pagamentos`: exibe os pagamentos registrados

### 5.6 Relatórios

- `1 - Relatório geral`: lista todas as consultas
- `2 - Relatório por profissional`: filtra por profissional
- `3 - Relatório por período`: filtra por intervalo de datas
- `4 - Resumo financeiro`: mostra faturamento e multas
- `5 - Relatório de cancelamentos`: exibe consultas canceladas
- `6 - Relatório de multas`: lista multas aplicadas
- `7 - Relatório unificado de cadastros`: mostra pacientes e profissionais cadastrados
- `8 - Exportar dados`: imprime dados de consultas, atendimentos, pagamentos, pacientes e profissionais

## 6. Observações finais

- Todos os dados são mantidos somente enquanto o programa estiver aberto.
- CPF é validado com regras básicas de dígitos verificadores.
- Convênios são aceitos conforme a especialidade cadastrada.
- O sistema funciona em modo texto, sem interface gráfica.
