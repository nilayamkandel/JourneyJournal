package np.com.neelayamkandel.journeyjournal.frameworks.firebase;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import np.com.neelayamkandel.journeyjournal.dao.auth.registration.Registration;
import np.com.neelayamkandel.journeyjournal.dao.auth.registration.RegistrationForm;
import np.com.neelayamkandel.journeyjournal.model.auth.RegistrationModel;

public class FirebaseAuthImpl {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseDbImpl database = new FirebaseDbImpl();
    private final FirebaseStorageImpl storage = new FirebaseStorageImpl();


    private final MutableLiveData<RegistrationModel> isRegistrationSuccess = new MutableLiveData<>();

    public FirebaseAuthImpl(){
        if (auth.getCurrentUser() != null) {
            //todo check if user is null or not
        }
    }

    public MutableLiveData<RegistrationModel> createUser(RegistrationForm registrationForm){
        //step-1 create user with email
        auth.createUserWithEmailAndPassword(registrationForm.getEmail(), registrationForm.getPassword())
                .addOnCompleteListener(createData->{
                    //step 2 success condition check
                    if(createData.isSuccessful()){
                        // step-3 enter in database
                        database.getDatabase()
                                .getReference("Account")
                                .child(auth.getCurrentUser().getUid())
                                .setValue(new Registration(registrationForm.getDisplayName(),registrationForm.getEmail()))
                                .addOnCompleteListener(addUser->{
                        //step-4 success check and redirection
                                    if(addUser.isSuccessful()){
                                        RegistrationModel registrationModel = new RegistrationModel(true,"User Registered Successfully");
                                        registrationModel.setRegistrationForm(registrationForm);
                                        isRegistrationSuccess.postValue(registrationModel);
                                    }else {
                        isRegistrationSuccess.postValue(new RegistrationModel(false, createData.getException().getMessage()));
                                    }
                                });
                    }
                    else{
                        isRegistrationSuccess.postValue(new RegistrationModel(false, createData.getException().getMessage()));
                    }
                });
        return isRegistrationSuccess;
    }

}

