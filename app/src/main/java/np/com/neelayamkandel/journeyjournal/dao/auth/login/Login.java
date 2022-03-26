package np.com.neelayamkandel.journeyjournal.dao.auth.login;

public class Login {
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private final String email;
    private final String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
