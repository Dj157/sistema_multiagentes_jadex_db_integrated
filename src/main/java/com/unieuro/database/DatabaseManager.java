package com.unieuro.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Gerenciador de banco de dados para o sistema de monitoramento de saúde mental.
 * Utiliza H2 para desenvolvimento e PostgreSQL para produção.
 */
public class DatabaseManager {
    
    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());
    private static DatabaseManager instance;
    private HikariDataSource dataSource;
    
    private DatabaseManager() {
        initializeDatabase();
    }
    
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    /**
     * Inicializa o banco de dados H2 para desenvolvimento.
     */
    private void initializeDatabase() {
        try {
            HikariConfig config = new HikariConfig();
            
            // Configuração para H2 (desenvolvimento)
            config.setJdbcUrl("jdbc:h2:mem:saude_mental;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
            config.setUsername("sa");
            config.setPassword("");
            config.setDriverClassName("org.h2.Driver");
            
            // Configurações do pool de conexões
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);
            
            dataSource = new HikariDataSource(config);
            
            // Cria as tabelas
            createTables();
            
            // Insere dados de exemplo
            insertSampleData();
            
            logger.info("Banco de dados H2 inicializado com sucesso!");
            
        } catch (Exception e) {
            logger.severe("Erro ao inicializar banco de dados: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Cria as tabelas do banco de dados.
     */
    private void createTables() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            
            // Tabela idosos
            String createIdosos = "CREATE TABLE IF NOT EXISTS idosos (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "nome VARCHAR(100) NOT NULL," +
                "idade INT NOT NULL," +
                "sexo CHAR(1)," +
                "data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            
            // Tabela dados_saude
            String createDadosSaude = "CREATE TABLE IF NOT EXISTS dados_saude (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "id_idoso BIGINT REFERENCES idosos(id)," +
                "data_coleta DATE NOT NULL," +
                "sono_horas DECIMAL(3,1)," +
                "qualidade_sono INT," +
                "humor VARCHAR(20)," +
                "atividade_fisica VARCHAR(50)," +
                "frequencia_cardiaca INT," +
                "observacoes TEXT" +
                ")";
            
            // Tabela analises_emocionais
            String createAnalises = "CREATE TABLE IF NOT EXISTS analises_emocionais (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "id_idoso BIGINT REFERENCES idosos(id)," +
                "data_analise TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "risco_emocional VARCHAR(20)," +
                "descricao TEXT" +
                ")";
            
            // Tabela recomendacoes
            String createRecomendacoes = "CREATE TABLE IF NOT EXISTS recomendacoes (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "id_idoso BIGINT REFERENCES idosos(id)," +
                "data_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "recomendacao VARCHAR(100)," +
                "tipo_risco VARCHAR(20)," +
                "observacoes TEXT" +
                ")";
            
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createIdosos);
                stmt.execute(createDadosSaude);
                stmt.execute(createAnalises);
                stmt.execute(createRecomendacoes);
            }
            
            logger.info("Tabelas criadas com sucesso!");
        }
    }
    
    /**
     * Insere dados de exemplo no banco.
     */
    private void insertSampleData() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            
            // Verificar se já existem dados
            String checkData = "SELECT COUNT(*) FROM idosos";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(checkData)) {
                if (rs.next() && rs.getInt(1) > 0) {
                    logger.info("Dados já existem no banco, pulando inserção de dados de exemplo.");
                    return;
                }
            }
            
            // Inserir idosos
            String insertIdosos = "INSERT INTO idosos (nome, idade, sexo) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertIdosos)) {
                // Usuário 1
                stmt.setString(1, "João Silva");
                stmt.setInt(2, 70);
                stmt.setString(3, "M");
                stmt.executeUpdate();
                
                // Usuário 2
                stmt.setString(1, "Maria Oliveira");
                stmt.setInt(2, 75);
                stmt.setString(3, "F");
                stmt.executeUpdate();
                
                // Usuário 3
                stmt.setString(1, "Carlos Santos");
                stmt.setInt(2, 68);
                stmt.setString(3, "M");
                stmt.executeUpdate();
                
                // Usuário 4
                stmt.setString(1, "Ana Costa");
                stmt.setInt(2, 72);
                stmt.setString(3, "F");
                stmt.executeUpdate();
                
                // Usuário 5
                stmt.setString(1, "Pedro Almeida");
                stmt.setInt(2, 77);
                stmt.setString(3, "M");
                stmt.executeUpdate();
            }
            
            logger.info("Dados de exemplo inseridos com sucesso! 5 usuários cadastrados.");
        }
    }
    
    /**
     * Insere dados de saúde no banco.
     */
    public void inserirDadosSaude(long idIdoso, double sonoHoras, int qualidadeSono, 
                                  String humor, String atividadeFisica, int frequenciaCardiaca) {
        String sql = "INSERT INTO dados_saude (id_idoso, data_coleta, sono_horas, qualidade_sono, " +
                     "humor, atividade_fisica, frequencia_cardiaca) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, idIdoso);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setDouble(3, sonoHoras);
            stmt.setInt(4, qualidadeSono);
            stmt.setString(5, humor);
            stmt.setString(6, atividadeFisica);
            stmt.setInt(7, frequenciaCardiaca);
            
            stmt.executeUpdate();
            logger.info("Dados de saúde inseridos para idoso ID: " + idIdoso);
            
        } catch (SQLException e) {
            logger.severe("Erro ao inserir dados de saúde: " + e.getMessage());
        }
    }
    
    /**
     * Busca dados de saúde recentes de um idoso.
     */
    public List<Map<String, Object>> buscarDadosSaudeRecentes(long idIdoso, int dias) {
        String sql = "SELECT * FROM dados_saude " +
                     "WHERE id_idoso = ? AND data_coleta >= DATEADD('DAY', ?, CURRENT_DATE) " +
                     "ORDER BY data_coleta DESC";
        
        List<Map<String, Object>> resultados = new ArrayList<>();
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, idIdoso);
            stmt.setInt(2, -dias);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> dados = new HashMap<>();
                    dados.put("id", rs.getLong("id"));
                    dados.put("data_coleta", rs.getDate("data_coleta"));
                    dados.put("sono_horas", rs.getDouble("sono_horas"));
                    dados.put("qualidade_sono", rs.getInt("qualidade_sono"));
                    dados.put("humor", rs.getString("humor"));
                    dados.put("atividade_fisica", rs.getString("atividade_fisica"));
                    dados.put("frequencia_cardiaca", rs.getInt("frequencia_cardiaca"));
                    resultados.add(dados);
                }
            }
            
        } catch (SQLException e) {
            logger.severe("Erro ao buscar dados de saúde: " + e.getMessage());
        }
        
        return resultados;
    }
    
    /**
     * Insere análise emocional no banco.
     */
    public void inserirAnaliseEmocional(long idIdoso, String riscoEmocional, String descricao) {
        String sql = "INSERT INTO analises_emocionais (id_idoso, risco_emocional, descricao) " +
                     "VALUES (?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, idIdoso);
            stmt.setString(2, riscoEmocional);
            stmt.setString(3, descricao);
            
            stmt.executeUpdate();
            logger.info("Análise emocional inserida para idoso ID: " + idIdoso);
            
        } catch (SQLException e) {
            logger.severe("Erro ao inserir análise emocional: " + e.getMessage());
        }
    }
    
    /**
     * Insere recomendação no banco.
     */
    public void inserirRecomendacao(long idIdoso, String recomendacao, String tipoRisco, String observacoes) {
        String sql = "INSERT INTO recomendacoes (id_idoso, recomendacao, tipo_risco, observacoes) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, idIdoso);
            stmt.setString(2, recomendacao);
            stmt.setString(3, tipoRisco);
            stmt.setString(4, observacoes);
            
            stmt.executeUpdate();
            logger.info("Recomendação inserida para idoso ID: " + idIdoso);
            
        } catch (SQLException e) {
            logger.severe("Erro ao inserir recomendação: " + e.getMessage());
        }
    }
    
    /**
     * Lista todos os idosos cadastrados.
     */
    public List<Map<String, Object>> listarIdosos() {
        String sql = "SELECT * FROM idosos ORDER BY nome";
        List<Map<String, Object>> idosos = new ArrayList<>();
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Map<String, Object> idoso = new HashMap<>();
                idoso.put("id", rs.getLong("id"));
                idoso.put("nome", rs.getString("nome"));
                idoso.put("idade", rs.getInt("idade"));
                idoso.put("sexo", rs.getString("sexo"));
                idoso.put("data_cadastro", rs.getTimestamp("data_cadastro"));
                idosos.add(idoso);
            }
            
        } catch (SQLException e) {
            logger.severe("Erro ao listar idosos: " + e.getMessage());
        }
        
        return idosos;
    }
    
    /**
     * Fecha o pool de conexões.
     */
    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            logger.info("Pool de conexões fechado.");
        }
    }
}

