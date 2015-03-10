package de.uni.bremen.monty.mode.parser;

import com.intellij.lang.PsiBuilder;

public class DeclarationParser extends BaseParser {

    private MontyParseStep montyParseStep;

    public DeclarationParser(PsiBuilder builder, MontyParseStep montyParseStep) {
        super(builder);
        this.montyParseStep = montyParseStep;
    }

    public boolean parseFunctionDeclaration() {
        PsiBuilder.Marker mark = mark();
        if (parseType()) {
            if (is(IDENTIFIER)) {
                advanceLexer();
                if (parseParameterList()) {
                    statementParser().consumeStatementBlock();
                    mark.done(FUNCTION_DECLARATION);
                    return true;
                } else {
                    mark.rollbackTo();
                    return false;
                }
            } else if (is(KEYWORD, "operator")) {
                advanceLexer();
                consumeBinaryOperation();
                consumeParameterList();
                statementParser().consumeStatementBlock();
                mark.done(FUNCTION_DECLARATION);
                return true;
            } else {
                mark.rollbackTo();
                return false;
            }
        } else {
            mark.drop();
            return false;
        }
    }

    private void consumeBinaryOperation() {
        if (expressionParser().is(OPERATOR)) {
            if (ExpressionParser.OPS.contains(expressionParser().getTokenText())) {
                advanceLexer();
            } else {
                PsiBuilder.Marker mark = mark();
                advanceLexer();
                mark.error("Invalid Operator");
            }
        } else if (expressionParser().is(BRACKETS, "[")) {
            advanceLexer();
            expressionParser().consume(BRACKETS, "]");
        } else {
            errorMark("Expected Operation Symbol");
        }
    }

    public boolean parseProcedureDeclaration() {
        PsiBuilder.Marker mark = mark();
        if (is(IDENTIFIER)) {
            advanceLexer();
            if (parseParameterList()) {
                if (statementParser().parseStatementBlock()) {
                    mark.done(FUNCTION_DECLARATION);
                    return true;
                }
            }
        }
        mark.rollbackTo();
        return false;
    }

    protected boolean parseIndependentDeclaration() {
        if (parseFunctionDeclaration() || parseProcedureDeclaration()) {
            return true;
        }

        PsiBuilder.Marker mark = mark();

        if (parseVariableDeclaration()) {
            parseAssignment(mark);
            consumeEOL();
            return true;
        } else if (parseConstantDeclaration()) {
            if (!parseAssignment(mark)) {
                markMissing(ASSIGNMENT);
            }
            consumeEOL();
            return true;
        } else {
            mark.rollbackTo();
            return false;
        }
    }

    protected boolean parseInitializerDeclaration() {
        if (is(KEYWORD, "initializer")) {
            advanceLexer();
            PsiBuilder.Marker mark = mark();
            consumeParameterList();
            statementParser().consumeStatementBlock();
            mark.done(INITIALIZER);
            return true;
        }
        return false;
    }

    private void consumeParameterList() {
        if (!parseParameterList()) {
            markMissingKeyword("(");
        }
    }

    private boolean parseParameterList() {
        if (is(PARENTHESES, "(")) {
            advanceLexer();
            boolean defaultP = parseDefaultOrNormalParameter(false);
            while (is(COMMA)) {
                advanceLexer();
                defaultP = parseDefaultOrNormalParameter(defaultP);
            }
            consume(PARENTHESES, ")");
            return true;
        } else {
            return false;
        }
    }

    private boolean parseDefaultOrNormalParameter(boolean mustBeDefault) {
        boolean defaultP = false;
        PsiBuilder.Marker mark = mark();
        if (parseVariableDeclaration()) {
            if (is(ASSIGNMENT)) {
                defaultP = true;
                advanceLexer();
                if (!expressionParser().parseExpression()) {
                    markMissing(EXPRESSION);
                }
            } else if (mustBeDefault) {
                PsiBuilder.Marker precede = mark.precede();
                mark.error("Default Parameter expected");
                mark = precede;
            }
            mark.done(PARAMETER);
        } else {
            mark.drop();
        }
        return defaultP;
    }

    private boolean parseAssignment(PsiBuilder.Marker mark) {
        boolean b;
        if (is(ASSIGNMENT)) {
            advanceLexer();
            if (!expressionParser().parseExpression()) {
                markMissing(EXPRESSION);
            }
            mark.done(ASSIGNMENT);
            b = true;
        } else {
            mark.drop();
            b = false;
        }
        return b;
    }

    private ExpressionParser expressionParser() {
        return montyParseStep.expressionParser;
    }

    private StatementParser statementParser() {
        return montyParseStep.statementParser;
    }
}