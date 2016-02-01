/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package google;

/**
 *
 * @author Lefteris Paraskevas
 */
public class Pair<E, V> {
    private E e;
    private V v;
    
    /**
     * Public constructor
     * @param start
     * @param end 
     */
    public Pair(E e, V v){
        this.e = e;
        this.v = v;
    }
    
    public E getE() { return e; }
    
    public V getV() { return v; }
    
    public void setE(E e) { this.e = e; }
    
    public void setV(V v) { this.v = v; }
}
