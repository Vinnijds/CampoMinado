package projeto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuCampoMinado extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MenuCampoMinado() {
        setTitle("Campo Minado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Painel do Menu Principal
        JPanel menuPanel = criarMenuPanel();
        mainPanel.add(menuPanel, "Menu");

        add(mainPanel);
        cardLayout.show(mainPanel, "Menu"); // Começa mostrando o menu

        pack(); 
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JPanel criarMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel titulo = new JLabel("Campo Minado");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton iniciarButton = new JButton("Iniciar Jogo");
        JButton sairButton = new JButton("Sair");
        
        iniciarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sairButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJogo();
            }
        });
        
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(iniciarButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(sairButton);
        
        return panel;
    }
    
    private void iniciarJogo() {
        // Painel do jogo
        int linhas = 10;
        int colunas = 10;
        int bombas = 15;
        PainelCampoMinado jogoPanel = new PainelCampoMinado(linhas, colunas, bombas);
        
        mainPanel.add(jogoPanel, "Jogo");
        cardLayout.show(mainPanel, "Jogo");
        
        // Redimensiona a janela para o tamanho do jogo
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuCampoMinado());
    }
}