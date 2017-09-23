package deltavista.deltavistamemo;

public class Inhalt {

    private int id;
    private String inhalt;
    //private String inhalt_gesprochen;
    //private String inhalt_geschrieben;
    private String level;
    //private String gruppe_1;
    //private String gruppe_2;
    //private String gruppe_3;
    private String frei_teacher;
    private String frei_eltern;
    private String prozent_richtig;
    public Inhalt()
    {
    }
    public Inhalt(int id, String inhalt, String level, String frei_teacher, String frei_eltern, String prozent_richtig)
    {
        this.id=id;
        this.inhalt=inhalt;
        this.level=level;
        this.frei_teacher=frei_teacher;
        this.frei_eltern=frei_eltern;
        this.prozent_richtig=prozent_richtig;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public void setFreigabeLehrer(String frei_teacher) {
        this.frei_teacher = frei_teacher;
    }
    public void setFreigabeEltern(String frei_eltern) {
        this.frei_eltern = frei_eltern;
    }
    public void setPrzentRichtig(String prozent_richtig) {
        this.prozent_richtig = prozent_richtig;
    }
    public int getId() {
        return id;
    }
    public String getInhalt() {
        return inhalt;
    }
    public String getLevel() {
        return level;
    }
    public String getFreigabeLehrer() {
        return frei_teacher;
    }
    public String getFreigabeEltern() {
        return frei_eltern;
    }
    public String getProzentRichtig() {
        return prozent_richtig;
    }
}
