"use client"

import { useState } from "react"
import { Card } from "@/components/ui/card"
import { FileUpload, type UploadResult } from "@/components/file-upload"
import { KpiFilters, type FilterState } from "@/components/kpi-filters"
import { TopProductsTable } from "@/components/top-products-table"
import { ProductRotationTable } from "@/components/product-rotation-table"
import { ComprehensiveKpis } from "@/components/comprehensive-kpis"
import { BarChart3 } from "lucide-react"

export default function Home() {
  const now = new Date()
  const yearStart = new Date(now.getFullYear(), 0, 1)

  const [filters, setFilters] = useState<FilterState>({
    startDate: yearStart.toISOString().split("T")[0],
    endDate: now.toISOString().split("T")[0],
  })

  const [refreshKey, setRefreshKey] = useState(0)
  const [isFilterLoading, setIsFilterLoading] = useState(false)

  const handleFilterChange = (newFilters: FilterState) => {
    setIsFilterLoading(true)
    setFilters(newFilters)
    setTimeout(() => setIsFilterLoading(false), 500)
  }

  const handleUploadComplete = (result: UploadResult) => {
    if (result.errorCount === 0) {
      setRefreshKey((k) => k + 1)
    }
  }

  return (
    <main className="min-h-screen bg-gradient-to-b from-background to-muted/20">
      {/* Header */}
      <div className="border-b border-border bg-card">
        <div className="max-w-7xl mx-auto px-4 py-6 sm:px-6 lg:px-8">
          <div className="flex items-center gap-3 mb-2">
            <div className="p-2 bg-primary/10 rounded-lg">
              <BarChart3 className="w-6 h-6 text-primary" />
            </div>
            <h1 className="text-3xl font-bold">Sales Analytics</h1>
          </div>
          <p className="text-muted-foreground">Análisis integral de ventas y KPIs por tienda</p>
        </div>
      </div>

      {/* Content */}
      <div className="max-w-7xl mx-auto px-4 py-8 sm:px-6 lg:px-8">
        {/* File Upload Section */}
        <div className="mb-8">
          <h2 className="text-xl font-semibold mb-4">Importar datos</h2>
          <FileUpload onUploadComplete={handleUploadComplete} />
        </div>

        {/* Filters Section */}
        <div className="mb-8">
          <KpiFilters onFilterChange={handleFilterChange} loading={isFilterLoading} />
        </div>

        {/* KPIs Section */}
        <div className="mb-8">
          <ComprehensiveKpis
            key={refreshKey}
            storeId={filters.storeId}
            startDate={filters.startDate}
            endDate={filters.endDate}
          />
        </div>

        {/* Tables Section */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          <div key={`top-products-${refreshKey}`}>
            <TopProductsTable
              storeId={filters.storeId}
              startDate={filters.startDate}
              endDate={filters.endDate}
              limit={10}
            />
          </div>

          <div key={`rotation-${refreshKey}`}>
            <ProductRotationTable storeId={filters.storeId} />
          </div>
        </div>

        {/* Footer */}
        <div className="mt-12 pt-8 border-t border-border">
          <Card className="p-6 bg-muted/50">
            <h3 className="font-semibold mb-2">Información de la API</h3>
            <p className="text-sm text-muted-foreground">
              {`Conectado a: ${process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080/api/v1"}`}
            </p>
            <p className="text-sm text-muted-foreground mt-1">
              Asegúrate de que el servidor backend esté corriendo en esta dirección
            </p>
          </Card>
        </div>
      </div>
    </main>
  )
}
