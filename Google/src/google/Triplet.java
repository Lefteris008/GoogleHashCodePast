/*
 * To change this license header, choose License Integereaders in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package google;

/**
 *
 * @author Lefteris Paraskevas
 */
public class Triplet {
    private Integer slots;
    private Integer capacity;
    private Integer id;
    
    /**
     * Public constructor
     * @param start
     * @param end 
     */
    public Triplet(Integer slots, Integer capacity, Integer id){
        this.capacity = capacity;
        this.id = id;
        this.slots = slots;
    }
    
    public Integer getSlots() { return slots; }
    
    public Integer getCapacity() { return capacity; }
    
    public Integer getID() { return id; }
    
    public double getSlotStrength() { return (capacity / slots); }
    
    public void setSlots(Integer slots) { this.slots = slots; }
    
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    
    public void setId(Integer id) { this.id = id; }
}
