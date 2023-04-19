package sg.edu.nus.iss;

import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        // Assign variable to argument
        String dirPath = args[0];

        // Use File class to check if directory exists
        // Create directory if it does not exist
        File newDirectory = new File(dirPath);
        if (newDirectory.exists()) {
            System.out.println("Directory " + newDirectory + " already exists.");
        } else {
            newDirectory.mkdir();
        }

        // Initial message shown to user
        System.out.println("Welcome to your shopping cart");

        // List collection to store the cart items of logged-in user
        List<String> cartItems = new ArrayList<String>();

        // use Console class to read input from keyboard
        Console con = System.console();
        String input = "";

        // used to keep track of current logged-in user
        // this is also used as the file name to store user's cart items
        String loginuser = "";

        // exit the while loop if keyboard input equals to quit
        while (!input.equals("quit")) {
            input = con.readLine("What do you want to do? > ");

            if (input.startsWith("login")) {
                Scanner scan = new Scanner(input.substring(5));

                while (scan.hasNext()) {
                    loginuser = scan.next();                                        
                }

                // check if the file <loginuser> exists
                // if it doesn't exist, create a new file or override

                File loginFile = new File(dirPath + File.separator + loginuser);
                if (loginFile.exists()) {
                    System.out.println("File " + loginuser + " already exists");                    
                } else {
                    loginFile.createNewFile();
                }
            }

            if (input.equals("users")) {
                File directoryPath = new File(dirPath);

                String[] directoryListing = directoryPath.list();
                System.out.println("List of files and directories in the specific folder " + dirPath);

                for (String dirList : directoryListing) {
                    System.out.println(dirList);
                }
            }

            if (input.startsWith("add")) {
                input = input.replace(',', ' ');
                
                // use FileWriter & PrintWriter to write to a <loginuser> file
                FileWriter fw = new FileWriter(dirPath + File.separator + loginuser, false);
                PrintWriter pw = new PrintWriter(fw);

                String currentScanString = "";
                Scanner inputScanner = new Scanner(input.substring(4));
                while (inputScanner.hasNext()) {
                    currentScanString = inputScanner.next();
                    cartItems.add(currentScanString);

                    // write to file using PrintWriter
                    pw.write("\n" + currentScanString);
                }

                // flush and close the FileWriter & PrintWriter objects
                pw.flush();
                pw.close();
                fw.close();
            }
        }

    }
}
