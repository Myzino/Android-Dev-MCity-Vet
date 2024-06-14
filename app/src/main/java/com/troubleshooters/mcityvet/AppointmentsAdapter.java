package com.troubleshooters.mcityvet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private Context context;
    private List<Appointment> appointmentList;
    private Fragment fragment;

    public AppointmentsAdapter(List<Appointment> appointmentList, Fragment fragment) {
        this.appointmentList = appointmentList;
        this.fragment = fragment;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_HEADER) {
            // Handle header binding if necessary
        } else {
            int adjustedPosition = position - 1; // Adjust position for header
            Appointment appointment = appointmentList.get(adjustedPosition);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.scheduleTextView.setText(appointment.getSchedule());
            itemViewHolder.clientTextView.setText(appointment.getFirstName() + " " + appointment.getLastName());
            itemViewHolder.statusTextView.setText(appointment.getStatus());

            itemViewHolder.viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = itemViewHolder.getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        Fragment appointmentRecordFragment = new AppointmentRecordFragment();
                        FragmentTransaction transaction = fragment.getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, appointmentRecordFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            });

            itemViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = itemViewHolder.getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        new AlertDialog.Builder(fragment.getContext())
                                .setTitle("Delete Appointment")
                                .setMessage("Are you sure you want to delete this appointment?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        appointmentList.remove(currentPosition - 1);
                                        notifyItemRemoved(currentPosition);
                                        notifyItemRangeChanged(currentPosition, appointmentList.size());
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
            });

            itemViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    EditAppointmentDialogFragment dialogFragment = new EditAppointmentDialogFragment();
                    dialogFragment.show(fragmentManager, "EditAppointmentDialog");

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return appointmentList.size() + 1; // Add one for the header
    }



    private static AppointmentsAdapter newInstance(Appointment appointment) {
        return null;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView scheduleTextView;
        TextView clientTextView;
        TextView statusTextView;
        Button viewButton;
        Button deleteButton;
        Button editButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            scheduleTextView = itemView.findViewById(R.id.tv_schedule);
            clientTextView = itemView.findViewById(R.id.tv_client);
            statusTextView = itemView.findViewById(R.id.tv_status);
            viewButton = itemView.findViewById(R.id.view_btn);
            deleteButton = itemView.findViewById(R.id.delete_btn);
            editButton = itemView.findViewById(R.id.edit_btn);
        }
    }
}
