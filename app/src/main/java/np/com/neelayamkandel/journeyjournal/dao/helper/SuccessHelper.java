package np.com.neelayamkandel.journeyjournal.dao.helper;

public class SuccessHelper {
    private final boolean isSuccess;
    private final String message;


    public SuccessHelper(boolean isSuccess, String message) {
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public SuccessHelper( boolean isSuccess) {
        this.isSuccess = isSuccess;
        this.message = null;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
