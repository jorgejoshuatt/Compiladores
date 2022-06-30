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
 * (0|1)*00(1)*
 * @author Josh
 */
public class AnalizadorCeros {
    private Herramientas hr=new Herramientas();
    private ArrayList<String> lineas=new ArrayList<>();
    private int linea=0;
    private char simbolo=' ';
    private final boolean valido=true;
    public static void main(String[] args) {
        AnalizadorCeros app=new AnalizadorCeros();
        try {
            app.lineas=app.hr.lectura();
            for(int i=0; i<app.lineas.size();i++){
                try{
            app.hr.setPalabra(app.lineas.get(app.linea));
            app.simbolo=app.hr.siguienteCaracter();
            if(app.estado_A()){
                System.out.println("Cadena válida "+(app.linea+1));
            }
            
                }catch (ErrorLexico ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } 
        app.linea++;
            }
        }catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error en el archivo");
        }
        
    }
    private boolean estado_A() throws ErrorLexico{
        switch(this.simbolo){
            case '1':
                return estado_C();
            case '0':
                return estado_B();
            default:
                throw new ErrorLexico("Caracter no reconocido: linea "+(this.linea+1));
        }
    }
    private boolean estado_B() throws ErrorLexico{
        char c=this.hr.siguienteCaracter();
        switch(c){
            case '1':
                return estado_C();
            case '0':
                return estado_D();
            default:
                throw new ErrorLexico("Caracter no reconocido: linea "+(this.linea+1));
        }
    }
    private boolean estado_C() throws ErrorLexico{
        char c=this.hr.siguienteCaracter();
        switch(c){
            case '1':
                return estado_C();
            case '0':
                return estado_B();
            default:
                throw new ErrorLexico("Caracter no reconocido: linea "+(this.linea+1));
        }
    }
    private boolean estado_D() throws ErrorLexico{
        char c=this.hr.siguienteCaracter();
        switch(c){
            case '1':
                return estado_E();
            case '0':
                return estado_D();
            default:
                throw new ErrorLexico("Caracter no reconocido: linea "+(this.linea+1));
        }
    }
    private boolean estado_E() throws ErrorLexico{
        char c=this.hr.siguienteCaracter();
        switch(c){
            case '1':
                return estado_E();
            case '0':
                return estado_B();
            case ';':
                return valido;
            default:
                throw new ErrorLexico("Caracter no reconocido: linea "+(this.linea+1));
        }
    }
  
}
