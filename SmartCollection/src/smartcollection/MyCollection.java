package smartcollection;

public interface MyCollection {

    public void add(Node o);

    public String remove(Node o);

    public void remove(int i);

    public Node get(int i);

    public int getSize();
}
