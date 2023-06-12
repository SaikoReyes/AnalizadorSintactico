package mx.ipn.escom.compiladores;

public enum TipoToken {
    IDENTIFICADOR, CADENA, NUMERO,

    // Palabras reservadas
    VAR, WHILE, IF, CLASS, RETURN, FOR, PRINT, FUN,

    // Caracteres
    PARENTESIS_DER, PARENTESIS_IZQ, LLAVE_DER, LLAVE_IZQ, COMA, PUNTO, PUNTO_COMA, GUION_MEDIO, MAS, ASTERISCO,
    BARRA_INCL, ADMIRACION, OP_DIFERENTE, IGUAL_QUE, OP_IGUAL_QUE, MENOR_QUE, OP_MENOR_IGUAL_QUE, MAYOR_QUE, OP_MAYOR_IGUAL_QUE,OR,AND,SINO,OP_MENOR_QUE,

    // Final de cadena
    EOF
}
/*
Signos o símbolos del lenguaje:
(   PARENTESIS_DERECHO
)   PARENTESIS_IZQUIERDO
{   LLAVE_DERECHA
}   LLAVE_IZQUIERDA
,   COMA
.   PUNTO
;   PUNTO_COMA
-   GUION_MEDIO
+   MAS
*   ASTERISCO
/   BARRA_INCLINADA
!   ADMIRACION
!=  OP_DIFERENTE
=   IGUAL_QUE
==  OP_IGUAL_QUE
<   MENOR_QUE
<=  OP_MENOR_IGUAL_QUE
>   MAYOR_QUE
>=  OP_MAYOR_IGUAL_QUE
// -> comentarios (no se genera token)
/* ... * / -> comentarios (no se genera token)
Identificador,  IDENTIFICADOR
Cadena          CADENA
Numero          NUMERO
Cada palabra reservada tiene su nombre de token
Y, CLASE, ADEMAS, FALSO, PARA, FUN, SI, NULO, O, IMPRIMIR, RETORNAR, SUPER, ESTE, VERDADERO, VAR, MIENTRAS,
 */
