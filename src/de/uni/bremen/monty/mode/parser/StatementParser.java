package de.uni.bremen.monty.mode.parser;

import com.intellij.lang.PsiBuilder;
import de.uni.bremen.monty.mode.MontyElementType;

public class StatementParser extends BaseParser {

    private final MontyParseStep montyParseStep;

    public StatementParser(PsiBuilder builder, MontyParseStep montyParseStep) {
        super(builder);
        this.montyParseStep = montyParseStep;
    }

    public boolean parseStatement() {
        if (is(KEYWORD)) {
            switch (builder.getTokenText()) {
                case "while": {
                    return parseWhile();
                }
                case "if": {
                    return parseIf();
                }
                case "try": {
                    return parseTry();
                }
            }
        }

        if (declarationParser().parseIndependentDeclaration()) {
            return true;
        }

        if (parseAssignment()) {
            return true;
        }

        if (parseSimple("return", true, RETURN)) {
            return true;
        }

        if (parseSimple("raise", true, RAISE)) {
            return true;
        }

        if (parseSimple("skip", false, SKIP)) {
            return true;
        }

        if (parseSimple("break", false, BREAK)) {
            return true;
        }

        if (expressionParser().parseExpression()) {
            consumeEOL();
            return true;
        }

        return false;
    }

    private boolean parseSimple(String keyword, boolean hasOptionalExpression, MontyElementType type) {
        if (is(KEYWORD, keyword)) {
            PsiBuilder.Marker mark = mark();
            advanceLexer();
            if (hasOptionalExpression) {
                expressionParser().parseExpression();
            }
            consumeEOL();
            mark.done(type);
            return true;
        }
        return false;
    }

    private boolean parseAssignment() {
        PsiBuilder.Marker mark = mark();
        if (expressionParser().parseExpression()) {
            if (is(ASSIGNMENT)) {
                advanceLexer();
                if (!expressionParser().parseExpression()) {
                    markMissing(EXPRESSION);
                }
                consumeEOL();
                mark.done(ASSIGNMENT);
                return true;
            }
        }
        mark.rollbackTo();
        return false;
    }

    private boolean parseTry() {
        PsiBuilder.Marker mark = builder.mark();
        advanceLexer();
        consumeStatementBlock();
        if (is(KEYWORD, "handle")) {
            parseHandle();
            while (is(KEYWORD, "handle")) {
                parseHandle();
            }
        } else {
            markMissingKeyword("handle");
        }
        mark.done(TRY);
        return true;
    }

    private void parseHandle() {
        advanceLexer();
        if (!parseVariableDeclaration()) {
            markMissing(VARIABLE_DECLARATION);
        }
        consumeStatementBlock();
    }

    private boolean parseWhile() {
        PsiBuilder.Marker mark = builder.mark();
        conditionAndBlock();
        mark.done(WHILE);
        return true;
    }

    private boolean parseIf() {
        PsiBuilder.Marker mark = builder.mark();
        conditionAndBlock();

        while (is(KEYWORD, "elif")) {
            conditionAndBlock();
        }

        if (is(KEYWORD, "else")) {
            advanceLexer();
            consumeStatementBlock();
        }

        mark.done(IF_STATEMENT);
        return true;
    }

    private void conditionAndBlock() {
        advanceLexer();
        if (!expressionParser().parseExpression()) {
            markMissing(EXPRESSION);
        }
        consumeStatementBlock();
    }

    public void consumeStatementBlock() {
        consumeOpenBlock();
        if (is(KEYWORD, "pass")) {
            advanceLexer();
        } else if (parseStatement()) {
            while (parseStatement()) ;
        } else {
            errorMark("Expected: Statement");
        }
        consumeCloseBlock();
    }

    public boolean parseStatementBlock() {
        if (parseOpenBlock()) {
            if (is(KEYWORD, "pass")) {
                advanceLexer();
            } else if (parseStatement()) {
                while (parseStatement()) ;
            } else {
                errorMark("Expected: Statement");
            }
            consumeCloseBlock();
            return true;
        } else return false;
    }

    private ExpressionParser expressionParser() {
        return montyParseStep.expressionParser;
    }

    private DeclarationParser declarationParser() {
        return montyParseStep.declarationParser;
    }
}
