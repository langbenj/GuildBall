package com.example.langbenj.guildball.TeamFragmentList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.langbenj.guildball.DataAssemblers.Team;
import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.Helpers.StringArrayFragmentBusEvent;
import com.example.langbenj.guildball.Helpers.TeamListFragmentBusEvent;
import com.example.langbenj.guildball.R;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder>{

    private Team[] mLeague;

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTeamNameField;
        public ImageView mImageNameField;
        private int mCurrPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            //Assign references to the TextViews in the XML layout. These will be used later to write info to the fields
            mTeamNameField = (TextView) itemView.findViewById(R.id.team_name_field);
            mImageNameField = (ImageView) itemView.findViewById(R.id.team_list_logo_image);

            //Setup the onClickListener to handle click events on each view (individual row)
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String current_section = App.getCurrentSection();
            String passed_team= (String) mTeamNameField.getText();
            switch (current_section) {
                case "lists":
                    String [] passed_array = {"build_team_choose_guild",passed_team};
                    App.bus.post(new StringArrayFragmentBusEvent(passed_array));
                    break;
                case "guilds":
                    App.bus.post(new TeamListFragmentBusEvent(passed_team));

            }

        }
    }

    public TeamListAdapter() {
        mLeague = App.getLeague().getTeams();
    }

    @Override
    public TeamListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View teamView = inflater.inflate(R.layout.team_list_item, parent, false);
        return new ViewHolder(teamView);
    }

    @Override
    public void onBindViewHolder(TeamListAdapter.ViewHolder viewHolder, int position) {
        // Get the team name based on the current position
        String teamName = mLeague[position].getTeamName();
        // Set text fields based on the generated player info
        TextView textView;
        textView = viewHolder.mTeamNameField;
        textView.setText((CharSequence) teamName);

        //Pull the logo image view from the viewHolder
        ImageView image_view = viewHolder.mImageNameField;
        //build the string name of the team logo
        String logo_image = (teamName+"_logo").toLowerCase();
        //grab the resource id for the image and set the image view to it
        Context context = App.getContext();
        int image_id = context.getResources().getIdentifier(logo_image, "drawable", context.getPackageName());
        image_view.setImageResource(image_id);
    }

    @Override
    public int getItemCount() {
      return mLeague.length;

    }

    private void setPlayerInfoDisplay(View view, String res_ID, String contents, String type) {
        int view_id;
        int image_id;
        Context context = App.getContext();
        switch (type) {
            case "text":
                view_id = view.getResources().getIdentifier(res_ID, "id", context.getPackageName());
                TextView target_field = (TextView) view.findViewById(view_id);
                target_field.setText(contents);
                break;
            case "drawable":
                image_id = view.getResources().getIdentifier(contents, "drawable", context.getPackageName());
                view_id = view.getResources().getIdentifier(res_ID, "id", context.getPackageName());
                ImageView target_image_field = (ImageView) view.findViewById(view_id);
                target_image_field.setImageResource(image_id);
                break;
        }
    }
}
