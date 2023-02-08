package maze;

//the maze can only have one entrance
//return an errormessage if the maze has more than 1 entrance 

public class MultipleEntranceException extends InvalidMazeException {
    

    public MultipleEntranceException(String errorMessage) {
        super(errorMessage);
    }
}
