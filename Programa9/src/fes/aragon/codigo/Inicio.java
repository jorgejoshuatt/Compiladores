/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.codigo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorgejoshuat
 */

public class Inicio {
    private AnalizadorLexico analizador=null;
    private Token token=null; 
    //false->no hay error en la línea; true->si hay error en la línea
    private boolean errorEnLinea=false;
    private void siguienteToken(){
        try {
            token=analizador.yylex();
            if(token==null){
                token=new Token("EOF", Sym.EOF, 0, 0);
                throw new IOException("Fin");
            }
        } catch (IOException ex) {
            System.out.println("Fin de Archivo");
        }        
    }
    public static void main(String[] args) {
        try {
            Inicio app=new Inicio();
            BufferedReader buf;
            buf=new BufferedReader(new FileReader(System.getProperty("user.dir")+
                    "/fuente.txt"));
            app.analizador=new AnalizadorLexico(buf);
            app.siguienteToken();
            while(app.token.getLexema()!=Sym.EOF){
                try {
                    app.S();
                    if(!app.errorEnLinea){
                        System.out.println(" Línea "+(app.token.getLinea())+" correcta");
                    }else{
                        System.out.println(" Línea "+(app.token.getLinea())+" incorrecta");
                    }
                    app.errorEnLinea=false;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                
                //System.out.println(app.token.getToken());
                app.siguienteToken();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
    }
    //INICIO DEL CODIGO DEL ANALIZADOR SINTACTICO

    private void S() throws IOException {
        System.out.print("S-> ");
        E();
        if (token.getLexema() != Sym.PUNTOCOMA) {
            errorEnLinea = true;
            throw new IOException("Error al compilar " + (token.getLinea()));
        } else {
            errorEnLinea = false;
            System.out.print(token.getToken());
        }
    }
    
    private void E() throws IOException{
        System.out.print("E-> ");
        if((token.getLexema()==Sym.ENTERO)||(token.getLexema()==Sym.REAL)||(token.getLexema()==Sym.ID)){
            System.out.print(token.getToken()+"-> ");
            siguienteToken();
            if(token.getLexema()==Sym.OP){
                X();
            }
        }else if(token.getLexema()==Sym.PARA){
            
            System.out.print(token.getToken()+"-> ");
            siguienteToken();
            E();
            if(token.getLexema()==Sym.PARC){
                siguienteToken();
                if(token.getLexema()==Sym.OP){
                    X();
                }
            }
        }else{
            errorEnLinea=true;
            throw new IOException("Error al compilar en la línea "+(token.getLinea()));
        }
    }
    
    private void X() throws IOException {
        System.out.print("X-> ");
        if (token.getLexema() == Sym.OP) {
            System.out.print(token.getToken()+"-> ");
            siguienteToken();
            E();
            if (token.getLexema() == Sym.OP) {
                System.out.print(token.getToken()+"-> ");
                X();
            }
        }else{
            errorEnLinea=true;
            throw new IOException("Error al compilar en la línea "+(token.getLinea()));
        }
    }
}
