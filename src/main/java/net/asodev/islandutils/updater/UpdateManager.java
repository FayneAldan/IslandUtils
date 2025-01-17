package net.asodev.islandutils.updater;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.asodev.islandutils.updater.schema.GithubRelease;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UpdateManager {

    HttpClient client;
    Gson gson;

    public UpdateManager() {
        client = HttpClient.newBuilder().build();
        gson = new Gson();
    }

    public CompletableFuture<GithubRelease> checkForUpdates() throws Exception {
        CompletableFuture<GithubRelease> f = new CompletableFuture<>();

        URI updatorURI = new URI("https://api.github.com/repos/AsoDesu/IslandUtils/releases/latest");
        HttpRequest req = HttpRequest.newBuilder(updatorURI).GET().build();

        client.sendAsync(req, HttpResponse.BodyHandlers.ofString()).thenAccept(res -> {
            f.complete(gson.fromJson(res.body(), GithubRelease.class));
        });

        return f;
    }

}
