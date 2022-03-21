package np.com.neelayamkandel.journeyjournal.viewmodel;

public class HelperViewModel {
    private final String message;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    private final boolean success;

    public HelperViewModel(boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    public HelperViewModel(boolean success) {
        this.message = null;
        this.success = success;
    }

}
