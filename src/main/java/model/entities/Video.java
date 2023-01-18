package model.entities;

public class Video {

    private Long ownerId;
    private String videoUrl;
    private Long createdAt;

    public Video (String videoUrl, long createdAt , Long ownerId){
        this.videoUrl = videoUrl;
        this.createdAt = createdAt;
        this.ownerId = ownerId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
