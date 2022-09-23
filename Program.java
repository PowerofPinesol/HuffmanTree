import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Program {
    public static void main(String[] args) throws Exception {
        String inputFile = "";
        String outputFile = "";
        byte magicByte1 = 0x48;
        byte magicByte2 = 0x46;

        boolean compress = false;
        boolean uncompress = false;
        boolean force = false;

        for(int i = 0; i < args.length; i++) {
            outputFile = args[args.length - 1] ;
            inputFile = args[args.length - 2];

            if(args[i].equals("-c")) {
               compress = true;
            }
            if (args[i].equals("-u")) {
               uncompress = true;
            }
            if (args[i].equals("-f")) {
                force = true;
            }
        }

        MinHeap heap = new MinHeap();
        HuffmanTree huffmanTree = new HuffmanTree();
        CharacterFrequency characterFrequency = new CharacterFrequency(inputFile);
        HashMap<Character, Integer> charFrequencies = characterFrequency.charOccurrences();

        for (Map.Entry<Character, Integer> entry : charFrequencies.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();
            Node addToHeap = new Node(key, value);
            heap.add(addToHeap);
        }
        huffmanTree.construct(heap);
        huffmanTree.constructMapOfChars(huffmanTree.root);

        if(compress) {
            TextFile inputText = new TextFile(inputFile, 'r');
            BinaryFile outputBinary = new BinaryFile(outputFile, 'w');
            outputBinary.writeChar((char) magicByte1);
            outputBinary.writeChar((char) magicByte2);

            ArrayList path = new ArrayList();
            ArrayList serializedHuffman = huffmanTree.serialize(huffmanTree.root, path);
            int numBitsHuffman = 0;
            for(int i = 0; i < serializedHuffman.size(); i++) {
                if(serializedHuffman.get(i) == null) {
                    numBitsHuffman += 9;
                } else {
                    if(serializedHuffman.get(i).c == null) {
                        numBitsHuffman += 1;
                    } else {
                        numBitsHuffman += 9;
                    }
                }
            }

            byte[] byteArray = intToByteArray(numBitsHuffman);
            for (byte b : byteArray) {
                outputBinary.writeChar((char) b);
            }

            for(int i = 0; i < serializedHuffman.size(); i++) {
                if(serializedHuffman.get(i) == null) {
                    outputBinary.writeBit(true);
                    outputBinary.writeChar((char) 0xff);
                } else {
                    if(serializedHuffman.get(i).c == null) {
                        outputBinary.writeBit(false);
                    } else {
                        outputBinary.writeBit(true);
                        outputBinary.writeChar(serializedHuffman.get(i).c);
                    }
                }
            }

            try {
                // Read to measure number of bits in compressed output
                int compressedDataBits = 0;
                FileReader inputStream1 = new FileReader(inputFile);
                int c1;
                while ((c1 = inputStream1.read()) != -1) {
                    Character characterValue = (char) c1;
                    compressedDataBits += huffmanTree.bitsOfCharacter(characterValue).length;
                }

                // Write number of bits in compressed output
                byte[] compressedBitsCounterByteArray = intToByteArray(compressedDataBits);
                for (byte b : compressedBitsCounterByteArray) {
                    outputBinary.writeChar((char) b);
                }

                // Write the compressed bits
                FileReader inputStream2 = new FileReader(inputFile);
                int c;
                while ((c = inputStream2.read()) != -1) {
                    Character characterValue = (char) c;
                    for(int i = 0; i < huffmanTree.bitsOfCharacter(characterValue).length; i++) {
                        boolean[] bitArray = huffmanTree.bitsOfCharacter(characterValue);
                        boolean bit = bitArray[i];
                        outputBinary.writeBit(bit);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(characterFrequency.fileBitSize() > outputBinary.fileBitSize()) {
                outputBinary.close();
            } else {
                if (force) {
                    outputBinary.close();
                } else {
                    throw new Error("Compressed file is larger than input file, add -f to force this operation.");
                }
            }
        } else if (uncompress) {
            BinaryFile inputBinary = new BinaryFile(inputFile, 'r');
            TextFile outputText = new TextFile(outputFile, 'w');

            //check for magic number
            byte[] magicNumber = new byte[2];

            if(inputBinary.readChar((char) magicByte1)) {
            }


        } else {
            throw new Exception("You did not specify compress or uncompress, you dolt. jk... unless...? haha jk.... unless?");
        }
    }


    private static Boolean isBitSet(byte b, int bit)
    {
        return (b & (1 << bit)) != 0;
    }

    public static final byte[] intToByteArray(int value) {
        return new byte[] {
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }
}
