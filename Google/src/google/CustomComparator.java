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


import java.util.Comparator;

/**
 * @author  Sofia Agathangelou
 * @author  Lefteris Paraskevas
 * @author  Panos Petridis
 * @author  Xenia Zlati
 * @version 2016.02.02_1242
 */
public class CustomComparator implements Comparator<Triplet> {
    @Override
    public int compare(Triplet o1, Triplet o2) {
        if ((o2.getSlotStrength() + o2.getSlots()) < (o1.getSlotStrength() + o1.getSlots())) return -1;
        if ((o2.getSlotStrength() + o2.getSlots()) > (o1.getSlotStrength() + o1.getSlots())) return 1;
        return 0;
    }
}
