package se.frost.rxgithub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pddstudio.highlightjs.HighlightJsView;
import com.pddstudio.highlightjs.models.Language;
import com.pddstudio.highlightjs.models.Theme;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.jsonView)
    HighlightJsView jsonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initJsonView();
    }

    private void initJsonView() {
        jsonView.setTheme(Theme.SOLARIZED_DARK);
        jsonView.setHighlightLanguage(Language.JSON);
        jsonView.setSource("{\"hello\":\"world\"}");
        jsonView.reload();
    }
}
