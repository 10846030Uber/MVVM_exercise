package com.example.test_allen.data_Format;

public class Data {
    private int deviceID;
    private int channelID;
    private String time;
    private String color;
    private boolean hasHelmet;
    private Boolean hasMask;
    private Boolean hasVest;


    public Data(int deviceID, int channelID, String time, String color, boolean helmet, boolean hasMask, boolean hasVest) {
        this.deviceID = deviceID;
        this.channelID = channelID;
        this.time = time;
        this.color = color;
        this.hasHelmet = helmet;
        this.hasMask = hasMask;
        this.hasVest = hasVest;
//        this.equipped = equipped;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public int getChannelID() {
        return channelID;
    }

    public String getTime() {
        return time;
    }

    public String getColor() {
        return color;
    }

    public boolean getHasHelmet() {
        return hasHelmet;
    }

    public Boolean getHasMask() {
        return hasMask;
    }

    public Boolean getHasVest() {
        return hasVest;
    }

    public String getEquipped() {
        StringBuffer equipped = new StringBuffer();
        if (hasMask) {
            equipped.append("口罩");
            if (hasVest) {
                equipped.append(",背心");
            }
        } else if (hasVest) {
            equipped.append("背心");
        } else equipped.append("");
        return equipped.toString();
    }

}
