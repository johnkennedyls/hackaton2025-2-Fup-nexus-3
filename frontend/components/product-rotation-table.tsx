"use client"

import { useEffect, useState } from "react"
import { getProductRotation } from "@/lib/api-client"
import { Card } from "@/components/ui/card"

interface ProductRotation {
  productId: number
  productName: string
  rotationRate: number
  lastRestockDate: string
  currentStock: number
  averageStock: number
}

interface ProductRotationTableProps {
  storeId?: number
}

export function ProductRotationTable({ storeId }: ProductRotationTableProps) {
  const [rotations, setRotations] = useState<ProductRotation[]>([])
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const fetchRotation = async () => {
      setIsLoading(true)
      setError(null)

      try {
        const response = await getProductRotation(storeId)
        setRotations(response.data)
      } catch (err: any) {
        setError(err.response?.data?.message || "Error al cargar rotación de productos")
      } finally {
        setIsLoading(false)
      }
    }

    fetchRotation()
  }, [storeId])

  if (isLoading) {
    return (
      <Card className="p-6">
        <div className="text-center text-muted-foreground">Cargando rotación...</div>
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

  const getRotationColor = (rate: number) => {
    if (rate >= 8) return "text-green-600"
    if (rate >= 5) return "text-blue-600"
    if (rate >= 2) return "text-orange-600"
    return "text-red-600"
  }

  return (
    <Card className="p-6">
      <h3 className="text-lg font-semibold mb-4">Rotación de productos</h3>
      <div className="overflow-x-auto">
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-border">
              <th className="text-left py-3 px-4 font-semibold">Producto</th>
              <th className="text-right py-3 px-4 font-semibold">Rotación</th>
              <th className="text-right py-3 px-4 font-semibold">Stock</th>
              <th className="text-right py-3 px-4 font-semibold">Promedio</th>
              <th className="text-left py-3 px-4 font-semibold">Último restock</th>
            </tr>
          </thead>
          <tbody>
            {rotations.map((product) => (
              <tr key={product.productId} className="border-b border-border hover:bg-muted/50 transition-colors">
                <td className="py-3 px-4">
                  <div>
                    <p className="font-medium">{product.productName}</p>
                    <p className="text-xs text-muted-foreground">
                      {"ID: "}
                      {product.productId}
                    </p>
                  </div>
                </td>
                <td className={`py-3 px-4 text-right font-semibold ${getRotationColor(product.rotationRate)}`}>
                  {product.rotationRate.toFixed(2)}x
                </td>
                <td className="py-3 px-4 text-right">{product.currentStock.toLocaleString()}</td>
                <td className="py-3 px-4 text-right text-muted-foreground">{product.averageStock.toLocaleString()}</td>
                <td className="py-3 px-4 text-left text-muted-foreground">
                  {new Date(product.lastRestockDate).toLocaleDateString("es-AR")}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {rotations.length === 0 && (
          <div className="text-center py-8 text-muted-foreground">No hay datos disponibles</div>
        )}
      </div>
    </Card>
  )
}
