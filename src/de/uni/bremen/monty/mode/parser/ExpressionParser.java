package de.uni.bremen.monty.mode.parser;

import com.intellij.lang.PsiBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionParser extends BaseParser {

    public ExpressionParser(PsiBuilder builder) {
        super(builder);
    }

    boolean parseNotLeftRecursive() {
        if (parseFunctionCall()) {
            return true;
        }
        if (parsePrimary()) {
            return true;
        }

        if (is(OPERATOR, "+") || is(OPERATOR, "-") || is(OPERATOR, "not")) {
            PsiBuilder.Marker mark = mark();
            advanceLexer();
            if (parseNotLeftRecursive()) {
                mark.done(EXPRESSION);
            } else {
                markMissing(EXPRESSION);
                mark.done(EXPRESSION);
            }
            return true;
        }
        return false;
    }

    public boolean parseExpression() {
        return parseExprNode(precedence.size());
    }

    protected boolean parseFunctionCall() {
        PsiBuilder.Marker mark = mark();
        if (parseType()) {
            if (finishFunctionCall(mark)) {
                return true;
            }
        } else if (is(IDENTIFIER)) {
            advanceLexer();
            if (finishFunctionCall(mark)) {
                return true;
            }
        } else if (is(KEYWORD, "initializer")) {
            advanceLexer();
            if (parseExpressionList()) {
                //TODO
                mark.done(FUNCTION_CALL);
                return true;
            } else {
                markMissingKeyword("(");
                mark.done(FUNCTION_CALL);
                return true;
            }
        } else {
            mark.drop();
        }
        return false;
    }

    private boolean finishFunctionCall(PsiBuilder.Marker mark) {
        if (parseExpressionList()) {
            //TODO
            mark.done(FUNCTION_CALL);
            return true;
        } else {
            mark.rollbackTo();
        }
        return false;
    }

    private boolean parseExprNode(int until) {

        PsiBuilder.Marker mark = builder.mark();
        boolean terminal = parseNotLeftRecursive();
        if (terminal) {
            Integer index = precedence.get(getTokenText());
            while (is(OPERATOR, DOT) && index != null && index < until) {
                if (is(OPERATOR, "as") || is(OPERATOR, "is")) {
                    advanceLexer();
                    if (is(CLASS_IDENTIFIER)) {
                        advanceLexer();
                    } else {
                        markMissing(CLASS_IDENTIFIER);
                    }
                    PsiBuilder.Marker precede = mark.precede();
                    mark.done(MontyParseStep.BINARY_EXPRESSION);
                    mark = precede;
                    index = precedence.get(getTokenText());
                } else {
                    advanceLexer();
                    if (!parseExprNode(index)) {
                        markMissing(EXPRESSION);
                    }
                    PsiBuilder.Marker precede = mark.precede();
                    mark.done(MontyParseStep.BINARY_EXPRESSION);
                    mark = precede;
                    index = precedence.get(getTokenText());
                }
            }
            PsiBuilder.Marker precede = mark.precede();
            parseIf(mark);
            mark = precede;
        }
        mark.drop();
        return terminal;
    }

    boolean parseIf(PsiBuilder.Marker mark) {
        if (is(MontyParseStep.KEYWORD, "if")) {
            advanceLexer();
            if (!parseExpression()) {
                markMissing(EXPRESSION);
            }
            if (is(MontyParseStep.KEYWORD, "else")) {
                advanceLexer();
                if (!parseExpression()) {
                    markMissing(EXPRESSION);
                }
            } else {
                markMissingKeyword("else");
            }
            mark.done(MontyParseStep.IF_EXPRESSION);
            return true;
        }
        mark.drop();
        return false;
    }

    boolean parsePrimary() {
        if (is(MontyParseStep.PARENTHESES, "(")) {
            advanceLexer();
            if (!parseExpression()) {
                markMissing(EXPRESSION);
            }
            consume(MontyParseStep.PARENTHESES, ")");
            return true;
        }
        if (parseLiteral()) {
            return true;
        }
        if (is(MontyParseStep.IDENTIFIER) ||
                is(MontyParseStep.CONSTANT_IDENTIFIER) ||
                is(MontyParseStep.KEYWORD, "self")) {
            advanceLexer();
            return true;
        }

        if (is(MontyParseStep.KEYWORD, "parent")) {
            PsiBuilder.Marker mark = mark();
            advanceLexer();
            consume(PARENTHESES, "(");
            consume(CLASS_IDENTIFIER);
            consume(PARENTHESES, ")");
            mark.done(PARENT);
            return true;
        }
        return false;
    }

    boolean parseLiteral() {
        if (is(MontyParseStep.NUMBER) || is(MontyParseStep.STRING) || is(MontyParseStep.KEYWORD, "true") || is(MontyParseStep.KEYWORD, "false")) {
            advanceLexer();
            return true;
        }
        if (parseArrayLiteral()) {
            return true;
        }
        return false;
    }

    private boolean parseArrayLiteral() {
        if (is(BRACKETS, "[")) {
            PsiBuilder.Marker mark = mark();
            advanceLexer();
            if (parseExpression()) {
                while (is(COMMA)) {
                    advanceLexer();
                    if (!parseExpression()) {
                        markMissing(EXPRESSION);
                    }
                }
            }
            consume(BRACKETS, "]");
            mark.done(ARRAY);
            return true;
        }
        return false;
    }

    public boolean parseExpressionList() {
        if (is(PARENTHESES, "(")) {
            advanceLexer();
            parseExpression();
            while (is(COMMA)) {
                advanceLexer();
                if (!parseExpression()) {
                    markMissing(EXPRESSION);
                }
            }
            consume(PARENTHESES, ")");
            return true;
        } else {
            return false;
        }
    }

    static List<String> precedenceRaw = Arrays.asList(
            ".;->",
            "^",
            "*;/;%",
            "+;-",
            "<;>;>=;<=",
            "=;!=",
            "in",
            "and",
            "or;xor",
            "as",
            "is"
    );
    static Map<String, Integer> precedence = new HashMap<String, Integer>();

    static {
        for (int i = 0; i < precedenceRaw.size(); i++) {
            String s = precedenceRaw.get(i);
            List<String> strings = Arrays.asList(s.split(";"));
            for (String string : strings) {
                precedence.put(string, i);
            }
        }
    }

    public static final List<String> OPS = Arrays.asList(
            "+", "-", "*", "/", "%", "^",
            "=", "!=", "<", ">", "<=", ">=",
            "and", "or", "xor", "in"
    );

}