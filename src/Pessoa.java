public abstract class Pessoa {
    // Classe abstrata representa uma pessoa generica na clinica.
    // Usamos uma classe abstrata para compartilhar atributos comuns
    // e para definir um contrato que obrigue as subclasses a implementarem
    // comportamentos especificos.
    private String nome;
    private String cpf;

    public Pessoa(String nome, String cpf) {
        setNome(nome);
        setCpf(cpf);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (nome.trim().length() < 2) {
            throw new IllegalArgumentException("Nome muito curto");
        }
        this.nome = nome.trim();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null) throw new IllegalArgumentException("CPF nulo");
        String onlyDigits = cpf.replaceAll("\\D", "");
        if (!validarCpf(onlyDigits)) {
            throw new IllegalArgumentException("CPF inválido: " + cpf);
        }
        this.cpf = onlyDigits;
    }

    // validação básica de CPF com dígitos verificadores
    private static boolean validarCpf(String cpf) {
        if (cpf == null) return false;
        if (cpf.length() != 11) return false;
        // inválido se todos os dígitos forem iguais
        boolean todosIguais = true;
        for (int i = 1; i < 11; i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) { todosIguais = false; break; }
        }
        if (todosIguais) return false;

        try {
            int[] nums = new int[11];
            for (int i = 0; i < 11; i++) nums[i] = Character.getNumericValue(cpf.charAt(i));

            // primeiro dígito verificador
            int sum = 0;
            for (int i = 0; i < 9; i++) sum += nums[i] * (10 - i);
            int r = 11 - (sum % 11);
            int dv1 = (r == 10 || r == 11) ? 0 : r;
            if (dv1 != nums[9]) return false;

            // segundo dígito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) sum += nums[i] * (11 - i);
            r = 11 - (sum % 11);
            int dv2 = (r == 10 || r == 11) ? 0 : r;
            return dv2 == nums[10];
        } catch (Exception e) {
            return false;
        }
    }

    // Metodo abstrato define um comportamento que deve ser implementado
    // por todas as subclasses, sem fornecer a implementacao na classe pai.
    public abstract void exibirResumo();

    /**
     * Retorna uma representação simples e legível desta pessoa.
     * Útil para depurar, registrar ou mostrar informações sem
     * precisar acessar cada campo individualmente.
     */
    @Override
    public String toString() {
        return "Pessoa{nome='" + nome + "', cpf='" + cpf + "'}";
    }
}
