"use client"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Card } from "@/components/ui/card"
import { Calendar } from "lucide-react"

interface KpiFiltersProps {
  onFilterChange: (filters: FilterState) => void
  loading?: boolean
}

export interface FilterState {
  storeId?: number
  startDate: string
  endDate: string
}

export function KpiFilters({ onFilterChange, loading = false }: KpiFiltersProps) {
  const now = new Date()
  const yearStart = new Date(now.getFullYear(), 0, 1)

  const [filters, setFilters] = useState<FilterState>({
    startDate: yearStart.toISOString().split("T")[0],
    endDate: now.toISOString().split("T")[0],
  })

  const [storeId, setStoreId] = useState<string>("")

  const handleApplyFilters = () => {
    onFilterChange({
      ...filters,
      storeId: storeId ? Number.parseInt(storeId) : undefined,
    })
  }

  const handlePreset = (days: number) => {
    const endDate = new Date()
    const startDate = new Date()
    startDate.setDate(startDate.getDate() - days)

    const newFilters = {
      ...filters,
      startDate: startDate.toISOString().split("T")[0],
      endDate: endDate.toISOString().split("T")[0],
    }
    setFilters(newFilters)
    onFilterChange({
      ...newFilters,
      storeId: storeId ? Number.parseInt(storeId) : undefined,
    })
  }

  return (
    <Card className="p-6 bg-gradient-to-r from-primary/5 to-accent/5">
      <h3 className="text-lg font-semibold mb-4">Filtros</h3>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <div>
          <label className="block text-sm font-medium mb-2">Tienda</label>
          <select
            value={storeId}
            onChange={(e) => setStoreId(e.target.value)}
            className="w-full px-3 py-2 border border-border rounded-md bg-background text-foreground"
          >
            <option value="">Todas las tiendas</option>
            <option value="1">{"Tienda 1 - FUP Principal"}</option>
            <option value="2">{"Tienda 2 - Centro Comercial"}</option>
            <option value="3">{"Tienda 3 - Outlet Sur"}</option>
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium mb-2">Fecha inicio</label>
          <div className="flex items-center">
            <Calendar className="w-4 h-4 mr-2 text-muted-foreground" />
            <input
              type="date"
              value={filters.startDate}
              onChange={(e) => setFilters({ ...filters, startDate: e.target.value })}
              className="flex-1 px-3 py-2 border border-border rounded-md bg-background text-foreground"
            />
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium mb-2">Fecha fin</label>
          <div className="flex items-center">
            <Calendar className="w-4 h-4 mr-2 text-muted-foreground" />
            <input
              type="date"
              value={filters.endDate}
              onChange={(e) => setFilters({ ...filters, endDate: e.target.value })}
              className="flex-1 px-3 py-2 border border-border rounded-md bg-background text-foreground"
            />
          </div>
        </div>

        <div className="flex items-end">
          <Button onClick={handleApplyFilters} disabled={loading} className="w-full">
            {loading ? "Cargando..." : "Aplicar filtros"}
          </Button>
        </div>
      </div>

      <div className="mt-4 flex flex-wrap gap-2">
        <span className="text-sm text-muted-foreground">Presets:</span>
        <Button size="sm" variant="outline" onClick={() => handlePreset(7)} disabled={loading}>
          {"Últimos 7 días"}
        </Button>
        <Button size="sm" variant="outline" onClick={() => handlePreset(30)} disabled={loading}>
          {"Últimos 30 días"}
        </Button>
        <Button size="sm" variant="outline" onClick={() => handlePreset(90)} disabled={loading}>
          {"Últimos 90 días"}
        </Button>
      </div>
    </Card>
  )
}
