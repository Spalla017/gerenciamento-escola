# 📚 Sistema de Gerenciamento de Cursos e Alunos

> Aplicação web desenvolvida em Java EE para gerenciamento acadêmico de cursos e alunos.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JSF](https://img.shields.io/badge/JSF-2.3-orange?style=for-the-badge)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)

## 📋 Sobre o Projeto

Sistema web desenvolvido como trabalho avaliativo da disciplina de **Programação Orientada a Objetos Aplicada** do curso de **Sistemas de Informação**. 

O projeto permite o gerenciamento completo de cursos e alunos, incluindo cadastro, edição, exclusão e geração de relatórios com filtros avançados.

### ✨ Funcionalidades Principais

- 🎓 **Gerenciamento de Cursos**
  - Cadastro, edição e exclusão de cursos
  - Listagem ordenada por área
  - Filtro de busca por nome
  - Validação de exclusão (impede deletar curso com alunos vinculados)

- 👨‍🎓 **Gerenciamento de Alunos**
  - Cadastro completo com dados pessoais e endereço
  - Edição de dados (CPF não editável)
  - Exclusão de registros
  - Listagem ordenada por nome
  - Formatação automática de CPF
  - Vinculação com cursos

- 📊 **Relatórios Avançados**
  - Filtros combinados: curso, maioridade e cidade
  - Busca por cidade com LIKE
  - Filtro por maior/menor de idade (18 anos)
  - Ordenação por nome do aluno

## 🚀 Tecnologias Utilizadas

### Backend
- **Java EE 8** - Plataforma enterprise
- **JSF 2.3** (JavaServer Faces) - Framework MVC para interface web
- **JPA** (Java Persistence API) - Especificação de persistência
- **Hibernate** - Implementação JPA (ORM)
- **EJB** (Enterprise Java Beans) - Componentes de negócio
- **JPA Criteria API** - Consultas type-safe

### Servidor & Banco de Dados
- **WildFly 20.0.1** - Servidor de aplicação
- **MySQL 5.x** - Sistema de gerenciamento de banco de dados

### IDE & Ferramentas
- **Eclipse IDE for Java EE Developers (2020-06)**
- **Maven** - Gerenciamento de dependências
- **Git** - Controle de versão

## 🏗️ Arquitetura do Projeto

O projeto segue o padrão **MVC (Model-View-Controller)** com separação em camadas:

```
📦 GerenciamentoEscola
 ┣ 📂 src
 ┃ ┣ 📂 modelo (Model - Entidades JPA)
 ┃ ┃ ┣ 📜 Aluno.java
 ┃ ┃ ┣ 📜 Curso.java
 ┃ ┃ ┗ 📜 Endereco.java
 ┃ ┃
 ┃ ┣ 📂 service (Camada de Negócio)
 ┃ ┃ ┣ 📜 GenericService.java
 ┃ ┃ ┣ 📜 AlunoService.java
 ┃ ┃ ┗ 📜 CursoService.java
 ┃ ┃
 ┃ ┣ 📂 controle (Controller - Managed Beans)
 ┃ ┃ ┣ 📜 AlunoBean.java
 ┃ ┃ ┣ 📜 CursoBean.java
 ┃ ┃ ┗ 📜 RelatorioBean.java
 ┃ ┃
 ┃ ┗ 📂 META-INF
 ┃   ┗ 📜 persistence.xml
 ┃
 ┗ 📂 WebContent (View - Interface)
   ┣ 📂 WEB-INF
   ┃ ┣ 📜 beans.xml
   ┃ ┣ 📜 faces-config.xml
   ┃ ┗ 📜 web.xml
   ┣ 📜 index.xhtml
   ┣ 📜 curso.xhtml
   ┣ 📜 aluno.xhtml
   ┗ 📜 relatorio.xhtml
```

### 📐 Diagrama de Classes

```
┌─────────────┐         ┌─────────────┐         ┌─────────────┐
│   Curso     │         │    Aluno    │         │  Endereco   │
├─────────────┤         ├─────────────┤         ├─────────────┤
│ - id        │◄────┐   │ - id        │         │ - id        │
│ - nome      │     │   │ - nome      │         │ - rua       │
│ - area      │     └───│ - curso     │────────►│ - numero    │
│ - duracao   │    0..* │ - idade     │    1    │ - bairro    │
└─────────────┘         │ - cpf       │         │ - cidade    │
                        │ - email     │         └─────────────┘
                        │ - endereco  │
                        └─────────────┘
```

## 🔧 Pré-requisitos

Antes de começar, você precisa ter instalado:

- [Java JDK 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) ou superior
- [Eclipse IDE for Java EE Developers](https://www.eclipse.org/downloads/packages/)
- [WildFly 20.0.1](https://www.wildfly.org/downloads/)
- [MySQL 5.x](https://dev.mysql.com/downloads/mysql/) ou superior
- [Git](https://git-scm.com/downloads)

## 📥 Instalação e Configuração

### 1️⃣ Clonar o Repositório

```bash
git clone https://github.com/Spalla017/gerenciamento-escola.git
cd gerenciamento-escola
```

### 2️⃣ Configurar o Banco de Dados

Abra o MySQL e execute:

```sql
CREATE DATABASE gerenciamentoescola;
```

### 3️⃣ Configurar o WildFly

1. Adicione o driver MySQL ao WildFly:
   - Acesse: `http://localhost:8080`
   - Vá em **Administration Console**
   - Deploy → Add → Selecione `mysql-connector-java-8.0.26.jar`

2. Crie o DataSource:
   - Configuration → Subsystems → Datasources & Drivers
   - Add → MySQL
   - **Name:** `GerenciamentoEscolaDS`
   - **JNDI Name:** `java:/GerenciamentoEscolaDS`
   - **Connection URL:** `jdbc:mysql://localhost:3306/gerenciamentoescola`
   - **Username:** `root`
   - **Password:** `sua_senha`
   - Teste a conexão e salve

### 4️⃣ Importar no Eclipse

1. Abra o Eclipse
2. File → Import → Existing Projects into Workspace
3. Selecione a pasta do projeto clonado
4. Finish

### 5️⃣ Executar o Projeto

1. Clique com botão direito no projeto
2. Run As → Run on Server
3. Selecione WildFly 20.0.1
4. Acesse: `http://localhost:8080/GerenciamentoEscola/`

## 📖 Como Usar

### Tela Inicial
Ao acessar o sistema, você verá três opções:
- **Gerenciar Cursos** - CRUD de cursos
- **Gerenciar Alunos** - CRUD de alunos
- **Relatórios de Alunos** - Filtros e consultas

### Cadastrar um Curso
1. Clique em "Gerenciar Cursos"
2. Preencha: Nome, Área e Duração (em meses)
3. Clique em "Gravar"

### Cadastrar um Aluno
1. Clique em "Gerenciar Alunos"
2. Preencha os dados pessoais (Nome, Idade, CPF, Email)
3. Selecione um curso
4. Preencha o endereço completo
5. Clique em "Gravar"

### Gerar Relatórios
1. Clique em "Relatórios de Alunos"
2. Selecione os filtros desejados:
   - Curso específico ou todos
   - Maior/menor de idade
   - Cidade (busca parcial)
3. Clique em "Filtrar"

## 🎯 Requisitos Implementados

- [x] Modelagem e mapeamento de entidades JPA
- [x] Tela inicial com navegação
- [x] CRUD completo de Cursos
- [x] CRUD completo de Alunos
- [x] Relatórios com filtros combinados
- [x] Validação de campos obrigatórios
- [x] Formatação de CPF
- [x] Ordenação de listagens
- [x] Uso de JPA Criteria API
- [x] Relacionamentos entre entidades
- [x] Impedimento de exclusão com dependências

## 🧪 Testando o Sistema

### Dados de Teste

**Curso 1:**
- Nome: Engenharia de Software
- Área: Tecnologia
- Duração: 48 meses

**Aluno 1:**
- Nome: João Silva
- Idade: 20
- CPF: 12345678901
- Email: joao@email.com
- Curso: Engenharia de Software
- Endereço: Rua A, 123, Centro, São Paulo

## 👥 Autores

- **[Vinicius Spalla Silva]** - *Desenvolvimento* - [Spalla017](https://github.com/Spalla017)
- **[Victor Hugo Oliveira dos Santos]** - *Desenvolvimento* - [Victor1302](https://github.com/victor1302)

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos como parte do trabalho da disciplina de Programação Orientada a Objetos Aplicada.
---

**Desenvolvido com ☕ e 💻 por estudantes de Sistemas de Informação**
