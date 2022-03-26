package np.com.neelayamkandel.journeyjournal.dao.auth.login;

import com.google.firebase.auth.FirebaseUser;

import np.com.neelayamkandel.journeyjournal.dao.auth.registration.Registration;

public class LoginProfile {
    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public LoginProfile(Registration registration, FirebaseUser firebaseUser) {
        this.registration = registration;
        this.firebaseUser = firebaseUser;
    }

    private  Registration registration;
    private  FirebaseUser firebaseUser;
}
