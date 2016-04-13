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


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTeamNameField;
        public ImageView mImageNameField;
        private int mCurrPosition;


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
            App.bus.post(new LoadSavedTeamBusEvent("Alchemists"));
        }
    }

    public TeamBuilderAdapter_New_Or_Saved_Screen() {
        SavedTeamsDbHelper current_database = App.getSavedTeamsDB();
        SQLiteDatabase db = current_database.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.

        String[] projection = {
                "team_id",
                "teamname",
                "team",
                "player1",
                "player2",
                "player3",
                "player4",
                "player5",
                "player6",
                "player7",
                "player8",
                "player9",
                "player10",
                "player11",
                "player12",
                "player13",
                "player14",
                "player15",
                "player16"
        };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                "teamname" + " DESC";

        Cursor c = db.query(
                "teams",  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        c.moveToFirst();
        String team_name = c.getString(c.getColumnIndexOrThrow("teamname"));
        mTeamNames.add("Midas-Conditions");
        mTeamNames.add("alchemists");

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



        String teamName = mTeamNames.get(0);
        String teamLogo = mTeamNames.get(1);
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
        return 1;

    }

}