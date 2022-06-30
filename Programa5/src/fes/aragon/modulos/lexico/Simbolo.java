/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.modulos.lexico;

/**
 *
 * @author Josh
 */
public class Simbolo {
    private int tipoSimbolo;
    private int numeroColumna;
    private int numeroLinea;
    private String simbolo;
    private String tipo;
    private boolean declarado;
    private boolean inicializado;

    public Simbolo(int tipoSimbolo, int numeroColumna, int numeroLinea, String simbolo, String tipo, boolean declarado, boolean inicializado) {
        this.tipoSimbolo = tipoSimbolo;
        this.numeroColumna = numeroColumna;
        this.numeroLinea = numeroLinea;
        this.simbolo = simbolo;
        this.tipo = tipo;
        this.declarado = declarado;
        this.inicializado = inicializado;
    }

    public int getTipoSimbolo() {
        return tipoSimbolo;
    }

    public void setTipoSimbolo(int tipoSimbolo) {
        this.tipoSimbolo = tipoSimbolo;
    }

    public int getNumeroColumna() {
        return numeroColumna;
    }

    public void setNumeroColumna(int numeroColumna) {
        this.numeroColumna = numeroColumna;
    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isDeclarado() {
        return declarado;
    }

    public void setDeclarado(boolean declarado) {
        this.declarado = declarado;
    }

    public boolean isInicializado() {
        return inicializado;
    }

    public void setInicializado(boolean inicializado) {
        this.inicializado = inicializado;
    }

    
}
