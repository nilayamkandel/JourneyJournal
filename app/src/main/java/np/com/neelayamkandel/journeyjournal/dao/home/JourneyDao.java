package np.com.neelayamkandel.journeyjournal.dao.home;

import com.google.firebase.database.annotations.Nullable;

import java.io.Serializable;

public class JourneyDao implements Serializable {
    public void setUser(String user) {
        User = user;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public JourneyDao(String user, String imageUri, String title, String date, String description) {
        User = user;
        ImageUri = imageUri;
        Title = title;
        Date = date;
        Description = description;
    }

    public String getUser() {
        return User;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public String getTitle() {
        return Title;
    }

    public String getDate() {
        return Date;
    }

    public String getDescription() {
        return Description;
    }

    public JourneyDao() {
        User = null;
        ImageUri = null;
        Title = null;
        Date = null;
        Description = null;
    }

    private  String User;
    @Nullable
    private  String ImageUri;
    private  String Title;
    @Nullable
    private  String Date;
    @Nullable
    private  String Description;


}
