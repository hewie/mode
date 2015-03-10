package de.uni.bremen.monty.mode;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;

public interface MontyElementTypes {
    MontyElementType STRING = new MontyElementType("StringLiteral");
    MontyElementType NUMBER = new MontyElementType("NumberLiteral");
    MontyElementType PARENTHESES = new MontyElementType("Parentheses");
    MontyElementType BRACES = new MontyElementType("Braces");
    MontyElementType BRACKETS = new MontyElementType("Brackets");
    MontyElementType KEYWORD = new MontyElementType("Keyword");
    MontyElementType COMMA = new MontyElementType("Comma");
    MontyElementType DOT = new MontyElementType("Dot");
    MontyElementType COLON = new MontyElementType("Colon");
    MontyElementType OPERATOR = new MontyElementType("Operator");
    MontyElementType ASSIGNMENT = new MontyElementType("Assignment");
    MontyElementType IDENTIFIER = new MontyElementType("Identifier");
    MontyElementType CLASS_IDENTIFIER = new MontyElementType("ClassIdentifier");
    MontyElementType CONSTANT_IDENTIFIER = new MontyElementType("ConstantIdentifier");
    MontyElementType COMMENT = new MontyElementType("Comment");
    MontyElementType EOL_INDENT = new MontyElementType("Indent");
    MontyElementType EOL_DEDENT = new MontyElementType("Dedent");
    MontyElementType BAD_DEDENT = new MontyElementType("BadDedent");
    MontyElementType EOL = new MontyElementType("Eol");

    MontyElementType MODULE = new MontyElementType("Module");
    MontyElementType BLOCK = new MontyElementType("Block");
    MontyElementType CLASS_DECLARATION = new MontyElementType("ClassDeclaration");
    MontyElementType WHILE = new MontyElementType("While");
    MontyElementType TRY = new MontyElementType("Try");
    MontyElementType IF_STATEMENT = new MontyElementType("If");
    MontyElementType IF_EXPRESSION = new MontyElementType("IfExpression");
    MontyElementType BINARY_EXPRESSION = new MontyElementType("Expression");
    MontyElementType UNARY_EXPRESSION = new MontyElementType("Expression");
    MontyElementType EXPRESSION = new MontyElementType("Expression");
    MontyElementType IMPORT = new MontyElementType("Import");
    MontyElementType VARIABLE_DECLARATION = new MontyElementType("VariableDeclaration");
    MontyElementType CONSTANT_DECLARATION = new MontyElementType("ConstantDeclaration");
    MontyElementType FUNCTION_DECLARATION = new MontyElementType("FunctionDeclaration");
    MontyElementType TYPE = new MontyElementType("Type");
    MontyElementType PARAMETER = new MontyElementType("Parameter");
    MontyElementType INITIALIZER = new MontyElementType("Initializer");
    MontyElementType FUNCTION_CALL = new MontyElementType("FunctionCall");
    MontyElementType BREAK = new MontyElementType("Break");
    MontyElementType SKIP = new MontyElementType("Skip");
    MontyElementType RETURN = new MontyElementType("Return");
    MontyElementType RAISE = new MontyElementType("Raise");
    MontyElementType MEMBER_ACCESS = new MontyElementType("MemberAccess");
    MontyElementType ARRAY = new MontyElementType("Array");
    MontyElementType PARENT = new MontyElementType("Parent");


    IElementType WHITESPACE = TokenType.WHITE_SPACE;
    IElementType ERROR = TokenType.BAD_CHARACTER;
    IFileElementType FILE = new MontyElementType.MontyFileElementType();
}
