package maze;
import java.util.*;
import java.io.*;

public class Maze implements java.io.Serializable{

    //directions

    private Tile entrance;
    private Tile exit;
    private List<List<Tile>> tiles;


    public enum Direction
    {
        
        NORTH, SOUTH, EAST, WEST;
    }

    public static class Coordinate {
        
        //we have two coordinates x,y
        private int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        //return x,y as string
        public String toString() {
            return "("+this.x+", "+this.y+")";
        }
    }

    //maze as an array list
    private Maze() {
        this.tiles = new ArrayList<List<Tile>>();
    }

    public Tile getEntrance() {
        return this.entrance;
    }

    public Tile getExit() {
        return this.exit;
    }

    public List<List<Tile>> getTiles() {
        return tiles;
    }


    public static Maze fromTxt(String filename) throws InvalidMazeException {
        Maze maze = new Maze();

        int ch, row = 0;
        
        
        try {
            FileReader fr = new FileReader(filename);
            maze.tiles.add(new ArrayList<Tile>());
            while((ch=fr.read()) != -1) {
                char character = (char) ch;
                if (character == '\n')
                    //creating new arraylist
                    { maze.tiles.add(new ArrayList<Tile>());}
                //check if there are other characters than x#.e
                else if(!(character=='e' || character=='x' || character=='#' || character=='.')) {
                    throw new InvalidMazeException("Invalid Character found!");
                }
                else {
                    //add new tile
                    Tile tile = Tile.fromChar(character);
                    maze.tiles.get(maze.tiles.size() - 1).add(tile);
                    //if character is e => set the entrance
                    if(character == 'e') {
                        try {
                            maze.setEntrance(tile);
                        } catch(MultipleEntranceException ex) {
                            throw new MultipleEntranceException("More than one entrance in the Maze!");

                        }
                    }
                    //if character is x => set the exit
                    if(character == 'x') {
                        try {
                            maze.setExit(tile);
                        } catch(MultipleExitException ex) {
                            throw new MultipleExitException("Multiple Exits found in the Maze!");

                        }
                    }
                }
            }

        } catch(IOException | IllegalAccessException e) {
            //prints the throwable along with other details like the line number and class name where the exception occurred
            e.printStackTrace();
        }

        if(maze.getEntrance() == null) {
            throw new NoEntranceException("No Entrance in the Maze!");
        }
        if(maze.getExit() == null) {
            throw new NoExitException("No Exit in the Maze!");
        }

        if(!(maze.tiles.stream().allMatch(l -> l.size() == maze.tiles.get(0).size()))) {
            throw new RaggedMazeException("There needs to be the same number of rows! ");
        }

        return maze;
    }

    public Tile getAdjacentTile(Tile tile, Direction direction) {
        Coordinate coords = getTileLocation(tile);
        Tile adjacentTile;
        if (direction == Direction.NORTH){
            adjacentTile = coords.getY() < tiles.size() - 1? getTileAtLocation(new Coordinate(coords.getX(), coords.getY()+1)) : null; }
        else if (direction == Direction.SOUTH){
            adjacentTile = coords.getY() > 0? getTileAtLocation(new Coordinate(coords.getX(), coords.getY()-1)) : null;}
            else if (direction == Direction.EAST){
                 adjacentTile = coords.getX() < tiles.get(0).size() - 1? getTileAtLocation(new Coordinate(coords.getX()+1, coords.getY())) : null;}
              else if (direction == Direction.WEST){
                 adjacentTile = coords.getX() > 0? getTileAtLocation(new Coordinate(coords.getX()-1, coords.getY())): null;} 
                else{
                   adjacentTile = null;}
        return adjacentTile;
    }


    public Tile getTileAtLocation(Coordinate coord) {
        // (0, 0) is the bottom-left 
        int numOfRows = tiles.size() - 1;
        int coordY ;
        coordY = numOfRows - coord.getY();
        //returns the location of the tile
        return tiles.get(coordY).get(coord.getX());
    }

    public Coordinate getTileLocation(Tile tile) {
        int row, col = 0;
        boolean ok = false;
        for(row=0;row<tiles.size();row++) {
            for(col=0;col<tiles.get(0).size();col++) {
                try {
                    // for RaggedMazeExceptions
                    if(tiles.get(row).get(col).equals(tile)) {
                        ok = true;
                        break;
                    }
                } catch(IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }

            }
            if(ok)
                break;
        }

        int numOfRows = tiles.size() - 1;
        row = numOfRows - row;

        //if ok is true return new coord
        return ok ? new Coordinate(col, row) : null;
    }


    //setting the entrance

    private void setEntrance(Tile tile) throws MultipleEntranceException, IllegalAccessException {
        if(getTileLocation(tile) == null){
            throw new IllegalArgumentException("Illegal Acces Exception - tile location is null!");
        }
        else if(this.getEntrance() == null) {
            this.entrance = tile;
        } else {
            throw new MultipleEntranceException("There is more than one entrance!");
        }
    }

    //setting the exit
    private void setExit(Tile tile) throws MultipleExitException, IllegalAccessException {
        if(getTileLocation(tile) == null){
            throw new IllegalArgumentException("Illegal Acces Exception - tile location is null!");
        }
        else if(this.getExit() == null) {
            this.exit = tile;
        } else {
            throw new MultipleExitException("There is more than one entrance!");
        }
    }

    public String toString() {
        String out = "";
        for(List<Tile> row: tiles) {
            for(Tile tile: row) {
                out += tile.toString();
            }
            //the end
            out += "\n";
        }
        //return the string output
        return out;
    }
}
