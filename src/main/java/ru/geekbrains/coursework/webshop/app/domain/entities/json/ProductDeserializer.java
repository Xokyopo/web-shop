package ru.geekbrains.coursework.webshop.app.domain.entities.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.geekbrains.coursework.webshop.app.domain.entities.Brand;
import ru.geekbrains.coursework.webshop.app.domain.entities.Product;

import java.io.IOException;
import java.util.Set;

public class ProductDeserializer extends StdDeserializer<Product> {

    public ProductDeserializer() {
        this((Class) null);
    }

    protected ProductDeserializer(Class<?> vc) {
        super(vc);
    }

    protected ProductDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected ProductDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Product deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Product result = new Product();

        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode productNode = objectCodec.readTree(jsonParser);

        result.setId(productNode.get("id").asLong(0));
        result.setName(productNode.get("name").asText(""));
        result.setPrice(productNode.get("price").asLong(0));

        result.setImagesUrls(objectCodec.readValue(productNode.get("imagesUrls").traverse(), Set.class));
        result.setBrand(objectCodec.readValue(productNode.get("brand").traverse(), Brand.class));
        result.setCategories(objectCodec.readValue(productNode.get("categories").traverse(), Set.class));

        return result;
    }
}
