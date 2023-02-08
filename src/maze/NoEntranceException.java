package maze;

//the maze needs to have at least one entrance
//return an errormessage if the maze has no entrance

public class NoEntranceException extends InvalidMazeException {
    

    public NoEntranceException(String errorMessage) {
        super(errorMessage);
    }
}
