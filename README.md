# 📚 LiterAlura - Catálogo de Livros

![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

## Descrição
Aplicação de catálogo de livros que consome a API Gutendex (Project Gutenberg), armazena dados em PostgreSQL e oferece interface de console para gerenciamento.

Desenvolvido como desafio do programa Oracle Next Education (ONE) - Alura.

## 🏗️ Arquitetura Spring Boot

### Estrutura do Projeto:
```
literalura/
├── src/main/java/com/literalura/
│   ├── LiterAluraApplication.java     # Main + Console UI
│   ├── model/
│   │   ├── Author.java                # Entity (Autor)
│   │   └── Book.java                  # Entity (Livro)
│   ├── dto/
│   │   ├── GutendexResponse.java      # API Response wrapper
│   │   ├── BookData.java              # Book data from API
│   │   └── AuthorData.java            # Author data from API
│   ├── repository/
│   │   ├── BookRepository.java        # JPA Repository (Livros)
│   │   └── AuthorRepository.java      # JPA Repository (Autores)
│   └── service/
│       ├── ApiService.java            # Gutendex API client
│       └── BookService.java           # Business logic
├── src/main/resources/
│   └── application.properties         # Database config
└── pom.xml                            # Maven dependencies
```

### Camadas (Layered Architecture):
- **Model**: Entidades JPA (Author, Book)
- **DTO**: Data Transfer Objects para API
- **Repository**: Acesso ao banco de dados (Spring Data JPA)
- **Service**: Lógica de negócio
- **Application**: Interface de console (CommandLineRunner)

## 🚀 Funcionalidades

1. **Buscar livro pelo título** - Busca na API Gutendex e salva no BD
2. **Listar livros registrados** - Mostra todos os livros salvos
3. **Listar autores registrados** - Mostra todos os autores salvos
4. **Listar autores vivos em determinado ano** - Filtro por ano
5. **Listar livros em um determinado idioma** - Filtro por idioma (en, es, pt, etc.)

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.3**
- **Spring Data JPA** (Hibernate)
- **PostgreSQL 16**
- **Maven 4**
- **Jackson** (JSON parsing)
- **Gutendex API** (Project Gutenberg books)

## 📦 Pré-requisitos

- ✅ Java JDK 17 ou superior
- ✅ Maven 4 ou superior
- ✅ PostgreSQL 16 instalado e rodando
- ✅ Banco de dados `literalura` criado
- ✅ IntelliJ IDEA (opcional, mas recomendado)

## 🔧 Configuração e Execução

### Passo 1: Verificar PostgreSQL
```bash
# Testar conexão
psql -U postgres -d literalura

# Se conectar com sucesso, você está pronto
# Digite \q para sair
```

### Passo 2: Configurar application.properties
O arquivo `application.properties` já está configurado com:
- URL: `jdbc:postgresql://localhost:5432/literalura`
- Username: `postgres`
- Password: `SUA_SENHA_AQUI`

**Para que funcione corretamente**, edite a linha no arquivo:
```properties
spring.datasource.password=YOUR_PASSWORD_HERE
```

### Passo 3: Compilar o Projeto

**Opção A - IntelliJ IDEA (Recomendado):**
1. Abra o IntelliJ IDEA
2. File → Open → Selecione a pasta `literalura`
3. Aguarde o Maven baixar as dependências
4. Clique com botão direito em `LiterAluraApplication.java`
5. Run 'LiterAluraApplication'

**Opção B - Linha de Comando:**
```bash
# Navegue até a pasta do projeto
cd literalura

# Limpar e compilar
mvn clean install

# Executar
mvn spring-boot:run
```

**Opção C - JAR Executável:**
```bash
# Compilar JAR
mvn clean package

# Executar JAR
java -jar target/literalura-1.0.0.jar
```

## 🎯 Como Usar

### Exemplo de Uso:

```
===============================================
      BEM-VINDO AO LITERALURA
    Catálogo de Livros - Gutendex API
===============================================

╔════════════════════════════════════════════╗
║              MENU DE OPÇÕES                ║
╠════════════════════════════════════════════╣
║ 1 - Buscar livro pelo título              ║
║ 2 - Listar livros registrados             ║
║ 3 - Listar autores registrados            ║
║ 4 - Listar autores vivos em determinado ano║
║ 5 - Listar livros em um determinado idioma║
║ 0 - Sair                                   ║
╚════════════════════════════════════════════╝

Escolha uma opção: 1

Digite o título do livro: frankenstein

🔍 Buscando livro na API Gutendex...

✅ Livro salvo com sucesso!

----- LIVRO -----
Título: Frankenstein; Or, The Modern Prometheus
Autor: Mary Wollstonecraft Shelley
Idioma: en
Número de downloads: 67890
-----------------
```

## 📊 Estrutura do Banco de Dados

### Tabela: autores
```sql
id              BIGSERIAL PRIMARY KEY
nome            VARCHAR(255) UNIQUE
ano_nascimento  INTEGER
ano_falecimento INTEGER
```

### Tabela: livros
```sql
id               BIGSERIAL PRIMARY KEY
titulo           VARCHAR(255) UNIQUE
autor_id         BIGINT REFERENCES autores(id)
idioma           VARCHAR(10)
numero_downloads INTEGER
```

**Relacionamento:** Um autor pode ter vários livros (One-to-Many)

## ⚠️ Solução de Problemas

### Erro: "Unable to connect to database"
```
✅ Verificar se PostgreSQL está rodando
✅ Verificar se database 'literalura' existe
✅ Verificar senha no application.properties
✅ Verificar se porta 5432 está livre
```

### Erro: "Table 'livros' doesn't exist"
```
✅ spring.jpa.hibernate.ddl-auto=update está configurado
✅ O Hibernate cria as tabelas automaticamente na primeira execução
```

### Erro: "API não retorna resultados"
```
✅ Verificar conexão com internet
✅ Tentar outro título de livro
✅ A API Gutendex pode estar temporariamente indisponível
```

### Erro: "Duplicate key value violates unique constraint"
```
✅ O livro já existe no banco de dados
✅ Isso é esperado - o sistema avisa que o livro já está registrado
```

## 📚 Exemplos de Livros para Testar

- **Inglês:** "pride and prejudice", "frankenstein", "alice wonderland"
- **Espanhol:** "don quijote", "cien años"
- **Francês:** "les miserables", "comte monte cristo"
- **Português:** "dom casmurro", "memorias postumas"


## 📄 Licença
Projeto educacional - Oracle Next Education (ONE) - Alura

---

**Desenvolvido para o Challenge LiterAlura**
