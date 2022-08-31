package com.example.senocare.fragments.doctor;

import static android.app.Activity.RESULT_OK;
import static com.example.senocare.helper.SenoDB.getDoctor;
import static com.example.senocare.helper.SenoDB.modifyDoctor;
import static com.example.senocare.helper.SenoDB.modifyPatient;
import static com.example.senocare.helper.ViewSupporter.putByteArrayToImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.senocare.R;
import com.example.senocare.activity.doctor.DoctorEditProfileActivity;
import com.example.senocare.helper.TimeConverter;
import com.example.senocare.model.Doctor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class DoctorProfileFragment extends Fragment {

    private static final int LAUNCH_EDIT = 1;
    private static final int GALLERY_ACTIVITY = 2;
    private static final int CAMERA_ACTIVITY = 3;

    Dialog dialog;
    ImageView profile_pic;
    Doctor doctor;
    byte[] img;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_doctor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        doctor = getDoctor();

        img = doctor.getImg();

        createDialog();

        profile_pic = view.findViewById(R.id.profile_pic);
        putByteArrayToImageView(doctor.getImg(), profile_pic, doctor.getSex());

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        setViewContent(view, doctor);

        TextView editText = view.findViewById(R.id.edit_text);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), DoctorEditProfileActivity.class), LAUNCH_EDIT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == LAUNCH_EDIT) {
                Doctor newDoctor = (Doctor) data.getParcelableExtra("doctor");
                setViewContent(getView(), newDoctor);
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
                    modifyDoctor(doctor.get_id(), img);

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
                modifyDoctor(doctor.get_id(), img);

            }
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        byte[] bytesResult = null;
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
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
        String[] choice = new String[] {"Set Default", "From Gallery", "From Camera"};
        builder.setTitle("Choose a picture")
                .setItems(choice, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            img = null;
                            modifyDoctor(doctor.get_id(), null);
                            putByteArrayToImageView(null, profile_pic, doctor.getSex());
                        }
                        else if (which == 1){
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

    private void setViewContent(@NonNull View view, Doctor doctor) {

        TextView email = view.findViewById(R.id.email_content);
        email.setText(doctor.getEmail());

        TextView name = view.findViewById(R.id.name_content);
        name.setText(doctor.getName());

        TextView sex = view.findViewById(R.id.sex_content);
        sex.setText(doctor.getSex());

        TextView birthday = view.findViewById(R.id.birthday_content);
        birthday.setText(TimeConverter.toString(doctor.getBirth(), "dd/MM/yyyy"));

        TextView spec = view.findViewById(R.id.spec_content);
        spec.setText(doctor.getSpec());

        TextView location = view.findViewById(R.id.location_content);
        location.setText(doctor.getLoc());

        TextView bio = view.findViewById(R.id.bio_content);
        bio.setText(doctor.getBio());

        TextView exper = view.findViewById(R.id.exper_content);
        exper.setText(String.valueOf(doctor.getExper()));

        if (img == null) putByteArrayToImageView(null, profile_pic, doctor.getSex());
    }

}