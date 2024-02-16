package net.bobdb.inventoryservice;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductJSonTest {

    @Test
    void shouldCompareJson() throws JSONException {
        var actual = getRestData();
        var expected = """
                [
                    {
                        "id": null,
                        "name": "A Widget",
                        "description": "Something of some use",
                        "price": 100.00
                    }
                ]
        """;
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    void shouldCompareJsonPath() throws JSONException {
        var json = """
                        {"products" :[
                            {
                                "id": null,
                                "name": "A Widget",
                                "description": "Something of some use",
                                "price": 100.00
                            },
                           {
                                "id": null,
                                "name": "Another Widget",
                                "description": "Something of some more use",
                                "price": 200.00
                            }
                           ]
                        }
                """;
        Integer length = JsonPath.read(json,"$.products.length()");
        String name = JsonPath.read(json, "$.products[1].name");
        assertEquals(2, length);
        assertEquals("Another Widget", name);

    }

    private String getRestData() {
        return """
                [
                    {
                        "id": null,
                        "name": "A Widget",
                        "description": "Something of some use",
                        "price": 100.00
                    }
                ]
        """;
    }
}
