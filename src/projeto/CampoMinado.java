package projeto;

import java.util.Scanner;

public class CampoMinado {

    static String BombasTabuleiro[][] = new String[4][4];
    static String BombasPorPerto[][] = {
        {"*", "*", "*", "*"},
        {"*", "*", "*", "*"},
        {"*", "*", "*", "*"},
        {"*", "*", "*", "*"}
    };

    public void Campo() {
        Scanner leitor = new Scanner(System.in);
        int y = 4;
        ImpimirTabuleiro(y);
        Bombas(y);
        Tabuleiro(y);

    }

    public static int ImpimirTabuleiro(int m) {
        for (int i = 0; i < m; i++) {
            System.out.println(" * " + " * " + " * " + " * ");
        }
        return m;
    }

    public static int Bombas(int N) {
        int q = 1;
        int r = 2;
        for (int linha = 0; linha < 4; linha++) {
            for (int coluna = 0; coluna < 4; coluna++) {
                if (linha == 0) {
                    BombasTabuleiro[linha][q] = "X";
                    BombasTabuleiro[linha][r] = "X";
                }
                if (linha == 1) {
                    q = 0;
                    r = 3;
                    BombasTabuleiro[linha][r] = "X";
                    BombasTabuleiro[linha][q] = "X";
                }
                if (linha == 2) {
                    q = 0;
                    r = 1;
                    BombasTabuleiro[linha][r] = "X";
                    BombasTabuleiro[linha][q] = "X";
                }
                if (linha == 3) {
                    q = 2;
                    BombasTabuleiro[linha][q] = "X";
                }
            }
        }
        return N;
    }

    public static int Tabuleiro(int m) {
        Scanner leitor = new Scanner(System.in);
        int valor = 0, valor2 = 0;
        boolean ok = false;
        while (ok == false) {
            System.out.println("Digite a linha de 1 a 4");
            valor = leitor.nextInt();
            System.out.println("Digite a coluna de 1 a 4");
            valor2 = leitor.nextInt();
            if (valor2 > 4 || valor > 4) {
                System.out.println("Cordenadas inválidas");
            }
            if (valor < 5 && valor >= 0 && valor2 < 5 && valor2 >= 0) {
                ok = true;
            }
        }
        BombasTabuleiro[0][0] = "2";
        BombasTabuleiro[0][3] = "2";
        BombasTabuleiro[1][1] = "5";
        BombasTabuleiro[1][2] = "4";
        BombasTabuleiro[2][2] = "3";
        BombasTabuleiro[2][3] = "21";
        BombasTabuleiro[3][0] = "2";
        BombasTabuleiro[3][1] = "3";
        BombasTabuleiro[3][3] = "1";

        while (ok == true) {
            BombasPorPerto[valor - 1][valor2 - 1] = BombasTabuleiro[valor - 1][valor2 - 1];
            ImprimirBombas(1);
            String resposta = BombasTabuleiro[valor - 1][valor2 - 1];
            if (resposta.equals("1") || resposta.equals("2") || resposta.equals("3") || resposta.equals("4") || resposta.equals("5")) {
                System.out.println("Ainda não chegou sua hora!!!");
                System.out.println("Digite a linha:");
                valor = leitor.nextInt();
                System.out.println("Digite a coluna:");
                valor2 = leitor.nextInt();
                while (valor > 4 || valor < 1 || valor2 < 1 || valor2 > 4) {
                    System.out.println("Cordenada Inválida");
                    System.out.println("Digite a linha:");
                    valor = leitor.nextInt();
                    System.out.println("Digite a coluna:");
                    valor2 = leitor.nextInt();
                }
            }
            if (resposta.equals("X")) {
                System.out.println("KABUMMMMMM \nVocê foi EXPLODIDO KKKK! \ntente outra vez!!!");
                ok = false;
            }
        }

        return valor;
    }

    public static int ImprimirBombas(int a) {
        while (a == 1) {
            for (int i = 0; i < 4; i++) {
                for (int k = 0; k < 4; k++) {
                    System.out.print(" " + BombasPorPerto[i][k] + " ");
                    if (k == 3) {
                        System.out.println("\n");
                    }
                    if (i == 3 && k == 3) {
                        a = 2;
                    }
                }
            }
        }
        return a;
    }
}