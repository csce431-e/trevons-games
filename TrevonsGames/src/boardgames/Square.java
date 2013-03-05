//Squares use char value to signify the state of cell
    //check isEmpty() for useful implementation

package boardgames;


public class Square 
{
    char state;
    
    Square()
    {
	state = '+';
            //plus acts as empty character
    }

    char getState() {return state;}
    boolean isEmpty() 
    {
        if(state == '+')
        {			
            return true;
        }
        else
        {
            return false;
        }
    }
    void setState(char s)
    {
        state = s;
    }
}
