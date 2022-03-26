package np.com.neelayamkandel.journeyjournal.model.auth;

import np.com.neelayamkandel.journeyjournal.dao.auth.login.LoginProfile;
import np.com.neelayamkandel.journeyjournal.dao.helper.SuccessHelper;

public class UserProfileModel extends SuccessHelper {
    public LoginProfile getLoginProfile() {
        return loginProfile;
    }

    public void setLoginProfile(LoginProfile loginProfile) {
        this.loginProfile = loginProfile;
    }

    private LoginProfile loginProfile;

    public UserProfileModel(boolean isSuccess, String message) {
        super(isSuccess, message);
    }

    public UserProfileModel(boolean isSuccess) {
        super(isSuccess);
    }
}
