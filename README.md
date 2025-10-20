Sistema Multiagente Integrado para Monitoramento Emocional

Um sistema distribuído de análise e recomendação emocional, baseado em agentes cognitivos Jadex (BDI), com integração a banco de dados, API REST (Spring Boot) e dashboard web (React).
O projeto simula sensores virtuais (sono, humor, atividade e frequência cardíaca) para monitorar o bem-estar de idosos e gerar recomendações automáticas personalizadas.

Sumário

Visão Geral

Arquitetura do Sistema

Arquitetura Multiagente (Jadex)

Sensores e Atuadores

Banco de Dados

Interface Web (Dashboard)

Instruções de Execução

Tecnologias Utilizadas

1. Visão Geral

O sistema foi projetado para simular, analisar e recomendar intervenções emocionais em um contexto de monitoramento de saúde mental.
É composto por três agentes inteligentes Jadex integrados a um banco de dados relacional e a uma interface web interativa.

Fluxo geral:

[Sensores simulados]
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

2. Arquitetura do Sistema
Camada	Função	Tecnologias	Comunicação
Agentes Inteligentes (Jadex)	Simulam e analisam dados de saúde mental	Jadex BDI + Java	Acesso direto ao banco
Banco de Dados (Persistência)	Armazena dados e resultados dos agentes	H2 (dev) / PostgreSQL (prod)	JDBC (HikariCP)
API REST (Backend)	Expõe endpoints para o frontend	Spring Boot + Maven	HTTP (porta 8080)
Dashboard Web (Frontend)	Visualiza dados e análises	React + Vite + Tailwind + Recharts	HTTP (porta 5173)
3. Arquitetura Multiagente (Jadex)

Os agentes seguem o modelo BDI (Belief–Desire–Intention), cada um com crenças, objetivos e planos próprios.

Agente	Tipo	Função	Comunicação
Agente de Coleta de Dados	Sensor / Produtor	Simula sinais de saúde (sono, humor, atividade, FC) e grava no banco	DatabaseManager.inserirDadosSaude()
Agente Analisador Emocional	Processador / Avaliador	Avalia dados recentes e classifica risco emocional (baixo/médio/alto)	DatabaseManager.buscarDadosSaudeRecentes()
Agente de Recomendação	Atuador / Orientador	Gera recomendações personalizadas com base na análise emocional	DatabaseManager.inserirRecomendacao()
4. Sensores e Atuadores
Categoria	Elemento	Tipo	Função	Origem/Destino
Sensor	Sono (horas e qualidade)	Virtual	Mede descanso e recuperação emocional	Agente de Coleta
Sensor	Humor	Virtual	Mede estado afetivo	Agente de Coleta
Sensor	Atividade Física	Virtual	Indica movimento e engajamento	Agente de Coleta
Sensor	Frequência Cardíaca	Virtual	Indica estresse e saúde física	Agente de Coleta
Atuador	Análise Emocional	Lógico	Determina nível de risco emocional	Agente Analisador → Banco
Atuador	Recomendações	Cognitivo	Gera ações preventivas e orientações	Agente de Recomendação → Banco/API
Atuador	Visualização	Interativo	Exibe dados e alertas	API REST → Frontend
5. Banco de Dados

O módulo DatabaseManager.java centraliza toda a persistência de dados, integrando os agentes e a API REST.

Principais tabelas:

Tabela	Descrição
idosos	Cadastro dos usuários monitorados
dados_saude	Dados simulados dos sensores
analises_emocionais	Classificações de risco
recomendacoes	Sugestões personalizadas

Funções principais:

inserirDadosSaude() → grava dados dos sensores

buscarDadosSaudeRecentes() → consulta últimas medições

inserirAnaliseEmocional() → registra o risco detectado

inserirRecomendacao() → salva recomendações

listarIdosos() → fornece dados para a API REST

6. Interface Web (Dashboard)

A interface web foi desenvolvida em React + Vite, com foco em clareza e interatividade.
Ela exibe os dados dos idosos, gráficos de variação emocional e recomendações geradas pelos agentes.

Principais funcionalidades:

Visualização de métricas (humor, sono, atividade, frequência cardíaca)

Classificação de risco emocional em tempo real

Exibição de recomendações personalizadas

Atualização automática via API REST

7. Instruções de Execução
Terminal 1 — Sistema Multiagente (Jadex)
cd /workspaces/sistema_multiagentes_jadex_db_integrated
mvn exec:java


Executa os agentes (com.unieuro.Main).

Terminal 2 — API REST (Spring Boot)
cd /workspaces/sistema_multiagentes_jadex_db_integrated
mvn spring-boot:run


Inicia a API REST na porta 8080.
Acesse em: http://localhost:8080

Terminal 3 — Dashboard Web (React)
cd /workspaces/sistema_multiagentes_jadex_db_integrated/health-dashboard
npm install
npm run dev


Inicia o servidor Vite na porta 5173.
Acesse em: http://localhost:5173

8. Tecnologias Utilizadas
Camada	Tecnologias
Agentes	Jadex BDI Framework, Java
Backend	Spring Boot, Maven, HikariCP
Banco de Dados	H2 (dev), PostgreSQL (prod)
Frontend	React, Vite, TailwindCSS, Recharts
Integração	JDBC, REST API, JSON

D
