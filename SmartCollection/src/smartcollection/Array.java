/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package smartcollection;

import java.util.ArrayList;

/**
 *
 * @author Hoang Hai
 */
public class Array implements MyCollection{
    
    ArrayList<Node> array;

    public Array() {
        array = new ArrayList<Node>();
    }
    
    
    @Override
    public void add(Node o) {
        array.add(o);
    }

    @Override
    public void remove(Node o) {
        array.remove(o);
    }

    @Override
    public void remove(int i) {
        array.remove(i);
    }

    @Override
    public Node get(int i) {
        return array.get(i);
    }

    @Override
    public int getSize() {
        return array.size();
    }
    
}
