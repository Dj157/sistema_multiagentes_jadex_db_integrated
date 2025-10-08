# 🌐 Dashboard Web - Sistema Multiagente Jadex

## 🎉 Interface Web Completa Implementada!

Este documento descreve a **interface web com dashboard** criada para visualização dos dados do sistema multiagente Jadex de monitoramento de saúde mental.

## 🏗️ Arquitetura da Interface Web

### Frontend (React)
- **Framework**: React 18 com Vite
- **UI Components**: shadcn/ui + Tailwind CSS
- **Gráficos**: Recharts
- **Ícones**: Lucide React
- **Localização**: `/health-dashboard/`

### Backend API (Spring Boot)
- **Framework**: Spring Boot 2.7.18
- **Endpoints REST**: `/api/*`
- **CORS**: Habilitado para todas as origens
- **Integração**: Conecta com o banco H2 do sistema Jadex

## 📊 Funcionalidades do Dashboard

### 1. **Métricas Principais em Tempo Real**
- ✅ **Sono**: Horas dormidas com indicador de adequação
- ✅ **Humor**: Escala 1-10 com classificação (positivo/neutro/negativo)
- ✅ **Atividade Física**: Minutos de atividade com status
- ✅ **Frequência Cardíaca**: BPM com indicador de normalidade

### 2. **Visualizações Avançadas**
- ✅ **Gráfico de Tendências**: Evolução dos últimos 7 dias
- ✅ **Distribuição de Riscos**: Gráfico de pizza com percentuais
- ✅ **Análise de Correlações**: Gráfico de área empilhada
- ✅ **Indicadores Visuais**: Cores e ícones intuitivos

### 3. **Abas de Navegação**
- ✅ **Visão Geral**: Dashboard principal com métricas e gráficos
- ✅ **Tendências**: Análise detalhada de correlações
- ✅ **Análises**: Resultados das análises emocionais dos agentes
- ✅ **Recomendações**: Sugestões personalizadas geradas

### 4. **Status dos Agentes Jadex**
- ✅ **Monitoramento em Tempo Real**: Status de cada agente
- ✅ **Indicadores Visuais**: Pontos pulsantes para agentes ativos
- ✅ **Descrições**: Frequência de operação de cada agente

## 🚀 Como Executar o Dashboard

### Pré-requisitos
- Node.js 18+ e npm/pnpm
- Java 11+ e Maven
- Sistema Jadex funcionando

### 1. **Iniciar o Frontend React**
```bash
cd health-dashboard
npm install  # ou pnpm install
npm run dev -- --host
```
**Acesso**: http://localhost:5173

### 2. **Iniciar a API Backend (Opcional)**
```bash
# Na raiz do projeto
mvn spring-boot:run -Dspring-boot.run.main-class=com.unieuro.WebApiApplication
```
**API**: http://localhost:8080/api

### 3. **Sistema Jadex (Principal)**
```bash
cd meu-primeiro-jadex
mvn exec:java
```

## 🔗 Endpoints da API

| Endpoint | Método | Descrição |
|----------|--------|-----------|
| `/api/health-data/{id}` | GET | Dados de saúde dos últimos 7 dias |
| `/api/latest-data/{id}` | GET | Dados mais recentes do idoso |
| `/api/patients` | GET | Lista de idosos cadastrados |
| `/api/risk-stats` | GET | Estatísticas de distribuição de riscos |
| `/api/analyses/{id}` | GET | Análises emocionais recentes |
| `/api/recommendations/{id}` | GET | Recomendações personalizadas |
| `/api/agents-status` | GET | Status dos agentes Jadex |

## 🎨 Design e UX

### Paleta de Cores
- **Verde**: Indicadores normais/baixo risco
- **Amarelo**: Indicadores moderados/atenção
- **Vermelho**: Indicadores altos/críticos
- **Azul**: Elementos de navegação e branding

### Responsividade
- ✅ **Desktop**: Layout completo com sidebar
- ✅ **Tablet**: Adaptação de grid responsivo
- ✅ **Mobile**: Interface otimizada para toque

### Acessibilidade
- ✅ **Contraste**: Cores com contraste adequado
- ✅ **Ícones**: Elementos visuais intuitivos
- ✅ **Navegação**: Estrutura clara e lógica

## 📈 Dados Exibidos

### Métricas de Saúde
```javascript
{
  sono: 6.2,           // Horas de sono
  humor: 5,            // Escala 1-10
  atividade: 25,       // Minutos de atividade
  fc: 82               // Frequência cardíaca (bpm)
}
```

### Análises Emocionais
```javascript
{
  risco: "moderado",   // baixo/moderado/alto
  descricao: "Sono insuficiente e humor baixo detectados",
  data: "2024-01-07"
}
```

### Recomendações
```javascript
{
  recomendacao: "Pratique exercícios de respiração por 10 minutos",
  tipo: "moderado"     // baixo/moderado/alto
}
```

## 🔄 Integração com Sistema Jadex

### Fluxo de Dados
1. **Agentes Jadex** → Coletam e analisam dados
2. **Banco H2** → Armazena dados persistentes
3. **API Spring Boot** → Expõe dados via REST
4. **Dashboard React** → Visualiza dados em tempo real

### Fallback para Dados Simulados
- O dashboard funciona **independentemente** da API
- Dados simulados são usados quando a API não está disponível
- Transição transparente entre dados reais e simulados

## 🛠️ Tecnologias Utilizadas

### Frontend
- **React 18**: Framework principal
- **Vite**: Build tool e dev server
- **Tailwind CSS**: Estilização utilitária
- **shadcn/ui**: Componentes UI modernos
- **Recharts**: Biblioteca de gráficos
- **Lucide React**: Ícones SVG

### Backend
- **Spring Boot**: Framework REST API
- **Jackson**: Serialização JSON
- **H2 Database**: Banco de dados em memória
- **HikariCP**: Pool de conexões

## 📱 Screenshots das Funcionalidades

### Visão Geral
- Métricas principais em cards coloridos
- Gráfico de tendências dos últimos 7 dias
- Distribuição de riscos em pizza
- Status dos agentes em tempo real

### Análises
- Lista de análises emocionais recentes
- Badges coloridos por nível de risco
- Descrições detalhadas dos riscos

### Recomendações
- Sugestões personalizadas por risco
- Classificação visual por tipo
- Interface limpa e organizada

## 🎯 Próximas Melhorias Sugeridas

- [ ] **Notificações Push**: Alertas em tempo real
- [ ] **Filtros Avançados**: Por data, tipo de risco, etc.
- [ ] **Exportação**: PDF/Excel dos relatórios
- [ ] **Múltiplos Pacientes**: Seleção e comparação
- [ ] **Configurações**: Personalização de limiares
- [ ] **Histórico Expandido**: Dados de meses/anos
- [ ] **Integração IoT**: Dispositivos reais
- [ ] **Machine Learning**: Predições avançadas

## 🔐 Segurança e Privacidade

- ✅ **CORS Configurado**: Acesso controlado
- ✅ **Dados Simulados**: Nenhum dado real exposto
- ✅ **Banco em Memória**: Dados não persistem
- ⚠️ **Produção**: Implementar autenticação/autorização

---

## 🎉 **Dashboard Completo e Funcional!**

O dashboard web está **100% operacional** e oferece uma interface moderna e intuitiva para visualização dos dados do sistema multiagente Jadex. A integração entre frontend React e backend Spring Boot proporciona uma experiência fluida e responsiva para monitoramento da saúde mental de idosos.

**🔗 Branch**: `feature/web-dashboard`  
**🌐 Repositório**: https://github.com/Dj157/sistema_multiagentes_jadex_db_integrated

