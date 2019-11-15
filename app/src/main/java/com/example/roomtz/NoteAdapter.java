package com.example.roomtz;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {
    private OnItemClickListener listener;
    private OnLongClickListener longListener;
    private OnBooleanListener bool;
    private static final String TAG = "logcat";
    private Boolean mBoolean;


    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(Note oldItem, Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Note oldItem, Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());

            /*&&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();*/
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteHolder holder, final int position) {
        final Note currentNote = getItem(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.checkBox.setChecked(currentNote.getBool());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Integer pos = (Integer) holder.checkBox.getTag();
                //Toast.makeText(ctx, imageModelArrayList.get(pos).getAnimal() + " clicked!", Toast.LENGTH_SHORT).show();

                if (currentNote.getBool()) {
                    bool.onBool(getItem(position));
                } else {
                    bool.onBool(getItem(position));
                }
            }
        });

       // holder.textViewDescription.setText(currentNote.getDescription());
       /// holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }


    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private ImageView imageView;
        private CheckBox checkBox;
       // private TextView textViewDescription;
        //private TextView textViewPriority;


        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            imageView = itemView.findViewById(R.id.image);
            checkBox = itemView.findViewById(R.id.checkbox);



            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAdapterPosition();
                    if (isChecked){
                        imageView.setImageResource(R.drawable.linux);
                        //bool.onBool(getItem(position));
                        //mBoolean = true;
                    } else {
                        imageView.setImageResource(R.drawable.fox);
                        //bool.onBool(getItem(position));
                        //mBoolean = false;
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                        int position = getAdapterPosition();
                    if (longListener != null && position != RecyclerView.NO_POSITION) {
                            longListener.onLongClicked(getItem(position));
                        }
                    return false;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }
    public  interface OnLongClickListener {
        void  onLongClicked(Note note);
    }
    public interface OnBooleanListener {
        void onBool(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void setOnItemLongClickListener(OnLongClickListener longListener){
        this.longListener = longListener;
    }
    public void setOnBooleanListener(OnBooleanListener bool){
        this.bool = bool;
        Log.d(TAG, "setOnBooleanListener: "+ bool);
    }
}