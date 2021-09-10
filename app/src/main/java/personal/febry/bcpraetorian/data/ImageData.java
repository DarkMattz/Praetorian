package personal.febry.bcpraetorian.data;

public class ImageData {
    private String name, description, url, author;

    public ImageData(){

    }

    public ImageData(String name, String description, String url, String author){
        this.name = name;
        this.description = description;
        this.url = url;
        this.author = author;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }
}
