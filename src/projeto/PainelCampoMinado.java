package projeto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class PainelCampoMinado extends JPanel {

    private final MenuCampoMinado menuPrincipal;
    private final int LINHAS;
    private final int COLUNAS;
    private final int BOMBAS;
    private final String NIVEL;
    private CampoMinado campoMinado;
    private final JButton[][] botoes;
    private boolean gameOver = false;
    private boolean primeiroClique = true;

    // Cronômetro
    private Timer timer;
    private long tempoDecorrido;
    private JLabel timerLabel;

    public PainelCampoMinado(MenuCampoMinado menu, int linhas, int colunas, int bombas, String nivel) {
        this.menuPrincipal = menu;
        this.LINHAS = linhas;
        this.COLUNAS = colunas;
        this.BOMBAS = bombas;
        this.NIVEL = nivel;
        this.botoes = new JButton[LINHAS][COLUNAS];
        
        setLayout(new BorderLayout());
        
        // Painel para o cronômetro
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        timerLabel = new JLabel("Tempo: 0s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        infoPanel.add(timerLabel);
        add(infoPanel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(LINHAS, COLUNAS));
        gridPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Adiciona uma margem
        inicializarBotoes(gridPanel);
        add(gridPanel, BorderLayout.CENTER);
        
        reiniciarJogo();
    }

    private void inicializarBotoes(JPanel gridPanel) {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                JButton botao = new JButton();
                // A linha abaixo foi removida para que o tamanho seja uniforme
                // botao.setPreferredSize(new Dimension(30, 30));
                botao.setFont(new Font("Arial", Font.BOLD, 14));
                
                final int linha = i;
                final int int_coluna = j;

                botao.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (gameOver || campoMinado.estaRevelado(linha, int_coluna)) {
                            return;
                        }
                        
                        if (primeiroClique) {
                            iniciarCronometro();
                            primeiroClique = false;
                        }

                        if (e.getButton() == MouseEvent.BUTTON1) {
                            clicarQuadrado(linha, int_coluna);
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            marcarQuadrado(linha, int_coluna);
                        }
                    }
                });
                botoes[i][j] = botao;
                gridPanel.add(botao);
            }
        }
    }

    private void iniciarCronometro() {
        tempoDecorrido = 0;
        timer = new Timer(1000, e -> {
            tempoDecorrido++;
            timerLabel.setText("Tempo: " + tempoDecorrido + "s");
        });
        timer.start();
    }
    
    private void pararCronometro() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void clicarQuadrado(int linha, int coluna) {
        if (campoMinado.getValor(linha, coluna) == -1) {
            gameOver = true;
            pararCronometro();
            revelarTodasBombas();
            mostrarMenuGameOver("KABUMMMMM! Você perdeu!", "Fim de Jogo");
        } else {
            campoMinado.revelar(linha, coluna);
            atualizarInterface();
            if (campoMinado.verificarVitoria()) {
                gameOver = true;
                pararCronometro();
                mostrarMenuGameOver("Parabéns! Você venceu em " + tempoDecorrido + "s!", "Vitória");
            }
        }
    }
    
    private void marcarQuadrado(int linha, int coluna) {
        campoMinado.alternarMarcacao(linha, coluna);
        atualizarInterface();
    }
    
    private void atualizarInterface() {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                JButton botao = botoes[i][j];
                if (campoMinado.estaRevelado(i, j)) {
                    botao.setEnabled(false);
                    botao.setBackground(Color.LIGHT_GRAY);
                    int valor = campoMinado.getValor(i, j);
                    if (valor > 0) {
                        botao.setText(String.valueOf(valor));
                        switch (valor) {
                            case 1: botao.setForeground(Color.BLUE); break;
                            case 2: botao.setForeground(new Color(34, 139, 34)); break;
                            case 3: botao.setForeground(Color.RED); break;
                            case 4: botao.setForeground(new Color(128, 0, 128)); break;
                            case 5: botao.setForeground(new Color(255, 140, 0)); break;
                            case 6: botao.setForeground(new Color(0, 139, 139)); break;
                            case 7: botao.setForeground(Color.BLACK); break;
                            case 8: botao.setForeground(Color.DARK_GRAY); break;
                            default: botao.setForeground(Color.BLACK); break;
                        }
                    } else {
                         botao.setText("");
                    }
                } else if (campoMinado.estaMarcado(i, j)) {
                    botao.setText("?");
                    botao.setBackground(Color.YELLOW);
                    botao.setForeground(Color.BLACK);
                } else {
                    botao.setText("");
                    botao.setEnabled(true);
                    botao.setBackground(UIManager.getColor("Button.background"));
                }
            }
        }
    }
    
    private void revelarTodasBombas() {
         for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                 botoes[i][j].setEnabled(false);
                 if (campoMinado.getValor(i, j) == -1) {
                     botoes[i][j].setText("X");
                     botoes[i][j].setBackground(Color.RED);
                 }
            }
         }
    }
    
    private void mostrarMenuGameOver(String mensagem, String titulo) {
        if (titulo.equals("Vitória") && menuPrincipal.isNewRecord(NIVEL, tempoDecorrido)) {
            JOptionPane.showMessageDialog(this, mensagem + "\nNovo recorde para o nível " + NIVEL + "!", "Vitória", JOptionPane.INFORMATION_MESSAGE);
            menuPrincipal.setRecord(NIVEL, tempoDecorrido);
        } else {
            JOptionPane.showMessageDialog(this, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
        }
        
        Object[] opcoes = {"Reiniciar", "Voltar para o Menu"};
        int escolha = JOptionPane.showOptionDialog(
            this,
            "O que você gostaria de fazer?",
            "Opções",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoes,
            opcoes[0]
        );

        if (escolha == JOptionPane.YES_OPTION) {
            reiniciarJogo();
        } else if (escolha == JOptionPane.NO_OPTION) {
            menuPrincipal.voltarParaMenu();
        }
    }
    
    private void reiniciarJogo() {
        campoMinado = new CampoMinado(LINHAS, COLUNAS, BOMBAS);
        gameOver = false;
        primeiroClique = true;
        pararCronometro();
        tempoDecorrido = 0;
        timerLabel.setText("Tempo: 0s");
        
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                JButton botao = botoes[i][j];
                botao.setText("");
                botao.setEnabled(true);
                botao.setBackground(UIManager.getColor("Button.background"));
            }
        }
    }
}