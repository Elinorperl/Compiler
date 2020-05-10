package oop.ex6.parser;

import oop.ex6.codeelements.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Parsers the lines into different types (where code will be sent for further examining)
 */
public class FileParser {

    private final static String IN_LINE = " in line ";
    private final static int CODE_IS_VALID = 0;
    private final static int CODE_IS_INVALID = 1;

    /**
     * Parses the given file, and sends it to the line parser to make the line declaration.
     * @param file the file to parse
     * @throws IOException throws all the possible exceptions
     */
    public static void parseFile(File file) throws IOException
    {
        int lineCount = 1;
        try {
            Scanner scan = new Scanner(file);
            // declaration of root scope
            Scope root = new Scope(null,false);
            boolean prevLineIsReturn = false;
            Scope currentScope = root;
            while (scan.hasNextLine()) {
                String line = scan.nextLine().trim();
                LineType type = LineType.chooseLineType(line);
                if (type == LineType.CONDITION)
                    currentScope = new Scope(currentScope,false);
                else if (type == LineType.METHOD_SIGNATURE)
                    currentScope = new Scope (currentScope,true);
                else if (type == LineType.CLOSE_SCOPE) {
                    if (!prevLineIsReturn && currentScope.isMethodScope())
                        throw new InvalidMethodException();
                    currentScope = currentScope.getOuterScope();
                }
                if (type == LineType.RETURN)
                    prevLineIsReturn= true;
                else
                    prevLineIsReturn = false;
                type.parse(line,currentScope);
                lineCount++;
            }
            AssignmentCall.verifyAll();
            MethodCall.verifyAll();
            resetAllMutables();
            System.out.println(CODE_IS_VALID);
        }
        catch (ParserException | CodeElementsException e) {
            System.err.println(e.getMessage() + IN_LINE + lineCount);
            resetAllMutables();
            System.out.println(CODE_IS_INVALID);
        }
    }

    /**
     * resets all the static methods.
     */
    private static void resetAllMutables()
    {
        Method.resetAllMethods();
        AssignmentCall.resetAll();
        MethodCall.resetAll();
        Scope.resetRoot();
    }




}



