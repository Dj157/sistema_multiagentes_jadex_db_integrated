# 🧠 Sistema Multiagente Jadex - Monitoramento de Saúde Mental

## 🚀 Apresentação do Projeto

Este projeto demonstra um sistema multiagente inteligente, desenvolvido com o framework Jadex, focado no **monitoramento da saúde mental de pessoas idosas**. Ele integra agentes autônomos, um banco de dados persistente, uma API REST robusta e um dashboard web interativo para visualização em tempo real.

### 🎯 **Objetivo Principal**

Proporcionar uma solução tecnológica para acompanhar e apoiar a saúde mental de idosos, identificando proativamente riscos e sugerindo intervenções personalizadas.

### 💡 **Como Funciona?**

1.  **Agentes Jadex (Inteligência Artificial):**
    *   **Agente Coleta de Dados:** Simula e armazena dados vitais (sono, humor, atividade física, frequência cardíaca) em um banco de dados.
    *   **Agente Analisador Emocional:** Processa os dados coletados, identifica padrões e avalia o nível de risco emocional (baixo, moderado, alto).
    *   **Agente de Recomendação:** Gera sugestões personalizadas de bem-estar com base nas análises de risco.

2.  **Banco de Dados (Persistência):**
    *   Armazena de forma organizada todos os dados coletados, análises e recomendações, permitindo histórico e tendências.

3.  **API REST (Comunicação):**
    *   Uma interface de programação (API) desenvolvida em Spring Boot que serve os dados do banco para o dashboard web.

4.  **Dashboard Web (Visualização):**
    *   Uma interface gráfica intuitiva, desenvolvida em React, que exibe em tempo real as métricas de saúde, tendências, análises de risco e recomendações.

## 📊 **Funcionalidades do Dashboard Web**

*   **Métricas Principais:** Sono, Humor, Atividade Física e Frequência Cardíaca com indicadores visuais.
*   **Gráficos de Tendências:** Evolução dos indicadores ao longo do tempo.
*   **Distribuição de Riscos:** Gráfico de pizza mostrando a proporção de riscos (baixo, moderado, alto).
*   **Análises Emocionais:** Lista de resultados das análises dos agentes.
*   **Recomendações Personalizadas:** Sugestões de ações baseadas no nível de risco.
*   **Status dos Agentes:** Monitoramento em tempo real do funcionamento dos agentes Jadex.

## 🛠️ **Tecnologias Utilizadas**

*   **Backend:** Java 11+, Jadex Framework, Spring Boot, Maven, H2 Database (em memória).
*   **Frontend:** React 18, Vite, Tailwind CSS, shadcn/ui, Recharts.

--- 

## 🚀 **Passo a Passo para Rodar o Programa Completo no Codespace**

Para iniciar todo o sistema (Agentes Jadex, API REST e Dashboard Web) no seu ambiente Codespace, você precisará de **três terminais separados**.

### **Pré-requisitos:**

Certifique-se de que seu Codespace possui:
*   **Java 11+**
*   **Maven**
*   **Node.js 18+**
*   **npm** (ou pnpm)

### **Instruções:**

1.  **Clone o Repositório:**
    Se ainda não o fez, clone o projeto:
    ```bash
    git clone https://github.com/Dj157/sistema_multiagentes_jadex_db_integrated.git
    cd sistema_multiagentes_jadex_db_integrated
    ```

2.  **Terminal 1 – Sistema Multiagente Jadex**
    Este terminal rodará os agentes Jadex (Coleta de Dados, Analisador Emocional, Recomendação) e o banco de dados H2 em memória.
    ```bash
    cd /workspaces/sistema_multiagentes_jadex_db_integrated/meu-primeiro-jadex
    mvn clean compile # Apenas na primeira vez ou se houver mudanças no código Java
    mvn exec:java
    ```
    *Mantenha este terminal aberto. Logs dos agentes aparecerão aqui.*

3.  **Terminal 2 – API REST Spring Boot**
    Este terminal iniciará a API REST que serve os dados do banco para o dashboard.
    ```bash
    cd /workspaces/sistema_multiagentes_jadex_db_integrated
    mvn spring-boot:run -Dspring-boot.run.main-class=com.unieuro.WebApiApplication
    ```
    *Mantenha este terminal aberto. A API estará acessível em `http://localhost:8080`.*

4.  **Terminal 3 – Dashboard Web (React)**
    Este terminal iniciará o servidor de desenvolvimento do dashboard React.
    ```bash
    cd /workspaces/sistema_multiagentes_jadex_db_integrated/health-dashboard
    npm install --legacy-peer-deps # Apenas na primeira vez ou se houver mudanças nas dependências
    npm run dev -- --host
    ```
    *Mantenha este terminal aberto. O dashboard estará acessível em `http://localhost:5173` (ou outra porta se 5173 estiver em uso).*

### **🌐 Acessando o Dashboard**

Após iniciar todos os três componentes, abra seu navegador e acesse:

**`http://localhost:5173`** (ou a porta indicada pelo `npm run dev`)

Você verá o dashboard web exibindo os dados gerados pelos agentes Jadex e servidos pela API Spring Boot.

### **🛑 Parando o Sistema**

Para parar o sistema, pressione `Ctrl+C` em cada um dos três terminais onde os componentes estão sendo executados.

--- 

**Desenvolvido com ❤️ para o monitoramento da saúde mental de idosos.**


