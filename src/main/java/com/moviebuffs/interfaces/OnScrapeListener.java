package com.moviebuffs.interfaces;

public interface OnScrapeListener {
    void onSuccess(String jsonResult);
    void onError(String errorMessage);
}
