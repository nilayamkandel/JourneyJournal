package np.com.neelayamkandel.journeyjournal.frameworks.firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ConcurrentModificationException;

import np.com.neelayamkandel.journeyjournal.dao.helper.SuccessHelper;

public class FirebaseStorageImpl {
    private final FirebaseStorage storage= FirebaseStorage.getInstance();

    public MutableLiveData<SuccessHelper> getIsUploadSuccess() {
        return IsUploadSuccess;
    }

    public MutableLiveData<SuccessHelper> getIsDeleteSuccess() {
        return IsDeleteSuccess;
    }

    private final MutableLiveData<SuccessHelper> IsUploadSuccess = new MutableLiveData<>();
    private final MutableLiveData<SuccessHelper> IsDeleteSuccess = new MutableLiveData<>();


    public void UploadBitmap(Bitmap image, String Filepath, String Filename, Context context){
        if(image != null && Filename != null && Filepath != null ){
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Image uploading...");
            progressDialog.show();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            StorageReference reference = storage.getReference().child(Filepath + Filename);
            reference.putBytes(data).addOnSuccessListener(imageData ->{
                reference.getDownloadUrl().addOnSuccessListener(uri -> {
                    progressDialog.dismiss();
                    IsUploadSuccess.postValue(new SuccessHelper(true , uri.toString()));
                });
            })
            .addOnFailureListener(failure ->{
                progressDialog.dismiss();
                IsUploadSuccess.postValue(new SuccessHelper(false , failure.getMessage()));
            })
            .addOnProgressListener(progress->{
                double success = (100.0 * progress.getBytesTransferred()) / progress.getTotalByteCount();
                progressDialog.setMessage("Uploaded" + (int)success + "%");
            });


        }
    }

    public void UploadUri(Uri image, String Filepath, String Filename, Context context){
        if(image != null && Filename != null && Filepath != null ){
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Image uploading...");
            progressDialog.show();

            StorageReference reference = storage.getReference().child(Filepath + Filename);
            reference.putFile(image).addOnSuccessListener(imageData ->{
                reference.getDownloadUrl().addOnSuccessListener(uri -> {
                    progressDialog.dismiss();
                    IsUploadSuccess.postValue(new SuccessHelper(true , uri.toString()));
                });
            })
                    .addOnFailureListener(failure ->{
                        progressDialog.dismiss();
                        IsUploadSuccess.postValue(new SuccessHelper(false , failure.getMessage()));
                    })
                    .addOnProgressListener(progress->{
                        double success = (100.0 * progress.getBytesTransferred()) / progress.getTotalByteCount();
                        progressDialog.setMessage("Uploaded" + (int)success + "%");
                    });


        }

    }

    public void Delete(String uri){
        if(uri != null){
            storage.getReferenceFromUrl(uri).delete().addOnSuccessListener(success ->{
                IsDeleteSuccess.postValue(new SuccessHelper(true, "Image Deleted Successfully!!"));
            }).addOnFailureListener(failure->{
                IsDeleteSuccess.postValue(new SuccessHelper(false, failure.getMessage()));
            });
        }
    }
}
