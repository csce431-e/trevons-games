package boardgames.pegSolitaire;

import java.util.*;

public class SolitaireCoordinate {
	
	int x;
	int y;
	
	boolean filled;
	boolean onBoard;
	
	SolitaireCoordinate()
        {
            x = 0;
            y = 0;
            filled = true;
            onBoard = true;
        }

        SolitaireCoordinate(SolitaireCoordinate c)
        {
            x = (c.x);
            y = (c.y);
            filled = c.filled;
            onBoard = c.onBoard;
        }

        SolitaireCoordinate(int a, int b, boolean f, boolean o)
        {
            x = a;
            y = b;
            filled = f;
            onBoard = o;
        }
        
        @Override
        public boolean equals(Object other){
            if (other == null) 
            {
                return false;
            }
            if (other == this) 
            {
                return true;
            }
            if (!(other instanceof SolitaireCoordinate))
            {
                return false;
            }
            SolitaireCoordinate otherMyClass = (SolitaireCoordinate)other;
            
            if(otherMyClass.x == x && otherMyClass.y == y && otherMyClass.filled == filled && otherMyClass.onBoard==onBoard)
            {
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 89 * hash + this.x;
            hash = 89 * hash + this.y;
            hash = 89 * hash + (this.filled ? 1 : 0);
            return hash;
        }

        ArrayList<SolitaireCoordinate> getTwoSpacesAway(SolitaireBoard b)
        {
            ArrayList<SolitaireCoordinate> dests = new ArrayList<>();
            if(y-2>=-3 && b.getCoordinate(x,y-2).onBoard)//down
            {
                dests.add(b.getCoordinate(x,y-2));
            }
            if(y+2<=3 && b.getCoordinate(x,y+2).onBoard)//up
            {
                dests.add(b.getCoordinate(x, y + 2));
            }
            if(x-2>=-3 && b.getCoordinate(x-2,y).onBoard)//left
            {
                dests.add(b.getCoordinate(x - 2, y));
            }
            if(x+2<=3 && b.getCoordinate(x+2,y).onBoard)//right
            {
                dests.add(b.getCoordinate(x + 2, y));
            }
            if(y-2>=-3 && x+2<=3 && b.getCoordinate(x+2,y-2).onBoard)//down right
            {
                dests.add(b.getCoordinate(x + 2, y - 2));
            }
            if(y-2>=-3 && x-2>=-3 && b.getCoordinate(x-2,y-2).onBoard)//dwn left
            {
                dests.add(b.getCoordinate(x - 2, y - 2));
            }
            if(y+2<=3 && x+2<=3 && b.getCoordinate(x+2,y+2).onBoard)//up right
            {
                dests.add(b.getCoordinate(x + 2, y + 2));
            }
            if(y+2<=3 && x-2>=-3 && b.getCoordinate(x-2,y+2).onBoard)//up left
            {
                 dests.add(b.getCoordinate(x - 2, y + 2));
            }
            
            return dests;
        }

        ArrayList<SolitaireMove> getJumpsSrc(SolitaireBoard b)
        {
            ArrayList<SolitaireMove> jumps = new ArrayList<>();

            ArrayList<SolitaireCoordinate> dests = getTwoSpacesAway(b);
            for(int i = 0; i<dests.size(); i++)
            {
                SolitaireCoordinate middle = this.canJump(b, dests.get(i));
                if(middle != null)
                {
                    SolitaireMove m = new SolitaireMove(this, dests.get(i), middle);
                    jumps.add(m);
                }
            }

            return jumps;
        }

        ArrayList<SolitaireMove> getJumpsDest(SolitaireBoard b)
        {
            ArrayList<SolitaireMove> jumps = new ArrayList<>();

            ArrayList<SolitaireCoordinate> srcs = getTwoSpacesAway(b);
            for (int i = 0; i < srcs.size(); i++)
            {
                SolitaireCoordinate middle = srcs.get(i).canJump(b, this);
                if (middle != null)
                {
                    SolitaireMove m = new SolitaireMove(srcs.get(i), this, middle);
                    jumps.add(m);
                }
            }

            return jumps;
        }

        int max(int a, int b)
        {
            if(a>b)
            {
                return a;
            }
            return b;
        }
        int min(int a, int b)
        {
            if(a<b)
            {
                return a;
            }
            return b;
        }
	
	    //finds out the direction of the jump
         int getJumpType(SolitaireCoordinate dest)
        {
                //right
            if(this.x+2==dest.x && dest.y == this.y)
            {
                return 0;
            }
            //left
            if(this.x-2==dest.x && dest.y == this.y)
            {
                return 1;
            }
            //up
            if(this.y+2==dest.y && dest.x == this.x)
            {
                return 2;
            }
            //down
            if(this.y-2==dest.y && dest.x == this.x)
            {
                return 3;
            }
                //upright
            if (this.y + 2 == dest.y && this.x + 2 == dest.x)
            {
                return 4;
            }
            //upleft
            if (this.y + 2 == dest.y && this.x - 2 == dest.x)
            {
                return 5;
            }
            //downright
            if (this.y - 2 == dest.y && this.x + 2 == dest.x)
            {
                return 6;
            }
            //downleft
            if (this.y - 2 == dest.y && this.x - 2 == dest.x)
            {
                return 7;
            }

            //err: should never happen tho
            //System.out.println("Err: in board getJumpType() function: shouldn't happen but it did");
            return -1;
        }
	
	    //checks if the src(this) can jump to the dest
        SolitaireCoordinate canJump(SolitaireBoard b, SolitaireCoordinate dest) //returns the middle piece (that has a peg in it) if the requested move is possible; if not returns null
        {
            if(dest.onBoard && this.onBoard)
            {
                SolitaireCoordinate middlePeg;

                int jumpType = this.getJumpType(dest);

                //vertical (x's are the same)
                if(jumpType == 2 || jumpType == 3)
                {
                    int dif = max(this.y,dest.y)-1;
                    middlePeg = b.getCoordinate(this.x,dif);
                    if(middlePeg.filled && middlePeg.onBoard && this.filled && dest.filled == false)
                    {
                            //System.out.println(middlePeg.x + " " + middlePeg.y);
                        return middlePeg;
                    }
                    else
                    {
                        return null;
                    }
                }

                //horizontal (y's are the same)
                else if(jumpType == 0 || jumpType == 1)
                {
                    int dif = max(this.x,dest.x)-1;
                    middlePeg = b.getCoordinate(dif,this.y);
                    if(middlePeg.filled && middlePeg.onBoard && this.filled && dest.filled == false)
                    {
                            //System.out.println(middlePeg.x + " " + middlePeg.y);
                        return middlePeg;
                    }
                    else
                    {
                        return null;
                    }
                }
                //upright/downleft
                else if(jumpType == 4 || jumpType == 7)
                {
                    int difx = max(this.x,dest.x)-1;
                    int dify = max(this.y,dest.y)-1;
                    middlePeg = b.getCoordinate(difx,dify);
                    if(middlePeg.filled && middlePeg.onBoard && this.filled && dest.filled == false)
                    {
                        return middlePeg;
                    }
                    else
                    {
                        return null;
                    }
                }

                //upleft/downright
                else if(jumpType == 5 || jumpType == 6)
                {
                    int difx = max(this.x,dest.x)-1;
                    int dify = max(this.y,dest.y)-1;
                    middlePeg = b.getCoordinate(difx,dify);
                    if(middlePeg.filled && middlePeg.onBoard && this.filled && dest.filled == false)
                    {
                        return middlePeg;
                    }
                    else
                    {
                        return null;
                    }
                }

                return null;
	    }
            
            return null;
        }
	
	 public String toString()
	{
		if(onBoard == false)
		{
			return "~";
		}
		else if(filled)
		{
			return "X";
		}
		return "O";
	}

}
