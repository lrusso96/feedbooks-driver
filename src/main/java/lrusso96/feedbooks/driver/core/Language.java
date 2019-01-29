package lrusso96.feedbooks.driver.core;

public enum Language
{
    ENGLISH("en"),
    ITALIAN("it"),
    SPANISH("es"),
    FRENCH("fr"),
    DEUTSCH("de");

    private final String code;

    Language(final String code) {
        this.code= code;
    }

    @Override
    public String toString() {
        return code;
    }
}
