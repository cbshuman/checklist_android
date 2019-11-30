package com.example.cs356_project.Activities.ViewTools;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs356_project.Activities.CheckListActivity;
import com.example.cs356_project.AudioPlayer.AudioController;
import com.example.cs356_project.R;
import com.example.cs356_project.dataModel.CheckListItem;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CheckListItemView>
    {
    private List<CheckListItem> checkListItems;

    public CheckListAdapter(List<CheckListItem> checkListItems)
        {
        this.checkListItems = checkListItems;
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
        CheckListItem listItem = checkListItems.get(position);
        holder.BindCheckListItem(listItem);
        }

    @Override
    public int getItemCount()
        {
        return (checkListItems.size());
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

        public void BindCheckListItem(final CheckListItem checkListItem)
            {
            System.out.println("  ----->   Binding Item . . .");
            //Set the contents
            final TextView content = view.findViewById(R.id.list_content);
            content.setText(checkListItem.GetContents());

            //Set the checkbox
            final CheckBox checkBox = view.findViewById(R.id.list_checkbox);
            checkBox.setChecked(checkListItem.completed);

            checkBox.setOnClickListener(new View.OnClickListener()
                {
                @Override
                public void onClick(View v)
                    {
                    LinearLayout parent = view.findViewById(R.id.checkListItem_P);
                    checkListItem.completed = checkBox.isChecked();

                    if(checkListItem.completed)
                        {
                        parent.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.colorTaskCompleted));

                        //Text changes
                        //content.setTextColor(ContextCompat.getColor(view.getContext(),R.color.white));
                        content.setPaintFlags(content.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        AudioController.PlayCongrats(view.getContext());

                        Snackbar snackbar = Snackbar.make(view,"Congrats on finishing that task!", Snackbar.LENGTH_SHORT);
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
            }
        }
    }
