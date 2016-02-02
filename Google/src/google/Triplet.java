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

/**
 * @author  Sofia Agathangelou
 * @author  Lefteris Paraskevas
 * @author  Panos Petridis
 * @author  Xenia Zlati
 * @version 2016.02.02_1242
 */
public class Triplet {
    private int slots;
    private int capacity;
    private int id;
    
    /**
     * Public constructor
     * @param slots
     * @param capacity
     * @param id
     */
    public Triplet(int slots, int capacity, int id){
        this.capacity = capacity;
        this.id = id;
        this.slots = slots;
    }
    
    public int getSlots() { return slots; }
    
    public int getCapacity() { return capacity; }
    
    public int getID() { return id; }
    
    public double getSlotStrength() { return (capacity / slots); }
    
    public void setSlots(int slots) { this.slots = slots; }
    
    public void setCapacity(int capacity) { this.capacity = capacity; }
    
    public void setId(int id) { this.id = id; }
}
