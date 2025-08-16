# README - Word Analysis App

## Descrição do Projeto
Este projeto é uma aplicação web  para análise de frases digitadas pelo usuário. O sistema recebe uma frase, analisa a quantidade de palavras distintas e o número de ocorrências de cada palavra (ignorando pontuação, números e convertendo para lowercase), persiste os resultados no banco de dados e exibe o histórico de análises. A aplicação foi desenvolvida como teste técnico para a Prover Tech, com foco em simplicidade, eficiência no processamento e clareza na apresentação dos resultados.

### Objetivo
Desenvolver um sistema web que recebe uma frase digitada pelo usuário e retorna a quantidade de palavras distintas e a quantidade de ocorrências de cada palavra encontrada na frase.

### Requisitos Funcionais
- Interface Gráfica:
    - Deve permitir a entrada de uma frase pelo usuário.
    - Deve possuir um botão ou mecanismo para disparar a análise da frase.
    - Após a análise, mostrar a quantidade de palavras distintas e a quantidade de ocorrências de cada palavra.
- Backend:
    - O motor de análise deve ser capaz de processar apenas uma requisição por vez, de forma sincronizada.
    - A análise deve ser realizada no backend, calculando a quantidade de palavras distintas.
    - A quantidade de ocorrências de cada palavra.
    - O resultado deve ser enviado de volta para a interface gráfica.
    - A interface gráfica exibe o resultado para o usuário.
- Fluxo de Operação:
    1. O usuário insere uma frase na interface gráfica.
    2. O usuário aciona o disparo da análise.
    3. A frase é enviada para o backend.
    4. O backend realiza a análise, calculando:
    5. A quantidade de palavras distintas.
    6. A quantidade de ocorrências de cada palavra.
    7. O resultado é enviado de volta para a interface gráfica.
    8. A interface gráfica exibe o resultado para o usuário.
- Este sistema deve ser desenvolvido com foco na simplicidade de uso, eficiência no processamento da análise e clareza na apresentação dos resultados.

### Premissas
- A comunicação entre interface e backend deve ser feita utilizando binding de componentes para beans (EL).
- O sistema deve utilizar maven e ser compatível para execução em WildFly na versão 10.
- O código-fonte dever ser disponibilizado em um repositório GIT de sua escolha (BitBucket, GitHub, GitLab, etc).
- O sistema deve ter compatibilidade com Chrome e Edge.

### Stack Tecnológica
- Java 8 (Obrigatório)
- JSF (Obrigatório)
- PrimeFaces ou RichFaces (Obrigatório)
- CDI
- JPA + Hibernate

### Configurações Principais
- **Servidor**: WildFly 10.1.0.Final, configurado via `standalone.xml` para JPA com H2 database, JSF, CDI e Undertow.
- **Maven (`pom.xml`)**: Dependências para `javaee-api:7.0`, `primefaces:6.0`, `h2:1.4.200`, `gson:2.8.0`, `junit:4.12`, `mockito-core:1.10.19`, `cdi-unit:4.0.0` (provided), `hibernate-testing:5.2.10.Final` (provided).
- **JPA (`persistence.xml`)**: Unidade de persistência `wordAnalysisPU` com datasource `java:/jboss/datasources/ExampleDS`, modo `update` para schema.
- **JSF (`web.xml`)**: FacesServlet mapeado para `.xhtml`, welcome-file `index.xhtml`.
- **Bean**: `AnalysisBean` para lógica de análise, persistência e histórico.
- **Entidade**: `AnalysisResult` para persistir resultados (frase, palavras distintas, ocorrências como JSON).
- **DAO**: `AnalysisResultDAO` para operações de banco (persist e findAll).
- **Testes**: `AnalysisBeanTest` com Mockito para validar análise, persistência e carregamento do histórico.

### Como Buildar e Executar
1. **Requisitos**:
    - Java 8 JDK.
    - Maven 3.x.
    - WildFly 10.1.0.Final (baixe e configure em `WILDFLY_HOME`).

2. **Build**:
    - Clone o repositório: `git clone <repo-url>`.
    - Vá para o diretório do projeto: `cd word-analysis`.
    - Execute `mvn clean install` para buildar o `.war` (gerado em `target/word-analysis.war`).

3. **Deploy**:
    - Copie `target/word-analysis.war` para `WILDFLY_HOME/standalone/deployments/`.
    - Inicie o WildFly: `WILDFLY_HOME/bin/standalone.bat` (Windows) ou `./standalone.sh` (Unix).

4. **Acessar**:
    - URL: `http://localhost:8080/word-analysis/`.
    - Insira uma frase, clique em "Analisar" e verifique os resultados e histórico.

### Notas de Desenvolvimento
- A aplicação usa JSF com PrimeFaces para a interface, JPA (Hibernate) para persistência em H2 in-memory, CDI para injeção.
- O histórico é carregado do banco em cada análise para refletir todos os resultados persistidos.
- Testes unitários cobrem cenários de sucesso, frase vazia, carregamento do histórico e falha no DAO.
