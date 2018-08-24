package helpers;

public class Rule {
    public final String[] requiredWords;
    public final String[] bannedWords;
    public final String category;

    public Rule(String[] requiredWords, String[] bannedWords, String category) {
        this.requiredWords = requiredWords;
        this.bannedWords = bannedWords;
        this.category = category;
    }
}
