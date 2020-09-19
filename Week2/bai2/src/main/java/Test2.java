import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Test2 {
    static Element newElement;
    static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        Connection connection = Jsoup.connect("https://dantri.com.vn/");
        Document document;
        LocalDateTime now = LocalDateTime.now();
        try {
            document = connection.get();
            newElement = document.select("div .news-item--highlight").get(0);
            New newObj = new New(getTitle(),
                    getImageUrl(),
                    getContent(),
                    getReferenceNew());
            File fileOutput = new File("/home/nmq/Desktop/vccorp/Week2/bai2/data/" + now.toString() + ".txt");
            fileOutput.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(fileOutput);
            objectMapper.writeValue(fileOutputStream, newObj);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                File fileOutput = new File("/home/nmq/Desktop/vccorp/Week2/bai2/data/data/error.txt");
                fileOutput.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(fileOutput);
                fileOutputStream.write(("ERROR WITH: " + e.getMessage() + " " + now.toString()).getBytes());
                fileOutputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private static String getTitle() {
        Element child = newElement.child(1).child(0);
        return child.text();
    }

    private static String getImageUrl() {
        Element child = newElement.child(0).child(0).child(0);
        return child.attr("src");
    }

    private static String getContent() {
        Element child = newElement.child(1).child(1);
        return child.text();
    }

    private static List<ReferenceNew> getReferenceNew() {
        List<ReferenceNew> referenceNews = new ArrayList<>();
        Elements elements = newElement.getElementsByAttributeValue("class", "news-item__related-item");
        for (Element element : elements) {
            Element aTag = element.child(0);
            referenceNews.add(new ReferenceNew(aTag.html(), "https://dantri.com.vn/" + aTag.attr("href").split("#")[0]));
        }
        return referenceNews;
    }
}
