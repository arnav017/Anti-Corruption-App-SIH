package com.example.anticorruptionapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anticorruptionapp.R;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.services.language.v1.CloudNaturalLanguage;
import com.google.api.services.language.v1.CloudNaturalLanguageRequestInitializer;
import com.google.api.services.language.v1.model.AnnotateTextRequest;
import com.google.api.services.language.v1.model.AnnotateTextResponse;
import com.google.api.services.language.v1.model.Document;
import com.google.api.services.language.v1.model.Entity;
import com.google.api.services.language.v1.model.Features;
import com.google.api.services.language.v1.model.Sentiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    public static final String API_KEY = "AIzaSyCBSWAhQezzANe1yfzZAdVEE9S-OfDo2XY";



    Button analyze;
    TextView sentiment;
    RecyclerView recyclerView;
    EditText docText;

    private List<Entity> entityList;

    private CloudNaturalLanguage naturalLanguageService;
    private Document document;
    private Features features;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        analyze = findViewById(R.id.analyze);
        sentiment = findViewById(R.id.sentiment);
        docText = findViewById(R.id.docText);

        naturalLanguageService = new CloudNaturalLanguage.Builder(
                AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(),
                null
        ).setCloudNaturalLanguageRequestInitializer(
                new CloudNaturalLanguageRequestInitializer(API_KEY)
        ).build();


        document = new Document();
        document.setType("PLAIN_TEXT");
        document.setLanguage("en-US");

        features = new Features();
        features.setExtractEntities(true);
        features.setExtractSyntax(true);
        features.setExtractDocumentSentiment(true);

        final AnnotateTextRequest request = new AnnotateTextRequest();
        request.setDocument(document);
        request.setFeatures(features);


        analyze.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String text = docText.getText().toString().trim();
        if (!TextUtils.isEmpty(text)) {
            document.setContent(text);

            final AnnotateTextRequest request = new AnnotateTextRequest();
            request.setDocument(document);
            request.setFeatures(features);

            new AsyncTask<Object, Void, AnnotateTextResponse>() {
                @Override
                protected AnnotateTextResponse doInBackground(Object... params) {
                    AnnotateTextResponse response = null;
                    try {
                        response = naturalLanguageService.documents().annotateText(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return response;
                }

                @Override
                protected void onPostExecute(AnnotateTextResponse response) {
                    super.onPostExecute(response);
                    if (response != null) {
                        Sentiment sent = response.getDocumentSentiment();
                        Log.e("xxx",response.toString());
                        Toast.makeText(FeedbackActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        sentiment.setText("Score : " + sent.getScore() + " Magnitude : " + sent.getMagnitude());
                    }
                }
            }.execute();
        }
    }
}
