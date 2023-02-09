package ir.misterdeveloper.githubusers.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserResponse {

    @SerializedName("items")
    @Expose
    private ArrayList<InformationUsers> informationUsers = null;

    public ArrayList<InformationUsers> getInformationUsers() {
        return informationUsers;
    }

    public void setInformationUsers(ArrayList<InformationUsers> informationUsers) {
        this.informationUsers = informationUsers;
    }
}
