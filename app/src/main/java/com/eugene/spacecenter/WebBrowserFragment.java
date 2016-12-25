package com.eugene.spacecenter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebBrowserFragment extends Fragment {

    public WebBrowserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_web_browser, container, false);

        WebView webView=(WebView)rootView.findViewById(R.id.web_view);

        String urlWiki = getArguments().getString("url");

        if (urlWiki!=null) {
            webView.loadUrl(urlWiki);
        }

        Button button = (Button) rootView.findViewById(R.id.back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.root_frame, new PlanetsInfoFragment());
                transaction.commit();
            }
        });


        return rootView;
    }

}
