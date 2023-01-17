package model.twitter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Map;

public class TweetObjectParser {
    private String postId;
    private String text;
    private String videoUrl;

    @JsonProperty("data")
    private void unpackData(Map<String,Object> data){
        this.postId = (String) data.get("id");
        this.text = (String) data.get("text");
    }
    @JsonProperty("includes")
    private void unpackIncludes(Map<String,Object> includes){
        ArrayList<Map<String,Object>> result = (ArrayList<Map<String, Object>>) includes.get("media");
        ArrayList<Map<String,Object>> variants = (ArrayList<Map<String, Object>>) result.get(0).get("variants");
        Integer bitrate = 0;
        int iMaxBitRate = 0;
        for (int i = 0; i<variants.size(); i++){
            if (variants.get(i).get("bit_rate") != null && (Integer)variants.get(i).get("bit_rate") > bitrate){
                bitrate = (Integer) variants.get(i).get("bit_rate");
                iMaxBitRate=i;
            }
        }
        this.videoUrl = (String) variants.get(iMaxBitRate).get("url");

    }
    public String getPostId() {return postId;}
    public String getText() {
        return text;
    }
    public String getVideoUrl() {
        return videoUrl;
    }
}
