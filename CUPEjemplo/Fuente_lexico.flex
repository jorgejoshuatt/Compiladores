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
INT={DIGITO}+
%%
"+" {System.out.println("mas"); return new Symbol(sym.MAS); }
"-" {System.out.println("menos"); return new Symbol(sym.MENOS); }
"*" {System.out.println("mult"); return new Symbol(sym.POR); }
"/" {System.out.println("div"); return new Symbol(sym.ENTRE); }
";" {System.out.println("puntocoma"); return new Symbol(sym.PUNTOYCOMA); }
"(" {System.out.println("parAbre"); return new Symbol(sym.LPAREN); }
")" {System.out.println("parCierra"); return new Symbol(sym.RPAREN); }
">" {System.out.println("mayor"); return new Symbol(sym.MAYOR); }
">=" {System.out.println("mayorigual"); return new Symbol(sym.MAYORIGUAL); }
"<" {System.out.println("menor"); return new Symbol(sym.MENOR); }
"<=" {System.out.println("mayorigual"); return new Symbol(sym.MENORIGUAL); }
":=" {System.out.println("asigna"); return new Symbol(sym.ASIG); }
"PRINT" { return new Symbol(sym.PRINT); }
{ID}* {
		Datos s;
		if((s = tabla.buscar(yytext())) == null){
			s = tabla.insertar(yytext());
		}
		System.out.println("Variable: "+yytext());
		return new Symbol(sym.ID, s);
}
{INT}+ {
		System.out.println("Entero");
		return new Symbol(sym.NUMERO, new Integer(yytext())); }
[\t\r\f]  {}
[\n] {}
" " {System.out.println("Simbolo ."+yytext());}
. { System.out.println("Caracter no valido. "+yytext()+"-"); }


