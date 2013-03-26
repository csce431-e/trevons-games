/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.Mancala;

import java.util.*;
/**
 *
 * @author Admin
 */

public class Pit {
    private ArrayList<Pebble> pebbles;
    
    Pit() {
        pebbles = new ArrayList<>();
    }
    
    Pit(int n) {
        pebbles = new ArrayList<>();
        for(int i =0; i<n; i++) {
            add(new Pebble());
        }
    }
    
    public void add(Pebble p){
        pebbles.add(p);
    }
    
    public Pebble remove(int i){
        return pebbles.remove(i);
    }
    
    public Pebble get(int i){
        return pebbles.get(i);
    }
    
    public int size() {
        return pebbles.size();
    }
}
