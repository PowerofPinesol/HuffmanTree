public class Node {
    Character c;
    int frequency;
    Node left;
    Node right;

    public Node(Character c, int frequency) {
        this.c = c;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    public int getValue(Character c) {
        return frequency;
    }
}
