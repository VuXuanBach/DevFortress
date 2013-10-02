/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package smartcollection;

import java.util.Objects;

/**
 *
 * @author Hoang Hai
 */
public class Node {
    public Object data;
    public Node nextNode;

    public Node(Object data) {
       this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.data);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }
    
    
}
