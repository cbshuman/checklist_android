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

    public class CheckSubListItemView extends RecyclerView.ViewHolder
        {
        View view;
        SubListItem checkListItem;

        public CheckSubListItemView(View itemView)
            {
            super(itemView);
            this.view = itemView;
            }

        public void BindListItem(final SubListItem subListItem)
            {
            //System.out.println("  ----->   Binding Item : " + subListItem.GetContents());
            //Set the contents
            final TextView content = view.findViewById(R.id.sublist_content);
            content.setText(subListItem.GetContents());

            //Set the checkbox
            final CheckBox checkBox = view.findViewById(R.id.sublist_checkbox);
            checkBox.setChecked(subListItem.getCompleted());

            checkBox.setOnClickListener(new View.OnClickListener()
                {
                @Override
                public void onClick(View v)
                    {
                    LinearLayout parent = view.findViewById(R.id.checkListItem_P);
                    subListItem.SetCompleted(checkBox.isChecked());
                    checkBox.setChecked(subListItem.getCompleted());

                    if(subListItem.getCompleted())
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
                });

            //Set the checkbox
            final ImageView deleteButton = view.findViewById(R.id.sublist_delete);
            deleteButton.setOnClickListener(new View.OnClickListener()
                {
                @Override
                public void onClick(View v)
                    {
                    subListItem.RemoveFromParent();
                    UserSettings.UpdateList();
                    }
                });
            //End of BindCheckListItem
            }
        }
    }
