"use client"

import { Card } from "@/components/ui/card"
import { TrendingUp, DollarSign, Package, Store } from "lucide-react"

interface KpiStatsProps {
  totalSales: number
  totalQuantity: number
  averageOrderValue: number
  storesActive: number
}

export function KpiStats({ totalSales, totalQuantity, averageOrderValue, storesActive }: KpiStatsProps) {
  const stats = [
    {
      label: "Ventas totales",
      value: `$${totalSales.toLocaleString("es-AR", {
        minimumFractionDigits: 2,
      })}`,
      icon: DollarSign,
      color: "bg-blue-500/10 text-blue-600",
    },
    {
      label: "Cantidad vendida",
      value: totalQuantity.toLocaleString(),
      icon: Package,
      color: "bg-green-500/10 text-green-600",
    },
    {
      label: "Ticket promedio",
      value: `$${averageOrderValue.toLocaleString("es-AR", {
        minimumFractionDigits: 2,
      })}`,
      icon: TrendingUp,
      color: "bg-purple-500/10 text-purple-600",
    },
    {
      label: "Tiendas activas",
      value: storesActive,
      icon: Store,
      color: "bg-orange-500/10 text-orange-600",
    },
  ]

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
      {stats.map((stat) => {
        const Icon = stat.icon
        return (
          <Card key={stat.label} className="p-6">
            <div className="flex items-start justify-between">
              <div>
                <p className="text-sm text-muted-foreground">{stat.label}</p>
                <p className="text-2xl font-bold mt-2">{stat.value}</p>
              </div>
              <div className={`p-2 rounded-lg ${stat.color}`}>
                <Icon className="w-5 h-5" />
              </div>
            </div>
          </Card>
        )
      })}
    </div>
  )
}
