package oop.ex6.codeelements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An enum class that defines the 5 types of variables our program can compile.
 */

public enum VariableType {

    INT ("int", RegexForTypes.REGEXINTERGER),
    DOUBLE ("double", RegexForTypes.REGEXDOUBLE),
    STRING("String", RegexForTypes.REGEXSTRING),
    BOOLEAN ("boolean", RegexForTypes.REGEXBOOLEAN),
    CHAR("char", RegexForTypes.REGEXCHAR) ;


    private static class RegexForTypes {

        private static final String REGEXINTERGER = "-?[\\d]+";
        private static final String REGEXDECIMAL = "-?[\\d]+\\.+[\\d]+";
        private static final String REGEXDOUBLE = REGEXINTERGER + "|" + REGEXDECIMAL;
        private static final String REGEXBOOLEAN = REGEXDOUBLE + "|" + "true|false";
        private static final String REGEXSTRING = "\"[^>]*\"";
        private static final String REGEXCHAR = "'[^>]{1}'";

    }

    /**
     * Creates a regex for all types, making the code more extensible
     * @return a string of regex types
     */
    public static String getAllTypesRegex()
    {
        String regexAlternatives = "(";
        for (VariableType variableType : VariableType.values())
            regexAlternatives += variableType.getName()+"|";
        regexAlternatives = regexAlternatives.substring(0,regexAlternatives.length()-1)+")";
        return regexAlternatives;
    }

    /**
     * Chooses the type of a value.
     * @param value the value we'd like to extract type.
     * @return type of value.
     */
    public static VariableType chooseTypeByName(String value) throws InvalidTypeException {
        for (VariableType variableType : VariableType.values()) {
            if (value.equals(variableType.getName()))
                return variableType;
        }
        throw new InvalidTypeException ();
    }

    /**
     * @param line the line to check
     * @return true if the line starts with an existing variable name, false otherwise.
     */
    public static boolean startsWithVariableTypeName (String line)
    {
        for (VariableType variableType : VariableType.values())
        {
            if (line.startsWith(variableType.name))
                return true;
        }
        return false;
    }


    private final String name;
    private final String valuePattern;


    /**
     * VariableType constructor that takes the name of our type and the pattern it understands.
     * @param name -  the type's name
     * @param valuePattern - the type's regex value pattern.
     */
        VariableType(String name, String valuePattern) {
            this.name = name;
            this.valuePattern = valuePattern;
        }

    /**
     * A getter function for our type's name.
     * @return the type name.
     */
    public String getName() {
        return name;
    }

    /**
     * A getter function for our types regex pattern.
     * @return the value pattern
     */
    public String getValuePattern() {
        return valuePattern;
    }

    /**
     * A function that checks that the input value matches our type values intake.
     * @param value - the value to be checked.
     * @return true if the value matches the value pattern of its type.
     * @throws IncompatibleValueTypeException is thrown when value and type don't match
     */
    public boolean checkValue(String value) throws IncompatibleValueTypeException {
        Pattern pattern = Pattern.compile(valuePattern);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

}
