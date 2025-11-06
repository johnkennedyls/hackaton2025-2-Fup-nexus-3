import axios, { type AxiosInstance } from "axios"

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080/api/v1"

const apiClient: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 30000,
})

// Data Upload endpoint
export const uploadFile = async (file: File) => {
  const formData = new FormData()
  formData.append("file", file)
  return apiClient.post("/data-upload", formData, {
    headers: { "Content-Type": "multipart/form-data" },
  })
}

// KPI endpoints
export const getTopProducts = async (params: {
  storeId?: number
  startDate: string
  endDate: string
  limit?: number
}) => {
  return apiClient.get("/kpis/top-products", { params })
}

export const getProductRotation = async (storeId?: number) => {
  return apiClient.get("/kpis/product-rotation", {
    params: storeId ? { storeId } : {},
  })
}

export const getComprehensiveKpis = async (params: {
  storeId?: number
  startDate: string
  endDate: string
}) => {
  return apiClient.get("/kpis/comprehensive", { params })
}

export default apiClient
