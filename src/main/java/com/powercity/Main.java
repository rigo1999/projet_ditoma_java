package com.powercity;

import com.powercity.view.GameView;

import javax.swing.*;

/**
 * Classe principale du jeu PowerCity Manager
 * Point d'entrée de l'application
 */
public class Main {
    public static void main(String[] args) {
        // Utiliser le look and feel du système
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Lancement de l'application Swing
        SwingUtilities.invokeLater(() -> new GameView());
    }
}
