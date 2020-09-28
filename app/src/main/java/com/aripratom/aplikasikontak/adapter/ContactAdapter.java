package com.aripratom.aplikasikontak.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aripratom.aplikasikontak.FormActivity;
import com.aripratom.aplikasikontak.MainActivity;
import com.aripratom.aplikasikontak.R;
import com.aripratom.aplikasikontak.db.AppDatabase;
import com.aripratom.aplikasikontak.model.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    Context context;
    List<Contact> contacts;

    public ContactAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_contact, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return (contacts != null) ? contacts.size() : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Contact contact = contacts.get(i);
        viewHolder.txtNama.setText(contact.nama);
        viewHolder.txtEmail.setText(contact.email);
        viewHolder.txtTgllahir.setText(contact.tgllahir);
        viewHolder.txtPekerjaan.setText(contact.pekerjaan);
        viewHolder.txtTelpon.setText(contact.telpon);
        viewHolder.txtJk.setText(contact.jeniskelamin);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FormActivity.class);
                intent.putExtra("contact",contact);
                context.startActivity(intent);
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Hapus data?");
                builder.setPositiveButton("Hapus",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                delete(contact);
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama, txtEmail, txtTgllahir, txtTelpon, txtJk, txtPekerjaan;
        CardView cardView;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txt_nama);
            txtEmail =  itemView.findViewById(R.id.txt_email);
            txtTgllahir =  itemView.findViewById(R.id.txt_tgllahir);
            txtTelpon =  itemView.findViewById(R.id.txt_telpon);
            txtPekerjaan = itemView.findViewById(R.id.txt_pekerjaan);
            txtJk =  itemView.findViewById(R.id.txt_jk);
            cardView = itemView.findViewById(R.id.card);
            btnDelete =  itemView.findViewById(R.id.btn_delete);
        }
    }

    private void delete(Contact contact){
        AppDatabase.getInstance(context).contactDao().delete(contact);
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
