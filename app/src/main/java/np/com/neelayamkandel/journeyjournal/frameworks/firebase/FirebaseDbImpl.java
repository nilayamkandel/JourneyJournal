package np.com.neelayamkandel.journeyjournal.frameworks.firebase;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDbImpl {
    public FirebaseDatabase getDatabase() {
        return database;
    }

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final FirebaseStorageImpl storage = new FirebaseStorageImpl();
}
