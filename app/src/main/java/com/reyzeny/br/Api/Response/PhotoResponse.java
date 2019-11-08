package com.reyzeny.br.Api.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoResponse {
    @SerializedName("stat")
    public String stat;
    @SerializedName("photos")
    public Photos photos;

    public class Photos {
        @SerializedName("page")
        public int page;
        @SerializedName("pages")
        public int totalPages;
        @SerializedName("perpage")
        int per_page;
        @SerializedName("total")
        int totalPictures;
        @SerializedName("photo")
        public List<Photo> photoList;

        public class Photo {
            @SerializedName("id")
            public String id;
            @SerializedName("secret")
            public String secret;
            @SerializedName("server")
            public String server;
            @SerializedName("farm")
            public int farm;
            @SerializedName("title")
            public String title;
        }
    }
}

