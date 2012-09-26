package cr.ac.ucr.ecci.ci2354.netsamples;

import java.util.Arrays;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class User {
    private int id;

//    @SerializedName("FIRSTNAME")
    private String firstname;

    private String lastname;

    private String[] phonelist;

    private String birthdate;

    private float weight;

    private float height;

    private Location location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String[] getPhonelist() {
        return phonelist;
    }

    public void setPhonelist(String[] phonelist) {
        this.phonelist = phonelist;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", phonelist="
                + Arrays.toString(phonelist) + ", birthdate=" + birthdate + ", weight=" + weight + ", height=" + height
                + ", location=" + location + "]";
    }

}
