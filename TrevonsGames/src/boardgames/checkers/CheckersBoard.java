/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.checkers;

import java.util.*;
/**
 *
 * @author Trevon
 */
public class CheckersBoard {
    
    /* 
     * The board alternates between light and dark cells for every row. 
     * The bottom right cell is always light and pieces live on dark squares.
     * The indexing starts at the top and Player1 is at the bottom. 
     */
    public static int BOARDSIZE = 8;
    public ArrayList< ArrayList<CheckersCell> > board = new ArrayList<>();
    public CheckersGame currentGame;
    public boolean anotherJump;
    public boolean gameOver;
    public ArrayList<CheckersCell> p1Pieces;
    public ArrayList<CheckersCell> p2Pieces;
    
    public void storePlayerPieces()
    {
        p1Pieces = Owner.PLAYER1.pieces;
        p2Pieces = Owner.PLAYER2.pieces;
    }
    
    public void clearPlayerPieces()
    {
        Owner.PLAYER1.pieces.clear();
        Owner.PLAYER2.pieces.clear();
    }
    
    public void loadPlayerPieces()
    {
        CheckersCell temp = new CheckersCell(Owner.PLAYER1, -1, -1, this); 
        ArrayList<CheckersCell> tempContainer = new ArrayList<CheckersCell>();
        tempContainer.add(temp);
        
        Owner.PLAYER1.pieces.add(temp);
        Owner.PLAYER1.pieces.retainAll(tempContainer);

        Owner.PLAYER2.pieces.add(temp);
        Owner.PLAYER2.pieces.retainAll(tempContainer);
        
        if(p1Pieces!=null)
        {
            Owner.PLAYER1.pieces = p1Pieces;
        }
        if(p2Pieces!=null)
        {
            Owner.PLAYER2.pieces = p1Pieces;
        }
    }
    
    public CheckersBoard()
    {
        board.clear();
        if(Owner.PLAYER1.pieces.size() == 0)
        {
                //Create nxn board
            for(int i = 0; i < BOARDSIZE; i++)
            {
                ArrayList<CheckersCell> currentRow = new ArrayList<>();
                for(int j =0; j< BOARDSIZE; j++) 
                {
                    currentRow.add(new CheckersCell(Owner.EMPTY,i,j,this));
                }

                board.add(currentRow);
            }

            //Place 12 pieces on board for player 1
            for(int i = 0; i<3; i++)
            {
                boolean playerCell;

                if(i%2==0)
                {
                    playerCell = false;
                }
                else
                {
                    playerCell = true;
                }

                for(CheckersCell cell: board.get(i))
                {
                    if(playerCell) 
                    {
                        cell.setOwner(Owner.PLAYER2);
                        Owner.PLAYER2.pieces.add(cell);
                    }
                    playerCell ^= true;
                }
            }

            //Place 12 pieces on board for player 2
            for(int i = 5; i<8; i++)
            {
                boolean playerCell;

                if(i%2==0)
                {
                    playerCell = false;
                }
                else
                {
                    playerCell = true;
                }

                for(CheckersCell cell: board.get(i))
                {
                    if(playerCell) 
                    {
                        cell.setOwner(Owner.PLAYER1);
                        Owner.PLAYER1.pieces.add(cell);
                    }
                    playerCell ^= true;
                }
            }

            anotherJump = false;
            p1Pieces = Owner.PLAYER1.pieces;
            p2Pieces = Owner.PLAYER2.pieces;
            Owner.PLAYER1.setBoard(this);
            Owner.PLAYER2.setBoard(this);
            //printBoard();
        }
        else
        {
            board = Owner.PLAYER1.gameBoard.board;
        }
    }
    
    public void initGame(CheckersGame g)
    {
        currentGame = g;
    }
    
    public void printBoard()
    {
        System.out.print("  ");
        for(int i=0;i<BOARDSIZE;i++)
        {
            System.out.print(i + " ");
        }
        System.out.print("\n");
        for(ArrayList<CheckersCell> row: board)
        {
            System.out.print(board.indexOf(row) + " ");
            for(CheckersCell cell: row)
            {
                System.out.print(cell.toString() + " ");
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }
    
    /*public boolean jump(CheckersMove move)
    {
        CheckersCell s = board.get(move.source.y).get(move.source.x);
        CheckersCell m = board.get(move.middle.y).get(move.middle.x);
        CheckersCell d = board.get(move.dest.y).get(move.dest.x);
        
        Owner opp = s.getOwner();
        s.setOwner(Owner.EMPTY);
        m.setOwner(Owner.EMPTY);
        d.setOwner(opp);
        
        return false;
    }*/
    public ArrayList<CheckersMove> getAllMoves()
    {
        ArrayList<CheckersJump> j = getJumpMoves();
        if(j.size() > 0)
        {
            ArrayList<CheckersMove> jumpMoves = new ArrayList();
            for(CheckersJump currentJump: j)
            {
                jumpMoves.add((CheckersMove)currentJump);
            }
            return jumpMoves;
        }
        else
        {
            return getNormalMoves();
        }
    }
    
    public ArrayList<CheckersJump> getJumpMoves()
    {
        Owner o = currentGame.turn;
        
        ArrayList<CheckersJump> jumps = new ArrayList();
        for(CheckersCell c: o.pieces)
        {
            jumps.addAll(c.getJumps());
        }
        
        return jumps;   
    }
    
    public ArrayList<CheckersMove> getNormalMoves()
    {
        Owner o = currentGame.turn;
        
        /*ArrayList<CheckersMove> jumps = new ArrayList();
        for(CheckersCell c: o.pieces)
        {
            jumps.addAll(c.getJumps());
        }
        
        if(jumps.size() > 0)
        {
            return jumps;
        }*/
        
        ArrayList<CheckersMove> moves = new ArrayList();
        
        for(CheckersCell c: o.pieces)
        {
            moves.addAll(c.getMoves());
        }
        
        return moves;
    }
    
    
    public boolean makeMove(CheckersMove m)
    {
        Owner currentOwner = m.source.getOwner();
        if(currentOwner == Owner.EMPTY)
        {
            currentGame.setStatus("There is no piece there!");
            return false;
        }
        else if(currentOwner != currentGame.turn)
        {
            currentGame.setStatus("It is not your turn!");
            return false;
        }
        if(!m.isValidMove())
        {
            currentGame.setStatus("Invalid Move");
            return false;
        }

        ArrayList<CheckersJump> jumps = getJumpMoves();
        //@TODO double jump logic is off. check recursion conditions
        if(jumps.size() > 0)
        {
            boolean moveFound = false;

            for(CheckersJump currentJump: jumps)
            {
                if(m.equals(currentJump))
                {
                    moveFound = true;
                    break;
                }
            }

            if(!moveFound)
            {
                currentGame.setStatus("You must jump if one is available!");
                return false;
            }

           /* if(m.updateBoard(this))
            {
                printBoard();
                jumps = getJumpMoves();
                if(jumps.size() > 0)
                {
                    if(!makeMove(currentGame.requestMove()))
                    {
                        return false;
                    }
                }
                else
                {
                    return true;
                }
            }    */
            //return true;
        }
        
        if(m.updateBoard())
        {
            int destRow = m.dest.x;
            if(m.dest.getJumps().isEmpty() || destRow == CheckersMove.BOTOFBOARD
                    || destRow == CheckersMove.TOPOFBOARD)
            {
                anotherJump = false;
            }
            
            printBoard();
            return true;
        }
     
        currentGame.setStatus("Error: Please try again!");
        return false;
    }
    
    public CheckersCell getCell(int x, int y)
    {
        return board.get(x).get(y);
    }
 

    //returns the winner of the game. if the game is not over, returns empty
    public Owner isGameOver()
    {
        
        if(Owner.PLAYER1.pieces.isEmpty())
        {
            gameOver = true;
            return Owner.PLAYER2;
        }
        else if(Owner.PLAYER2.pieces.isEmpty())
        {
            gameOver = true;
            return Owner.PLAYER1;
        }
        else if(getAllMoves().isEmpty())
        {
            gameOver = true;
            return currentGame.turn.opposite();
        }
      
        return Owner.EMPTY;
    }
    
   /* public static void main(String[] args) 
    {
         System.out.println("Start");
         CheckersBoard b = new CheckersBoard();
         CheckersCell src = b.board.get(2).get(1);
         CheckersCell dest = b.board.get(3).get(0);
         b.makeMove(src, dest);
         
         src = b.board.get(3).get(0);
         ArrayList<CheckersMove> moves = src.getMoves();
         b.makeMove(moves.get(0));
         
         //System.out.println(x);
         b.printBoard();
         
         src = b.board.get(5).get(0);
         ArrayList<CheckersMove> j = src.getJumps();
         b.jump(j.get(0));
         
         b.printBoard();
         
    }*/
}
