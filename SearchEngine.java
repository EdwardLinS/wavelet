import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> stringList = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().contains("/add")) {
            String[] stringsToAdd = url.getQuery().split("=");
            for (String s : stringsToAdd) {
                stringList.add(s);
            }
            return (stringsToAdd.length > 1) ? ("Strings added!") : ("String added!");
        } else if (url.getPath().contains("/search")) {
            String key = (String)url.getQuery();
            ArrayList<String> matches = new ArrayList<>();
            for (String s : stringList) {
                if (s.contains(key)) {
                    matches.add(s);
                }
            }
            return (matches.toString());
        } 

        if (stringList.size() == 0) {
            return "Add strings to the list or search for strings!";
        } else {
            return "404 NOT FOUND!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
