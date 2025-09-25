# 🧠 Sistema Multiagente Jadex - Monitoramento de Saúde Mental (Versão com Banco de Dados)

Este projeto implementa um sistema multiagente usando o framework Jadex para monitoramento da saúde mental de pessoas idosas, **agora integrado com banco de dados** para persistência e análise histórica dos dados.

## 🎯 Objetivo

Criar um sistema multiagente BDI (Belief-Desire-Intention) que:
- ✅ Monitora dados de saúde mental e física de idosos
- ✅ **Armazena dados em banco de dados H2/PostgreSQL**
- ✅ Identifica riscos emocionais (ansiedade, estresse, apatia)
- ✅ Sugere intervenções rápidas e mudanças de rotina
- ✅ **Mantém histórico para análise de tendências**
- ✅ Oferece suporte emocional contínuo

## 🏗️ Arquitetura Atualizada

### Componentes do Sistema

1. **Banco de Dados** (H2 para desenvolvimento, PostgreSQL para produção)
   - Tabela `idosos`: Cadastro dos usuários
   - Tabela `dados_saude`: Dados coletados (sono, humor, atividade, FC)
   - Tabela `analises_emocionais`: Resultados das análises
   - Tabela `recomendacoes`: Sugestões geradas

2. **DatabaseManager** - Gerenciador de conexões e operações do banco

3. **Agentes Jadex**:
   - **AgenteColetaDados**: Simula e armazena dados de saúde
   - **AgenteAnalisadorEmocional**: Analisa dados e identifica riscos
   - **AgenteRecomendacao**: Gera recomendações personalizadas

## 🛠️ Tecnologias

- **Java 17**
- **Jadex Framework 4.0.241** - Sistema multiagente BDI
- **H2 Database** - Banco em memória para desenvolvimento
- **PostgreSQL** - Banco para produção (configurável)
- **HikariCP** - Pool de conexões
- **Maven** - Gerenciamento de dependências
- **SLF4J** - Sistema de logs

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- VS Code com Extension Pack for Java (recomendado)

### Passos

1. **Clone o repositório**:
```bash
git clone https://github.com/Dj157/sistema_multiagentes_jadex.git
cd sistema_multiagentes_jadex/meu-primeiro-jadex
```

2. **Compile o projeto**:
```bash
mvn clean compile
```

3. **Execute o sistema**:
```bash
mvn exec:java
```

## 📊 Funcionalidades Implementadas

### 🔄 Fluxo de Funcionamento

1. **Inicialização**:
   - Banco H2 é criado em memória
   - Tabelas são criadas automaticamente
   - Dados de exemplo são inseridos (João Silva, Maria Oliveira)

2. **Coleta de Dados** (AgenteColetaDados):
   - Simula dados realistas a cada 10 segundos
   - Armazena no banco: sono, humor, atividade física, frequência cardíaca
   - Dados variam de forma realística baseados em correlações

3. **Análise Emocional** (AgenteAnalisadorEmocional):
   - Analisa dados a cada 15 segundos
   - Identifica riscos: baixo, moderado, alto
   - Considera tendências históricas
   - Salva análises no banco

4. **Recomendações** (AgenteRecomendacao):
   - Gera recomendações a cada 20 segundos
   - Personaliza sugestões baseadas no nível de risco
   - Armazena recomendações no banco

### 📈 Dados Simulados

- **Sono**: 5.0-9.0 horas (correlacionado com humor)
- **Qualidade do Sono**: 2-5 (escala 1-5)
- **Humor**: positivo/neutro/negativo (baseado no sono)
- **Atividade Física**: sedentária/leve/moderada/intensa
- **Frequência Cardíaca**: 50-120 bpm (varia com atividade)

### 🎯 Análise de Riscos

**Critérios de Risco**:
- Sono < 6 horas (+2 pontos)
- Qualidade sono < 3 (+2 pontos)
- Humor negativo (+3 pontos)
- Atividade sedentária (+2 pontos)
- FC anormal (+1 ponto)

**Níveis**:
- **Baixo**: 0-2 pontos
- **Moderado**: 3-5 pontos
- **Alto**: 6+ pontos

### 💡 Recomendações por Risco

**Risco Baixo**:
- Manter rotina saudável
- Exercícios de respiração
- Música relaxante

**Risco Moderado**:
- Caminhadas de 15-20 min
- Meditação/mindfulness
- Contato social

**Risco Alto**:
- Contatar familiar/cuidador
- Buscar profissional de saúde
- Evitar isolamento

## 🗃️ Estrutura do Banco de Dados

```sql
-- Tabela de idosos cadastrados
CREATE TABLE idosos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    sexo CHAR(1),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Dados de saúde coletados
CREATE TABLE dados_saude (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_idoso BIGINT REFERENCES idosos(id),
    data_coleta DATE NOT NULL,
    sono_horas DECIMAL(3,1),
    qualidade_sono INT,
    humor VARCHAR(20),
    atividade_fisica VARCHAR(50),
    frequencia_cardiaca INT,
    observacoes TEXT
);

-- Análises emocionais realizadas
CREATE TABLE analises_emocionais (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_idoso BIGINT REFERENCES idosos(id),
    data_analise TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    risco_emocional VARCHAR(20),
    descricao TEXT
);

-- Recomendações geradas
CREATE TABLE recomendacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_idoso BIGINT REFERENCES idosos(id),
    data_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    recomendacao VARCHAR(100),
    tipo_risco VARCHAR(20),
    observacoes TEXT
);
```

## 📁 Estrutura do Projeto

```
src/main/java/com/unieuro/
├── Main.java                           # Classe principal
├── database/
│   └── DatabaseManager.java           # Gerenciador do banco
└── agents/
    ├── AgenteColetaDados.java         # Coleta e simula dados
    ├── AgenteAnalisadorEmocional.java # Analisa riscos emocionais
    └── AgenteRecomendacao.java        # Gera recomendações
```

## 🔧 Configuração

### Banco H2 (Desenvolvimento)
- **URL**: `jdbc:h2:mem:saude_mental`
- **Usuário**: `sa`
- **Senha**: (vazia)
- **Console H2**: Disponível em http://localhost:8082 (se habilitado)

### Banco PostgreSQL (Produção)
Para usar PostgreSQL, altere as configurações em `DatabaseManager.java`:

```java
config.setJdbcUrl("jdbc:postgresql://localhost:5432/saude_mental");
config.setUsername("seu_usuario");
config.setPassword("sua_senha");
config.setDriverClassName("org.postgresql.Driver");
```

## 📊 Logs do Sistema

O sistema gera logs detalhados mostrando:
- Dados coletados em tempo real
- Análises emocionais realizadas
- Recomendações geradas
- Alertas de risco alto

Exemplo de log:
```
INFO: Dados coletados - Sono: 7.2h, Humor: positivo, Atividade: moderada, FC: 78 bpm
INFO: Análise realizada - Risco: baixo, Descrição: Indicadores dentro da normalidade.
INFO: Recomendação gerada para risco baixo: Continue mantendo sua rotina saudável
```

## 🎯 Próximos Desenvolvimentos

- [ ] Interface web para visualização dos dados
- [ ] Dashboard com gráficos e relatórios
- [ ] Integração com dispositivos IoT reais
- [ ] Machine Learning para análise preditiva
- [ ] Sistema de notificações (email, SMS)
- [ ] API REST para integração externa
- [ ] Múltiplos idosos simultâneos
- [ ] Configuração de limiares personalizados

## ⚠️ Aviso Importante

Este sistema é destinado apenas para fins educacionais e de pesquisa. **Não substitui acompanhamento médico ou terapêutico profissional.**

## 🤝 Contribuição

Este é um projeto acadêmico. Contribuições são bem-vindas através de pull requests!

---

**Desenvolvido com ❤️ usando Jadex Framework e Java**

