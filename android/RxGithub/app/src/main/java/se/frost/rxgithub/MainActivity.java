package se.frost.rxgithub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import se.frost.rxgithub.screen.SearchCitiesUserFragment;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragments();
    }

    private void initFragments() {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.contentFrame, SearchCitiesUserFragment.newInstance(), SearchCitiesUserFragment.TAG)
                .commitAllowingStateLoss();
        getFragmentManager().executePendingTransactions();
    }

}
