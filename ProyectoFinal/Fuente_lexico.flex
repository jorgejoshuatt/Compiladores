package fes.aragon.compilador;
import java_cup.runtime.Symbol;
import java.io.Reader;
%%
%{
	private TablaSimbolos tabla;
	public Lexico(java.io.InputStream in, TablaSimbolos t){
		this(in);
		this.tabla = t; 
	}	
	public int getYyline() {
                return yyline;
        }

        public int getYy_currentPos() {
            return yy_currentPos-1;
        }
%}
%class Lexico
%public
%char
%line
%cup
%ignorecase
%type java_cup.runtime.Symbol
//%implements java_cup.runtime.Scanner
%eofval{
 return new Symbol(sym.EOF,new String("Fin del archivo"));
//return null;
%eofval}
LETRA= [a-zA-Z]
DIGITO=[0-9]
ID={LETRA}({LETRA}|{DIGITO})*
%%
"or" {System.out.println("or"); return new Symbol(sym.OR); }
"and" {System.out.println("and"); return new Symbol(sym.AND); }
"not" {System.out.println("not"); return new Symbol(sym.NOT); }
"true" {System.out.println("true"); return new Symbol(sym.TRUE); }
"false" {System.out.println("false"); return new Symbol(sym.FALSE); }
";" {System.out.println("puntocoma"); return new Symbol(sym.PUNTOYCOMA); }
"(" {System.out.println("parAbre"); return new Symbol(sym.LPAREN); }
")" {System.out.println("parCierra"); return new Symbol(sym.RPAREN); }
":=" {System.out.println("asigna"); return new Symbol(sym.ASIG); }
{ID}* {
		Datos s;
		if((s = tabla.buscar(yytext())) == null){
			s = tabla.insertar(yytext());
		}
		System.out.println("Variable: "+yytext());
		return new Symbol(sym.ID, s);
}
[\t\r\f ]  {}
[\n] {}
. { System.out.println("Caracter no valido. "+yytext()+"-"); }