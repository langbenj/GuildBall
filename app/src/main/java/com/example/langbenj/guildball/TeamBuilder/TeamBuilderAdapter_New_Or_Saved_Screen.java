package com.example.langbenj.guildball.TeamBuilder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.langbenj.guildball.DataAssemblers.TeamList;
import com.example.langbenj.guildball.Databases.SavedTeamsDbHelper;
import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.Helpers.LoadSavedTeamBusEvent;

import com.example.langbenj.guildball.R;

import java.util.ArrayList;

public class TeamBuilderAdapter_New_Or_Saved_Screen extends RecyclerView.Adapter<TeamBuilderAdapter_New_Or_Saved_Screen.ViewHolder> {
    private ArrayList<String> mTeamNames = new ArrayList<String>();
    private ArrayList<String> mTeamTeams = new ArrayList<String>();
    private Cursor mSavedTeamDatabaseResults;
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTeamNameField;
        public ImageView mImageNameField;
        private final SavedTeamsDbHelper current_database = App.getSavedTeamsDB();
        private final SQLiteDatabase db = current_database.getReadableDatabase();

        public ViewHolder(View itemView) {
            super(itemView);
            //Assign references to the TextViews in the XML layout. These will be used later to write info to the fields
            mTeamNameField = (TextView) itemView.findViewById(R.id.build_team_name_field);
            mImageNameField = (ImageView) itemView.findViewById(R.id.build_team_list_logo_image);
            //Setup the onClickListener to handle click events on each view (individual row)
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Handles the clicks on the saved team names
            String[] fields_to_return = {"team_id"};
            String selection = "teamname" + " LIKE ?";
            String [] passed_team_name= {(String) mTeamNameField.getText()};
            Cursor results = db.query("teams", fields_to_return, selection, passed_team_name, null, null, null);
            results.moveToFirst();
            String team_id = results.getString(results.getColumnIndexOrThrow("team_id"));
            results.close();


            App.bus.post(new LoadSavedTeamBusEvent(team_id));
        }
    }

    public TeamBuilderAdapter_New_Or_Saved_Screen() {
        SavedTeamsDbHelper current_database = App.getSavedTeamsDB();
        SQLiteDatabase db = current_database.getReadableDatabase();
        String[] fields_to_return = {"team_id","teamname", "team"};
        String sort_type ="teamname" + " DESC";
       mSavedTeamDatabaseResults = db.query("teams",fields_to_return, null,null, null, null, sort_type );
        mSavedTeamDatabaseResults.moveToFirst();
        String team_name;
        String team_team;
        for (int i=0; i<mSavedTeamDatabaseResults.getCount(); i++) {
            team_name = mSavedTeamDatabaseResults.getString(mSavedTeamDatabaseResults.getColumnIndexOrThrow("teamname"));
            mTeamNames.add(team_name);
            team_team = mSavedTeamDatabaseResults.getString(mSavedTeamDatabaseResults.getColumnIndexOrThrow("team"));
            mTeamTeams.add(team_team);
            mSavedTeamDatabaseResults.moveToNext();
        }
    }

    @Override
    public TeamBuilderAdapter_New_Or_Saved_Screen.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View teamView = inflater.inflate(R.layout.team_build_list_item, parent, false);
        return new ViewHolder(teamView);
    }

    @Override
    public void onBindViewHolder(TeamBuilderAdapter_New_Or_Saved_Screen.ViewHolder viewHolder, int position) {
        // Get the team name based on the current position

        // Set text fields



        String teamName = mTeamNames.get(position);
        String teamLogo = mTeamTeams.get(position);
        TextView textView = viewHolder.mTeamNameField;
        textView.setText((CharSequence) teamName);
        //Pull the logo image view from the viewHolder
        ImageView image_view = viewHolder.mImageNameField;
        //build the string name of the team logo
        String logo_image = (teamLogo + "_logo").toLowerCase();
        //grab the resource id for the image and set the image view to it
        Context context = App.getContext();
        int image_id = context.getResources().getIdentifier(logo_image, "drawable", context.getPackageName());
        image_view.setImageResource(image_id);



    }

    @Override
    public int getItemCount() {
        return mSavedTeamDatabaseResults.getCount();

    }

}