package projeto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class PainelCampoMinado extends JPanel {

    private final int LINHAS;
    private final int COLUNAS;
    private final int BOMBAS;
    private CampoMinado campoMinado;
    private final JButton[][] botoes;
    private boolean gameOver = false;

    public PainelCampoMinado(int linhas, int colunas, int bombas) {
        this.LINHAS = linhas;
        this.COLUNAS = colunas;
        this.BOMBAS = bombas;
        this.botoes = new JButton[LINHAS][COLUNAS];
        
        setLayout(new GridLayout(LINHAS, COLUNAS));
        inicializarBotoes();
        reiniciarJogo(); // Inicia o jogo automaticamente na primeira vez
    }

    private void inicializarBotoes() {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                JButton botao = new JButton();
                botao.setPreferredSize(new Dimension(50, 50));
                botao.setFont(new Font("Arial", Font.BOLD, 20));
                
                final int linha = i;
                final int coluna = j;

                botao.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (gameOver || campoMinado.estaRevelado(linha, coluna)) {
                            return;
                        }

                        if (e.getButton() == MouseEvent.BUTTON1) { // Botão Esquerdo
                            clicarQuadrado(linha, coluna);
                        } else if (e.getButton() == MouseEvent.BUTTON3) { // Botão Direito
                            marcarQuadrado(linha, coluna);
                        }
                    }
                });
                botoes[i][j] = botao;
                add(botao);
            }
        }
    }

    private void clicarQuadrado(int linha, int coluna) {
        if (campoMinado.getValor(linha, coluna) == -1) {
            gameOver = true;
            revelarTodasBombas();
            mostrarMenuGameOver("KABUMMMMM! Você perdeu!", "Fim de Jogo");
        } else {
            campoMinado.revelar(linha, coluna);
            atualizarInterface();
            if (campoMinado.verificarVitoria()) {
                gameOver = true;
                mostrarMenuGameOver("Parabéns! Você venceu!", "Vitória");
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
                        // Adicionando cores aos números para ficar mais bonito
                        switch (valor) {
                            case 1: botao.setForeground(Color.BLUE); break;
                            case 2: botao.setForeground(Color.GREEN.darker()); break;
                            case 3: botao.setForeground(Color.RED); break;
                            case 4: botao.setForeground(Color.MAGENTA); break;
                            default: botao.setForeground(Color.BLACK); break;
                        }
                    } else if (valor == -1) {
                        botao.setText("X");
                        botao.setBackground(Color.RED);
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
                    botao.setBackground(null);
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
        Object[] opcoes = {"Reiniciar", "Sair"};
        int escolha = JOptionPane.showOptionDialog(
            this,
            mensagem,
            titulo,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoes,
            opcoes[0]
        );

        if (escolha == JOptionPane.YES_OPTION) {
            reiniciarJogo();
        } else if (escolha == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }
    
    private void reiniciarJogo() {
        campoMinado = new CampoMinado(LINHAS, COLUNAS, BOMBAS);
        gameOver = false;
        
        // Reinicializa todos os botões para o estado inicial
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                JButton botao = botoes[i][j];
                botao.setText("");
                botao.setEnabled(true);
                botao.setBackground(null);
            }
        }
    }
}