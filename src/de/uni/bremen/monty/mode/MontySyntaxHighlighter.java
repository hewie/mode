package de.uni.bremen.monty.mode;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class MontySyntaxHighlighter extends SyntaxHighlighterBase {

    private final MontyLexer montyLexer;
    private final static Map<MontyElementType, TextAttributesKey[]> tokenMap = new HashMap<MontyElementType, TextAttributesKey[]>();

    public static final TextAttributesKey STRING_LITERAL = createTextAttributesKey("MONTY_STRING", DefaultLanguageHighlighterColors.STRING);
    private static final TextAttributesKey[] STRING_LITERAL_KEYS = new TextAttributesKey[]{STRING_LITERAL};

    public static final TextAttributesKey NUMBER_LITERAL = createTextAttributesKey("MONTY_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    private static final TextAttributesKey[] NUMBER_LITERAL_KEYS = new TextAttributesKey[]{NUMBER_LITERAL};

    public static final TextAttributesKey BRACKETS = createTextAttributesKey("MONTY_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
    private static final TextAttributesKey[] BRACKETS_KEYS = new TextAttributesKey[]{BRACKETS};

    public static final TextAttributesKey PARENTHESES = createTextAttributesKey("MONTY_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
    private static final TextAttributesKey[] PARENTHESES_KEYS = new TextAttributesKey[]{PARENTHESES};

    public static final TextAttributesKey BRACES = createTextAttributesKey("MONTY_BRACES", DefaultLanguageHighlighterColors.BRACES);
    private static final TextAttributesKey[] BRACES_KEYS = new TextAttributesKey[]{BRACES};

    public static final TextAttributesKey KEYWORD = createTextAttributesKey("MONTY_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};

    public static final TextAttributesKey COMMA_TOKEN = createTextAttributesKey("MONTY_COMMA_TOKEN", DefaultLanguageHighlighterColors.COMMA);
    private static final TextAttributesKey[] COMMA_TOKEN_KEYS = new TextAttributesKey[]{COMMA_TOKEN};

    public static final TextAttributesKey DOT_TOKEN = createTextAttributesKey("MONTY_DOT_TOKEN", DefaultLanguageHighlighterColors.DOT);
    private static final TextAttributesKey[] DOT_TOKEN_KEYS = new TextAttributesKey[]{DOT_TOKEN};

    public static final TextAttributesKey ASSIGNMENT = createTextAttributesKey("MONTY_ASSIGNMENT", DefaultLanguageHighlighterColors.KEYWORD);
    private static final TextAttributesKey[] ASSIGNMENT_KEYS = new TextAttributesKey[]{ASSIGNMENT};

    public static final TextAttributesKey COLON_TOKEN = createTextAttributesKey("MONTY_COLON_TOKEN", DefaultLanguageHighlighterColors.SEMICOLON);
    private static final TextAttributesKey[] COLON_TOKEN_KEYS = new TextAttributesKey[]{COLON_TOKEN};

    public static final TextAttributesKey OPERATOR = createTextAttributesKey("MONTY_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    private static final TextAttributesKey[] OPERATOR_KEYS = new TextAttributesKey[]{OPERATOR};

    public static final TextAttributesKey IDENTIFIER = createTextAttributesKey("MONTY_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    private static final TextAttributesKey[] IDENTIFIER_KEYS = new TextAttributesKey[]{IDENTIFIER};

    public static final TextAttributesKey CLASS_IDENTIFIER = createTextAttributesKey("MONTY_CLASS_IDENTIFIER", DefaultLanguageHighlighterColors.CLASS_NAME);
    private static final TextAttributesKey[] CLASS_IDENTIFIER_KEYS = new TextAttributesKey[]{CLASS_IDENTIFIER};

    public static final TextAttributesKey CONSTANT_IDENTIFIER = createTextAttributesKey("MONTY_CONSTANT_IDENTIFIER", DefaultLanguageHighlighterColors.CONSTANT);
    private static final TextAttributesKey[] CONSTANT_IDENTIFIER_KEYS = new TextAttributesKey[]{CONSTANT_IDENTIFIER};

    public static final TextAttributesKey COMMENT = createTextAttributesKey("MONTY_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};

    static {
        tokenMap.put(MontyElementTypes.IDENTIFIER, IDENTIFIER_KEYS);
        tokenMap.put(MontyElementTypes.STRING, STRING_LITERAL_KEYS);
        tokenMap.put(MontyElementTypes.NUMBER, NUMBER_LITERAL_KEYS);
        tokenMap.put(MontyElementTypes.PARENTHESES, PARENTHESES_KEYS);
        tokenMap.put(MontyElementTypes.BRACKETS, BRACKETS_KEYS);
        tokenMap.put(MontyElementTypes.BRACES, BRACES_KEYS);
        tokenMap.put(MontyElementTypes.KEYWORD, KEYWORD_KEYS);
        tokenMap.put(MontyElementTypes.COMMA, COMMA_TOKEN_KEYS);
        tokenMap.put(MontyElementTypes.DOT, DOT_TOKEN_KEYS);
        tokenMap.put(MontyElementTypes.ASSIGNMENT, ASSIGNMENT_KEYS);
        tokenMap.put(MontyElementTypes.COLON, COLON_TOKEN_KEYS);
        tokenMap.put(MontyElementTypes.OPERATOR, OPERATOR_KEYS);
        tokenMap.put(MontyElementTypes.CLASS_IDENTIFIER, CLASS_IDENTIFIER_KEYS);
        tokenMap.put(MontyElementTypes.CONSTANT_IDENTIFIER, CONSTANT_IDENTIFIER_KEYS);
        tokenMap.put(MontyElementTypes.COMMENT, COMMENT_KEYS);
    }

    public MontySyntaxHighlighter() {
        montyLexer = new MontyLexer();
    }

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return montyLexer;
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        TextAttributesKey[] textAttributesKeys = tokenMap.get(tokenType);
        if (textAttributesKeys == null) {
            return EMPTY;
        }
        return textAttributesKeys;
    }
}
