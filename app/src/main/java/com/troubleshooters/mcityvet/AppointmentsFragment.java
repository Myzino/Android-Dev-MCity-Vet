package com.troubleshooters.mcityvet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentsFragment extends Fragment {

    private RecyclerView recyclerView;
    private AppointmentsAdapter appointmentsAdapter;
    private List<Appointment> appointmentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_appointments, container, false);

        // Initialize the appointment list
        appointmentList = new ArrayList<>();
        appointmentList.add(new Appointment("11:00 AM", "Jane","Dae", "Pending","","","","","",2,1,""));
        appointmentList.add(new Appointment("11:00 AM", "Jane","Dae", "Pending","","","","","",2,1,""));
        // Initialize RecyclerView and set adapter
        recyclerView = rootView.findViewById(R.id.appointments_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        appointmentsAdapter = new AppointmentsAdapter(appointmentList, this);
        recyclerView.setAdapter(appointmentsAdapter);

        // Fetch appointments from the server
        fetchAppointments();

        // Initialize the create appointment button
        Button createAppointmentButton = rootView.findViewById(R.id.create_appointment_btn);
        createAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the dialog fragment for booking appointment
                showBookAppointmentDialog();
            }
        });

        return rootView;
    }

    private void fetchAppointments() {
        String authToken = "XRpwetHFiPPam5284cJpHQDpMV1h6AZl"; // Replace this with your actual token retrieval logic
        AppointmentApi appointmentApi = RetrofitClient.getRetrofitInstance(authToken).create(AppointmentApi.class);
        Call<List<Appointment>> call = appointmentApi.getAllAppointment();

        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if (response.isSuccessful()) {
                    List<Appointment> appointments = response.body();
                    if (appointments != null && !appointments.isEmpty()) {
                        appointmentList.clear();
                        appointmentList.addAll(appointments);
                        appointmentsAdapter.notifyDataSetChanged();
                    } else {
                        // Handle the case where the response is empty
                        Toast.makeText(requireContext(), "No appointments found", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    // Handle 401 Unauthorized error
                    // For example, show a message to the user or prompt for authentication
                    Toast.makeText(requireContext(), "Unauthorized access. Please log in.", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle other non-successful response codes
                    String errorMessage = "Failed to fetch appointments";
                    Log.e("Appointment Fetch Error", errorMessage + " - Response code: " + response.code());
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                // Handle the error case
                String errorMessage = "Error fetching appointments: " + t.getMessage();
                Log.e("Appointment Fetch Error", errorMessage);
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showBookAppointmentDialog() {
        BookAppointmentDialogFragment dialog = new BookAppointmentDialogFragment();
        dialog.show(getParentFragmentManager(), "BookAppointmentDialogFragment");
    }
}
