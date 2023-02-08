package maze;

//the maze needs to have at least one exit
//return an errormessage if the maze has no exit

public class NoExitException extends InvalidMazeException {
    

    public NoExitException(String errorMessage) {
        super(errorMessage);
    }
}
