package model.entities;

public class Video {

    private String videoUrl;
    private String createdAt;

    public Video (String videoUrl, String createdAt){
        this.videoUrl = videoUrl;
        this.createdAt = createdAt;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
