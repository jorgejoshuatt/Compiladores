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
public class LexicoDos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int estado=0;
        int entrada=0;
        Herramientas hr=new Herramientas();
        ArrayList<String> lineas=null;
        char simbolo=' ';
        int linea=0;
        String[][] tabla={{"2","1","0"},{"1","1","0"},{"2","2","1"}};
        try{
            lineas=hr.lectura();
            while(linea<lineas.size()){
               hr.setPalabra(lineas.get(linea));
            do{
            simbolo=hr.siguienteCaracter();
            if(RangoDeValores.letra(simbolo)){
                entrada=0;
            }else if(RangoDeValores.enterosPositivos(simbolo)){
                entrada=1;
            }else if(RangoDeValores.finCadena(simbolo)){
                entrada=2;
            }else{
                throw new ErrorLexico("Caracter no válido, linea "+(linea+1));
            }
            int valor=Integer.valueOf(tabla[estado][entrada]);
            estado=valor;
            if(estado==0){
               throw new ErrorLexico("Caracter no válido, linea "+(linea+1)); 
            }
            
        }while(simbolo!=59);
        if(entrada==2 && estado==1){
            System.out.println("Variable valida, linea "+(linea+1));
        }else if(entrada==2 && estado==0){
            System.out.println("Variable no valida, linea "+(linea+1));
        }
        estado=0;
        simbolo=' ';
        linea++;
        }
        }catch (IOException ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en el archivo");
        }catch (ErrorLexico ex) {
            System.out.println("Variable no valida, linea "+(linea+1));
        }
    }  
}