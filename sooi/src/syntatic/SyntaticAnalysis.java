package syntatic;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import lexical.Lexeme;
import lexical.TokenType;
import lexical.LexicalAnalysis;

import interpreter.command.Command;

public class SyntaticAnalysis {

    private LexicalAnalysis lex;
    private Lexeme current;

    public SyntaticAnalysis(LexicalAnalysis lex) throws IOException {
        this.lex = lex;
        this.current = lex.nextToken();
    }

    public Command start() throws IOException {
        return null;
    }

    private void matchToken(TokenType type) throws IOException {
        // System.out.println("Match token: " + current.type + " == " + type + "?");
        if (type == current.type) {
            current = lex.nextToken();
        } else {
            showError();
        }
    }

    private void showError() {
        System.out.printf("%02d: ", lex.getLine());

        switch (current.type) {
            case INVALID_TOKEN:
                System.out.printf("Lexema inválido [%s]\n", current.token);
                break;
            case UNEXPECTED_EOF:
            case END_OF_FILE:
                System.out.printf("Fim de arquivo inesperado\n");
                break;
            default:
                System.out.printf("Lexema não esperado [%s]\n", current.token);
                break;
        }

        System.exit(1);
    }
    
    
    //<obj> ::= '{' <var> | <obj> '}'
    private void procCode() throws IOException {
        /*while (     current.type == TokenType.ARGS
                ||  current.type == TokenType.NAME) {
            procStatement();
        }*/
    }

    //<assign> ::= ':' <number> | <name> | <string> | <array>
    //@TODO: 
    private void procAssign() throws IOException {
        matchToken(TokenType.ASSIGN);
        //procRhs();
    }

    //<var> ::= <name>
    private void procVar() throws IOException {
        procName();
    }
    
    //<array> ::= '[' <number> | <string> | <obj> {',' <number> | <string> | <obj> } ']'
    private void procArray() throws IOException {
        
        //@TODO:
    }
    
    private void procNumber() throws IOException {
        matchToken(TokenType.NUMBER);
    }

    private void procName() throws IOException {
        matchToken(TokenType.NAME);
    }

    private void procString() throws IOException {
        matchToken(TokenType.STRING);
    }
}