package maze;

//the maze exceptions extend this class
//return the error message

public class InvalidMazeException extends Exception
{

    public InvalidMazeException(String errorMessage)
    {
        super(errorMessage);
    }
}
