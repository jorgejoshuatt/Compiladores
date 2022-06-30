/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.codigo;
import fes.aragon.codigo.AnalizadorLexico;
import fes.aragon.codigo.Sym;
import fes.aragon.codigo.Token;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Josh
 */

public class Inicio {

    private AnalizadorLexico analizador = null;
    private Token token = null;
    private final String[] noTerminales = {"S", "A", "B", "C"};
    private final String[] terminales = {"a", "b", "c", "d", "#"};
    private String lamda = "&";
    private String[][] tabla = {
        {"BA", "BA", "error", "error", "error"},
        {"a", "&", "error", "error", "error"},
        {"error", "dCb", "error", "error", "error"},
        {"error", "error", "c", "&", "error"}};
    private Stack<String> pila = new Stack<>();
    private ArrayList<String> cadena = new ArrayList<>();
    private String topePila = "";
    private String simbolo = "";
    private boolean error = false;

    private void siguienteToken() {
        try {
            token = analizador.yylex();
            if (token == null) {
                token = new Token("EOF", Sym.EOF, 0, 0);
                throw new IOException("Fin");
            }
        } catch (IOException ex) {
            System.out.println("Fin de Archivo");
        }
    }

    public static void main(String[] args) {
        try {
            Inicio app = new Inicio();
            BufferedReader buf;
            buf = new BufferedReader(new FileReader(System.getProperty("user.dir")
                    + "/fuente.txt"));
            app.analizador = new AnalizadorLexico(buf);
            app.siguienteToken();
            while (app.token.getLexema() != Sym.EOF) {
                try {
                    app.ASDP();
                    if (!app.error) {
                        System.out.println("\tCADENA " + (app.token.getLinea()) + " VALIDA\n\n");
                    } else {
                        System.out.println("Cadena " + (app.token.getLinea()) + " incorrecta");
                    }
                    app.error = false;
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

    private void ASDP() throws IOException {
        guardarCadena();
        inicializarPila();
        agregarPila();
    }

    private void guardarCadena() {
        while (token.getLexema() != Sym.PUNTOCOMA) {
            cadena.add(token.getToken());
            siguienteToken();
        }
        cadena.add("#");
    }

    private void inicializarPila() {
        pila.push("#");
        pila.push("S");
        System.out.println("Pila de Símbolos\t\tEntrada");
    }

    private void agregarPila() throws IOException {
        while (!pila.empty()) {
            System.out.println(pila + "\t\t\t" + cadena);
            topePila = pila.lastElement();
            simbolo = cadena.get(0);
            if (!topePila.equals(simbolo)) {
                int f = sacarFila(topePila);
                int c = sacarColumna(simbolo);
                pila.pop();
                if (tabla[f][c].length() >= 2 & tabla[f][c] != "error") {
                    String[] agrega = tabla[f][c].split("");
                    for (int x = 0; x < agrega.length; x++) {
                        pila.push(agrega[x]);
                    }
                } else if (tabla[f][c] == "error") {
                    pila.clear();
                    cadena.removeAll(cadena);
                    System.out.println(cadena);
                    error = true;
                    throw new IOException("\tCADENA " + (token.getLinea()) + " NO VALIDA\n");
                } else if (tabla[f][c].equals(lamda)) {
                } else {
                    pila.push(tabla[f][c]);
                }
            } else if (topePila.equals(simbolo)) {
                pila.pop();
                String consumida = cadena.remove(0);
                System.out.println("\tSimbolo " + consumida + " consumido");
                if (pila.empty()) {
                    error = false;
                }
            }
            agregarPila();
        }
    }

    private int sacarFila(String noTerminal) {
        for (int x = 0; x < noTerminales.length; x++) {
            if (noTerminales[x].equals(noTerminal)) {
                return x;
            }
        }
        return -1;
    }

    private int sacarColumna(String terminal) {
        for (int y = 0; y < terminales.length; y++) {
            if (terminales[y].equals(terminal)) {
                return y;
            }
        }
        return -1;
    }
}
