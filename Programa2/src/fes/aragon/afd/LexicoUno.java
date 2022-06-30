/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.afd;

import fes.aragon.herramientas.ErrorLexico;
import fes.aragon.herramientas.Herramientas;
import fes.aragon.herramientas.RangoDeValores;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Josh
 */
public class LexicoUno {
    public static void main(String[] args){
        int estado=1;
        Herramientas hr=new Herramientas();
        ArrayList<String> lineas=null;
        char simbolo=' ';
        int linea=0;
        try {
            lineas=hr.lectura();
            while(linea<lineas.size()){
                hr.setPalabra(lineas.get(linea));
                simbolo=hr.siguienteCaracter();
                while(simbolo!=32){
                    switch(estado){
                        case 1:
                            if(RangoDeValores.letra(simbolo)){
                                estado=3;
                            }else if (RangoDeValores.enterosPositivos(simbolo)){
                                estado=2;
                            }else{
                                throw new ErrorLexico("Caracter no válido, linea "+(linea+1));
                            }
                            break;
                        case 2:
                            throw new ErrorLexico("Caracter no válido, linea "+(linea+1));
                        case 3:
                            if(RangoDeValores.letra(simbolo)){
                                estado=3;
                            }else if (RangoDeValores.enterosPositivos(simbolo)){
                                estado=3;
                            }else{
                                throw new ErrorLexico("Caracter no válido, linea "+(linea+1));
                            }
                            break; 
                    }
                    simbolo=hr.siguienteCaracter();
                }
                if(estado==3){
                    System.out.println("Variable válida, línea "+(linea+1));
                }
                estado=1;
                simbolo=' ';
                linea++;
            }
            System.exit(0);
            } catch (IOException ex) {
                //ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error en el archivo");
            } catch (ErrorLexico ex) {
                //ex.printStackTrace();
                System.out.println("Variable no valida, linea "+(linea+1));
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }