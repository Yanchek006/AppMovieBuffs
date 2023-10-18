package com.moviebuffs.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.moviebuffs.R;


public class HomeFragment extends Fragment {

    private NavController navController;
//    Button toMenuB;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

//        toMenuB = root.findViewById(R.id.buttonToMenu);

//        toMenuB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //i was stuck on this button about from the beginning,
//                //but finally i find the solution in documentations
//                //https://developer.android.com/guide/navigation/navigation-navigate#id
//
////                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
////                transaction.replace(R.id.Home_ConstraintLayout, new MovieListFragment());
////                transaction.addToBackStack(null);
////                transaction.commit();
//
////                Navigation.findNavController(view).navigate(R.id.action_global_nav_movies);
////                NavHostFragment navHostFragment =
////                        (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
////                navController = navHostFragment.getNavController();
//////                navController.navigate(R.id.action_menu_home_item_to_menu_movies_item);
////                navController.navigate(R.id.action_menu_home_item_to_menu_movies_item, null, new NavOptions.Builder().addToBackStack("home").build());
////                MovieListFragment movieListFragment = new MovieListFragment();
////                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
////                transaction.replace(R.id.Home_ConstraintLayout, movieListFragment);
////                transaction.addToBackStack(null);
////                transaction.commit();
//            }
//        });


        return root;
    }


}