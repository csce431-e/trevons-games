package boardgames.Gomoku;

import boardgames.UnivBoard;
import java.util.ArrayList;
import java.util.Scanner;

//Crabb
public class Gomoku 
{
    UnivBoard UBoard = new UnivBoard();
    
    int turnCounter;
    boolean gameOver;
    boolean wrongMove;
    
    int playX = 0;
    int playY = 0;
    
    //to keep track of recent past moves for undo
    ArrayList<Integer> recentX;
    ArrayList<Integer> recentY;
     
    void consoleDisplay()
    {//y u no work?
        
        //System.out.println("X 0 1 2 3 4 5 6 7 8 9 A B C D E F G");
        char state;
        
        
        for(int i=0;i<16;i++)
        {
            for(int i2=0;i2<16;++i2)
            {
                //state = '+';
                state = UBoard.getSquareState(i, i2);  
                System.out.print(state + " ");
            }
            System.out.println("");
        }
    }
    
    public Gomoku()
    {
        //System.out.println("Welcome to Gomoku, you're gonna play pvp and you're gonna like it");
        

        turnCounter = 0;
        gameOver = false;
        wrongMove = false;
        
        recentX = new ArrayList();
        recentY = new ArrayList();
            recentX.ensureCapacity(10);
            recentY.ensureCapacity(10);
        //more to come
            //pvp or ai
            //AI ONLY: choose color
    }
    
    boolean checkMove(int row, int col, char clr)
    {
         if(UBoard.getSquareState(row, col) == '+')
         {
             return false; //returns false to wrongMove, implies valid move
         }
         else {return true;} //implies invalid move
    }
   
    boolean checkWin(int row, int col, char clr)
    {//taken from old project, may need to refactor
        
        int tempR,tempC;
	int currRow = row;
	int currCol = col;

	int UpperLeft	=0;
	int Left	=0;
	int LowerLeft	=0;
	int Down	=0;
	int LowerRight	=0;
	int Right	=0;
	int UpperRight	=0;
	int Up		=0;

	//read in last played location via col,row
	//increment and decrement to check all 8 values around it
		//if same color, continue in that direction

	//UPPER LEFT
		tempR = currRow;
		tempC = currCol;
		
		while((tempR > 0 && tempC > 0)&&(tempR>currRow-4)&&(tempC>currCol-4))
		{	
			tempR -=1;
			tempC -=1;

			if(UBoard.getSquareState(tempR,tempC) == clr)
                        {
                            UpperLeft++;
                        }
			else
                        {
				break;
                        }
		}
	//UP
		tempR = currRow;
		tempC = currCol;
		
		while((tempR > 0)&&(tempR>currRow-4))
		{	
			tempR -=1;

			if(UBoard.getSquareState(tempR,tempC) == clr)
                        {
                            Up++;
                        }
			else
                        {
                            break;
                        }
		}
	//UPPER RIGHT
		tempR = currRow;
		tempC = currCol;
		
		while((tempR > 0 && tempC < 15)&&(tempR>currRow-4)&&(tempC<currCol+4))
		{	
			tempR -=1;
			tempC +=1;

			if(UBoard.getSquareState(tempR,tempC) == clr)
                        {
                            UpperRight++;
                        }
			else
                        {
                            break;
                        }	
		}
	//LEFT
		tempR = currRow;
		tempC = currCol;
		
		while((tempC > 0)&&(tempC>currCol-4))
		{	
			tempC -=1;

			if(UBoard.getSquareState(tempR,tempC) == clr)
                        {
                            Left++;
                        }
			else
                        {
                            break;
                        }
			
		}
	//LOWER LEFT
		tempR = currRow;
		tempC = currCol;
		
		while((tempR < 15 && tempC > 0)&&(tempR<currRow+4)&&(tempC>currCol-4))
		{	
			tempR +=1;
			tempC -=1;

			if(UBoard.getSquareState(tempR,tempC) == clr)
                        {
                            LowerLeft++;
                        }
			else 
                        {
                            break;
                        }
			
		}
	//DOWN
		tempR = currRow;
		tempC = currCol;
		while((tempR < 15)&&(tempR<currRow+4))
		{	
			tempR +=1;

			if(UBoard.getSquareState(tempR,tempC) == clr)
                        {
                            Down++;
                        }
			else
                        {
                            break;
                        }
			
		}
	//LOWER RIGHT
		tempR = currRow;
		tempC = currCol;
		
		while((tempR < 15 && tempC < 15)&&(tempR<currRow+4)&&(tempC<currCol+4))
		{	
			tempR +=1;
			tempC +=1;

			if(UBoard.getSquareState(tempR,tempC) == clr)
                        {
                            LowerRight++;
                        }
			else
                        {
                            break;
                        }
			
		}
	//RIGHT
		tempR = currRow;
		tempC = currCol;
		
		while((tempC < 15)&&(tempC<currCol+4))
		{	
			tempC +=1;

			if(UBoard.getSquareState(tempR,tempC) == clr)
                        {
                            Right++;
                        }
			else
                        {
                            break;
                        }
			
		}

	if(Up+Down >= 4) {return true;}
	else if(Left+Right >= 4) {return true;}
	else if(UpperLeft+LowerRight >= 4) {return true;}
	else if(UpperRight+LowerLeft >= 4){return true;}
	else {return false;}
    }
    
    void undo() 
    {
        int x=1;
        //x corresponds to desired number of turns undone
            //can be made into paramter future development
        
        for(int i=0;i<x;++i)
        {
            UBoard.unplay(recentX.get(recentX.size()-1),recentY.get(recentY.size()-1));
        }
    }
    
    void restart()
    {
        UBoard.empty();
        turnCounter = 1;
            //needs to be set to 1; checked for after turnCounter increment
    }    
    
    void getMove() //for console
    {
        //will change to graphical interface function
        
        System.out.println("Enter your move like so: 4 5\n>");
        
        Scanner sc = new Scanner(System.in);
            playX = sc.nextInt();
            playY = sc.nextInt();
    }
    
    public void incTC()
    {
        ++turnCounter;
    }
    
    public void setTC(int x) //set to zero for reset
    {
        turnCounter = x;
    } 
    
    /* was used for console version
    public void initGomoku()
    {
        do
        { 
            ++turnCounter;
        
            int row=0;
            int col=0;
            char clr='+'; //to relay which color is playing currently
                                //default '+' implies blank
            
            //needs to check for option buttons
                 //ie: undo, restart, quit
            
            display();
            
            if(turnCounter % 2 != 0)
            {
                clr = 'b'; //black always goes first, affirmative action ;)
            }
            else {clr = 'w';}
        
            getMove();
                col = --playX;
                row = --playY;
            
            do
            {
                wrongMove = checkMove(row,col,clr);
            } while (wrongMove);
            
            UBoard.play(row,col,clr);
                    recentX.add(col); //col value determines x location
                    recentY.add(row); // row value determines y location
                
                
                gameOver = checkWin(row,col,clr);
            
        } while (!gameOver);
        
        System.out.println("GAME OVER!");
         
        return false;
        
    }
    */
}
