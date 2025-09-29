# 🚀 Guia de Execução no VS Code Codespace

Caminho correto

Entra na pasta interna onde está o pom.xml + src:

cd sistema_multiagentes_jadex_db_integrated


Depois roda:

mvn clean compile
mvn exec:java

## ✅ Sistema Testado e Funcionando!

O sistema multiagente Jadex com integração de banco de dados foi **testado com sucesso** e está funcionando perfeitamente. Aqui está o guia completo para executar no seu Codespace.

## 📋 Pré-requisitos

- VS Code Codespace configurado
- Java 11+ (já disponível no Codespace)
- Maven (será instalado automaticamente)

## 🔧 Configuração Inicial

### 1. Clone o Repositório
```bash
git clone https://github.com/Dj157/sistema_multiagentes_jadex.git
cd sistema_multiagentes_jadex/meu-primeiro-jadex
```

### 2. Instale o Maven (se necessário)
```bash
sudo apt update && sudo apt install -y maven
```

### 3. Verifique as Versões
```bash
java -version    # Deve ser Java 11+
mvn -version     # Deve ser Maven 3.6+
```

## ▶️ Executando o Sistema

### Compilação
```bash
mvn clean compile
```

### Execução
```bash
mvn exec:java
```

## 📊 O que Você Verá

Quando o sistema iniciar, você verá logs como estes:

```
INFO: === Iniciando Sistema Multiagente de Monitoramento de Saúde Mental ===
INFO: Inicializando banco de dados...
INFO: Banco de dados H2 inicializado com sucesso!
INFO: Idosos cadastrados no sistema:
INFO: ID: 1, Nome: João Silva, Idade: 70
INFO: ID: 2, Nome: Maria Oliveira, Idade: 75
INFO: Configurando agentes...
INFO: Iniciando plataforma Jadex...
INFO: Agente de Coleta de Dados iniciado para idoso ID: 1
INFO: Agente Analisador Emocional iniciado para idoso ID: 1
INFO: Agente de Recomendação iniciado para idoso ID: 1
INFO: === Sistema iniciado com sucesso! ===
```

### Logs em Tempo Real

O sistema gerará logs contínuos mostrando:

**Coleta de Dados (a cada 10 segundos):**
```
INFO: Dados coletados - Sono: 7.8h, Humor: neutro, Atividade: moderada, FC: 86 bpm
```

**Análise Emocional (a cada 15 segundos):**
```
INFO: Análise realizada - Risco: baixo, Descrição: Indicadores dentro da normalidade.
```

**Recomendações (a cada 20 segundos):**
```
INFO: Recomendação gerada para risco moderado: Faça uma caminhada de 15-20 minutos
```

## 🛑 Parando o Sistema

Para parar o sistema, pressione `Ctrl+C` no terminal.

## 🗃️ Banco de Dados

O sistema usa **H2 Database** em memória com as seguintes características:

- **Tipo**: Banco em memória (dados são perdidos ao parar)
- **URL**: `jdbc:h2:mem:saude_mental`
- **Usuário**: `sa`
- **Senha**: (vazia)

### Tabelas Criadas Automaticamente:
- `idosos` - Cadastro dos usuários
- `dados_saude` - Dados coletados pelos sensores
- `analises_emocionais` - Resultados das análises
- `recomendacoes` - Sugestões geradas

## 🎯 Funcionalidades Demonstradas

### ✅ Agente de Coleta de Dados
- Simula dados realistas de saúde
- Armazena no banco de dados
- Varia dados baseado em correlações (sono → humor → atividade)

### ✅ Agente Analisador Emocional
- Analisa dados recentes do banco
- Identifica riscos: baixo, moderado, alto
- Considera tendências históricas
- Salva análises no banco

### ✅ Agente de Recomendação
- Gera recomendações baseadas no risco
- Personaliza sugestões
- Armazena recomendações no banco

## 📈 Dados Simulados

O sistema simula dados realistas:

- **Sono**: 5.0-9.0 horas
- **Qualidade do Sono**: 2-5 (escala 1-5)
- **Humor**: positivo/neutro/negativo
- **Atividade Física**: sedentária/leve/moderada/intensa
- **Frequência Cardíaca**: 50-120 bpm

## 🔍 Análise de Riscos

**Critérios de Pontuação:**
- Sono < 6 horas: +2 pontos
- Qualidade sono < 3: +2 pontos
- Humor negativo: +3 pontos
- Atividade sedentária: +2 pontos
- FC anormal: +1 ponto

**Níveis de Risco:**
- **Baixo**: 0-2 pontos
- **Moderado**: 3-5 pontos
- **Alto**: 6+ pontos

## 💡 Recomendações por Risco

**Risco Baixo:**
- Continue mantendo sua rotina saudável
- Pratique exercícios de respiração por 5 minutos
- Ouça música relaxante

**Risco Moderado:**
- Pratique exercícios de respiração profunda por 10 minutos
- Faça uma caminhada de 15-20 minutos
- Pratique meditação ou mindfulness

**Risco Alto:**
- Entre em contato com um familiar ou cuidador
- Considere conversar com um profissional de saúde
- Evite ficar sozinho por longos períodos

## 🐛 Resolução de Problemas

### Erro de Compilação
```bash
mvn clean compile -X  # Para debug detalhado
```

### Erro de Execução
```bash
# Verifique se o Java está correto
java -version

# Limpe e recompile
mvn clean install
```

### Logs de Erro SLF4J
O warning sobre SLF4J pode ser ignorado - não afeta o funcionamento.

## 🔧 Personalização

### Alterar Intervalos
No arquivo `Main.java`, você pode configurar:
- ID do idoso monitorado
- Intervalos de coleta, análise e recomendação

### Adicionar Novos Idosos
Modifique o `DatabaseManager.java` para inserir mais dados de exemplo.

### Configurar PostgreSQL
Para usar PostgreSQL em produção, altere as configurações no `DatabaseManager.java`.

## 📁 Estrutura de Arquivos

```
meu-primeiro-jadex/
├── pom.xml                           # Configuração Maven
├── src/main/java/com/unieuro/
│   ├── Main.java                     # Classe principal
│   ├── database/
│   │   └── DatabaseManager.java     # Gerenciador do banco
│   └── agents/
│       ├── AgenteColetaDados.java    # Coleta dados
│       ├── AgenteAnalisadorEmocional.java # Analisa riscos
│       └── AgenteRecomendacao.java   # Gera recomendações
└── target/                           # Arquivos compilados
```

## 🎉 Próximos Passos

1. **Experimente modificar os limiares** de risco no `AgenteAnalisadorEmocional`
2. **Adicione novas recomendações** no `AgenteRecomendacao`
3. **Implemente uma interface web** para visualizar os dados
4. **Adicione mais tipos de sensores** simulados
5. **Integre com dispositivos IoT reais**

---

**🎯 O sistema está funcionando perfeitamente e demonstra todos os conceitos de sistemas multiagente BDI com persistência de dados!**

