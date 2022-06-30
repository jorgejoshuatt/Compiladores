/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.herramientas;

/**
 *
 * @author Josh
 */
public class ErrorLexico extends Exception{
    private String error;
    private int numeroColumna;
    private int numeroLinea;

    public ErrorLexico(String error) {
        super(error);
    }
    
    

    public ErrorLexico(String error, int numeroColumna, int numeroLinea) {
        super(error);
        this.error = error;
        this.numeroColumna = numeroColumna;
        this.numeroLinea = numeroLinea;
    }
}