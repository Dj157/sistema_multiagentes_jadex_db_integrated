package com.unieuro.agents;

import com.unieuro.database.DatabaseManager;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.OnStart;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentArgument;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Agente responsável por coletar e simular dados de saúde de todos os idosos cadastrados.
 * Os dados são armazenados no banco de dados e enviados para análise.
 */
@Agent
public class AgenteColetaDados {
    
    private static final Logger logger = Logger.getLogger(AgenteColetaDados.class.getName());
    private Random random = new Random();
    private DatabaseManager dbManager;
    
    @AgentArgument
    private int intervaloColeta = 10000; // Intervalo em milissegundos (10 segundos)
    
    /**
     * Inicializa o agente e inicia a coleta de dados.
     */
    @OnStart
    void iniciarColeta(IInternalAccess me) {
        logger.info("Agente de Coleta de Dados iniciado para TODOS os usuários cadastrados");
        
        // Inicializa o gerenciador de banco de dados
        dbManager = DatabaseManager.getInstance();
        
        // Inicia a coleta periódica de dados para todos os usuários
        me.repeatStep(1000, intervaloColeta, dummy -> {
            coletarDadosTodosUsuarios();
            return IFuture.DONE;
        });
    }
    
    /**
     * Coleta dados de saúde para todos os usuários cadastrados.
     */
    private void coletarDadosTodosUsuarios() {
        try {
            // Buscar todos os idosos cadastrados
            List<Map<String, Object>> idosos = dbManager.listarIdosos();
            
            for (Map<String, Object> idoso : idosos) {
                Long idIdoso = (Long) idoso.get("id");
                String nome = (String) idoso.get("nome");
                
                coletarDadosSaudeUsuario(idIdoso, nome);
            }
            
        } catch (Exception e) {
            logger.severe("Erro ao coletar dados de todos os usuários: " + e.getMessage());
        }
    }
    
    /**
     * Simula e coleta dados de saúde de um idoso específico.
     */
    private void coletarDadosSaudeUsuario(Long idIdoso, String nome) {
        try {
            // Simula dados de saúde realistas para idosos
            DadosSaude dados = simularDadosSaude();
            
            // Armazena no banco de dados
            dbManager.inserirDadosSaude(
                idIdoso,
                dados.sonoHoras,
                dados.qualidadeSono,
                dados.humor,
                dados.atividadeFisica,
                dados.frequenciaCardiaca
            );
            
            logger.info(String.format(
                "[%s] Dados coletados - Sono: %.1fh, Humor: %s, Atividade: %s, FC: %d bpm",
                nome, dados.sonoHoras, dados.humor, dados.atividadeFisica, dados.frequenciaCardiaca
            ));
            
            // Aqui poderia enviar mensagem para o Agente Analisador
            // Por simplicidade, vamos apenas logar
            
        } catch (Exception e) {
            logger.severe("Erro na coleta de dados para " + nome + ": " + e.getMessage());
        }
    }
    
    /**
     * Simula dados de saúde realistas para uma pessoa idosa.
     */
    private DadosSaude simularDadosSaude() {
        DadosSaude dados = new DadosSaude();
        
        // Sono: 4-10 horas (tendência para 6-8 horas)
        dados.sonoHoras = 5.0 + (random.nextDouble() * 4.0); // 5.0 a 9.0 horas
        
        // Qualidade do sono: 1-5 (1=muito ruim, 5=excelente)
        dados.qualidadeSono = 2 + random.nextInt(4); // 2 a 5
        
        // Humor baseado na qualidade do sono
        if (dados.qualidadeSono >= 4 && dados.sonoHoras >= 7) {
            dados.humor = random.nextBoolean() ? "positivo" : "neutro";
        } else if (dados.qualidadeSono <= 2 || dados.sonoHoras < 6) {
            dados.humor = random.nextBoolean() ? "negativo" : "neutro";
        } else {
            dados.humor = "neutro";
        }
        
        // Atividade física baseada no humor
        if ("positivo".equals(dados.humor)) {
            String[] atividades = {"moderada", "intensa", "leve"};
            dados.atividadeFisica = atividades[random.nextInt(atividades.length)];
        } else if ("negativo".equals(dados.humor)) {
            String[] atividades = {"sedentaria", "leve", "nenhuma"};
            dados.atividadeFisica = atividades[random.nextInt(atividades.length)];
        } else {
            String[] atividades = {"leve", "moderada", "sedentaria"};
            dados.atividadeFisica = atividades[random.nextInt(atividades.length)];
        }
        
        // Frequência cardíaca: 60-100 bpm (normal para idosos)
        // Pode variar com atividade física e humor
        int fcBase = 65 + random.nextInt(20); // 65-85 bpm base
        
        if ("intensa".equals(dados.atividadeFisica)) {
            dados.frequenciaCardiaca = fcBase + 10 + random.nextInt(15); // +10 a +25
        } else if ("moderada".equals(dados.atividadeFisica)) {
            dados.frequenciaCardiaca = fcBase + 5 + random.nextInt(10); // +5 a +15
        } else {
            dados.frequenciaCardiaca = fcBase + random.nextInt(10) - 5; // -5 a +5
        }
        
        // Garante que FC está dentro dos limites normais
        dados.frequenciaCardiaca = Math.max(50, Math.min(120, dados.frequenciaCardiaca));
        
        return dados;
    }
    
    /**
     * Classe interna para representar dados de saúde.
     */
    private static class DadosSaude {
        double sonoHoras;
        int qualidadeSono;
        String humor;
        String atividadeFisica;
        int frequenciaCardiaca;
    }
}

