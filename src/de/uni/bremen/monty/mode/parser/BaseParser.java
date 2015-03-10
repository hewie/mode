package de.uni.bremen.monty.mode.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import de.uni.bremen.monty.mode.MontyElementType;
import de.uni.bremen.monty.mode.MontyElementTypes;

public class BaseParser implements MontyElementTypes {

    protected PsiBuilder builder;

    public BaseParser(PsiBuilder builder) {
        this.builder = builder;
    }

    protected void errorMark(String message) {
        mark().error(message);
    }

    protected void markMissing(IElementType type) {
        errorMark("Expected: " + type.toString());
    }

    protected void markMissingKeyword(String keyword) {
        errorMark("Expected: " + keyword);
    }

    protected boolean hasText(String text) {
        String tokenText = builder.getTokenText();
        return tokenText != null && tokenText.equals(text);
    }

    protected boolean is(MontyElementType... types) {
        IElementType tokenType = builder.getTokenType();
        if (tokenType != null) {
            for (MontyElementType type : types) {
                if (tokenType.equals(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean is(MontyElementType type, String text) {
        return is(type) && hasText(text);
    }

    protected void advanceLexer() {
        builder.advanceLexer();
    }

    protected boolean eof() {
        return builder.eof();
    }

    protected PsiBuilder.Marker mark() {
        return builder.mark();
    }

    protected String getTokenText() {
        return builder.getTokenText();
    }

    protected void consumeOpenBlock() {
        consume(COLON);
        parseEOL();
        consume(EOL_INDENT);
        parseEOL();
    }

    protected boolean parseOpenBlock() {
        if (is(COLON)) {
            advanceLexer();
            consume(EOL_INDENT);
            return true;
        }
        return false;
    }

    protected void consumeCloseBlock() {
        parseEOL();
        while (!eof() && !is(EOL_DEDENT)) {
            PsiBuilder.Marker mark = mark();
            String tokenText = getTokenText();
            advanceLexer();
            mark.error("Unexpected symbol: " + tokenText);
        }
        if (is(EOL_DEDENT)) {
            advanceLexer();
        }
    }


    protected boolean parseType() {
        if (is(CLASS_IDENTIFIER)) {
            PsiBuilder.Marker mark = mark();
            advanceLexer();
            if (is(OPERATOR, "<")) {
                advanceLexer();
                consumeTypeList();
                consume(OPERATOR, ">");
            }
            mark.done(TYPE);
            return true;
        } else {
            return false;
        }
    }

    protected void consumeType() {
        if (!parseType()) {
            markMissing(TYPE);
        }
    }

    protected void consumeTypeList() {
        consumeType();
        while (is(COMMA)) {
            advanceLexer();
            consumeType();
        }
    }

    protected void consume(MontyElementType type, String text) {
        if (is(type, text)) {
            advanceLexer();
        } else {
            markMissingKeyword(text);
        }
    }


    protected void consume(MontyElementType type) {
        if (is(type)) {
            advanceLexer();
        } else {
            markMissing(type);
        }
    }

    protected void consumeEOL() {
        if (!eof() && !is(EOL_DEDENT)) {
            consume(EOL);
            parseEOL();
        }
    }

    protected void parseEOL() {
        while (is(EOL)) {
            advanceLexer();
        }
    }

    protected boolean parseVariableDeclaration() {
        return parseVarDeclaration(IDENTIFIER, VARIABLE_DECLARATION);
    }

    protected boolean parseConstantDeclaration() {
        return parseVarDeclaration(CONSTANT_IDENTIFIER, CONSTANT_DECLARATION);
    }

    private boolean parseVarDeclaration(MontyElementType idType, MontyElementType newType) {
        PsiBuilder.Marker mark = mark();
        if (parseType()) {
            if (is(idType)) {
                advanceLexer();
                mark.done(newType);
                return true;
            }
        }
        mark.rollbackTo();
        return false;
    }
}
