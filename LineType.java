package oop.ex6.parser;


import oop.ex6.codeelements.*;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 *A class to define the line type in our hands
 * 9 types - declaration, assignment, method signature, method call, condition, empty line, comment, closed scope,
 * and return.
 */
public enum LineType {
    DECLARATION (LineTypeIdentifier.declerationLineIdentifier())
            {
                /**
                 * Sends the declaration to be parsed
                 * @param line the line we'd like to parse
                 * @param scope the scope the line is in
                 * @throws ParserException throws all the exception that parser exception inherits
                 * @throws CodeElementsException throws all the exceptions that code elements exception inherits
                 */
                void parse(String line, Scope scope) throws ParserException, CodeElementsException
                {
                    LineParser.parseDeclaration(line,scope);
                }
            },
    ASSIGNMENT(LineTypeIdentifier.assignmentLineIdentifier())
            {
                /**
                 * Sends the assignment to be parsed
                 * @param line the line we'd like to parse
                 * @param scope the scope the line is in
                 * @throws ParserException throws all the exception that parser exception inherits
                 * @throws CodeElementsException throws all the exceptions that code elements exception inherits
                 */
                void parse(String line, Scope scope) throws ParserException, CodeElementsException
                {
                    LineParser.parseAssignment(line,scope);
                }
            },
    METHOD_SIGNATURE(LineTypeIdentifier.methodSignatureIdentifier())
            {
                /**
                 * Sends the method signature to be parsed
                 * @param line the line we'd like to parse
                 * @param scope the scope the line is in
                 * @throws ParserException throws all the exception that parser exception inherits
                 * @throws CodeElementsException throws all the exceptions that code elements exception inherits
                 */
                void parse (String line, Scope scope) throws CodeElementsException, ParserException
                {
                    LineParser.parseMethodSignature(line,scope);
                }
            },
    METHOD_CALL(LineTypeIdentifier.methodCallIdentifier())
            {
                /**
                 * Sends the method call to be parsed
                 * @param line the line we'd like to parse
                 * @param scope the scope the line is in
                 * @throws ParserException throws all the exception that parser exception inherits
                 * @throws CodeElementsException throws all the exceptions that code elements exception inherits
                 */
                void parse (String line, Scope scope) throws CodeElementsException, ParserException
                {
                    LineParser.parseMethodCall(line,scope);
                }
            },
    CONDITION (LineTypeIdentifier.conditionLineIdentifier())
            {
                /**
                 * Sends the condition to be parsed
                 * @param line the line we'd like to parse
                 * @param scope the scope the line is in
                 * @throws ParserException throws all the exception that parser exception inherits
                 * @throws CodeElementsException throws all the exceptions that code elements exception inherits
                 */
                void parse(String line, Scope scope) throws CodeElementsException, ParserException
                {
                    LineParser.parseCondition(line,scope);
                }
            },
    EMPTY_LINE (LineTypeIdentifier.emptyLineIdentifier())
            {
                /**
                 * Sends the empty line to be parsed
                 * @param line the line we'd like to parse
                 * @param scope the scope the line is in
                 */
                void parse(String line, Scope scope)
                {
                    return;
                }
            },
    COMMENT (LineTypeIdentifier.commentIdentifier())
            {
                /**
                 * Sends the comment to be parsed
                 * @param line the line we'd like to parse
                 * @param scope the scope the line is in
                 */
                void parse(String line, Scope scope)
                {
                    return;
                }
            },
    CLOSE_SCOPE(LineTypeIdentifier.closeScopeIdentifier())
            {
                /**
                 * Sends the close scope to be parsed
                 * @param line the line we'd like to parse
                 * @param scope the scope the line is in
                 * @throws InvalidScopeException throws for an invalid scope
                 */
              void parse (String line, Scope scope) throws InvalidScopeException
              {
                  return;
              }
            },
    RETURN (LineTypeIdentifier.returnIdentifier())
            {
                /**
                 * Sends the return to be parsed
                 * @param line the line we'd like to parse
                 * @param scope the scope the line is in
                 */
                void parse (String line, Scope scope) {
                    return;
                }
            };


    /**
     * Chooses a line type according to the identifier.
     * @param line the line we are choosing the type for
     * @return returns the type of line in our hands
     * @throws InvalidSyntaxException throws an invalid syntax
     */
    static LineType chooseLineType(String line) throws InvalidSyntaxException
    {
        for (LineType type: LineType.values())
        {
            if (type.identify(line))
                return type;
        }
        throw new InvalidSyntaxException();

    }

    private Predicate<String> identifier;

    /**
     * Line type constructor
     * @param stringPredicate the identifier for the line type.
     */
    LineType(Predicate<String> stringPredicate) {
        identifier = stringPredicate;
    }

    /**
     * Identifies the line according to their specific test.
     * @param line the line we'd like to identify
     * @return true if the line has been identified, false otherwise
     */
    private boolean identify(String line)
    {
        return identifier.test(line);
    }

    /**
     * the parse method for each line - over ridden by each type of line
     * @param line the line we'd like to parse
     * @param scope the scope the line is in
     * @throws ParserException throws the abstract parse exception containing all the exceptions
     * @throws CodeElementsException throws the code elements exception containing all its inherited exceptions.
     */
    void parse(String line, Scope scope) throws ParserException, CodeElementsException
    {}



    private static class LineTypeIdentifier
    {
        private final static String IF = "if";
        private final static String WHILE = "while";
        private final static String FINAL = "final";
        private final static String ASSIGNMENT_SIGN = "=";
        private final static String SEMICOLON = ";";
        private final static String OPEN_BRACKET = "(";
        private final static String CLOSE_BRACLET = ")";
        private final static String OPEN_SCOPE = "{";
        private final static String CLOSE_SCOPE = "}";
        private final static String SLASH = "/";
        private final static String VOID = "void";
        private final static String COMMENT_ANNOTATION = "//";
        private final static String RETURN = "return;";


        /**
         * Using a Predicate function, checks if the line identifies with a declaration line.
         * @return returns true if the line matches declaration criteria, false otherwise.
         */
        private static Predicate<String> declerationLineIdentifier()
        {
            return ((line -> line.startsWith(FINAL) || VariableType.startsWithVariableTypeName(line)
            && !line.contains(OPEN_BRACKET) && !line.contains(CLOSE_BRACLET))
            );
        }

        /**
         * Using a Predicate function, checks if the line identifies with a condition line.
         * @return returns true if the line matches condition criteria, false otherwise.
         */
        private static Predicate<String> conditionLineIdentifier()
        {
            return (line -> (line.startsWith(IF) || line.startsWith(WHILE)) && line.endsWith(OPEN_SCOPE))  ;
        }

        /**
         * Using a Predicate function, checks if the line identifies with a assignment line.
         * @return returns true if the line matches assignment criteria, false otherwise.
         */
        private static Predicate<String> assignmentLineIdentifier()
        {
            return (line -> line.contains(ASSIGNMENT_SIGN) && !line.contains(IF) && !line.contains(WHILE) &&
                    line.endsWith(SEMICOLON) && !line.startsWith(SLASH) && !line.contains(VOID));
        }

        /**
         * Using a Predicate function, checks if the line identifies with a method call line.
         * @return returns true if the line matches method call criteria, false otherwise.
         */
        private static Predicate<String> methodCallIdentifier()
        {
            return (line ->  line.contains(OPEN_BRACKET) && line.contains(CLOSE_BRACLET) && line.endsWith(SEMICOLON) ) ;
        }

        /**
         * Using a Predicate function, checks if the line identifies with a method signature line.
         * @return returns true if the line matches method signature criteria, false otherwise.
         */
        private static Predicate<String> methodSignatureIdentifier()
        {
            return (line -> line.startsWith(VOID) && line.contains(OPEN_BRACKET) && line.contains(CLOSE_BRACLET) &&
                    !line.contains(SEMICOLON) && line.endsWith(OPEN_SCOPE));
        }

        /**
         * Using a Predicate function, checks if the line identifies with a empty line.
         * @return returns true if the line matches empty line criteria, false otherwise.
         */
        private static Predicate<String> emptyLineIdentifier()
        {
            Pattern pattern = Pattern.compile("\\s*");
            return (line -> pattern.matcher(line).matches());
        }

        /**
         * Using a Predicate function, checks if the line identifies with a comment line.
         * @return returns true if the line matches comment criteria, false otherwise.
         */
        private static Predicate<String> commentIdentifier()
        {
            return (line -> line.startsWith(COMMENT_ANNOTATION));
        }

        /**
         * Using a Predicate function, checks if the line identifies with a close scope line.
         * @return returns true if the line matches close scope criteria, false otherwise.
         */
        private static Predicate<String> closeScopeIdentifier()
        {
            return (line -> line.equals(CLOSE_SCOPE));
        }

        /**
         * Using a Predicate function, checks if the line identifies with a return line.
         * @return returns true if the line matches return criteria, false otherwise.
         */
        private static Predicate<String> returnIdentifier()
        {
            return (line -> line.equals(RETURN));
        }

    }



}
