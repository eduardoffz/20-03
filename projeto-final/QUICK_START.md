# 🚀 INÍCIO RÁPIDO - 5 MINUTOS

## Um guia super rápido para começar!

### Passo 1: Preparar Banco de Dados (2 min)

Abra o MySQL e execute:

```sql
CREATE DATABASE gerenciador;
USE gerenciador;

-- Copie e cole todo o conteúdo do arquivo: database_setup.sql
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
