import java.util.HashMap;

public class HuffmanTree {
    Node root = null;
    HashMap<Character, boolean[]> bitsForCharacter = new HashMap();

    public HuffmanTree() {}

    public void construct(MinHeap heap) {
        while(heap.size() > 1) {
            Node node1 = heap.pop();
            Node node2 = heap.pop();
            Node huffNode = new Node(null, node1.frequency + node2.frequency);
            huffNode.right = node1;
            huffNode.left = node2;
            root = huffNode;
            heap.add(huffNode);
        }
    }

    public void constructMapOfChars(Node node) throws Exception {
        ArrayList path = new ArrayList();
        constructMapOfChars(node, path);
    }

    public void constructMapOfChars(Node node, ArrayList path) throws Exception {
        if (node == null) return;
        path.add(node);
        if(path.size() < 1) {
            return;
        }

        if(node.left == null && node.right == null) {
            boolean[] bitArray = new boolean[path.size() - 1];

            for(int i = 0; i < path.size() - 1; i++) {
                int j = i + 1;

                Node current = path.get(i);
                Node next = path.get(j);

                if(current.left == next) {
                    bitArray[i] = false;
                } else if (current.right == next) {
                    bitArray[i] = true;
                } else {
                    throw new Exception("Logic error, next is not a child of either");
                }
            }
            bitsForCharacter.put(node.c, bitArray);
        }
        constructMapOfChars(node.left, path);
        constructMapOfChars(node.right, path);
        path.pop();
    }
    
    public boolean[] bitsOfCharacter(Character character) {
        return bitsForCharacter.get(character);
    }

    public ArrayList serialize(Node root, ArrayList arrayList) {
        if(root == null) {
            arrayList.add(null);
            return arrayList;
        }
        serialize(root.left, arrayList);
        serialize(root.right, arrayList);
        return arrayList;
    }

    private ArrayList serialize(Node root) {
        ArrayList toSerialize = new ArrayList();
        return serialize(root, toSerialize);
    }

    public void deserialize(Node root, ArrayList tree) {

    }
}
