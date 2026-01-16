package com.powercity.model;

/**
 * Types de centrales électriques disponibles
 */
public enum PowerPlantType {
    COAL("Centrale à Charbon", 500, 100, 0.3, "Polluante mais fiable"),
    SOLAR("Panneau Solaire", 300, 50, 0.9, "Énergie propre du soleil"),
    WIND("Éolienne", 400, 70, 0.8, "Vent capricieux mais écologique"),
    NUCLEAR("Centrale Nucléaire", 2000, 500, 0.5, "Puissante mais coûteuse"),
    HYDRO("Barrage Hydraulique", 800, 200, 0.7, "Force de l'eau");
    
    private final String displayName;
    private final int baseCost;
    private final int baseProduction;
    private final double environmentalScore; // 0-1, plus élevé = plus propre
    private final String description;
    
    PowerPlantType(String displayName, int baseCost, int baseProduction, 
                   double environmentalScore, String description) {
        this.displayName = displayName;
        this.baseCost = baseCost;
        this.baseProduction = baseProduction;
        this.environmentalScore = environmentalScore;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getBaseCost() {
        return baseCost;
    }
    
    public int getBaseProduction() {
        return baseProduction;
    }
    
    public double getEnvironmentalScore() {
        return environmentalScore;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Coût d'amélioration pour un niveau donné
     */
    public int getUpgradeCost(int currentLevel) {
        return (int) (baseCost * 0.7 * currentLevel);
    }
    
    /**
     * Production pour un niveau donné
     */
    public int getProductionForLevel(int level) {
        return baseProduction * level;
    }
}
