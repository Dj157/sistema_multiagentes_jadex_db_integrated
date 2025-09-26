// Serviço para consumir a API do backend Jadex
const API_BASE_URL = 'http://localhost:8080/api';

class ApiService {
  
  /**
   * Busca dados de saúde recentes de um idoso
   */
  async getHealthData(idIdoso = 1) {
    try {
      const response = await fetch(`${API_BASE_URL}/health-data/${idIdoso}`);
      if (!response.ok) {
        throw new Error('Erro ao buscar dados de saúde');
      }
      return await response.json();
    } catch (error) {
      console.error('Erro na API getHealthData:', error);
      return this.getMockHealthData(); // Fallback para dados simulados
    }
  }

  /**
   * Busca os dados mais recentes de um idoso
   */
  async getLatestData(idIdoso = 1) {
    try {
      const response = await fetch(`${API_BASE_URL}/latest-data/${idIdoso}`);
      if (!response.ok) {
        throw new Error('Erro ao buscar dados mais recentes');
      }
      return await response.json();
    } catch (error) {
      console.error('Erro na API getLatestData:', error);
      return this.getMockLatestData(); // Fallback para dados simulados
    }
  }

  /**
   * Busca lista de pacientes/idosos
   */
  async getPatients() {
    try {
      const response = await fetch(`${API_BASE_URL}/patients`);
      if (!response.ok) {
        throw new Error('Erro ao buscar pacientes');
      }
      return await response.json();
    } catch (error) {
      console.error('Erro na API getPatients:', error);
      return this.getMockPatients(); // Fallback para dados simulados
    }
  }

  /**
   * Busca estatísticas de risco
   */
  async getRiskStats() {
    try {
      const response = await fetch(`${API_BASE_URL}/risk-stats`);
      if (!response.ok) {
        throw new Error('Erro ao buscar estatísticas de risco');
      }
      return await response.json();
    } catch (error) {
      console.error('Erro na API getRiskStats:', error);
      return this.getMockRiskStats(); // Fallback para dados simulados
    }
  }

  /**
   * Busca análises emocionais recentes
   */
  async getAnalyses(idIdoso = 1) {
    try {
      const response = await fetch(`${API_BASE_URL}/analyses/${idIdoso}`);
      if (!response.ok) {
        throw new Error('Erro ao buscar análises');
      }
      return await response.json();
    } catch (error) {
      console.error('Erro na API getAnalyses:', error);
      return this.getMockAnalyses(); // Fallback para dados simulados
    }
  }

  /**
   * Busca recomendações recentes
   */
  async getRecommendations(idIdoso = 1) {
    try {
      const response = await fetch(`${API_BASE_URL}/recommendations/${idIdoso}`);
      if (!response.ok) {
        throw new Error('Erro ao buscar recomendações');
      }
      return await response.json();
    } catch (error) {
      console.error('Erro na API getRecommendations:', error);
      return this.getMockRecommendations(); // Fallback para dados simulados
    }
  }

  /**
   * Busca status dos agentes
   */
  async getAgentsStatus() {
    try {
      const response = await fetch(`${API_BASE_URL}/agents-status`);
      if (!response.ok) {
        throw new Error('Erro ao buscar status dos agentes');
      }
      return await response.json();
    } catch (error) {
      console.error('Erro na API getAgentsStatus:', error);
      return this.getMockAgentsStatus(); // Fallback para dados simulados
    }
  }

  // Métodos de fallback com dados simulados (para quando a API não estiver disponível)

  getMockHealthData() {
    return [
      { date: '2024-01-01', sono: 7.5, humor: 8, atividade: 45, fc: 72 },
      { date: '2024-01-02', sono: 6.8, humor: 6, atividade: 30, fc: 78 },
      { date: '2024-01-03', sono: 8.2, humor: 9, atividade: 60, fc: 70 },
      { date: '2024-01-04', sono: 5.5, humor: 4, atividade: 15, fc: 85 },
      { date: '2024-01-05', sono: 7.0, humor: 7, atividade: 40, fc: 75 },
      { date: '2024-01-06', sono: 8.5, humor: 9, atividade: 55, fc: 68 },
      { date: '2024-01-07', sono: 6.2, humor: 5, atividade: 25, fc: 82 }
    ];
  }

  getMockLatestData() {
    return { date: '2024-01-07', sono: 6.2, humor: 5, atividade: 25, fc: 82 };
  }

  getMockPatients() {
    return [
      { id: 1, nome: 'João Silva', idade: 70, sexo: 'M' },
      { id: 2, nome: 'Maria Oliveira', idade: 75, sexo: 'F' }
    ];
  }

  getMockRiskStats() {
    return { baixo: 60, moderado: 30, alto: 10 };
  }

  getMockAnalyses() {
    return [
      { id: 1, data: '2024-01-07', risco: 'moderado', descricao: 'Sono insuficiente e humor baixo detectados' },
      { id: 2, data: '2024-01-06', risco: 'baixo', descricao: 'Indicadores dentro da normalidade' },
      { id: 3, data: '2024-01-05', risco: 'baixo', descricao: 'Bom padrão de sono e atividade' },
      { id: 4, data: '2024-01-04', risco: 'alto', descricao: 'Múltiplos indicadores de risco detectados' }
    ];
  }

  getMockRecommendations() {
    return [
      { id: 1, recomendacao: 'Pratique exercícios de respiração por 10 minutos', tipo: 'moderado' },
      { id: 2, recomendacao: 'Faça uma caminhada de 15-20 minutos', tipo: 'moderado' },
      { id: 3, recomendacao: 'Continue mantendo sua rotina saudável', tipo: 'baixo' },
      { id: 4, recomendacao: 'Entre em contato com um familiar ou cuidador', tipo: 'alto' }
    ];
  }

  getMockAgentsStatus() {
    return {
      agents: [
        { name: 'Agente de Coleta', status: 'ativo', description: 'Coletando dados a cada 10s' },
        { name: 'Agente Analisador', status: 'ativo', description: 'Analisando a cada 15s' },
        { name: 'Agente de Recomendação', status: 'ativo', description: 'Gerando sugestões a cada 20s' }
      ],
      timestamp: Date.now()
    };
  }
}

export default new ApiService();

