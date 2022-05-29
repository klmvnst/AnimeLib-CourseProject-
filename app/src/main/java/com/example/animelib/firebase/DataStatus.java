package com.example.animelib.firebase;

import java.util.List;

public interface DataStatus{
    void DataIsLoaded(List<Anime> anime, List<String> keys);
}