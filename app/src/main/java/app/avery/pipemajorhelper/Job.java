package app.avery.pipemajorhelper;

import java.io.Serializable;
import java.util.List;

public class Job implements Serializable{
    private String name;
    private String dateString;
    private String weather;
    private int temperature;
    private int pitch;
    private List<String> setList;
    private List<String> playerList;

    public Job(String name){
        this.name = name;
    }

    /*
    public Job(String name, String dateString, String weather, int temperature, int pitch, List<String> setList, List<String> playerList){
        this.name = name;
        this.dateString = dateString;
        this.weather = weather;
        this.temperature = temperature;
        this.pitch = pitch;
        for (String s : setList) {
            this.setList.add(s);
        }
        for(String s : playerList){
            this.playerList.add(s);
        }
    }
    */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public List<String> getSetList() {
        return setList;
    }

    public void setSetList(List<String> setList) {
        for(String s : setList){
            this.setList.add(s);
        }
    }

    public List<String> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<String> playerList) {
        for(String s : playerList){
            this.playerList.add(s);
        }
    }
}
