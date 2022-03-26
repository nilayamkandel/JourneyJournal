package np.com.neelayamkandel.journeyjournal.model.auth;

import np.com.neelayamkandel.journeyjournal.dao.auth.registration.RegistrationForm;
import np.com.neelayamkandel.journeyjournal.dao.helper.SuccessHelper;

public class RegistrationModel extends SuccessHelper {
    public void setRegistrationForm(RegistrationForm registrationForm) {
        this.registrationForm = registrationForm;
    }

    public RegistrationForm getRegistrationForm() {
        return registrationForm;
    }

    private RegistrationForm registrationForm;

    public RegistrationModel(boolean isSuccess, String message) {
        super(isSuccess, message);
    }

    public RegistrationModel(boolean isSuccess) {
        super(isSuccess);
    }
}
