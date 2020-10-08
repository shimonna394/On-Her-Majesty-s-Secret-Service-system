
package bgu.spl.mics.json;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonMission {

    @SerializedName("serialAgentsNumbers")
    @Expose
    private List<String> serialAgentsNumbers = null;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("gadget")
    @Expose
    private String gadget;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("timeExpired")
    @Expose
    private Integer timeExpired;
    @SerializedName("timeIssued")
    @Expose
    private Integer timeIssued;
    @SerializedName("missionName")
    @Expose
    private String missionName;

    public List<String> getSerialAgentsNumbers() {
        return serialAgentsNumbers;
    }

    public void setSerialAgentsNumbers(List<String> serialAgentsNumbers) {
        this.serialAgentsNumbers = serialAgentsNumbers;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getGadget() {
        return gadget;
    }

    public void setGadget(String gadget) {
        this.gadget = gadget;
    }

    public String getName() {
        if(name!=null)
            return name;
        else
            return missionName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTimeExpired() {
        return timeExpired;
    }

    public void setTimeExpired(Integer timeExpired) {
        this.timeExpired = timeExpired;
    }

    public Integer getTimeIssued() {
        return timeIssued;
    }

    public void setTimeIssued(Integer timeIssued) {
        this.timeIssued = timeIssued;
    }

}
