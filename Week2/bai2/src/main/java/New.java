import java.io.Serializable;
import java.util.List;

public class New implements Serializable {
    private String title;
    private String imageUrl;
    private String content;
    private List<ReferenceNew> referenceNews;

    public New(String title, String imageUrl, String content, List<ReferenceNew> referenceNews) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.content = content;
        this.referenceNews = referenceNews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ReferenceNew> getReferenceNews() {
        return referenceNews;
    }

    public void setReferenceNews(List<ReferenceNew> referenceNews) {
        this.referenceNews = referenceNews;
    }
}