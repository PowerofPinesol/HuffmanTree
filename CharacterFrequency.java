import java.io.*;
import java.util.HashMap;

public class CharacterFrequency {
    String fileName;
    public CharacterFrequency(String filename) {
        this.fileName = filename;
    }

    public HashMap<Character, Integer> charOccurrences() {
        HashMap<Character, Integer> charFrequencies = new HashMap<Character, Integer>();
        FileReader inputStream = null;

        try
        {
            inputStream = new FileReader(fileName);

            int c;
            while ((c = inputStream.read()) != -1) {
                Character someCharacter = (char) c;
                if (!charFrequencies.containsKey(someCharacter)) {
                    charFrequencies.put(someCharacter, 1);
                } else {
                    charFrequencies.put(someCharacter, charFrequencies.get(someCharacter) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return charFrequencies;
      }

      public int fileBitSize() {
          FileReader inputStream = null;
          int bits = 0;

          try
          {
              inputStream = new FileReader(fileName);

              int c;
              while ((c = inputStream.read()) != -1) {
                  bits += 8;
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
          return bits;
      }
}

