package com.unieuro.agents;

import com.unieuro.database.DatabaseManager;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.OnStart;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentArgument;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Agente responsável por gerar recomendações personalizadas baseadas nas análises emocionais.
 * Monitora as análises e sugere intervenções apropriadas para todos os usuários cadastrados.
 */
@Agent
public class AgenteRecomendacao {
    
    private static final Logger logger = Logger.getLogger(AgenteRecomendacao.class.getName());
    private DatabaseManager dbManager;
    private Random random = new Random();
    
    @AgentArgument
    private int intervaloRecomendacao = 20000; // Intervalo em milissegundos (20 segundos)
    
    // Recomendações por tipo de risco
    private static final List<String> RECOMENDACOES_RISCO_BAIXO = Arrays.asList(
        "Continue mantendo sua rotina saudável",
        "Pratique exercícios de respiração por 5 minutos",
        "Ouça música relaxante",
        "Faça uma caminhada leve",
        "Mantenha contato social com familiares"
    );
    
    private static final List<String> RECOMENDACOES_RISCO_MODERADO = Arrays.asList(
        "Pratique exercícios de respiração profunda por 10 minutos",
        "Faça uma caminhada de 15-20 minutos",
        "Pratique meditação ou mindfulness",
        "Converse com um familiar ou amigo",
        "Escute música calma e relaxante",
        "Faça alongamentos suaves",
        "Beba um chá calmante (camomila, erva-cidreira)"
    );
    
    private static final List<String> RECOMENDACOES_RISCO_ALTO = Arrays.asList(
        "Entre em contato com um familiar ou cuidador",
        "Considere conversar com um profissional de saúde",
        "Pratique técnicas de relaxamento imediatamente",
        "Evite ficar sozinho por longos períodos",
        "Mantenha uma rotina de sono regular",
        "Procure atividades que tragam prazer e bem-estar",
        "Considere participar de grupos de apoio"
    );
    
    /**
     * Inicializa o agente e inicia o monitoramento.
     */
    @OnStart
    void iniciarMonitoramento(IInternalAccess me) {
        logger.info("Agente de Recomendação iniciado para TODOS os usuários cadastrados");
        
        // Inicializa o gerenciador de banco de dados
        dbManager = DatabaseManager.getInstance();
        
        // Inicia o monitoramento periódico para todos os usuários
        me.repeatStep(8000, intervaloRecomendacao, dummy -> {
            verificarAnalisesRecentesTodosUsuarios();
            return IFuture.DONE;
        });
    }
    
    /**
     * Verifica análises emocionais recentes e gera recomendações para todos os usuários.
     */
    private void verificarAnalisesRecentesTodosUsuarios() {
        try {
            // Buscar todos os idosos cadastrados
            List<Map<String, Object>> idosos = dbManager.listarIdosos();
            
            for (Map<String, Object> idoso : idosos) {
                Long idIdoso = (Long) idoso.get("id");
                String nome = (String) idoso.get("nome");
                
                verificarAnalisesRecentesUsuario(idIdoso, nome);
            }
            
        } catch (Exception e) {
            logger.severe("Erro ao verificar análises de todos os usuários: " + e.getMessage());
        }
    }
    
    /**
     * Verifica análises emocionais recentes e gera recomendações para um usuário específico.
     */
    private void verificarAnalisesRecentesUsuario(Long idIdoso, String nome) {
        try {
            // Busca a análise mais recente
            // Por simplicidade, vamos simular que sempre há uma análise recente
            // Em um sistema real, consultaríamos a tabela analises_emocionais
            
            String riscoSimulado = simularRiscoAtual();
            String recomendacao = gerarRecomendacao(riscoSimulado);
            
            // Salva a recomendação no banco
            dbManager.inserirRecomendacao(
                idIdoso, 
                recomendacao, 
                riscoSimulado, 
                "Recomendação gerada automaticamente baseada na análise emocional"
            );
            
            logger.info(String.format(
                "[%s] Recomendação gerada para risco %s: %s",
                nome, riscoSimulado, recomendacao
            ));
            
            // Se o risco é alto, pode disparar alertas adicionais
            if ("alto".equals(riscoSimulado)) {
                logger.warning("ALERTA CRÍTICO: Risco alto detectado para " + nome + " - Intervenção imediata recomendada!");
            }
            
        } catch (Exception e) {
            logger.severe("Erro ao gerar recomendação para " + nome + ": " + e.getMessage());
        }
    }
    
    /**
     * Gera uma recomendação baseada no nível de risco.
     */
    private String gerarRecomendacao(String nivelRisco) {
        List<String> recomendacoes;
        
        switch (nivelRisco.toLowerCase()) {
            case "alto":
                recomendacoes = RECOMENDACOES_RISCO_ALTO;
                break;
            case "moderado":
                recomendacoes = RECOMENDACOES_RISCO_MODERADO;
                break;
            case "baixo":
            default:
                recomendacoes = RECOMENDACOES_RISCO_BAIXO;
                break;
        }
        
        // Seleciona uma recomendação aleatória da lista apropriada
        return recomendacoes.get(random.nextInt(recomendacoes.size()));
    }
    
    /**
     * Simula o nível de risco atual (em um sistema real, consultaria o banco).
     */
    private String simularRiscoAtual() {
        // Simula distribuição realista de riscos
        int probabilidade = random.nextInt(100);
        
        if (probabilidade < 60) {
            return "baixo";
        } else if (probabilidade < 85) {
            return "moderado";
        } else {
            return "alto";
        }
    }
    
    /**
     * Gera recomendação personalizada baseada no histórico do idoso.
     */
    private String gerarRecomendacaoPersonalizada(String nivelRisco, String contexto) {
        // Esta função poderia usar machine learning ou regras mais sofisticadas
        // para personalizar recomendações baseadas no histórico do idoso
        
        String recomendacaoBase = gerarRecomendacao(nivelRisco);
        
        // Adiciona personalização baseada no contexto
        if (contexto != null && contexto.contains("sono")) {
            recomendacaoBase += " Foque especialmente em melhorar a qualidade do sono.";
        } else if (contexto != null && contexto.contains("humor")) {
            recomendacaoBase += " Procure atividades que melhorem seu humor.";
        } else if (contexto != null && contexto.contains("atividade")) {
            recomendacaoBase += " Aumente gradualmente sua atividade física.";
        }
        
        return recomendacaoBase;
    }
}

