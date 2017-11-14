package cs121.ucsc.roomie;
import android.app.Activity;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.sendbird.android.SendBird;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private final String APPID = new String("A21A2BDC-6192-4A27-A4A9-F3FEA23F2CFA");
    private List<User> roomies;
    private ListView listView;
    private ArrayList<String> userList;
    private User currUser;
    private boolean secondPress;
    private String[] userArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roomies = SimpleLoginActivity.getUsers();
        String username = getIntent().getStringExtra("CurrentUser");
        //initialize the connection to SendBird servers
        SendBird.init(APPID, this);

        //create the list and buttons for displaying roommates
        //and find the current user
        listView = (ListView) findViewById(R.id.roomieList);
        Button displayMates = (Button) findViewById(R.id.roomies);
        secondPress = false;
        for(int i=0; i<roomies.size();i++){
            if(roomies.get(i).username.equals(username)){
                currUser = roomies.get(i);
                break;
            }
        }
        int counter=0;
        for(int i=0; i<roomies.size(); i++){
            if(!(roomies.get(i).username.equals(currUser.username)) &&
                    roomies.get(i).houseName == currUser.houseName){
                userList.add(roomies.get(i).username);
                counter+=1;
            }
        }
        userArray = new String[counter];
        for(int i=0; i< counter; i++){
            userArray[i] = userList.get(i);
        }

        ArrayAdapter<String> listArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_2, userArray);
       if(listArrayAdapter != null) {
           listView.setAdapter(listArrayAdapter);
       }


        displayMates.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(listView.getVisibility() == View.INVISIBLE){
                    listView.setVisibility(View.VISIBLE);
                }else{
                    listView.setVisibility(View.INVISIBLE);
                }
                if(secondPress == false){
                    view.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                    view.invalidate();
                }else if(secondPress == true){
                    view.getBackground().clearColorFilter();
                    view.invalidate();
                }

            }
        });
    }



}
