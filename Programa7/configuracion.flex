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
NUMERO=[0-9]+(.[0-9]+)?
%%
"or"    {
            Token token=new Token(yytext(),Sym.OR,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
"and"    {
            Token token=new Token(yytext(),Sym.AND,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
"not"    {
            Token token=new Token(yytext(),Sym.NOT,yyline+1,yycolumn+1);
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
"true"    {
            Token token=new Token(yytext(),Sym.TRUE,yyline+1,yycolumn+1);
            this.hayToken=true;
            return token;
}
"false"    {
            Token token=new Token(yytext(),Sym.FALSE,yyline+1,yycolumn+1);
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