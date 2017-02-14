/**
 * Created by bzeymer on 13/02/17.
 */

public class Individuo implements Comparable<Individuo>{

    private Integer valoresX;
    private String xEmBinario;
    private Double resultadoX;

    public Individuo(Integer valoresX) {
        this.valoresX = valoresX;
        this.resultadoX = (Main.A * Math.pow(valoresX, 2)) + (Main.B * valoresX) + Main.C;
        this.xEmBinario = Integer.toBinaryString(Math.abs(valoresX));
        if (this.xEmBinario.length() == 1) this.xEmBinario = "000" + this.xEmBinario;
        if (this.xEmBinario.length() == 2) this.xEmBinario = "00" + this.xEmBinario;
        if (this.xEmBinario.length() == 3) this.xEmBinario = "0" + this.xEmBinario;
    }

    public String getxEmBinario() {
        return xEmBinario;
    }

    public Integer getValoresX() {

        return valoresX;
    }

    public Double getResultadoX() {
        return resultadoX;
    }

    public int compareTo(Individuo individuo) {

        return -(this.resultadoX.compareTo(individuo.resultadoX));
    }
}
