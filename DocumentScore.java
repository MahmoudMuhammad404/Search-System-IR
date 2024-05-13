public class DocumentScore implements Comparable<DocumentScore> {
    public int documentNumber;
    public double score;

    public DocumentScore(int documentNumber, double score) {
        this.documentNumber = documentNumber;
        this.score = score;
    }

    public int getDocumentNumber() {
        return documentNumber;
    }

    public double getScore() {
        return score;
    }

    @Override
    public int compareTo(DocumentScore other) {
        return Double.compare(other.score, this.score);
    }
}
