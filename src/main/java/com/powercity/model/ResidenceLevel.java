package com.powercity.model;

/**
 * Niveaux de résidences dans la ville
 */
public enum ResidenceLevel {
    BASIC("Cabane Modeste", 1),
    MEDIUM("Maison Confortable", 2),
    ADVANCED("Villa Luxueuse", 3),
    ELITE("Penthouse Prestigieux", 4);
    
    private final String displayName;
    private final int tier;
    
    ResidenceLevel(String displayName, int tier) {
        this.displayName = displayName;
        this.tier = tier;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getTier() {
        return tier;
    }
    
    /**
     * Retourne la consommation d'énergie par cycle (intervalle aléatoire)
     */
    public int getEnergyConsumptionMin() {
        return tier * 10;
    }
    
    public int getEnergyConsumptionMax() {
        return tier * 20;
    }
    
    /**
     * Retourne le pouvoir d'achat par unité d'énergie (intervalle aléatoire)
     */
    public double getPurchasingPowerMin() {
        return tier * 0.5;
    }
    
    public double getPurchasingPowerMax() {
        return tier * 1.5;
    }
}
