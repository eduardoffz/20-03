# 🏗️ ARQUITETURA DO PROJETO

## Diagrama MVC

```
┌─────────────────────────────────────────────────────────────────┐
│                      VISTA (View Layer)                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────────┐   │
│  │   Login      │  │  Cadastro    │  │  Inicio (Dashboard)  │   │
│  │              │  │              │  │                      │   │
│  │ • JTextField │  │ • JTextField │  │ • JTable (tarefas)  │   │
│  │ • Botão      │  │ • Botão      │  │ • 4 Botões CRUD     │   │
│  └──────────────┘  └──────────────┘  └──────────────────────┘   │
│         │                │                        │               │
│         └────────────────┼────────────────────────┘               │
│                          │                                         │
│                    ┌─────────────────────────┐                   │
│                    │ GerenciadorTarefas      │                   │
│                    │                         │                   │
│                    │ • JTextField (Título)   │                   │
│                    │ • JTextArea (Descrição) │                   │
│                    │ • JDateChooser (Data)   │                   │
│                    │ • JComboBox (Status)    │                   │
│                    │ • JComboBox (Prioridade)│                   │
│                    │ • Botões Salvar         │                   │
│                    └─────────────────────────┘                   │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                    MODELO (Model Layer)                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  ┌──────────────────────────┐  ┌──────────────────────────┐     │
│  │   UsuarioBean            │  │   TarefaBean             │     │
│  │                          │  │                          │     │
│  │ - id: int                │  │ - id: int                │     │
│  │ - nome: String           │  │ - titulo: String         │     │
│  │ - usuario: String        │  │ - descricao: String      │     │
│  │ - senha: String          │  │ - dataCriacao: LocalDT   │     │
│  │ - admin: boolean         │  │ - dataVencimento: LocalD │     │
│  │                          │  │ - status: String         │     │
│  │ + getters/setters        │  │ - prioridade: String     │     │
│  │                          │  │ - idUsuario: int         │     │
│  └──────────────────────────┘  │                          │     │
│                                 │ + getters/setters        │     │
│                                 └──────────────────────────┘     │
│                                                                   │
│             [Possível] ProdutoBean, ProdutoDAO                  │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                    DAO (Data Access Layer)                       │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  ┌──────────────────────────┐  ┌──────────────────────────┐     │
│  │   UsuarioDAO             │  │   TarefaDAO              │     │
│  │                          │  │                          │     │
│  │ + cadastrar()            │  │ + criar()                │     │
│  │ + logar()                │  │ + listar()               │     │
│  │                          │  │ + listarPorUsuario()     │     │
│  │ [Métodos completos]      │  │ + atualizar()            │     │
│  │                          │  │ + deletar()              │     │
│  │                          │  │ + obterPorId()           │     │
│  │                          │  │                          │     │
│  │ [CRUD: 2 métodos]        │  │ [CRUD: 6 métodos]        │     │
│  └──────────────────────────┘  └──────────────────────────┘     │
│                                                                   │
│             [Possível] ProdutoDAO (somente read)                 │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                   CONEXÃO (Connection Layer)                     │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │   Conexao.java                                           │   │
│  │                                                          │   │
│  │   URL: jdbc:mysql://localhost:3306/gerenciador         │   │
│  │   USER: root                                            │   │
│  │   SENHA: 1234                                           │   │
│  │                                                          │   │
│  │   + conectar(): Connection                              │   │
│  │   + testarConexao(): void                               │   │
│  │                                                          │   │
│  └──────────────────────────────────────────────────────────┘   │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                   BANCO DE DADOS (MySQL)                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  Database: gerenciador                                          │
│  ├─ Tabela: usuarios                                            │
│  │  ├─ id (PK)                                                  │
│  │  ├─ nome                                                     │
│  │  ├─ usuario (UNIQUE)                                         │
│  │  ├─ senha                                                    │
│  │  └─ admin                                                    │
│  │                                                              │
│  └─ Tabela: tarefas                                             │
│     ├─ id (PK)                                                  │
│     ├─ titulo                                                   │
│     ├─ descricao                                                │
│     ├─ data_criacao                                             │
│     ├─ data_vencimento                                          │
│     ├─ status                                                   │
│     ├─ prioridade                                               │
│     └─ id_usuario (FK → usuarios.id)                            │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
```

---

## Fluxo de Dados

### 1️⃣ LOGIN

```
Login.java
  ↓
[Usuário digita credenciais]
  ↓
UsuarioDAO.logar(usuario, senha)
  ↓
[Query: SELECT FROM usuarios WHERE usuario=? AND senha=?]
  ↓
UsuarioBean (objeto preenchido ou vazio)
  ↓
[Se válido] → Início.java(usuarioLogado)
[Se inválido] → Mensagem de erro
```

### 2️⃣ LISTAR TAREFAS

```
Inicio.java (construtor)
  ↓
PreencherTabela()
  ↓
TarefaDAO.listarPorUsuario(usuarioLogado.getId())
  ↓
[Query: SELECT FROM tarefas WHERE id_usuario=?]
  ↓
List<TarefaBean>
  ↓
DefaultTableModel.addRow() para cada tarefa
  ↓
JTable exibe dados
```

### 3️⃣ CRIAR TAREFA

```
btnNovaTarefa.onClick()
  ↓
GerenciadorTarefas.new()
  ↓
[Usuário preenche formulário]
  ↓
btnSalvar.onClick()
  ↓
[Validações: titulo != null, dataVencimento != null]
  ↓
TarefaBean.setTitulo(), setDescricao(), etc.
  ↓
TarefaDAO.criar(TarefaBean)
  ↓
[Query: INSERT INTO tarefas VALUES(...)]
  ↓
JOptionPane: "Sucessso!"
  ↓
Inicio.PreencherTabela() [atualiza tabela]
```

### 4️⃣ EDITAR TAREFA

```
tableTarefas.getSelectedRow()
  ↓
btnEditar.onClick()
  ↓
[Se seleção vazia] → Mensagem de aviso
  ↓
GerenciadorTarefas.new(usuarioLogado, telaInicio, tarefaId)
  ↓
TarefaDAO.obterPorId(tarefaId)
  ↓
[Query: SELECT FROM tarefas WHERE id=?]
  ↓
[Campos preenchidos com dados atuais]
  ↓
[Usuário edita campos]
  ↓
btnSalvar.onClick()
  ↓
TarefaDAO.atualizar(TarefaBean)
  ↓
[Query: UPDATE tarefas SET ... WHERE id=?]
  ↓
Inicio.PreencherTabela() [atualiza tabela]
```

### 5️⃣ DELETAR TAREFA

```
tableTarefas.getSelectedRow()
  ↓
btnDeletar.onClick()
  ↓
[Se seleção vazia] → Mensagem de aviso
  ↓
JOptionPane.showConfirmDialog()
  ↓
[Se SIM]
  ↓
TarefaDAO.deletar(tarefaId)
  ↓
[Query: DELETE FROM tarefas WHERE id=?]
  ↓
JOptionPane: "Deletado!"
  ↓
Inicio.PreencherTabela() [atualiza tabela]
```

---

## Relacionamento Entre Classes

```
┌─────────────────────────────────────────────────┐
│        CLASSES E RELACIONAMENTOS                │
└─────────────────────────────────────────────────┘

Login ──┬──→ UsuarioDAO ──→ UsuarioBean
        │                      ↓
        │                   Conexao
        │                      ↓
        │                   MySQL
        │
        └──→ Cadastro ────→ UsuarioDAO

Inicio ──┬──→ TarefaDAO ───→ TarefaBean
         │                      ↓
         │                   Conexao
         │                      ↓
         │                   MySQL
         │
         └──→ GerenciadorTarefas
               ↓
              TarefaDAO


Todos usam:
  Conexao.java (Connection Manager)
  Swing Components (JFrame, JTable, etc.)
```

---

## Dependências do Projeto

```
java 23+
  ├─ java.sql (JDBC)
  ├─ javax.swing (Swing GUI)
  └─ java.time (LocalDate, LocalDateTime)

Maven Dependencies:
  ├─ mysql-connector-java (8.0.33)
  │  └─ Conectar ao MySQL
  │
  ├─ jcalendar (1.4)
  │  ├─ Componente: JDateChooser
  │  └─ Seletor visual de datas
  │
  └─ AbsoluteLayout (RELEASE240)
     └─ Layout absoluto do NetBeans
```

---

## Padrões de Design Utilizados

### 🏛️ MVC (Model-View-Controller)

```
View        → Login, Cadastro, Inicio, GerenciadorTarefas
Model       → UsuarioBean, TarefaBean
Controller  → UsuarioDAO, TarefaDAO
```

### 📊 DAO (Data Access Object)

```
Abstração de acesso a dados
UserDAO e TarefaDAO encapsulam SQL
```

### 🍃 Bean Pattern (POJO)

```
Objetos simples com getters/setters
UsuarioBean, TarefaBean
```

### 🔌 Singleton (Implícito)

```
Conexao.conectar() sempre retorna nova connection
(Pode ser melhorado com ConnectionPool)
```

---

## Fluxo Visual Completo

```
                    ┌─────────────┐
                    │   START     │
                    └──────┬──────┘
                           │
                    ┌──────▼──────┐
                    │  Login.java │
                    └──────┬──────┘
                           │
                    ┌──────▼──────────────┐
                    │ UsuarioDAO.logar()  │
                    └──────┬──────────────┘
                           │
                    ┌──────▼──────┐
                    │ Válido?     │
                    └──┬───────┬──┘
                      SIM     NÃO
                       │       │
                       │    [Erro]
                       │
                 ┌─────▼─────────────┐
                 │ Inicio.java       │
                 │ (Dashboard)       │
                 └─────┬─────────────┘
                       │
         ┌─────────────┼─────────────┐
         │             │             │
    ┌────▼────┐  ┌────▼────┐  ┌────▼────┐
    │ Novo    │  │ Editar  │  │ Deletar │
    └────┬────┘  └────┬────┘  └────┬────┘
         │            │            │
    ┌────▼─────────────▼─────────────▼────┐
    │   GerenciadorTarefas.java (CRUD)     │
    └────┬─────────────────────────────────┘
         │
    ┌────▼─────────────┐
    │ TarefaDAO.java   │
    │ • criar()        │
    │ • atualizar()    │
    │ • deletar()      │
    └────┬─────────────┘
         │
    ┌────▼──────────┐
    │  MySQL.java   │
    └────┬──────────┘
         │
    ┌────▼──────────┐
    │ OK / ERROR    │
    └───────────────┘
```

---

## Matriz de Responsabilidades

| Classe             | Responsabilidade     | Dependências         |
| ------------------ | -------------------- | -------------------- |
| Login              | Autenticar usuário   | UsuarioDAO, Conexao  |
| Cadastro           | Criar novo usuário   | UsuarioDAO, Conexao  |
| Inicio             | Listar tarefas       | TarefaDAO, Conexao   |
| GerenciadorTarefas | Criar/Editar tarefa  | TarefaDAO, Conexao   |
| UsuarioBean        | Dados de usuário     | -                    |
| TarefaBean         | Dados de tarefa      | -                    |
| UsuarioDAO         | Acesso dados usuário | Conexao, UsuarioBean |
| TarefaDAO          | Acesso dados tarefa  | Conexao, TarefaBean  |
| Conexao            | Conectar ao banco    | MySQL                |

---

**Arquitetura limpa, escalável e profissional! ✨**
