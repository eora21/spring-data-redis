package eora21.demo.redis.template;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

public record NewMutant(Long id, String name, int age, int power) {

    private static final String DELIMITER = "/";

    public byte[] serialize() {

        String mutantText = new StringJoiner(DELIMITER)
                .add(String.valueOf(id))
                .add(name)
                .add(String.valueOf(age))
                .toString();

        return mutantText.getBytes(StandardCharsets.UTF_8);
    }

    public static NewMutant deserialize(byte[] bytes) {
        String value = new String(bytes);
        String[] mutantInfo = value.split(DELIMITER);

        if (mutantInfo.length != 4) {
            throw new IllegalArgumentException();
        }

        long id = Long.parseLong(mutantInfo[0]);
        String name = mutantInfo[1];
        int age = Integer.parseInt(mutantInfo[2]);
        int power = Integer.parseInt(mutantInfo[3]);

        return new NewMutant(id, name, age, power);
    }
}
