package fes.aragon.codigo;
%%
%public
%class AnalizadorLexico
%line
%column
%char
%full
%type Token
%{
    private boolean hayToken=false;
    public boolean getHayToken(){
        return this.hayToken;
    }
%}
%init{
    /* código que se ejecuta en el constructor de la clase*/
%init}
%eof{
    /* código que se ejecuta al terminar el archivo*/
    this.hayToken=false;
%eof}
Espacio=" "
PuntoComa=";"
saltoLinea=\n|\r
num_entero=[0-9]+
num_real=["-"|"+"]*+[0-9]+["."[0-9]]*
Comparadores="=="|"!="|">="|"<="|"<"|">"
asignacion=":="
Identificador=[A-Za-zñÑ][_0-9A-Za-zñÑ]*

%%
{num_entero} {
            Token token=new Token(yytext(),Sym.ENTERO,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{num_real} {
            Token token=new Token(yytext(),Sym.REAL,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
"("    {
            Token token=new Token(yytext(),Sym.PARA,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
")"    {
            Token token=new Token(yytext(),Sym.PARC,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
"if"    {
            Token token=new Token(yytext(),Sym.IF,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
"else"    {
            Token token=new Token(yytext(),Sym.ELSE,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
"end"    {
            Token token=new Token(yytext(),Sym.END,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{Comparadores} {
            Token token=new Token(yytext(),Sym.COMPARADORES,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{asignacion} {
            Token token=new Token(yytext(),Sym.ASIGNACION,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{Identificador} {
            Token token=new Token(yytext(),Sym.ID,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{PuntoComa} {
            Token token=new Token(yytext(),Sym.PUNTOCOMA,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
{saltoLinea} {
}
{Espacio} {
}
. {
    String error="Error Léxico: " + yytext() + " linea "+
    (yyline+1)+ " columna "  + (yycolumn+1);
    System.out.println(error);
}