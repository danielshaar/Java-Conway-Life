/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life;

import java.io.*;

/**
 *
 * @author dsizzle
 */
public class ColonySaverAndReader {

    /**Saves a string to disk
     * 
     * @param s - the string to be saved to disk
     * @param fileNumber - the file number to call the text file
     */
    public void saveString(String s, String fileName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName + ".col"));
            out.write(s);
            out.close();
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    /**Gets a string from disk
     * 
     * @param fileNumber - the file number to get that file from disk
     * @return - the colony read from disk 
     */
    private String getString(String fileName) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(fileName + ".col"));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String realColony = "";
            String colonyString;
            colonyString = br.readLine();
            realColony += colonyString;

            while ((colonyString = br.readLine()) != null) {
                realColony += "\n";
                realColony += colonyString;
            }

            in.close();
            return realColony;

        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return "Error";
    }

    /**Turns the string from disk into a colony
     * 
     * @param fileNumber - the file number to get that file from disk
     * @return - the colony from disk
     */
    public Colony turnStringToColony(String fileName) {
        String lines[] = (getString(fileName)).split("\n");
        int numRows = lines.length;
        int numCols = lines[0].length();
        Colony c = new Colony(numRows, numCols);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (lines[i].charAt(j) == '*') {
                    c.setCellAlive(i, j);
                }
            }
        }
        return c;
    }

    /**Deletes one of the text files from disk
     * 
     * @param fileNumber - the file number of the file to delete
     */
    public void deleteColony(String fileName) {
        File fileToDelete = new File(fileName + ".col");
        boolean fileDeleted = fileToDelete.delete();
        if (fileDeleted) {
            System.out.println("File deleted successfully");
        } else {
            System.err.println("No such file exists to be deleted");
        }
    }
}
