package findwith.Entities;

/**
 * Created by milinchuk on 11/28/14.
 */
public class Organization extends User {
    private String name;
    private String content;
    private String website;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}
