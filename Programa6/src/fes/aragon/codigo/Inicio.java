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

/**
 *
 * @author Josh
 */
public class Inicio {
    private AnalizadorLexico analizador=null;
    private Token token=null;
    public static void main(String[] args) {
        try{
            Inicio app=new Inicio();
            BufferedReader buf;
            buf=new BufferedReader(new FileReader(System.getProperty("user.dir")+"/fuente.txt"));
            app.analizador=new AnalizadorLexico(buf);
            app.siguienteToken();
            while(app.token.getLexema()!=Sym.EOF){
                System.out.println(app.token.getToken());
                app.siguienteToken();
            }
    }catch (FileNotFoundException ex){
        ex.printStackTrace();
    }
    }
    private void siguienteToken(){
        try{
            token=analizador.yylex();
            if(token==null){
                token=new Token("EOF", Sym.EOF,0,0);
                throw new IOException("Fin");
        }
    }catch (IOException ex){
            System.out.println("Fin de archivo");
    }
    }
}
