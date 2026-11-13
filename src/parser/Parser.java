package parser;

import ast.*;
import lexer.Token;
import lexer.TokenType;

import java.util.ArrayList;

public class Parser implements Stmt {
    private ArrayList<Token> tokens;
    private int current;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.current = 0;
    }
    public Token peek() { //Access to token at index "current"
        return tokens.get(current);
    }
    public Token advance() { //Access to token at index "current" and add 1 to "current"
        Token token = peek();
        if (current < tokens.size()-1) {
            current++;
        }
        return token;
    }
    public boolean match(TokenType expected) { //Check if getting right lexer.TokenType
        if (peek().getType() != expected) {
            return false;
        }
        else {
            advance();
            return true;
        }
    }
    public Token consume(TokenType expected) { //Check if there is any missing tokens
        if (peek().getType() == expected) {
            return advance();
        }
        else {
            throw new IllegalArgumentException("Expected " + expected);
        }
    }
    public Expr parsePrimary() { //NUMBER
        if (match(TokenType.LPAREN)) {
            Expr term = parseTerm();
            consume(TokenType.RPAREN);
            return term;
        }
        else if (peek().getType() == TokenType.IDENTIFIER) {
            String identifier = "";
            identifier += advance().getValue();
            return new VariableNode(identifier);
        }
        else {
            return new NumberNode(Integer.parseInt(consume(TokenType.NUMBER).getValue()));
        }
    }
    public Expr parseFactor() { //Handle MULTIPLY and DIVIDE
        Expr left = parsePrimary();
        while (peek().getType()==TokenType.MULTIPLY || peek().getType()==TokenType.DIVIDE) {
            String operator = advance().getValue();
            Expr right = parsePrimary();
            left = new BinaryOpNode(left, right, operator);
        }
        return left;
    }
    public Expr parseTerm() { //Handle PLUS and MINUS
        Expr left = parseFactor();
        while (peek().getType()==TokenType.PLUS || peek().getType()==TokenType.MINUS) {
            String operator = advance().getValue();
            Expr right = parseFactor();
            left = new BinaryOpNode(left, right, operator);
        }
        return left;
    }
    public Expr parse() { //Check EOF
        Expr ast = parseTerm();
        if (peek().getType() != TokenType.EOF) {
            throw new IllegalArgumentException("Unexpected " + peek().getType());
        }
        else {
            return ast;
        }
    }
    public Stmt parseAssignment() { //Handle ASSIGNMENT
        String identifier = consume(TokenType.IDENTIFIER).getValue();
        consume(TokenType.ASSIGN);
        Expr right = parseTerm();
        consume(TokenType.SEMICOLON);
        return new AssignmentNode(identifier, right);
    }
    public Stmt parsePrint() { //Handle PRINT
        consume(TokenType.PRINT);
        Expr printNode = parseTerm();
        consume(TokenType.SEMICOLON);
        return new PrintNode(printNode);
    }
    public Stmt parseIf() { //Handle IF
        consume(TokenType.IF);
        consume(TokenType.LPAREN);
        Expr condition = parseTerm();
        consume(TokenType.RPAREN);
        consume(TokenType.LBRACE);
        ArrayList<Stmt> body = new ArrayList<>();
        while (peek().getType() != TokenType.RBRACE) {
            if (peek().getType() == TokenType.IDENTIFIER) {
                body.add(parseAssignment());
            }
            else if (peek().getType() == TokenType.PRINT) {
                body.add(parsePrint());
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        consume(TokenType.RBRACE);
        return new IfNode(condition, body);
    }
    public Stmt parseWhile() { //Handle WHILE
        consume(TokenType.WHILE);
        consume(TokenType.LPAREN);
        Expr condition = parseTerm();
        consume(TokenType.RPAREN);
        consume(TokenType.LBRACE);
        ArrayList<Stmt> body = new ArrayList<>();
        while (peek().getType() != TokenType.RBRACE) {
            if (peek().getType() == TokenType.IDENTIFIER) {
                body.add(parseAssignment());
            }
            else if (peek().getType() == TokenType.PRINT) {
                body.add(parsePrint());
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        consume(TokenType.RBRACE);
        return new WhileNode(condition, body);
    }
    public ArrayList<Stmt> parseProgram() { //Handle multiple ASSIGNMENT
        ArrayList<Stmt> stmt = new ArrayList<Stmt>();
        while (peek().getType() != TokenType.EOF) {
            if (peek().getType() == TokenType.IDENTIFIER) {
                stmt.add(parseAssignment());
            }
            else if (peek().getType() == TokenType.PRINT) {
                stmt.add(parsePrint());
            }
            else if (peek().getType() == TokenType.IF) {
                stmt.add(parseIf());
            }
            else if (peek().getType() == TokenType.WHILE) {
                stmt.add(parseWhile());
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        return stmt;
    }
}
