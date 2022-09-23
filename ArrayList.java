import java.util.Objects;

public class ArrayList {
    private int size = 1024;
    private Node[] arrayList;
    private int elementCounter = 0;

    public ArrayList() {
        this.arrayList = new Node[size];
    }


    public Node[] resizeArray(Node[] arrayList) {
        int sizeCompare = size / 2;
        Node[] resizedArray = new Node[size * 2];

        for(int i = 0; i < size; i++) {
            resizedArray[i] = arrayList[i];
        }
        arrayList = resizedArray;
        return arrayList;
    }

    public void add(Node node) {
        arrayList[elementCounter] = node;
        elementCounter++;

        if(elementCounter == size / 2) {
            resizeArray(arrayList);
        }
    }

    public Node get(int index) {
        return arrayList[index];
    }

    public void setValue(int index, Node node) {
        arrayList[index] = node;
        if(arrayList[index] == null) {
            elementCounter++;
        }
    }

    public Node pop() {
        Node lastElement = arrayList[elementCounter - 1];
        arrayList[elementCounter - 1] = null;
        elementCounter--;
        return lastElement;
    }


    public void setSize(int size) {
        this.size = size;
    }

    public int size() {
        return elementCounter;
    }

    public void setElementCounter(int elementCounter) {
        this.elementCounter = elementCounter;
    }

    public int getElementCounter() {
        return elementCounter;
    }
}
