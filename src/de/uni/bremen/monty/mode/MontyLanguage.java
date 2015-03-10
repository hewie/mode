package de.uni.bremen.monty.mode;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MontyLanguage extends Language {

    public static final MontyLanguage INSTANCE = new MontyLanguage();

    private MontyLanguage() {
        super("monty", "text/monty");
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Monty";
    }

    @Override
    public boolean isCaseSensitive() {
        return true;
    }

    @Nullable
    @Override
    public LanguageFileType getAssociatedFileType() {
        return MontyFileType.INSTANCE;
    }
}