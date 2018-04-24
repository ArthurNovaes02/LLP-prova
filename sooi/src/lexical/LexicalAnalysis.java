package lexical;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.PushbackInputStream;

import java.util.*;

public class LexicalAnalysis implements AutoCloseable {

    private int line;
    private SymbolTable st;
    private PushbackInputStream input;

    public LexicalAnalysis(String filename) throws LexicalException {
        try {
            input = new PushbackInputStream(new FileInputStream(filename));
        } catch (Exception e) {
            throw new LexicalException("Unable to open file");
        }

        st = new SymbolTable();
        line = 1;
    }

    public void close() throws IOException {
        input.close();
    }

    public int getLine() {
        return this.line;
    }

    public Lexeme nextToken() throws IOException {
        // Decide o que vai fazer em cada estado
        
        int estado = 1;
        
        Lexeme lex = new Lexeme("", TokenType.END_OF_FILE);
        
        // fica aqui durante um lexema inteiro
        while (estado != 5 && estado != 6){
            int c = input.read(); // ler cada caracter do código
            System.out.println("[" + estado + ", \'" + ((char) c) + "\']");
            
            switch(estado){
                case 1:
                    // comandos que não fazem nada
                    if (c == ' ' || c == '\t' || c == '\r'){
                        estado = 1; // permanece no mesmo estado
                    }
                    
                    // \n linha de baixo
                    else if (c == '\n'){
                        line ++;
                        lex.type = TokenType.LINE;
                        estado = 5;
                    }
                    
                    // numeros
                    else if (Character.isDigit(c)){
                        lex.type = TokenType.NUMBER;
                        lex.token += (char)c;
                        estado = 2;
                    }
                    
                    // letras
                    else if (Character.isLetter(c)){
                        lex.type = TokenType.NAME;
                        lex.token += (char) c; // concatena
                        estado = 3; 
                    }
                    
                    // aspas - string
                    else if (c == '\"'){
                        // não coloca no token
                        lex.type = TokenType.STRING;
                        estado = 4;
                    }
                    
                    // END OF FILE
                    else if (c == -1){
                        lex.type = TokenType.END_OF_FILE;
                        estado = 7;
                    }
                    
                    // atribuição
                    else if (c == ':'){
                        lex.type = TokenType.ASSIGN;
                        lex.token += (char) c; // concatena
                        estado = 5;
                    }

                    // open chave
                    else if (c == '{'){
                        lex.token += (char) c;
                        lex.type = TokenType.OPEN_CUR;
                        estado = 5;
                    }
                    
                    // close chave
                    else if (c == '}'){
                        lex.token += (char) c;
                        lex.type = TokenType.CLOSE_CUR;
                        estado = 5;
                    }
                    
                    // open colchete
                    else if (c == '['){
                        lex.token += (char) c;
                        lex.type = TokenType.OPEN_BRACK;
                        estado = 5;
                    }
                    
                    // close colchete
                    else if (c == ']'){
                        lex.token += (char) c;
                        lex.type = TokenType.CLOSE_BRACK;
                        estado = 5;
                    }
                    
                    // virgula
                    else if (c == ','){
                        lex.token += (char) c;
                        lex.type = TokenType.COMMA;
                        estado = 5;
                    }

                    else {
                        lex.token += (char)c;
                        lex.type = TokenType.INVALID_TOKEN;
                    }
                    break;

                // numero
                case 2:
                    if (Character.isDigit(c)){
                        lex.token += (char) c;
                        estado = 2;
                    }
                    else if(c != -1) input.unread(c);
                        estado = 6;
                    break;
                
                // letra
                case 3:
                    if (!Character.isDigit(c) && !Character.isLetter(c)){
                        input.unread(c);
                        estado = 5;
                    }
                    else{
                        lex.token += (char)c;
                        estado = 3;
                    }
                    break;
                    
                // aspas
                case 4:
                    if (c == '\"'){
                        estado = 6;
                    }
                    else{
                        lex.token += (char)c;
                        estado = 4;
                    }
                    break;
                default:
                    break;
            }
        }
        if (estado == 5) {
            if (st.contains(lex.token)) {
                lex.type = st.find(lex.token);
            } 
            else {
                lex.type = TokenType.NAME;
            }
        }
        return lex;
    }
}