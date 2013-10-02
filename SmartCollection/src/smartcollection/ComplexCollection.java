/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package smartcollection;

/**
 *
 * @author Hoang Hai
 */
public interface ComplexCollection {

    public String add(Node o);

    public String remove(Node o);

    public String remove(int i);

    public Node get(int i);
    
    
}
