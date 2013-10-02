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
public class LinkedList implements MyCollection {
    
    public Node head;
    private int size;
    
    public void add(Node data) {
        Node add = new Node(data);
        Node current = head;
        if (current == null) {
            head = add;
            size++;
        } else {
            while (current.nextNode != null) {
                current = current.nextNode;
            }
            current.nextNode = add;
            size++;
        }
    }
    
    public void remove(Node data) {
        Node remove = new Node(data);
        Node current = head;
        if (current != null) {
            if (current.equals(remove)) {
                head = head.nextNode;
                size--;
            } else {
                while (current.nextNode != null) {
                    if (current.nextNode.equals(remove)) {
                        current.nextNode = current.nextNode.nextNode;
                        size--;
                        break;
                    }
                    current = current.nextNode;
                }
            }
        }
    }
    
    public Node get(int i) {
        int pos = 0;
        Node current = head;
        if (i >= 0) {
            if (current != null) {
                while (current.nextNode != null) {
                    if (pos == i) {
                        return current;
                    }
                    pos++;
                    current = current.nextNode;
                }
            }
        }
        return null;
    }
    
    @Override
    public void remove(int i) {
        Node remove = get(i);
        Node previous = get(i - 1);
        if (remove != null) {
            if (previous != null) {
                previous.nextNode = remove.nextNode;   
                size--;
            }else{
                head = remove.nextNode;
                size--;
            }
        }
    }

    @Override
    public int getSize() {
        return size;
    }
    
    
}
