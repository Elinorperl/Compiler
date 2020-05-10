package oop.ex6.parser;

import oop.ex6.codeelements.*;
import oop.ex6.codeelements.IncompatibleValueTypeException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses the lines of our method.
 */
class LineParser
{
    private final static String FINAL = "final";
    private final static String VARIABLE_DELIMITER = ",";
    private final static String ASSIGNMENT_SIGN = "=";
    private final static String EMPTY_STRING = "";
    private final static String SEMICOLON = ";";
    private final static String OPEN_CONDITION = "(";
    private final static String CLOSE_CONDITION = ")";
    private final static String OPEN_SCOPE = "{";
    private final static String WORD = "\\w+";
    private final static String LETTERSTARTER = "[a-z]+";
    private final static String CONDITIONSPLITER = "\\|{2}|&{2}";
    private final static String VOID = "void";
    private final static String METHODCALLWORD = "[a-zA-Z]+\\w*";
    private final static String METHODPARAMETERS = "\\w{1,}|\"\\w{1,}\"|'\\w{1}'";
    private final static String METHODNAMESEARCH = "[^(]{1,}";
    private final static String WHITESPACE = "\\s";


    /**
     * Declaration parser
     * @param line declaration line
     * @param scope the scope the line lies in.
     * @throws InvalidTypeException for invalid types
     * @throws InvalidSyntaxException  for invalid syntax
     * @throws InvalidVariableNameException  for invalid variable names
     * @throws UndeclaredVariableException for undeclared variables
     * @throws IncompatibleValueTypeException for unmatched type and values
     */
    static void parseDeclaration(String line, Scope scope) throws InvalidTypeException, InvalidSyntaxException,
            InvalidVariableNameException, UndeclaredVariableException, IncompatibleValueTypeException,
            DuplicateVariableException, FinalAssigmentException
    {
            verifyTypeAppearsOnce(line);
            boolean isFinal = false;
            //check whether variable is final
            if (line.startsWith(FINAL)) {
                isFinal = true;
                line = removeUsedPart(line, FINAL);
            }
            //get variableType of variable
            VariableType variableType = getDeclarationVariableType(line);
            line = removeUsedPart(line, variableType.getName());
            Variable variable;
            String variableData = "";
            for (int i = 0; i < line.length(); i++) {
                if (!line.substring(i, i+1).equals(VARIABLE_DELIMITER) && !(line.substring(i, i+1).equals(SEMICOLON)))
                    variableData += line.substring(i, i+1);
                else {
                        variable = createNewVariable(variableData, variableType, isFinal, scope);
                        scope.addVariable(variable);
                        variableData = "";
                    }
            }
        }

    /**
     * A getter function - gets the "VariableType" of our line
     * @param line the line we're checking
     * @return returns the type of the line.
     * @throws InvalidSyntaxException for invalid syntax
     * @throws InvalidTypeException for invalid types
     */
    private static VariableType getDeclarationVariableType(String line) throws InvalidSyntaxException, InvalidTypeException
    {
        Pattern pattern = Pattern.compile(WORD);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find())
            return VariableType.chooseTypeByName(line.substring(matcher.start(), matcher.end()));
        else
            throw new InvalidSyntaxException();
    }

    /**
     * Removes the part of the line that is unneeded.
     * @param line the line we'd like to re-define
     * @param removed - the part we'd like to remove
     * @return the new line.
     */
    private static String removeUsedPart(String line, String removed)
    {
        String returnLine =  line.replace(removed,EMPTY_STRING);
        return returnLine.trim();
    }

    /**
     * Creates a new variable.
     * @param variableData the variable name
     * @param variableType variableType of variable
     * @param isFinal boolean variable indicating whether the variable is final
     * @param scope the scope in which the variable should be placed.
     * @return the new variable we've created.
     * @throws InvalidTypeException for invalid types
     * @throws InvalidVariableNameException - for an invalid variable name
     * @throws UndeclaredVariableException for an undeclared variable
     * @throws IncompatibleValueTypeException for unmatched value and type
     * @throws FinalAssigmentException for a final variable that has been reassigned
     */
    private static Variable createNewVariable (String variableData, VariableType variableType, boolean isFinal, Scope scope) throws
            InvalidTypeException, InvalidVariableNameException, UndeclaredVariableException,
            IncompatibleValueTypeException, FinalAssigmentException
    {
        String [] arrayVariable;
        //handle assignment
        if (variableData.contains(ASSIGNMENT_SIGN))
        {
            arrayVariable = variableData.split(ASSIGNMENT_SIGN);
            return new Variable (variableType,arrayVariable[0].trim(),arrayVariable[1].trim(),isFinal,scope);
        }
        // create unassigned variable
        else
             return new Variable(variableType,variableData.trim(),null,isFinal,scope);
    }

    /**
     * A function that knows to parse deal with assignments.
     * @param line the line to be parsed for assignment
     * @param scope the scope in which the line appears
     * @throws UndeclaredVariableException for undeclared variable
     * @throws FinalAssigmentException for a final variable reassignment
     * @throws IncompatibleValueTypeException for unmatched values and types
     * @throws InvalidTypeException for invalid types
     * @throws InvalidVariableNameException for invalid variable names
     * @throws DuplicateVariableException for duplicate variables
     * @throws UninitializedVariableException for uninitialized variables.
     */

    static void parseAssignment(String line, Scope scope) throws UndeclaredVariableException,
            FinalAssigmentException, IncompatibleValueTypeException, InvalidTypeException, InvalidVariableNameException,
            DuplicateVariableException, UninitializedVariableException
    {
        line = line.trim();
        String [] arrayVariable = line.split(ASSIGNMENT_SIGN);
        String variableName = arrayVariable[0].trim(),
                variableValue = arrayVariable[1].replace(SEMICOLON,EMPTY_STRING).trim();
        Scope variableScope = Scope.searchScopeOfVariable(variableName,scope);
        if (variableScope == null)
            throw new UninitializedVariableException();
        Variable variable = variableScope.getVariable(variableName);
        if (variable.isFinal())
            throw new FinalAssigmentException();
        if (variableScope!=scope) {
            Variable newVariable = new Variable(variable.getVariableType(), variable.getName(), variableValue,
                    false, scope);
            scope.addVariable(newVariable);
        }
        else
            variable.setValue(variableValue);
    }


    /**
     * Parses the condition line to ensure that conditions are valid and a new scope is created.
     * @param line the condition line needed to be parsed
     * @throws InvalidConditionException for invalid conditions
     * @throws UninitializedVariableException for uninitialized variables
     * @throws UndeclaredVariableException for undeclared variables
     * @throws IncompatibleValueTypeException for unmatched value and type
     */
    static void parseCondition(String line, Scope scope) throws InvalidConditionException, UninitializedVariableException,
    UndeclaredVariableException, IncompatibleValueTypeException{
        Pattern pattern = Pattern.compile(LETTERSTARTER);
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        String conditionWord = line.substring(matcher.start(),matcher.end());
        line = line.replace(conditionWord,"").replace(OPEN_CONDITION,EMPTY_STRING).replace
                (CLOSE_CONDITION,EMPTY_STRING).replace(OPEN_SCOPE,EMPTY_STRING).trim();
        String [] conditions = line.split(CONDITIONSPLITER);
        if (conditions[0].equals(""))
            throw new InvalidConditionException();
        pattern = Pattern.compile(VariableType.BOOLEAN.getValuePattern());
        Variable variable;
        String trimmedCondition;
        for (String condition: conditions)
        {
            trimmedCondition =   condition.trim();
            matcher = pattern.matcher(trimmedCondition);
            if (!matcher.matches()) {
                if (!Variable.checkValueIsVariable(trimmedCondition))
                    throw new InvalidConditionException();
                else
                {
                    variable = Variable.getVariable(trimmedCondition, scope);
                    if (variable == null)
                        throw new UninitializedVariableException();
                    else {
                        if (variable.getValue()!=null) {
                            if (!VariableType.BOOLEAN.checkValue(variable.getValue()))
                                throw new IncompatibleValueTypeException();
                        }
                        else
                            throw new UndeclaredVariableException();
                    }

                }

            }
        }
    }

    /**
     *
     * @param line the line we'd like to parse
     * @param scope the scope we're currently in
     * @throws InvalidSyntaxException for invalid syntax
     * @throws InvalidTypeException for invalid type
     * @throws InvalidVariableNameException for invalid variable name
     * @throws UndeclaredVariableException for undeclared variable
     * @throws FinalAssigmentException for final variable reassignments
     * @throws IncompatibleValueTypeException for unmatched values and types
     * @throws DuplicateVariableException for duplicate variables
     * @throws InvalidMethodException for invalid methods
     */
    static void parseMethodSignature(String line, Scope scope) throws InvalidSyntaxException,InvalidTypeException,
            InvalidVariableNameException, UndeclaredVariableException, FinalAssigmentException,
            IncompatibleValueTypeException, DuplicateVariableException, InvalidMethodException
    {
        ArrayList<VariableType> parameters = new ArrayList<>();
        line = line.trim();
        if (!line.startsWith(VOID))
            throw new InvalidSyntaxException();
        line = line.replace(VOID, EMPTY_STRING).trim();
        // searching for the method name
        String methodName = getMethodNameInLine(line);
        //checking method parameters.
        verifyParenthesisValidity(line);
        line = line.substring(line.indexOf(OPEN_CONDITION)+1, line.indexOf(CLOSE_CONDITION)).trim();
        String [] parameterStrings = line.split(VARIABLE_DELIMITER);
        Variable variable;
        VariableType type;
        String variableName;
        boolean isFinal;
        ArrayList<String> parametersData;
        if (line.equals(EMPTY_STRING)) {
            new Method(methodName, null);
            return;
        }
        else {
        for (String parameter : parameterStrings) {
            parametersData = processParameterString(parameter);
            isFinal = parametersData.get(0).equals(FINAL);
            int indexVariableName;
            if (isFinal)
                indexVariableName = 1;
            else
                indexVariableName = 0;
            type = VariableType.chooseTypeByName(parametersData.get(indexVariableName));
            parameters.add(type);
            variableName = parametersData.get(indexVariableName + 1);
            Variable.checkValidName(variableName);
            variable = new Variable(type, variableName, EMPTY_STRING, isFinal, scope);
            scope.addVariable(variable);
        }new Method(methodName, parameters);
    }
    }

    /**
     * Parses the method call
     * @param line the line to parse
     * @param scope the scope the method call is found in
     * @throws InvalidSyntaxException throws invalid syntax
     * @throws UndeclaredVariableException throws undeclared variables.
     */
    static void parseMethodCall(String line, Scope scope) throws InvalidSyntaxException, UndeclaredVariableException {
        String methodName;
        Pattern pattern = Pattern.compile(METHODCALLWORD);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find())
            methodName = line.substring(matcher.start(), matcher.end());
        else
            throw new InvalidSyntaxException();
        verifyParenthesisValidity(line);
        line = line.substring(line.indexOf("(")+1, line.indexOf(")")).trim();
        pattern = Pattern.compile(METHODPARAMETERS);
        matcher = pattern.matcher(line);
        ArrayList<String> variableValues = new ArrayList<>();
        int countMatch = 0;
        while (matcher.find()) {
            variableValues.add(line.substring(matcher.start(), matcher.end()));
            countMatch ++;
        }
        if (countMatch > 0)
            new MethodCall(methodName,variableValues,scope);
        else
            new MethodCall(methodName,null,scope);
    }

    /**
     * A getter function - to retrieve the method name in the line
     * @param line the line we'd like to get from
     * @return the method name
     * @throws InvalidSyntaxException throws invalid syntax.
     */
    private static String getMethodNameInLine(String line) throws InvalidSyntaxException
    {
        Pattern pattern = Pattern.compile(METHODNAMESEARCH);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find())
            return line.substring(matcher.start(), matcher.end()).trim();
        else
            throw new InvalidSyntaxException();
    }

    /**
     * Verifies the parenthesis are valid
     * @param line we are checking
     * @throws InvalidSyntaxException for invalid syntax
     */
    private static void verifyParenthesisValidity(String line) throws InvalidSyntaxException
    {
        if (!line.contains(OPEN_CONDITION) || !line.contains(CLOSE_CONDITION))
            throw new InvalidSyntaxException();
        if (line.indexOf(CLOSE_CONDITION)<line.indexOf(OPEN_CONDITION))
            throw new InvalidSyntaxException();
    }

    /**
     * Verifies that the type of a declaration appears once.
     * @param line the line we are checking
     * @throws InvalidSyntaxException is thrown for invalid syntax
     */
    private static void verifyTypeAppearsOnce(String line) throws InvalidSyntaxException
    {
        Pattern pattern = Pattern.compile(VariableType.getAllTypesRegex());
        Matcher matcher = pattern.matcher(line);
        int count = 0;
        while (matcher.find())
            count++;
        if (count!=1)
            throw new InvalidSyntaxException();

    }

    /**
     * "Cleans" the parameter string to make use easier
     * @param parametersString the "unclean" parameter string
     * @return the "clean" parameter string
     * @throws InvalidMethodException throws invalid method.
     */
    private static ArrayList<String> processParameterString(String parametersString) throws InvalidMethodException
    {
        String[] parameterData = parametersString.trim().split(WHITESPACE);
        ArrayList<String> cleanParameters = new ArrayList<>();
        for (String parameter : parameterData)
        {
            if (!parameter.equals(EMPTY_STRING))
                cleanParameters.add(parameter);
        }
        if (cleanParameters.size()!=2)
        {
            if (!(cleanParameters.size()==3 && cleanParameters.get(0).equals(FINAL)))
                throw new InvalidMethodException();
        }
        return cleanParameters;
    }



}