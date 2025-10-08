import { useState, useEffect } from 'react'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select'
import { 
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer,
  BarChart, Bar, PieChart, Pie, Cell, AreaChart, Area
} from 'recharts'
import { 
  Heart, Brain, Activity, Moon, Smile, AlertTriangle, 
  TrendingUp, TrendingDown, Users, Calendar, Clock, User
} from 'lucide-react'
import './App.css'

// Dados simulados para demonstração (fallback quando API não está disponível)
const mockHealthData = [
  { date: '2024-01-01', sono: 7.5, humor: 8, atividade: 45, fc: 72 },
  { date: '2024-01-02', sono: 6.8, humor: 6, atividade: 30, fc: 78 },
  { date: '2024-01-03', sono: 8.2, humor: 9, atividade: 60, fc: 70 },
  { date: '2024-01-04', sono: 5.5, humor: 4, atividade: 15, fc: 85 },
  { date: '2024-01-05', sono: 7.0, humor: 7, atividade: 40, fc: 75 },
  { date: '2024-01-06', sono: 8.5, humor: 9, atividade: 55, fc: 68 },
  { date: '2024-01-07', sono: 6.2, humor: 5, atividade: 25, fc: 82 }
]

const mockAnalyses = [
  { id: 1, data: '2024-01-07', risco: 'moderado', descricao: 'Sono insuficiente e humor baixo detectados' },
  { id: 2, data: '2024-01-06', risco: 'baixo', descricao: 'Indicadores dentro da normalidade' },
  { id: 3, data: '2024-01-05', risco: 'baixo', descricao: 'Bom padrão de sono e atividade' },
  { id: 4, data: '2024-01-04', risco: 'alto', descricao: 'Múltiplos indicadores de risco detectados' }
]

const mockRecommendations = [
  { id: 1, recomendacao: 'Pratique exercícios de respiração por 10 minutos', tipo: 'moderado' },
  { id: 2, recomendacao: 'Faça uma caminhada de 15-20 minutos', tipo: 'moderado' },
  { id: 3, recomendacao: 'Continue mantendo sua rotina saudável', tipo: 'baixo' },
  { id: 4, recomendacao: 'Entre em contato com um familiar ou cuidador', tipo: 'alto' }
]

const riskDistribution = [
  { name: 'Baixo', value: 60, color: '#22c55e' },
  { name: 'Moderado', value: 30, color: '#f59e0b' },
  { name: 'Alto', value: 10, color: '#ef4444' }
]

function App() {
  const [patients, setPatients] = useState([])
  const [selectedPatient, setSelectedPatient] = useState(null)
  const [currentTime, setCurrentTime] = useState(new Date())
  const [healthData, setHealthData] = useState(mockHealthData)
  const [latestData, setLatestData] = useState(null)
  const [analyses, setAnalyses] = useState(mockAnalyses)
  const [recommendations, setRecommendations] = useState(mockRecommendations)
  const [riskStats, setRiskStats] = useState(riskDistribution)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const timer = setInterval(() => setCurrentTime(new Date()), 1000)
    return () => clearInterval(timer)
  }, [])

  // Carregar lista de pacientes
  useEffect(() => {
    const loadPatients = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/patients')
        if (response.ok) {
          const data = await response.json()
          setPatients(data)
          if (data.length > 0 && !selectedPatient) {
            setSelectedPatient(data[0])
          }
        } else {
          // Fallback para dados simulados
          const fallbackPatients = [
            { id: 1, nome: 'João Silva', idade: 70, sexo: 'M' },
            { id: 2, nome: 'Maria Oliveira', idade: 75, sexo: 'F' },
            { id: 3, nome: 'Carlos Santos', idade: 68, sexo: 'M' },
            { id: 4, nome: 'Ana Costa', idade: 72, sexo: 'F' },
            { id: 5, nome: 'Pedro Almeida', idade: 77, sexo: 'M' }
          ]
          setPatients(fallbackPatients)
          setSelectedPatient(fallbackPatients[0])
        }
      } catch (error) {
        console.error('Erro ao carregar pacientes:', error)
        // Fallback para dados simulados
        const fallbackPatients = [
          { id: 1, nome: 'João Silva', idade: 70, sexo: 'M' },
          { id: 2, nome: 'Maria Oliveira', idade: 75, sexo: 'F' },
          { id: 3, nome: 'Carlos Santos', idade: 68, sexo: 'M' },
          { id: 4, nome: 'Ana Costa', idade: 72, sexo: 'F' },
          { id: 5, nome: 'Pedro Almeida', idade: 77, sexo: 'M' }
        ]
        setPatients(fallbackPatients)
        setSelectedPatient(fallbackPatients[0])
      } finally {
        setLoading(false)
      }
    }

    loadPatients()
  }, [])

  // Carregar dados do paciente selecionado
  useEffect(() => {
    if (!selectedPatient) return

    const loadPatientData = async () => {
      try {
        // Carregar dados de tendências
        const trendsResponse = await fetch(`http://localhost:8080/api/trends/${selectedPatient.id}`)
        if (trendsResponse.ok) {
          const trendsData = await trendsResponse.json()
          setHealthData(trendsData)
        }

        // Carregar dados mais recentes
        const latestResponse = await fetch(`http://localhost:8080/api/latest-data/${selectedPatient.id}`)
        if (latestResponse.ok) {
          const latestDataResponse = await latestResponse.json()
          setLatestData(latestDataResponse)
        }

        // Carregar análises
        const analysesResponse = await fetch(`http://localhost:8080/api/analyses/${selectedPatient.id}`)
        if (analysesResponse.ok) {
          const analysesData = await analysesResponse.json()
          setAnalyses(analysesData)
        }

        // Carregar recomendações
        const recommendationsResponse = await fetch(`http://localhost:8080/api/recommendations/${selectedPatient.id}`)
        if (recommendationsResponse.ok) {
          const recommendationsData = await recommendationsResponse.json()
          setRecommendations(recommendationsData)
        }

        // Carregar estatísticas de risco
        const riskResponse = await fetch(`http://localhost:8080/api/risk-stats/${selectedPatient.id}`)
        if (riskResponse.ok) {
          const riskData = await riskResponse.json()
          const riskArray = [
            { name: 'Baixo', value: riskData.baixo, color: '#22c55e' },
            { name: 'Moderado', value: riskData.moderado, color: '#f59e0b' },
            { name: 'Alto', value: riskData.alto, color: '#ef4444' }
          ]
          setRiskStats(riskArray)
        }

      } catch (error) {
        console.error('Erro ao carregar dados do paciente:', error)
        // Manter dados simulados como fallback
      }
    }

    loadPatientData()
  }, [selectedPatient])

  const getRiskColor = (risk) => {
    switch (risk) {
      case 'baixo': return 'bg-green-100 text-green-800 border-green-200'
      case 'moderado': return 'bg-yellow-100 text-yellow-800 border-yellow-200'
      case 'alto': return 'bg-red-100 text-red-800 border-red-200'
      default: return 'bg-gray-100 text-gray-800 border-gray-200'
    }
  }

  const getLatestData = () => {
    if (latestData) return latestData
    const latest = healthData[healthData.length - 1]
    return latest
  }

  const currentLatestData = getLatestData()

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto"></div>
          <p className="mt-4 text-gray-600">Carregando dados...</p>
        </div>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-gray-50 p-4">
      <div className="max-w-7xl mx-auto space-y-6">
        {/* Header */}
        <div className="bg-white rounded-lg shadow-sm border p-6">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-3xl font-bold text-gray-900 flex items-center gap-2">
                <Brain className="h-8 w-8 text-blue-600" />
                Dashboard de Saúde Mental
              </h1>
              <p className="text-gray-600 mt-1">Sistema Multiagente Jadex - Monitoramento em Tempo Real</p>
            </div>
            <div className="flex items-center gap-4">
              <div className="text-right">
                <div className="flex items-center gap-2 text-sm text-gray-500">
                  <Clock className="h-4 w-4" />
                  {currentTime.toLocaleString('pt-BR')}
                </div>
              </div>
              <div className="flex items-center gap-2">
                <User className="h-4 w-4 text-blue-600" />
                <Select 
                  value={selectedPatient?.id?.toString()} 
                  onValueChange={(value) => {
                    const patient = patients.find(p => p.id.toString() === value)
                    setSelectedPatient(patient)
                  }}
                >
                  <SelectTrigger className="w-48">
                    <SelectValue placeholder="Selecionar paciente" />
                  </SelectTrigger>
                  <SelectContent>
                    {patients.map((patient) => (
                      <SelectItem key={patient.id} value={patient.id.toString()}>
                        {patient.nome} ({patient.idade} anos)
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
            </div>
          </div>
        </div>

        {/* Métricas Principais */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Sono (horas)</CardTitle>
              <Moon className="h-4 w-4 text-blue-600" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{currentLatestData?.sono_horas || currentLatestData?.sono || 0}h</div>
              <p className="text-xs text-muted-foreground">
                {(currentLatestData?.sono_horas || currentLatestData?.sono || 0) >= 7 ? (
                  <span className="text-green-600 flex items-center gap-1">
                    <TrendingUp className="h-3 w-3" /> Adequado
                  </span>
                ) : (
                  <span className="text-red-600 flex items-center gap-1">
                    <TrendingDown className="h-3 w-3" /> Insuficiente
                  </span>
                )}
              </p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Humor</CardTitle>
              <Smile className="h-4 w-4 text-yellow-600" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{currentLatestData?.humor || 'N/A'}</div>
              <p className="text-xs text-muted-foreground">
                {typeof currentLatestData?.humor === 'string' ? (
                  currentLatestData.humor === 'positivo' ? (
                    <span className="text-green-600">Positivo</span>
                  ) : currentLatestData.humor === 'neutro' ? (
                    <span className="text-yellow-600">Neutro</span>
                  ) : (
                    <span className="text-red-600">Negativo</span>
                  )
                ) : (
                  <span className="text-gray-600">-</span>
                )}
              </p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Atividade (min)</CardTitle>
              <Activity className="h-4 w-4 text-green-600" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{currentLatestData?.atividade_fisica || currentLatestData?.atividade || 'N/A'}</div>
              <p className="text-xs text-muted-foreground">
                {typeof currentLatestData?.atividade_fisica === 'string' ? (
                  currentLatestData.atividade_fisica === 'sedentaria' || currentLatestData.atividade_fisica === 'nenhuma' ? (
                    <span className="text-red-600">Sedentário</span>
                  ) : currentLatestData.atividade_fisica === 'intensa' || currentLatestData.atividade_fisica === 'moderada' ? (
                    <span className="text-green-600">Ativo</span>
                  ) : (
                    <span className="text-yellow-600">Leve</span>
                  )
                ) : (
                  <span className="text-gray-600">-</span>
                )}
              </p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Freq. Cardíaca</CardTitle>
              <Heart className="h-4 w-4 text-red-600" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{currentLatestData?.frequencia_cardiaca || currentLatestData?.fc || 0} bpm</div>
              <p className="text-xs text-muted-foreground">
                {(() => {
                  const fc = currentLatestData?.frequencia_cardiaca || currentLatestData?.fc || 0
                  return fc >= 60 && fc <= 90 ? (
                    <span className="text-green-600">Normal</span>
                  ) : (
                    <span className="text-red-600">Anormal</span>
                  )
                })()}
              </p>
            </CardContent>
          </Card>
        </div>

        {/* Tabs para diferentes visualizações */}
        <Tabs defaultValue="overview" className="space-y-4">
          <TabsList className="grid w-full grid-cols-4">
            <TabsTrigger value="overview">Visão Geral</TabsTrigger>
            <TabsTrigger value="trends">Tendências</TabsTrigger>
            <TabsTrigger value="analysis">Análises</TabsTrigger>
            <TabsTrigger value="recommendations">Recomendações</TabsTrigger>
          </TabsList>

          <TabsContent value="overview" className="space-y-4">
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-4">
              {/* Gráfico de Tendências */}
              <Card>
                <CardHeader>
                  <CardTitle>Tendências dos Últimos 7 Dias</CardTitle>
                  <CardDescription>Evolução dos indicadores de saúde</CardDescription>
                </CardHeader>
                <CardContent>
                  <ResponsiveContainer width="100%" height={300}>
                    <LineChart data={healthData}>
                      <CartesianGrid strokeDasharray="3 3" />
                      <XAxis dataKey="data" tickFormatter={(date) => new Date(date).toLocaleDateString('pt-BR')} />
                      <YAxis />
                      <Tooltip 
                        labelFormatter={(date) => new Date(date).toLocaleDateString('pt-BR')}
                        formatter={(value, name) => [
                          name === 'sono' ? `${value}h` : 
                          name === 'humor' ? `${value}/10` :
                          name === 'atividade' ? `${value}min` : `${value}bpm`,
                          name === 'sono' ? 'Sono' :
                          name === 'humor' ? 'Humor' :
                          name === 'atividade' ? 'Atividade' : 'Freq. Cardíaca'
                        ]}
                      />
                      <Legend />
                      <Line type="monotone" dataKey="sono" stroke="#3b82f6" name="Sono" />
                      <Line type="monotone" dataKey="humor" stroke="#f59e0b" name="Humor" />
                      <Line type="monotone" dataKey="atividade" stroke="#10b981" name="Atividade" />
                      <Line type="monotone" dataKey="frequencia_cardiaca" stroke="#ef4444" name="Freq. Cardíaca" />
                    </LineChart>
                  </ResponsiveContainer>
                </CardContent>
              </Card>

              {/* Distribuição de Riscos */}
              <Card>
                <CardHeader>
                  <CardTitle>Distribuição de Riscos</CardTitle>
                  <CardDescription>Análise dos últimos 30 dias</CardDescription>
                </CardHeader>
                <CardContent>
                  <ResponsiveContainer width="100%" height={300}>
                    <PieChart>
                      <Pie
                        data={riskStats}
                        cx="50%"
                        cy="50%"
                        labelLine={false}
                        label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
                        outerRadius={80}
                        fill="#8884d8"
                        dataKey="value"
                      >
                        {riskStats.map((entry, index) => (
                          <Cell key={`cell-${index}`} fill={entry.color} />
                        ))}
                      </Pie>
                      <Tooltip />
                    </PieChart>
                  </ResponsiveContainer>
                </CardContent>
              </Card>
            </div>
          </TabsContent>

          <TabsContent value="trends" className="space-y-4">
            <Card>
              <CardHeader>
                <CardTitle>Análise Detalhada de Tendências</CardTitle>
                <CardDescription>Correlações entre diferentes indicadores</CardDescription>
              </CardHeader>
              <CardContent>
                <ResponsiveContainer width="100%" height={400}>
                  <AreaChart data={healthData}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="data" tickFormatter={(date) => new Date(date).toLocaleDateString('pt-BR')} />
                    <YAxis />
                    <Tooltip 
                      labelFormatter={(date) => new Date(date).toLocaleDateString('pt-BR')}
                    />
                    <Legend />
                    <Area type="monotone" dataKey="sono" stackId="1" stroke="#3b82f6" fill="#3b82f6" fillOpacity={0.6} />
                    <Area type="monotone" dataKey="humor" stackId="2" stroke="#f59e0b" fill="#f59e0b" fillOpacity={0.6} />
                  </AreaChart>
                </ResponsiveContainer>
              </CardContent>
            </Card>
          </TabsContent>

          <TabsContent value="analysis" className="space-y-4">
            <Card>
              <CardHeader>
                <CardTitle>Análises Emocionais Recentes</CardTitle>
                <CardDescription>Resultados das análises dos agentes Jadex</CardDescription>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  {analyses.map((analysis) => (
                    <div key={analysis.id} className="flex items-center justify-between p-4 border rounded-lg">
                      <div className="flex items-center gap-3">
                        <AlertTriangle className={`h-5 w-5 ${
                          analysis.risco === 'alto' ? 'text-red-500' :
                          analysis.risco === 'moderado' ? 'text-yellow-500' : 'text-green-500'
                        }`} />
                        <div>
                          <p className="font-medium">{analysis.descricao}</p>
                          <p className="text-sm text-gray-500">{new Date(analysis.data).toLocaleDateString('pt-BR')}</p>
                        </div>
                      </div>
                      <Badge className={getRiskColor(analysis.risco)}>
                        {analysis.risco.toUpperCase()}
                      </Badge>
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>
          </TabsContent>

          <TabsContent value="recommendations" className="space-y-4">
            <Card>
              <CardHeader>
                <CardTitle>Recomendações Personalizadas</CardTitle>
                <CardDescription>Sugestões geradas pelos agentes de recomendação</CardDescription>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  {recommendations.map((rec) => (
                    <div key={rec.id} className="p-4 border rounded-lg">
                      <div className="flex items-start justify-between">
                        <p className="font-medium flex-1">{rec.recomendacao}</p>
                        <Badge className={getRiskColor(rec.tipo)}>
                          {rec.tipo.toUpperCase()}
                        </Badge>
                      </div>
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>
          </TabsContent>
        </Tabs>

        {/* Status dos Agentes */}
        <Card>
          <CardHeader>
            <CardTitle>Status dos Agentes Jadex</CardTitle>
            <CardDescription>Monitoramento em tempo real dos agentes multiagente</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              <div className="flex items-center gap-3 p-3 border rounded-lg">
                <div className="w-3 h-3 bg-green-500 rounded-full animate-pulse"></div>
                <div>
                  <p className="font-medium">Agente de Coleta</p>
                  <p className="text-sm text-gray-500">Coletando dados a cada 10s</p>
                </div>
              </div>
              <div className="flex items-center gap-3 p-3 border rounded-lg">
                <div className="w-3 h-3 bg-green-500 rounded-full animate-pulse"></div>
                <div>
                  <p className="font-medium">Agente Analisador</p>
                  <p className="text-sm text-gray-500">Analisando a cada 15s</p>
                </div>
              </div>
              <div className="flex items-center gap-3 p-3 border rounded-lg">
                <div className="w-3 h-3 bg-green-500 rounded-full animate-pulse"></div>
                <div>
                  <p className="font-medium">Agente de Recomendação</p>
                  <p className="text-sm text-gray-500">Gerando sugestões a cada 20s</p>
                </div>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}

export default App

