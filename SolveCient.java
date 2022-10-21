package br.com.bb.Calc;

import java.math.BigDecimal;

public class SolveCient extends Solve{
    SolveCient(Operacoes ope)
    {
        super(ope);
    }



    public void demorou()
    {
        System.out.println("só demoramos um pouco");
    }

    public void fatorial(int a)
    {
        for (int i = a-1; i>0; i--)
        {
            a=a*i;
        }
        System.out.println("Solução: " + a);
    }

    public Double pow(Double numerador, int expoente) {

        Double inicial = numerador;
        if (!(expoente == 1)) {

            for (int i = 1; i < expoente; i++) {

             numerador = inicial * numerador;

            }

            return numerador;

        }
        return numerador;
    }
        

}
