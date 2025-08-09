package projeto;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;
import javax.swing.border.EmptyBorder;

public class MenuCampoMinado extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private final String RECORDS_FILE = "records.properties";
    private Properties records = new Properties();

    public MenuCampoMinado() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        loadRecords();

        setTitle("Campo Minado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel menuPanel = criarMenuPanel();
        mainPanel.add(menuPanel, "Menu");
        
        JPanel recordsPanel = criarRecordsPanel();
        mainPanel.add(recordsPanel, "Records");
        
        JPanel configPanel = criarConfigPanel();
        mainPanel.add(configPanel, "Config");
        
        add(mainPanel);
        
        cardLayout.show(mainPanel, "Menu");

        // Define um tamanho fixo para o menu inicial
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void loadRecords() {
        try (FileInputStream fis = new FileInputStream(RECORDS_FILE)) {
            records.load(fis);
        } catch (IOException e) {
            records.setProperty("Fácil", "9999");
            records.setProperty("Médio", "9999");
            records.setProperty("Difícil", "9999");
            saveRecords();
        }
    }
    
    private void saveRecords() {
        try (FileOutputStream fos = new FileOutputStream(RECORDS_FILE)) {
            records.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isNewRecord(String nivel, long tempo) {
        long recordAtual = Long.parseLong(records.getProperty(nivel, "9999"));
        return tempo < recordAtual;
    }
    
    public void setRecord(String nivel, long tempo) {
        records.setProperty(nivel, String.valueOf(tempo));
        saveRecords();
    }
    
    private JPanel criarMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));

        JLabel titulo = new JLabel("Campo Minado");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton iniciarButton = new JButton("Iniciar Jogo");
        JButton recordsButton = new JButton("Recordes");
        JButton sairButton = new JButton("Sair");
        
        Dimension buttonSize = new Dimension(150, 30);
        iniciarButton.setPreferredSize(buttonSize);
        recordsButton.setPreferredSize(buttonSize);
        sairButton.setPreferredSize(buttonSize);
        
        iniciarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        recordsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sairButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        iniciarButton.addActionListener(e -> cardLayout.show(mainPanel, "Config"));
        recordsButton.addActionListener(e -> mostrarRecords());
        sairButton.addActionListener(e -> System.exit(0));

        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(iniciarButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(recordsButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(sairButton);
        
        return panel;
    }
    
    private JPanel criarConfigPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));
        
        JLabel titulo = new JLabel("Configurações");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton facilButton = new JButton("Fácil (8x8, 10 bombas)");
        JButton medioButton = new JButton("Médio (16x16, 40 bombas)");
        JButton dificilButton = new JButton("Difícil (30x30, 99 bombas)");
        JButton voltarButton = new JButton("Voltar");
        
        facilButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        medioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        dificilButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        voltarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        facilButton.addActionListener(e -> iniciarJogo(8, 8, 10, "Fácil"));
        medioButton.addActionListener(e -> iniciarJogo(16, 16, 40, "Médio"));
        dificilButton.addActionListener(e -> iniciarJogo(30, 30, 99, "Difícil"));
        voltarButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(facilButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(medioButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(dificilButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(voltarButton);
        
        return panel;
    }
    
    private JPanel criarRecordsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));

        JLabel titulo = new JLabel("Recordes");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        String recordFacil = records.getProperty("Fácil", "9999");
        String recordMedio = records.getProperty("Médio", "9999");
        String recordDificil = records.getProperty("Difícil", "9999");

        panel.add(new JLabel("Fácil: " + (recordFacil.equals("9999") ? "N/A" : recordFacil + "s")));
        panel.add(new JLabel("Médio: " + (recordMedio.equals("9999") ? "N/A" : recordMedio + "s")));
        panel.add(new JLabel("Difícil: " + (recordDificil.equals("9999") ? "N/A" : recordDificil + "s")));

        JButton voltarButton = new JButton("Voltar");
        voltarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        voltarButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(voltarButton);

        return panel;
    }
    
    private void mostrarRecords() {
        mainPanel.remove(2);
        JPanel recordsPanel = criarRecordsPanel();
        mainPanel.add(recordsPanel, "Records");
        cardLayout.show(mainPanel, "Records");
    }
    
    private void iniciarJogo(int linhas, int colunas, int bombas, String nivel) {
        PainelCampoMinado jogoPanel = new PainelCampoMinado(this, linhas, colunas, bombas, nivel);
        mainPanel.add(jogoPanel, "Jogo");
        cardLayout.show(mainPanel, "Jogo");
        
        // Define o tamanho da janela de forma fixa para todos os modos
        setSize(1000, 800);
        setLocationRelativeTo(null);
    }
    
    public void voltarParaMenu() {
        cardLayout.show(mainPanel, "Menu");
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuCampoMinado());
    }
}