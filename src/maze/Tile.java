package maze;
import java.util.*;
import java.io.*;


public class Tile implements java.io.Serializable{


    private Type type;
    public enum Type
    {
        
        CORRIDOR,ENTRANCE,EXIT,WALL;
    }

    private Tile(Type type) {
        this.type = type;
    }
    public Type getType() {
        return this.type;
    }


    //setting the char characters 
    protected static Tile fromChar(char c) {

    Tile tile;
    tile = null;
    if(c == '.')
        {tile = new Tile(Type.CORRIDOR);}
    else if (c == 'e')
        {tile = new Tile(Type.ENTRANCE);}
        else if (c == 'x')
        {tile = new Tile(Type.EXIT);}
            else if (c == '#')
            {tile = new Tile(Type.WALL);}
    return tile;
    }


    //if is navigable
    public boolean isNavigable() {
        if(this.type == Type.WALL)
            return false;
        else
            return true;
    }

    //return the string output - the tiles (corridor,wall,entrance,exit)
    public String toString() {
        String output = "";

        if(this.type == Type.WALL)
            output = "#";
        else
            if(this.type == Type.CORRIDOR)
                output = ".";
            else
                if(this.type == Type.ENTRANCE)
                   output = "e";
                else
                    if(this.type == Type.EXIT)
                        output = "x";
                    else
                        output = "";
                

       return output;
    }

}
