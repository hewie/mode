package de.uni.bremen.monty.mode.parser;

import com.intellij.lang.PsiBuilder;

public class ClassParser extends BaseParser {

    private MontyParseStep montyParseStep;

    public ClassParser(PsiBuilder builder, MontyParseStep montyParseStep) {
        super(builder);
        this.montyParseStep = montyParseStep;
    }

    public boolean parseClass() {
        PsiBuilder.Marker marker = builder.mark();
        if (is(KEYWORD, "abstract")) {
            advanceLexer();
        }
        if (is(KEYWORD, "class")) {
            finishClassDecl();
            marker.done(CLASS_DECLARATION);
            return true;
        }
        marker.rollbackTo();
        return false;
    }

    private void finishClassDecl() {
        advanceLexer();
        consumeType();
        if (is(KEYWORD, "inherits")) {
            advanceLexer();
            consumeTypeList();
        }
        consumeOpenBlock();
        if (is(KEYWORD, "pass")) {
            advanceLexer();
        } else {
            if (parseMemberDeclaration()) {
                while (parseMemberDeclaration()) ;
            }
        }
        consumeCloseBlock();

    }

    private boolean parseMemberDeclaration() {
        if (is(OPERATOR, "#") || is(OPERATOR, "+") || is(OPERATOR, "-") || is(OPERATOR, "~")) {
            advanceLexer();
            if (!declarationParser().parseIndependentDeclaration() && !declarationParser().parseInitializerDeclaration()) {
                errorMark("Expected: MemberDeclaration");
            }
            return true;
        } else {
            PsiBuilder.Marker mark = mark();
            markMissingKeyword("Access Modifier");
            boolean isMember = declarationParser().parseIndependentDeclaration() || declarationParser().parseInitializerDeclaration();
            if (isMember) {
                mark.drop();
            } else {
                mark.rollbackTo();
            }
            return isMember;
        }
    }

    private DeclarationParser declarationParser() {
        return montyParseStep.declarationParser;
    }

}