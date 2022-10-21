package br.com.bb.Calc;
import java.util.Arrays;
import java.util.Scanner;

public class Calc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Operacoes ope = new Operacoes();
        Boolean continuar = true;
        while (continuar) {
            SolveCient sol = new SolveCient(ope);
            System.out.println("\033c");
            System.out.println("=============================================");
            System.out.println("|       Quadro de operações possíveis       |");
            System.out.println("|-------------------------------------------|");
            System.out.println("| multiplicação  | * || mostrar memória | h |");
            System.out.println("| divisão        | / || sair            | e |");
            System.out.println("| adição         | + || fatorial        | f |");
            System.out.println("| subtração      | - || potencia        | p |");
            System.out.println("=============================================");
            String entrada = sc.nextLine();
            //String[] caracteres = entrada.trim().split(" ");
            if (entrada.length() == 1) 
            {
                switch (entrada.substring(0, 1)){
                    case "h":
                        System.out.println("Histórico");
                        System.out.println(Arrays.toString(ope.getMemory()));
                    break;
                    case "e":
                        continuar = false;
                        sol.demorou();
                    break;
                    case "f":
                        System.out.println("coloque numero para fatorial sem !");
                        sol.fatorial(Integer.parseInt(sc.nextLine()));
                        break;
                    
                    case "p":
                        System.out.println("coloque o primeiro numero para para potencia");
                        Double a = Double.parseDouble(sc.nextLine());
                        System.out.println("coloque o Segundo numero para para potencia");
                        int b = Integer.parseInt(sc.nextLine());
                        System.out.println("Solução: " + sol.pow(a,b));
                }   
            }
            else if (entrada.length() >= 2 ) //entradas com quantidade par de caracteres não sâo válidas (( " 1 + 1 + ", ... ))
            { 

               System.out.println(sol.solve(entrada));
            }
            System.out.println("Pressione ENTER para continuar");
            sc.nextLine();
        }
    }
}