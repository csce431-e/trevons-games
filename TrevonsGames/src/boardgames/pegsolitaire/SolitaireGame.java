package boardgames.pegSolitaire;


import java.util.Scanner;

public class SolitaireGame {
	
	//public static void main(String[] args)
    SolitaireBoard b;
    
    public void initGame()
	{
		b = new SolitaireBoard();
                int state = 0;
                //firstChoice = new Rectangle();
                //rects = new List<List<Rectangle>>();
                
		b.printBoard();
        }
    
        public void sendMove(int x1, int y1, int x2, int y2)
        {
		Scanner scan = new Scanner(System.in);
		
		//while (true) 
		//{
                    //int x1;
                    //int x2;
                    //int y1;
                    //int y2;
			System.out.println("Please enter in the src coordinates");
			//x1 = scan.nextInt();
			//y1 = scan.nextInt();
			System.out.println("Please enter in the dest coordinates");
			//x2 = scan.nextInt();
			//y2 = scan.nextInt();
			
			SolitaireCoordinate c1 = b.getCoordinate(x1, y1);
			SolitaireCoordinate c2 = b.getCoordinate(x2, y2);
			
			if(c1 != null && c2 != null)
			{
				//input was good, proceed to make the move if possible
				SolitaireCoordinate moveMade = b.move(c1, c2);
				if(moveMade == null)
				{
					System.out.println("Bad Move: try again");
				}
				else
				{
					b.printBoard();
				}
			}
			else if(x1 == 99)
			{
				System.out.println("So you want some help?");
				if(scan.nextInt()==1)
				{
					//Solver s = new Solver();
					//Coordinate c3 = s.getNextMove(b);
					//System.out.println("Try: "+c3.x+ " " + c3.y);
				}
			}
			else
			{
				//err one or both of the coordinates given by user was bad
				System.out.println("ERRRRRRR: bad input");
				//break;
                                return;
			}
			
			
	      //}
	}

}
