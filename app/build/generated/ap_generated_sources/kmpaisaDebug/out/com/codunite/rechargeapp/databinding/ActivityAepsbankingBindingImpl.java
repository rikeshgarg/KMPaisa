package com.codunite.rechargeapp.databinding;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityAepsbankingBindingImpl extends ActivityAepsbankingBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.lay_top, 3);
        sViewsWithIds.put(R.id.img_back, 4);
        sViewsWithIds.put(R.id.img_view3, 5);
        sViewsWithIds.put(R.id.heading, 6);
        sViewsWithIds.put(R.id.img_wallet, 7);
        sViewsWithIds.put(R.id.toolbar_txt_walletbal, 8);
        sViewsWithIds.put(R.id.img_ewallet, 9);
        sViewsWithIds.put(R.id.toolbar_txt_ewalletbal, 10);
        sViewsWithIds.put(R.id.tv_selectmethod, 11);
        sViewsWithIds.put(R.id.radio_bank_enq, 12);
        sViewsWithIds.put(R.id.radio_mini_stat, 13);
        sViewsWithIds.put(R.id.bank_details, 14);
        sViewsWithIds.put(R.id.radio_withdrawal, 15);
        sViewsWithIds.put(R.id.radio_aadharpay, 16);
        sViewsWithIds.put(R.id.tv_machinetype, 17);
        sViewsWithIds.put(R.id.device_type, 18);
        sViewsWithIds.put(R.id.radio_morpho, 19);
        sViewsWithIds.put(R.id.radio_mantra, 20);
        sViewsWithIds.put(R.id.input_type, 21);
        sViewsWithIds.put(R.id.radio_one, 22);
        sViewsWithIds.put(R.id.radio_two, 23);
        sViewsWithIds.put(R.id.radio_three, 24);
        sViewsWithIds.put(R.id.radio_four, 25);
        sViewsWithIds.put(R.id.edt_mob_number, 26);
        sViewsWithIds.put(R.id.edt_aadhar_number, 27);
        sViewsWithIds.put(R.id.edt_Amount, 28);
        sViewsWithIds.put(R.id.spinner_statelist, 29);
        sViewsWithIds.put(R.id.img_drop_1, 30);
        sViewsWithIds.put(R.id.txt_tobank, 31);
        sViewsWithIds.put(R.id.btn_process, 32);
        sViewsWithIds.put(R.id.btn_submit, 33);
        sViewsWithIds.put(R.id.tv_output, 34);
        sViewsWithIds.put(R.id.lay_web, 35);
        sViewsWithIds.put(R.id.lay_balance, 36);
        sViewsWithIds.put(R.id.txt_balance, 37);
        sViewsWithIds.put(R.id.txt_sharereceipt, 38);
        sViewsWithIds.put(R.id.img_cross_web, 39);
        sViewsWithIds.put(R.id.tv_output_webview, 40);
        sViewsWithIds.put(R.id.rv_main, 41);
        sViewsWithIds.put(R.id.lay_search, 42);
        sViewsWithIds.put(R.id.lay_searchview, 43);
        sViewsWithIds.put(R.id.img_search, 44);
        sViewsWithIds.put(R.id.edt_searche, 45);
        sViewsWithIds.put(R.id.img_cross, 46);
        sViewsWithIds.put(R.id.main_recycler, 47);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @Nullable
    private final com.codunite.rechargeapp.databinding.IncludeDemourlBinding mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityAepsbankingBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 48, sIncludes, sViewsWithIds));
    }
    private ActivityAepsbankingBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[14]
            , (androidx.appcompat.widget.AppCompatButton) bindings[32]
            , (androidx.appcompat.widget.AppCompatButton) bindings[33]
            , (android.widget.RadioGroup) bindings[18]
            , (android.widget.EditText) bindings[27]
            , (android.widget.EditText) bindings[28]
            , (android.widget.EditText) bindings[26]
            , (android.widget.EditText) bindings[45]
            , (me.grantland.widget.AutofitTextView) bindings[6]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.ImageView) bindings[46]
            , (android.widget.ImageView) bindings[39]
            , (android.widget.ImageView) bindings[30]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.ImageView) bindings[44]
            , (android.view.View) bindings[5]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.RadioGroup) bindings[21]
            , (android.widget.LinearLayout) bindings[36]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[42]
            , (android.widget.RelativeLayout) bindings[43]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.RelativeLayout) bindings[35]
            , (androidx.recyclerview.widget.RecyclerView) bindings[47]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.RadioButton) bindings[16]
            , (android.widget.RadioButton) bindings[12]
            , (android.widget.RadioButton) bindings[25]
            , (android.widget.RadioButton) bindings[20]
            , (android.widget.RadioButton) bindings[13]
            , (android.widget.RadioButton) bindings[19]
            , (android.widget.RadioButton) bindings[22]
            , (android.widget.RadioButton) bindings[24]
            , (android.widget.RadioButton) bindings[23]
            , (android.widget.RadioButton) bindings[15]
            , (android.widget.RelativeLayout) bindings[41]
            , (android.widget.Spinner) bindings[29]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[34]
            , (androidx.recyclerview.widget.RecyclerView) bindings[40]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[37]
            , (android.widget.TextView) bindings[38]
            , (android.widget.TextView) bindings[31]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (bindings[2] != null) ? com.codunite.rechargeapp.databinding.IncludeDemourlBinding.bind((android.view.View) bindings[2]) : null;
        this.prepaidAndSemilarLayout.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}