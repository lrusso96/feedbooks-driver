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
}
