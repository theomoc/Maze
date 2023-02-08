package maze;

//the maze can only have one exit
//return an errormessage if the maze has more than 1 exit

public class MultipleExitException extends InvalidMazeException {
    
    public MultipleExitException(String errorMessage) {
        super(errorMessage);
    }
}
