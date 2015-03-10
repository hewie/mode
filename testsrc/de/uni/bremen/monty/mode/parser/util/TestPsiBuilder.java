package de.uni.bremen.monty.mode.parser.util;

import com.intellij.lang.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestPsiBuilder implements PsiBuilder {

    private final List<TestParserBase.Token> tokens;


    //    public final List<TestMarker> markers;
    public final Map<Integer, List<TestMarker>> markerMap;
    public int currentIndex;

    public TestPsiBuilder(List<TestParserBase.Token> tokens) {
        this.tokens = tokens;
        currentIndex = 0;
        markerMap = new HashMap<>();
    }

    @Override
    public Project getProject() {
        throw new IllegalArgumentException();
    }

    @Override
    public CharSequence getOriginalText() {
        throw new IllegalArgumentException();
    }

    @Override
    public void advanceLexer() {
        if (eof()) {
            return;
        }
        currentIndex++;
    }

    @Nullable
    @Override
    public IElementType getTokenType() {
        if (currentIndex >= tokens.size()) return null;
        return tokens.get(currentIndex).type;
    }

    @Override
    public void setTokenTypeRemapper(ITokenTypeRemapper remapper) {
        throw new IllegalArgumentException();
    }

    @Override
    public void remapCurrentToken(IElementType type) {
        throw new IllegalArgumentException();
    }

    @Override
    public void setWhitespaceSkippedCallback(WhitespaceSkippedCallback callback) {
        throw new IllegalArgumentException();
    }

    @Nullable
    @Override
    public IElementType lookAhead(int steps) {
        return tokens.get(currentIndex + steps).type;
    }

    @Nullable
    @Override
    public IElementType rawLookup(int steps) {
        return tokens.get(currentIndex + steps).type;
    }

    @Override
    public int rawTokenTypeStart(int steps) {
        throw new IllegalArgumentException();
    }

    @Override
    public int rawTokenIndex() {
        throw new IllegalArgumentException();
    }

    @Nullable
    @Override
    public String getTokenText() {
        if (eof()) {
            return null;
        }
        return tokens.get(currentIndex).text;
    }

    @Override
    public int getCurrentOffset() {
        return currentIndex;
    }

    @Override
    public Marker mark() {
        return new TestMarker(this);
    }

    @Override
    public void error(String messageText) {
        mark().error(messageText);
    }

    @Override
    public boolean eof() {
        return currentIndex >= tokens.size();
    }

    @Override
    public ASTNode getTreeBuilt() {
        throw new IllegalArgumentException();
    }

    @Override
    public FlyweightCapableTreeStructure<LighterASTNode> getLightTree() {
        throw new IllegalArgumentException();
    }

    @Override
    public void setDebugMode(boolean dbgMode) {
        throw new IllegalArgumentException();
    }

    @Override
    public void enforceCommentTokens(TokenSet tokens) {
        throw new IllegalArgumentException();
    }

    @Nullable
    @Override
    public LighterASTNode getLatestDoneMarker() {
        throw new IllegalArgumentException();
    }

    @Nullable
    @Override
    public <T> T getUserData(Key<T> key) {
        throw new IllegalArgumentException();
    }

    @Override
    public <T> void putUserData(Key<T> key, T value) {
        throw new IllegalArgumentException();
    }

    @Nullable
    @Override
    public <T> T getUserDataUnprotected(Key<T> key) {
        throw new IllegalArgumentException();
    }

    @Override
    public <T> void putUserDataUnprotected(Key<T> key, T value) {
        throw new IllegalArgumentException();
    }
}
