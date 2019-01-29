package lrusso96.feedbooks.driver.core;

import java.util.ArrayList;
import java.util.List;

public class FeedbooksBuilder
{
    //supported lang: en, it, es, de and fr.
    //default: only en.
    private List<Language> languages = new ArrayList<>();
    private Integer maxResults = 25;
    private Category.Label label;

    public FeedbooksBuilder addLanguage(Language language)
    {
        this.languages.add(language);
        return this;
    }

    public FeedbooksBuilder setMaxResults(Integer maxResults)
    {
        this.maxResults = maxResults;
        return this;
    }

    public FeedbooksBuilder setUnlimitedSearch()
    {
        this.maxResults = null;
        return this;
    }


    public FeedbooksBuilder setLabel(Category.Label label)
    {
        this.label = label;
        return this;
    }

    public Feedbooks build(){
        if(languages.isEmpty())
            languages.add(Language.ENGLISH);
        Language[] lang = new Language[languages.size()];
        for(int i = 0; i < languages.size(); i++)
            lang[i] = languages.get(i);

        return new Feedbooks(lang, maxResults, label);
    }
}
