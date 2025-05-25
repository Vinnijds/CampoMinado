package projeto;

import java.util.Scanner;

public class MenuCampoMinado {

    
    public static void main(String[] args) {
       new MenuCampoMinado();
        
    }
        
        public  MenuCampoMinado(){
        
        int opcao = 0;
        boolean executando;
        String resposta;
       
        do {

            Scanner leitor = new Scanner(System.in);
            System.out.println("Lista de Tarefas");
            System.out.println("(1) - Iniciar");
            System.out.println("(2) - Recordes");
            System.out.println("(3) - Opçoes");
            System.out.println("(4) - Sobre");
            System.out.println("(5) - Sair");

            opcao = leitor.nextInt();
            executando = true;
            switch (opcao) {

                case 1:                   
                  CampoMinado CampoMinado=new CampoMinado();
                  CampoMinado.Campo();                   
                    executando=false;
                    break;
                case 2:
                case 3:
                    System.out.println("Opção não disponível");
                    break;
                case 4:
                    System.out.println("Campo Minado");
                    System.out.println("Informatica para Internet");
                    System.out.println("Instituto Federal de Ciência e Tecnologia");
                    System.out.println("Alunos:\n \nDhiorgenes Francisco\nJosé Vinicius\nMaurício Lee Pablo\nLuiz Henrique");                
                    System.out.println("Data 20/08/2016");
                    System.out.println("Versão 1.1");
                    break;
                case 5:
                    System.out.println("Deseja realmente sair?     (S/N)");
                    resposta = leitor.next();
                    if (resposta.equals("S") || resposta.equals("s")) {
                        executando = false;
                        break;

                    }
                default:
                    System.out.println("Opção inválida");
                    break;

            }
        }while (executando);
}  
        
        }          
    