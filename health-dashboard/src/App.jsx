import { useState, useEffect } from 'react'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
import { 
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer,
  BarChart, Bar, PieChart, Pie, Cell, AreaChart, Area
} from 'recharts'
import { 
  Heart, Brain, Activity, Moon, Smile, AlertTriangle, 
  TrendingUp, TrendingDown, Users, Calendar, Clock
} from 'lucide-react'
import './App.css'

// Dados simulados para demonstração
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
  const [selectedPatient, setSelectedPatient] = useState('João Silva')
  const [currentTime, setCurrentTime] = useState(new Date())

  useEffect(() => {
    const timer = setInterval(() => setCurrentTime(new Date()), 1000)
    return () => clearInterval(timer)
  }, [])

  const getRiskColor = (risk) => {
    switch (risk) {
      case 'baixo': return 'bg-green-100 text-green-800 border-green-200'
      case 'moderado': return 'bg-yellow-100 text-yellow-800 border-yellow-200'
      case 'alto': return 'bg-red-100 text-red-800 border-red-200'
      default: return 'bg-gray-100 text-gray-800 border-gray-200'
    }
  }

  const getLatestData = () => {
    const latest = mockHealthData[mockHealthData.length - 1]
    return latest
  }

  const latestData = getLatestData()

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
            <div className="text-right">
              <div className="flex items-center gap-2 text-sm text-gray-500">
                <Clock className="h-4 w-4" />
                {currentTime.toLocaleString('pt-BR')}
              </div>
              <div className="flex items-center gap-2 mt-1">
                <Users className="h-4 w-4 text-blue-600" />
                <span className="font-medium">{selectedPatient}</span>
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
              <div className="text-2xl font-bold">{latestData.sono}h</div>
              <p className="text-xs text-muted-foreground">
                {latestData.sono >= 7 ? (
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
              <div className="text-2xl font-bold">{latestData.humor}/10</div>
              <p className="text-xs text-muted-foreground">
                {latestData.humor >= 7 ? (
                  <span className="text-green-600">Positivo</span>
                ) : latestData.humor >= 5 ? (
                  <span className="text-yellow-600">Neutro</span>
                ) : (
                  <span className="text-red-600">Negativo</span>
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
              <div className="text-2xl font-bold">{latestData.atividade}</div>
              <p className="text-xs text-muted-foreground">
                {latestData.atividade >= 30 ? (
                  <span className="text-green-600">Ativo</span>
                ) : (
                  <span className="text-red-600">Sedentário</span>
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
              <div className="text-2xl font-bold">{latestData.fc} bpm</div>
              <p className="text-xs text-muted-foreground">
                {latestData.fc >= 60 && latestData.fc <= 90 ? (
                  <span className="text-green-600">Normal</span>
                ) : (
                  <span className="text-red-600">Anormal</span>
                )}
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
                    <LineChart data={mockHealthData}>
                      <CartesianGrid strokeDasharray="3 3" />
                      <XAxis dataKey="date" tickFormatter={(date) => new Date(date).toLocaleDateString('pt-BR')} />
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
                      <Line type="monotone" dataKey="fc" stroke="#ef4444" name="Freq. Cardíaca" />
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
                        data={riskDistribution}
                        cx="50%"
                        cy="50%"
                        labelLine={false}
                        label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
                        outerRadius={80}
                        fill="#8884d8"
                        dataKey="value"
                      >
                        {riskDistribution.map((entry, index) => (
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
                  <AreaChart data={mockHealthData}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="date" tickFormatter={(date) => new Date(date).toLocaleDateString('pt-BR')} />
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
                  {mockAnalyses.map((analysis) => (
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
                  {mockRecommendations.map((rec) => (
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

