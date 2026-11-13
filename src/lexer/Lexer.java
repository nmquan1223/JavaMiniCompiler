package lexer;

import java.util.ArrayList;

public class Lexer {
    private String content;

    public Lexer(String content) {
        this.content = content;
    }

    //lexer.Token recognizer
    public ArrayList<Token> tokenize() {
        int i = 0;
        ArrayList<Token> tokens = new ArrayList<>();
        while (i < content.length()) {
            // Check current char
            char c = content.charAt(i); //Operators token
            switch (c) {
                case '+' : tokens.add(new Token(TokenType.PLUS, "+")); i++; continue;
                case '-' : tokens.add(new Token(TokenType.MINUS, "-")); i++; continue;
                case '*' : tokens.add(new Token(TokenType.MULTIPLY, "*")); i++; continue;
                case '/' : tokens.add(new Token(TokenType.DIVIDE, "/")); i++; continue;
                case '=' : tokens.add(new Token(TokenType.ASSIGN, "=")); i++; continue;
                case '(' : tokens.add(new Token(TokenType.LPAREN, "(")); i++; continue;
                case ')' : tokens.add(new Token(TokenType.RPAREN, ")")); i++; continue;
                case ';' : tokens.add(new Token(TokenType.SEMICOLON, ";")); i++; continue;
                case '{' : tokens.add(new Token(TokenType.LBRACE, "{")); i++; continue;
                case '}' : tokens.add(new Token(TokenType.RBRACE, "}")); i++; continue;
            }
            if (Character.isDigit(content.charAt(i))) { //Number token
                String number = "";
                while (i < content.length() && Character.isDigit(content.charAt(i))) {
                    number += content.charAt(i);
                    i++;
                }
                tokens.add(new Token(TokenType.NUMBER, number));
            }
            else if (Character.isLetter(content.charAt(i))) { //Identifier token
                String identifier = "";
                while (i < content.length() && Character.isLetterOrDigit(content.charAt(i))) {
                    identifier += content.charAt(i);
                    i++;
                }
                switch (identifier) {
                    case "print" : tokens.add(new Token(TokenType.PRINT, "print")); continue;
                    case "if" : tokens.add(new Token(TokenType.IF, "if")); continue;
                    case "while" : tokens.add(new Token(TokenType.WHILE, "while")); continue;
                }
                tokens.add(new Token(TokenType.IDENTIFIER, identifier));
            }
            else if (Character.isWhitespace(c)) {
                i++;
            }
            else {
                throw new IllegalArgumentException("Invalid character: " + c); //Invalid character handling
            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}
