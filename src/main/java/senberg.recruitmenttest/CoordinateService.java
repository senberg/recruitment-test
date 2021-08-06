package senberg.recruitmenttest;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class CoordinateService {
    private static final String URL = "http://daily.digpro.se/bios/servlet/bios.servlets.web.RecruitmentTestServlet";
    private static final Charset FORCED_CHARSET = StandardCharsets.ISO_8859_1;
    private static final Pattern COORDINATE_PATTERN = Pattern.compile("^-?\\d+,\\s-?\\d+,\\s[A-ZÅÄÖ\\-0-9]+$");

    private final MapPanel mapPanel;
    private final StatusService statusService;

    public CoordinateService(MapPanel mapPanel, StatusService statusService) {
        this.mapPanel = mapPanel;
        this.statusService = statusService;
    }

    public void refreshCoordinates() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    statusService.addMessage("Requesting new coordinates.");
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
                    String response = client.send(request, (responseInfo) -> HttpResponse.BodySubscribers.ofString(FORCED_CHARSET)).body();
                    statusService.addMessage("Parsing retrieved coordinates.");
                    Set<Coordinate> latestCoordinates = new HashSet<>();

                    response.lines().filter(line -> COORDINATE_PATTERN.matcher(line).matches()).forEach(line -> {
                        String[] parts = line.split(", ");
                        int x = Integer.parseInt(parts[0]);
                        int y = Integer.parseInt(parts[1]);
                        String label = parts[2];
                        latestCoordinates.add(new Coordinate(x, y, label));
                    });

                    mapPanel.setCoordinates(latestCoordinates);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    statusService.addMessage("Error while refreshing coordinates. " + e.getMessage());
                }
            }
        }.start();
    }
}
