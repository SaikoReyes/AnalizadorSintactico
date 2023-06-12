package mx.ipn.escom.compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
    private final String source;
    private int posicionActual;
    private final List<Token> tokens = new ArrayList<>();
    

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        
        palabrasReservadas.put("var", TipoToken.VAR);
        palabrasReservadas.put(")", TipoToken.PARENTESIS_DER);
        palabrasReservadas.put("(", TipoToken.PARENTESIS_IZQ);
        palabrasReservadas.put("}", TipoToken.LLAVE_DER);
        palabrasReservadas.put("{", TipoToken.LLAVE_IZQ);
        palabrasReservadas.put(",", TipoToken.COMA);
        palabrasReservadas.put(".", TipoToken.PUNTO);
        palabrasReservadas.put(";", TipoToken.PUNTO_COMA);
        palabrasReservadas.put("-", TipoToken.GUION_MEDIO);
        palabrasReservadas.put("+", TipoToken.MAS);
        palabrasReservadas.put("*", TipoToken.ASTERISCO);
        palabrasReservadas.put("/", TipoToken.BARRA_INCL);
        palabrasReservadas.put("!", TipoToken.ADMIRACION);
        palabrasReservadas.put("!=", TipoToken.OP_DIFERENTE);
        palabrasReservadas.put("=", TipoToken.IGUAL_QUE);
        palabrasReservadas.put("==", TipoToken.OP_IGUAL_QUE);
        palabrasReservadas.put("<", TipoToken.MENOR_QUE);
        palabrasReservadas.put("<=", TipoToken.OP_MENOR_IGUAL_QUE);
        palabrasReservadas.put(">", TipoToken.MAYOR_QUE);
        palabrasReservadas.put(">=", TipoToken.OP_MAYOR_IGUAL_QUE);
        palabrasReservadas.put("if", TipoToken.IF);
        palabrasReservadas.put("print", TipoToken.PRINT);
        palabrasReservadas.put("for", TipoToken.FOR);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("class", TipoToken.CLASS);
        palabrasReservadas.put("while", TipoToken.WHILE);
        palabrasReservadas.put("fun", TipoToken.FUN);
        
        
        
    }
    
    

    Scanner(String source){
        this.source = source + " ";
        this.posicionActual = 0;
    }

    List<Token> scanTokens() {
        while (posicionActual < source.length()) {
            char caracterActual = source.charAt(posicionActual);

            if (esCaracterIgnorable(caracterActual)) {
                // Ignorar el caracter y avanzar a la siguiente posición
                posicionActual++;
            } else if (esInicioDePalabra(caracterActual)) {
                // Escanear la palabra
                String palabra = escanearPalabra();
                tokens.add(crearToken(palabra));
            } else if (esNumero(caracterActual)) {
                String palabra = escanearPalabra();
                tokens.add(crearToken(palabra));
            } else if (caracterActual == '"') {
                // Escanear cadena entre comillas
                String cadena = escanearCadena();
                tokens.add(new Token(TipoToken.CADENA, cadena, posicionActual));
            } else if(caracterActual == '/'){
                if(source.charAt(posicionActual+1)=='*'){
                    posicionActual++;
                    while(true){
                        posicionActual++;
                        //System.out.println(source.charAt(posicionActual));
                        if(source.charAt(posicionActual)=='*'&&source.charAt(posicionActual+1)=='/'){
                            if(source.charAt(posicionActual+2)=='\0'){
                                posicionActual++;
                                break;
                            }else{
                                posicionActual=posicionActual+2;
                                break;
                            }
                        }
                    }
                }
                
            }else {
                // Tratar el caracter como un carácter no reservado
                tokens.add(crearToken(String.valueOf(caracterActual)));
                posicionActual++;
            }
        }

        return tokens;
    }
    

    private boolean esCaracterIgnorable(char c) {
        return c == ' ' || c == '\t' || c == '\n';
    }

    private boolean esInicioDePalabra(char c) {
        return Character.isLetter(c);
    }
    
    private boolean esNumero(char c){
        return Character.isDigit(c);
    }

    private String escanearPalabra() {
        int inicio = posicionActual;
        while (posicionActual < source.length() && Character.isLetter(source.charAt(posicionActual))) {
            posicionActual++;
        }
        while (posicionActual < source.length() && Character.isDigit(source.charAt(posicionActual))) {
            posicionActual++;
        }
        return source.substring(inicio, posicionActual);
    }
    private String escanearCadena() {
        int inicio = posicionActual + 1; // Ignorar el primer "
        posicionActual++; // Avanzar a la siguiente posición

        while (posicionActual < source.length() && source.charAt(posicionActual) != '"') {
            posicionActual++;
        }

        String cadena = source.substring(inicio, posicionActual);
        posicionActual++; // Avanzar a la posición después del último "

        return cadena;
    }

    private Token crearToken(String lexema) {
        if (palabrasReservadas.containsKey(lexema)) {
            TipoToken tipo = palabrasReservadas.get(lexema);
            return new Token(tipo, lexema, posicionActual);
        } else if (isNumeric(lexema)) {
            return new Token(TipoToken.NUMERO, lexema, posicionActual);
        } else if (lexema.equals("class")) {
            return new Token(TipoToken.CLASS, lexema, posicionActual);
        } else if (lexema.equals("fun")) {
            return new Token(TipoToken.FUN, lexema, posicionActual);
        } else {
            return new Token(TipoToken.IDENTIFICADOR, lexema, posicionActual);
        }
    }
    
    
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            // El valor no es un número entero
        }

        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            // El valor no es un número decimal
        }

        return false;
    }
}


