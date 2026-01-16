package com.powercity.model;

import java.util.Random;

/**
 * Représente une centrale électrique
 */
public class PowerPlant {
    private static final Random random = new Random();
    private static int nextId = 1;
    
    private final int id;
    private final PowerPlantType type;
    private int level;
    private boolean isOperational;
    private int maintenanceCyclesRemaining;
    
    public PowerPlant(PowerPlantType type) {
        this.id = nextId++;
        this.type = type;
        this.level = 1;
        this.isOperational = true;
        this.maintenanceCyclesRemaining = 10 + random.nextInt(10); // 10-20 cycles avant maintenance
    }
    
    /**
     * Retourne la production d'énergie actuelle
     */
    public int getCurrentProduction() {
        if (!isOperational) {
            return 0;
        }
        // Variation aléatoire de ±10%
        int baseProduction = type.getProductionForLevel(level);
        double variation = 0.9 + (random.nextDouble() * 0.2); // 0.9 à 1.1
        return (int) (baseProduction * variation);
    }
    
    /**
     * Améliore la centrale d'un niveau
     */
    public int upgrade() {
        int cost = type.getUpgradeCost(level);
        level++;
        return cost;
    }
    
    /**
     * Met à jour l'état de la centrale pour un nouveau cycle
     */
    public void updateCycle() {
        if (isOperational) {
            maintenanceCyclesRemaining--;
            if (maintenanceCyclesRemaining <= 0) {
                // Chance de panne si maintenance non effectuée
                if (random.nextDouble() < 0.3) {
                    isOperational = false;
                }
            }
        }
    }
    
    /**
     * Effectue la maintenance de la centrale
     */
    public int performMaintenance() {
        int cost = type.getBaseCost() / 10; // 10% du coût de base
        maintenanceCyclesRemaining = 10 + random.nextInt(10);
        isOperational = true;
        return cost;
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public PowerPlantType getType() {
        return type;
    }
    
    public int getLevel() {
        return level;
    }
    
    public boolean isOperational() {
        return isOperational;
    }
    
    public int getMaintenanceCyclesRemaining() {
        return maintenanceCyclesRemaining;
    }
    
    public boolean needsMaintenance() {
        return maintenanceCyclesRemaining <= 3 || !isOperational;
    }
    
    @Override
    public String toString() {
        String status = isOperational ? "Opérationnelle" : "EN PANNE";
        return String.format("%s Niv.%d #%d - %s (Maintenance: %d cycles)", 
                           type.getDisplayName(), level, id, status, maintenanceCyclesRemaining);
    }
}
