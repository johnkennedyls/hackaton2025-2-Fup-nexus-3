"use client"

import { useEffect, useState } from "react"
import { getComprehensiveKpis } from "@/lib/api-client"
import { Card } from "@/components/ui/card"
import { KpiStats } from "./kpi-stats"

interface ComprehensiveKpi {
  totalSales: number
  totalQuantity: number
  averageOrderValue: number
  storesActive: number
  ordersCount: number
  averageOrderSize: number
}

interface ComprehensiveKpisProps {
  storeId?: number
  startDate: string
  endDate: string
}

export function ComprehensiveKpis({ storeId, startDate, endDate }: ComprehensiveKpisProps) {
  const [kpis, setKpis] = useState<ComprehensiveKpi | null>(null)
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const fetchKpis = async () => {
      setIsLoading(true)
      setError(null)

      try {
        const response = await getComprehensiveKpis({
          storeId,
          startDate,
          endDate,
        })
        setKpis(response.data)
      } catch (err: any) {
        setError(err.response?.data?.message || "Error al cargar KPIs")
      } finally {
        setIsLoading(false)
      }
    }

    fetchKpis()
  }, [storeId, startDate, endDate])

  if (isLoading) {
    return (
      <Card className="p-6">
        <div className="text-center text-muted-foreground">Cargando KPIs...</div>
      </Card>
    )
  }

  if (error) {
    return (
      <Card className="p-6">
        <div className="text-center text-destructive">{error}</div>
      </Card>
    )
  }

  if (!kpis) {
    return null
  }

  return (
    <div className="space-y-6">
      <KpiStats
        totalSales={kpis.totalSales}
        totalQuantity={kpis.totalQuantity}
        averageOrderValue={kpis.averageOrderValue}
        storesActive={kpis.storesActive}
      />

      <Card className="p-6">
        <h3 className="text-lg font-semibold mb-4">Análisis detallado</h3>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div>
            <p className="text-sm text-muted-foreground">{"Total de órdenes"}</p>
            <p className="text-2xl font-bold mt-2">{kpis.ordersCount}</p>
          </div>
          <div>
            <p className="text-sm text-muted-foreground">{"Tamaño promedio de orden"}</p>
            <p className="text-2xl font-bold mt-2">{kpis.averageOrderSize.toFixed(2)} unidades</p>
          </div>
          <div>
            <p className="text-sm text-muted-foreground">{"Margen promedio por orden"}</p>
            <p className="text-2xl font-bold mt-2">
              $
              {((kpis.totalSales / Math.max(kpis.ordersCount, 1)) * 0.35).toLocaleString("es-AR", {
                minimumFractionDigits: 2,
              })}
            </p>
          </div>
        </div>
      </Card>
    </div>
  )
}
