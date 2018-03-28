package se.frost.rxgithub.screen;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import se.frost.rxgithub.R;
import se.frost.rxgithub.model.City;
import se.frost.rxgithub.networking.NetworkManager;

/**
 * RxGithub
 * Created by Sergii Nezdolii on 2018-03-26.
 * <p>
 * Copyright (c) 2018 Frost°. All rights reserved.
 */
public class SearchCitiesUserFragment extends Fragment {

    public static final String TAG = SearchCitiesUserFragment.class.getSimpleName();

    private final CompositeDisposable disposables = new CompositeDisposable();

    @BindView(R.id.resultTextView)
    TextView resultTextView;

    @BindView(R.id.searchEditText)
    EditText searchEditText;

    City[] cities;

    public static SearchCitiesUserFragment newInstance() {
        return new SearchCitiesUserFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("City search");
        setRetainInstance(true);
        getCities();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_cities, container, false);
        ButterKnife.bind(this, view);
        setupViews();
        setupBindings();
        return view;
    }

    private void setupViews() {
        searchEditText.setEnabled(false);
        searchEditText.setHint("Loading");
    }

    private void setupBindings() {
        RxTextView.textChanges(searchEditText)
                .filter(charSequence -> charSequence.length() > 1)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .map(String::toLowerCase)
                .subscribe(this::searchCities);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    private void getCities() {
        // Async network call
        disposables.clear();
        disposables.add(NetworkManager.getInstance()
                .getApiClient()
                .getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetCitiesSuccess, this::onGetCitiesError));
    }

    private void onGetCitiesSuccess(City[] cities) {
        this.cities = cities;
        searchEditText.setEnabled(true);
        searchEditText.setHint("Search city");
    }

    private void onGetCitiesError(Throwable throwable) {
        searchEditText.setHint("Error... try again later\n" + throwable.getMessage());
    }

    private void searchCities(String query) {
        // Clear text
        resultTextView.setText("");
        // Async filtering
        Observable.fromArray(cities)
                .filter(city -> city.city.toLowerCase().contains(query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.toList()
                .subscribe(this::addCities, this::noItemsFound, this::onSearchDone);
    }

    private void addCities(City city) {
        resultTextView.append(String.format("%s in %s\n", city.city, city.state));
    }

    private void noItemsFound(Throwable throwable) {
        resultTextView.setText("No city found 💩");
    }

    private void onSearchDone() {
        Toast.makeText(getActivity(), "Search done", Toast.LENGTH_SHORT).show();
    }
}
