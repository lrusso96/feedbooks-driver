package lrusso96.feedbooks.driver.core;

import java.util.Objects;

public class Category
{
    private String term;
    private String label;

    public String getTerm()
    {
        return term;
    }

    public void setTerm(String term)
    {
        this.term = term;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Category category = (Category) o;
        return Objects.equals(term, category.term) && Objects.equals(label, category.label);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(term, label);
    }

    @Override
    public String toString()
    {
        return "Category{" + "term='" + term + '\'' + ", label='" + label + '\'' + '}';
    }

    public enum Label
    {
        FICTION("FBFIC000000"),
        NON_FICTION("FBNFC000000"),

        //main fiction subcategories
        ACTION_AND_ADVENTURE("FBFIC020000"),
        BIOGRAPHICAL("FBFIC041000"),
        DRAMA("FBDRA000000"),
        FANTASY("FBFIC009000"),
        HISTORICAL("FBFIC014000"),
        HOMOROUS("FBFIC016000"),
        HORROR("FBFIC015000"),
        LITERARY("FBFIC019000"),
        MYSTERY_AND_DETECTIVE("FBFIC022000"),
        OCCULT_AND_SUPERNATURAL("FBFIC024000"),
        POETRY("FBPOE000000"),
        REGIONAL_FICTION("FBFIC050000N"),
        ROMANCE("FBFIC027000"),
        SEA_STORIES("FBFIC047000"),
        SCIENCE_FICTION("FBFIC028000"),
        SHORT_STORIES("FBFIC029000"),
        TRAVEL_WRITING("FBTRV010000"),
        WAR_AND_MILITARY("FBFIC032000"),
        WESTERNS("FBFIC033000"),

        //main non-fiction subcategories
        BIOGRAPHY_AND_AUTOBIOGRAPHY("FBBIO000000"),
        BUSINESS_AND_ECONOMICS("FBBUS000000"),
        //COMPUTERS("FBFIC000000"),
        //EDUCATION("FBFIC000000")
        HISTORY("FBHIS000000"),
        HUMAN_SCIENCE("FSHUM000000N"),
        HUMOR("FBHUM000000"),
        LITERARY_ESSAY("FBLIT000000"),
        NEWS_AND_INVESTIGATIONS("FBSACT000000"),
        RELIGION("FBREL000000"),
        SCIENCE_AND_TECHNICS("FBSCI000000N"),
        SOCIAL_SCIENCE("FBSOC000000"),
        TRAVEL("FBTRV000000"),
        ;

        private final String term;

        Label(final String term) {
            this.term = term;
        }

        @Override
        public String toString() {
            return term;
        }
    }
}
