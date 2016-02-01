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
import java.util.Collections;

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
    private static final ArrayList<Triplet> servers = new ArrayList<>();
    private static final ArrayList<Pair<Integer, Integer>> unavailableSlots = new ArrayList<>();
    private static final ArrayList<ArrayList<Triplet>> serverRows = new ArrayList();

    public static final void readFile(String filename) {
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] temp;
            int index = 0;
            int serverID = 0;
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
                    Triplet triplet = new Triplet(Integer.parseInt(temp[0]),
                            Integer.parseInt(temp[1]), serverID);
                    servers.add(triplet);
                    serverID++;
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
        servers.stream().forEach((triplet) -> {
            System.out.println("Servers: " + triplet.getID() + " -> " 
                    + triplet.getSlots() + ", " + triplet.getCapacity());
        });
    }
    
    public final static void printResult() {
        serverRows.stream().map((list) -> {
            list.stream().forEach((triplet) -> {
                if(triplet.getSlots() == -1) {
                    System.out.print("-");
                } else if(triplet.getSlots() == -2) {
                    System.out.print("Χ");
                } else {
                    System.out.println(triplet.getID());
                }
            });
            return list;
        }).forEach((_item) -> {
            System.out.println("");
        });
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String path = "C:\\Users\\Lefteris\\Downloads\\dc.in";
        readFile(path);
        //print();
        
        
        for(int i = 0; i <numberOfRows; i++) {
            ArrayList<Triplet> tempList = new ArrayList<>();
            for(int j = 0; j < slotsPerRow; j++)
            {
                Triplet tempTriplet = new Triplet(-1,-1, -1); //-1 not used slot
                tempList.add(tempTriplet);
            }
            serverRows.add(tempList);
        }
        for(Pair<Integer, Integer> uvSlot : unavailableSlots)
        {
            ArrayList<Triplet> tempList = new ArrayList<>(
                    serverRows.get(uvSlot.getE()));
            tempList.set(uvSlot.getV(), new Triplet(-2, -2, -2)); //-2 unavailable slot
            serverRows.set(uvSlot.getE(), tempList);
        }
        int totalCapacity = 0;
        totalCapacity = servers.stream().map((triplet) -> triplet.getCapacity()).reduce(totalCapacity, Integer::sum);
        double avgCapacityPerRow = totalCapacity / numberOfRows;
        Collections.sort(servers, new CustomComparator());
        
        ArrayList<Integer> capacityPerRow = new ArrayList<>();
        for(int i = 0; i < capacityPerRow.size(); i++) {
            capacityPerRow.add(0);
        }
        for(Triplet triplet : servers) {
            int slots = triplet.getSlots();
            for(ArrayList<Triplet> list : serverRows) {
                int slotSeq = 0;
                int firstInSeq = 0;
                for(int i = 0; i < list.size(); i++) {
                    while(slotSeq < slots && list.get(i).getID() == -1) {
                        slotSeq++;
                    }
                    if(slotSeq == slots) {
                        for(int j = firstInSeq; j < slots; j++) {
                            list.set(j, triplet);
                        }
                    } else {
                        firstInSeq = i + 1;
                        slotSeq = 0;
                    }
                }
            }
        }
    }
    
}
