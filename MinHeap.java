public class MinHeap {
    ArrayList heap = new ArrayList();

    public Node pop() {
        Node poppedOff = heap.get(0);
        heap.setValue(0, heap.pop());
        heapifyDown(heap, 0);
        return poppedOff;
    }

    public void heapifyDown(ArrayList heap, int parentIndex) {
        int newCurrentIndex;
        int current = heap.get(parentIndex).frequency;
        int smallestChildIndex;
        int leftChildIndex = 2 * parentIndex + 1;
        int rightChildIndex = 2 * parentIndex+ 2;

        if(!(rightChildIndex > heap.getElementCounter() - 1) && heap.get(leftChildIndex).frequency > heap.get(rightChildIndex).frequency) {
            smallestChildIndex = rightChildIndex;
        } else if(!(leftChildIndex > heap.getElementCounter() - 1)){
            smallestChildIndex = leftChildIndex;
        } else {
            return;
        }

        if(current > heap.get(smallestChildIndex).frequency) {
            swap(parentIndex, smallestChildIndex);
            newCurrentIndex = smallestChildIndex;
            heapifyDown(heap, newCurrentIndex);
        } else {
            return;
        }
    }

    public void heapifyUp(ArrayList heap, int lastIndex) {
        //hard code
        int parentOfLeftChildsIndex = lastIndex / 2;
        int parentOfRightChildsIndex = lastIndex / 2 - 1;
        int newLastIndex = 0;

        if(lastIndex == 0) {
            return;
        }

        if(lastIndex % 2 == 0) {
            //current is rightchild
            if(heap.get(lastIndex).frequency < heap.get(parentOfRightChildsIndex).frequency) {
                swap(lastIndex, parentOfRightChildsIndex);
                newLastIndex = parentOfRightChildsIndex;
            }
        } else if(heap.get(lastIndex).frequency < heap.get(parentOfLeftChildsIndex).frequency) {
            swap(lastIndex, parentOfLeftChildsIndex);
            newLastIndex = parentOfLeftChildsIndex;
        }
        heapifyUp(heap, newLastIndex);
    }

    public void add(Node value) {
        heap.add(value);
        heapifyUp(heap, heap.getElementCounter() - 1);
    }

    public void swap(int index1, int index2) {
        Node temp = heap.get(index1);
        heap.setValue(index1, heap.get(index2));
        heap.setValue(index2, temp);
    }

    public int size() {
        return heap.getElementCounter();
    }
}
