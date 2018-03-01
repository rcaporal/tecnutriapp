package com.example.caporal.tecnutriapp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by caporal on 21/02/18.
 */

public class Constants {
    public static final String baseUrl = "http://api.tecnonutri.com.br/api/v4/";
    public static final String FEED_HASH_STRING_PARCELABLE = "feedHashString";
    public static final String PROFILE_STRING_PARCELABLE = "profileString";

    public static final List<String> mealTypeList = Collections.unmodifiableList(Arrays.asList(
            "Café da Manhã de", "Lanche da Manhã de","Almoço de","Lanche da Tarde de","Jantar de",
            "Ceia de","Pré-Treino de","Pós-Treino de"));
}
