/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package smartcollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Hoang Hai
 */
public class SmartCollection implements ComplexCollection {

    MyCollection collection;

    @Override
    public String add(Node o) {
        int size = (collection != null) ? collection.getSize() : 0;
        String result = "";
        if (size <= 10 - 1) {
            if (!(collection instanceof Array)) {
                if (collection == null) {
                    collection = new Array();
                    result += "Create new Array. ";
                } else {
                    Array temp = new Array();
                    for (int i = 0; i < collection.getSize(); i++) {
                        temp.add(collection.get(i));
                    }
                    result += "Convert to Array. ";
                    collection = temp;

                }
            }
            collection.add(o);
            result += "Save " + collection.getSize() + " element(s) to Array";
            return result;
        } else {
            if (!(collection instanceof LinkedList)) {
                if (collection == null) {
                    collection = new LinkedList();
                    result += "Create new LinkedList. ";
                } else {
                    LinkedList temp = new LinkedList();
                    for (int i = 0; i < collection.getSize(); i++) {
                        temp.add(collection.get(i));
                    }
                    result += "Convert to LinkedList. ";
                    collection = temp;
                }
            }
            collection.add(o);
            result += "Save " + collection.getSize() + " element(s) to LinkedList";
            return result;
        }

    }

    @Override
    public String remove(Node o) {
        int size = (collection != null) ? collection.getSize() : 0;
        String result = "";
        if (size <= 10 + 1) {
            if (!(collection instanceof Array)) {
                if (collection == null) {
                    collection = new Array();
                    result += "Create new Array. ";
                } else {
                    Array temp = new Array();
                    for (int i = 0; i < collection.getSize(); i++) {
                        temp.add(collection.get(i));
                    }
                    result += "Convert to Array. ";
                    collection = temp;

                }
            }
            collection.remove(o);
            result += "Remove one element. Has " + collection.getSize() + " element(s) left in Array";
            return result;
        } else {
            if (!(collection instanceof LinkedList)) {
                if (collection == null) {
                    collection = new LinkedList();
                    result += "Create new LinkedList. ";
                } else {
                    LinkedList temp = new LinkedList();
                    for (int i = 0; i < collection.getSize(); i++) {
                        temp.add(collection.get(i));
                    }
                    result += "Convert to LinkedList. ";
                    collection = temp;
                }
            }
            collection.remove(o);
            result += "Remove one element. Has " + collection.getSize() + " element(s) left in LinkedList";
            return result;
        }

    }

    @Override
    public String remove(int index) {
        int size = (collection != null) ? collection.getSize() : 0;
        String result = "";
        if (size <= 10 + 1) {
            if (!(collection instanceof Array)) {
                if (collection == null) {
                    collection = new Array();
                    result += "Create new Array. ";
                } else {
                    Array temp = new Array();
                    for (int i = 0; i < collection.getSize(); i++) {
                        temp.add(collection.get(i));
                    }
                    result += "Convert to Array. ";
                    collection = temp;

                }
            }
            collection.remove(index);
            result += "Remove one element. Has " + collection.getSize() + " element(s) left in Array";
            return result;
        } else {
            if (!(collection instanceof LinkedList)) {
                if (collection == null) {
                    collection = new LinkedList();
                    result += "Create new LinkedList. ";
                } else {
                    LinkedList temp = new LinkedList();
                    for (int i = 0; i < collection.getSize(); i++) {
                        temp.add(collection.get(i));
                    }
                    result += "Convert to LinkedList. ";
                    collection = temp;
                }
            }
            collection.remove(index);
            result += "Remove one element. Has " + collection.getSize() + " element(s) left in LinkedList";
            return result;
        }
    }

    @Override
    public Node get(int i) {
        return collection.get(i);
    }
}
