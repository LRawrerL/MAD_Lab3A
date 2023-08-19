package com.sp.restuarantlist;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TabHost;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class RestaurantList extends AppCompatActivity {
    private EditText restaurantName;
    private RadioGroup restaurantTypes;
    private Button buttonSave;
    private EditText restaurantAddress;
    private EditText restaurantTel;

    private List<Restaurant> model = new ArrayList<Restaurant>();
    private RestaurantAdapter adapter = null;
    private ListView list;
    private TabHost host;

    private boolean showMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        restaurantName = findViewById(R.id.restauarant_name);
        restaurantTypes = findViewById(R.id.restaurant_types);

        buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);

        restaurantAddress = findViewById(R.id.restaurant_address);
        restaurantTel = findViewById(R.id.restaurant_tel);

        list = findViewById(R.id.restaurants);
        adapter = new RestaurantAdapter();
        list.setAdapter(adapter);

        host = findViewById(R.id.tabHost);
        host.setup();

        TabHost.TabSpec spec = host.newTabSpec("List");
        spec.setContent(R.id.restaurants_tab);
        spec.setIndicator("List");
        host.addTab(spec);

        spec = host.newTabSpec("Details");
        spec.setContent(R.id.details_tab);
        spec.setIndicator("Details");
        host.addTab(spec);
        host.setCurrentTab(1);
        list.setOnItemClickListener(onListClick);

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    protected void onStart() {
        invalidateOptionsMenu();
        super.onStart();
    }

    @Override
    public void invalidateOptionsMenu() {
        if (host.getCurrentTab() == 0) {
            showMenu = false;
        }
        else if (host.getCurrentTab() == 1) {
            showMenu = true;
        }
        super.invalidateOptionsMenu();;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (showMenu == true) {
            getMenuInflater().inflate(R.menu.option, menu);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
                Toast.makeText(this, "Restaurant List - Version 1.0", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
    private View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nameStr = restaurantName.getText().toString();
            String addressStr = restaurantAddress.getText().toString();
            String telStr = restaurantTel.getText().toString();
            String restType= "";
            int radioID = restaurantTypes.getCheckedRadioButtonId();
            if (radioID == R.id.chinese) {
                restType = "Chinese";
            } else if (radioID == R.id.western) {
                restType = "Western";
            } else if (radioID == R.id.indian) {
                restType = "Indian";
            } else if (radioID == R.id.indonesian) {
                restType = "Indonesian";
            } else if (radioID == R.id.korean) {
                restType = "Korean";
            } else if (radioID == R.id.japanese) {
                restType = "Japanese";
            } else if (radioID == R.id.thai) {
                restType = "Thai";
            }
            String combineStr = nameStr + "\n" + addressStr + "\n" + telStr + "\n" + restType;
            Toast.makeText(getApplicationContext(), combineStr, Toast.LENGTH_LONG).show();

            Restaurant restaurant = new Restaurant();
            restaurant.setName(nameStr);
            restaurant.setAddress(addressStr);
            restaurant.setTelephone(telStr);
            restaurant.setRestaurantType(restType);
            adapter.add(restaurant);
            host.setCurrentTab(0);
        }
    };

    static class RestaurantHolder {
        private TextView restName = null;
        private TextView addr = null;
        private ImageView icon = null;
        RestaurantHolder(View row) {
            restName = row.findViewById(R.id.restName);
            addr = (row.findViewById(R.id.restAddr));
            icon = row.findViewById(R.id.icon);
        }
        void populateFrom(Restaurant r) {
            restName.setText(r.getName());
            addr.setText(r.getAddress().toString() + " " + r.getTelephone().toString());
            if (r.getRestaurantType().equals("Chinese")) {
                icon.setImageResource(R.drawable.ball_red);
            }
            else if (r.getRestaurantType().equals("Western")) {
                icon.setImageResource(R.drawable.ball_green);
            }
            else {
                icon.setImageResource(R.drawable.ball_yellow);
            }
        }
    }

    class RestaurantAdapter extends ArrayAdapter<Restaurant> {
        RestaurantAdapter() {
            super(RestaurantList.this,R.layout.row, model);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            RestaurantHolder holder;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.row, parent, false);
                holder = new RestaurantHolder(row);
                row.setTag(holder);
            }
            else {
                holder = (RestaurantHolder)row.getTag();
            }
            holder.populateFrom(model.get(position));
            return (row);
        }
    }

    AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Restaurant r = model.get(position);

            restaurantName.setText(r.getName());
            restaurantAddress.setText(r.getAddress());
            restaurantTel.setText(r.getTelephone());

            if (r.getRestaurantType().equals("Chinese")) {
                restaurantTypes.check(R.id.chinese);
            }
            else if (r.getRestaurantType().equals("Western")) {
                restaurantTypes.check(R.id.western);
            }
            else if (r.getRestaurantType().equals("Indian")) {
                restaurantTypes.check(R.id.indian);
            }
            else if (r.getRestaurantType().equals("Indonesia")) {
                restaurantTypes.check(R.id.indonesian);
            }
            else if (r.getRestaurantType().equals("Korean")) {
                restaurantTypes.check(R.id.korean);
            }
            else if (r.getRestaurantType().equals("Japanese")) {
                restaurantTypes.check(R.id.japanese);
            }
            else {
                restaurantTypes.check(R.id.thai);
            }
            host.setCurrentTab(1);
        }
    };
}