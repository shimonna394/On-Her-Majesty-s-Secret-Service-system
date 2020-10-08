
package bgu.spl.mics.json;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonServices {

    @SerializedName("M")
    @Expose
    private Integer m;
    @SerializedName("Moneypenny")
    @Expose
    private Integer moneypenny;
    @SerializedName("intelligence")
    @Expose
    private List<JsonIntelligence> intelligence = null;
    @SerializedName("time")
    @Expose
    private Integer time;

    public Integer getM() {
        return m;
    }

    public void setM(Integer m) {
        this.m = m;
    }

    public Integer getMoneypenny() {
        return moneypenny;
    }

    public void setMoneypenny(Integer moneypenny) {
        this.moneypenny = moneypenny;
    }

    public List<JsonIntelligence> getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(List<JsonIntelligence> intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

}
