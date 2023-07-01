// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActMemberListBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnAddmember;

  @NonNull
  public final CardView cardview;

  @NonNull
  public final IncludeActionbarBinding layActionbar;

  @NonNull
  public final RecyclerView rvMembers;

  private ActMemberListBinding(@NonNull LinearLayout rootView, @NonNull Button btnAddmember,
      @NonNull CardView cardview, @NonNull IncludeActionbarBinding layActionbar,
      @NonNull RecyclerView rvMembers) {
    this.rootView = rootView;
    this.btnAddmember = btnAddmember;
    this.cardview = cardview;
    this.layActionbar = layActionbar;
    this.rvMembers = rvMembers;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActMemberListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActMemberListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_member_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActMemberListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_addmember;
      Button btnAddmember = ViewBindings.findChildViewById(rootView, id);
      if (btnAddmember == null) {
        break missingId;
      }

      id = R.id.cardview;
      CardView cardview = ViewBindings.findChildViewById(rootView, id);
      if (cardview == null) {
        break missingId;
      }

      id = R.id.lay_actionbar;
      View layActionbar = ViewBindings.findChildViewById(rootView, id);
      if (layActionbar == null) {
        break missingId;
      }
      IncludeActionbarBinding binding_layActionbar = IncludeActionbarBinding.bind(layActionbar);

      id = R.id.rv_members;
      RecyclerView rvMembers = ViewBindings.findChildViewById(rootView, id);
      if (rvMembers == null) {
        break missingId;
      }

      return new ActMemberListBinding((LinearLayout) rootView, btnAddmember, cardview,
          binding_layActionbar, rvMembers);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
