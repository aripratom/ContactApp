package com.aripratom.aplikasikontak;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.aripratom.aplikasikontak.db.AppDatabase;
import com.aripratom.aplikasikontak.model.Contact;

import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    private EditText txtNama, txtTglLahir, txtPhone, txtEmail;
    private Spinner spPekerjaan;
    private RadioGroup rbJkGroup;
    private RadioButton rbL, rbP, rbSelected;
    private Button btnSave;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String tglLahirDb;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        setTitle("Form Tambah/Ubah Kontak");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNama = findViewById(R.id.txt_nama);
        txtTglLahir = findViewById(R.id.txt_tgl_lahir);
        txtEmail = findViewById(R.id.txt_email);
        txtPhone = findViewById(R.id.txt_telepon);
        spPekerjaan = findViewById(R.id.spn_pekerjaan);
        rbJkGroup = findViewById(R.id.rb_jk_group);
        rbL = findViewById(R.id.rb_l);
        rbP = findViewById(R.id.rb_p);
        btnSave = findViewById(R.id.btn_save);

        String[] pekerjaan = {"Mahasiswa", "Dosen", "Karyawan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FormActivity.this,
                android.R.layout.simple_spinner_dropdown_item, pekerjaan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPekerjaan.setAdapter(adapter);
        Intent intent = getIntent();
        contact = (Contact) intent.getSerializableExtra("contact");

        if (contact != null) {
            txtNama.setText(contact.nama);
            txtEmail.setText(contact.email);
            txtPhone.setText(contact.telpon);
            txtTglLahir.setText(contact.tgllahir);
            if (contact.jeniskelamin.equalsIgnoreCase("Laki-laki")) {
                rbL.setChecked(true);
            } else {
                rbP.setChecked(true);
            }

            for (int i = 0; i < pekerjaan.length; i++) {
                String s = pekerjaan[i];
                if (s.equalsIgnoreCase(contact.pekerjaan)) {
                    spPekerjaan.setSelection(i);
                }
            }
        }

        txtTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FormActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                txtTglLahir.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                tglLahirDb = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = txtNama.getText().toString();
                String email = txtEmail.getText().toString();
                String phone = txtPhone.getText().toString();
                String tglLahir = tglLahirDb;
                String selectedPekerjaan = (String) spPekerjaan.getSelectedItem();
                rbSelected = findViewById(rbJkGroup.getCheckedRadioButtonId());
                String jenisKelamin = rbSelected.getText().toString();

                if (TextUtils.isEmpty(nama)) {
                    txtNama.setError("Nama Tidak Boleh Kosong");
                    return;
                }

                if (contact == null) {
                    contact = new Contact();
                    contact.nama = nama;
                    contact.tgllahir = tglLahir;
                    contact.jeniskelamin = jenisKelamin;
                    contact.pekerjaan = selectedPekerjaan;
                    contact.email = email;
                    contact.telpon = phone;

                    AppDatabase.getInstance(FormActivity.this)
                            .contactDao().insert(contact);
                    Toast.makeText(FormActivity.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                } else {
                    rbSelected = (RadioButton) findViewById(rbJkGroup.getCheckedRadioButtonId());
                    contact.nama = txtNama.getText().toString();
                    contact.email = txtEmail.getText().toString();
                    contact.jeniskelamin = rbSelected.getText().toString();
                    contact.telpon = txtPhone.getText().toString();
                    String selectedItem = (String) spPekerjaan.getSelectedItem();
                    contact.pekerjaan = selectedItem;
                    contact.tgllahir = txtTglLahir.getText().toString();
                    AppDatabase.getInstance(getApplicationContext()).contactDao().update(contact);
                }

                Intent intent1 = new Intent(FormActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

    }
}
