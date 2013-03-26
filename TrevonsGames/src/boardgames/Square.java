//Squares use char value to signify the state of cell
    //check isEmpty() for useful implementation

package boardgames;


public class Square 
{
    char state;
    
    public Square()
    {
	state = '+';
            //plus acts as empty character
    }

    public char getState() {return state;}
    public boolean isEmpty() {return (state == '+');}
    public void setState(char s) {state = s;}
}
