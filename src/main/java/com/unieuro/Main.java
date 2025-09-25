package com.unieuro;

import com.unieuro.agents.AgenteColetaDados;
import com.unieuro.agents.AgenteAnalisadorEmocional;
import com.unieuro.agents.AgenteRecomendacao;
import com.unieuro.database.DatabaseManager;
import jadex.base.IPlatformConfiguration;
import jadex.base.PlatformConfigurationHandler;
import jadex.base.Starter;

import java.util.logging.Logger;

/**
 * Classe principal para iniciar o sistema multiagente de monitoramento de saúde mental.
 */
public class Main {
    
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        try {
            logger.info("=== Iniciando Sistema Multiagente de Monitoramento de Saúde Mental ===");
            
            // Inicializa o banco de dados
            logger.info("Inicializando banco de dados...");
            DatabaseManager dbManager = DatabaseManager.getInstance();
            
            // Lista os idosos cadastrados
            logger.info("Idosos cadastrados no sistema:");
            dbManager.listarIdosos().forEach(idoso -> 
                logger.info(String.format("ID: %d, Nome: %s, Idade: %d", 
                    idoso.get("id"), idoso.get("nome"), idoso.get("idade")))
            );
            
            // Configura a plataforma Jadex
            IPlatformConfiguration config = PlatformConfigurationHandler.getMinimal();
            
            // Adiciona os agentes ao sistema
            logger.info("Configurando agentes...");
            
            // Agente de Coleta de Dados para idoso ID 1
            config.addComponent(AgenteColetaDados.class);
            
            // Agente Analisador Emocional para idoso ID 1
            config.addComponent(AgenteAnalisadorEmocional.class);
            
            // Agente de Recomendação para idoso ID 1
            config.addComponent(AgenteRecomendacao.class);
            
            // Inicia a plataforma Jadex
            logger.info("Iniciando plataforma Jadex...");
            Starter.createPlatform(config).get();
            
            logger.info("=== Sistema iniciado com sucesso! ===");
            logger.info("Os agentes estão executando e monitorando a saúde mental do idoso.");
            logger.info("Pressione Ctrl+C para parar o sistema.");
            
            // Adiciona hook para shutdown graceful
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("Encerrando sistema...");
                dbManager.close();
                logger.info("Sistema encerrado.");
            }));
            
        } catch (Exception e) {
            logger.severe("Erro ao iniciar o sistema: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}

