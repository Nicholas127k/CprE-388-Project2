package com.example.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.fragment.app.FragmentActivity;

import com.example.workdaybutbetter.AdvisorMainScreenFragment;
import com.example.workdaybutbetter.ControlFragment;
import com.example.workdaybutbetter.CreateClassFragment;

public class TabAdapter extends FragmentStateAdapter {

    public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AdvisorMainScreenFragment();
            case 1:
                return new CreateClassFragment();
            case 2:
                return new ControlFragment();
            default:
                return new AdvisorMainScreenFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;  // Total number of tabs
    }
}

