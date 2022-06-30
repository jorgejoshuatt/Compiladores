package fes.aragon.codigo;
%%
%public
%class AnalizadorLexico
%line
%char
%column
%full
%type Token
%{
    private boolean hayToken=false;
    public boolean getHayToken(){
        return this.hayToken;
    }
%}
%init{
    /*código que se ejecuta en el constructor de la clase*/
%init}
%eof{
    /*código que se ejecuta al terminar de leer el archivo*/
this.hayToken=false;
%eof}
Espacio=" "
PuntoComa=";"
saltoLinea=\n|\r
ENTERO=[0-9]+
REAL=["-"]*+[0-9]+["."[0-9]]*
ID=[A-Za-z][_0-9A-Za-z]*
Parentesis=["("|")"]
Coma=","
Operadores= "+"|"-"|"*"|"/"
Comparadores="="|"!="|">="|"<="|"<"|">"
NumDecimal=[0-9]+[","|"."][0-9]*
%%
{ENTERO} {
            Token toke=new Token(yytext(),Sym.ENTERO,yyline+1,yycolumn+1);
            this.hayToken=true;
            return toke;
}
{ID} {
            String Reservadas0= new String("inicio");
            String Reservadas1= new String("fin");
            String Reservadas2= new String("mientras");
            String Reservadas3= new String("entero");
            String Reservadas4= new String("real");
            String Reservadas5= new String("si");
            String Reservadas6= new String("sino");
            String Reservadas7= new String("finmientras");
            if(yytext().equals(Reservadas0)){
            System.out.println(yytext()+": Esta palabra es una Palabra reservada.");
            
            }else if(yytext().equals(Reservadas1)){
            System.out.println(yytext()+": Esta palabra es una Palabra reservada.");
           
            }else if(yytext().equals(Reservadas2)){
            System.out.println(yytext()+": Esta palabra es una Palabra reservada.");
           
            }else if(yytext().equals(Reservadas3)){
            System.out.println(yytext()+": Esta palabra es una Palabra reservada.");
           
            }
            else if(yytext().equals(Reservadas4)){
            System.out.println(yytext()+": Esta palabra es una Palabra reservada.");
           
            }
            else if(yytext().equals(Reservadas5)){
            System.out.println(yytext()+": Esta palabra es una Palabra reservada.");
           
            }
            else if(yytext().equals(Reservadas6)){
            System.out.println(yytext()+": Esta palabra es una Palabra reservada.");
           
            }
            else if(yytext().equals(Reservadas7)){
            System.out.println(yytext()+": Esta palabra es una Palabra reservada.");
           
            }else{
            Token toke=new Token(yytext(),Sym.PUNTOCOMA,yyline+1,yycolumn+1);
            this.hayToken=true;
            return toke;

}

}

{PuntoComa} {
            Token toke=new Token(yytext(),Sym.PUNTOCOMA,yyline+1,yycolumn+1);
            this.hayToken=true;
            return toke;
}
{REAL} {
            Token toke=new Token(yytext(),Sym.REAL,yyline+1,yycolumn);
            this.hayToken=true;
            return toke;
}
{Parentesis} {
            Token toke=new Token(yytext(),Sym.PARENTESIS,yyline+1,yycolumn+1);
            this.hayToken=true;
            return toke;
}
{Coma} {
            Token toke=new Token(yytext(),Sym.COMA,yyline+1,yycolumn+1);
            this.hayToken=true;
            return toke;
}
{Operadores} {
            Token toke = new Token(yytext(), Sym.OPERADORES, yyline+1, yycolumn+1);
            this.hayToken = true;
            return toke;
}
{Comparadores} {
            Token toke=new Token(yytext(),Sym.COMPARADORES,yyline+1,yycolumn+1);
            this.hayToken=true;
            return toke;
}
{NumDecimal} {
            Token toke=new Token(yytext(),Sym.NUMDECIMAL,yyline+1,yycolumn+1);
            this.hayToken=true;
            return toke;
}
{saltoLinea} {
           
}
{Espacio} {
           
}
. {
    String error="Error Léxico: "+yytext() + " en la linea" +
    (yyline+1) + " Columna " +(yycolumn+1);
    System.out.println(error);
}
