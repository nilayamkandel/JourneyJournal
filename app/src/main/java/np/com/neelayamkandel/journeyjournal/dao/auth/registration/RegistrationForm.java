package np.com.neelayamkandel.journeyjournal.dao.auth.registration;

public class RegistrationForm{

    public RegistrationForm(String displayName, String email,String password, String confirmPassword) {
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    private String displayName;
    private String email;
    private String password;
    private String confirmPassword;

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public RegistrationForm() {
        this.displayName = null;
        this.email = null;
        this.password = null;
        this.confirmPassword = null;
    }


}


