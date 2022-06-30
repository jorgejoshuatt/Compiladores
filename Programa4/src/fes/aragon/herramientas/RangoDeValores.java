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
public class RangoDeValores {
    public static boolean enterosPositivos(char entero){
        boolean valido=false;
        if((entero>=48) && (entero<=57)){
            valido=true;
        }
        return valido;
    }
    public static boolean letra(char letra){
        boolean valido=false;
        if((letra>=65 && letra<=90) || (letra<=122 && letra>=97)){
            valido=true;
        }
        return valido;
    }
    public static boolean finCadena(char letra){
        boolean valido=false;
        if(letra==59){
            valido=true;
        }
        return valido;
    }
}