import java.util.ArrayList;

public class PracTwo {

    private static class  Profession {
        String name;
        int salary;

        Profession(String name, int salary) {
            this.name = name;
            this.salary = salary;
        }
    }


    public static void main(String[] args) {
        System.out.println("Test");

        int[] salaries = {24000, 34000, 40000, 21000, 18000};
        String[] profession = {"nurse", "teacher", "Architect", "Bus Driver", "Postman"};



        int count = 0;
        for (int salary : salaries) {
            if (salary> 20000) {
                count++;
            }
        }
        System.out.println(count);
        int maxSal = -1;
        String maxProf = "";
        for (int i = 0; i < salaries.length; i++) {
            if (salaries[i] > maxSal) {
                maxSal = salaries[i];
                maxProf = profession[i];
            }
        }
        System.out.println(maxProf);

        ArrayList<Profession> profs = new ArrayList();
        for (int i = 0; i < salaries.length; i++) {
            profs.add(new Profession(profession[i], salaries[i]));
        }

        final var profession1 = highestVal(profs);
        System.out.println(profession1.name +"\tSalary:\t"+profession1.salary);

    }


    private static  Profession highestVal(ArrayList<Profession> profs) {
        Profession maxSal = null;
        for (Profession prof : profs) {
            if (maxSal == null || maxSal.salary < prof.salary) {
                maxSal = prof;
            }
        }
        return maxSal;
    }
}
