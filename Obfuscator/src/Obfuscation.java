package obfuscation;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Obfuscation {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите путь к файлу");
        String regexFile = readUsingBufferedReader(filePath); 
        String nameFile = findNameFile(filePath); 
        String finalFile = regex(regexFile, nameFile); 
        writeFile(nameFile, finalFile); 
    }

   
    private static String readUsingBufferedReader(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

   
    private static String findNameFile(String filePath) {
        filePath = filePath.replaceAll("\\.java", "");
        ArrayList<String> nameFiles = new ArrayList<>(Arrays.asList(filePath.split("\\\\")));
        return nameFiles.get(nameFiles.size() - 1);
    }

   
    private static String regex(String regexFile, String nameFile) {
        regexFile = regexFile.replaceAll("\\s{2,}", "");
        regexFile = regexFile.replaceAll("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)", "");
        regexFile = regexFile.replaceAll("(/\\*.+?\\*/)|(//.+?)[:;a-zA-Za-яА-ЯЁё]*", "");
        regexFile = regexFile.replaceAll(nameFile, "New" + nameFile);
        return regexFile;
    }

 
    private static void writeFile(String nameFile, String finalFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("New" + nameFile))) {
            bw.write(finalFile);
        } catch (IOException ignored) {

        }
    }
}
