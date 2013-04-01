package boardgames.checkers;

import java.util.*;
//import java.lang.Math;

/**
 *
 * @author Trevon
 */
public class CheckersCell {

    public enum RowType {

        ODD, EVEN, UNSET;
    }
    //Member variables
    private Owner owner;
    private RowType rowType;
    int x; //x position
    int y; //y position
    ArrayList< ArrayList< CheckersCell>> b = CheckersBoard.board;
    
    //Constructors
    public CheckersCell() {
        owner = Owner.EMPTY;
    }

    public CheckersCell(Owner o) {
        owner = o;
    }

    public CheckersCell(int xPos, int yPos) {
        x = xPos;
        y = yPos;
    }

    public CheckersCell(Owner o, int xPos, int yPos) {
        owner = o;
        x = xPos;
        y = yPos;
    }

    public CheckersCell(Owner o, RowType t) {
        owner = o;
        rowType = t;
    }

    public CheckersCell(CheckersCell c) {
        this.owner = c.owner;
        this.rowType = c.rowType;
        this.x = c.x;
        this.y = c.y;
    }

    //Getters and Setters
    public void setOwner(Owner o) {
        this.owner = o;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setRowType(RowType t) {
        this.rowType = t;
    }

    public RowType getRowType() {
        return rowType;
    }

    //Returns an array of the moves available from a given cell
    public ArrayList<CheckersMove> getMoves() {
        
        ArrayList<CheckersMove> moves = new ArrayList<>();
        
        int newX = 0;
        int newY = 0;

        if (this.owner == Owner.PLAYER2) {
            newX = this.x + 1;
        } else {
            newX = this.x - 1;
        }

        //Add left move
        newY = this.y - 1;
        CheckersCell newLocation1 = new CheckersCell();
        newLocation1.x = newX;
        newLocation1.y = newY;
        if(isValidCell(newLocation1))
        {
            newLocation1 = b.get(newLocation1.x).get(newLocation1.y);
            CheckersMove m1 = new CheckersMove(this, newLocation1);
            if (m1.isValidMove()) {
                moves.add(m1);
            }
        }
        //Add right move
        newY = this.y + 1;
        CheckersCell newLocation2 = new CheckersCell();
        newLocation2.x = newX;
        newLocation2.y = newY;
        if(isValidCell(newLocation2))
        {
            newLocation2 = b.get(newLocation2.x).get(newLocation2.y);
            CheckersMove m2 = new CheckersMove(this, newLocation2);
            if (m2.isValidMove()) {
                moves.add(m2);
            }
        }
        
        //Player must make a jump if one is available
       
        
        return moves;
        
    }

    public CheckersCell getMid(CheckersCell src, CheckersCell dest) {
        int xPos = (src.x + dest.x) / 2;
        int yPos = (src.y + dest.y) / 2;
        CheckersCell mid = b.get(xPos).get(yPos);
        return mid;
    }

    public ArrayList<CheckersJump> getJumps() {
        
        ArrayList<CheckersJump> jumps = new ArrayList();
        
        Owner opponent = owner.opposite();
        if(opponent== Owner.EMPTY)
        {
            return new ArrayList();
        }
        
        ArrayList<CheckersCell> jumpDest = new ArrayList();
        jumpDest.add(new CheckersCell(this.x + 2, this.y + 2));
        jumpDest.add(new CheckersCell(this.x - 2, this.y + 2));
        jumpDest.add(new CheckersCell(this.x + 2, this.y - 2));
        jumpDest.add(new CheckersCell(this.x - 2, this.y - 2));

       
        //delete destination cells that are off the board
        Iterator<CheckersCell> it = jumpDest.iterator();
        while (it.hasNext()) {
            if (!isValidCell(it.next())) {
                it.remove();
            }
        }
        
        //add jumps that have the correct opponent
        it = jumpDest.iterator();
        while (it.hasNext())
        {
            CheckersCell temp = it.next();
            CheckersCell jump = b.get(temp.x).get(temp.y);
            CheckersCell mid = getMid(this, jump);
            if(mid.owner == opponent)
            {
                CheckersJump j = new CheckersJump(this,mid,jump);
                if(j.isValidJump())
                {
                    jumps.add(j);
                }
            }
        }
        return jumps;
    }

    public static boolean isValidCell(CheckersCell c) {

        if (c.x < 0 || c.x >= 8) {
            return false;
        }
        if (c.y < 0 || c.y >= 8) {
            return false;
        }

        return true;
    }
    //Checks if moving between two cells is valid

    

    //makes the calling cell equal to the parameter c
    public void updateCell(CheckersCell c) {
        this.owner = c.owner;
        this.rowType = c.rowType;
        this.x = c.x;
        this.y = c.y;
    }
    
    public boolean equals(CheckersCell c)
    {
        if(!this.owner.equals(c.owner))
        {
            return false;
        }
        if(this.x != c.x || this.y != c.y)
        {
            return false;
        }
        return true;
    }

    //Convert to string for console play
    @Override
    public String toString() {
        if (owner == Owner.EMPTY) {
            return "~";
        } else if (owner == Owner.PLAYER1) {
            return "B";
        } else {
            return "R";
        }
    }
}
