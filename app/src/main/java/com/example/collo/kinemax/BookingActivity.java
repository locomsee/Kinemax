package com.example.collo.kinemax;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {

    //database reference
    public DatabaseReference mDatabase;

    FirebaseAuth firebaseAuth;


    GridView gridView;
    Button bookseat;
    int seatNo;
    String Useremail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();


        //Initializing Database
        mDatabase= FirebaseDatabase.getInstance().getReference("Tickets").push();//getting unique id



        //Initialization
        gridView=(GridView)findViewById(R.id.Gridview);
        bookseat=(Button)findViewById(R.id.ButtonBook);

        Useremail=user.getEmail();



        ImageAdapter imageAdapter=new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);
        bookseat.setOnClickListener(this);


           //Gridview Item listener
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    seatNo=position+ 1;


                }


            });


    }

    public void currentUser(){



        mDatabase.child("UserEmail").setValue(Useremail);//useremail


    }



    @Override
    public void onClick (View v)   {
        //Message on click
        Toast.makeText(getApplicationContext(),"Please wait...",Toast.LENGTH_SHORT).show();

       //Bookseat method activated onclick


          if(seatNo==1){

             validSeat();


          }

       /* if(seatNo==2){

            currentUser();
            BookSeat();
        }
        if(seatNo==3){
            currentUser();
            BookSeat();
        }
        if(seatNo==4){
            currentUser();
            BookSeat();
        }
        if(seatNo==5){
            currentUser();
            BookSeat();
        }
        if(seatNo==6){
            currentUser();
            BookSeat();
        }
        if(seatNo==7){
            currentUser();
            BookSeat();
        }
        if(seatNo==8){
            currentUser();
            BookSeat();
        }
        if(seatNo==9){
            currentUser();
            BookSeat();
        }
        if(seatNo==10){
            currentUser();
            BookSeat();
        }
        if(seatNo==11){
            currentUser();
            BookSeat();
        }
        if(seatNo==12){
            currentUser();
            BookSeat();
        }*/


    }

    public void BookSeat(){
      


        mDatabase.child("seatNo").setValue(seatNo, new DatabaseReference.CompletionListener() {


            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                //Problem with saving the data
                if (databaseError != null) {

                    Toast.makeText(getApplicationContext(),"Server Unavailable",Toast.LENGTH_LONG).show();

                } else {
                    //Data uploaded successfully on the server



                    //Book dialog Box
                    AlertDialog alertDialog = new AlertDialog.Builder(BookingActivity.this).create();
                    alertDialog.setTitle("Ticket Information");
                    alertDialog.setMessage("You have succesfully booked Seat Number " + seatNo);
                    alertDialog.setIcon(R.drawable.bookicon);

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent bk = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(bk);

                        }
                    });

                    alertDialog.show();//showing the dialog view
                }


            }

        }
        );
         }

         public  boolean validSeat(){
             mDatabase= FirebaseDatabase.getInstance().getReference().child("Tickets");

        mDatabase.orderByChild("seatNo").equalTo(seatNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    //username exist
                    Toast.makeText(getApplicationContext(), "Sorry the seat is already bought", Toast.LENGTH_LONG).show();
                }else {
                    BookSeat();
                    currentUser();
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

          return true;
         }
        

}
