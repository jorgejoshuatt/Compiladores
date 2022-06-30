/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.modulos.lexico;
import fes.aragon.herramientas.ErrorLexico;
import fes.aragon.herramientas.Herramientas;
import fes.aragon.herramientas.RangoDeValores;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Josh
 */
public class Lexico {
   private ArrayList<String> codigo;
   private ArrayList<ErrorLexico> errores;
   private ArrayList<Simbolo> simbolos;
   private String[] palabrasReservadas;
   private int numeroLinea;
   private int numeroErrores;
   private int estado;
   private int indiceSimbolos;
   private String tokenReconocidos;
   private Herramientas hr;
   private char simbolo;

    public Lexico() throws ErrorLexico {
        hr=new Herramientas();
        this.numeroLinea=0;
        this.numeroErrores=0;
        this.tokenReconocidos="";
        this.estado=0;
        this.indiceSimbolos++;
        this.errores=new ArrayList<>();
        this.simbolos=new ArrayList<>();
       try {
           this.codigo=hr.lectura();
           this.crearTablas();
           hr.setLinea(codigo.get(this.numeroLinea));
           simbolo=hr.siguienteCaracter();
       } catch (IOException ex) {
           numeroErrores++;
           this.errores.add(new ErrorLexico("Error en el archivo", hr.getColumnaLinea()+1,numeroLinea+1));
           throw new ErrorLexico("Error en el archivo");
       }
    }
    private void crearTablas(){
        this.palabrasReservadas=new String[8];
        this.palabrasReservadas[0]="inicio";
        this.palabrasReservadas[1]="fin";
        this.palabrasReservadas[2]="entero";
        this.palabrasReservadas[3]="real";
        this.palabrasReservadas[4]="mientras";
        this.palabrasReservadas[5]="finmientras";
        this.palabrasReservadas[6]="si";
        this.palabrasReservadas[7]="sino";
    }
   
    public Token siguienteToken(){
        boolean tokenEncontrado=false;
        Token t=null;
        Simbolo s=null;
        String palabra="";
        int salto=0;
        while(numeroLinea<this.codigo.size() && numeroErrores<5 && tokenEncontrado==false){
            switch(this.estado){
                case 0:
                    if(simbolo==' ' || simbolo=='\t'){
                        this.estado=0;
                        this.numeroLinea++;
                        if(numeroLinea<this.codigo.size()){
                            hr.setLinea(codigo.get(this.numeroLinea));
                            hr.setColumnaLinea(0);
                            simbolo=hr.siguienteCaracter();
                            }else{
                            break;
                            }
                    }else if(simbolo==':'){
                        this.estado=9;
                    }else if(RangoDeValores.letra(simbolo)){
                        this.estado=1;
                    }else if(simbolo=='('){
                        this.estado=11;
                    }else if(simbolo==')'){
                        this.estado=12;
                    }else if(simbolo==';'){
                        this.estado=8;
                    }else if(RangoDeValores.enterosPositivos(simbolo)){
                        this.estado=3;
                    }else if(simbolo=='!'){
                        this.estado=13;
                    }else if(simbolo=='='){
                        this.estado=16;
                    }else if(simbolo=='>'){
                            this.estado=17;
                    }else if(simbolo=='<'){
                        this.estado=20;
                    }else if(simbolo=='/'){
                        this.estado=26;
                    }else if(simbolo=='*'){
                        this.estado=25;
                    }else if(simbolo=='-'){
                        this.estado=24;
                    }else if(simbolo=='+'){
                        this.estado=23;
                    }
                    break;
                
                case 1:
                    do {                        
                        if(RangoDeValores.letra(simbolo) || RangoDeValores.enterosPositivos(simbolo)){
                            palabra+=simbolo;
                        }
                        simbolo=hr.siguienteCaracter();
                    }while(RangoDeValores.letra(simbolo) || RangoDeValores.enterosPositivos(simbolo));
                    this.estado=2;
                    break;
                case 2:
                    this.estado=0;
                    tokenEncontrado=true;
                    t=identificadorAsignarId(palabra);
                    break;
                case 3:
                    do {                        
                            if(RangoDeValores.enterosPositivos(simbolo)){
                                palabra+=simbolo;
                            }
                            simbolo=hr.siguienteCaracter();
                        }while(RangoDeValores.enterosPositivos(simbolo));
                    if(simbolo=='.'){
                        this.estado=5;
                    }else{ 
                        this.estado=4;
                        }
                    break;
                case 4:
                    this.estado=0;
                    tokenEncontrado=true;
                    s=new Simbolo(3, ((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1, palabra, "", false, false);
                    simbolos.add(s);
                    t=new Token(3, ((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1, this.indiceSimbolos++, palabra, "numero");
                    tokenReconocidos+=t.getToken()+"\n";
                    break;
                case 5:
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    if(RangoDeValores.enterosPositivos(simbolo)){
                        palabra+=simbolo;
                        this.estado=6;
                    }else{
                        this.estado=0;
                        numeroErrores++;
                        ErrorLexico error= new ErrorLexico("Dato numerico mal estructurado", hr.getColumnaLinea()+1, numeroLinea+1);
                        this.errores.add(error);
                    }
                    break;
                case 6:
                    do {     
                        simbolo=hr.siguienteCaracter();
                        if(RangoDeValores.enterosPositivos(simbolo)){
                            palabra+=simbolo;
                        }
                    }while(RangoDeValores.enterosPositivos(simbolo));
                    this.estado=7;
                    break;
                case 7:
                    this.estado=0;
                    tokenEncontrado=true;
                    s=new Simbolo(3, ((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1, palabra, "", false, false);
                    simbolos.add(s);
                    t=new Token(3, ((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1, this.indiceSimbolos++, palabra, "numero");
                    tokenReconocidos+=t.getToken()+"\n";
                    break;
                case 8:
                    this.estado=0;
                    tokenEncontrado=true;
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    t=new Token(0, hr.getColumnaLinea()+1, numeroLinea+1, -1, palabra, palabra);
                    tokenReconocidos+=t.getToken()+"\n";
                    break;
                case 9:
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    if(simbolo=='='){
                        this.estado=10;
                    }else{
                        this.estado=0;
                        ErrorLexico error=new ErrorLexico("Expresión mal estructurada", hr.getColumnaLinea()+1, numeroLinea+1);
                        this.errores.add(error);
                        this.numeroErrores++;
                    }
                    break;
                case 10:
                    this.estado=0;
                    tokenEncontrado=true;
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    t=new Token(0, hr.getColumnaLinea()+1, numeroLinea+1, -1, palabra, palabra);
                    tokenReconocidos+=t.getToken()+"\n"; 
                    break;
                case 11:
                    this.estado=0;
                    tokenEncontrado=true;
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    t=new Token(0, hr.getColumnaLinea()+1, numeroLinea+1, -1, palabra, palabra);
                    tokenReconocidos+=t.getToken()+"\n";
                    break;
                case 12:
                    this.estado=0;
                    tokenEncontrado=true;
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    t=new Token(0, hr.getColumnaLinea()+1, numeroLinea+1, -1, palabra, palabra);
                    tokenReconocidos+=t.getToken()+"\n";
                    break;
                case 13:
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    if(simbolo=='='){
                        this.estado=15;
                    }else{
                        this.estado=14;
                    }
                    break;
                case 14:
                    this.estado=0;
                    tokenEncontrado=true;
                    s=new Simbolo(1, ((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1, palabra, "", false, false);
                    simbolos.add(s);
                    t=new Token(0, ((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1, this.indiceSimbolos++, palabra, "palabra reservada");
                    tokenReconocidos+=t.getToken()+"\n";
                    break;
                case 15:
                    this.estado=0;
                    tokenEncontrado=true;
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    t=new Token(0, hr.getColumnaLinea()+1, numeroLinea+1, -1, palabra, palabra);
                    tokenReconocidos+=t.getToken()+"\n"; 
                    break;
                case 16:
                    this.estado=0;
                    tokenEncontrado=true;
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    t=new Token(0, hr.getColumnaLinea()+1, numeroLinea+1, -1, palabra, palabra);
                    tokenReconocidos+=t.getToken()+"\n"; 
                    break;
                case 17:
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    if(simbolo=='='){
                        this.estado=18;
                    }else{
                        this.estado=19;
                    }
                    break;
                case 18:
                    this.estado=0;
                    tokenEncontrado=true;
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    t=new Token(0, hr.getColumnaLinea()+1, numeroLinea+1, -1, palabra, palabra);
                    tokenReconocidos+=t.getToken()+"\n"; 
                    break;
                case 19:
                    this.estado=0;
                    tokenEncontrado=true;
                    s=new Simbolo(1, ((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1, palabra, "", false, false);
                    simbolos.add(s);
                    t=new Token(0, ((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1, this.indiceSimbolos++, palabra, "palabra reservada");
                    tokenReconocidos+=t.getToken()+"\n";
                case 20:
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    if(simbolo=='='){
                        this.estado=21;
                    }else{
                        this.estado=22;
                    }
                    break;
                case 21:
                    this.estado=0;
                    tokenEncontrado=true;
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    t=new Token(0, hr.getColumnaLinea()+1, numeroLinea+1, -1, palabra, palabra);
                    tokenReconocidos+=t.getToken()+"\n"; 
                    break;
                case 22:
                    this.estado=0;
                    tokenEncontrado=true;
                    s=new Simbolo(1, ((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1, palabra, "", false, false);
                    simbolos.add(s);
                    t=new Token(0, ((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1, this.indiceSimbolos++, palabra, "palabra reservada");
                    tokenReconocidos+=t.getToken()+"\n";
                    break;
                case 23:
                    this.estado=0;
                    tokenEncontrado=true;
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    t=new Token(0, hr.getColumnaLinea()+1, numeroLinea+1, -1, palabra, palabra);
                    tokenReconocidos+=t.getToken()+"\n";
                    break;
                case 24:
                    this.estado=0;
                    tokenEncontrado=true;
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    t=new Token(0, hr.getColumnaLinea()+1, numeroLinea+1, -1, palabra, palabra);
                    tokenReconocidos+=t.getToken()+"\n";
                    break;
                case 25:
                    this.estado=0;
                    tokenEncontrado=true;
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    t=new Token(0, hr.getColumnaLinea()+1, numeroLinea+1, -1, palabra, palabra);
                    tokenReconocidos+=t.getToken()+"\n";
                    break;
                case 26:
                    this.estado=0;
                    tokenEncontrado=true;
                    palabra+=simbolo;
                    simbolo=hr.siguienteCaracter();
                    t=new Token(0, hr.getColumnaLinea()+1, numeroLinea+1, -1, palabra, palabra);
                    tokenReconocidos+=t.getToken()+"\n";
                    break;
            }
        }
        return t;
    }
    private Token identificadorAsignarId(String palabra){
        String tipoToken="";
        Token t=null;
        Simbolo s=null;
        boolean encontrado=false;
        //comprobar palabra reservada del lenguaje
        for (int i = 0; i < this.palabrasReservadas.length; i++) {
            if(palabra.equals(this.palabrasReservadas[i])){
                tipoToken=this.palabrasReservadas[i];
                encontrado=true;
                break;
            }
        }
        if(encontrado){
            t=new Token(0, hr.getColumnaLinea()+1, numeroLinea+1, -1, palabra, tipoToken);
            tokenReconocidos+=t.getToken()+"\n";
        }else{
            encontrado=false;
            if(palabra.length()<16){
                //si ya está en la tabla de símbolos
                int j=0;
                for (int i = 0; i < this.simbolos.size(); i++) {
                    if(palabra.equals(this.simbolos.get(i).getSimbolo())){
                        encontrado=true;
                        j=i;
                        break;
                    }
                }
                if(encontrado){
                    t=new Token(1, ((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1, j, palabra, "identificador");
                    tokenReconocidos+=t.getToken()+"\n";
                }else{
                    s=new Simbolo(1, ((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1, palabra, "", false, false);
                    simbolos.add(s);
                    t=new Token(1, ((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1, j, palabra, "identificador");
                    tokenReconocidos+=t.getToken()+"\n";
                }
            }else{
                numeroErrores++;
                ErrorLexico error=new ErrorLexico("Identificador demasiado largo",((hr.getColumnaLinea()-palabra.length())+1), numeroLinea+1);
                this.errores.add(error);
            }
        }
        return t;
    }
    
    public ArrayList<ErrorLexico> getErrores() {
        return errores;
    }

    public String getTokenReconocidos() {
        return tokenReconocidos;
    }
    
}
