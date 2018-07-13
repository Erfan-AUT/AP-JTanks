package game.template.logic.utils;

import game.template.logic.Map;
import game.template.logic.cellfillers.GameObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class FileUtils {

    private static final String NOTES_PATH = "./notes/";

    //It's a static initializer. It's executed when the class is loaded.
    //It's similar to constructor
    static {
        //boolean isSuccessful = new File(NOTES_PATH).mkdirs();
        //System.out.println("Creating " + NOTES_PATH + " directory is successful: " + isSuccessful);
    }

    public static File[] getFilesInDirectory() {
        return new File(NOTES_PATH).listFiles();
    }


    public static String fileReader(File file) {
        byte[] allBytes = null;
        try {
            allBytes = Files.readAllBytes(Paths.get(file.getPath()));
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
        return new String(allBytes);
    }

    public static void writeLinesToFile(Collection<String> lines, String fileName) {
        try (PrintWriter out = new PrintWriter(fileName)) {
            for (String line : lines) {
                out.println(line);
            }
        } catch (FileNotFoundException ex) {
        }
    }

//    public static void queuesSaver(HashMap<String, Queue> queues) {
//        try (PrintWriter out = new PrintWriter("Logs\\Removed.jdm")) {
//            for (Queue queue : queues.values())
//                out.println(queue.getName());
//        } catch (FileNotFoundException ex) {
//        }
//    }

    //TODO: Phase1: define method here for reading file with InputStream

    public static ArrayList<String> readWithStream(String file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            ArrayList<String> returnValue = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null)
                returnValue.add(line);
            reader.close();
            return returnValue;
        } catch (IOException ex) {
            return null;
        }
    }

//    public static void checker(String file1, String file2)
//    {
//        ArrayList<String> f1 = readWithStream(file1);
//        ArrayList<String> f2 = readWithStream(file2);
//        for (int i = 0; i < f1.size(); ++i) {
//            if (!f1.get(i).equals(f2.get(i)))
//            {
//                System.out.println("File1: \n" + f1.get(i));
//                System.out.println("File2: \n" + f2.get(i));
//            }
//
//        }
//    }

    public static void writeWithStream(String context, String filePath) throws IOException {
        OutputStream outputStream = new FileOutputStream(filePath);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        for (char c : context.toCharArray())
            writer.append(c);
        writer.close();
    }

    private static String getProperFileName(String content) {
        int loc = content.indexOf("\n");
        if (loc != -1) {
            return content.substring(0, loc);
        }
        if (!content.isEmpty()) {
            return content + ".txt";
        }
        return System.currentTimeMillis() + "_new file.txt";
    }

    public static void writeMap(Map map, String filePath) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(map);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static Map readMap(String fileName){
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Map map  =(Map) in.readObject();
            return map;
            //in.close();
            //fileIn.close();
        } catch (IOException | ClassNotFoundException  i) {
            System.out.println(i.getStackTrace());
            return null;
        }
        //return returnValue;
    }

}
