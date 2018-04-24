package lexical;

import java.util.Map;
import java.util.HashMap;

class SymbolTable {

    private Map<String, TokenType> st;

    public SymbolTable() {
        st = new HashMap<String, TokenType>();
        
        // symbols
        st.put("{", TokenType.OPEN_CUR);
        st.put("}", TokenType.CLOSE_CUR);
        st.put(",", TokenType.COMMA);
        st.put("[", TokenType.OPEN_BRACK);
        st.put("]", TokenType.CLOSE_BRACK);
        st.put("/n", TokenType.LINE);

        // operators
        st.put(":", TokenType.ASSIGN);
    }

    public boolean contains(String token) {
        return st.containsKey(token);
    }

    public TokenType find(String token) {
        return this.contains(token) ?
            st.get(token) : TokenType.INVALID_TOKEN;
    }
}