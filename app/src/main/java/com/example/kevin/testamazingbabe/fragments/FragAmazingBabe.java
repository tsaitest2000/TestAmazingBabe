package com.example.kevin.testamazingbabe.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kevin.testamazingbabe.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FragAmazingBabe extends Fragment {

   private ImageView ivBabe;
   private TextView tvBabe;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.frag_amazing_babe, null);
      ivBabe = (ImageView) view.findViewById(R.id.imageView);
      tvBabe = (TextView) view.findViewById(R.id.textView);
      return view;
   }

   @Override
   public void onResume() {
      super.onResume();
      new RunWork().start();
   }

   private class RunWork extends Thread {

      @Override
      public void run() {
         try {
            Document doc = Jsoup.connect("https://zh.wikipedia.org/wiki/%E8%92%99%E5%85%B6%C2%B7D%C2%B7%E9%AD%AF%E5%A4%AB").get();
            Elements tables = doc.select("table");
            final Element table = tables.get(1);

            getActivity().runOnUiThread(new Runnable() {
               @Override
               public void run() {
                  tvBabe.setText(table.select("tr").get(0).select("th").get(0).text());
                  Picasso
                     .with(getActivity())
                     .load(table.select("tr").get(2).select("td").get(0).select("img").first().absUrl("src"))
                     .into(ivBabe);
               }
            });
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

}
