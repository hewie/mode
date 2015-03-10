package de.uni.bremen.monty.mode;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import de.uni.bremen.monty.mode.parser.MontyParser;
import de.uni.bremen.monty.mode.psi.MontyElementCreator;
import de.uni.bremen.monty.mode.psi.MontyFileImpl;
import org.jetbrains.annotations.NotNull;

public class MontyParserDefinition implements ParserDefinition {

    private static final Logger log = Logger.getInstance("#MontyParserDefinition");
    private MontyElementCreator elementCreator = new MontyElementCreator();

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new MontyLexer();
    }

    @Override
    public PsiParser createParser(Project project) {
        return new MontyParser(project);
    }

    @Override
    public IFileElementType getFileNodeType() {
        return MontyElementTypes.FILE;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return TokenSet.create(MontyElementTypes.WHITESPACE);
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return TokenSet.create(MontyElementTypes.COMMENT);
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return TokenSet.create(MontyElementTypes.STRING);
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return elementCreator.create(node);
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new MontyFileImpl(viewProvider);
    }

    @Override
    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return null;
    }
}
