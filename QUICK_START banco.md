# 🚀 INÍCIO RÁPIDO - 5 MINUTOS

## Um guia super rápido para começar!

### Passo 1: Preparar Banco de Dados (2 min)

Abra o MySQL e execute:

```sql
CREATE DATABASE gerenciador;
USE gerenciador;



-- =============================================================

-- INSTRUÇÕES:
--   1. Abra o MySQL Workbench (ou seu cliente preferido)
--   2. Execute este script inteiro de uma vez
--   3. O banco 'projetofinal' será criado automaticamente
--   4. Ajuste o usuário/senha na classe Conexao.java se necessário
-- =============================================================

-- Cria o banco de dados se ainda não existir

CREATE DATABASE IF NOT EXISTS projetofinal
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Seleciona o banco para as próximas instruções
USE projetofinal;

-- -------------------------------------------------------------
-- Tabela: usuarios
-- Armazena os dados de autenticação e perfil de cada usuário.
-- Referenciada por: tarefas.responsavel (indiretamente)
-- -------------------------------------------------------------

CREATE TABLE IF NOT EXISTS usuarios (
    id        INT          PRIMARY KEY AUTO_INCREMENT,
    nome      VARCHAR(100) NOT NULL,
    usuario   VARCHAR(50)  NOT NULL UNIQUE,
    senha     VARCHAR(50)  NOT NULL,
    admin     BOOLEAN      DEFAULT FALSE
);

-- -------------------------------------------------------------
-- Tabela: tarefas
-- Armazena as tarefas criadas pelos usuários.
-- Coluna chave: id_tarefa (usada em TarefaDAO.java)
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tarefas (
    id_tarefa   INT          PRIMARY KEY AUTO_INCREMENT,
    titulo      VARCHAR(150) NOT NULL,
    descricao   TEXT,
    responsavel VARCHAR(100) NOT NULL,
    status      VARCHAR(20)  DEFAULT 'Pendente'
);

-- -------------------------------------------------------------
-- Dados iniciais — usuários de exemplo para teste
-- Senha em texto plano (alterar para hash em produção)
-- -------------------------------------------------------------
INSERT INTO usuarios (nome, usuario, senha, admin) VALUES
    ('Administrador', 'admin', 'admin123', TRUE),
    ('João Silva',    'joao',  '123456',   FALSE),
    ('Maria Santos',  'maria', '123456',   FALSE);

-- -------------------------------------------------------------
-- Dados iniciais — tarefas de exemplo
-- -------------------------------------------------------------
INSERT INTO tarefas (titulo, descricao, responsavel, status) VALUES
    ('Estudar Java',    'Revisar conceitos de OOP e coleções',    'João Silva',   'Pendente'),
    ('Fazer compras',   'Compras do mês no supermercado',         'Maria Santos', 'Em andamento'),
    ('Projeto Final',   'Completar e documentar o projeto final', 'João Silva',   'Pendente');
```

**Ou execute via terminal:**

```bash
mysql -u root -p < database_setup.sql
```



### Passo 2: Verificar Credenciais (30 seg)

Abra: `src/main/java/conexao/Conexao.java`

Altere se sua senha do MySQL é diferente:

```java

private static final String senha = "1234"; // MUDE AQUI
```

### Passo 3: Compilar (1 min)

```bash
mvn clean install
```

### Passo 4: Executar (30 seg)

```bash
mvn exec:java -Dexec.mainClass="view.Login"
```

### Passo 5: Fazer Login (1 min)

Use qualquer um desses usuários:

- **admin** / admin
- **joao** / 123456
- **maria** / 123456

---

## ✨ Pronto! Você está dentro!

Agora você pode:

- ✅ Ver suas tarefas
- ✅ Criar novas tarefas
- ✅ Editar tarefas
- ✅ Deletar tarefas
- ✅ Fazer logout

---

## 📚 Documentação Completa

Confira esses arquivos para mais detalhes:

| Arquivo              | Conteúdo                          |
| -------------------- | --------------------------------- |
| `README.md`          | Guia completo do projeto          |
| `SETUP_DATABASE.md`  | Passo a passo do banco (3 opções) |
| `GUIA_TESTES.md`     | 10 cenários de teste              |
| `RESUMO_MUDANCAS.md` | Todas as mudanças implementadas   |

---

## 🆘 Problemas?

### "Erro de conexão ao banco"

→ Verifique se MySQL está rodando
→ Confirme o nome do banco: `gerenciador`
→ Verifique credenciais em `Conexao.java`

### "Abre tela de login, mas não loga"

→ Verifique se `database_setup.sql` foi executada
→ Teste login com: admin / admin

### "Maven não encontra dependências"

→ Execute: `mvn clean install`
→ Aguarde download das libs (pode levar 1-2 min)

### "JDateChooser não encontrado"

→ Já está no pom.xml!
→ Execute: `mvn clean install`

---

## 📞 Dicas Úteis

💡 **Use Debug:**

```bash
# Compilar com debug
mvn clean install -DskipTests
```

💡 **Limpar Cache:**

```bash
# Remove cache de dependências
rm -rf ~/.m2/repository (Linux/Mac)
rmdir %userprofile%\.m2\repository (Windows)
```

💡 **Checar Java:**

```bash
java -version  # Mínimo 23
javac -version # Mínimo 23
```

---

**Boa sorte! 🎉**

Qualquer dúvida, consulte a documentação completa nos arquivos .md
