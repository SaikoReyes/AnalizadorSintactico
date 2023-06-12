package mx.ipn.escom.compiladores;

import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int currentPosition=0;
    boolean esValida=true;
    

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        
    }
    
    
    public void parse() {
        PROGRAM();
        
    }

    private void PROGRAM() {
        DECLARATION();
    }

        private void DECLARATION() {
            
        if (comparar(TipoToken.CLASS)) {
            CLASS_DECL();
            DECLARATION();
        }else if (comparar(TipoToken.FUN)) {
            FUN_DECL();
            DECLARATION();
        } else if (comparar(TipoToken.VAR)) {
            VAR_DECL();
            DECLARATION();
        } 
        else {
            
            STATEMENT();         
        }
    }

    private void CLASS_DECL() {
        match(TipoToken.CLASS);
        match(TipoToken.IDENTIFICADOR);
        CLASS_INHER();
        match(TipoToken.LLAVE_IZQ);
        FUNCTIONS();
        match(TipoToken.LLAVE_DER);
    }

    private void CLASS_INHER() {
        if (comparar(TipoToken.MENOR_QUE)) {
            match(TipoToken.MENOR_QUE);
            match(TipoToken.IDENTIFICADOR);
        }
    }

    private void FUN_DECL() {
        match(TipoToken.FUN);
        FUNCTION();
    }

    private void VAR_DECL() {
        match(TipoToken.VAR);
        match(TipoToken.IDENTIFICADOR);
        VAR_INIT();
        match(TipoToken.PUNTO_COMA);
    }

    private void VAR_INIT() {
    if (comparar(TipoToken.IGUAL_QUE)) {
        match(TipoToken.IGUAL_QUE);
        EXPRESSION();
    }
    
}


    private void STATEMENT() {
        //System.out.println("Ayuda");
        if(comparar(TipoToken.PARENTESIS_IZQ)){
            
            EXPR_STMT();            
        }
        else if (comparar(TipoToken.FOR)) {
            
            FOR_STMT();
        } else if (comparar(TipoToken.IF)) {
            
            IF_STMT();
        } else if (comparar(TipoToken.PRINT)) {
            
            
            PRINT_STMT();
        } else if (comparar(TipoToken.RETURN)) {
            
            RETURN_STMT();
        }  else if (comparar(TipoToken.LLAVE_IZQ)) {
            BLOCK();
        }
    }

    private void EXPR_STMT() {
        EXPRESSION();
        match(TipoToken.PUNTO_COMA);
    }

    private void FOR_STMT() {
        match(TipoToken.FOR);
        match(TipoToken.PARENTESIS_IZQ);
        FOR_STMT_1();
        FOR_STMT_2();
        FOR_STMT_3();
        match(TipoToken.PARENTESIS_DER);
        STATEMENT();
    }

    private void FOR_STMT_1() {
        if (comparar(TipoToken.VAR)) {
            VAR_DECL();
        } else {
            EXPR_STMT();
        }
        
        System.out.println(tokens.get(currentPosition));
        
    }

    private void FOR_STMT_2() {
    
        if(!comparar(TipoToken.PUNTO_COMA)){
            EXPRESSION();
            match(TipoToken.PUNTO_COMA);
        }
    }

   

 private void FOR_STMT_3() {
     
        if (!comparar(TipoToken.PARENTESIS_DER)) {
            EXPRESSION();
        }
    }

    private void IF_STMT() {
        match(TipoToken.IF);
        match(TipoToken.PARENTESIS_IZQ);
        
        EXPRESSION();
        match(TipoToken.PARENTESIS_DER);
        STATEMENT();
        ELSE_STATEMENT();
    }

    private void ELSE_STATEMENT() {
        if (comparar(TipoToken.SINO)) {
            match(TipoToken.SINO);
            STATEMENT();
        }
    }

    private void PRINT_STMT() {
        match(TipoToken.PRINT);
        EXPRESSION();
        match(TipoToken.PUNTO_COMA);
    }

    private void RETURN_STMT() {
        match(TipoToken.RETURN);
        RETURN_EXP_OPC();
        match(TipoToken.PUNTO_COMA);
    }

    private void RETURN_EXP_OPC() {
        if (!comparar(TipoToken.PUNTO_COMA)) {
            EXPRESSION();
        }
    }


    private void BLOCK() {
        match(TipoToken.LLAVE_IZQ);
         
        BLOCK_DECL();
        match(TipoToken.LLAVE_DER);
    }

    private void BLOCK_DECL() {

        
    if (!comparar(TipoToken.LLAVE_DER)) {
        DECLARATION();
        BLOCK_DECL();
    
    }
    }

    private void EXPRESSION() {
        ASSIGNMENT();
    }

    private void ASSIGNMENT() {
        
        CALL_OPC();
        
        
        if(comparar(TipoToken.IDENTIFICADOR)){
            
            match(TipoToken.IDENTIFICADOR);
            match(TipoToken.IGUAL_QUE);
            
            ASSIGNMENT();
        }
        
        
        LOGIC_OR();
        
    }

    

    private void LOGIC_OR() {
        
        LOGIC_AND();
        LOGIC_OR_2();
    }

    private void LOGIC_OR_2() {
        if (comparar(TipoToken.OR)) {
            match(TipoToken.OR);
            LOGIC_AND();
            LOGIC_OR_2();
        }
    }

    private void LOGIC_AND() {
        EQUALITY();
        LOGIC_AND_2();
    }

    private void LOGIC_AND_2() {
        if (comparar(TipoToken.AND)) {
            match(TipoToken.AND);
            EQUALITY();
            LOGIC_AND_2();
        }
    }

    private void EQUALITY() {
        COMPARISON();
        EQUALITY_2();
    }

    private void EQUALITY_2() {
        if (comparar(TipoToken.OP_DIFERENTE)) {
            match(TipoToken.OP_DIFERENTE);
            COMPARISON();
            EQUALITY_2();
        } else if (comparar(TipoToken.IGUAL_QUE)) {
            match(TipoToken.IGUAL_QUE);
            COMPARISON();
            EQUALITY_2();
        }
    }

    private void COMPARISON() {
        
        TERM();
        COMPARISON_2();
    }

    private void COMPARISON_2() {
        
        if (comparar(TipoToken.MAYOR_QUE)) {
            if(tokens.get(currentPosition+1).lexema.equals("=")){
                match(TipoToken.MAYOR_QUE);
                match(TipoToken.IGUAL_QUE);
                TERM();
                COMPARISON_2();
                
            }else{
                match(TipoToken.MAYOR_QUE);
                TERM();
                COMPARISON_2();
            }
            
        } 
        else if (comparar(TipoToken.MENOR_QUE)) {
            
            if(tokens.get(currentPosition+1).lexema.equals("=")){
                match(TipoToken.MENOR_QUE);
                match(TipoToken.IGUAL_QUE);
                TERM();
                COMPARISON_2();
                
            }else{
                match(TipoToken.MENOR_QUE);
                TERM();
                COMPARISON_2();
            }
            
            
            
        }
    }

    private void TERM() {
        FACTOR();
        TERM_2();
    }

    private void TERM_2() {
        if (comparar(TipoToken.GUION_MEDIO)) {
            match(TipoToken.GUION_MEDIO);
            FACTOR();
            TERM_2();
        } else if (comparar(TipoToken.MAS)) {
            match(TipoToken.MAS);
            FACTOR();
            TERM_2();
        }
    }

    private void FACTOR() {
        UNARY();
        FACTOR_2();
    }

    private void FACTOR_2() {
        if (comparar(TipoToken.BARRA_INCL)) {
            match(TipoToken.BARRA_INCL);
            UNARY();
            FACTOR_2();
        } else if (comparar(TipoToken.ASTERISCO)) {
            match(TipoToken.ASTERISCO);
            UNARY();
            FACTOR_2();
        }
    }

    private void UNARY() {
        if (comparar(TipoToken.ADMIRACION)) {
            match(TipoToken.ADMIRACION);
            UNARY();
        } else if (comparar(TipoToken.GUION_MEDIO)) {
            match(TipoToken.GUION_MEDIO);
            UNARY();
        } else {
            CALL();
        }
    }

    private void CALL() {
        PRIMARY();
        CALL_2();
    }

    private void CALL_2() {
        if (comparar(TipoToken.PARENTESIS_IZQ)) {
            match(TipoToken.PARENTESIS_IZQ);
            ARGUMENTS_OPC();
            match(TipoToken.PARENTESIS_DER);
            CALL_2();
        } else if (comparar(TipoToken.PUNTO)) {
            match(TipoToken.PUNTO);
            match(TipoToken.IDENTIFICADOR);
            CALL_2();
        }
    }
    
    private void CALL_OPC(){
        CALL();
        if(comparar(TipoToken.PUNTO)){
            match(TipoToken.PUNTO);
        }
    }

    private void ARGUMENTS_OPC() {
        if (!comparar(TipoToken.PARENTESIS_DER)) {
            ARGUMENTS();
        }
    }
    private void PRIMARY(){
        if(comparar(TipoToken.TRUE)){
            match(TipoToken.TRUE);
        }
        else if(comparar(TipoToken.FALSE)){
            match(TipoToken.FALSE);
        }
        else if(comparar(TipoToken.NULL)){
            match(TipoToken.NULL);
        }
        else if(comparar(TipoToken.NUMERO)){
            match(TipoToken.NUMERO);
        }
        else if(comparar(TipoToken.STRING)){
            match(TipoToken.STRING);
        }
        else if(comparar(TipoToken.IDENTIFICADOR)){
            match(TipoToken.IDENTIFICADOR);
        }
        else if(comparar(TipoToken.PARENTESIS_IZQ)){
            match(TipoToken.PARENTESIS_IZQ);
            EXPRESSION();
            match(TipoToken.PARENTESIS_DER);
        }
        else if(comparar(TipoToken.SUPER)){
            match(TipoToken.SUPER);
            match(TipoToken.PUNTO);
            match(TipoToken.IDENTIFICADOR);
        }
    }

    private void ARGUMENTS() {
        EXPRESSION();
        ARGUMENTS_2();
    }

    private void ARGUMENTS_2() {
        if (comparar(TipoToken.COMA)) {
            match(TipoToken.COMA);
            EXPRESSION();
            ARGUMENTS_2();
        }
    }

    private void FUNCTION() {
        
        match(TipoToken.IDENTIFICADOR);
        
        match(TipoToken.PARENTESIS_IZQ);
        
        PARAMETERS_OPC();
        match(TipoToken.PARENTESIS_DER);
        //System.out.println(tokens.get(currentPosition)+"Holaaa");
        BLOCK();
    }

    private void FUNCTIONS() {
        if (!comparar(TipoToken.LLAVE_DER)) {
            FUNCTION();
            FUNCTIONS();
        }
    }

    private void PARAMETERS_OPC() {
        if (!comparar(TipoToken.PARENTESIS_DER)) {
            PARAMETERS();
        }
    }

    private void PARAMETERS() {
        match(TipoToken.IDENTIFICADOR);
        
        PARAMETERS_2();
    }

    private void PARAMETERS_2() {
        if (comparar(TipoToken.COMA)) {
            match(TipoToken.COMA);
            
            match(TipoToken.IDENTIFICADOR);
            PARAMETERS_2();
            
        }
    }

    public void match(TipoToken tipoToken) {
        Token token = tokens.get(currentPosition);
        if (token.tipo == tipoToken) {
            currentPosition++;
            
        
        } else {
            esValida=false;
            System.out.println("Error: Se esperaba " + tipoToken +
                    " pero se encontró " + token.tipo + " en la posición " + currentPosition);
        }
    }

    private boolean comparar(TipoToken tipoToken) {
        if (currentPosition >= tokens.size()) {
            return false;
        }
        Token token = tokens.get(currentPosition);
        return token.tipo == tipoToken;
    }



}

