package com.juliensacre.androidarchicomponentimpl;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{
    private List<Note> notes;

    public RecyclerViewAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.setData(notes.get(notes.size()-(position+1))); //inverse the list
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void addItem (Note note){
        notes.add(note);
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.textView)
        TextView message;
        @BindView(R.id.tv_date)
        TextView date;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(Note note) {
            message.setText(note.getMessage());
            date.setText(note.getCreatedAt());
        }
    }
}
