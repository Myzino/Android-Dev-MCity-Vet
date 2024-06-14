package com.troubleshooters.mcityvet;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointmentDialogFragment extends DialogFragment {

    private Spinner technicianSpinner;
    private List<String> technicianList;
    private EditText first_name_edit_text, last_name_edit_text,
            address_name_edit_text, landmark_name_edit_text, email_name_edit_text,
            phone_number_edit_text, age_number, number_head_edit;

    private CheckBox checkboxOption1, checkboxOption2, checkboxOption3, checkboxOption4, checkboxOption5;
    private TextView calendarTextView;
    private StringBuilder selectedServices;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.book_appointment, null);

        // Find and initialize the EditText fields
        first_name_edit_text = view.findViewById(R.id.first_name_edit_text);
        last_name_edit_text = view.findViewById(R.id.last_name_edit_text);
        address_name_edit_text = view.findViewById(R.id.address_name_edit_text);
        landmark_name_edit_text = view.findViewById(R.id.landmark_name_edit_text);
        email_name_edit_text = view.findViewById(R.id.email_name_edit_text);
        phone_number_edit_text = view.findViewById(R.id.phone_number_edit_text);
        age_number = view.findViewById(R.id.age_number);
        number_head_edit = view.findViewById(R.id.number_head_edit);
        checkboxOption1 = view.findViewById(R.id.checkbox_option_1);

        // Find the calendar TextView
        calendarTextView = view.findViewById(R.id.calendar);

//        // Initialize checkboxes
//        checkboxOption1 = view.findViewById(R.id.checkbox_option_1);
//        checkboxOption2 = view.findViewById(R.id.checkbox_option_2);
//        checkboxOption3 = view.findViewById(R.id.checkbox_option_3);
//        checkboxOption4 = view.findViewById(R.id.checkbox_option_4);
//        checkboxOption5 = view.findViewById(R.id.checkbox_option_5);
//
//        selectedServices = new StringBuilder();
//
//        // Set up listeners for the checkboxes
//        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                updateSelectedServices();
//            }
//        };
//
//        checkboxOption1.setOnCheckedChangeListener(listener);
//        checkboxOption2.setOnCheckedChangeListener(listener);
//        checkboxOption3.setOnCheckedChangeListener(listener);
//        checkboxOption4.setOnCheckedChangeListener(listener);
//        checkboxOption5.setOnCheckedChangeListener(listener);

        // Set click listener for the calendar TextView
        calendarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog and set the listener
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date to the calendar TextView
                        String selectedDate = String.format(Locale.getDefault(), "%d/%02d/%02d", year, monthOfYear + 1, dayOfMonth);
                        calendarTextView.setText(selectedDate);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        // Find the close button
        Button closeButton = view.findViewById(R.id.close_button);

        // Set click listener for the close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the dialog fragment and return to the parent fragment
                dismiss();
            }
        });

        // Find the technician spinner
        technicianSpinner = view.findViewById(R.id.technician_spinner);

        // Populate technician list (replace with your actual list)
        technicianList = Arrays.asList("66403bf9bb5b020c7934361b", "66462eae4ff845a2c83f7784", "664e6528bed02b386158c195");

        // Create an ArrayAdapter using the technician list and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, technicianList);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        technicianSpinner.setAdapter(adapter);

        Spinner animalTypeSpinner = view.findViewById(R.id.animal_type_spinner);
        // Populate the animal type spinner with your options
        List<String> animalTypeList = Arrays.asList("Cow", "Pig", "Horse");
        ArrayAdapter<String> animalTypeAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, animalTypeList);
        animalTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        animalTypeSpinner.setAdapter(animalTypeAdapter);

        Button cancelButton = view.findViewById(R.id.cancel);

        // Set click listener for the cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog fragment
                dismiss();
            }
        });

        Button create_button = view.findViewById(R.id.create_appointment);

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAppointment(); // Renamed method to follow Java naming conventions
            }

            private void addAppointment() {
                // Check for null views before accessing their properties
                if (first_name_edit_text == null || last_name_edit_text == null || technicianSpinner == null ||
                        calendarTextView == null || address_name_edit_text == null || landmark_name_edit_text == null ||
                        email_name_edit_text == null || phone_number_edit_text == null || animalTypeSpinner == null ||
                        age_number == null || number_head_edit == null) {
                    // Log an error and return early to avoid further processing
                    Log.e("AddAppointment", "One or more views are null");
                    return;
                }

                String firstName = first_name_edit_text.getText().toString().trim();
                String lastName = last_name_edit_text.getText().toString().trim();
                String technicianName = technicianSpinner.getSelectedItem().toString();
                String schedule = calendarTextView.getText().toString().trim();
                String address = address_name_edit_text.getText().toString().trim();
                String landmark = landmark_name_edit_text.getText().toString().trim();
                String email = email_name_edit_text.getText().toString().trim();
                String phone = phone_number_edit_text.getText().toString().trim();
                String patient = animalTypeSpinner.getSelectedItem().toString();
                String ageString = age_number.getText().toString().trim();
                int age = 0; // Default value if parsing fails
                if (!ageString.isEmpty()) {
                    try {
                        age = Integer.parseInt(ageString);
                    } catch (NumberFormatException e) {
                        // Log an error and return early if ageString cannot be parsed
                        Log.e("AddAppointment", "Failed to parse ageString to integer", e);
                        return;
                    }
                }
                String numberOfHeadsString = number_head_edit.getText().toString().trim();
                int numberOfHeads = 0; // Default value if parsing fails
                if (!numberOfHeadsString.isEmpty()) {
                    try {
                        age = Integer.parseInt(numberOfHeadsString);
                    } catch (NumberFormatException e) {
                        // Log an error and return early if ageString cannot be parsed
                        Log.e("AddAppointment", "Failed to parse numberOfHeads to integer", e);
                        return;
                    }
                }
                String services = checkboxOption1.getText().toString().trim();

                if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(technicianName) ||
                        TextUtils.isEmpty(schedule) || TextUtils.isEmpty(address) || TextUtils.isEmpty(landmark) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(patient) ||
                        TextUtils.isEmpty(ageString) || TextUtils.isEmpty(numberOfHeadsString)) {
                    Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Appointment appointment = new Appointment(firstName, lastName, technicianName, schedule, address, landmark, email, phone, patient, age, numberOfHeads, services);

                // Pass the token here
                String authToken = "XRpwetHFiPPam5284cJpHQDpMV1h6AZl"; // Replace this with your actual token retrieval logic
                AppointmentApi appointmentApi = RetrofitClient.getRetrofitInstance(authToken).create(AppointmentApi.class);
                Call<Appointment> call = appointmentApi.createAppointment(appointment);

                call.enqueue(new Callback<Appointment>() {
                    @Override
                    public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Appointment created successfully
                            Toast.makeText(requireContext(), "Appointment created successfully", Toast.LENGTH_SHORT).show();
                            dismiss(); // Dismiss the dialog fragment
                        } else {
                            // Failed to create appointment
                            String errorMessage = "Failed to create appointment";
                            if (response.errorBody() != null) {
                                try {
                                    errorMessage = response.errorBody().string();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            Log.e("AddAppointment", "Failed to create appointment: " + errorMessage);
                        }
                    }

                    @Override
                    public void onFailure(Call<Appointment> call, Throwable t) {
                        // Handle API call failure
                        Toast.makeText(requireContext(), "Failure on creating an appointment", Toast.LENGTH_SHORT).show();
                        Log.e("AddAppointment", "Failure on API call", t);
                    }
                });
            }
        });

        builder.setView(view);
        return builder.create();
    }

}
