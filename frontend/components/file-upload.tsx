"use client"

import type React from "react"

import { useState } from "react"
import { Upload, CheckCircle, AlertCircle } from "lucide-react"
import { uploadFile } from "@/lib/api-client"
import { Button } from "@/components/ui/button"
import { Card } from "@/components/ui/card"

export interface UploadResult {
  successCount: number
  errorCount: number
  errors: string[]
}

interface FileUploadProps {
  onUploadComplete?: (result: UploadResult) => void
}

export function FileUpload({ onUploadComplete }: FileUploadProps) {
  const [isDragging, setIsDragging] = useState(false)
  const [isLoading, setIsLoading] = useState(false)
  const [result, setResult] = useState<UploadResult | null>(null)
  const [error, setError] = useState<string | null>(null)

  const handleDrag = (e: React.DragEvent) => {
    e.preventDefault()
    setIsDragging(e.type === "dragenter" || e.type === "dragover")
  }

  const handleFile = async (file: File) => {
    if (!file.name.endsWith(".csv") && !file.name.endsWith(".xlsx")) {
      setError("Solo se permiten archivos CSV o XLSX")
      return
    }

    setIsLoading(true)
    setError(null)

    try {
      const response = await uploadFile(file)
      const uploadResult: UploadResult = response.data

      setResult(uploadResult)
      if (onUploadComplete) {
        onUploadComplete(uploadResult)
      }

      if (uploadResult.errorCount > 0) {
        setError(`Se encontraron ${uploadResult.errorCount} errores`)
      }
    } catch (err: any) {
      setError(err.response?.data?.message || "Error al procesar el archivo")
      setResult(null)
    } finally {
      setIsLoading(false)
    }
  }

  const handleDrop = (e: React.DragEvent) => {
    handleDrag(e)
    const files = e.dataTransfer.files
    if (files.length > 0) {
      handleFile(files[0])
    }
  }

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = e.currentTarget.files
    if (files && files.length > 0) {
      handleFile(files[0])
    }
  }

  return (
    <Card className="w-full p-6 border-2 border-dashed border-border hover:border-primary/50 transition-colors">
      <div
        className="text-center"
        onDragEnter={handleDrag}
        onDragLeave={handleDrag}
        onDragOver={handleDrag}
        onDrop={handleDrop}
      >
        {result && !error ? (
          <div className="space-y-2">
            <CheckCircle className="mx-auto h-12 w-12 text-green-500" />
            <h3 className="font-semibold">{"Archivo cargado exitosamente"}</h3>
            <p className="text-sm text-muted-foreground">
              {`${result.successCount} registros importados correctamente`}
            </p>
            {result.errorCount > 0 && (
              <div className="mt-2 text-sm text-destructive">
                <p>{`${result.errorCount} registros con error:`}</p>
                {result.errors.map((err, i) => (
                  <p key={i} className="text-xs">
                    {err}
                  </p>
                ))}
              </div>
            )}
            <Button
              size="sm"
              variant="outline"
              onClick={() => {
                setResult(null)
                setError(null)
              }}
            >
              Subir otro archivo
            </Button>
          </div>
        ) : error ? (
          <div className="space-y-2">
            <AlertCircle className="mx-auto h-12 w-12 text-destructive" />
            <h3 className="font-semibold">Error al procesar</h3>
            <p className="text-sm text-destructive">{error}</p>
            <Button size="sm" variant="outline" onClick={() => setError(null)}>
              Intentar de nuevo
            </Button>
          </div>
        ) : (
          <label className="cursor-pointer">
            <div className="space-y-2">
              <Upload
                className={`mx-auto h-12 w-12 transition-colors ${
                  isDragging ? "text-primary" : "text-muted-foreground"
                }`}
              />
              <h3 className="font-semibold">{isLoading ? "Procesando archivo..." : "Arrastra tu archivo aquí"}</h3>
              <p className="text-sm text-muted-foreground">o haz clic para seleccionar (CSV, XLSX)</p>
            </div>
            <input
              type="file"
              className="hidden"
              accept=".csv,.xlsx"
              onChange={handleInputChange}
              disabled={isLoading}
            />
          </label>
        )}
      </div>
    </Card>
  )
}
