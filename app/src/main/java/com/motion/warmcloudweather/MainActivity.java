package com.motion.warmcloudweather;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.motion.warmcloudweather.location.ILocationContract;
import com.motion.warmcloudweather.location.LocationPresenter;
import com.permissionx.guolindev.PermissionX;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements ILocationContract.ILocationView {


    private ILocationContract.ILocationPresenter mLocationPresenter;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.rv_content)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mLocationPresenter = new LocationPresenter(this.getApplicationContext());
        mLocationPresenter.setView(this);

        setToolBar();
        setRecyclerViewContent();

        PermissionX.init(this)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        mLocationPresenter.startLocation();
                    } else {
                        Toast.makeText(this, "need permission grant", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void setToolBar() {
        this.setSupportActionBar(mToolBar);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
//        ActionBarDrawerToggle
    }

    private void setRecyclerViewContent() {
        List<String> myData = new ArrayList<>();
        int index = 0;
        while (index++ < 100) {
            myData.add(String.valueOf(index));
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(new BaseRecyclerViewAdapter(this, myData));
    }

    @Override
    public void setLocationInfo(String cityName) {
        Timber.e(cityName);
        Toast.makeText(this, cityName, Toast.LENGTH_SHORT).show();
    }


}

class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<String> data;
    private LayoutInflater layoutInflater;

    public BaseRecyclerViewAdapter(Context context, List<String> content) {
        this.data = content;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.content_item, null);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class BaseViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.tv_content);
    }
}