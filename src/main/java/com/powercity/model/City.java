package com.powercity.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Représente la ville et ses habitants
 */
public class City {
    private static final Random random = new Random();
    
    private final String name;
    private final List<Residence> residences;
    private final List<PowerPlant> powerPlants;
    private double money;
    private int currentCycle;
    private double averageHappiness;
    
    public City(String name, double startingMoney) {
        this.name = name;
        this.money = startingMoney;
        this.currentCycle = 0;
        this.residences = new ArrayList<>();
        this.powerPlants = new ArrayList<>();
        this.averageHappiness = 50.0;
        
        // Initialisation avec quelques résidences de base
        initializeCity();
    }
    
    private void initializeCity() {
        // 5 résidences de niveau basique pour commencer
        for (int i = 0; i < 5; i++) {
            residences.add(new Residence(ResidenceLevel.BASIC));
        }
        // 2 résidences de niveau moyen
        for (int i = 0; i < 2; i++) {
            residences.add(new Residence(ResidenceLevel.MEDIUM));
        }
        
        // 1 centrale à charbon de base
        powerPlants.add(new PowerPlant(PowerPlantType.COAL));
    }
    
    /**
     * Calcule la demande totale d'énergie
     */
    public int getTotalEnergyDemand() {
        return residences.stream()
                .mapToInt(Residence::getEnergyConsumption)
                .sum();
    }
    
    /**
     * Calcule la production totale d'énergie
     */
    public int getTotalEnergyProduction() {
        return powerPlants.stream()
                .mapToInt(PowerPlant::getCurrentProduction)
                .sum();
    }
    
    /**
     * Distribue l'énergie aux résidences
     */
    public void distributeEnergy() {
        int availableEnergy = getTotalEnergyProduction();
        
        // Priorité aux résidences de niveau supérieur
        List<Residence> sortedResidences = new ArrayList<>(residences);
        sortedResidences.sort((r1, r2) -> r2.getLevel().getTier() - r1.getLevel().getTier());
        
        for (Residence residence : sortedResidences) {
            if (availableEnergy >= residence.getEnergyConsumption()) {
                residence.setPowered(true);
                availableEnergy -= residence.getEnergyConsumption();
            } else {
                residence.setPowered(false);
            }
        }
    }
    
    /**
     * Calcule et collecte les revenus des résidences alimentées
     */
    public double collectRevenue() {
        double revenue = residences.stream()
                .mapToDouble(Residence::calculatePayment)
                .sum();
        money += revenue;
        return revenue;
    }
    
    /**
     * Met à jour la satisfaction moyenne
     */
    public void updateHappiness() {
        residences.forEach(Residence::updateSatisfaction);
        averageHappiness = residences.stream()
                .mapToDouble(Residence::getSatisfactionLevel)
                .average()
                .orElse(0.0);
    }
    
    /**
     * Fait croître la ville (nouvelles résidences)
     */
    public void cityGrowth() {
        // Si le bonheur est élevé et il y a assez d'énergie, nouvelles résidences
        if (averageHappiness > 70 && getTotalEnergyProduction() > getTotalEnergyDemand() * 1.2) {
            if (random.nextDouble() < 0.3) { // 30% de chance
                ResidenceLevel newLevel = ResidenceLevel.values()[random.nextInt(3)];
                residences.add(new Residence(newLevel));
            }
        }
        
        // Amélioration aléatoire de résidences existantes si bonheur élevé
        if (averageHappiness > 80 && random.nextDouble() < 0.2) {
            residences.stream()
                    .filter(r -> random.nextDouble() < 0.3)
                    .findFirst()
                    .ifPresent(Residence::upgrade);
        }
    }
    
    /**
     * Construire une nouvelle centrale
     */
    public boolean buildPowerPlant(PowerPlantType type) {
        int cost = type.getBaseCost();
        if (money >= cost) {
            money -= cost;
            powerPlants.add(new PowerPlant(type));
            return true;
        }
        return false;
    }
    
    /**
     * Améliorer une centrale existante
     */
    public boolean upgradePowerPlant(PowerPlant plant) {
        int cost = plant.getType().getUpgradeCost(plant.getLevel());
        if (money >= cost) {
            money -= plant.upgrade();
            return true;
        }
        return false;
    }
    
    /**
     * Effectuer la maintenance d'une centrale
     */
    public boolean maintainPowerPlant(PowerPlant plant) {
        int cost = plant.performMaintenance();
        if (money >= cost) {
            money -= cost;
            return true;
        }
        return false;
    }
    
    /**
     * Avancer d'un cycle de jeu
     */
    public void advanceCycle() {
        currentCycle++;
        
        // Mise à jour des centrales
        powerPlants.forEach(PowerPlant::updateCycle);
        
        // Distribution d'énergie
        distributeEnergy();
        
        // Collecte des revenus
        collectRevenue();
        
        // Mise à jour du bonheur
        updateHappiness();
        
        // Croissance de la ville
        cityGrowth();
    }
    
    /**
     * Vérifie si le jeu est perdu (bonheur trop bas)
     */
    public boolean isGameOver() {
        return averageHappiness < 20 || money < -1000;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public List<Residence> getResidences() {
        return new ArrayList<>(residences);
    }
    
    public List<PowerPlant> getPowerPlants() {
        return new ArrayList<>(powerPlants);
    }
    
    public double getMoney() {
        return money;
    }
    
    public int getCurrentCycle() {
        return currentCycle;
    }
    
    public double getAverageHappiness() {
        return averageHappiness;
    }
    
    public int getPopulation() {
        return residences.size();
    }
}
