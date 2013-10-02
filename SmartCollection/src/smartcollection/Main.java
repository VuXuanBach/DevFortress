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
public class Main {

    public static void main(String[] args) {
        ComplexCollection sc = new SmartCollection();
        for (int i = 0; i < 20; i++) {
            System.out.println(sc.add(new Node(i)));
        }

        System.out.println("---------------------------------------------------------------------");
        for (int i = 0; i < 20; i++) {
            if (sc.get(0) != null) {
                System.out.println(sc.remove(0));
            }
        }
    }
}
