package np.com.neelayamkandel.journeyjournal.frameworks.firebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

import np.com.neelayamkandel.journeyjournal.dao.helper.SuccessHelper;
import np.com.neelayamkandel.journeyjournal.dao.home.JourneyDao;
import np.com.neelayamkandel.journeyjournal.dao.home.JourneyRecyclerDao;
import np.com.neelayamkandel.journeyjournal.model.home.JourneyModel;


public class FirebaseDbImpl {
    private String TAG = "J_" + FirebaseDbImpl.class.getSimpleName();
    private final String TableName = "Journey";
    private FirebaseStorageImpl storage = new FirebaseStorageImpl();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public MutableLiveData<JourneyModel> Addjourney = new MutableLiveData<>();
    public MutableLiveData<JourneyModel> Updatejourney = new MutableLiveData<>();
    public MutableLiveData<SuccessHelper> Deletejourney = new MutableLiveData<>();
    public MutableLiveData<ArrayList<JourneyRecyclerDao>> Fetchjourney = new MutableLiveData<>();

    public MutableLiveData<JourneyModel> getAddJourney() {
        return Addjourney;
    }
    public MutableLiveData<JourneyModel> getUpdateJourney() {
        return Updatejourney;
    }
    public MutableLiveData<SuccessHelper> getDeleteJourney() {
        return Deletejourney;
    }
    public MutableLiveData<ArrayList<JourneyRecyclerDao>> getFetchJourney() {
        return Fetchjourney;
    }


    public void AddJourney(JourneyDao journeyDao, Uri Image, Context context,
                           LifecycleOwner Owner, boolean Camera, Bitmap bitmap) {
        String Id = RandomId();
        if (Image != null || bitmap != null) {
            String ImagePath = "Images/Journey/" + journeyDao.getUser() + "/" ;
            if (Camera) {
                storage.UploadBitmap(bitmap, ImagePath, Id, context);
            } else {
                storage.UploadUri(Image, ImagePath, Id, context);
            }
//step-2
            storage.getIsUploadSuccess().observe(Owner, successHelper -> {
                if (successHelper.isSuccess()){
                    database.getReference(TableName)
                            .child(journeyDao.getUser())
                            .child(Id)
                            .setValue(journeyDao)
                            .addOnCompleteListener(status -> {
                                JourneyModel journeyModel;
                                if (status.isSuccessful()) {
                                    journeyModel = new JourneyModel(true, "Journey diary added Successfully!!");
                                } else {
                                    journeyModel = new JourneyModel(false, status.getException().getMessage());
                                }
                                journeyModel.setDao(journeyDao );
                                Addjourney.postValue(journeyModel);
                            });
                }
                else{
                    Addjourney.postValue(new JourneyModel(false, successHelper.getMessage()));
                }
            });
        }

        else{
            database.getReference(TableName)
                    .child(journeyDao.getUser())
                    .child(Id)
                    .setValue(journeyDao)
                    .addOnCompleteListener(status -> {
                        JourneyModel journeyModel;
                        if (status.isSuccessful()) {
                            journeyModel = new JourneyModel(true, "Journey diary added Successfully!!");
                        } else {
                            journeyModel = new JourneyModel(false, status.getException().getMessage());
                        }
                        journeyModel.setDao(journeyDao );
                        Addjourney.postValue(journeyModel);
                    });
        }
    }


    public void UpdateJourney(String JourneyId, JourneyDao journeyDao, Uri Image, Context context,
                              LifecycleOwner Owner, boolean Camera, Bitmap bitmap){
        if (Image != null || bitmap != null) {
            String ImagePath = "Images/Journey/" + journeyDao.getUser() + "/" ;
            if (Camera) {
                storage.UploadBitmap(bitmap, ImagePath, JourneyId, context);
            } else {
                storage.UploadUri(Image, ImagePath, JourneyId, context);
            }
//step-2
            storage.getIsUploadSuccess().observe(Owner, successHelper -> {
                if (successHelper.isSuccess()){
                    database.getReference(TableName)
                            .child(journeyDao.getUser())
                            .child(JourneyId)
                            .setValue(journeyDao)
                            .addOnCompleteListener(status -> {
                                JourneyModel journeyModel;
                                if (status.isSuccessful()) {
                                    journeyModel = new JourneyModel(true, "Journey diary updated Successfully!!");
                                } else {
                                    journeyModel = new JourneyModel(false, status.getException().getMessage());
                                }
                                journeyModel.setDao(journeyDao );
                                Updatejourney.postValue(journeyModel);
                            });
                }
                else{
                    Updatejourney.postValue(new JourneyModel(false, successHelper.getMessage()));
                }
            });
        }
        else{
            database.getReference(TableName)
                    .child(journeyDao.getUser())
                    .child(JourneyId)
                    .setValue(journeyDao)
                    .addOnCompleteListener(status -> {
                        JourneyModel journeyModel;
                        if (status.isSuccessful()) {
                            journeyModel = new JourneyModel(true, "Journey diary updated Successfully!!");
                        } else {
                            journeyModel = new JourneyModel(false, status.getException().getMessage());
                        }
                        journeyModel.setDao(journeyDao );
                        Updatejourney.postValue(journeyModel);
                    });
        }
    }


    public void DeleteJourney(JourneyDao journeyDao,String JourneyId,  LifecycleOwner Owner){

    }



    public void FetchJourney(String Uuid){
        database.getReference(TableName).child(Uuid).get().addOnSuccessListener(dataSet->{
            if(dataSet!= null){
                ArrayList<JourneyRecyclerDao> journey = new ArrayList<>();
                for(DataSnapshot dataSnapshot: dataSet.getChildren()){
                    JourneyDao journeyDao = dataSnapshot.getValue(JourneyDao.class);
                    journey.add(new JourneyRecyclerDao(dataSnapshot.getKey(), journeyDao));
                }
                Fetchjourney.postValue(journey);
            }
            else{
                Fetchjourney.postValue(new ArrayList<>());
            }
        });
    }





    public FirebaseDatabase getDatabase() {
        return database;
    }
    public long RandomNumber(){
        int maximum = 200;
        int minimum = 1;
        return minimum + (int)Math.random() * maximum;
    }

    public String RandomId(){
        long RandomNumberValue = new Date().getTime()/(RandomNumber()*RandomNumber());
        return "JOURNEY_" + RandomNumberValue;
    }





}
