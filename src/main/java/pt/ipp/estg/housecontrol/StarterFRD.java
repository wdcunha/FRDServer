package pt.ipp.estg.housecontrol;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.IOException;

class StarterFRD {

    public static void main(String[] args) throws IOException {

        FileInputStream refreshToken = new FileInputStream("/Users/wdcunha/Development/Java/frdproj/src/main/resources/housecontrolmobile-firebase-adminsdk-qv0hl-f41a07409d.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .setDatabaseUrl("https://housecontrolmobile.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);

     // As an admin, the app has access to read and write all data, regardless of Security Rules
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("users");

        System.out.println("ref: "+ref);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object document = dataSnapshot.getValue();
                System.out.println("document: "+document);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("canceled: "+error.toException());
            }
        });

        Utilizador utilz = new Utilizador("Euquipe", "eu@quipe.com");

        System.out.println("utilz nome: "+utilz.getNome());
        System.out.println("utilz email: "+utilz.getEmail());

        ref.child("2").push().setValueAsync(utilz);

        ref.child("3").setValue("I'm writing data", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully."+databaseReference);
                }
            }
        });
    }
}
