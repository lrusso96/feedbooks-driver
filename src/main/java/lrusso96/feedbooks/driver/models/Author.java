package lrusso96.feedbooks.driver.models;

import java.util.Objects;

public class Author
{
    private int id;
    private String fullName;
    private int birthDate;
    private int deathDate;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public int getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(int birthDate)
    {
        this.birthDate = birthDate;
    }

    public int getDeathDate()
    {
        return deathDate;
    }

    public void setDeathDate(int deathDate)
    {
        this.deathDate = deathDate;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Author author = (Author) o;
        return id == author.id && birthDate == author.birthDate && deathDate == author.deathDate && Objects.equals(fullName, author.fullName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, fullName, birthDate, deathDate);
    }

    @Override
    public String toString()
    {
        return "Author{" + "id=" + id + ", fullName='" + fullName + '\'' + ", birthDate=" + birthDate + ", deathDate" +
                "=" + deathDate + '}';
    }
}
