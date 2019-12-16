package com.example.cs356_project.Activities.ViewTools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs356_project.Activities.Activity_ViewListItem;
import com.example.cs356_project.AudioPlayer.AudioController;
import com.example.cs356_project.R;
import com.example.cs356_project.dataModel.CheckListItem;
import com.example.cs356_project.dataModel.UserSettings.UserSettings;
import com.google.android.material.snackbar.Snackbar;

import java.util.Comparator;
import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CheckListItemView>
    {
    public CheckListAdapter(List<CheckListItem> checkListItems)
        {
        }

    @Override
    public CheckListItemView onCreateViewHolder(ViewGroup parent, int viewType)
        {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_list_item, parent, false);
        CheckListItemView viewHolder = new CheckListItemView(view);
        return (viewHolder);
        }

    @Override
    public void onBindViewHolder(CheckListAdapter.CheckListItemView holder, int position)
        {
        CheckListItem listItem = UserSettings.checkListItems.get(position);
        holder.BindCheckListItem(listItem);
        }

    @Override
    public int getItemCount()
        {
        return (UserSettings.checkListItems.size());
        }

    public class CheckListItemView extends RecyclerView.ViewHolder
        {
        View view;
        CheckListItem checkListItem;

        public CheckListItemView(View itemView)
            {
            super(itemView);
            this.view = itemView;
            }

        public void BindCheckListItem(CheckListItem input)
            {
            this.checkListItem = input;
            System.out.println("-----> Binding Item : " + input.GetContents() + "/" + input.getCompleted() + "/" + checkListItem.getCompleted());

            //Get the views needed
            final TextView content = view.findViewById(R.id.list_content);
            final LinearLayout parent = view.findViewById(R.id.checkListItem_P);

            //Set the contents
            content.setText(checkListItem.GetContents());

            //Set the checkbox
            final CheckBox checkBox = view.findViewById(R.id.list_checkbox);
            checkBox.setChecked(checkListItem.getCompleted());
            if(checkListItem.getCompleted())
                {
                parent.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.colorTaskCompleted));
                content.setPaintFlags(content.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            else
                {
                parent.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.white));
                //content.setTextColor(ContextCompat.getColor(view.getContext(),R.color.white));
                content.setPaintFlags(content.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                content.setTextColor(ContextCompat.getColor(view.getContext(),R.color.black));
                }

            checkBox.setOnClickListener(new CompoundButton.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v)
                {
                System.out.println("Something changed: " + checkListItem.GetContents());
                checkListItem.SetCompleted(checkBox.isChecked());

                if(checkListItem.getCompleted())
                    {
                    try
                        {
                        parent.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.colorTaskCompleted));

                        //Text changes
                        //content.setTextColor(ContextCompat.getColor(view.getContext(),R.color.white));
                        content.setPaintFlags(content.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        AudioController.PlayCongrats(view.getContext());

                        //Vibration
                        Vibrator vib = (Vibrator) view.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        vib.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

                        Snackbar snackbar = Snackbar.make(view,"Congrats on finishing that task!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        System.out.println(checkListItem.GetContents());
                        }
                    catch (Exception e)
                        {
                        e.printStackTrace();
                        }
                    }
                else
                    {
                    parent.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.white));
                    //content.setTextColor(ContextCompat.getColor(view.getContext(),R.color.white));
                    content.setPaintFlags(content.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    content.setTextColor(ContextCompat.getColor(view.getContext(),R.color.black));
                    }

                checkBox.setChecked(checkListItem.getCompleted());
                }
            });

            ImageView editDetailsIcon = view.findViewById(R.id.list_itemDetails);

            editDetailsIcon.setOnClickListener(new View.OnClickListener()
                {
                @Override
                public void onClick(View v)
                    {
                    //System.out.println("  --- >> Listening to click . . .");
                    Activity_ViewListItem.targetListItem = checkListItem;
                    Intent intent = new Intent(view.getContext(), Activity_ViewListItem.class);
                    view.getContext().startActivity(intent);
                    }
                });

            //Sublist
            final LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            SubListAdapter adapter = new SubListAdapter(checkListItem.subListItems);

            RecyclerView recyclerView = view.findViewById(R.id.list_subItemList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);

            //Set the checkbox
            final ImageView deleteButton = view.findViewById(R.id.list_itemDelete);
            deleteButton.setOnClickListener(new View.OnClickListener()
                {
                @Override
                public void onClick(View v)
                    {
                    UserSettings.checkListItems.remove(checkListItem);
                    UserSettings.UpdateList();
                    }
                });

            //End of BindCheckListItem
            }
        }
    }
