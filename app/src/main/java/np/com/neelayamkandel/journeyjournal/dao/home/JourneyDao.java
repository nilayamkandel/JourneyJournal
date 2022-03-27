package np.com.neelayamkandel.journeyjournal.dao.home;

import com.google.firebase.database.annotations.Nullable;

import java.io.Serializable;

public class JourneyDao implements Serializable {
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

    private final String User;
    @Nullable
    private final String ImageUri;
    private final String Title;
    private final String Date;
    private final String Description;


}
