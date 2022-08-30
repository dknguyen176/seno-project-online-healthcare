package com.example.senocare.fragments.patient;

import static com.example.senocare.helper.SenoDB.getPatient;
import static com.example.senocare.helper.SenoDB.modifyDoctor;
import static com.example.senocare.helper.SenoDB.modifyPatient;
import static com.example.senocare.helper.ViewSupporter.putByteArrayToImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.activity.patient.PatientEditProfileActivity;
import com.example.senocare.helper.TimeConverter;
import com.example.senocare.helper.ViewSupporter;
import com.example.senocare.model.Patient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PatientProfileFragment extends Fragment {
    private static final int LAUNCH_EDIT = 1;
    private static final int GALLERY_ACTIVITY = 2;
    private static final int CAMERA_ACTIVITY = 3;

    Dialog dialog;
    ImageView profile_pic;
    Patient patient;
    byte[] img;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_patient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        patient = getPatient();
        img = patient.getImg();

        createDialog();

        setViewContent(view, patient);

        TextView editText = view.findViewById(R.id.edit_text);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), PatientEditProfileActivity.class), LAUNCH_EDIT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LAUNCH_EDIT) {
                Patient newPatient = (Patient) data.getParcelableExtra("patient");
                setViewContent(getView(), newPatient);
            }
            else if (requestCode == GALLERY_ACTIVITY) {
                Uri selectedImage = data.getData();
                try {
                    InputStream iStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    byte[] inputData = getBytes(iStream);

                    Bitmap bmp = BitmapFactory.decodeByteArray(inputData, 0, inputData.length);
                    bmp = Bitmap.createScaledBitmap(bmp, 100, 100, false);
                    profile_pic.setImageBitmap(bmp);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    img = stream.toByteArray();
                    modifyPatient(patient.get_id(), img);

                } catch (Exception e) {

                }
            }
            else if (requestCode == CAMERA_ACTIVITY) {
                Bitmap selectedImage = (Bitmap) data.getExtras().get("data");

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage = Bitmap.createScaledBitmap(selectedImage, 100, 100, false);
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

                profile_pic.setImageBitmap(selectedImage);

                img = stream.toByteArray();
                modifyDoctor(patient.get_id(), img);
            }
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        byte[] bytesResult = null;
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 10000;
        byte[] buffer = new byte[bufferSize];
        try {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            bytesResult = byteBuffer.toByteArray();
        } finally {
            // close the stream
            try{ byteBuffer.close(); } catch (IOException ignored){ /* do nothing */ }
        }
        return bytesResult;
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] choice = new String[] {"From Gallery", "From Camera"};
        builder.setTitle("Choose a picture")
                .setItems(choice, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto , GALLERY_ACTIVITY);
                        }
                        else{
                            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, CAMERA_ACTIVITY);
                        }
                    }
                });
        dialog = builder.create();
    }

    private void setViewContent(@NonNull View view, Patient patient) {

        TextView email = view.findViewById(R.id.email_content);
        email.setText(patient.getEmail());

        TextView name = view.findViewById(R.id.name_content);
        name.setText(patient.getName());

        TextView sex = view.findViewById(R.id.sex_content);
        sex.setText(patient.getSex());

        TextView birthday = view.findViewById(R.id.birthday_content);
        birthday.setText(TimeConverter.toString(patient.getBirth(), "dd/MM/yyyy"));

        TextView phone = view.findViewById(R.id.phone_content);
        phone.setText(patient.getPhone());

        TextView address = view.findViewById(R.id.address_content);
        address.setText(patient.getAddress());

        profile_pic = view.findViewById(R.id.profile_pic);
        putByteArrayToImageView(patient.getImg(), profile_pic, patient.getSex());
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }
}