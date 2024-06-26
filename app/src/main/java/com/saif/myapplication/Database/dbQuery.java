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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.saif.myapplication.Interface.dbCompleteListener;
import com.saif.myapplication.Model.CategoryModel;
import com.saif.myapplication.Model.QuestionModel;
import com.saif.myapplication.Model.RankModel;
import com.saif.myapplication.Model.TestModel;
import com.saif.myapplication.Model.UserModel;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class dbQuery {
    public static FirebaseFirestore firebaseFirestore;

    public static ArrayList<CategoryModel> categoryList = new ArrayList<>();

    public static int selected_Category_Index = 0;
    public static int selected_Test_Index = 0;

    public static ArrayList<TestModel> testList = new ArrayList<>();

    public  static UserModel dbuserModel = new UserModel("","","");
    public  static RankModel rankModel = new RankModel(0,-1,"");
    public static ArrayList<QuestionModel> questionList = new ArrayList<>();
    public static final int NOT_VISITED = 0;
    public static final int ANSWERED = 1;
    public static final int UNANSWERED = 2;
    public static final int REVIEW = 3;

    public static ArrayList<RankModel> userLeaderboardList = new ArrayList<>();
    public static int total_users_count = 0;
    public static boolean isCurUserInTopList = false;


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

                       if (documentSnapshot.getString("Phone_No")!=null ){
                           String curUserPhoneNo = documentSnapshot.getString("Phone_No");
                           dbuserModel.setUserPhoneNo(curUserPhoneNo);
                       }


                       rankModel.setScore(Math.toIntExact(documentSnapshot.getLong("TOTAL_SCORE")));
                       rankModel.setName(curUserName);
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

                getUserData(new dbCompleteListener() {
                    @Override
                    public void onSuccess() {
                        getUsersCount(dbCompleteListener);
                    }

                    @Override
                    public void onFailure() {
                        dbCompleteListener.onFailure();
                    }
                });
            }

            @Override
            public void onFailure() {
                dbCompleteListener.onFailure();
            }
        });
    }

    public static void loadQuestions(dbCompleteListener dbCompleteListener){
        firebaseFirestore.collection("QUESTIONS")
                .whereEqualTo("Category",categoryList.get(selected_Category_Index).getCAT_ID())
                .whereEqualTo("Test",testList.get(selected_Test_Index).getTestID()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                            questionList.add(new QuestionModel(
                                    documentSnapshot.getString("Question"),
                                    documentSnapshot.getString("A"),
                                    documentSnapshot.getString("B"),
                                    documentSnapshot.getString("C"),
                                    documentSnapshot.getString("D"),
                                    documentSnapshot.getString("Answer"),
                                    -1,NOT_VISITED
                            ));
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

    public static void saveScore(int score,dbCompleteListener dbCompleteListener){
        WriteBatch writeBatch = firebaseFirestore.batch();

        DocumentReference userDoc = firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid());
        writeBatch.update(userDoc,"TOTAL_SCORE",score);

        if (score > testList.get(selected_Test_Index).getMaxScore()){

            Log.d("score1234", String.valueOf(score)+testList.get(selected_Test_Index).getMaxScore());
            DocumentReference scoreDoc = firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid()).
                    collection("USER_DATA").document("MY_SCORE");

            Log.d("score1234", String.valueOf(scoreDoc));
            Map<String,Object> testData = new ArrayMap<>();
            testData.put(testList.get(selected_Test_Index).getTestID(),score);
            writeBatch.set(scoreDoc,testData, SetOptions.merge());

        }

        writeBatch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                if (score > testList.get(selected_Test_Index).getMaxScore()){
                    testList.get(selected_Test_Index).setMaxScore(score);
                }
                rankModel.setScore(score);
                dbCompleteListener.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dbCompleteListener.onFailure();
            }
        });
    }


    public static void loadMyScore(dbCompleteListener dbCompleteListener){
        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA").document("MY_SCORE").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        for (int i = 0;i<testList.size();i++){
                            int top = 0;
                            if (documentSnapshot.get(testList.get(i).getTestID())!=null){
                                top = documentSnapshot.getLong(testList.get(i).getTestID()).intValue();
                                Log.d("score1234", String.valueOf(top));
                            }
                            testList.get(i).setMaxScore(top);
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

    public static void saveProfileData(String name ,String phone ,dbCompleteListener dbCompleteListener){
        Map<String,Object> profileData = new ArrayMap<>();
        profileData.put("Name",name);
        if (phone!=null){
            profileData.put("Phone_No",phone);
        }

        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid())
                .update(profileData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        dbuserModel.setUserName(name);
                        if (phone!= null){
                            dbuserModel.setUserPhoneNo(phone);
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

    public static void getLeaderboardUsers(dbCompleteListener dbCompleteListener){
        userLeaderboardList.clear();

        firebaseFirestore.collection("Users")
                .whereGreaterThan("TOTAL_SCORE",0)
                .orderBy("TOTAL_SCORE", Query.Direction.DESCENDING)
                .limit(20)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int rank = 1;
                        for (QueryDocumentSnapshot doc:queryDocumentSnapshots){
                            userLeaderboardList.add(new RankModel(
                                    doc.getLong("TOTAL_SCORE").intValue(),
                                    rank,
                                    doc.getString("Name")
                            ));

                            if ((FirebaseAuth.getInstance().getUid()).compareTo(doc.getId()) == 0){
                                isCurUserInTopList = true;
                                rankModel.setRank(rank);
                            }
                            rank++;
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

    public static void getUsersCount(dbCompleteListener dbCompleteListener){

        firebaseFirestore.collection("Users").document("Total_Users")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        total_users_count = documentSnapshot.getLong("Count").intValue();
                        dbCompleteListener.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dbCompleteListener.onFailure();
                    }
                });
    }

    public static void saveQuestion(String category,String question,String answer,String test
            ,String optionA,String optionB,String optionC,String optionD,dbCompleteListener dbListener ){

        Map<String,Object> questionData = new ArrayMap<>();
        questionData.put("Category",category);
        questionData.put("Question",question);
        questionData.put("Answer",answer);
        questionData.put("Test",test);
        questionData.put("A",optionA);
        questionData.put("B",optionB);
        questionData.put("C",optionC);
        questionData.put("D",optionD);

         firebaseFirestore.collection("QUESTIONS").add(questionData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
             @Override
             public void onSuccess(DocumentReference documentReference) {
                 dbListener.onSuccess();
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 dbListener.onFailure();
             }
         });

    }
}
