import java.util.Scanner;
import java.util.Random;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

// class RoundRecord
class RoundRecord
{
    int catRoom; // This creates a record with in variables
    int mouseRoom;
    int cheeseRoom;
}
// End class RoundRecord

class game
{
    public static void main(String[] a) throws IOException
    {
        playGame(); // Change this to a call to the method doing the work
        return;
    }
    // END main

    // inputString Code
    public static String inputString(String message)
    {
        String answer;
        // This method will be used when a user input in needed.
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        answer = scanner.nextLine();
        return answer;
    }
    // End inputString

    // printLine code
    public static void printLine()
    {
        System.out.println("-------------------------------------");
        // This prints a line
        return;
    }
    // END printLine

    // createRecord Code
    public static RoundRecord createRecord(int c, int m, int ch)
    {
        RoundRecord r = new RoundRecord();
        r.catRoom = c;
        r.mouseRoom = m; // creates a record
        r.cheeseRoom = ch;
        return r;
    }
    // END createRecord

    // getCat code
    public static int getCat(RoundRecord r)
    {
        return r.catRoom;
    }
    // end getCat

    // getMouse Code
    public static int getMouse(RoundRecord r)
    {
        return r.mouseRoom;
    }
    // These methods are used to print the variable
    // End getMouse

    // getCheese Code
    public static int getCheese(RoundRecord r)
    {
        return r.cheeseRoom;
    }
    // end getCheese

    // roll code
    public static int roll(String name, int round)
    {
        // This method randomly rolls a number from 1 - 10 and assigns it to a room
        Random r = new Random();
        int room = r.nextInt(10) + 1;
        System.out.println("\n" + name + " - ROUND " + round);
        System.out.println("Room: " + room);
        String ans = inputString("Reroll? (Y/N)"); // User gets chance to repeat
        while (!(ans.equalsIgnoreCase("Y") || ans.equalsIgnoreCase("N")))
        {
            ans = inputString("Invalid. Type Y or N:");
        }
        while (ans.equalsIgnoreCase("Y"))
        {
            room = r.nextInt(10) + 1;
            System.out.println("Room: " + room);
            ans = inputString("Reroll? (Y/N)");
            while (!(ans.equalsIgnoreCase("Y") || ans.equalsIgnoreCase("N")))
            {
                ans = inputString("Invalid. Type Y or N:");
            }
        }
        return room;
    }
    // End roll

    // randomRoom code
    public static int randomRoom()
    {
        Random r = new Random();
        return r.nextInt(10) + 1; // random number for cheese room
    }
    // END randomRoom

    // createRounds code
    public static void createRounds(int[] catA, int[] mouseA, int[] cheeseA)
    {
        int i;
        for (i = 0; i < 4; i++)
        {
            int cat = roll("Cat", i + 1); // rounds are created for the game
            int mouse = roll("Mouse", i + 1);
            int cheese = randomRoom();
            // Create ADT then unpack into arrays
            RoundRecord r = createRecord(cat, mouse, cheese);
            catA[i] = getCat(r);
            mouseA[i] = getMouse(r);
            cheeseA[i] = getCheese(r);
        }
        return;
    }
    // End createRounds

    // showResults Code
    public static void showResults(int[] catA, int[] mouseA, int[] cheeseA)
    {
        int catScore = 0;
        int mouseScore = 0;
        printLine();
        System.out.println("RESULTS");
        printLine();
        int i;
        for (i = 0; i < 4; i++)
        {
            System.out.println("\nRound " + (i + 1));
            System.out.println("Cat: " + catA[i] + " | Mouse: " + mouseA[i] + " | Cheese: " + cheeseA[i]);
            if (mouseA[i] == cheeseA[i])
                mouseScore += 10;
            else if (catA[i] == mouseA[i])
                catScore += 10;
            else
            {
                mouseScore += 5;
                catScore += 5;
            }
        }
        printLine();
        System.out.println("Cat: " + catScore);
        System.out.println("Mouse: " + mouseScore);
        if (catScore > mouseScore)
            System.out.println("Winner: CAT");
        else if (mouseScore > catScore)
            System.out.println("Winner: MOUSE");
        else
            System.out.println("DRAW");
        return; // prints the winner at the end
    }
    // End showResults

    // saveGame code
    public static void saveGame(int[] catA, int[] mouseA, int[] cheeseA) throws IOException
    {
        String filename = "save.txt";
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        int i;
        for (i = 0; i < 4; i++)
        {
            out.println(catA[i] + "," + mouseA[i] + "," + cheeseA[i]);
        }
        out.close();
        System.out.println("Game saved.");
        return;
    }
    // END saveGame

    // loadGame code
    public static void loadGame(int[] catA, int[] mouseA, int[] cheeseA) throws IOException
    {
        String filename = "save.txt";
        BufferedReader in = new BufferedReader(new FileReader(filename));
        int i;
        for (i = 0; i < 4; i++)
        {
            String line = in.readLine();
            String[] parts = line.split(",");
            catA[i] = Integer.parseInt(parts[0]);
            mouseA[i] = Integer.parseInt(parts[1]);
            cheeseA[i] = Integer.parseInt(parts[2]);
        }
        in.close();
        // Print loaded file (screenshot style)
        System.out.println("Loaded Saved Game:");
        int j;
        for (j = 0; j < 4; j++)
        {
            System.out.println(catA[j] + "\t" + mouseA[j] + "\t" + cheeseA[j]);
        }
        return;
    }
    // END loadGame

    // playGame code
    public static void playGame() throws IOException
    {
        printLine();
        System.out.println("CAT AND MOUSE GAME");
        printLine();
        String choice = inputString("Enter 1 for NEW GAME or 2 for LOAD GAME:");
        while (!(choice.equals("1") || choice.equals("2")))
        {
            choice = inputString("Invalid. Enter 1 or 2:");
        }
        // PARALLEL ARRAYS - NOT array of records
        int[] catRooms = new int[4];
        int[] mouseRooms = new int[4];
        int[] cheeseRooms = new int[4];
        if (choice.equals("2"))
        {
            loadGame(catRooms, mouseRooms, cheeseRooms);
        }
        else
        {
            createRounds(catRooms, mouseRooms, cheeseRooms);
        }
        saveGame(catRooms, mouseRooms, cheeseRooms);
        showResults(catRooms, mouseRooms, cheeseRooms);
        return;
    }
    // END playGame

} // End game
