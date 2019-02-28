package com.example.keshar.architecture_example_mvvm.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.keshar.architecture_example_mvvm.R;
import com.example.keshar.architecture_example_mvvm.RoomDatabase.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<NoteEntity,NoteAdapter.NoteHolder> {

    private OnItemClickListerner listerner;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }
    public static final DiffUtil.ItemCallback<NoteEntity> DIFF_CALLBACK=new DiffUtil.ItemCallback<NoteEntity>() {
        @Override
        public boolean areItemsTheSame( NoteEntity noteEntity, NoteEntity t1) {
            return noteEntity.getId()==t1.getId();
        }

        @Override
        public boolean areContentsTheSame( NoteEntity noteEntity,  NoteEntity t1) {
            return noteEntity.getTitle().equals(t1.getTitle())
                    && noteEntity.getDescription().equals(t1.getDescription())
                    && noteEntity.getPriority()==t1.getPriority();
        }
    };


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_item, viewGroup, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {

        NoteEntity noteEntity = getItem(i);
        noteHolder.txtPriority.setText(String.valueOf(noteEntity.getPriority()));
        noteHolder.txtTitle.setText(noteEntity.getTitle());
        noteHolder.txtDescription.setText(noteEntity.getDescription());

    }


    public NoteEntity getNoteAt(int position) {
        return getItem(position);
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
                        listerner.OnItemClick(getItem(position));
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
