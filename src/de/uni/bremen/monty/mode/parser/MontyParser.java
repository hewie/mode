package de.uni.bremen.monty.mode.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.openapi.project.Project;
import com.intellij.psi.tree.IElementType;
import de.uni.bremen.monty.mode.MontyElementTypes;
import org.jetbrains.annotations.NotNull;

public class MontyParser implements PsiParser, MontyElementTypes {

    public MontyParser(Project project) {

    }

    @NotNull
    @Override
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        MontyParseStep montyParseStep = new MontyParseStep(builder);
        PsiBuilder.Marker file = builder.mark();
        PsiBuilder.Marker module = builder.mark();
        montyParseStep.parseModule();
        module.done(MODULE);
        file.done(root);

        return builder.getTreeBuilt();
    }
}
