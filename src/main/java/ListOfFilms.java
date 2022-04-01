import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListOfFilms {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        final String password = " ";

        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(new URI("https://imdb-api.com/en/API/Top250Movies/" + password))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String json = response.body();


        //////////////////// WITHOUT GSON ////////////////////


        String[] movieList = parseJsonMovies(json);
        List<String> titles = parseTitle(movieList);
        titles.forEach(System.out::println);
        List<String> images = parseImage(movieList);
        images.forEach(System.out::println);


        ///////////////////// USING GSON /////////////////////

        Gson gson = new Gson();
        Listfilms listfilms = gson.fromJson(json, Listfilms.class);

        // TITLE//

        List<String> title = new ArrayList<>();
        listfilms.getFilm().forEach(film -> title.add(film.getTitle()));
        title.forEach(System.out::println);

        // IMAGE//
        List<String> image = new ArrayList<>();
        listfilms.getFilm().forEach(film -> image.add(film.getImage()));
        image.forEach(System.out::println);

    }
     /////////////// STRING METHODS /////////////////
    public static String[] parseJsonMovies(String json) {
        json = json.replace("{" + '"' + "items" + '"' + ":[", "");
        json = json.replace("]", "");
        json = json.replace("}", "}-");
        String[] movieList = json.split("-,");

        return movieList;
    }

    public static List<String> parseAttributes(String[] arrayMovies) {

        List<String> list = new ArrayList<String>(Arrays.asList(arrayMovies));
        List<String> titulos = new ArrayList<String>();
        list.forEach(film -> saveAttributes(film, titulos));
        return titulos;


    }


    private static List<String> saveAttributes(String film, List<String> filmesSplitados) {

        String[] splitParameter = film.split('"' + ",");
        Arrays.stream(splitParameter).forEach(s -> filmesSplitados.add(s));
        return filmesSplitados;
    }

    private static List<String> parseTitle(String[] movieList) {

        List<String> atributos = parseAttributes(movieList);
        List<String> titles = new ArrayList<>();
        atributos.forEach(att -> saveTitle(att, titles));
        return titles;

    }

    private static List<String> saveTitle(String att, List titles) {

        if (att.contains('"' + "title" + '"' + ":" + '"')) {
            titles.add(att.replace('"' + "title" + '"' + ":" + '"', ""));

        }
        return titles;
    }

    private static List<String> parseImage(String[] movieList) {

        List<String> atributos = parseAttributes(movieList);
        List<String> images = new ArrayList<>();
        atributos.forEach(att -> saveImage(att, images));
        return images;

    }

    private static List<String> saveImage(String att, List titles) {

        if (att.contains('"' + "image" + '"' + ":" + '"')) {
            titles.add(att.replace('"' + "image" + '"' + ":" + '"', ""));

        }
        return titles;
    }

    ///////////////// GSON CLASSESS ////////////////

    public static class Listfilms {
        private List<Items> items;

        public List<Items> getFilm() {
            return items;
        }

        public void setFilm(List<Items> film) {
            this.items = film;
        }

        @Override
        public String toString() {
            return "Listfilms{" +
                    "film=" + items +
                    '}';
        }
    }

    public static class Items {
        @Override
        public String toString() {
            return "Items{" +
                    "id='" + id + "\n" +
                    ", rank='" + rank + "\n" +
                    ", title='" + title + "\n" +
                    ", fullTitle='" + fullTitle + "\n" +
                    ", year='" + year + "\n" +
                    ", image='" + image + "\n" +
                    ", crew='" + crew + "\n" +
                    ", imDbRating='" + imDbRating + "\n" +
                    ", imDbRatingCount='" + imDbRatingCount + '\'' +
                    '}' + "\n";
        }

        private String id;
        private String rank;
        private String title;
        private String fullTitle;
        private String year;
        private String image;
        private String crew;
        private String imDbRating;
        private String imDbRatingCount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFullTitle() {
            return fullTitle;
        }

        public void setFullTitle(String fullTitle) {
            this.fullTitle = fullTitle;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCrew() {
            return crew;
        }

        public void setCrew(String crew) {
            this.crew = crew;
        }

        public String getImDbRating() {
            return imDbRating;
        }

        public void setImDbRating(String imDbRating) {
            this.imDbRating = imDbRating;
        }

        public String getImDbRatingCount() {
            return imDbRatingCount;
        }

        public void setImDbRatingCount(String imDbRatingCount) {
            this.imDbRatingCount = imDbRatingCount;
        }
    }


}
