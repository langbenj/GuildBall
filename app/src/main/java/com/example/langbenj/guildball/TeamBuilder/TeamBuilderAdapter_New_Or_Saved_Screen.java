package com.example.langbenj.guildball.TeamBuilder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.langbenj.guildball.DataAssemblers.BuiltTeams;
import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.Helpers.TeamBuilderListFragmentBusEvent;
import com.example.langbenj.guildball.R;

import java.util.ArrayList;

public class TeamBuilderAdapter_New_Or_Saved_Screen extends RecyclerView.Adapter<TeamBuilderAdapter_New_Or_Saved_Screen.ViewHolder> {
    private ArrayList<String> mTeamTest = new ArrayList<String>();
    private BuiltTeams[] teamList;

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
            String passed_team = (String) mTeamNameField.getText();
            App.bus.post(new TeamBuilderListFragmentBusEvent(passed_team));
        }
    }

    public TeamBuilderAdapter_New_Or_Saved_Screen() {


        mTeamTest.add("Midas-Conditions");
        mTeamTest.add("alchemists");
        mTeamTest.add("Midas");
        mTeamTest.add("Flask");
        mTeamTest.add("Vitriol");
        mTeamTest.add("Katalyst");
        mTeamTest.add("Venin");
        mTeamTest.add("Compound");
        mTeamTest.add("Hemlocke");
        mTeamTest.add("Mist");

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



        String teamName = mTeamTest.get(0);
        String teamLogo = mTeamTest.get(1);
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