package src;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class IoMan {
    private Scanner scanner = new Scanner(System.in);

    void selectFromMenu(final List<UserDatum> userData) {
        System.out.println("Select from the following options:");
        int entryNum = 0;
        for (UserDatum userDatum : userData) {
            System.out.println("[" + entryNum + "] " + userDatum.getKey());
            entryNum++;
        }
        System.out.println("[" + entryNum +"] Exit");
        Integer indexOfChosenItem = Integer.parseInt(scanner.nextLine());
        if (entryNum!=indexOfChosenItem) {
            System.out.println(userData.get(indexOfChosenItem).getValue());
            selectFromMenu(userData);
        }
    }

    UserDatum requestUserDatum(String key) {
        System.out.println("Please Enter your "+key);
        return new UserDatum(key,scanner.nextLine());
    }
}
