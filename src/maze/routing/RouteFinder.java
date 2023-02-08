package maze.routing;
import maze.*;
import java.util.*;
import java.io.*;


public class RouteFinder implements java.io.Serializable{

    private Maze maze;
    private boolean finished;
    private Stack<Tile> route;
    
    //declare the best route
    private Queue<Tile> bestRoute;
    
    //declare the visited Tiles
    private List<Tile> traversedTiles;

    public Maze getMaze() {
        return this.maze;
    }

    public List<Tile> getRoute() {
        return this.route;
    }

    public boolean isFinished() {
        return this.finished;
    }

    //new route

    public RouteFinder(Maze maze) {
        this.maze = maze;
        //new stack for the route
        this.route = new Stack<Tile>();
        //a list for the best route
        this.bestRoute = new LinkedList<Tile>();
        //array list for the visited Tiles
        this.traversedTiles = new ArrayList<Tile>();
        this.finished = false;
    }




     //reading the serialised input stream
     //return the route 
    public static RouteFinder load(String filename) throws IOException, ClassNotFoundException{
        RouteFinder rf = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            rf = (RouteFinder) objIn.readObject();
        } catch(IOException ex){
            throw ex;
        } catch(ClassNotFoundException ex) {
            throw ex;
        }
        return rf;
    }

    //save the file

    public void save(String filename) throws IOException {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch(IOException ex) {
            throw new IOException(ex);
        }
    }
    
    //return the tiles that have been visited

    public List<Tile> getTraversedTiles() {
        return this.traversedTiles;
    }
    
    //the next step

    public boolean step() {
        if(!this.finished) {
            if(!(this.route.contains(this.maze.getEntrance()) || this.traversedTiles.contains(this.maze.getEntrance()))) {
                this.traversedTiles.add(this.maze.getEntrance());
                this.route.push(this.maze.getEntrance());

            } else {
                if(route.size() > 0) {
                    Tile current = route.peek();
                    Tile next;


                    Tile westTile = maze.getAdjacentTile(current, Maze.Direction.WEST);
                    Tile eastTile = maze.getAdjacentTile(current, Maze.Direction.EAST);
                    Tile northTile = maze.getAdjacentTile(current, Maze.Direction.NORTH);
                    Tile southTile = maze.getAdjacentTile(current, Maze.Direction.SOUTH);


                    if(northTile != null && northTile.isNavigable() && !traversedTiles.contains(northTile))
                        next = northTile;
                    else if(westTile != null && westTile.isNavigable() && !traversedTiles.contains(westTile))
                        next = westTile;
                    else if(eastTile != null && eastTile.isNavigable() && !traversedTiles.contains(eastTile))
                        next = eastTile;
                    else if(southTile != null && southTile.isNavigable() && !traversedTiles.contains(southTile))
                        next = southTile;
                    else
                        next = null;

                    if(next != null) {
                        traversedTiles.add(next);
                        route.push(next);
                    } else {
                        route.pop();
                    }

                    this.finished = next == null? false : next.toString().equals("x");
                } else {
                    throw new NoRouteFoundException("No route !");
                }
            }
        } else if(!traversedTiles.contains(this.maze.getExit())) {
            traversedTiles.add(this.maze.getExit());
        }
        return this.finished;
    }

    public String toString() {
        String out = "";
        //getting all tiles and searching for * tile => to string
        List<List<Tile>> mazeTiles = maze.getTiles();
        for(List<Tile> row: mazeTiles) {
            for(Tile tile: row) {
                out += route.contains(tile) ? "*" : tile.toString();
            }
            out += "\n";
        }
        return out;
    }

}
