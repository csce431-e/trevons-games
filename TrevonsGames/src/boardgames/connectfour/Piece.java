/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.connectfour;

/**
 *
 * @author Jonathan
 */
public class Piece {
    private int color; // 0 for empty, 1 for black, 2 for red

    Piece(){
        color = 0;
    }
    
    Piece(int col) {
        color = col;
    }
    
    void setColor(int col) {
        color = col;
    }
    
    int pieceColor() {
        return color;
    }
}
