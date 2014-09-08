/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life;

import java.awt.Color;
import java.util.*;

/**
 *
 * @author dsizzle
 */
public class ColonyController {

    /**Scans information from user*/
    private Scanner scan = new Scanner(System.in);
    /**Saver and Reader for colonies*/
    private ColonySaverAndReader csr = new ColonySaverAndReader();
    /**Used to call methods in colony class*/
    private Colony colony;
    /**frame for the colony*/
    private ColonyFrame frame;
    /**Array of timers*/
    private Timer t[] = new Timer[10];
    /**The delay between evolutions*/
    private int delay[] = new int[10];
    /**Array of colonies*/
    private Colony c[] = new Colony[10];
    /**Array of frames*/
    private ColonyFrame f[] = new ColonyFrame[10];
    /**Array of timers*/
    private EvolveTask e[] = new EvolveTask[10];
    /**Number of colonies created*/
    private int coloniesCreated = 0;
    /**Current colony number in use*/
    private int currentColony = 0;
    /**Holds the name of the saving file*/
    private String saving;
    /**Holds option parts from user*/
    private String optionParts[] = new String[3];
    /**Holds option parts used to set information about a colony*/
    private int optionForSetting[] = {15, 15};

    /**Initializes a standard Colony, takes options, and quits the program*/
    public void drive_Loop() {
        System.out.println("Welcome to the game of life!");
        createColony();
        setColony(0);
        printOptions();
        char option = getOption();
        while (option != 'q') {
            doOption(option);
            option = getOption();
        }
        System.exit(0);
    }

    /**Prints out the generic table of options*/
    public void printOptions() {
        System.out.println("\nOptions for the game of life");
        System.out.println("\nq - quit");
        System.out.println("\n\nc numberRows numberColumns- create colony with numberRows and numberColumns");
        System.out.println("u n - use colony number n");
        System.out.println("a xcoor ycoor - set the cell at position (xcoor, ycoor) to be alive");
        System.out.println("d xcoor ycoor - set the cell at postition (xcoor, ycoor) to be dead");
        System.out.println("e numGen - evolve the colony by numGen generations");
        System.out.println("r - reset the colony by setting all cells to be dead and changing wrap to off and colors to be reset");
        System.out.println("l - list of colonies");
        System.out.println("p - print current colony");
        System.out.println("h - print this help table of options");
        System.out.println("y (1/2) - print out table 1) color and evolve settings, 2) Arrangements and file settings");
    }

    /**Prints out the table of options for color and evolve settings*/
    public void printColorOptions() {
        System.out.println("i (1-13) (1-13) - color combos (live cells, dead cells)");
        System.out.println("1) black\n2) blue\n3) cyan\n4) dark gray\n5) gray\n6) green (this will conflict with the grid)\n7) light gray\n8) magenta\n9) orange\n10) pink\n11) red\n12) white\n13) yellow");
        System.out.println("k - regular color combo");
        System.out.println("j n - set delay to n miliseconds");
        System.out.println("m 0/1 - set pixel on with 1 and off with 0");
    }

    /**Prints out the table of options for arrangement and file settings*/
    public void printArrangementOptions() {
        System.out.println("o n - generate random number of cells making up n% of the colony");
        System.out.println("r - reset the colony by setting all cells to be dead and changing wrap to off and colors to be reset");
        System.out.println("s fileName - save the current colony to disk to a file by the name of fileName");
        System.out.println("n fileName - get the colony from disk from a file by the name of fileName");
        System.out.println("x fileName - delete the colony by the name of fileName from disk");
        System.out.println("b 0/1 - turns grid on with 1 and off with 0");
        System.out.println("t (1-6) - 1) glider gun, 2) pulsar, 3) LWSS, 4) Glider, 5) T, 6) Hearts and Faces(must have 20 by 15 colony)");
        System.out.println("w 0/1 - set wrapping on with 1 and off with 0");
    }

    /**Returns the option wanted by the user
     *
     * @return - the option to execute
     */
    public char getOption() {
        System.out.print("Option...");
        String fullOption = scan.nextLine();
        optionParts = fullOption.split(" ");
        char option = fullOption.charAt(0);
        return option;
    }

    /**Performs the option that the user requested
     *
     * @param g - the option the user requested
     */
    public void doOption(char g) {
        switch (g) {
            //prints out other tables
            case 'y':
                if (optionParts.length == 2) {
                    optionForSetting[0] = Integer.parseInt(optionParts[1]);
                    if (optionForSetting[0] == 1) {
                        printColorOptions();
                    } else if (optionForSetting[0] == 2) {
                        printArrangementOptions();
                    } else {
                        System.err.println("Invalid input!");
                    }
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //creates a new colony
            case 'c':
                if (optionParts.length == 3) {
                    optionForSetting[0] = Integer.parseInt(optionParts[1]);
                    optionForSetting[1] = Integer.parseInt(optionParts[2]);
                    createColony();
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //sets the current colony
            case 'u':
                if (optionParts.length == 2) {
                    optionForSetting[0] = Integer.parseInt(optionParts[1]);
                    setColony(optionForSetting[0]);
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //sets raandom cells alive
            case 'o':
                if (optionParts.length == 2) {
                    optionForSetting[0] = Integer.parseInt(optionParts[1]);
                    randomize();
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //sets a cell alive
            case 'a':
                if (optionParts.length == 3) {
                    optionForSetting[0] = Integer.parseInt(optionParts[1]);
                    optionForSetting[1] = Integer.parseInt(optionParts[2]);
                    setAlive();
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //sets a cell dead
            case 'd':
                if (optionParts.length == 3) {
                    optionForSetting[0] = Integer.parseInt(optionParts[1]);
                    optionForSetting[1] = Integer.parseInt(optionParts[2]);
                    setDead();
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //evolves a colony
            case 'e':
                if (optionParts.length == 2) {
                    optionForSetting[0] = Integer.parseInt(optionParts[1]);
                    evolve();
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //resets a colony and its settings
            case 'r':
                reset();
                break;
            //saves the current colony to disk
            case 's':
                if (optionParts.length == 2) {
                    saving = optionParts[1];
                    save();
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //retrieves a colony from disk
            case 'n':
                if (optionParts.length == 2) {
                    saving = optionParts[1];
                    getColony();
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //deletes a colony from disk
            case 'x':
                if (optionParts.length == 2) {
                    saving = optionParts[1];
                    deleteColony();
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //sets the color combination
            case 'i':
                if (optionParts.length == 3) {
                    optionForSetting[0] = Integer.parseInt(optionParts[1]);
                    optionForSetting[1] = Integer.parseInt(optionParts[2]);
                    color();
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //resets the color combination to the default
            case 'k':
                resetColor();
                break;
            //turns wrapping on and off
            case 'w':
                if (optionParts.length == 2) {
                    optionForSetting[0] = Integer.parseInt(optionParts[1]);
                    setWrap();
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //sets a pixel border or turns it off
            case 'm':
                if (optionParts.length == 2) {
                    optionForSetting[0] = Integer.parseInt(optionParts[1]);
                    setPixelBorder();
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //makes a popular arrangement for the colony
            case 't':
                if (optionParts.length == 2) {
                    optionForSetting[0] = Integer.parseInt(optionParts[1]);
                    shapesForColony();
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //sets the delay between evolutions
            case 'j':
                if (optionParts.length == 2) {
                    optionForSetting[0] = Integer.parseInt(optionParts[1]);
                    setDelay();
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //turns on and off a green grid
            case 'b':
                if (optionParts.length == 2) {
                    optionForSetting[0] = Integer.parseInt(optionParts[1]);
                    setGrid();
                } else {
                    System.err.println("Invalid input!");
                }
                break;
            //lists the colonies and their properties
            case 'l':
                listColonies();
                break;
            //prints out a colony with some properties
            case 'p':
                printColony();
                break;
            //prints out the generic help table
            case 'h':
                printOptions();
                break;
            default:
                System.err.println("Invalid Option!");
        }
    }

    /**Creates a new colony*/
    public void createColony() {
        if (optionForSetting[0] < 4 || optionForSetting[1] < 4 || optionForSetting[0] > 100 || optionForSetting[1] > 100) {
            System.err.println("Invalid Colony Dimensions!");
        } else if (coloniesCreated <= c.length) {
            c[coloniesCreated] = new Colony(optionForSetting[0], optionForSetting[1]);
            f[coloniesCreated] = new ColonyFrame(c[coloniesCreated]);
            e[coloniesCreated] = new EvolveTask(f[coloniesCreated], c[coloniesCreated], 1, t[coloniesCreated]);
            delay[coloniesCreated] = 150;
            coloniesCreated++;
            setColony(coloniesCreated - 1);
        } else {
            System.err.println("Maximum number of colonies have been created.");
        }
    }

    /**Sets the colony number that the user wants to work with
     * 
     * @param colonyNumber - colony number to set as current colony 
     */
    public void setColony(int colonyNumber) {
        int colonyWanted = colonyNumber;
        if (colonyWanted >= coloniesCreated) {
            System.err.println("Unavailable Colony.");
        } else if (colonyWanted > c.length) {
            System.err.println("Colony does not exist");
        } else {
            currentColony = colonyWanted;
            colony = c[currentColony];
            frame = f[currentColony];
        }
    }

    /**Sets a cell alive*/
    public void setAlive() {
        if (optionForSetting[0] >= c[currentColony].getColonySizeNumRows() || optionForSetting[1] >= c[currentColony].getColonySizeNumCols()) {
            System.err.println("Invalid Point!");
        } else {
            colony.setCellAlive(optionForSetting[0], optionForSetting[1]);
            frame.repaint();
        }
    }

    /**Kills a cell*/
    public void setDead() {
        if (optionForSetting[0] > c[currentColony].getColonySizeNumRows() || optionForSetting[1] > c[currentColony].getColonySizeNumCols()) {
            System.err.println("Invalid Point!");
        } else {
            colony.setCellDead(optionForSetting[0], optionForSetting[1]);
            frame.repaint();
        }
    }

    /**Evolves a colony a certain amount of generations*/
    public void evolve() {
        e[currentColony].start(frame, colony, optionForSetting[0], delay[currentColony]);
    }

    /**Resets a requested colony*/
    public void reset() {
        colony.resetColony();
        colony.setWrap(false);
        resetColor();
        frame.repaint();
    }

    /**Makes a random cell arrangement*/
    public void randomize() {
        reset();
        if (optionForSetting[0] <= 100 && optionForSetting[0] >= 0) {
            while (colony.getNumberLivingCells() != (int) (.01 * optionForSetting[0] * colony.getColonySizeNumCols() * colony.getColonySizeNumRows())) {
                colony.setCellAlive((int) (Math.random() * colony.getColonySizeNumRows()), (int) (Math.random() * colony.getColonySizeNumCols()));
            }
            frame.repaint();
        } else {
            System.err.println("Invalid!");
        }
    }

    /**Prints out a list of colonies with the number of living cells and the current generation number of each colony*/
    public void listColonies() {
        System.out.println("\nColony List:\n\n\tCells\tgen#");
        for (int i = 0; i < c.length; i++) {
            if (i == currentColony) {
                System.out.println("*" + i + ":\t" + c[i].getNumberLivingCells() + "\t" + c[i].getGenerationNumber());
            } else if (i < coloniesCreated) {
                System.out.println(i + ":\t" + c[i].getNumberLivingCells() + "\t" + c[i].getGenerationNumber());
            } else {
                System.out.println(i + ":\tnull");
            }
        }
    }

    /**Prints out current colony*/
    public void printColony() {
        frame.repaint();
        System.out.println("Colony number: " + currentColony + "\nGeneration: " + colony.getGenerationNumber() + "\nNumber of living cells: " + colony.getNumberLivingCells());
    }

    /**Sets the color combination*/
    public void color() {
        setColorForLive();
        setColorForDead();
        frame.repaint();
    }

    /**Sets the color for the live cells*/
    private void setColorForLive() {
        if (optionForSetting[0] == 1) {
            frame.setAliveCellColor(Color.BLACK);
        } else if (optionForSetting[0] == 2) {
            frame.setAliveCellColor(Color.BLUE);
        } else if (optionForSetting[0] == 3) {
            frame.setAliveCellColor(Color.CYAN);
        } else if (optionForSetting[0] == 4) {
            frame.setAliveCellColor(Color.DARK_GRAY);
        } else if (optionForSetting[0] == 5) {
            frame.setAliveCellColor(Color.GRAY);
        } else if (optionForSetting[0] == 6) {
            frame.setAliveCellColor(Color.GREEN);
        } else if (optionForSetting[0] == 7) {
            frame.setAliveCellColor(Color.LIGHT_GRAY);
        } else if (optionForSetting[0] == 8) {
            frame.setAliveCellColor(Color.MAGENTA);
        } else if (optionForSetting[0] == 9) {
            frame.setAliveCellColor(Color.ORANGE);
        } else if (optionForSetting[0] == 10) {
            frame.setAliveCellColor(Color.PINK);
        } else if (optionForSetting[0] == 11) {
            frame.setAliveCellColor(Color.RED);
        } else if (optionForSetting[0] == 12) {
            frame.setAliveCellColor(Color.WHITE);
        } else if (optionForSetting[0] == 13) {
            frame.setAliveCellColor(Color.YELLOW);
        } else {
            System.err.println("Invalid");
        }
    }

    /**Sets the color for the dead cells*/
    private void setColorForDead() {
        if (optionForSetting[1] == 1) {
            frame.setDeadCellColor(Color.BLACK);
        } else if (optionForSetting[1] == 2) {
            frame.setDeadCellColor(Color.BLUE);
        } else if (optionForSetting[1] == 3) {
            frame.setDeadCellColor(Color.CYAN);
        } else if (optionForSetting[1] == 4) {
            frame.setDeadCellColor(Color.DARK_GRAY);
        } else if (optionForSetting[1] == 5) {
            frame.setDeadCellColor(Color.GRAY);
        } else if (optionForSetting[1] == 6) {
            frame.setDeadCellColor(Color.GREEN);
        } else if (optionForSetting[1] == 7) {
            frame.setDeadCellColor(Color.LIGHT_GRAY);
        } else if (optionForSetting[1] == 8) {
            frame.setDeadCellColor(Color.MAGENTA);
        } else if (optionForSetting[1] == 9) {
            frame.setDeadCellColor(Color.ORANGE);
        } else if (optionForSetting[1] == 10) {
            frame.setDeadCellColor(Color.PINK);
        } else if (optionForSetting[1] == 11) {
            frame.setDeadCellColor(Color.RED);
        } else if (optionForSetting[1] == 12) {
            frame.setDeadCellColor(Color.WHITE);
        } else if (optionForSetting[1] == 13) {
            frame.setDeadCellColor(Color.YELLOW);
        } else {
            System.err.println("Invalid");
        }
    }

    /**Sets the default color combination*/
    public void resetColor() {
        frame.setAliveCellColor(Color.BLACK);
        frame.setDeadCellColor(Color.WHITE);
        frame.repaint();
    }

    /**Makes an arrangement for the colony*/
    public void shapesForColony() {
        if (optionForSetting[0] == 1) {
            makeGliderGun();
        } else if (optionForSetting[0] == 2) {
            makePulsar();
        } else if (optionForSetting[0] == 3) {
            makeSpaceShip();
        } else if (optionForSetting[0] == 4) {
            makeGlider();
        } else if (optionForSetting[0] == 5) {
            makeT();
        } else if (optionForSetting[0] == 6) {
            makeHeartsFaces();
        } else {
            System.err.println("Invalid!");
        }
    }

    /**Makes the glider gun*/
    private void makeGliderGun() {
        if (colony.getColonySizeNumRows() > 26 && colony.getColonySizeNumCols() > 39) {
            reset();
            colony.setCellAlive(1, 25);
            colony.setCellAlive(2, 23);
            colony.setCellAlive(2, 25);
            colony.setCellAlive(3, 13);
            colony.setCellAlive(3, 14);
            colony.setCellAlive(3, 21);
            colony.setCellAlive(3, 22);
            colony.setCellAlive(3, 36);
            colony.setCellAlive(3, 35);
            colony.setCellAlive(4, 12);
            colony.setCellAlive(4, 16);
            colony.setCellAlive(4, 21);
            colony.setCellAlive(4, 22);
            colony.setCellAlive(4, 35);
            colony.setCellAlive(4, 36);
            colony.setCellAlive(5, 1);
            colony.setCellAlive(5, 2);
            colony.setCellAlive(5, 11);
            colony.setCellAlive(5, 17);
            colony.setCellAlive(5, 21);
            colony.setCellAlive(5, 22);
            colony.setCellAlive(6, 1);
            colony.setCellAlive(6, 2);
            colony.setCellAlive(6, 11);
            colony.setCellAlive(6, 15);
            colony.setCellAlive(6, 17);
            colony.setCellAlive(6, 18);
            colony.setCellAlive(6, 23);
            colony.setCellAlive(6, 25);
            colony.setCellAlive(7, 11);
            colony.setCellAlive(7, 17);
            colony.setCellAlive(7, 25);
            colony.setCellAlive(8, 12);
            colony.setCellAlive(8, 16);
            colony.setCellAlive(9, 13);
            colony.setCellAlive(9, 14);
            frame.repaint();
        } else {
            System.err.println("Requires a colony of minimum dimensions 27 x 40");
        }
    }

    /**Makes the pulsar*/
    private void makePulsar() {
        if (colony.getColonySizeNumRows() > 14 && colony.getColonySizeNumCols() > 14) {
            reset();
            colony.setCellAlive(1, 3);
            colony.setCellAlive(1, 4);
            colony.setCellAlive(1, 5);
            colony.setCellAlive(1, 9);
            colony.setCellAlive(1, 10);
            colony.setCellAlive(1, 11);
            colony.setCellAlive(3, 1);
            colony.setCellAlive(3, 6);
            colony.setCellAlive(3, 8);
            colony.setCellAlive(3, 13);
            colony.setCellAlive(4, 1);
            colony.setCellAlive(4, 6);
            colony.setCellAlive(4, 8);
            colony.setCellAlive(4, 13);
            colony.setCellAlive(5, 1);
            colony.setCellAlive(5, 6);
            colony.setCellAlive(5, 8);
            colony.setCellAlive(5, 13);
            colony.setCellAlive(6, 3);
            colony.setCellAlive(6, 4);
            colony.setCellAlive(6, 5);
            colony.setCellAlive(6, 9);
            colony.setCellAlive(6, 10);
            colony.setCellAlive(6, 11);
            colony.setCellAlive(8, 3);
            colony.setCellAlive(8, 4);
            colony.setCellAlive(8, 5);
            colony.setCellAlive(8, 9);
            colony.setCellAlive(8, 10);
            colony.setCellAlive(8, 11);
            colony.setCellAlive(9, 1);
            colony.setCellAlive(9, 6);
            colony.setCellAlive(9, 8);
            colony.setCellAlive(9, 13);
            colony.setCellAlive(10, 1);
            colony.setCellAlive(10, 6);
            colony.setCellAlive(10, 8);
            colony.setCellAlive(10, 13);
            colony.setCellAlive(11, 1);
            colony.setCellAlive(11, 6);
            colony.setCellAlive(11, 8);
            colony.setCellAlive(11, 13);
            colony.setCellAlive(13, 3);
            colony.setCellAlive(13, 4);
            colony.setCellAlive(13, 5);
            colony.setCellAlive(13, 9);
            colony.setCellAlive(13, 10);
            colony.setCellAlive(13, 11);
            frame.repaint();
        } else {
            System.err.println("Requires a colony of minimum dimensions 15 x 15");
        }
    }

    /**Makes the small space ship*/
    private void makeSpaceShip() {
        if (colony.getColonySizeNumRows() > 7 && colony.getColonySizeNumCols() > 15) {
            reset();
            colony.setWrap(true);
            colony.setCellAlive(0, 0);
            colony.setCellAlive(0, 3);
            colony.setCellAlive(1, 4);
            colony.setCellAlive(2, 0);
            colony.setCellAlive(2, 4);
            colony.setCellAlive(3, 1);
            colony.setCellAlive(3, 2);
            colony.setCellAlive(3, 3);
            colony.setCellAlive(3, 4);
            frame.repaint();
        } else {
            System.err.println("Requires a colony of minimum dimensions 8 x 16");
        }
    }

    /**MAkes the glider*/
    private void makeGlider() {
        reset();
        colony.setWrap(true);
        colony.setCellAlive(0, 0);
        colony.setCellAlive(0, 1);
        colony.setCellAlive(0, 2);
        colony.setCellAlive(2, 1);
        colony.setCellAlive(1, 2);
        frame.repaint();
    }

    /**Makes the T shape*/
    private void makeT() {
        if (colony.getColonySizeNumRows() > 12 && colony.getColonySizeNumCols() > 12) {
            reset();
            colony.setCellAlive(5, 5);
            colony.setCellAlive(5, 6);
            colony.setCellAlive(5, 7);
            colony.setCellAlive(6, 6);
            frame.repaint();
        } else {
            System.err.println("Requires colony of minimum dimensions 13 x 13");
        }
    }

    /**Makes the special arrangement of hearts and faces*/
    private void makeHeartsFaces() {
        if (colony.getColonySizeNumRows() == 20 && colony.getColonySizeNumCols() == 15) {
            reset();
            colony.setWrap(true);
            colony.setCellAlive(8, 6);
            colony.setCellAlive(8, 8);
            colony.setCellAlive(8, 5);
            colony.setCellAlive(8, 9);
            colony.setCellAlive(9, 5);
            colony.setCellAlive(9, 9);
            colony.setCellAlive(10, 6);
            colony.setCellAlive(10, 7);
            colony.setCellAlive(10, 8);
            colony.setCellAlive(11, 7);
            frame.repaint();
        } else {
            System.err.println("Requires colony of dimensions 20 x 15");
        }
    }

    /**Sets the delay between evolutions*/
    public void setDelay() {
        if (optionForSetting[0] < 2000 && optionForSetting[0] > 0) {
            delay[currentColony] = optionForSetting[0];
        } else {
            System.err.println("Unreasonable time delay");
        }
    }

    /**Sets the grid*/
    public void setGrid() {
        boolean grid = false;
        if (optionForSetting[0] == 1) {
            System.out.println("grid mode on!");
            grid = true;
        } else if (optionForSetting[0] == 0) {
            System.out.println("grid mode off!");
            grid = false;
        } else {
            System.err.println("Invalid!");
        }
        frame.setGrid(grid);
        frame.repaint();
    }

    /**Sets the pixel grid*/
    public void setPixelBorder() {
        boolean grid = false;
        if (optionForSetting[0] == 1) {
            System.out.println("pixel border mode on!");
            grid = true;
        } else if (optionForSetting[0] == 0) {
            System.out.println("pixel border mode off!");
            grid = false;
        } else {
            System.err.println("Invalid!");
        }
        frame.setBorderCellColor(Color.WHITE);
        frame.setGrid(grid);
        frame.repaint();
    }

    /**Saves the colony to disk*/
    public void save() {
        csr.saveString(colony.toString(), saving);
        System.out.println("File saved");
    }

    /**Gets a colony from disk*/
    public void getColony() {
        if (coloniesCreated < c.length) {
            c[coloniesCreated] = csr.turnStringToColony(saving);
            f[coloniesCreated] = new ColonyFrame(c[coloniesCreated]);
            e[coloniesCreated] = new EvolveTask(f[coloniesCreated], c[coloniesCreated], 1, t[coloniesCreated]);
            delay[coloniesCreated] = 150;
            coloniesCreated++;
        } else {
            System.err.println("All colonies are filled!");
        }
    }

    /**Deletes a colony from disk*/
    public void deleteColony() {
        csr.deleteColony(saving);
    }

    /**Sets the wrapping mode*/
    public void setWrap() {
        boolean wrapSet = false;
        if (optionForSetting[0] == 1) {
            System.out.println("wrap mode on!");
            wrapSet = true;
        } else if (optionForSetting[0] == 0) {
            System.out.println("wrap mode off!");
            wrapSet = false;
        } else {
            System.err.println("Invalid!");
        }
        colony.setWrap(wrapSet);
    }

    /**Creates a ColonyViewController and runs its drive_Loop method
     *
     * @param args - necessary to run the code
     */
    public static void main(String[] args) {
        ColonyController cvc = new ColonyController();
        cvc.drive_Loop();
    }
}
