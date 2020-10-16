package com.mycompany.nats.avro.producer;

import java.io.ByteArrayOutputStream;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.avro.Schema.Field;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.util.Utf8;

public class MyTransformerBinary {

    private DatumReader<GenericRecord> datumReader;
    private DatumWriter<GenericRecord> datumWriter;
    private Schema schema;

    public MyTransformerBinary(String avroSchemaPath) {
        try (InputStream in = getClass().getResourceAsStream(avroSchemaPath)) {
            schema = new Schema.Parser().parse(in);
            datumReader = new GenericDatumReader<GenericRecord>(schema);
            datumWriter = new GenericDatumWriter<GenericRecord>(schema);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public byte[] serialize(Map<String, Object> map) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            GenericRecord user1 = new GenericData.Record(schema);
            for(String key: map.keySet()){
                user1.put(key, map.get(key));
            }
            
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(stream, null);
            datumWriter.write(user1, encoder);
            encoder.flush();
            return stream.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Map<String, Object> deserialize(byte[] data) {
        try {
            Map<String, Object> map = new HashMap();
            BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(data, null);
            GenericRecord record = datumReader.read(null, decoder);
            List<Field> fields = schema.getFields();
            for(Field field: fields){
                String name = field.name();
                map.put(name, String.valueOf(record.get(name)));
            }
            //Map map = record.getSchema().getObjectProps();
            //Map map = schema.getObjectProps(record.get("name"));
           // System.out.println(map);
            return map;
            /*String name = ((Utf8) record.get("name")).toString();
            Integer favoriteNumber = (Integer) record.get("favorite_number");
            System.out.println("name=" + name);
            System.out.println("favoriteNumber=" + favoriteNumber);
            Map<String, Object> map = new HashMap();
            map.put("name", name);
            map.put("favorite_number", favoriteNumber);
            return map;*/
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
