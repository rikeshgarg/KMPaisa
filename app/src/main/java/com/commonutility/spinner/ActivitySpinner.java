package com.commonutility.spinner;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.rechargeapp.R;
import com.commonutility.GlobalVariables;
import com.commonutility.MyDividerItemDecoration;
import com.commonutility.NoInternetScreen;
import com.commonutility.ShowCustomToast;
import com.commonutility.customfont.FontUtils;

import java.util.List;

import me.grantland.widget.AutofitTextView;

public class ActivitySpinner extends AppCompatActivity implements View.OnClickListener, PopulateSpinnerAdapter.OnItemClickListener {
    private ProgressBar progressbarLoad;
    private RecyclerView recyclerView;
    private PopulateSpinnerAdapter mAdapter;
    private SearchView searchView;

    boolean isImageShow = true;
    private String title = "";
    private static List<SpinnerModel> itemList;

    public static final String TAG_DATA = "data";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_populatespinner);
        if (getIntent() != null && getIntent().hasExtra("title")) {
            title = getIntent().getStringExtra("title");
        }

        StartApp();
        resumeApp();
    }

    public void resumeApp() {
        AutofitTextView txtHeading = (AutofitTextView) findViewById(R.id.heading);
        txtHeading.setText(title);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.searchview);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        mAdapter = new PopulateSpinnerAdapter(svContext, itemList, this, isImageShow, false);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 0));
        recyclerView.setAdapter(mAdapter);
        progressbarLoad.setVisibility(View.GONE);
    }

    private Context svContext;
    private ShowCustomToast customToast;
    private ViewGroup root;

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        
        root = (ViewGroup) findViewById(R.id.headlayout);
        new NoInternetScreen(svContext, root, ActivitySpinner.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        hideKeyboard();
        loadToolBar();
        progressbarLoad = (ProgressBar) findViewById(R.id.progressbar_load);
        progressbarLoad.setVisibility(View.VISIBLE);
    }

    private void loadToolBar() {
        ImageView imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText(getString(R.string.app_name));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideFragmentkeyboard(Context meraContext, View meraView) {
        final InputMethodManager imm = (InputMethodManager) meraContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(meraView.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemClick(View view, SpinnerModel selectedSpinnerModel, int position) {
        itemList = null;
        setResultIntent(selectedSpinnerModel, position);
    }

    public static String EXTRA_SPINNER_DATA = "spinner_data";
    public static String EXTRA_SPINNER_POSITION = "spinner_position";

    public void setResultIntent(SpinnerModel spinnerData, int position) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SPINNER_DATA, spinnerData);
        intent.putExtra(EXTRA_SPINNER_POSITION, position);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Open Custom Spinner Activity directly from here
     */
    public static void showSpinner(Context context, List<SpinnerModel> listSpinner, String spinnerTitle, int request_code) {
        itemList = listSpinner;
        Intent intent = new Intent(context, ActivitySpinner.class);
        intent.putExtra("title", spinnerTitle);
        ((Activity) context).startActivityForResult(intent, request_code);
    }

}