import maze.*;
import java.io.*;
import maze.routing.*;
import java.util.*;


public class MazeDriver {

    public static void main(String [] args) throws InvalidMazeException{
        
        //read the maze from the file
        
        Maze maze = Maze.fromTxt("../resources/mazes/maze2.txt");
        
        
        System.out.println(maze.toString());

        RouteFinder rf = new RouteFinder(maze);
        List<Tile> route = rf.getRoute();
        //printing every step until the maze is solved
        while (!rf.step())    
        {
        //printing every route until * gets to the exit
        if(route.size()>0)
            {
            System.out.print(maze.getTileLocation(route.get(route.size() - 1)).toString());
            System.out.print(rf.toString()+"\n");
            }
        }   
        
    }

    
}
