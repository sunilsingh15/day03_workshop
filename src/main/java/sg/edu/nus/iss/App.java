package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
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
                    pw.write(currentScanString + "\n");
                }

                // flush and close the FileWriter & PrintWriter objects
                pw.flush();
                pw.close();
                fw.close();
            }

            // a user must be logged in first
            // must perform the following first:
            // e.g. login <loginuser>
            if (input.equals("list")) {
                // need a File class and BufferedReader class to read the cart items from a file
                File readFile = new File(dirPath + File.separator + loginuser);
                BufferedReader br = new BufferedReader(new FileReader(readFile));
                
                String readFileInput = "";

                // reset the cartItems List collection
                cartItems = new ArrayList<String>();

                // while loop to read through all the items in the file
                while ((readFileInput = br.readLine()) != null) {
                    System.out.println(readFileInput);

                    cartItems.add(readFileInput);
                }

                // exit from while loop - close the BufferedReader class/object
                br.close();
            }

            if (input.startsWith("delete")) {
                // stringVal[0] --> "delete"
                // stringVal[1] --> index to delete from cartItems
                String [] stringVal = input.split(" ");

                // e.g. delete 2
                // removes 3rd item in the cartItems arrayList
                Integer deleteIndex = Integer.parseInt(stringVal[1]);
                if (deleteIndex <= cartItems.size()) {
                    cartItems.remove(deleteIndex);
                } else {
                    System.out.println("Index out of range error.");
                }

               // 1. open FileWriter and BufferedWriter
               FileWriter fw = new FileWriter(dirPath + File.separator + loginuser, false);
               BufferedWriter bw = new BufferedWriter(fw);

               // 2. loop to write cartItems to file
               int listIndex = 0;
               while (listIndex < cartItems.size()) {
                   bw.write(cartItems.get(listIndex));
                   bw.newLine();

                   listIndex++;
               }

               // 3. flush & close BufferedWriter and FileWriter
               bw.flush();
               bw.close();
               fw.close();

            }



        } // end of the while loop
    } // end of the main function
}
