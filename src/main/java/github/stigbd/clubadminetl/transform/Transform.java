package github.stigbd.clubadminetl.transform;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;

public class Transform {

    private static void printTable(Element table) {
        Elements rows = table.select("tr");

        int i = 0;
        for (Element row : rows) {
            System.out.println("\n\nRow #" + i++);
            Elements tds = row.select("td");
            for (Element td : tds) {
                System.out.println(td.text()); // --> This will print them indiviadually
            }
        }
    }

    private static void printTableToJSON (Element table) {
        try {
            File fileDir = new File("/tmp/20151207_brukere.json");

            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileDir), "UTF8"));

            Elements rows = table.select("tr");

            // Table headers are in the first row:
            Element headerRow = rows.get(0);
            Elements headertds = headerRow.select("td");
            ArrayList<String> headerList = new ArrayList<>();
            for (Element td : headertds) {
                if (td.text().length() != 0) {
                    headerList.add(td.text());
                }
            }
            // Need to put two dummies:
            headerList.add("dummy1");
            headerList.add("dummy2");

            int headerCount = 0;
            for (String s : headerList) {
                System.out.println("Header #" + headerCount++ + ": >" + s + "<");
            }

            for (Element row : rows) {
                Elements tds = row.select("td");
                JSONObject obj = new JSONObject();
                int i = 0;
                for (Element td : tds) {
                    obj.put(headerList.get(i++), td.text());
                }
                obj.remove("dummy1");
                obj.remove("dummy2");
                out.append(obj.toJSONString()).append("\n");
            }

            out.flush();
            out.close();

        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        File input = new File("/tmp/20151207_brukere.html");

        Document doc;
        try {
            doc = Jsoup.parse(input, "UTF-8", ""); //<table width="100%" border=0 cellpadding=3 cellspacing=0>
            ArrayList<Element> tables = doc
                    .getElementsByTag("table");

            System.out.println("Number of tables: " + tables.size());
            int i = 0;
            System.out.println("Printing table with index " + i++);
            printTable(tables.get(5));
            printTableToJSON(tables.get(5));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}