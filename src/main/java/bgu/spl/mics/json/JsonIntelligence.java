
package bgu.spl.mics.json;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonIntelligence {

    @SerializedName("missions")
    @Expose
    private List<JsonMission> missions = null;

    public List<JsonMission> getMissions() {
        return missions;
    }

    public void setMissions(List<JsonMission> missions) {
        this.missions = missions;
    }

}
