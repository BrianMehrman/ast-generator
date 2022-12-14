/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.ast.generator;

import java.io.File;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.file.Files;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.v4.parse.ANTLRLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ast.grammers.java.JavaLexer;
import org.ast.grammers.java.JavaParser;


public class App {

    public static String readFile(String path) throws IOException {
        // take file path(s) from input
        File file = new File(path);
        byte[] encoded = Files.readAllBytes(file.toPath());
        return new String(encoded, Charset.forName("UTF-8"));
    }

    public static String getGreeting() {
        return "Hello";
    }

    public static void main(String args[]) throws IOException {
        String path = args[0];
        // String inputString = readFile(path);
        
        CharStream input = CharStreams.fromFileName(path);
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParserRuleContext ctx = parser.classDeclaration();

        printAST(ctx, false, 0);
    }

    private static void printAST(RuleContext ctx, boolean verbose, int indentation) {
        boolean toBeIgnored = !verbose && ctx.getChildCount() == 1 && ctx.getChild(0) instanceof ParserRuleContext;

        if (!toBeIgnored) {
            String ruleName =JavaParser.ruleNames[ctx.getRuleIndex()];
            for (int i = 0; i < indentation; i++) {
                System.out.print("  ");
            }
            System.out.println(ruleName + " -> " + ctx.getText());
        }
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree element = ctx.getChild(i);
            if (element instanceof RuleContext) {
                printAST((RuleContext) element, verbose, indentation + (toBeIgnored ? 0 : 1));
            }
        }
    }
}