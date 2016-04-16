package brandon.payboy.brandon.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payboy.brandon.R;
import com.payboy.brandon.databinding.FragmentMainMoneyBinding;

import brandon.payboy.brandon.ui.activity.SettingsActivity;
import brandon.payboy.brandon.util.AppPreferences;
import brandon.payboy.brandon.util.AutoResizeTextView;
import brandon.payboy.brandon.util.Notification;
import brandon.payboy.brandon.viewmodels.MoneyViewModel;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MoneyFragment extends Fragment implements MoneyViewModel.MoneyViewModelListener {

    @Bind(R.id.money_tv) AutoResizeTextView moneyTextView;

    FragmentMainMoneyBinding binding;
    MoneyViewModel moneyViewModel;

    public MoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_money, container, false);
        View view = binding.getRoot();

        moneyViewModel = new MoneyViewModel(this, new AppPreferences(getActivity()), new Notification(getActivity()));
        binding.setViewModel(moneyViewModel);
        moneyViewModel.setup();

        ButterKnife.bind(this, view);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mplus-1c-black.ttf");
        moneyTextView.setTypeface(font);

        return view;
    }

    public void clearValues() {
        moneyViewModel.clearValues();
    }

    public void setMoneyValue(long elapsedSeconds) {
        moneyViewModel.setMoneyValue(elapsedSeconds);
    }

    @Override
    public void settingsPressed() {
        startActivity(new Intent(getActivity(), SettingsActivity.class));
    }
}