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
                        System.out.println("Estructura if correcta");
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
        if (token.getLexema() == Sym.IF) {
            System.out.print(token.getToken() + "-> ");
            siguienteToken();
            if (token.getLexema() == Sym.PARA) {
                System.out.print(token.getToken() + "-> ");
                siguienteToken();
                A();
                if (token.getLexema() == Sym.PARC) {
                    System.out.print(token.getToken() + "-> ");
                    siguienteToken();
                    F();
                    B();
                } else {
                    errorEnLinea = true;
                    throw new IOException("Error al compilar línea " + (token.getLinea()));
                }
            } else {
                errorEnLinea = true;
                throw new IOException("Error al compilar línea " + (token.getLinea()));
            }
        }else{
            errorEnLinea=true;
            throw new IOException("Error al compilar línea "+(token.getLinea()));
        }
    }
    private void B() throws IOException {
        System.out.print("B-> ");
        if (token.getLexema() == Sym.END) {
            System.out.print(token.getToken() + " ");
            siguienteToken();
            return;
            
        } else if (token.getLexema() == Sym.ELSE) {
            System.out.print(token.getToken() + "-> ");
            siguienteToken();
            F();
            if (token.getLexema() == Sym.END) {
                System.out.print(token.getToken()+" ");
                siguienteToken();
                return;
            }
        }else{
            errorEnLinea=true;
            throw new IOException("Error al compilar línea "+(token.getLinea()));
        }
    }
    private void A() throws IOException {
        System.out.print("A-> ");
        C();
        D();
        C();
    }
    private void D() throws IOException{
        System.out.print("D-> ");
        if(token.getLexema()==Sym.COMPARADORES){
            System.out.print(token.getToken()+"-> ");
            siguienteToken();
        }
    }
    private void C() throws IOException{
        System.out.print("C-> ");
        if(token.getLexema()==Sym.ID){
            System.out.print(token.getToken()+"-> ");
            siguienteToken();
        }else{
        E();
        }
    }
    private void E() throws IOException{
        System.out.print("E-> ");
        if(token.getLexema()==Sym.ENTERO){
            System.out.print(token.getToken()+"-> ");
            siguienteToken();
        }else if(token.getLexema()==Sym.REAL){
            System.out.print(token.getToken()+"-> ");
            siguienteToken();
        }
    }
    private void F() throws IOException{
        System.out.print("F-> ");
        G();
        if(token.getLexema()==Sym.PUNTOCOMA){
            System.out.print(token.getToken()+"-> ");
            siguienteToken();
            Fc();
        }
        
    }
    private void Fc() throws IOException{
        System.out.print("F'-> ");
        F();
    }
    private void G() throws IOException{
        System.out.print("G-> ");
        
        if(token.getLexema()==Sym.IF){
            S();
        }if(token.getLexema()==Sym.ID){
            H();
        }
    }
    private void H() throws IOException{
        System.out.print("H-> ");
        if(token.getLexema()==Sym.ID){
            System.out.print(token.getToken()+"-> ");
            siguienteToken();
            if(token.getLexema()==Sym.ASIGNACION){
            System.out.print(token.getToken()+"-> ");
            siguienteToken();
            E();
        }
        }
    }
}
