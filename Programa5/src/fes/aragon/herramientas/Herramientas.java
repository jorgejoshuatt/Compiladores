/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.herramientas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Josh
 */
public class Herramientas {
    private int longitudLinea=0;
    private int columnaLinea=0;
    private String linea;
    
    public Herramientas(){
    }
    public ArrayList<String> lectura() throws FileNotFoundException, IOException{
        JFileChooser archivo=new JFileChooser(System.getProperty("user.dir"));
        archivo.setMultiSelectionEnabled(false);
        FileNameExtensionFilter filtro=new FileNameExtensionFilter("Archivo de texto", "txt");
        archivo.setFileFilter(filtro);
        ArrayList<String> lineas=new ArrayList<>();
        int seleccion=archivo.showOpenDialog(null);
        if(seleccion==JFileChooser.APPROVE_OPTION){
            File f=archivo.getSelectedFile();
            FileReader fr=new FileReader(f);
            BufferedReader bf=new BufferedReader(fr);
            String cad="";
            while((cad=bf.readLine())!=null){
            lineas.add(cad);
            }
            bf.close();
            fr.close();
            archivo=null;
        }else{
            System.exit(0);
        }
        return lineas;
        
    }
    public void setLinea(String linea){
        this.linea=linea;
        this.longitudLinea=linea.length();
        
    }
    public char siguienteCaracter(){
        char c=' ';
        if(columnaLinea<longitudLinea){
            c=linea.charAt(columnaLinea);
            columnaLinea++;
        }
        return c;
    }

    public int getColumnaLinea() {
        return columnaLinea;
    }

    public void setColumnaLinea(int columnaLinea) {
        this.columnaLinea = columnaLinea;
    }
    
}