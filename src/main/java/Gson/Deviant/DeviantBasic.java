package Gson.Deviant;

import java.util.ArrayList;

public class DeviantBasic {
    public boolean has_more;
    public int next_offset;
    public int estimated_total;

    public ArrayList<DeviantResults> results;

    public class DeviantResults {
        public String deviationid;
        public String url;
        public String category;

        public DeviantContent content;

    }
}
