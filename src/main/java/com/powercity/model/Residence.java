package com.powercity.model;

import java.util.Random;

/**
 * Représente une résidence dans la ville
 */
public class Residence {
    private static final Random random = new Random();
    private static int nextId = 1;
    
    private final int id;
    private ResidenceLevel level;
    private final int energyConsumption;
    private final double purchasingPower;
    private boolean isPowered;
    private double satisfactionLevel; // 0-100
    
    public Residence(ResidenceLevel level) {
        this.id = nextId++;
        this.level = level;
        this.energyConsumption = randomInRange(level.getEnergyConsumptionMin(), 
                                                level.getEnergyConsumptionMax());
        this.purchasingPower = randomInRange(level.getPurchasingPowerMin(), 
                                              level.getPurchasingPowerMax());
        this.isPowered = false;
        this.satisfactionLevel = 50.0; // Débute neutre
    }
    
    private int randomInRange(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }
    
    private double randomInRange(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
    
    /**
     * Met à jour la satisfaction en fonction de l'alimentation électrique
     */
    public void updateSatisfaction() {
        if (isPowered) {
            satisfactionLevel = Math.min(100, satisfactionLevel + 5);
        } else {
            satisfactionLevel = Math.max(0, satisfactionLevel - 10);
        }
    }
    
    /**
     * Calcule le paiement que la résidence peut effectuer
     */
    public double calculatePayment() {
        if (isPowered) {
            return energyConsumption * purchasingPower;
        }
        return 0;
    }
    
    /**
     * Améliore la résidence au niveau supérieur
     */
    public boolean upgrade() {
        ResidenceLevel[] levels = ResidenceLevel.values();
        int currentIndex = level.ordinal();
        if (currentIndex < levels.length - 1) {
            level = levels[currentIndex + 1];
            return true;
        }
        return false;
    }
    
    // Getters et setters
    public int getId() {
        return id;
    }
    
    public ResidenceLevel getLevel() {
        return level;
    }
    
    public int getEnergyConsumption() {
        return energyConsumption;
    }
    
    public double getPurchasingPower() {
        return purchasingPower;
    }
    
    public boolean isPowered() {
        return isPowered;
    }
    
    public void setPowered(boolean powered) {
        isPowered = powered;
    }
    
    public double getSatisfactionLevel() {
        return satisfactionLevel;
    }
    
    @Override
    public String toString() {
        return String.format("%s #%d (Énergie: %d, Satisfaction: %.1f%%)", 
                           level.getDisplayName(), id, energyConsumption, satisfactionLevel);
    }
}
