import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

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

    // 3) Statistical Model Method
    public static double statModel(String query, String document) {
        String[] q = query.toUpperCase().split("\\s+"); // query = "Hello brothers my name is mahmoud muhammad"
        String[] doc = document.toUpperCase().split("\\s+"); // Document_1 :- Hello brothers my name is mahmoud muhammad

        double[] stat = new double[q.length];
        double score = 0;

        for (int i = 0; i < q.length; i++) {
            for (int j = 0; j < doc.length; j++) {
                if (doc[j].equals(q[i])) {
                    stat[i]++;
                }
            }
            stat[i] = stat[i] / doc.length;
            score += stat[i];
        }
        for (int i = 0; i < stat.length; i++) {
            System.out.println(stat[i]);
        }

        System.out.println("SCORE = " + score);
        System.out.println();
        return score;
    }

    // 4) Cosine Similarity Method
    public static double cosineSimilarity(String query, String document) {
        String[] q = query.toUpperCase().split("\\s+");
        String[] doc = document.toUpperCase().split("\\s+");
        ArrayList<Integer> vectorArr1 = new ArrayList<>();
        ArrayList<Integer> vectorArr2 = new ArrayList<>();

        // Combine (q and doc) arrays into a single array
        Set<String> combinedSet = new HashSet<>();

        for (int i = 0; i < q.length; i++) {
            combinedSet.add(q[i]);
        }
        for (int i = 0; i < doc.length; i++) {
            combinedSet.add(doc[i]);
        }
        // Convert the set to an array
        String[] combinedArray = combinedSet.toArray(new String[0]);

        System.out.println("\nUnique Words :-");
        for (int i = 0; i < combinedArray.length; i++) {
            System.out.print(combinedArray[i] + " ");
        }
        System.out.println();

        // Calculate the vector for the query
        for (int i = 0; i < combinedArray.length; i++) {
            boolean found = false;
            for (int j = 0; j < q.length; j++) {
                if (combinedArray[i].equals(q[j])) {
                    vectorArr2.add(1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                vectorArr2.add(0);
            }
        }

        // Calculate the vector for the document
        for (int i = 0; i < combinedArray.length; i++) {
            boolean found = false;
            for (int j = 0; j < doc.length; j++) {
                if (combinedArray[i].equals(doc[j])) {
                    vectorArr1.add(1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                vectorArr1.add(0);
            }
        }

        System.out.println("\nQuery Vector:");
        for (int i = 0; i < vectorArr2.size(); i++) {
            System.out.print(vectorArr2.get(i) + " ");
        }

        System.out.println("\nDocument Vector:");
        for (int i = 0; i < vectorArr1.size(); i++) {
            System.out.print(vectorArr1.get(i) + " ");
        }

        System.out.println();

        // calc dot product and magnitude
        double dotProduct = 0;
        double arr1MAG = 0;
        double arr2MAG = 0;
        for (int i = 0; i < vectorArr1.size(); i++) {
            dotProduct += vectorArr1.get(i) * vectorArr2.get(i);
            arr1MAG += Math.pow(vectorArr1.get(i), 2);
            arr2MAG += Math.pow(vectorArr2.get(i), 2);
        }
        double result = dotProduct / (Math.sqrt(arr1MAG) * Math.sqrt(arr2MAG));

        System.out.println("The Cosine Similarity between query and Document");
        return result;
    }

    // 5) Jaccard method
    public static double jaccardMethod(String query, String document) {
        // jaccard = query INTERSECTION document / query UNION document
        String[] q = query.toUpperCase().split("\\s+");
        String[] doc = document.toUpperCase().split("\\s+");

        Set<String> querySet = new HashSet<>();
        Set<String> documentSet = new HashSet<>();

        for (int i = 0; i < q.length; i++) {
            querySet.add(q[i]);
        }
        for (int i = 0; i < doc.length; i++) {
            documentSet.add(doc[i]);
        }

        // Intersection of querySet and documentSet
        Set<String> intersectionSet = new HashSet<>(querySet);
        intersectionSet.retainAll(documentSet); // This line modifies intersectionSet to retain only the elements that
                                                // are also in documentSet

        // Union of querySet and documentSet
        Set<String> unionSet = new HashSet<>(querySet);
        unionSet.addAll(documentSet);
        int intersection = intersectionSet.size();
        int union = unionSet.size();

        double result = (double) intersection / union;
        System.out.println("A U B = " + union);
        System.out.println("A ∩ B = " + intersection);

        System.out.print("Jaccard(DOC,Q) = " + intersection + " / " + union + " = ");
        return result;

    }

    // 6) Likelihood_Model
    public static double Likelihood_Model(String query, String document) {
        // Query: opacity lung right lob tumor brain
        String[] queryTerms = query.toUpperCase().split("\\s+");
        String[] doc = document.toUpperCase().split("\\s+");

        // Create a unique set of query terms
        Set<String> termsSet = new HashSet<>();
        for (int i = 0; i < queryTerms.length; i++) {
            termsSet.add(queryTerms[i]);
        }

        String[] terms = termsSet.toArray(new String[0]);

        ArrayList<Double> docFrequencies = new ArrayList<>();
        double P_MLE = 1.0;

        for (int i = 0; i < terms.length; i++) {
            double docFreq = 0;
            for (int j = 0; j < doc.length; j++) {
                if (doc[j].equals(terms[i])) {
                    docFreq++;
                }
            }
            double frequency = docFreq / doc.length;
            docFrequencies.add(frequency);
            P_MLE *= frequency;
        }

        // output term frequencies and P_MLE
        for (int i = 0; i < docFrequencies.size(); i++) {
            System.out.println("Term: (" + terms[i] + ") Frequency: -->> " + docFrequencies.get(i));
        }
        System.out.println("P_MLE = " + P_MLE);
        return P_MLE;
    }

    // 7) Jelinek Mercer Smoothing

    // 8) Precision Method
    public static double calcPrecision(int truePositives, int falsePositives) {
        // Precision = (relevent ∩ retrived) / retrived
        return (double) truePositives / (truePositives + falsePositives);
    }

    // 9) Recall Method
    public static double calcRecall(int truePositives, int falseNegatives) {
        // Recall = (relevent) / All relevent docs retrived
        return (double) truePositives / (truePositives + falseNegatives);
    }

    // 10) F-measure
    public static double calcF_measure(double precision, double recall) {
        return (2 * precision * recall) / (precision + recall);
    }

    // 11) Rank Power

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
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

        System.out.println("\nQuery :- " + query);

        /*
         * int truePositives = 30;
         * int falsePositives = 10;
         * int falseNegatives = 5;
         * 
         * double precision = calcPrecision(truePositives, falsePositives);
         * double recall = calcRecall(truePositives, falseNegatives);
         * double F_measure = calcF_measure(precision, recall);
         * System.out.println(precision);
         * System.out.println(recall);
         * System.out.println(F_measure);
         * 
         */
        /*
         * int[] relevant = new int[] { 0, 2, 3 };
         * int[] retrieved = new int[] { 1, 0, 3, 2 };
         * System.out.println("precision : -");
         * System.out.println(calcPrecision(relevant, retrieved));
         */

        /*
         * String q7 = "Information retrieval";
         * String d7 =
         * "Language models are widely used in information retrieval for ranking documents"
         * ;
         * System.out.println(jaccardMethod(q7, d7));
         */

        /*
         * String qu = "opacity lung right lob tumor brain";
         * String docc = "opacity lung right lob  ";
         * System.out.println("likelhood : - ");
         * Likelihood_Model(qu, docc);
         */

        do {
            int choise;
            int truePositives = 30;
            int falsePositives = 10;
            int falseNegatives = 5;
            System.out.println("\nEnter Your Choise :- ");
            System.out.println("1-Boolean Search Model");
            System.out.println("2-Statistical Search Model");
            System.out.println("3-Cosine Similarity Method");
            System.out.println("4-Jaccard Method");
            System.out.println("5-Likelihood Model");
            System.out.println("6-Calculate Precision");
            System.out.println("7-Calculate Recall");
            System.out.println("8-Calculate F-Measure");
            System.out.println("9-Calculate Rank-Power");
            choise = in.nextInt();
            switch (choise) {
                case 1:
                    System.out.println("\nBOOLEAN-SEARCH-MODEL :- ");
                    System.out.println("\nQUERY :- " + query + "\n");
                    for (int i = 0; i < Docs.length; i++) {
                        System.out.print("Is The Document_" + (i + 1) + " Relevent to This Query ? ");
                        System.out.println(boolSearch(query, Docs[i]));
                    }
                    System.out.println();
                    break;
                case 2:
                    System.out.println("\nSTATISTICAL-SEARCH-MODEL :- ");
                    System.out.println();
                    List<Double> scores = new ArrayList<>();
                    for (int i = 0; i < Docs.length; i++) {
                        double[] stat = { statModel(query, Docs[i]) };
                        scores.add(stat[stat.length - 1]);
                    }

                    // Sort document indices based on scores
                    List<Integer> indices = new ArrayList<>();
                    for (int i = 0; i < Docs.length; i++) {
                        indices.add(i);
                    }
                    Collections.sort(indices, (a, b) -> scores.get(b).compareTo(scores.get(a)));

                    // Print the rank of documents
                    System.out.print("\nRank :- ");
                    for (int i = 0; i < indices.size(); i++) {
                        System.out.print("d" + (indices.get(i) + 1) + " ");
                    }
                    System.out.println();
                    break;
                case 3:
                    System.out.println("\nCOSINE-SIMILARITY :- ");
                    for (int i = 0; i < Docs.length; i++) {
                        System.out.println(cosineSimilarity(query, Docs[i]));
                        System.out.println();
                    }
                    System.out.println();
                    break;
                case 4:
                    System.out.println("\nJACCARD-METHOD :- ");
                    for (int i = 0; i < Docs.length; i++) {
                        System.out.println(jaccardMethod(query, Docs[i]));
                        System.out.println();
                    }
                    System.out.println();
                    break;
                case 5:
                    System.out.println("\nLIKELHOOD_MODEL :- ");

                    // eveluate P_MLE for each document and store it in an array
                    double[] pMLEs = new double[Docs.length];
                    for (int i = 0; i < Docs.length; i++) {
                        pMLEs[i] = Likelihood_Model(query, Docs[i]);
                        System.out.println();
                    }

                    // Sort the documents by P_MLE in desc order
                    for (int i = 0; i < Docs.length - 1; i++) {
                        for (int j = i + 1; j < Docs.length; j++) {
                            if (pMLEs[i] < pMLEs[j]) {
                                // Swap docs
                                String tempDoc = Docs[i];
                                Docs[i] = Docs[j];
                                Docs[j] = tempDoc;
                                // Swap P_MLE values
                                double tempPMLE = pMLEs[i];
                                pMLEs[i] = pMLEs[j];
                                pMLEs[j] = tempPMLE;
                            }
                        }
                    }

                    // Print the sorted documents
                    for (int i = 0; i < Docs.length; i++) {
                        System.out.println("Document: " + Docs[i]);
                        System.out.println("P_MLE: " + pMLEs[i]);
                        System.out.println();
                    }
                    System.out.println();
                case 6:
                    System.out.println("\nCALCULATING PRECISION :- ");
                    System.out.println("TP = " + truePositives + " | " + "FP = " + falsePositives);
                    System.out.println("precision = (TP / (TP + FP) ) = " + truePositives + " / " + truePositives
                            + " + " + falsePositives + " = " + calcPrecision(truePositives, falsePositives));
                    break;
                case 7:
                    System.out.println("\nCALCULATING RECALL :- ");
                    System.out.println("TP = " + truePositives + " | " + "FP = " + falsePositives);
                    System.out.println("recall = (TP / (TP + FN) ) = " + truePositives + " / " + truePositives
                            + " + " + falseNegatives + " = " + calcRecall(truePositives, falseNegatives));
                    break;
                case 8:
                    System.out.println("\nCALCULATING F-MEASURE :- ");
                    double precision = calcPrecision(truePositives, falsePositives);
                    double recall = calcRecall(truePositives, falseNegatives);
                    System.out.println("PERCISION = " + precision + " | " + "RECALL = " + recall);
                    System.out.println("f-measure = " + "PRECISION / RECALL = " + calcF_measure(precision, recall));
                    break;
                default:
                    System.out.println("Enter Valid choise");
            }
        } while (true);

        // ---------------------------------------------------------

        /*
         * String q = "A B D";
         * String d1 = "A B A A C E B E";
         * String d2 = "A B B B B C B B B D D";
         * String d3 = "A B C C C D";
         * String d4 = "A B A A C E B E";
         */

        // String[] dd = { d1, d2, d3, d4 };

        // ------------------------------------------------------------

        /*
         * String q5 = "the quick brown fox";
         * String doc5 = "the quick brown dog";
         * System.out.println(cosineSimilarity(q5, doc5));
         */

    }
}
