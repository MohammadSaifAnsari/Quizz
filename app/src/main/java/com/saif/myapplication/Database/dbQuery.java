package com.saif.myapplication.Database;

import android.util.ArrayMap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.saif.myapplication.Interface.dbCompleteListener;
import com.saif.myapplication.Model.CategoryModel;
import com.saif.myapplication.Model.TestModel;
import com.saif.myapplication.Model.UserModel;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class dbQuery {
    public static FirebaseFirestore firebaseFirestore;

    public static ArrayList<CategoryModel> categoryList = new ArrayList<>();

    public static int selected_Category_Index = 0;

    public static ArrayList<TestModel> testList = new ArrayList<>();

    public  static UserModel dbuserModel = new UserModel("","");

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




    public  static void loadTestList(dbCompleteListener dbCompleteListener){
        testList.clear();

        firebaseFirestore.collection("Quiz_Category").document(categoryList.get(selected_Category_Index).getCAT_ID()).
                collection("TESTS").document("TEST_LIST").get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        int noOfTest = Integer.parseInt(categoryList.get(selected_Category_Index).getNO_OF_TEST());

                        for (int i = 1; i<= noOfTest;i++){
                            String testId = documentSnapshot.getString("TEST_ID"+ i);
                            String testTime = documentSnapshot.getString("TEST_TIME"+ i);
                            testList.add(new TestModel(testId,0,testTime));
                        }
                        dbCompleteListener.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dbCompleteListener.onFailure();
                    }
                });
    }


    public static void getUserData(dbCompleteListener dbCompleteListener){
        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid()).get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Log.d("header1234",String.valueOf(documentSnapshot));
                       String curUserName = documentSnapshot.getString("Name");
                       String curUserEmail = documentSnapshot.getString("Email_ID");
                       dbuserModel.setUserName(curUserName);
                       dbuserModel.setUserMail(curUserEmail);


                        dbCompleteListener.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dbCompleteListener.onFailure();
                    }
                });
    }

    public  static void loadData(dbCompleteListener dbCompleteListener){
        dbQuery.loadCategory(new dbCompleteListener() {
            @Override
            public void onSuccess() {
                getUserData(dbCompleteListener);
            }

            @Override
            public void onFailure() {
                dbCompleteListener.onFailure();
            }
        });
    }

}
