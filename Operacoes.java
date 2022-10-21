package br.com.bb.Calc;
import java.math.BigDecimal;
public class Operacoes {
    private BigDecimal[] memory = new BigDecimal[5];
    public BigDecimal[] getMemory() {
        return memory;
    }
    public Operacoes()
    {
      this.memory[0] = new BigDecimal(0);
    }
    void put(BigDecimal val){
        for(int i = memory.length-1; i > 0; i--){
            memory[i] = memory[i-1]; 
        }
        memory[0] = val;
    }
    //2
    public BigDecimal adicao(BigDecimal a, BigDecimal b){
        put(a.add(b));
        return this.memory[0];
    }
    public BigDecimal sub(BigDecimal a, BigDecimal b){
        put(a.subtract(b));
        return this.memory[0];
    }
    public BigDecimal mult(BigDecimal a, BigDecimal b){
        put(a.multiply(b));
        return this.memory[0];
    }
    public BigDecimal div(BigDecimal a, BigDecimal b){
        put(a.divide(b));
        return this.memory[0];
    }
    //1
    public BigDecimal adicao(BigDecimal b){
        put(memory[0].add(b));
        return this.memory[0];
    }
    public BigDecimal sub(BigDecimal b){
        put(memory[0].subtract(b));
        return this.memory[0];
    }
    public BigDecimal mult(BigDecimal b){
        put(memory[0].multiply(b));
        return this.memory[0];
    }
    public BigDecimal div(BigDecimal b){
        put(memory[0].divide(b));
        return this.memory[0];
    }

    public BigDecimal pow(BigDecimal a, int b)
    {
        return a.pow(b);
    }
}