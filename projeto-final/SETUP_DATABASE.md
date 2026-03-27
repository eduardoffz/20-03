# Configuração do Banco de Dados - MySQL

## ⚠️ IMPORTANTE: Execute os comandos abaixo ANTES de executar o projeto

Este arquivo contém os passos necessários para preparar o banco de dados para a lista de tarefas.

## Opção 1: Via MySQL Workbench ou phpMyAdmin

1. **Criar o banco de dados:**

   ```sql
   CREATE DATABASE gerenciador;
   ```

2. **Usar o banco:**

   ```sql
   USE gerenciador;
   ```

3. **Criar tabela de usuários:**

   ```sql
   CREATE TABLE usuarios (
       id INT PRIMARY KEY AUTO_INCREMENT,
       nome VARCHAR(100) NOT NULL,
       usuario VARCHAR(50) NOT NULL UNIQUE,
       senha VARCHAR(50) NOT NULL,
       admin BOOLEAN DEFAULT FALSE
   );
   ```

4. **Criar tabela de tarefas:**

   ```sql
   CREATE TABLE tarefas (
       id INT PRIMARY KEY AUTO_INCREMENT,
       titulo VARCHAR(150) NOT NULL,
       descricao TEXT,
       data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
       data_vencimento DATE NOT NULL,
       status VARCHAR(20) DEFAULT 'Pendente',
       prioridade VARCHAR(20) DEFAULT 'Normal',
       id_usuario INT NOT NULL,
       FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
   );
   ```

5. **Inserir dados de teste:**

   ```sql
   INSERT INTO usuarios (nome, usuario, senha, admin) VALUES
   ('Admin', 'admin', 'admin', TRUE),
   ('João Silva', 'joao', '123456', FALSE),
   ('Maria Santos', 'maria', '123456', FALSE);

   INSERT INTO tarefas (titulo, descricao, data_vencimento, status, prioridade, id_usuario) VALUES
   ('Estudar Java', 'Revisar conceitos de OOP', '2024-12-31', 'Pendente', 'Alta', 2),
   ('Fazer compras', 'Compras do mês', '2024-04-15', 'Em Progresso', 'Normal', 3),
   ('Projeto Final', 'Completar projeto de lista de tarefas', '2024-12-20', 'Pendente', 'Alta', 2);
   ```

## Opção 2: Via linha de comando MySQL

1. **Abra o terminal e conecte ao MySQL:**

   ```bash
   mysql -u root -p
   ```

2. **Cole todo o conteúdo do arquivo `database_setup.sql`**

3. **Pressione Enter e aguarde a conclusão**

## Opção 3: Importar arquivo SQL direto

**No Windows (PowerShell):**

```bash
mysql -u root -p gerenciador < database_setup.sql
```

**No Linux/Mac (Terminal):**

```bash
mysql -u root -p gerenciador < database_setup.sql
```

## ✅ Verificar se foi criado corretamente

```sql
-- Verificar tabelas
SHOW TABLES IN gerenciador;

-- Verificar usuários
SELECT * FROM usuarios;

-- Verificar tarefas
SELECT * FROM tarefas;
```

## 🔧 Ajustar credenciais (se necessário)

Se você usa uma senha diferente para MySQL, edite o arquivo:

```
src/main/java/conexao/Conexao.java
```

E altere:

```java
private static final String url = "jdbc:mysql://localhost:3306/gerenciador";
private static final String user = "root";              // Altere se seu usuário é diferente
private static final String senha = "1234";            // Altere se sua senha é diferente
```

## 📌 Dados padrão de teste

| Usuário | Senha  | Função        |
| ------- | ------ | ------------- |
| admin   | admin  | Administrador |
| joao    | 123456 | Usuário comum |
| maria   | 123456 | Usuário comum |

## ✨ Pronto!

Após completar qualquer uma das três opções acima, seu banco de dados estará pronto para usar a aplicação.

Agora você pode:

1. Compilar o projeto: `mvn clean install`
2. Executar a aplicação
3. Fazer login com um dos usuários acima
4. Gerenciar suas tarefas!

---

**Dúvidas??**

- Verifique se o MySQL está rodando
- Confirme as credenciais em `Conexao.java`
- Verifique se criou o banco `gerenciador`
