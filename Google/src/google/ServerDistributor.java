/*
 * Copyright (C) 2016   Sofia Agathangelou, Lefteris Paraskevas, 
                        Panos Petridis, Xenia Zlati
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package google;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author  Sofia Agathangelou
 * @author  Lefteris Paraskevas
 * @author  Panos Petridis
 * @author  Xenia Zlati
 * @version 2016.02.02_1234
 */
public class ServerDistributor {
    
    private static int numberOfRows;
    private static int slotsPerRow;
    private static int numberOfUnavailableSlots;
    private static int numberOfPools;
    private static int numberOfServers;
    private static final ArrayList<Triplet> servers = new ArrayList<>();
    private static final ArrayList<Pair<Integer, Integer>> unavailableSlots = new ArrayList<>();
    private static final ArrayList<ArrayList<Triplet>> serverRows = new ArrayList();

    /**
     * Auxiliary method to read the input file.
     * @param filename A String containing the path to the input file.
     */
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
        } catch (FileNotFoundException e) {
            Logger.getLogger(ServerDistributor.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(ServerDistributor.class.getName()).log(Level.SEVERE, null, e);
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
    
    /**
     * Method to print all retrieved data from the input file, along with
     * the proposed distribution of servers.
     */
    public final static void printResult() {
        int totalUnavailable = 0;
        for(ArrayList<Triplet> list : serverRows) {
            for(Triplet triplet : list) {
                if(triplet.getSlots() == -1) {
                    System.out.print("-");
                } else if(triplet.getSlots() == -2) {
                    System.out.print("Χ");
                    totalUnavailable++;
                } else {
                    System.out.print(triplet.getID() + "*");
                }
            }
            System.out.println();
        }
        System.out.println(totalUnavailable);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //String path = "C:\\Users\\Lefteris\\Downloads\\dc.in";
        String path = args[0];
        readFile(path); //Read the input file
        
        for(int i = 0; i <numberOfRows; i++) {
            ArrayList<Triplet> tempList = new ArrayList<>();
            for(int j = 0; j < slotsPerRow; j++)
            {
                Triplet tempTriplet = new Triplet(-1,-1, -1); //-1 not used slot
                tempList.add(tempTriplet);
            }
            serverRows.add(tempList);
        }
        unavailableSlots.stream().forEach((uvSlot) -> {
            ArrayList<Triplet> tempList = new ArrayList<>(
                    serverRows.get(uvSlot.getE()));
            tempList.set(uvSlot.getV(), new Triplet(-2, -2, -2)); //-2 unavailable slot
            serverRows.set(uvSlot.getE(), tempList);
        });
        int totalCapacity = 0;
        totalCapacity = servers.stream().map((triplet) -> triplet.getCapacity()).reduce(totalCapacity, Integer::sum);
        double avgCapacityPerRow = totalCapacity / numberOfRows;
        Collections.sort(servers, new CustomComparator());
        
        ArrayList<Integer> capacityPerRow = new ArrayList<>();
        for(int i = 0; i < capacityPerRow.size(); i++) {
            capacityPerRow.add(0);
        }
        boolean assigned = false;
        int assignedServers = 0;
        int nextAvailableRow = 0;
        for(Triplet triplet : servers) {
            int slots = triplet.getSlots();
            int firstInSeq = 0;
            for(int rowCounter = nextAvailableRow; rowCounter < serverRows.size(); rowCounter++) {
                ArrayList<Triplet> list = serverRows.get(rowCounter);
                int slotSeq = 0;
                for(int slotCounter = 0; slotCounter < list.size(); slotCounter++) {
                    while((slotSeq < slots) && 
                            (list.get(slotCounter).getID() == -1) && 
                            ((firstInSeq + slots) <= list.size())) {
                        slotSeq++;
                    }
                    if(slotSeq == slots && list.get(slotCounter).getID() != -2) {
                        for(int auxCounter = firstInSeq; 
                                auxCounter < (slots + firstInSeq); auxCounter++) {
                            list.set(auxCounter, triplet);
                        }
                        assigned = true;
                        nextAvailableRow = rowCounter + 1;
                        if(nextAvailableRow == serverRows.size()) {
                            nextAvailableRow = 0;
                        }
                        break;
                    } else {
                        firstInSeq = slotCounter + 1;
                        slotSeq = 0;
                    }
                    slotCounter++;
                }
                if(assigned) { //If the resource is assigned
                    assignedServers++;
                    assigned = false;
                    break;
                }
            }
        }
        printResult();
        System.out.println("Optimal capacity: " + avgCapacityPerRow);
        serverRows.stream().map((list) -> {
            int rowSummary = 0;
            rowSummary = list.stream().map((triplet) -> 
                    triplet.getCapacity() / triplet.getSlots())
                    .reduce(rowSummary, Integer::sum);
            return rowSummary;
        }).forEach((rowSummary) -> {
            System.out.println("Capaicty: " + rowSummary);
        });
        System.out.println("Assigned servers: " + assignedServers);
        System.out.println("Total servers: " + servers.size());
        
        int totalSlots = 0;
        totalSlots = servers.stream().map((triplet) -> triplet.getSlots())
                .reduce(totalSlots, Integer::sum);
        System.out.println("Slots: " + totalSlots);
    }
    
}
