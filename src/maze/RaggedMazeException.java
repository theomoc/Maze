package maze;

//the maze needs to have the same number of rows
//return an errormessage

public class RaggedMazeException extends InvalidMazeException {

    public RaggedMazeException(String errorMessage) {
        super(errorMessage);
    }
}
