package com.powercity.view;

import com.powercity.controller.GameController;
import com.powercity.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Vue principale du jeu - Interface Swing moderne et professionnelle
 * Architecture MVC: cette classe ne contient que la logique d'affichage
 */
public class GameView extends JFrame implements GameController.GameUpdateListener {
    private GameController controller;
    
    // Composants UI principaux
    private JLabel moneyLabel;
    private JLabel cycleLabel;
    private JLabel happinessLabel;
    private JLabel populationLabel;
    private JLabel energyLabel;
    private JLabel statusLabel;
    private DefaultListModel<String> powerPlantListModel;
    private JList<String> powerPlantList;
    private DefaultListModel<String> residenceListModel;
    private JList<String> residenceList;
    private JProgressBar happinessBar;
    private JProgressBar energyBalanceBar;
    
    public GameView() {
        // Initialiser le contrôleur
        controller = new GameController("NeoVille", 2000.0);
        controller.addUpdateListener(this);
        
        // Configuration de la fenêtre
        setTitle("⚡ PowerCity Manager - Gestionnaire d'Énergie");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);
        setLocationRelativeTo(null);
        
        // Couleur de fond générale
        getContentPane().setBackground(new Color(245, 247, 250));
        
        // Créer l'interface
        createUI();
        
        // Mise à jour initiale
        updateUI();
        
        setVisible(true);
    }
    
    /**
     * Crée l'interface utilisateur complète
     */
    private void createUI() {
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // En-tête avec statistiques
        add(createHeader(), BorderLayout.NORTH);
        
        // Centre avec les listes
        add(createCenter(), BorderLayout.CENTER);
        
        // Droite avec les actions (avec scroll)
        JScrollPane actionsScroll = new JScrollPane(createActionsPanel());
        actionsScroll.setBorder(null);
        actionsScroll.getVerticalScrollBar().setUnitIncrement(16);
        add(actionsScroll, BorderLayout.EAST);
        
        // Bas avec le statut
        add(createFooter(), BorderLayout.SOUTH);
    }
    
    /**
     * Crée l'en-tête avec les statistiques principales
     */
    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(new Color(255, 255, 255));
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 230, 230)),
            new EmptyBorder(10, 20, 10, 20)
        ));
        
        // Titre avec style réduit
        JLabel title = new JLabel("POWERCITY MANAGER");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(30, 136, 229));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("Gestionnaire d'Énergie Intelligent");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(120, 120, 120));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Grille de statistiques avec panneaux stylisés
        JPanel statsGrid = new JPanel(new GridLayout(1, 5, 10, 0));
        statsGrid.setBackground(new Color(255, 255, 255));
        statsGrid.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        moneyLabel = new JLabel("2000 €");
        cycleLabel = new JLabel("0");
        populationLabel = new JLabel("0");
        energyLabel = new JLabel("0 / 0");
        happinessLabel = new JLabel("50%");
        
        statsGrid.add(createStatCard("", "Argent", moneyLabel, new Color(76, 175, 80)));
        statsGrid.add(createStatCard("", "Cycle", cycleLabel, new Color(33, 150, 243)));
        statsGrid.add(createStatCard("", "Population", populationLabel, new Color(156, 39, 176)));
        statsGrid.add(createStatCard("", "Énergie", energyLabel, new Color(255, 152, 0)));
        statsGrid.add(createStatCard("", "Bonheur", happinessLabel, new Color(233, 30, 99)));
        
        // Barres de progression stylisées
        JPanel barsPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        barsPanel.setBackground(new Color(255, 255, 255));
        barsPanel.setBorder(new EmptyBorder(5, 20, 0, 20));
        
        happinessBar = new JProgressBar(0, 100);
        happinessBar.setValue(50);
        happinessBar.setStringPainted(true);
        happinessBar.setPreferredSize(new Dimension(400, 22));
        happinessBar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        happinessBar.setBorderPainted(false);
        happinessBar.setForeground(new Color(76, 175, 80));
        
        energyBalanceBar = new JProgressBar(0, 100);
        energyBalanceBar.setValue(50);
        energyBalanceBar.setStringPainted(true);
        energyBalanceBar.setPreferredSize(new Dimension(400, 22));
        energyBalanceBar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        energyBalanceBar.setBorderPainted(false);
        energyBalanceBar.setForeground(new Color(33, 150, 243));
        
        barsPanel.add(createProgressPanel("Satisfaction des Habitants", happinessBar));
        barsPanel.add(createProgressPanel("Équilibre Énergétique", energyBalanceBar));
        
        header.add(Box.createVerticalStrut(3));
        header.add(title);
        header.add(Box.createVerticalStrut(1));
        header.add(subtitle);
        header.add(Box.createVerticalStrut(8));
        header.add(statsGrid);
        header.add(Box.createVerticalStrut(5));
        header.add(barsPanel);
        header.add(Box.createVerticalStrut(5));
        
        return header;
    }
    
    /**
     * Crée la partie centrale avec les listes
     */
    private JPanel createCenter() {
        JPanel center = new JPanel(new GridLayout(1, 2, 20, 0));
        center.setBackground(new Color(245, 247, 250));
        center.setBorder(new EmptyBorder(10, 15, 10, 5));
        
        // Liste des centrales avec style
        JPanel powerPlantPanel = new JPanel(new BorderLayout(8, 8));
        powerPlantPanel.setBackground(Color.WHITE);
        powerPlantPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel powerPlantTitle = new JLabel("CENTRALES ÉLECTRIQUES");
        powerPlantTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        powerPlantTitle.setForeground(new Color(60, 60, 60));
        
        powerPlantListModel = new DefaultListModel<>();
        powerPlantList = new JList<>(powerPlantListModel);
        powerPlantList.setFont(new Font("Consolas", Font.PLAIN, 13));
        powerPlantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        powerPlantList.setBackground(new Color(250, 250, 250));
        powerPlantList.setBorder(new EmptyBorder(5, 5, 5, 5));
        JScrollPane powerPlantScroll = new JScrollPane(powerPlantList);
        powerPlantScroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        
        powerPlantPanel.add(powerPlantTitle, BorderLayout.NORTH);
        powerPlantPanel.add(powerPlantScroll, BorderLayout.CENTER);
        
        // Liste des résidences avec style
        JPanel residencePanel = new JPanel(new BorderLayout(8, 8));
        residencePanel.setBackground(Color.WHITE);
        residencePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel residenceTitle = new JLabel("RÉSIDENCES DE LA VILLE");
        residenceTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        residenceTitle.setForeground(new Color(60, 60, 60));
        
        residenceListModel = new DefaultListModel<>();
        residenceList = new JList<>(residenceListModel);
        residenceList.setFont(new Font("Consolas", Font.PLAIN, 13));
        residenceList.setBackground(new Color(250, 250, 250));
        residenceList.setBorder(new EmptyBorder(5, 5, 5, 5));
        JScrollPane residenceScroll = new JScrollPane(residenceList);
        residenceScroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        
        residencePanel.add(residenceTitle, BorderLayout.NORTH);
        residencePanel.add(residenceScroll, BorderLayout.CENTER);
        
        center.add(powerPlantPanel);
        center.add(residencePanel);
        
        return center;
    }
    
    /**
     * Crée le panneau d'actions à droite
     */
    private JPanel createActionsPanel() {
        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
        actionsPanel.setPreferredSize(new Dimension(300, 1000)); // Hauteur augmentée pour tout afficher
        actionsPanel.setBackground(Color.WHITE);
        actionsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 2, 0, 0, new Color(230, 230, 230)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel actionsTitle = new JLabel("PANNEAU DE CONTRÔLE");
        actionsTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        actionsTitle.setForeground(new Color(60, 60, 60));
        actionsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Bouton cycle suivant avec style moderne
        JButton nextCycleBtn = createStyledButton("CYCLE SUIVANT", new Color(76, 175, 80));
        nextCycleBtn.setMaximumSize(new Dimension(270, 50));
        nextCycleBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nextCycleBtn.addActionListener(e -> handleNextCycle());
        
        // Section construction avec style
        JLabel buildLabel = new JLabel("CONSTRUIRE UNE CENTRALE");
        buildLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        buildLabel.setForeground(new Color(80, 80, 80));
        buildLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel buildButtons = new JPanel();
        buildButtons.setLayout(new BoxLayout(buildButtons, BoxLayout.Y_AXIS));
        buildButtons.setBackground(Color.WHITE);
        
        Color[] colors = {
            new Color(120, 120, 120),  // Charbon - gris
            new Color(255, 193, 7),     // Solaire - jaune
            new Color(3, 169, 244),     // Éolienne - bleu clair
            new Color(156, 39, 176),    // Nucléaire - violet
            new Color(33, 150, 243)     // Hydro - bleu
        };
        
        int i = 0;
        for (PowerPlantType type : PowerPlantType.values()) {
            JButton btn = createStyledButton(
                String.format("%s - %d €", type.getDisplayName(), type.getBaseCost()), 
                colors[i++]
            );
            btn.setMaximumSize(new Dimension(270, 38));
            btn.setToolTipText(type.getDescription());
            btn.addActionListener(e -> controller.buildPowerPlant(type));
            buildButtons.add(btn);
            buildButtons.add(Box.createVerticalStrut(8));
        }
        
        // Section maintenance/amélioration
        JLabel upgradeLabel = new JLabel("GESTION DES CENTRALES");
        upgradeLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        upgradeLabel.setForeground(new Color(80, 80, 80));
        upgradeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton upgradeBtn = createStyledButton("AMÉLIORER", new Color(33, 150, 243));
        upgradeBtn.setMaximumSize(new Dimension(270, 38));
        upgradeBtn.addActionListener(e -> handleUpgradePowerPlant());
        
        JButton maintainBtn = createStyledButton("MAINTENANCE", new Color(255, 152, 0));
        maintainBtn.setMaximumSize(new Dimension(270, 38));
        maintainBtn.addActionListener(e -> handleMaintenance());
        
        actionsPanel.add(actionsTitle);
        actionsPanel.add(Box.createVerticalStrut(15));
        actionsPanel.add(nextCycleBtn);
        actionsPanel.add(Box.createVerticalStrut(20));
        actionsPanel.add(createDivider());
        actionsPanel.add(Box.createVerticalStrut(20));
        actionsPanel.add(buildLabel);
        actionsPanel.add(Box.createVerticalStrut(12));
        actionsPanel.add(buildButtons);
        actionsPanel.add(Box.createVerticalStrut(20));
        actionsPanel.add(createDivider());
        actionsPanel.add(Box.createVerticalStrut(20));
        actionsPanel.add(upgradeLabel);
        actionsPanel.add(Box.createVerticalStrut(12));
        actionsPanel.add(upgradeBtn);
        actionsPanel.add(Box.createVerticalStrut(8));
        actionsPanel.add(maintainBtn);
        actionsPanel.add(Box.createVerticalGlue());
        
        return actionsPanel;
    }
    
    /**
     * Crée le pied de page avec le message de statut
     */
    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(245, 247, 250));
        footer.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        statusLabel = new JLabel("Bienvenue dans PowerCity Manager!");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(232, 245, 233));
        statusLabel.setForeground(new Color(46, 125, 50));
        statusLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 230, 201), 2),
            new EmptyBorder(12, 15, 12, 15)
        ));
        
        footer.add(statusLabel, BorderLayout.CENTER);
        return footer;
    }
    
    /**
     * Crée une carte de statistique stylisée
     */
    private JPanel createStatCard(String icon, String label, JLabel valueLabel, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(8, 8, 8, 8)
        ));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel textLabel = new JLabel(label);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        textLabel.setForeground(new Color(120, 120, 120));
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valueLabel.setForeground(accentColor);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(3));
        card.add(textLabel);
        card.add(Box.createVerticalStrut(2));
        card.add(valueLabel);
        
        return card;
    }
    
    /**
     * Crée un panneau pour les barres de progression
     */
    private JPanel createProgressPanel(String label, JProgressBar bar) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(label);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLabel.setForeground(new Color(80, 80, 80));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        bar.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(bar);
        
        return panel;
    }
    
    /**
     * Crée un bouton stylisé moderne
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Effet hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    /**
     * Crée un séparateur stylisé
     */
    private JSeparator createDivider() {
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(270, 1));
        separator.setForeground(new Color(230, 230, 230));
        return separator;
    }
    
    /**
     * Gère le passage au cycle suivant
     */
    private void handleNextCycle() {
        if (controller.isGameOver()) {
            showGameOverDialog();
            return;
        }
        
        controller.nextCycle();
        
        if (controller.isGameOver()) {
            showGameOverDialog();
        }
    }
    
    /**
     * Gère l'amélioration d'une centrale
     */
    private void handleUpgradePowerPlant() {
        int selectedIndex = powerPlantList.getSelectedIndex();
        if (selectedIndex >= 0) {
            PowerPlant plant = controller.getCity().getPowerPlants().get(selectedIndex);
            controller.upgradePowerPlant(plant);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner une centrale à améliorer", 
                "Aucune sélection", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Gère la maintenance d'une centrale
     */
    private void handleMaintenance() {
        int selectedIndex = powerPlantList.getSelectedIndex();
        if (selectedIndex >= 0) {
            PowerPlant plant = controller.getCity().getPowerPlants().get(selectedIndex);
            controller.maintainPowerPlant(plant);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner une centrale pour la maintenance", 
                "Aucune sélection", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Implémentation de GameUpdateListener - appelé quand le jeu est mis à jour
     */
    @Override
    public void onGameUpdated() {
        updateUI();
    }
    
    /**
     * Met à jour toute l'interface utilisateur
     */
    private void updateUI() {
        // Mise à jour des labels de statistiques
        moneyLabel.setText(String.format("%.0f €", controller.getMoney()));
        cycleLabel.setText(String.format("%d", controller.getCurrentCycle()));
        populationLabel.setText(String.format("%d foyers", controller.getPopulation()));
        energyLabel.setText(String.format("%d / %d kWh", 
                                         controller.getTotalEnergyProduction(),
                                         controller.getTotalEnergyDemand()));
        
        double happiness = controller.getAverageHappiness();
        happinessLabel.setText(String.format("%.1f%%", happiness));
        
        // Mise à jour des barres de progression
        happinessBar.setValue((int) happiness);
        if (happiness > 70) {
            happinessBar.setForeground(new Color(76, 175, 80)); // Vert
        } else if (happiness > 40) {
            happinessBar.setForeground(new Color(255, 152, 0)); // Orange
        } else {
            happinessBar.setForeground(new Color(244, 67, 54)); // Rouge
        }
        
        // Barre d'équilibre énergétique
        int production = controller.getTotalEnergyProduction();
        int demand = controller.getTotalEnergyDemand();
        double balance = demand > 0 ? (double) production / demand : 1.0;
        energyBalanceBar.setValue((int) (Math.min(1.0, balance) * 100));
        if (balance >= 1.0) {
            energyBalanceBar.setForeground(new Color(76, 175, 80)); // Vert
        } else if (balance >= 0.7) {
            energyBalanceBar.setForeground(new Color(255, 152, 0)); // Orange
        } else {
            energyBalanceBar.setForeground(new Color(244, 67, 54)); // Rouge
        }
        
        // Mise à jour de la liste des centrales
        powerPlantListModel.clear();
        for (PowerPlant plant : controller.getCity().getPowerPlants()) {
            String status = plant.needsMaintenance() ? " [MAINTENANCE]" : "";
            powerPlantListModel.addElement(plant.toString() + status);
        }
        
        // Mise à jour de la liste des résidences
        residenceListModel.clear();
        for (Residence residence : controller.getCity().getResidences()) {
            String powered = residence.isPowered() ? "[ON]" : "[OFF]";
            residenceListModel.addElement(powered + " " + residence.toString());
        }
        
        // Mise à jour du statut avec couleur adaptée
        String status = controller.getStatusMessage();
        statusLabel.setText(status);
        
        if (status.contains("PÉNURIE") || status.contains("insuffisants") || status.contains("GAME OVER")) {
            statusLabel.setBackground(new Color(255, 235, 238));
            statusLabel.setForeground(new Color(198, 40, 40));
            statusLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(239, 154, 154), 2),
                new EmptyBorder(12, 15, 12, 15)
            ));
        } else if (status.contains("succès") || status.contains("excédentaire") || status.contains("Bienvenue")) {
            statusLabel.setBackground(new Color(232, 245, 233));
            statusLabel.setForeground(new Color(46, 125, 50));
            statusLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 230, 201), 2),
                new EmptyBorder(12, 15, 12, 15)
            ));
        } else {
            statusLabel.setBackground(new Color(255, 243, 224));
            statusLabel.setForeground(new Color(230, 126, 34));
            statusLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 213, 153), 2),
                new EmptyBorder(12, 15, 12, 15)
            ));
        }
    }
    
    /**
     * Affiche le dialogue de fin de jeu
     */
    private void showGameOverDialog() {
        int result = JOptionPane.showConfirmDialog(this,
                String.format(
                    "Votre mandat s'est terminé au cycle %d.\n" +
                    "Le bonheur de la population était à %.1f%%.\n\n" +
                    "Les habitants mécontents ont demandé votre remplacement.\n\n" +
                    "Voulez-vous recommencer?",
                    controller.getCurrentCycle(),
                    controller.getAverageHappiness()
                ),
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE);
        
        if (result == JOptionPane.YES_OPTION) {
            // Recommencer
            controller = new GameController("NeoVille", 2000.0);
            controller.addUpdateListener(this);
            updateUI();
        } else {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        // Utiliser le look and feel du système
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Créer et afficher l'interface dans le thread Swing
        SwingUtilities.invokeLater(() -> new GameView());
    }
}
