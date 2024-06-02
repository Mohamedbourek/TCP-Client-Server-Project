/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EXO1;

import java.io.*;
import java.util.*;

public class A {

    public static void main(String args[]) throws IOException {
        HashMap<String, Integer> map = new HashMap<>();
        try (Scanner a = new Scanner(new File("./src/EXO1/symptomes.txt"))) {
            while (a.hasNextLine()) {
                ajouter(map, a.nextLine());
            }
        }
    }

    static void ajouter(Map<String, Integer> map, String symptome) {
        map.compute(symptome, (key, value) -> (value == null) ? 1 : value + 1);
    }

}
