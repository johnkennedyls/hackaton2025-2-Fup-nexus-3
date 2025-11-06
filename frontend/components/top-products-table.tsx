"use client"

import { useEffect, useState } from "react"
import { getTopProducts } from "@/lib/api-client"
import { Card } from "@/components/ui/card"

interface TopProduct {
  productId: number
  productName: string
  totalQuantity: number
  totalAmount: number
  salesCount: number
}

interface TopProductsTableProps {
  storeId?: number
  startDate: string
  endDate: string
  limit?: number
}

export function TopProductsTable({ storeId, startDate, endDate, limit = 10 }: TopProductsTableProps) {
  const [products, setProducts] = useState<TopProduct[]>([])
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const fetchProducts = async () => {
      setIsLoading(true)
      setError(null)

      try {
        const response = await getTopProducts({
          storeId,
          startDate,
          endDate,
          limit,
        })
        setProducts(response.data)
      } catch (err: any) {
        setError(err.response?.data?.message || "Error al cargar productos")
      } finally {
        setIsLoading(false)
      }
    }

    fetchProducts()
  }, [storeId, startDate, endDate, limit])

  if (isLoading) {
    return (
      <Card className="p-6">
        <div className="text-center text-muted-foreground">Cargando productos...</div>
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

  return (
    <Card className="p-6">
      <h3 className="text-lg font-semibold mb-4">Productos más vendidos</h3>
      <div className="overflow-x-auto">
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-border">
              <th className="text-left py-3 px-4 font-semibold">Producto</th>
              <th className="text-right py-3 px-4 font-semibold">Cantidad</th>
              <th className="text-right py-3 px-4 font-semibold">Ventas</th>
              <th className="text-right py-3 px-4 font-semibold">Total</th>
            </tr>
          </thead>
          <tbody>
            {products.map((product, index) => (
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
                <td className="py-3 px-4 text-right text-primary font-semibold">
                  {product.totalQuantity.toLocaleString()}
                </td>
                <td className="py-3 px-4 text-right text-muted-foreground">{product.salesCount}</td>
                <td className="py-3 px-4 text-right font-semibold">
                  {`$${product.totalAmount.toLocaleString("es-AR", {
                    minimumFractionDigits: 2,
                  })}`}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {products.length === 0 && (
          <div className="text-center py-8 text-muted-foreground">No hay datos disponibles</div>
        )}
      </div>
    </Card>
  )
}
