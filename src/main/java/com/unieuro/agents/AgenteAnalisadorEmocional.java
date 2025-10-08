package com.unieuro.agents;

import com.unieuro.database.DatabaseManager;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.OnStart;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentArgument;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Agente responsável por analisar dados de saúde e identificar riscos emocionais.
 * Processa dados do banco e gera análises emocionais para todos os usuários cadastrados.
 */
@Agent
public class AgenteAnalisadorEmocional {
    
    private static final Logger logger = Logger.getLogger(AgenteAnalisadorEmocional.class.getName());
    private DatabaseManager dbManager;
    
    @AgentArgument
    private int intervaloAnalise = 15000; // Intervalo em milissegundos (15 segundos)
    
    // Limiares para identificação de riscos
    private static final double LIMIAR_SONO_BAIXO = 6.0;
    private static final int LIMIAR_QUALIDADE_SONO_BAIXA = 3;
    private static final int LIMIAR_FC_ALTA = 90;
    private static final int LIMIAR_FC_BAIXA = 60;
    
    /**
     * Inicializa o agente e inicia a análise periódica.
     */
    @OnStart
    void iniciarAnalise(IInternalAccess me) {
        logger.info("Agente Analisador Emocional iniciado para TODOS os usuários cadastrados");
        
        // Inicializa o gerenciador de banco de dados
        dbManager = DatabaseManager.getInstance();
        
        // Inicia a análise periódica para todos os usuários
        me.repeatStep(5000, intervaloAnalise, dummy -> {
            analisarTodosUsuarios();
            return IFuture.DONE;
        });
    }
    
    /**
     * Analisa dados de saúde para todos os usuários cadastrados.
     */
    private void analisarTodosUsuarios() {
        try {
            // Buscar todos os idosos cadastrados
            List<Map<String, Object>> idosos = dbManager.listarIdosos();
            
            for (Map<String, Object> idoso : idosos) {
                Long idIdoso = (Long) idoso.get("id");
                String nome = (String) idoso.get("nome");
                
                analisarDadosRecentesUsuario(idIdoso, nome);
            }
            
        } catch (Exception e) {
            logger.severe("Erro ao analisar dados de todos os usuários: " + e.getMessage());
        }
    }
    
    /**
     * Analisa os dados mais recentes de um usuário específico.
     */
    private void analisarDadosRecentesUsuario(Long idIdoso, String nome) {
        try {
            // Busca dados dos últimos 3 dias
            List<Map<String, Object>> dadosRecentes = dbManager.buscarDadosSaudeRecentes(idIdoso, 3);
            
            if (dadosRecentes.isEmpty()) {
                logger.warning("Nenhum dado encontrado para análise de " + nome + " (ID: " + idIdoso + ")");
                return;
            }
            
            // Analisa os dados mais recentes
            Map<String, Object> dadosMaisRecentes = dadosRecentes.get(0);
            AnaliseEmocional analise = analisarDados(dadosMaisRecentes, dadosRecentes);
            
            // Salva a análise no banco
            dbManager.inserirAnaliseEmocional(idIdoso, analise.nivelRisco, analise.descricao);
            
            logger.info(String.format(
                "[%s] Análise realizada - Risco: %s, Descrição: %s",
                nome, analise.nivelRisco, analise.descricao
            ));
            
            // Se há risco, pode notificar o agente de recomendação
            if (!"baixo".equals(analise.nivelRisco)) {
                logger.warning("ALERTA: Risco " + analise.nivelRisco + " detectado para " + nome + " (ID: " + idIdoso + ")");
            }
            
        } catch (Exception e) {
            logger.severe("Erro na análise emocional para " + nome + ": " + e.getMessage());
        }
    }
    
    /**
     * Analisa os dados de saúde recentes e identifica riscos emocionais.
     */
    private void analisarDadosRecentes() {
        try {
            // Busca dados dos últimos 3 dias
            List<Map<String, Object>> dadosRecentes = dbManager.buscarDadosSaudeRecentes(idIdoso, 3);
            
            if (dadosRecentes.isEmpty()) {
                logger.warning("Nenhum dado encontrado para análise do idoso ID: " + idIdoso);
                return;
            }
            
            // Analisa os dados mais recentes
            Map<String, Object> dadosMaisRecentes = dadosRecentes.get(0);
            AnaliseEmocional analise = analisarDados(dadosMaisRecentes, dadosRecentes);
            
            // Salva a análise no banco
            dbManager.inserirAnaliseEmocional(idIdoso, analise.nivelRisco, analise.descricao);
            
            logger.info(String.format(
                "Análise realizada - Risco: %s, Descrição: %s",
                analise.nivelRisco, analise.descricao
            ));
            
            // Se há risco, pode notificar o agente de recomendação
            if (!"baixo".equals(analise.nivelRisco)) {
                logger.warning("ALERTA: Risco " + analise.nivelRisco + " detectado para idoso ID: " + idIdoso);
            }
            
        } catch (Exception e) {
            logger.severe("Erro na análise emocional: " + e.getMessage());
        }
    }
    
    /**
     * Realiza a análise dos dados de saúde.
     */
    private AnaliseEmocional analisarDados(Map<String, Object> dadosAtuais, List<Map<String, Object>> historico) {
        AnaliseEmocional analise = new AnaliseEmocional();
        StringBuilder descricao = new StringBuilder();
        int pontuacaoRisco = 0;
        
        // Análise do sono
        double sonoHoras = (Double) dadosAtuais.get("sono_horas");
        int qualidadeSono = (Integer) dadosAtuais.get("qualidade_sono");
        
        if (sonoHoras < LIMIAR_SONO_BAIXO) {
            pontuacaoRisco += 2;
            descricao.append("Sono insuficiente (").append(sonoHoras).append("h). ");
        }
        
        if (qualidadeSono < LIMIAR_QUALIDADE_SONO_BAIXA) {
            pontuacaoRisco += 2;
            descricao.append("Qualidade do sono ruim (").append(qualidadeSono).append("/5). ");
        }
        
        // Análise do humor
        String humor = (String) dadosAtuais.get("humor");
        if ("negativo".equals(humor)) {
            pontuacaoRisco += 3;
            descricao.append("Humor negativo. ");
        }
        
        // Análise da atividade física
        String atividadeFisica = (String) dadosAtuais.get("atividade_fisica");
        if ("sedentaria".equals(atividadeFisica) || "nenhuma".equals(atividadeFisica)) {
            pontuacaoRisco += 2;
            descricao.append("Atividade física insuficiente. ");
        }
        
        // Análise da frequência cardíaca
        int fc = (Integer) dadosAtuais.get("frequencia_cardiaca");
        if (fc > LIMIAR_FC_ALTA || fc < LIMIAR_FC_BAIXA) {
            pontuacaoRisco += 1;
            descricao.append("Frequência cardíaca anormal (").append(fc).append(" bpm). ");
        }
        
        // Análise de tendências (se há dados históricos)
        if (historico.size() > 1) {
            analisarTendencias(historico, descricao);
        }
        
        // Determina o nível de risco
        if (pontuacaoRisco >= 6) {
            analise.nivelRisco = "alto";
        } else if (pontuacaoRisco >= 3) {
            analise.nivelRisco = "moderado";
        } else {
            analise.nivelRisco = "baixo";
        }
        
        // Finaliza a descrição
        if (descricao.length() == 0) {
            descricao.append("Indicadores dentro da normalidade.");
        }
        
        analise.descricao = descricao.toString().trim();
        
        return analise;
    }
    
    /**
     * Analisa tendências nos dados históricos.
     */
    private void analisarTendencias(List<Map<String, Object>> historico, StringBuilder descricao) {
        // Verifica se há piora consistente no humor
        int humorNegativoConsecutivo = 0;
        for (Map<String, Object> dado : historico) {
            if ("negativo".equals(dado.get("humor"))) {
                humorNegativoConsecutivo++;
            } else {
                break;
            }
        }
        
        if (humorNegativoConsecutivo >= 2) {
            descricao.append("Tendência de humor negativo persistente. ");
        }
        
        // Verifica tendência de sono ruim
        int sonoRuimConsecutivo = 0;
        for (Map<String, Object> dado : historico) {
            double sono = (Double) dado.get("sono_horas");
            if (sono < LIMIAR_SONO_BAIXO) {
                sonoRuimConsecutivo++;
            } else {
                break;
            }
        }
        
        if (sonoRuimConsecutivo >= 2) {
            descricao.append("Padrão de sono inadequado persistente. ");
        }
    }
    
    /**
     * Classe interna para representar uma análise emocional.
     */
    private static class AnaliseEmocional {
        String nivelRisco;
        String descricao;
    }
}

