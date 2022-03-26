package np.com.neelayamkandel.journeyjournal.dao.auth.registration;

import java.io.Serializable;

public class Registration implements Serializable {
    public Registration(String displayName, String email) {
        this.displayName = displayName;
        this.email = email;
    }

    public Registration() {

    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    private String displayName;
    private String email;


}
