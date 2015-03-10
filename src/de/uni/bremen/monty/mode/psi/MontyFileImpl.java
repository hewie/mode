package de.uni.bremen.monty.mode.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import de.uni.bremen.monty.mode.MontyFileType;
import de.uni.bremen.monty.mode.MontyLanguage;
import org.jetbrains.annotations.NotNull;

public class MontyFileImpl extends PsiFileBase {

    public MontyFileImpl(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, MontyLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return MontyFileType.INSTANCE;
    }
}
