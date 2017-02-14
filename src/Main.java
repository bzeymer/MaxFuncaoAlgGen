import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static final Integer A = 1;
    public static final Integer B = -3;
    public static final Integer C = 4;

    public static final Integer TAM_POPULACAO = 10;
    public static final Integer TAXA_MUTACAO = 1;
    public static final Double TAXA_CROSSOVER = 0.7;
    public static final Integer NUM_GERACOES = 200;

    public static final Double DELTA = Math.pow(B, 2.0) - (4.0 * A * C);

    public static Individuo selectFromPopulation(ArrayList<Individuo> populacao) {

        Individuo candidato1 = populacao.get(ThreadLocalRandom.current().nextInt(populacao.size()));
        Individuo candidato2 = populacao.get(ThreadLocalRandom.current().nextInt(populacao.size()));

        if (candidato1.getResultadoX() > candidato2.getResultadoX()) {
            return candidato1;
        } else {
            return candidato2;
        }
    }

    public static Individuo doCrossoverParents(Individuo pai, Individuo mae) {

        Integer sinalPai = (pai.getValoresX() < 0 ? -1 : 1);
        Integer sinalMae = (mae.getValoresX() < 0 ? -1 : 1);
        Integer sinalFilho = sinalPai * sinalMae;

        Integer xFilho = 11;

        while(xFilho > 10) {

            int pontoDeSplice = ThreadLocalRandom.current().nextInt(4);
            String binarioFilho = pai.getxEmBinario().substring(0, pontoDeSplice) + mae.getxEmBinario().substring(pontoDeSplice, 4);
            xFilho = Integer.parseInt(binarioFilho, 2);
        }

        return new Individuo(xFilho * sinalFilho);
    }

    public static Individuo doMutate(Individuo filho) {

        Integer xFilhoMutado = 11;

        while(xFilhoMutado> 10) {

            String cromossomo = filho.getxEmBinario();
            int pontoDeMutacao = ThreadLocalRandom.current().nextInt(4);

            if (cromossomo.charAt(pontoDeMutacao) == '1') {
                cromossomo = cromossomo.substring(0, pontoDeMutacao) + '0' + cromossomo.substring(pontoDeMutacao + 1);
            } else if (cromossomo.charAt(pontoDeMutacao) == '0') {
                cromossomo = cromossomo.substring(0, pontoDeMutacao) + '1' + cromossomo.substring(pontoDeMutacao + 1);
            }

            xFilhoMutado = Integer.parseInt(cromossomo, 2);
        }

        return new Individuo(xFilhoMutado);

    }

    public static void main(String[] args) {

        System.out.println("População inicial: ");
        ArrayList<Individuo> populacao = new ArrayList<>();
        for (int i = 0; i < TAM_POPULACAO; i++) {
            Individuo individuo = new Individuo(ThreadLocalRandom.current().nextInt(-10, 11));
            populacao.add(individuo);
            System.out.println("indivíduo " + i + ": x: " + individuo.getValoresX() + ", F(x)" + individuo.getResultadoX() + ", |x| em binário: " + individuo.getxEmBinario());
        }

        for (int geracao = 0; geracao < NUM_GERACOES; geracao++) {

            ArrayList<Individuo> proxGeracao = new ArrayList<>();

            for (int i = 0; i < TAM_POPULACAO; i++) {

                //INÍCIO SELEÇÃO
                Individuo pai = selectFromPopulation(populacao);
                Individuo mae = selectFromPopulation(populacao);
                //FIM SELEÇÃO

                //INÍCIO CROSSOVER
                Individuo filho;
                if (ThreadLocalRandom.current().nextInt(100) < TAXA_CROSSOVER) {
                    filho = doCrossoverParents(pai, mae);
                } else {
                    filho = (pai.getResultadoX() > mae.getResultadoX() ? pai : mae);
                }
                //FIM CROSSOVER

                //INÍCIO MUTAÇÃO
                if (ThreadLocalRandom.current().nextInt(100) < TAXA_MUTACAO) {
                    filho = doMutate(filho);
                }
                //FIM MUTAÇÃO

                proxGeracao.add(filho);
            }

            System.out.println("Geração " + geracao + "\n");

            for (Individuo individuo : populacao) {
                System.out.println("x: " + individuo.getValoresX() + ", F(x): " + individuo.getResultadoX() + ", |x| em binário: " + individuo.getxEmBinario());
            }
            System.out.println();

            populacao = proxGeracao;

        }

        System.out.println("Última geração");

        for (Individuo individuo : populacao) {
            System.out.println("x: " + individuo.getValoresX() + ", F(x): " + individuo.getResultadoX() + ", |x| em binário: " + individuo.getxEmBinario());
        }
        System.out.println();

        Collections.sort(populacao);

        System.out.println("Melhor resultado pelo algoritmo genético: \nx: " + populacao.get(0).getValoresX() + ", F(x): " + populacao.get(0).getResultadoX() + ", |x| em binário: " + populacao.get(0).getxEmBinario());

    }
}
