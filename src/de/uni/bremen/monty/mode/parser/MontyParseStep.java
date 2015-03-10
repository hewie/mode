package de.uni.bremen.monty.mode.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.openapi.diagnostic.Logger;

public class MontyParseStep extends BaseParser {

    private static final Logger log = Logger.getInstance("#MontyParseStep");
    protected final ExpressionParser expressionParser;
    protected final DeclarationParser declarationParser;
    protected StatementParser statementParser;
    private ClassParser classParser;

    public MontyParseStep(PsiBuilder builder) {
        super(builder);
        expressionParser = new ExpressionParser(builder);
        statementParser = new StatementParser(this.builder, this);
        declarationParser = new DeclarationParser(builder, this);
        classParser = new ClassParser(this.builder, this);
    }

    public void parseModule() {
        parseEOL();
        while (parseImport()) ;
        while (!builder.eof()) {
            if (!(parseClass() || parseStatement())) {
                errorMark("Unexpected Symbol: \"" + getTokenText() + "\" " + builder.getTokenType());
                advanceLexer();
            }
            parseEOL();
        }
    }

    private boolean parseImport() {
        if (is(KEYWORD, "import")) {
            PsiBuilder.Marker mark = builder.mark();
            advanceLexer();

            consume(IDENTIFIER);
            consumeEOL();
            mark.done(IMPORT);
            return true;
        }
        return false;
    }

    private boolean parseStatement() {
        return statementParser.parseStatement();
    }

    private boolean parseClass() {
        return classParser.parseClass();
    }
}
