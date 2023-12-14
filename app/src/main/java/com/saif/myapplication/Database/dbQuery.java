package com.saif.myapplication.Database;

import android.util.ArrayMap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.saif.myapplication.Interface.dbCompleteListener;
import com.saif.myapplication.Model.CategoryModel;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class dbQuery {
    public static FirebaseFirestore firebaseFirestore;

    public static ArrayList<CategoryModel> categoryList = new ArrayList<>();

    public static void createUserData(String name, String email, dbCompleteListener dbListener) {
        Map<String, Object> userData = new ArrayMap<>();
        userData.put("Email_ID", email);
        userData.put("Name", name);
        userData.put("TOTAL_SCORE", 0);

        DocumentReference userDoc = firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        WriteBatch writeBatch = firebaseFirestore.batch();
        writeBatch.set(userDoc, userData);

        DocumentReference countDoc = firebaseFirestore.collection("Users").document("Total_Users");
        writeBatch.update(countDoc, "Count", FieldValue.increment(1));

        writeBatch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dbListener.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dbListener.onFailure();
            }
        });
    }


    public static void loadCategory(dbCompleteListener completeListener) {
        categoryList.clear();
        firebaseFirestore.collection("Quiz_Category").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        
                        Map<String, QueryDocumentSnapshot> docList = new ArrayMap<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            docList.put(doc.getId(), doc);
                        }
                        QueryDocumentSnapshot catListDoc = docList.get("CATEGORIES");

                        long catCount = (catListDoc.getLong("COUNT"));
                        Log.d("Category1234", String.valueOf(catCount));
                        for (long i = 1 ; i <= catCount; i++) {
                            String catID = catListDoc.getString("CAT_ID" + i);
                            Log.d("Category1234",catID);
                            QueryDocumentSnapshot catDoc = docList.get(catID);
                            Log.d("Category1234", String.valueOf(catDoc));
                            String noOfTest = catDoc.getString("NO_OF_TESTS");
                            Log.d("Category1234", String.valueOf(noOfTest));
                            String catName = catDoc.getString("NAME");
                            Log.d("Category1234", catName);
                            categoryList.add(new CategoryModel(catID, catName, noOfTest));
                            Log.d("Category1234", String.valueOf(new CategoryModel(catID, catName, noOfTest)));

                        }
                        completeListener.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();
                    }
                });
    }

}
