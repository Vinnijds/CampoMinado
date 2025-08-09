package projeto;

import java.util.Random;

public class CampoMinado {

    private final int LINHAS;
    private final int COLUNAS;
    private final int NUMERO_DE_BOMBAS;
    private int[][] tabuleiro;
    private boolean[][] revelado;
    private boolean[][] marcado;

    public CampoMinado(int linhas, int colunas, int numeroDeBombas) {
        this.LINHAS = linhas;
        this.COLUNAS = colunas;
        this.NUMERO_DE_BOMBAS = numeroDeBombas;
        this.tabuleiro = new int[LINHAS][COLUNAS];
        this.revelado = new boolean[LINHAS][COLUNAS];
        this.marcado = new boolean[LINHAS][COLUNAS];
        gerarTabuleiro();
    }

    private void gerarTabuleiro() {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                tabuleiro[i][j] = 0;
            }
        }

        Random random = new Random();
        int bombasColocadas = 0;
        while (bombasColocadas < NUMERO_DE_BOMBAS) {
            int linha = random.nextInt(LINHAS);
            int coluna = random.nextInt(COLUNAS);
            if (tabuleiro[linha][coluna] != -1) {
                tabuleiro[linha][coluna] = -1;
                bombasColocadas++;
            }
        }

        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                if (tabuleiro[i][j] != -1) {
                    tabuleiro[i][j] = contarBombasVizinhas(i, j);
                }
            }
        }
    }

    private int contarBombasVizinhas(int linha, int coluna) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int novaLinha = linha + i;
                int novaColuna = coluna + j;

                if (novaLinha >= 0 && novaLinha < LINHAS && novaColuna >= 0 && novaColuna < COLUNAS) {
                    if (tabuleiro[novaLinha][novaColuna] == -1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void revelar(int linha, int coluna) {
        if (linha < 0 || linha >= LINHAS || coluna < 0 || coluna >= COLUNAS || revelado[linha][coluna]) {
            return;
        }

        revelado[linha][coluna] = true;

        if (tabuleiro[linha][coluna] == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    revelar(linha + i, coluna + j);
                }
            }
        }
    }

    public boolean verificarVitoria() {
        int naoBombas = LINHAS * COLUNAS - NUMERO_DE_BOMBAS;
        int quadradosRevelados = 0;
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                if (revelado[i][j] && tabuleiro[i][j] != -1) {
                    quadradosRevelados++;
                }
            }
        }
        return quadradosRevelados == naoBombas;
    }
    
    public int getValor(int linha, int coluna) {
        return tabuleiro[linha][coluna];
    }
    
    public boolean estaRevelado(int linha, int coluna) {
        return revelado[linha][coluna];
    }
    
    public void alternarMarcacao(int linha, int coluna) {
        marcado[linha][coluna] = !marcado[linha][coluna];
    }
    
    public boolean estaMarcado(int linha, int coluna) {
        return marcado[linha][coluna];
    }

    public int getLINHAS() {
        return LINHAS;
    }

    public int getCOLUNAS() {
        return COLUNAS;
    }
}