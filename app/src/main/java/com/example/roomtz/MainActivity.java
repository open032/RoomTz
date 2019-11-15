package com.example.roomtz;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.roomtz.dialogFragment.AddItemDialog;
import com.example.roomtz.dialogFragment.RenameDialogFragment;
import com.example.roomtz.dialogFragment.RenameOrDelete;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddItemDialog.IDialogListener,
RenameDialogFragment.IRename, RenameOrDelete.IDelete, RenameOrDelete.IRename{
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private int mPositionRename;
    private Boolean mBoolean;
    private RenameDialogFragment mRenameDialogFragment;

    private AddItemDialog mMyCustomDialog;
    private RenameOrDelete mRenameOrDelete;

    private NoteViewModel noteViewModel;

    private int mChange = 0;
    private int mChange2 = 0;
    private int mChange3 = 0;


    //private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                /*Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);*/
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {

                String name = note.getTitle();
                mPositionRename = note.getId();

                renameItem(name);

                boolean bool = note.getBool();
                Toast.makeText(MainActivity.this, "adapter.setOnItemClickListener = "
                        +bool, Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnItemLongClickListener(new NoteAdapter.OnLongClickListener() {
            @Override
            public void onLongClicked(Note note) {
                String name = note.getTitle();
                mPositionRename = note.getId();
                renameDeleteItem(name);
                boolean bool = note.getBool();
                Toast.makeText(MainActivity.this, "LongClick = "
                        +bool, Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnBooleanListener(new NoteAdapter.OnBooleanListener() {
            @Override
            public void onBool(Note note) {
                Boolean bool = note.getBool();
                /*Toast.makeText(MainActivity.this, "adapter.setOnBooleanListener = "
                        + bool, Toast.LENGTH_SHORT).show();*/
                mPositionRename = note.getId();
                String name = note.getTitle();
                isChecked(name, bool);
            }
        });
    }

    private void openDialog() {
        mMyCustomDialog = new AddItemDialog();
        mMyCustomDialog.show(getFragmentManager(), "MyCustomDialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
               // Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void addItem(String itemName) {
        Note note = new Note(itemName, false);
        noteViewModel.insert(note);
       // Toast.makeText(this, "add " + itemName, Toast.LENGTH_SHORT).show();
    }
    private void renameItem(String name) {
        mRenameDialogFragment = new RenameDialogFragment();
        mRenameDialogFragment.show(getFragmentManager(), "RenameDialogFragment");
        mRenameDialogFragment.setName(name);
    }
    private void renameDeleteItem(String name) {
        mRenameOrDelete = new RenameOrDelete();
        mRenameOrDelete.show(getFragmentManager(), "RenameOrDelete");
        mRenameOrDelete.setNamePosition(name);

    }
    private void isChecked(String name, Boolean bool) {
        if(bool == true) {
            Note note = new Note(name, false);
            note.setId(mPositionRename);
            noteViewModel.update(note);
            //Toast.makeText(this, "isChecked = " + bool, Toast.LENGTH_SHORT).show();
        }
        if(bool == false) {
            Note note = new Note(name, true);
            note.setId(mPositionRename);
            noteViewModel.update(note);
            //Toast.makeText(this, "isChecked = " + bool, Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void renameName(String rename) {
       // Toast.makeText(this, "" + rename, Toast.LENGTH_SHORT).show();
        Note note = new Note(rename, false);
        note.setId(mPositionRename);
        noteViewModel.update(note);
    }
    @Override
    public void renameInDel(String rename) {
      //  Toast.makeText(this, "renameInDel " + rename, Toast.LENGTH_SHORT).show();

        Note note = new Note(rename, false);
        note.setId(mPositionRename);
        noteViewModel.update(note);
    }

    @Override
    public void deletePosition(String rename) {
         Note note = new Note(rename, false);
        note.setId(mPositionRename);
        noteViewModel.delete(note);
    }




}

















