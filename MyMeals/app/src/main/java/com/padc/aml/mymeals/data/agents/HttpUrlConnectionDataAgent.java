package com.padc.aml.mymeals.data.agents;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.padc.aml.mymeals.MyMealsApp;
import com.padc.aml.mymeals.data.models.MealModel;
import com.padc.aml.mymeals.data.responses.MealListResponse;
import com.padc.aml.mymeals.data.vos.MealVO;
import com.padc.aml.mymeals.utils.CommonInstances;
import com.padc.aml.mymeals.utils.MyMealsConstants;

/**
 * Created by user on 7/9/2016.
 */
public class HttpUrlConnectionDataAgent implements MealDataAgent {

    private static HttpUrlConnectionDataAgent objInstance;

    private HttpUrlConnectionDataAgent() {

    }

    public static HttpUrlConnectionDataAgent getInstance() {
        if (objInstance == null) {
            objInstance = new HttpUrlConnectionDataAgent();
        }
        return objInstance;
    }

    @Override
    public void loadMeals() {
        new AsyncTask<Void, Void, List<MealVO>>() {

            @Override
            protected List<MealVO> doInBackground(Void... voids) {
                URL url;
                BufferedReader reader = null;
                StringBuilder stringBuilder;

                try {
                    // create the HttpURLConnection
                    url = new URL(MyMealsConstants.MEAL_BASE_URL + MyMealsConstants.API_GET_MEAL_LIST);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // just want to do an HTTP POST here
                    connection.setRequestMethod("POST");

                    // uncomment this if you want to write output to this url
                    //connection.setDoOutput(true);

                    // give it 15 seconds to respond
                    connection.setReadTimeout(15 * 1000);

                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    //put the request parameter into NameValuePair list.
                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair(MyMealsConstants.PARAM_ACCESS_TOKEN, MyMealsConstants.ACCESS_TOKEN));

                    //write the parameters from NameValuePair list into connection obj.
                    OutputStream outputStream = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(outputStream, "UTF-8"));
                    writer.write(getQuery(params));
                    writer.flush();
                    writer.close();
                    outputStream.close();

                    connection.connect();

                    // read the output from the server
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    stringBuilder = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }

                    String responseString = stringBuilder.toString();
                    MealListResponse response = CommonInstances.getGsonInstance().fromJson(responseString, MealListResponse.class);
                    List<MealVO> mealList = response.getMealList();

                    return mealList;

                } catch (Exception e) {
                    Log.e(MyMealsApp.TAG, e.getMessage());
                    MealModel.getInstance().notifyErrorInLoadingMeals(e.getMessage());
                } finally {
                    // close the reader; this can throw an exception too, so
                    // wrap it in another try/catch block.
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(List<MealVO> mealList) {
                super.onPostExecute(mealList);
                if (mealList != null || mealList.size() > 0) {
                    MealModel.getInstance().notifyMealsLoaded(mealList);
                }
            }
        }.execute();
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}