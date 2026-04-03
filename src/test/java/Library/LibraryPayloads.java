package Library;

public class LibraryPayloads {
    public static String addBookRequestBody(String bookName, String aisle)
    {
        return "{\n" +
                "\"name\": \""+bookName+"\",\n" +
                "\"isbn\": \"route\", \n" +
                "\"aisle\": \""+aisle+"\",\n" +
                "\"author\": \"John Doe\"\n" +
                "}";
    }
}
