package com.example.keshar.architecture_example_mvvm.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.keshar.architecture_example_mvvm.R;
import com.example.keshar.architecture_example_mvvm.RoomDatabase.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<NoteEntity> notes = new ArrayList<>();
    private Context context;
    private OnItemClickListerner listerner;

    public NoteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.note_item, viewGroup, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {

        NoteEntity noteEntity = notes.get(i);
        noteHolder.txtPriority.setText(String.valueOf(noteEntity.getPriority()));
        noteHolder.txtTitle.setText(noteEntity.getTitle());
        noteHolder.txtDescription.setText(noteEntity.getDescription());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<NoteEntity> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public NoteEntity getNoteAt(int position) {
        return notes.get(position);
    }


    public class NoteHolder extends RecyclerView.ViewHolder {
        TextView txtPriority, txtTitle, txtDescription;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            txtPriority = itemView.findViewById(R.id.txt_vw_priority);
            txtTitle = itemView.findViewById(R.id.txt_vw_title);
            txtDescription = itemView.findViewById(R.id.txt_vw_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listerner != null && position != RecyclerView.NO_POSITION) {
                        listerner.OnItemClick(notes.get(position));
                    }


                }
            });
        }
    }

    public interface OnItemClickListerner {
        void OnItemClick(NoteEntity noteEntity);
    }

    public void setOnItemClickListerner(OnItemClickListerner listerner) {
        this.listerner = listerner;

    }
}
