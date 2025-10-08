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
 * Suporta múltiplos usuários e fornece endpoints para seleção e visualização de dados.
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
     * Endpoint raiz para verificar se a API está rodando.
     */
    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("API de Monitoramento de Saúde Mental está rodando!");
    }
    
    /**
     * Endpoint para obter lista de todos os idosos cadastrados.
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
     * Endpoint para obter dados de saúde recentes de um idoso específico.
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
     * Endpoint para obter dados de saúde mais recentes de um idoso específico.
     */
    @GetMapping("/latest-data/{idIdoso}")
    public ResponseEntity<Map<String, Object>> getLatestData(@PathVariable long idIdoso) {
        try {
            List<Map<String, Object>> dados = dbManager.buscarDadosSaudeRecentes(idIdoso, 1);
            if (!dados.isEmpty()) {
                return ResponseEntity.ok(dados.get(0));
            } else {
                // Retorna dados padrão se não houver dados
                Map<String, Object> defaultData = new HashMap<>();
                defaultData.put("sono_horas", 7.0);
                defaultData.put("humor", "neutro");
                defaultData.put("atividade_fisica", "leve");
                defaultData.put("frequencia_cardiaca", 75);
                return ResponseEntity.ok(defaultData);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Endpoint para obter estatísticas de risco de um idoso específico.
     */
    @GetMapping("/risk-stats/{idIdoso}")
    public ResponseEntity<Map<String, Object>> getRiskStats(@PathVariable long idIdoso) {
        try {
            // Simulação de estatísticas de risco baseadas no usuário
            Map<String, Object> stats = new HashMap<>();
            
            // Variação baseada no ID do usuário para simular dados diferentes
            int seed = (int) (idIdoso % 5);
            switch (seed) {
                case 0:
                    stats.put("baixo", 70);
                    stats.put("moderado", 25);
                    stats.put("alto", 5);
                    break;
                case 1:
                    stats.put("baixo", 50);
                    stats.put("moderado", 35);
                    stats.put("alto", 15);
                    break;
                case 2:
                    stats.put("baixo", 60);
                    stats.put("moderado", 30);
                    stats.put("alto", 10);
                    break;
                case 3:
                    stats.put("baixo", 45);
                    stats.put("moderado", 40);
                    stats.put("alto", 15);
                    break;
                default:
                    stats.put("baixo", 65);
                    stats.put("moderado", 25);
                    stats.put("alto", 10);
                    break;
            }
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Endpoint para obter análises emocionais recentes de um idoso específico.
     */
    @GetMapping("/analyses/{idIdoso}")
    public ResponseEntity<List<Map<String, Object>>> getAnalyses(@PathVariable long idIdoso) {
        try {
            // Por simplicidade, retornamos dados simulados baseados no usuário
            // Em um sistema real, consultaríamos a tabela analises_emocionais
            List<Map<String, Object>> analyses = new ArrayList<>();
            
            // Gera análises simuladas baseadas no ID do usuário
            String[] riscos = {"baixo", "moderado", "alto"};
            String[] descricoes = {
                "Indicadores dentro da normalidade",
                "Sono insuficiente detectado",
                "Bom padrão de sono e atividade",
                "Múltiplos indicadores de risco detectados",
                "Humor baixo identificado"
            };
            
            for (int i = 0; i < 4; i++) {
                Map<String, Object> analysis = new HashMap<>();
                analysis.put("id", i + 1);
                analysis.put("data", String.format("2024-01-%02d", 7 - i));
                analysis.put("risco", riscos[(int) ((idIdoso + i) % riscos.length)]);
                analysis.put("descricao", descricoes[(int) ((idIdoso + i) % descricoes.length)]);
                analyses.add(analysis);
            }
            
            return ResponseEntity.ok(analyses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Endpoint para obter recomendações recentes de um idoso específico.
     */
    @GetMapping("/recommendations/{idIdoso}")
    public ResponseEntity<List<Map<String, Object>>> getRecommendations(@PathVariable long idIdoso) {
        try {
            // Por simplicidade, retornamos dados simulados baseados no usuário
            // Em um sistema real, consultaríamos a tabela recomendacoes
            List<Map<String, Object>> recommendations = new ArrayList<>();
            
            String[] recomendacoesBaixo = {
                "Continue mantendo sua rotina saudável",
                "Pratique exercícios de respiração por 5 minutos",
                "Ouça música relaxante"
            };
            
            String[] recomendacoesModerado = {
                "Pratique exercícios de respiração por 10 minutos",
                "Faça uma caminhada de 15-20 minutos",
                "Pratique meditação ou mindfulness"
            };
            
            String[] recomendacoesAlto = {
                "Entre em contato com um familiar ou cuidador",
                "Considere conversar com um profissional de saúde",
                "Evite ficar sozinho por longos períodos"
            };
            
            // Gera recomendações baseadas no ID do usuário
            String[] tipos = {"baixo", "moderado", "alto"};
            for (int i = 0; i < 4; i++) {
                Map<String, Object> recommendation = new HashMap<>();
                recommendation.put("id", i + 1);
                
                String tipo = tipos[(int) ((idIdoso + i) % tipos.length)];
                recommendation.put("tipo", tipo);
                
                String recomendacao;
                switch (tipo) {
                    case "baixo":
                        recomendacao = recomendacoesBaixo[i % recomendacoesBaixo.length];
                        break;
                    case "moderado":
                        recomendacao = recomendacoesModerado[i % recomendacoesModerado.length];
                        break;
                    default:
                        recomendacao = recomendacoesAlto[i % recomendacoesAlto.length];
                        break;
                }
                
                recommendation.put("recomendacao", recomendacao);
                recommendations.add(recommendation);
            }
            
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Endpoint para obter dados de tendências de um idoso específico.
     */
    @GetMapping("/trends/{idIdoso}")
    public ResponseEntity<List<Map<String, Object>>> getTrends(@PathVariable long idIdoso) {
        try {
            List<Map<String, Object>> trends = new ArrayList<>();
            
            // Gera dados de tendência simulados para os últimos 7 dias
            for (int i = 6; i >= 0; i--) {
                Map<String, Object> trend = new HashMap<>();
                trend.put("data", String.format("2024-01-%02d", 7 - i));
                
                // Varia os dados baseado no ID do usuário e dia
                double baseSono = 6.5 + (idIdoso % 3) * 0.5;
                double baseHumor = 5 + (idIdoso % 3);
                double baseAtividade = 20 + (idIdoso % 4) * 10;
                double baseFC = 70 + (idIdoso % 3) * 5;
                
                trend.put("sono", Math.round((baseSono + Math.sin(i) * 1.5) * 10.0) / 10.0);
                trend.put("humor", Math.max(1, Math.min(10, Math.round(baseHumor + Math.cos(i) * 2))));
                trend.put("atividade", Math.max(0, Math.round(baseAtividade + Math.sin(i * 0.5) * 15)));
                trend.put("frequencia_cardiaca", Math.max(60, Math.round(baseFC + Math.cos(i * 0.7) * 10)));
                
                trends.add(trend);
            }
            
            return ResponseEntity.ok(trends);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

