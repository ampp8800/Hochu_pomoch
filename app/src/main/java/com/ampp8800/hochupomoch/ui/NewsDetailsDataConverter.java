package com.ampp8800.hochupomoch.ui;

import android.content.Context;
import android.icu.text.SimpleDateFormat;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.R;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class NewsDetailsDataConverter {
    @NonNull
    public static String getDate(@NonNull Long startDate, @NonNull Long endDate, @NonNull Context context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyLocalizedPattern("dd.MM");
        DateTime currentDate = new DateTime().now();
        if (currentDate.isBefore(startDate)) {
            return new SimpleDateFormat("LLLL dd, yyyy", new Locale("ru")).format(startDate);
        } else {
            DateTime endDateTime = new DateTime(endDate);
            return context.getResources().getString(R.string.left) + Days.daysBetween(currentDate, endDateTime) +
                    "(" + simpleDateFormat.format(startDate) + " - " +
                    simpleDateFormat.format(endDate) + ")";
        }
    }

    @NonNull
    public static List<Long> convertStringToListOfLongs(@NonNull String string) {
        List<String> stringsArray = Arrays.asList(string.replaceAll("[^\\dA-Za-z ]", "").split(" "));
        List<Long> result = new ArrayList<>();
        for (String number : stringsArray) {
            result.add(Long.parseLong(number));
        }
        return result;
    }

    @NonNull
    public static List<String> convertStringToListOfStrings(@NonNull String string) {
        return Arrays.asList(string.split("\\s*,\\s*"));
    }
}
