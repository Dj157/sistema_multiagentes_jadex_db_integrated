# Sistema Multiagente de Monitoramento Emocional

## 1. Descrição Geral do Sistema

Este projeto implementa um **Sistema Multiagente** baseado na plataforma **Jadex**, integrado a uma **API REST Spring Boot** e a um **dashboard web em React**. O sistema simula a coleta, análise e recomendação de ações relacionadas ao estado emocional de usuários, utilizando múltiplos agentes que interagem entre si e com um banco de dados H2.

A arquitetura é composta por três camadas principais:

1. **Agentes Inteligentes (Jadex)** – responsáveis por coletar dados, analisar riscos emocionais e gerar recomendações.
2. **API REST (Spring Boot)** – atua como intermediária entre os agentes e o frontend, fornecendo endpoints para consulta e atualização dos dados.
3. **Interface Web (React + Vite)** – exibe visualmente os dados coletados e as recomendações geradas pelos agentes em tempo real.

---

## 2. Arquitetura Geral do Sistema

O sistema segue uma **arquitetura multiagente distribuída**, com comunicação entre os agentes via **mensagens Jadex** e persistência de dados via **banco de dados H2**.

| Camada | Função | Tecnologias |
|--------|--------|-------------|
| Multiagente (Backend Inteligente) | Processamento autônomo e colaborativo de informações emocionais | Jadex, Java |
| API REST | Comunicação entre os agentes e o dashboard | Spring Boot |
| Banco de Dados | Armazenamento persistente de dados simulados e resultados de análises | H2 Database |
| Interface Web | Visualização dos dados e das recomendações em tempo real | React, Vite, JavaScript |

---

## 3. Arquitetura Multiagente

O sistema utiliza três agentes principais:

| Agente | Função | Sensores (Entradas) | Atuadores (Saídas) |
|--------|--------|---------------------|--------------------|
| **AgenteColetaDados** | Coleta dados de humor, sono e atividade física dos usuários. | Dados simulados ou vindos da API REST. | Envia informações ao banco e ao AgenteAnalisadorEmocional. |
| **AgenteAnalisadorEmocional** | Avalia os dados recebidos, detecta riscos emocionais (como estresse, fadiga ou ansiedade). | Dados do AgenteColetaDados. | Gera alertas de risco e envia ao AgenteRecomendacao. |
| **AgenteRecomendacao** | Produz recomendações personalizadas para o usuário com base nas análises. | Alertas e diagnósticos do AgenteAnalisadorEmocional. | Envia recomendações para o banco e para exibição no dashboard. |

### Fluxo de Comunicação entre Agentes

1. **AgenteColetaDados** coleta informações e as envia ao **AgenteAnalisadorEmocional**.
2. **AgenteAnalisadorEmocional** processa os dados e identifica padrões de risco emocional.
3. **AgenteRecomendacao** interpreta as análises e gera recomendações ao usuário.
4. Os resultados são armazenados no **banco de dados H2** e exibidos no **dashboard React**.

---

## 4. Sensores e Atuadores

Os **sensores** do sistema correspondem às **entradas de dados** (simulados ou reais) coletadas pelos agentes — como humor, sono, frequência cardíaca e nível de atividade.  
Os **atuadores** são as **ações de saída** do sistema, representadas pelas **recomendações, alertas e visualizações** exibidas no dashboard.

| Tipo | Exemplo | Responsável |
|------|----------|-------------|
| Sensor | Dados de humor e sono | AgenteColetaDados |
| Sensor | Padrões de risco emocional | AgenteAnalisadorEmocional |
| Atuador | Recomendações personalizadas | AgenteRecomendacao |
| Atuador | Exibição visual de dados e gráficos | Interface Web |

---

## 5. Interface Web (Dashboard)

A interface gráfica foi desenvolvida em **React** com **Vite**. Ela permite visualizar, de forma dinâmica, os dados coletados e processados pelos agentes.  
O dashboard consome os endpoints da API REST para exibir:

- Dados simulados dos usuários;
- Análises emocionais;
- Recomendações geradas pelos agentes.

URL padrão do servidor local:
http://localhost:5173

yaml
Copy code

---

## 6. Banco de Dados

O sistema utiliza um banco de dados **H2 embutido**, inicializado automaticamente pelo Spring Boot.  
Ele armazena:

- Dados simulados coletados pelo AgenteColetaDados;  
- Resultados de análise emocional;  
- Recomendações geradas pelos agentes.

---

## 7. Estrutura do Projeto

src/main/java/com/unieuro/
├── Main.java # Classe principal Jadex
├── database/
│ └── DatabaseManager.java # Gerenciador do banco de dados
└── agents/
├── AgenteColetaDados.java # Coleta e simula dados
├── AgenteAnalisadorEmocional.java # Analisa riscos emocionais
└── AgenteRecomendacao.java # Gera recomendações

yaml
Copy code

---

## 8. Instruções de Execução

### Terminal 1 – Sistema Multiagente Jadex
```bash
cd /workspaces/sistema_multiagentes_jadex_db_integrated
mvn exec:java
Roda os agentes Jadex (com.unieuro.Main).

Mantém o terminal aberto para logs.

Terminal 2 – API REST Spring Boot
bash
Copy code
cd /workspaces/sistema_multiagentes_jadex_db_integrated
mvn spring-boot:run
Inicia a API REST (WebApiApplication).

Endereço padrão: http://localhost:8080

Terminal 3 – Dashboard Web (React)
bash
Copy code
cd /workspaces/sistema_multiagentes_jadex_db_integrated/health-dashboard
npm install
npm run dev
Inicia o servidor Vite.

Acessar em: http://localhost:5173

9. Diagrama da Arquitetura
mermaid
Copy code
flowchart TD
    subgraph Frontend [Dashboard Web - React]
        UI[Interface Web]
    end

    subgraph API [API REST - Spring Boot]
        REST[Endpoints REST]
    end

    subgraph Jadex [Sistema Multiagente Jadex]
        A1[AgenteColetaDados]
        A2[AgenteAnalisadorEmocional]
        A3[AgenteRecomendacao]
    end

    subgraph DB [Banco de Dados H2]
        BD[(Dados Persistentes)]
    end

    UI --> REST
    REST --> A1
    A1 --> A2
    A2 --> A3
    A3 --> BD
    BD --> REST
    REST --> UI
10. Tecnologias Utilizadas
Categoria	Tecnologias
Linguagem Principal	Java 17
Framework Multiagente	Jadex
Backend / API	Spring Boot
Banco de Dados	H2
Frontend	React + Vite
Build	Maven, npm

11. Objetivo do Projeto
Demonstrar a integração entre inteligência multiagente, banco de dados e visualização web, simulando um ambiente inteligente capaz de:

Coletar dados de usuários;

Detectar riscos emocionais;

Gerar recomendações automáticas;

Exibir resultados em tempo real.
