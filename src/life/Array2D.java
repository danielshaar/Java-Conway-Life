/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life;

/**
 *
 * @author dsizzle
 */
public class Array2D {

    /**Number of rows in the array*/
    private int numRows;
    /**Number of columns in the array*/
    private int numCols;
    /**Array that represents the two-dimensional array*/
    private int Array2D[];

    /**Constructor for a 2D array of integers
     * 
     * @param rows - the number of rows in the array
     * @param cols - the number of columns in the array
     */
    public Array2D(int rows, int cols) {
        numRows = rows;
        numCols = cols;
        Array2D = new int[numRows * numCols];
    }

    /**Returns the value of array [row,col]
     * 
     * @param row - the row number of the wanted coordinate 
     * @param col - the column number of the wanted coordinate
     * @return - the value at the wanted coordinate
     */
    public int getValue(int row, int col) {
        return Array2D[index(row, col)];
    }

    /**Returns the number of columns
     * 
     * @return - the number of columns 
     */
    public int numberColumns() {
        return numCols;
    }

    /**Returns the number of rows
     * 
     * @return - the number of rows 
     */
    public int numberRows() {
        return numRows;
    }

    /**Sets the value given a row and a col
     * 
     * @param row - the row number of the wanted coordinate
     * @param col - the column number of the wanted coordinate
     * @param value - the value to set at the wanted coordinate
     */
    public void setValue(int row, int col, int value) {
        Array2D[index(row, col)] = value;
    }

    /**Utility method that converts 2D array to 1D array
     * 
     * @param row - the row number of the coordinate to index
     * @param col - the column number of the coordinate to index
     * @return - the indexed value of the array
     */
    private final int index(int row, int col) {
        return row * numCols + col;
    }

    /**toString method that prints out 2D array
     * 
     * @return - a String representation of the 2D array 
     */
    @Override
    public String toString() {
        String output = "";
        String s = numRows + "*" + numCols + "\n\n";
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                output += Array2D[index(i, j)];
            }
            output += "\n";
        }
        return "\n" + s + output;
    }
}
