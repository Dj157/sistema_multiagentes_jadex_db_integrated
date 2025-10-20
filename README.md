# Sistema Multiagente Integrado para Monitoramento Emocional

Um sistema distribuído de análise e recomendação emocional, baseado em agentes cognitivos **Jadex (BDI)**, com integração a banco de dados, **API REST Spring Boot** e **dashboard web em React**.  
O projeto simula o comportamento de sensores virtuais (sono, humor, atividade e frequência cardíaca) para monitorar o bem-estar de idosos e gerar recomendações automáticas personalizadas.

---

## Sumário

1. [Visão Geral](#visão-geral)  
2. [Arquitetura do Sistema](#arquitetura-do-sistema)  
3. [Arquitetura Multiagente (Jadex)](#arquitetura-multiagente-jadex)  
4. [Sensores e Atuadores](#sensores-e-atuadores)  
5. [Banco de Dados](#banco-de-dados)  
6. [Interface Web (Dashboard)](#interface-web-dashboard)  
7. [Instruções de Execução](#instruções-de-execução)  
8. [Tecnologias Utilizadas](#tecnologias-utilizadas)  

---

## Visão Geral

O sistema foi projetado para **simular, analisar e recomendar intervenções emocionais** em um contexto de monitoramento de saúde mental.  
Ele é composto por três agentes inteligentes Jadex, integrados a um banco de dados relacional e a uma interface web interativa.

### Fluxo Geral do Sistema

[Sensores Simulados]
↓
[Agente de Coleta de Dados]
↓ (insere)
[Banco de Dados H2/PostgreSQL]
↓ (consulta)
[Agente Analisador Emocional]
↓ (gera)
[Agente de Recomendação]
↓ (exposição via API REST)
[Dashboard Web (React)]

yaml
Copy code

---

## Arquitetura do Sistema

| Camada | Função | Tecnologias | Comunicação |
|--------|--------|------------|-------------|
| Agentes Inteligentes (Jadex) | Simulam e analisam dados de saúde mental | Jadex BDI + Java | Acesso direto ao banco |
| Banco de Dados (Persistência) | Armazena dados de sensores, análises e recomendações | H2 (dev) / PostgreSQL (prod) | JDBC (HikariCP) |
| API REST (Backend) | Expõe endpoints para frontend | Spring Boot + Maven | HTTP (porta 8080) |
| Dashboard Web (Frontend) | Visualiza dados e análises | React + Vite + Tailwind + Recharts | Requisições HTTP |

---

## Arquitetura Multiagente (Jadex)

Os agentes seguem o modelo **BDI (Belief–Desire–Intention)**, cada um com crenças, objetivos e planos próprios.

| Agente | Tipo | Função | Comunicação |
|--------|------|--------|-------------|
| Agente de Coleta de Dados | Sensor / Produtor | Simula sinais de saúde (sono, humor, atividade, FC) e grava no banco | `DatabaseManager.inserirDadosSaude()` |
| Agente Analisador Emocional | Processador / Avaliador | Avalia dados recentes e classifica risco emocional (baixo/médio/alto) | `DatabaseManager.buscarDadosSaudeRecentes()` |
| Agente de Recomendação | Atuador / Orientador | Gera recomendações personalizadas | `DatabaseManager.inserirRecomendacao()` |

---

## Sensores e Atuadores

| Categoria | Elemento | Tipo | Função | Origem/Destino |
|-----------|----------|------|--------|----------------|
| Sensor | Sono (horas e qualidade) | Virtual | Mede descanso e recuperação emocional | Agente de Coleta |
| Sensor | Humor | Virtual | Mede estado afetivo | Agente de Coleta |
| Sensor | Atividade Física | Virtual | Indica movimento e engajamento | Agente de Coleta |
| Sensor | Frequência Cardíaca | Virtual | Indica estresse e saúde física | Agente de Coleta |
| Atuador | Análise Emocional | Lógico | Determina nível de risco emocional | Agente Analisador → Banco |
| Atuador | Recomendações | Cognitivo | Gera ações preventivas e orientações | Agente de Recomendação → Banco/API |
| Atuador | Visualização | Interativo | Exibe dados e alertas | API REST → Frontend |

---

## Banco de Dados

O módulo `DatabaseManager.java` centraliza toda a persistência de dados, integrando os agentes e a API REST.

### Principais Tabelas

| Tabela | Descrição |
|--------|-----------|
| `idosos` | Cadastro dos usuários monitorados |
| `dados_saude` | Dados simulados dos sensores |
| `analises_emocionais` | Classificações de risco |
| `recomendacoes` | Sugestões personalizadas |

### Funções Principais

- `inserirDadosSaude()` → grava dados dos sensores  
- `buscarDadosSaudeRecentes()` → consulta últimas medições  
- `inserirAnaliseEmocional()` → registra o risco detectado  
- `inserirRecomendacao()` → salva recomendações  
- `listarIdosos()` → fornece dados para a API REST  

---

## Interface Web (Dashboard)

A interface web foi desenvolvida em **React + Vite**, com foco em clareza e interatividade.  
Ela exibe os dados dos idosos, gráficos de variação emocional e recomendações geradas pelos agentes.

### Principais Funcionalidades

- Visualização de métricas (humor, sono, atividade, FC)  
- Classificação de risco emocional em tempo real  
- Exibição de recomendações personalizadas  
- Atualização automática via API REST  

---
## Instruções de Execução

### 1. Executar o Sistema Multiagente (Jadex)

Abra o terminal e execute os comandos abaixo:

```bash
cd /workspaces/sistema_multiagentes_jadex_db_integrated
mvn exec:java
Aguarde os agentes Jadex (com.unieuro.Main) iniciarem.
Mantenha o terminal aberto para visualizar os logs em tempo real.

2. Iniciar a API REST (Spring Boot)
Abra outro terminal e execute:

bash
Copy code
cd /workspaces/sistema_multiagentes_jadex_db_integrated
mvn spring-boot:run
A API será iniciada na porta padrão 8080.
Acesse pelo navegador: http://localhost:8080

3. Iniciar o Dashboard Web (React)
Abra outro terminal e execute:

bash
Copy code
cd /workspaces/sistema_multiagentes_jadex_db_integrated/health-dashboard
npm install
npm run dev
O servidor do dashboard Vite será iniciado.
Acesse pelo navegador: http://localhost:5173

Tecnologias Utilizadas
Camada	Tecnologias
Agentes	Jadex BDI Framework, Java
Backend	Spring Boot, Maven, HikariCP
Banco de Dados	H2 (dev) / PostgreSQL (prod)
Frontend	React, Vite, TailwindCSS, Recharts
Integração	JDBC, REST API, JSON

Repositório
Clone o repositório e explore o código:

bash
Copy code
git clone https://github.com/usuario/sistema_multiagentes_jadex_db_integrated.git
