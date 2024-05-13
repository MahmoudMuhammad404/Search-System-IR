import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class searchSystem {

    // 1) Reading Documents
    public static String readFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    // 2) Boolean Search Method
    public static boolean boolSearch(String query, String document) {
        String[] q = query.toUpperCase().split(" ");
        // System.out.println(q[1]);
        String[] doc = document.toUpperCase().split(" ");
        // System.out.println(doc[1]);

        for (int i = 0; i < Math.min(q.length, doc.length); i++) {
            for (int j = 0; j < Math.min(q.length, doc.length); j++) {
                if (q[i].equals(doc[j])) {

                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        String fileName_1 = "./Docs/Document_1.txt";
        String fileName_2 = "./Docs/Document_2.txt";
        String fileName_3 = "./Docs/Document_3.txt";
        String fileName_4 = "./Docs/Document_4.txt";
        String Document_1 = readFile(fileName_1);
        String Document_2 = readFile(fileName_2);
        String Document_3 = readFile(fileName_3);
        String Document_4 = readFile(fileName_4);

        System.out.println("Content of Document_1:");
        System.out.println(Document_1);

        System.out.println("Content of Document_2:");
        System.out.println(Document_2);

        System.out.println("Content of Document_3:");
        System.out.println(Document_3);

        System.out.println("Content of Document_4:");
        System.out.println(Document_4 + "\n");

        String query = "Hello brothers my name is mahmoud muhammad";
        String[] Docs = { Document_1, Document_2, Document_3, Document_4 };

        System.out.println("QUERY :- " + query + "\n");
        for (int i = 0; i < 4; i++) {
            System.out.print("Is The Document_" + (i + 1) + " Relevent to This Query ? ");
            System.out.println(boolSearch(query, Docs[i]));
        }

    }
}
