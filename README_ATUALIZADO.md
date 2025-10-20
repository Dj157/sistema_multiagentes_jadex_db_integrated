
---

## Arquitetura do Sistema

| Camada | Função | Tecnologias | Comunicação |
|---------|---------|--------------|--------------|
| Agentes Inteligentes (Jadex) | Simulam e analisam dados de saúde mental | Jadex BDI + Java | Acesso direto ao banco |
| Banco de Dados (Persistência) | Armazena dados de sensores, análises e recomendações | H2 (dev) / PostgreSQL (prod) | JDBC (HikariCP) |
| API REST (Backend) | Expõe endpoints para frontend | Spring Boot + Maven | HTTP (porta 8080) |
| Dashboard Web (Frontend) | Visualiza dados e análises | React + Vite + Tailwind + Recharts | Requisições HTTP |

---

## Arquitetura Multiagente (Jadex)

Os agentes seguem o modelo **BDI (Belief–Desire–Intention)**, cada um com crenças, objetivos e planos próprios.

| Agente | Tipo | Função | Comunicação |
|---------|------|---------|-------------|
| Agente de Coleta de Dados | Sensor / Produtor | Simula sinais de saúde (sono, humor, atividade, FC) e grava no banco | `DatabaseManager.inserirDadosSaude()` |
| Agente Analisador Emocional | Processador / Avaliador | Avalia dados recentes e classifica risco emocional (baixo/médio/alto) | `DatabaseManager.buscarDadosSaudeRecentes()` |
| Agente de Recomendação | Atuador / Orientador | Gera recomendações personalizadas | `DatabaseManager.inserirRecomendacao()` |

---

## Sensores e Atuadores

| Categoria | Elemento | Tipo | Função | Origem/Destino |
|------------|-----------|------|---------|----------------|
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
|---------|------------|
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

```bash
cd /workspaces/sistema_multiagentes_jadex_db_integrated
mvn exec:java
Executa os agentes (com.unieuro.Main).

2. Iniciar a API REST (Spring Boot)
cd /workspaces/sistema_multiagentes_jadex_db_integrated
mvn spring-boot:run


Acesse: http://localhost:8080

3. Iniciar o Dashboard Web (React)
cd /workspaces/sistema_multiagentes_jadex_db_integrated/health-dashboard
npm install
npm run dev


Acesse: http://localhost:5173

Tecnologias Utilizadas
Camada	Tecnologias
Agentes	Jadex BDI Framework, Java
Backend	Spring Boot, Maven, HikariCP
Banco de Dados	H2 (dev) / PostgreSQL (prod)
Frontend	React, Vite, TailwindCSS, Recharts
Integração	JDBC, REST API, JSON
Repositório

Clone o repositório e explore o código:

git clone https://github.com/usuario/sistema_multiagentes_jadex_db_integrat
