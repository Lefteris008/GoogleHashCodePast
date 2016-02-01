/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package google;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Lefteris Paraskevas
 */
public class Google {
    
    private static int numberOfRows;
    private static int slotsPerRow;
    private static int numberOfUnavailableSlots;
    private static int numberOfPools;
    private static int numberOfServers;
    private static final ArrayList<Pair<Integer, Integer>> servers = new ArrayList<>();
    private static final ArrayList<Pair<Integer, Integer>> unavailableSlots = new ArrayList<>();

    public static final void readFile(String filename) {
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] temp;
            int index = 0;
            for(String line; (line = br.readLine()) != null; ) {
                temp = line.split(" ");
                if(index == 0) {
                   numberOfRows = Integer.parseInt(temp[0]);
                   slotsPerRow = Integer.parseInt(temp[1]);
                   numberOfUnavailableSlots = Integer.parseInt(temp[2]);
                   numberOfPools = Integer.parseInt(temp[3]);
                   numberOfServers = Integer.parseInt(temp[4]);
                } else if(index < numberOfUnavailableSlots) {
                    Pair<Integer, Integer> pair = new Pair<>(Integer.parseInt(temp[0]),
                            Integer.parseInt(temp[1]));
                    unavailableSlots.add(pair); 
                } else if(index < numberOfServers) {
                    Pair<Integer, Integer> pair = new Pair<>(Integer.parseInt(temp[0]),
                            Integer.parseInt(temp[1]));
                    servers.add(pair);
                }
                index++;
            }
            // line is not visible here.
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            
        }
    }
    
    public static final void print() {
        System.out.println("Number of rows: " + numberOfRows);
        System.out.println("Number of pools: " + numberOfPools);
        System.out.println("Number of servers: " + numberOfServers);
        System.out.println("Number of unavailable slots: " + numberOfUnavailableSlots);
        System.out.println("Slots per row: " + slotsPerRow);
        unavailableSlots.stream().forEach((pair) -> {
            System.out.println("Unavailable slots: " + pair.getE() + ", " + pair.getV());
        });
        servers.stream().forEach((pair) -> {
            System.out.println("Servers: " + pair.getE() + ", " + pair.getV());
        });
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String path = "C:\\Users\\Lefteris\\Downloads\\dc.in";
        readFile(path);
        print();
    }
    
}
