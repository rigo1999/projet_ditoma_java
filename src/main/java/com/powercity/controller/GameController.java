package com.powercity.controller;

import com.powercity.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur principal du jeu - Architecture MVC
 * Gère toutes les interactions entre le modèle (City) et la vue (UI)
 */
public class GameController {
    private final City city;
    private final List<GameUpdateListener> listeners;
    private String statusMessage;
    
    public GameController(String cityName, double startingMoney) {
        this.city = new City(cityName, startingMoney);
        this.listeners = new ArrayList<>();
        this.statusMessage = "Bienvenue dans PowerCity Manager!";
    }
    
    /**
     * Interface pour notifier les changements à la vue
     */
    public interface GameUpdateListener {
        void onGameUpdated();
    }
    
    /**
     * Ajoute un listener pour les mises à jour du jeu
     */
    public void addUpdateListener(GameUpdateListener listener) {
        listeners.add(listener);
    }
    
    /**
     * Notifie tous les listeners
     */
    private void notifyListeners() {
        for (GameUpdateListener listener : listeners) {
            listener.onGameUpdated();
        }
    }
    
    /**
     * Avance le jeu d'un cycle
     */
    public void nextCycle() {
        city.advanceCycle();
        
        if (city.isGameOver()) {
            statusMessage = "GAME OVER: Le maire vous a révoqué!";
        } else {
            double balance = city.getTotalEnergyProduction() - city.getTotalEnergyDemand();
            if (balance >= 0) {
                statusMessage = String.format("Cycle %d terminé - Production excédentaire: %.0f kWh", 
                                             city.getCurrentCycle(), balance);
            } else {
                statusMessage = String.format("Cycle %d terminé - PÉNURIE: %.0f kWh manquants!", 
                                             city.getCurrentCycle(), Math.abs(balance));
            }
        }
        notifyListeners();
    }
    
    /**
     * Construire une nouvelle centrale
     */
    public boolean buildPowerPlant(PowerPlantType type) {
        if (city.buildPowerPlant(type)) {
            statusMessage = String.format("%s construite avec succès!", type.getDisplayName());
            notifyListeners();
            return true;
        } else {
            statusMessage = String.format("Fonds insuffisants pour construire %s (Coût: %d)", 
                                         type.getDisplayName(), type.getBaseCost());
            notifyListeners();
            return false;
        }
    }
    
    /**
     * Améliorer une centrale existante
     */
    public boolean upgradePowerPlant(PowerPlant plant) {
        int cost = plant.getType().getUpgradeCost(plant.getLevel());
        if (city.upgradePowerPlant(plant)) {
            statusMessage = String.format("Centrale #%d améliorée au niveau %d!", 
                                         plant.getId(), plant.getLevel());
            notifyListeners();
            return true;
        } else {
            statusMessage = String.format("Fonds insuffisants pour améliorer la centrale (Coût: %d)", cost);
            notifyListeners();
            return false;
        }
    }
    
    /**
     * Effectuer la maintenance d'une centrale
     */
    public boolean maintainPowerPlant(PowerPlant plant) {
        if (city.maintainPowerPlant(plant)) {
            statusMessage = String.format("Maintenance effectuée sur la centrale #%d", plant.getId());
            notifyListeners();
            return true;
        } else {
            statusMessage = "Fonds insuffisants pour la maintenance";
            notifyListeners();
            return false;
        }
    }
    
    /**
     * Obtenir la liste des résidences
     */
    public List<Residence> getResidences() {
        return city.getResidences();
    }
    
    /**
     * Obtenir la liste des centrales
     */
    public List<PowerPlant> getPowerPlants() {
        return city.getPowerPlants();
    }
    
    /**
     * Vérifier si le jeu est terminé
     */
    public boolean isGameOver() {
        return city.isGameOver();
    }
    
    // Getters pour accéder aux données
    public double getMoney() {
        return city.getMoney();
    }
    
    public int getCurrentCycle() {
        return city.getCurrentCycle();
    }
    
    public double getAverageHappiness() {
        return city.getAverageHappiness();
    }
    
    public int getPopulation() {
        return city.getPopulation();
    }
    
    public int getTotalEnergyProduction() {
        return city.getTotalEnergyProduction();
    }
    
    public int getTotalEnergyDemand() {
        return city.getTotalEnergyDemand();
    }
    
    public String getStatusMessage() {
        return statusMessage;
    }
    
    public City getCity() {
        return city;
    }
}
