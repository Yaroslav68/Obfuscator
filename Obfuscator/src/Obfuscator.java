
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class Obfuscator {
        public static void main(String[] args) throws IOException {
            Scanner read = new Scanner(System.in);
            System.out.println("Введите путь: ");
            String fullPath = read.nextLine();
            String s = "";
            String nameFile = fileName(fullPath);
            String path = pathNewFile(fullPath);
            s = readFile(s, fullPath);
            s = comment(s);
            s = space(s);
            fileWrite(s, path, nameFile);
        }

        private static String readFile(String s, String path) throws IOException {
            List<String> strings = Files.readAllLines(Paths.get(path));
            for (int i = 0; i < strings.size(); i++) {
                s = s.concat(strings.get(i));
            }
            return s;
        }
        private static String fileName(String path) {
            ArrayList<String> strings = new ArrayList<>(Arrays.asList(path.split("/")));
            ArrayList<String> fileNames = new ArrayList<>(Arrays.asList(strings.get(strings.size() - 1).split("\\.")));
            String nameFile = fileNames.get(0);
            return nameFile;
        }

        private static String space(String stringFile) {
            return stringFile.replaceAll("\\s+(?![^\\d\\s])", "");
        }

        private static String comment(String stringFile) {
            final String regex = "(/\\*.+?\\*/)|(//.+?)[:;a-zA-Zа-яА-ЯЁё]*";
            final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.COMMENTS);
            final Matcher matcher = pattern.matcher(stringFile);
            while (((Matcher) matcher).find()) {
                stringFile = stringFile.replace(matcher.group(), "");
            }
            return stringFile;
        }

        private static String pathNewFile(String path) {
            String pathNewFile = "";
            ArrayList<String> strings = new ArrayList<>(Arrays.asList(path.split("/")));
            for (int i = 0; i < strings.size() - 1; i++) {
                pathNewFile = pathNewFile + strings.get(i) + "/";
            }
            return pathNewFile;
        }

        private static void fileWrite(String stringFile, String path, String nameFile) throws IOException {
            try (FileWriter writer = new FileWriter(path + "New" + nameFile + ".java")) {
                writer.write(stringFile);
            }
        }
    }
