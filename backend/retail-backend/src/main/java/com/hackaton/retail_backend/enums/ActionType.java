package com.hackaton.retail_backend.enums;

public enum ActionType {
    URGENT_RESTOCK("Reposición Urgente"),
    MODERATE_RESTOCK("Reposición Moderada"),
    APPLY_DISCOUNT("Aplicar Descuento"),
    APPLY_PROMOTION("Crear Promoción"),
    OPTIMIZE_STOCK("Optimizar Inventario"),
    NO_ACTION("Sin Acción Necesaria");

    private final String description;

    ActionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
