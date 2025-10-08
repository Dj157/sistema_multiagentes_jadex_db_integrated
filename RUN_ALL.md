# 🚀 Como Rodar o Sistema Completo (Jadex + API + Dashboard)

Este guia detalha os passos para iniciar todas as partes do sistema multiagente de monitoramento de saúde mental, incluindo os agentes Jadex, a API REST e o dashboard web.

## ✅ Pré-requisitos

Certifique-se de ter o seguinte instalado e configurado:

-   **Java 11+**
-   **Maven**
-   **Node.js 18+**
-   **npm** ou **pnpm** (para o frontend React)
-   **Git**

## 📋 Passos para Iniciar o Sistema Completo

Você precisará de **três terminais separados** para rodar cada componente do sistema simultaneamente.

### Terminal 1: Iniciar o Sistema Multiagente Jadex

Este terminal irá rodar os agentes Jadex (Coleta de Dados, Analisador Emocional, Recomendação) e o banco de dados H2 em memória.

1.  **Navegue até o diretório do projeto Jadex:**
    ```bash
    cd /home/ubuntu/sistema_multiagentes_jadex_db_integrated/meu-primeiro-jadex
    ```

2.  **Compile o projeto (se ainda não o fez):**
    ```bash
    mvn clean compile
    ```

3.  **Execute o sistema Jadex:**
    ```bash
    mvn exec:java
    ```
    Você verá logs dos agentes coletando dados, analisando e gerando recomendações. Mantenha este terminal aberto.

### Terminal 2: Iniciar a API REST (Spring Boot)

Este terminal irá rodar a API Spring Boot que expõe os dados do banco de dados para o dashboard web.

1.  **Navegue até o diretório raiz do projeto:**
    ```bash
    cd /home/ubuntu/sistema_multiagentes_jadex_db_integrated
    ```

2.  **Execute a aplicação Spring Boot:**
    ```bash
    mvn spring-boot:run -Dspring-boot.run.main-class=com.unieuro.WebApiApplication
    ```
    A API será iniciada na porta `8080`. Mantenha este terminal aberto.

### Terminal 3: Iniciar o Dashboard Web (React)

Este terminal irá rodar o servidor de desenvolvimento do dashboard React.

1.  **Navegue até o diretório do dashboard:**
    ```bash
    cd /home/ubuntu/sistema_multiagentes_jadex_db_integrated/health-dashboard
    ```

2.  **Instale as dependências (se ainda não o fez):**
    ```bash
    npm install # ou pnpm install
    ```

3.  **Inicie o servidor de desenvolvimento:**
    ```bash
    npm run dev -- --host
    ```
    O dashboard será iniciado na porta `5173` (ou outra disponível, como `5174`). Mantenha este terminal aberto.

## 🌐 Acessando o Dashboard

Após iniciar todos os três componentes, abra seu navegador e acesse:

**`http://localhost:5173`** (ou a porta indicada pelo `npm run dev`)

Você verá o dashboard web exibindo os dados gerados pelos agentes Jadex e servidos pela API Spring Boot.

## 🛑 Parando o Sistema

Para parar o sistema, pressione `Ctrl+C` em cada um dos três terminais onde os componentes estão sendo executados.

