package mx.ipn.escom.compiladores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Principal {

    static boolean existenErrores = false;

    public static void main(String[] args) throws IOException {
        ejecutarPrompt();
    }

    private static void ejecutarPrompt() throws IOException{
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        String conjunto="";
        while (true) {
            System.out.print(">>");
            String linea = reader.readLine();
            
            if (linea == null||linea.equals("salir")){
                break; // Presionar Ctrl + D
            }
            
            
            existenErrores = false;
            ejecutar(linea);
        }
        
    }

    private static void ejecutar(String source){
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        for(Token token : tokens){
            System.out.println(token);
        }
        try{
        Parser parser = new Parser(tokens);
        parser.parse();
        if(parser.esValida){
            System.out.print("\n------->Cadena valida\n\n");
        }
        }catch(IndexOutOfBoundsException e){
            System.out.print("\n------->Cadena no Valida\n\n");
        }catch(StackOverflowError e){
            System.out.print("\n------->Cadena no Valida\n\n");
        }
        
        
    }

    static void error(int linea, String mensaje){
        reportar(linea, "", mensaje);
    }

    private static void reportar(int linea, String donde, String mensaje){
        System.err.println(
                "[linea " + linea + "] Error " + donde + ": " + mensaje
        );
        existenErrores = true;
    }

}