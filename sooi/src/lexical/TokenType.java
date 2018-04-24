package lexical;

public enum TokenType {
    // special tokens
    INVALID_TOKEN,
    UNEXPECTED_EOF,
    END_OF_FILE,
        
    // symbols
    OPEN_CUR,
    CLOSE_CUR,
    DOT_COMMA,
    DOT,
    COMMA,
    ASSIGN,
    OPEN_BRACK,
    CLOSE_BRACK,
    
    // others
    NAME,
    NUMBER,
    STRING,
    
    LINE,

};