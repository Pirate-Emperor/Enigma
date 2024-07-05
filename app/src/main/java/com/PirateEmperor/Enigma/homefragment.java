package com.PirateEmperor.Enigma;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.yks.secretmessaging.home.childList;
import static com.yks.secretmessaging.home.headerList;
import static com.yks.secretmessaging.home.populateExpandableList;
import static com.yks.secretmessaging.home.prepareMenuData;

public class homefragment extends Fragment {

  ExpandableListAdapter expandableListAdapter;
  ExpandableListView expandableListView;
  static Context context;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = null;
    view = inflater.inflate(R.layout.home_fragment, container, false);
    context = requireActivity();
    expandableListView = view.findViewById(R.id.expandableListView);
    populateExpandableList(context, expandableListView, expandableListAdapter, 2, getActivity());

    // requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,
    // new
    // operationfragment()).setReorderingAllowed(true).addToBackStack("Stack").commit();

    return view;
  }

}
