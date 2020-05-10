package oop.ex6.codeelements;

import oop.ex6.parser.AssignmentCall;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class to define variables in a program.
 */
public class Variable {

    private final static String QUOTATION_MARK = "\"";
    private final static String APOSTROPHE = "\'";
    private static final String TRUE = "true";
    private final static String FALSE = "false";
    private final static char MINUS = '-';
    private final static String VARIABLENAME = "[_]{1}\\w{1,}|[a-zA-Z]{1}\\w{0,}";


    private VariableType variableType;
    private String name;
    private String value;
    private boolean isFinal;
    private Scope scope;

    /**
     * @param name the name to check is valid
     * @throws InvalidVariableNameException is thrown when variable name is invalid
     */
    public static void checkValidName(String name) throws InvalidVariableNameException
    {
        Pattern pattern = Pattern.compile(VARIABLENAME);
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches())
            throw new InvalidVariableNameException();
    }

    /**
     * A getter function - gets the variable in our scope
     * @param variableName the name of the variable we'd like to get.
     * @param scope our current scope
     * @return returns the variable in our scope
     * @throws UndeclaredVariableException
     */
    public static Variable getVariable(String variableName, Scope scope) throws UndeclaredVariableException
    {
        Scope varScope = Scope.searchScopeOfVariable(variableName,scope);
        if (varScope == null)
            return null;
        return varScope.getVariable(variableName);
    }

    /**
     * A function that checks if the input value is a valid variable.
     * @param value value to be checked
     * @return returns true if the value is valid, false otherwise.
     */
    public static boolean checkValueIsVariable (String value) {
        if (value.startsWith(QUOTATION_MARK) && value.endsWith(QUOTATION_MARK))
            return false;
        if (value.startsWith(APOSTROPHE) && value.endsWith(APOSTROPHE))
            return false;
        if (value.equals(TRUE) || value.equals(FALSE))
            return false;
        if (Character.isDigit(value.charAt(0)) || (value.charAt(0)==(MINUS) && Character.isDigit(value.charAt(1))))
            return false;
        return true;
    }

    /**
     * A variable constructor.
     * @param variableType variable variableType according the the 5 predesignated types.
     * @param name variable name.
     * @param value the given value for the variable
     * @param isFinal a boolean indicating whether the variable is final.
     * @param scope the scope in which our variable occurs.
     * @throws InvalidTypeException when the variable has been assigned an invalid type.
     * @throws InvalidVariableNameException is thrown when the variable has been given an invalid name
     * @throws UndeclaredVariableException is thrown when the variable has been undeclared
     * @throws FinalAssigmentException is thrown when a final assignment has been reassigned
     * @throws IncompatibleValueTypeException is thrown when the value and type are incompatible.
     */
    public Variable(VariableType variableType, String name, String value, boolean isFinal, Scope scope)
            throws InvalidTypeException, InvalidVariableNameException, UndeclaredVariableException,
            FinalAssigmentException, IncompatibleValueTypeException {
        this.variableType = variableType;
        this.name = name;
        this.isFinal = isFinal;
        this.scope = scope;
        checkValidName();
        setValue(value);
        if (isFinal && value == null)
            throw new FinalAssigmentException();
    }

    /**
     * A function that checks if the variable name is valid.
     * @throws InvalidVariableNameException
     */
    private void checkValidName() throws InvalidVariableNameException
    {
        checkValidName(this.name);
    }

    /**
     * Checks that the variable assignment is valid according to the global and local variables.
     * @param variableValue the value we're checking.
     * @throws UndeclaredVariableException is thrown when the variable has been undeclared
     */
     private void checkVariableAssigmentAsValueIsValid(String variableValue) throws UndeclaredVariableException {
         Variable assignerVariable = getVariable(variableValue, scope);
         if (assignerVariable!=null) {
             if (assignerVariable.value != null)
                 this.value = assignerVariable.value;
             else {
                 // if both variables are global and one is not assigned, an error should be thrown
                 boolean assignerIsGlobal = Scope.getRoot() == assignerVariable.scope;
                 if (this.scope==Scope.getRoot() && assignerIsGlobal)
                     throw new UndeclaredVariableException();
                 // if a local variable is assigned as a value but it is not initialized, an error should be thrown
                 if (!assignerIsGlobal)
                     throw new UndeclaredVariableException();
                 /** if a global variable is assigned but not initialized to a local variable,
                  * checks should be performed when running over all lines */
                 else
                     new AssignmentCall(assignerVariable, this, null, scope);
             }
         }
         else {
             if (this.scope==Scope.getRoot())
                throw new UndeclaredVariableException();
             else
                 new AssignmentCall(null,this,variableValue,scope);
         }
        }

    /**
     * @return gets the value of the variable.
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * @return gets the variable name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return gets the variable type
     */
    public VariableType getVariableType() {
        return this.variableType;
    }

    /**
     * @return returns true if variable is final, false otherwise
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     *  A setter function - sets the value to a new value.
     * @param value the value we'd like to change
     * @throws UndeclaredVariableException is thrown when variable is undeclared
     * @throws IncompatibleValueTypeException -is thrown when type and a value are incompatible
     */
    public void setValue(String value) throws UndeclaredVariableException, IncompatibleValueTypeException {
        if (value == null || value.equals(""))
            this.value = value;
        else if (checkValueIsVariable(value)) {
            checkVariableAssigmentAsValueIsValid(value);
            Variable variable = Variable.getVariable(value,this.scope);
            if (variable==null)
                this.value = null;
            else
                this.value = variable.value;
        }
        else {
            //value is a value, not a variable
            if (!this.variableType.checkValue(value))
                throw new IncompatibleValueTypeException();
            this.value = value;
        }
    }
}

