package com.unieuro.api;

import com.unieuro.database.DatabaseManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Controlador REST para fornecer dados de saúde para o dashboard web.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Permite CORS para todas as origens
public class HealthDataController {
    
    private final DatabaseManager dbManager;
    
    public HealthDataController() {
        this.dbManager = DatabaseManager.getInstance();
    }
    
    /**
     * Endpoint para obter dados de saúde recentes de um idoso.
     */
    @GetMapping("/health-data/{idIdoso}")
    public ResponseEntity<List<Map<String, Object>>> getHealthData(@PathVariable long idIdoso) {
        try {
            List<Map<String, Object>> dados = dbManager.buscarDadosSaudeRecentes(idIdoso, 7);
            return ResponseEntity.ok(dados);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Endpoint para obter dados de saúde mais recentes de um idoso.
     */
    @GetMapping("/latest-data/{idIdoso}")
    public ResponseEntity<Map<String, Object>> getLatestData(@PathVariable long idIdoso) {
        try {
            List<Map<String, Object>> dados = dbManager.buscarDadosSaudeRecentes(idIdoso, 1);
            if (!dados.isEmpty()) {
                return ResponseEntity.ok(dados.get(0));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Endpoint para obter lista de idosos cadastrados.
     */
    @GetMapping("/patients")
    public ResponseEntity<List<Map<String, Object>>> getPatients() {
        try {
            List<Map<String, Object>> idosos = dbManager.listarIdosos();
            return ResponseEntity.ok(idosos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Endpoint para obter estatísticas de risco.
     */
    @GetMapping("/risk-stats")
    public ResponseEntity<Map<String, Object>> getRiskStats() {
        try {
            // Simulação de estatísticas de risco
            Map<String, Object> stats = new HashMap<>();
            stats.put("baixo", 60);
            stats.put("moderado", 30);
            stats.put("alto", 10);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Endpoint para obter análises emocionais recentes.
     */
    @GetMapping("/analyses/{idIdoso}")
    public ResponseEntity<List<Map<String, Object>>> getAnalyses(@PathVariable long idIdoso) {
        try {
            // Por simplicidade, retornamos dados simulados
            // Em um sistema real, consultaríamos a tabela analises_emocionais
            List<Map<String, Object>> analyses = new ArrayList<>();
            
            Map<String, Object> analysis1 = new HashMap<>();
            analysis1.put("id", 1);
            analysis1.put("data", "2024-01-07");
            analysis1.put("risco", "moderado");
            analysis1.put("descricao", "Sono insuficiente e humor baixo detectados");
            analyses.add(analysis1);
            
            Map<String, Object> analysis2 = new HashMap<>();
            analysis2.put("id", 2);
            analysis2.put("data", "2024-01-06");
            analysis2.put("risco", "baixo");
            analysis2.put("descricao", "Indicadores dentro da normalidade");
            analyses.add(analysis2);
            
            return ResponseEntity.ok(analyses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Endpoint para obter recomendações recentes.
     */
    @GetMapping("/recommendations/{idIdoso}")
    public ResponseEntity<List<Map<String, Object>>> getRecommendations(@PathVariable long idIdoso) {
        try {
            // Por simplicidade, retornamos dados simulados
            // Em um sistema real, consultaríamos a tabela recomendacoes
            List<Map<String, Object>> recommendations = new ArrayList<>();
            
            Map<String, Object> rec1 = new HashMap<>();
            rec1.put("id", 1);
            rec1.put("recomendacao", "Pratique exercícios de respiração por 10 minutos");
            rec1.put("tipo", "moderado");
            recommendations.add(rec1);
            
            Map<String, Object> rec2 = new HashMap<>();
            rec2.put("id", 2);
            rec2.put("recomendacao", "Faça uma caminhada de 15-20 minutos");
            rec2.put("tipo", "moderado");
            recommendations.add(rec2);
            
            Map<String, Object> rec3 = new HashMap<>();
            rec3.put("id", 3);
            rec3.put("recomendacao", "Continue mantendo sua rotina saudável");
            rec3.put("tipo", "baixo");
            recommendations.add(rec3);
            
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Endpoint para verificar status dos agentes.
     */
    @GetMapping("/agents-status")
    public ResponseEntity<Map<String, Object>> getAgentsStatus() {
        try {
            Map<String, Object> status = new HashMap<>();
            
            Map<String, Object> coletaAgent = new HashMap<>();
            coletaAgent.put("name", "Agente de Coleta");
            coletaAgent.put("status", "ativo");
            coletaAgent.put("description", "Coletando dados a cada 10s");
            
            Map<String, Object> analysisAgent = new HashMap<>();
            analysisAgent.put("name", "Agente Analisador");
            analysisAgent.put("status", "ativo");
            analysisAgent.put("description", "Analisando a cada 15s");
            
            Map<String, Object> recommendationAgent = new HashMap<>();
            recommendationAgent.put("name", "Agente de Recomendação");
            recommendationAgent.put("status", "ativo");
            recommendationAgent.put("description", "Gerando sugestões a cada 20s");
            
            List<Map<String, Object>> agents = new ArrayList<>();
            agents.add(coletaAgent);
            agents.add(analysisAgent);
            agents.add(recommendationAgent);
            
            status.put("agents", agents);
            status.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

