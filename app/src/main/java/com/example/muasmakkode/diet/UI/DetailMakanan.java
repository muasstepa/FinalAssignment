package com.example.muasmakkode.diet.UI;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muasmakkode.diet.Data.MakananModel;
import com.example.muasmakkode.diet.MainActivity;
import com.example.muasmakkode.diet.R;
import com.example.muasmakkode.diet.db.DatabaseHandler;
import com.example.muasmakkode.diet.db.model.ModelMakanan;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMakanan extends AppCompatActivity {


    @BindView(R.id.textView_detail_kalori_makanan)
    TextView textViewDetailKaloriMakanan;
    @BindView(R.id.textView_detail_karbohidrat)
    TextView textViewDetailKarbohidrat;
    @BindView(R.id.textView_detail_protein)
    TextView textViewDetailProtein;
    @BindView(R.id.textView_detail_lemak)
    TextView textViewDetailLemak;
    @BindView(R.id.editText_jumlah_takaran)
    EditText editTextJumlahTakaran;
    @BindView(R.id.button_tambah)
    Button buttonTambah;

    @BindView(R.id.textView_jumlahurt)
    TextView textViewJumlahurt;

    ImageView imageViewPosterMakanan;
    double hasil_karbo;
    double hasil_kalori;
    double hasil_protein;
    double hasil_lemak;

    String judulTitleBar;

    private String nama, urt, kalori, karbohidrat, protein, lemak;

    private DatabaseHandler db;

    DecimalFormat df = new DecimalFormat("#.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan);
        ButterKnife.bind(this);

        imageViewPosterMakanan = (ImageView) findViewById(R.id.imageView_poster_makanan);

        final MakananModel makananModel;
        makananModel = getIntent().getParcelableExtra("makananModel");

        judulTitleBar = makananModel.getNama_makanan();
        /*getSupportActionBar().setTitle(judulTitleBar);*/

        /*/Instantiate database handler*/
        db = new DatabaseHandler(getApplicationContext());

        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact();
            }
        });


 /*textViewDetailNamaMakanan.setText(makananModel.getNama_makanan()); //kode set textview dari model*/

        textViewDetailKaloriMakanan.setText(makananModel.getKalori_makanan());
        textViewDetailKarbohidrat.setText(makananModel.getKarbo_makanan());
        textViewDetailProtein.setText(makananModel.getProtein_makanan());
        textViewDetailLemak.setText(makananModel.getLemak_makanan());
        textViewJumlahurt.setText(makananModel.getUkuran_saji());
        imageViewPosterMakanan.setImageResource(makananModel.getPoster_makanan());

        editTextJumlahTakaran.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {


                    hasil_karbo = Integer.parseInt(editTextJumlahTakaran.getText().toString())
                            * Double.parseDouble(makananModel.getKarbo_makanan().toString());

                    hasil_kalori = Integer.parseInt(editTextJumlahTakaran.getText().toString())
                            * Double.parseDouble(makananModel.getKalori_makanan().toString());

                    hasil_protein = Integer.parseInt(editTextJumlahTakaran.getText().toString())
                            * Double.parseDouble(makananModel.getProtein_makanan().toString());

                    hasil_lemak = Integer.parseInt(editTextJumlahTakaran.getText().toString())
                            * Double.parseDouble(makananModel.getLemak_makanan().toString());

                    textViewDetailKaloriMakanan.setText(String.valueOf((int) hasil_kalori));
                    textViewDetailKarbohidrat.setText(String.valueOf(df.format(hasil_karbo)));
                    textViewDetailProtein.setText(String.valueOf(df.format(hasil_protein)));
                    textViewDetailLemak.setText(String.valueOf(df.format(hasil_lemak)));


                } catch (Exception ex) {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // function to get values from the Edittext and image
    private void getValues() {
        nama = judulTitleBar.toString();
        urt = textViewJumlahurt.getText().toString();
        kalori = textViewDetailKaloriMakanan.getText().toString();
        karbohidrat = textViewDetailKarbohidrat.getText().toString();
        protein = textViewDetailProtein.getText().toString();
        lemak = textViewDetailLemak.getText().toString();
    }

    //Insert data to the database
    private void addContact() {
        getValues();

        db.addContacts(new ModelMakanan(nama, urt, kalori, karbohidrat, protein, lemak));
        Toast.makeText(this, "Berhasil menambahkan " + nama + " ke dalam daftar", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}