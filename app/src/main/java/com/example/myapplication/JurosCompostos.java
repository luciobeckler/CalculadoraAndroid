package com.example.myapplication;

public class JurosCompostos {
    private double pv;
    private double fv;
    private double PMT;
    private double i;
    private double n;

    // Getters e Setters
    public double getPv() {
        return pv;
    }

    public Double setPv(double pv) {
        if (fv != 0 && PMT != 0 && i != 0 && n != 0) {
            this.pv = calcularPV();
            return this.pv;
        } else {
            this.pv = pv;
            return null;
        }
    }

    public double getFv() {
        return fv;
    }

    //Separar os métodos, o set apenas seta e o calcular apenas calcula e retorna o valor, a
    // diferenciação será pelo modo, EDITANDO seta a variável e EXIBINDO calcula
    public Double setFv(double fv) {
        if (pv != 0 && PMT != 0 && i != 0 && n != 0) {
            this.fv = calcularFV();
            return this.fv;
        } else {
            this.fv = fv;
            return null;
        }
    }

    public double getPMT() {
        return PMT;
    }

    public Double setPMT(double PMT) {
        if (pv != 0 && fv != 0 && i != 0 && n != 0) {
            this.PMT = calcularPMT();
            return this.PMT;
        } else {
            this.PMT = PMT;
            return null;
        }
    }

    public double getI() {
        return i;
    }

    public Double setI(double i) {
        if (pv != 0 && fv != 0 && PMT != 0 && n != 0) {
            this.i = calcularI();
            return this.i;
        } else {
            this.i = i;
            return null;
        }
    }

    public Double getN() {
        return n;
    }

    public Double setN(Double n) {
        if (pv != 0 && fv != 0 && PMT != 0 && i != 0) {
            this.n = calcularN();
            return this.n;
        } else {
            this.n = n;
            return null;
        }
    }

    // Métodos para calcular as variáveis baseadas nas outras
    private double calcularPV() {
        return fv / Math.pow(1 + i, n) + (PMT * (1 - Math.pow(1 + i, -n)) / i);
    }

    private double calcularFV() {
        return pv * Math.pow(1 + i, n) + (PMT * (Math.pow(1 + i, n) - 1) / i);
    }

    private double calcularPMT() {
        return (fv - pv * Math.pow(1 + i, n)) * i / (Math.pow(1 + i, n) - 1);
    }

    private double calcularI() {
        // Cálculo aproximado da taxa de juros (i) através de iteração numérica (exemplo simplificado)
        double guessI = 0.01; // Chute inicial
        double tolerance = 0.00001;
        double difference;

        do {
            double numerator = fv - (pv * Math.pow(1 + guessI, n)) - (PMT * (Math.pow(1 + guessI, n) - 1) / guessI);
            double denominator = n * pv * Math.pow(1 + guessI, n - 1) + PMT * (n * Math.pow(1 + guessI, n - 1) - (Math.pow(1 + guessI, n) - 1) / guessI);
            difference = numerator / denominator;
            guessI -= difference;
        } while (Math.abs(difference) > tolerance);

        return guessI;
    }

    private int calcularN() {
        return (int) (Math.log((fv * i + PMT) / (PMT + pv * i)) / Math.log(1 + i));
    }
}
