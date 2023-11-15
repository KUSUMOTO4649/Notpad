package Microsoft;

import java.util.HashMap;
import java.util.Map;

public class SearchResults {
    Map<String, String> relevantHeaders = new HashMap<>();
    String jsonResponse;
    SearchResults(HashMap<String, String> headers, String json) {
        relevantHeaders = headers;
        jsonResponse = json;
    }
}
