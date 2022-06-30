/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.test;

import fes.aragon.herramientas.ErrorLexico;
import fes.aragon.modulos.lexico.Lexico;
import fes.aragon.modulos.lexico.Token;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Josh
 */
public class TestLexico {
    public static void main(String[] args) {
        try {
            Lexico lexico=new Lexico();
            Token t=null;
            do{
                t=lexico.siguienteToken();
            }while(t!=null);
            System.out.println(lexico.getTokenReconocidos());
        } catch (ErrorLexico ex) {
            ex.printStackTrace();
        }
    }
}
