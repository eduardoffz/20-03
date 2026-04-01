# Projeto Lista de Tarefas - Guia de Conclusão

## 📋 Resumo das Alterações Realizadas

Este documento descreve todas as modificações feitas para completar o projeto de Lista de Tarefas em Java Swing.

### ✅ Implementações Concluídas

#### 1. **Modelo de Dados**

- **TarefaBean.java**: Classe modelo com atributos:
  - `id`: Identificador único
  - `titulo`: Título da tarefa
  - `descricao`: Descrição detalhada
  - `dataCriacao`: Data de criação automática
  - `dataVencimento`: Data limite para conclusão
  - `status`: Estados possíveis (Pendente, Em Progresso, Concluída)
  - `prioridade`: Níveis (Baixa, Normal, Alta)
  - `idUsuario`: Referência ao usuário proprietário

#### 2. **Camada de Dados (DAO)**

- **TarefaDAO.java**: Implementação completa de CRUD
  - `criar()`: Inserir nova tarefa
  - `listar()`: Listar todas as tarefas
  - `listarPorUsuario()`: Filtrar tarefas por usuário
  - `atualizar()`: Editar tarefa existente
  - `deletar()`: Remover tarefa
  - `obterPorId()`: Buscar tarefa específica

#### 3. **Interfaces de Usuário (Views)**

**Tela de Login (Login.java)**

- Autenticação de usuários
- Passagem do usuário autenticado para a tela inicial
- Link para cadastro de novos usuários

**Tela Inicial (Inicio.java)**

- JTable com lista de tarefas do usuário logado
- Colunas: ID, Título, Descrição, Vencimento, Status, Prioridade
- Botões CRUD completes:
  - **Nova Tarefa**: Abre tela de criação
  - **Editar**: Abre tela de edição da tarefa selecionada
  - **Deletar**: Remove tarefa com confirmação
  - **Logout**: Retorna à tela de login

**Gerenciador de Tarefas (GerenciadorTarefas.java)** ⭐ NOVA

- Interface para criar e editar tarefas
- Campos de entrada:
  - Título (texto obrigatório)
  - Descrição (área de texto)
  - Data de Vencimento (seletor de data)
  - Status (combo: Pendente, Em Progresso, Concluída)
  - Prioridade (combo: Baixa, Normal, Alta)
- Validação de campos obrigatórios
- Botões Salvar e Cancelar

#### 4. **Banco de Dados**

- **database_setup.sql**: Script para criar estrutura completa
  - Tabela `usuarios`: Gerenciamento de contas
  - Tabela `tarefas`: Armazenamento de tarefas
  - Relacionamento entre tabelas (Foreign Key)
  - Dados iniciais para teste

#### 5. **Dependências do Maven**

- Added `jcalendar`: Biblioteca para seleção de datas no Swing

## 🚀 Instruções de Execução

### Pré-requisitos

- Java 23+
- Apache NetBeans 24+
- MySQL 8.0+
- Maven 3.6+

### 1. Configurar o Banco de Dados

```sql
-- Abrir o MySQL e executar:
CREATE DATABASE gerenciador;
USE gerenciador;
-- Executar o arquivo database_setup.sql
```

### 2. Atualizar Conexão (se necessário)

Edite `src/main/java/conexao/Conexao.java`:

```java
private static final String url = "jdbc:mysql://localhost:3306/gerenciador";
private static final String user = "root";
private static final String senha = "1234"; // Altere conforme sua senha MySQL
```

### 3. Compilar e Executar

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="view.Login"
```

Ou execute pelo NetBeans:

1. Abra o projeto
2. Click direito → Run
3. A tela de Login será exibida

## 📁 Estrutura do Projeto

```
projetoFinal/
├── src/
│   └── main/
│       └── java/
│           ├── conexao/
│           │   └── Conexao.java
│           ├── model/
│           │   ├── TarefaBean.java          ⭐ NOVO
│           │   ├── TarefaDAO.java           ⭐ NOVO
│           │   ├── UsuarioBean.java
│           │   ├── UsuarioDAO.java
│           │   ├── ProdutoBean.java
│           │   └── ProdutoDAO.java
│           └── view/
│               ├── Login.java
│               ├── Cadastro.java
│               ├── Inicio.java              ✏️ MODIFICADO
│               └── GerenciadorTarefas.java  ⭐ NOVO
├── pom.xml                                  ✏️ MODIFICADO
└── database_setup.sql                       ⭐ NOVO
```

## 🎯 Funcionalidades Principais

### Fluxo do Usuário

1. **Login** → Autenticação do usuário
2. **Dashboard (Inicio)** → Visualização de tarefas pessoais
3. **CRUD Completo**:
   - **Create**: Button "Nova Tarefa" abre formulário
   - **Read**: Tabela lista todas as tarefas do usuário
   - **Update**: Button "Editar" permite modificação
   - **Delete**: Button "Deletar" remove tarefa com confirmação

### Validações Implementadas

- ✅ Título obrigatório
- ✅ Data de vencimento obrigatória
- ✅ Filtro por usuário logado
- ✅ Confirmação antes de deletar
- ✅ Mensagens de sucesso/erro

## 🔐 Dados de Teste

**Usuários criados no banco:**

- Usuário: `admin` | Senha: `admin`
- Usuário: `joao` | Senha: `123456`
- Usuário: `maria` | Senha: `123456`

## 📝 Notas Importantes

1. **JDateChooser**: Biblioteca `jcalendar` foi adicionada para seleção visual de datas
2. **Atualização Automática**: A tabela é atualizada automaticamente após criar/editar/deletar
3. **Isolamento de Dados**: Cada usuário só vê suas próprias tarefas
4. **Padrão MVC**: Código segue padrão Model-View-Controller

## 🐛 Possíveis Melhorias Futuras

- [ ] Autenticação com hash de senha (BCrypt)
- [ ] Paginação na tabela de tarefas
- [ ] Busca/filtro avançado
- [ ] Ordenação por coluna
- [ ] Backup automático
- [ ] Temas personalizáveis
- [ ] Notificações de tarefas vencidas
- [ ] Importação/Exportação de tarefas (CSV/Excel)

## 👨‍💻 Suporte

Para dúvidas ou problemas:

1. Verifique a conexão com o MySQL
2. Confirme se o banco `gerenciador` foi criado
3. Verifique as credenciais em `Conexao.java`
4. Compile novamente: `mvn clean install`

---

**Projeto Concluído!** ✨
Data de Conclusão: **Março 2026**
