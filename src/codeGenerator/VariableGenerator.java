package codeGenerator;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class VariableGenerator {

    private static Set<Integer> set = new LinkedHashSet<Integer>();

    public static String generaNomeVariabile() {
        Random randNum = new Random();
        String varName = "";

        while (set.size() < 1000) {
            int numRandom = randNum.nextInt(1000) + 1;
            if (set.add(numRandom)) {
                varName += "a"+ numRandom;
                break;
            }
        }
        return varName;
    }
}
