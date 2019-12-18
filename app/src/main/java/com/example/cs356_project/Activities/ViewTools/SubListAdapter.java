package com.example.cs356_project.Activities.ViewTools;

import android.graphics.Paint;
import android.media.Image;
import android.text.style.UpdateLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs356_project.AudioPlayer.AudioController;
import com.example.cs356_project.R;
import com.example.cs356_project.dataModel.CheckListItem;
import com.example.cs356_project.dataModel.SubListItem;
import com.example.cs356_project.dataModel.UserSettings.UserSettings;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SubListAdapter extends RecyclerView.Adapter<SubListAdapter.CheckSubListItemView>
    {
    private List<SubListItem> subListItems;

    public SubListAdapter(List<SubListItem> subListItems)
        {
        this.subListItems = subListItems;
        }

    @Override
    public CheckSubListItemView onCreateViewHolder(ViewGroup parent, int viewType)
        {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_sublist_item, parent, false);
        CheckSubListItemView viewHolder = new CheckSubListItemView(view);
        return (viewHolder);
        }

    @Override
    public void onBindViewHolder(CheckSubListItemView holder, int position)
        {
        SubListItem listItem = subListItems.get(position);
        holder.BindListItem(listItem);
        }

    @Override
    public int getItemCount()
        {
        return (subListItems.size());
        }

    public void RemoveItem(int position)
        {
        subListItems.remove(position);
        notifyItemRemoved(position);
        }

    public void RestoreItem(SubListItem item, int position)
        {
        subListItems.add(position, item);
        notifyItemInserted(position);
        }

    public class CheckSubListItemView extends RecyclerView.ViewHolder
        {
        View view;
        SubListItem checkListItem;

        public CheckSubListItemView(View itemView)
            {
            super(itemView);
            this.view = itemView;
            }

        public void BindListItem(SubListItem input)
            {
            checkListItem = input;
            //System.out.println("  ----->   Binding Item : " + subListItem.GetContents());
            //Get the required views
            final TextView content = view.findViewById(R.id.sublist_content);
            final View parent = view.findViewById(R.id.checkListItem_P);

            //Set the text
            content.setText(checkListItem.GetContents());

            //Set the checkbox
            final CheckBox checkBox = view.findViewById(R.id.sublist_checkbox);
            if(checkListItem.getCompleted())
                {
                checkBox.setChecked(checkListItem.getCompleted());
                parent.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.colorTaskCompleted));
                content.setPaintFlags(content.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }

            checkBox.setOnClickListener(new View.OnClickListener()
                {
                @Override
                public void onClick(View v)
                    {
                    if(!checkListItem.ParentCompleted())
                        {
                        checkListItem.SetCompleted(checkBox.isChecked());
                        checkBox.setChecked(checkListItem.getCompleted());

                        if(checkListItem.getCompleted())
                            {
                            parent.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.colorTaskCompleted));

                            //Text changes
                            //content.setTextColor(ContextCompat.getColor(view.getContext(),R.color.white));
                            content.setPaintFlags(content.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                            AudioController.PlayMinorCongrats(view.getContext());

                            Snackbar snackbar = Snackbar.make(view,"Congrats on finishing that subtask!", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            }
                        else
                            {
                            parent.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.white));

                            //Text changes
                            //content.setTextColor(ContextCompat.getColor(view.getContext(),R.color.white));
                            content.setPaintFlags(content.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);

                            content.setTextColor(ContextCompat.getColor(view.getContext(),R.color.black));
                            }
                        }
                    }
                });
            //End of BindCheckListItem
            }
        }
    }
