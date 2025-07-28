package wmi.amu.edu.pl.pri;

public class JSONChecklistObj {
    private String question;
    private boolean critical;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }
}
