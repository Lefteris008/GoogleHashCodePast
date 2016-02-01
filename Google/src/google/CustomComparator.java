package google;


import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lefteris Paraskevas
 */
public class CustomComparator implements Comparator<Triplet> {
    @Override
    public int compare(Triplet o1, Triplet o2) {
        if ((o2.getSlotStrength() + o2.getSlots()) < (o1.getSlotStrength() + o1.getSlots())) return -1;
        if ((o2.getSlotStrength() + o2.getSlots()) > (o1.getSlotStrength() + o1.getSlots())) return 1;
        return 0;
    }
}
