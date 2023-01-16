package Model.Entities;

public class Video {

    private String videoUrl;
    private String shortUrl;

    public Video (String videoUrl, String shortUrl){
        this.videoUrl = videoUrl;
        this.shortUrl = shortUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}
