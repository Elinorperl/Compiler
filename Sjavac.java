package oop.ex6.main;

import oop.ex6.parser.FileParser;

import java.io.File;
import java.io.IOException;

/**
 * The main class that takes the input arguments and sends them to our file parser to parse.
 */
public class Sjavac {

    private static final int IO_ERROR = 2;

    /**
     * The main function that sends our file to parsing.
     * @param args the input arguments.
     */
    public static void main (String [] args)
    {
        try {
            File file = new File(args[0]);
            FileParser.parseFile(file);

        }
        catch (IOException e) {
            System.out.println(IO_ERROR);
        }

    }
}
