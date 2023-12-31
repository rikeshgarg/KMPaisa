// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActMainBinding implements ViewBinding {
  @NonNull
  private final DrawerLayout rootView;

  @NonNull
  public final FloatingActionButton bottomNavMore;

  @NonNull
  public final BottomNavigationView bottomNavigation;

  @NonNull
  public final FrameLayout container;

  @NonNull
  public final DrawerLayout drawerLayout;

  @NonNull
  public final ExpandableListView expandableListView;

  @NonNull
  public final RelativeLayout headlayout;

  @NonNull
  public final ImageView imgFooterNav;

  @NonNull
  public final ImageView ivCircleNotification;

  @NonNull
  public final ImageView ivCircleScan;

  @NonNull
  public final ImageView ivCircleTrophy;

  @NonNull
  public final IncludeActionbarMainBinding layActionbar;

  @NonNull
  public final RelativeLayout layBottomNavigation;

  @NonNull
  public final RelativeLayout layBottomNavigationCircle;

  @NonNull
  public final IncludeAppupdateBinding layUpdate;

  @NonNull
  public final LinearLayout llLogout;

  @NonNull
  public final NavigationView navView;

  @NonNull
  public final RelativeLayout rlMain;

  private ActMainBinding(@NonNull DrawerLayout rootView,
      @NonNull FloatingActionButton bottomNavMore, @NonNull BottomNavigationView bottomNavigation,
      @NonNull FrameLayout container, @NonNull DrawerLayout drawerLayout,
      @NonNull ExpandableListView expandableListView, @NonNull RelativeLayout headlayout,
      @NonNull ImageView imgFooterNav, @NonNull ImageView ivCircleNotification,
      @NonNull ImageView ivCircleScan, @NonNull ImageView ivCircleTrophy,
      @NonNull IncludeActionbarMainBinding layActionbar,
      @NonNull RelativeLayout layBottomNavigation,
      @NonNull RelativeLayout layBottomNavigationCircle, @NonNull IncludeAppupdateBinding layUpdate,
      @NonNull LinearLayout llLogout, @NonNull NavigationView navView,
      @NonNull RelativeLayout rlMain) {
    this.rootView = rootView;
    this.bottomNavMore = bottomNavMore;
    this.bottomNavigation = bottomNavigation;
    this.container = container;
    this.drawerLayout = drawerLayout;
    this.expandableListView = expandableListView;
    this.headlayout = headlayout;
    this.imgFooterNav = imgFooterNav;
    this.ivCircleNotification = ivCircleNotification;
    this.ivCircleScan = ivCircleScan;
    this.ivCircleTrophy = ivCircleTrophy;
    this.layActionbar = layActionbar;
    this.layBottomNavigation = layBottomNavigation;
    this.layBottomNavigationCircle = layBottomNavigationCircle;
    this.layUpdate = layUpdate;
    this.llLogout = llLogout;
    this.navView = navView;
    this.rlMain = rlMain;
  }

  @Override
  @NonNull
  public DrawerLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActMainBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottom_nav_more;
      FloatingActionButton bottomNavMore = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavMore == null) {
        break missingId;
      }

      id = R.id.bottom_navigation;
      BottomNavigationView bottomNavigation = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigation == null) {
        break missingId;
      }

      id = R.id.container;
      FrameLayout container = ViewBindings.findChildViewById(rootView, id);
      if (container == null) {
        break missingId;
      }

      DrawerLayout drawerLayout = (DrawerLayout) rootView;

      id = R.id.expandableListView;
      ExpandableListView expandableListView = ViewBindings.findChildViewById(rootView, id);
      if (expandableListView == null) {
        break missingId;
      }

      id = R.id.headlayout;
      RelativeLayout headlayout = ViewBindings.findChildViewById(rootView, id);
      if (headlayout == null) {
        break missingId;
      }

      id = R.id.img_footer_nav;
      ImageView imgFooterNav = ViewBindings.findChildViewById(rootView, id);
      if (imgFooterNav == null) {
        break missingId;
      }

      id = R.id.iv_circle_notification;
      ImageView ivCircleNotification = ViewBindings.findChildViewById(rootView, id);
      if (ivCircleNotification == null) {
        break missingId;
      }

      id = R.id.iv_circle_scan;
      ImageView ivCircleScan = ViewBindings.findChildViewById(rootView, id);
      if (ivCircleScan == null) {
        break missingId;
      }

      id = R.id.iv_circle_trophy;
      ImageView ivCircleTrophy = ViewBindings.findChildViewById(rootView, id);
      if (ivCircleTrophy == null) {
        break missingId;
      }

      id = R.id.lay_actionbar;
      View layActionbar = ViewBindings.findChildViewById(rootView, id);
      if (layActionbar == null) {
        break missingId;
      }
      IncludeActionbarMainBinding binding_layActionbar = IncludeActionbarMainBinding.bind(layActionbar);

      id = R.id.lay_bottom_navigation;
      RelativeLayout layBottomNavigation = ViewBindings.findChildViewById(rootView, id);
      if (layBottomNavigation == null) {
        break missingId;
      }

      id = R.id.lay_bottom_navigation_circle;
      RelativeLayout layBottomNavigationCircle = ViewBindings.findChildViewById(rootView, id);
      if (layBottomNavigationCircle == null) {
        break missingId;
      }

      id = R.id.lay_update;
      View layUpdate = ViewBindings.findChildViewById(rootView, id);
      if (layUpdate == null) {
        break missingId;
      }
      IncludeAppupdateBinding binding_layUpdate = IncludeAppupdateBinding.bind(layUpdate);

      id = R.id.ll_logout;
      LinearLayout llLogout = ViewBindings.findChildViewById(rootView, id);
      if (llLogout == null) {
        break missingId;
      }

      id = R.id.nav_view;
      NavigationView navView = ViewBindings.findChildViewById(rootView, id);
      if (navView == null) {
        break missingId;
      }

      id = R.id.rl_main;
      RelativeLayout rlMain = ViewBindings.findChildViewById(rootView, id);
      if (rlMain == null) {
        break missingId;
      }

      return new ActMainBinding((DrawerLayout) rootView, bottomNavMore, bottomNavigation, container,
          drawerLayout, expandableListView, headlayout, imgFooterNav, ivCircleNotification,
          ivCircleScan, ivCircleTrophy, binding_layActionbar, layBottomNavigation,
          layBottomNavigationCircle, binding_layUpdate, llLogout, navView, rlMain);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
